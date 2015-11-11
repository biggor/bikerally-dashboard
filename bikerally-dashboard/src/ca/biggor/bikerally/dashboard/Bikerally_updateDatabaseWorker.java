package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class Bikerally_updateDatabaseWorker extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");

		String eventId = req.getParameter("eventId");
		if (eventId == null) {
			throw new NullPointerException("Bikerally_updateDatabaseWorker custom exception: null eventId");
		}
		// get the participant list from artez
		List<String> artezParticipantList = new ArrayList<String>();

		URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getAllRegIDs?eventID=" + eventId + "&Source=");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url.openStream());
			NodeList nodes = doc.getElementsByTagName("registrantID");
			for (int i = 0; i < nodes.getLength(); i++) {
				artezParticipantList.add(nodes.item(i).getTextContent());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		resp.getWriter().println("Artez db (master): " + artezParticipantList.size() + " participants");

		// get the participant list from the local db
		List<Entity> riderList = getParticipants(eventId);

		resp.getWriter().println("Local db (slave): " + riderList.size() + " participants");

		// compare and sync (both ways in case participants drop - check how the
		// artez api handles this)
		resp.getWriter().println();
		resp.getWriter().println("Participants updates");
		syncDatabases(artezParticipantList, riderList, eventId, req, resp);
	}

	private void syncDatabases(List<String> master, List<Entity> slave, String eventId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int x = 0;
		int y = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		while ((x < slave.size()) || (y < master.size())) {
			// If the master list is exhausted, delete the current element from
			// the slave list
			String updateSymbol = "";
			Entity participant;

			if (y >= master.size()) {
				// datastore.delete(slave.get(x).getKey());
				participant = slave.get(x);
				participant.setProperty("status", "deleted");
				datastore.put(participant);
				updateSymbol = "-";
				x += 1;

				// otherwise, if the slave list is exhausted, insert the current
				// element from the master list
			} else if (x >= slave.size()) {
				participant = addParticipant(master.get(y), eventId);
				datastore.put(getParticipantDetails(participant));
				updateSymbol = "+";
				y += 1;

				// otherwise, if the current slave element precedes the current
				// master element, delete the current slave element.
			} else if (Integer.valueOf(slave.get(x).getProperty("id").toString().trim()) < Integer.valueOf(master.get(y))) {
				// datastore.delete(slave.get(x).getKey());
				participant = slave.get(x);
				participant.setProperty("status", "deleted");
				datastore.put(participant);
				updateSymbol = " -";
				x += 1;

				// # otherwise, if the current slave element follows the current
				// master element, insert the current master element.
			} else if (Integer.valueOf(slave.get(x).getProperty("id").toString().trim()) > Integer.valueOf(master.get(y))) {
				participant = addParticipant(master.get(y), eventId);
				datastore.put(getParticipantDetails(participant));
				updateSymbol = "+";
				y += 1;

				// otherwise the current elements match; consider the next pair
			} else {
				participant = slave.get(x);
				if (req.getParameter("updateAll") != null) {
					getParticipantDetails(participant);
					updateSymbol = "*";
				}
				String status = participant.getProperty("status").toString();
				if (status != null && !status.equals("virtual")) {
					participant.setProperty("status", "active");
					datastore.put(participant);
					updateSymbol = updateSymbol + ".";
				}
				x += 1;
				y += 1;
			}
			resp.getWriter().println(String.format("%2s", updateSymbol) + " " + participant.getProperty("id") + ": " + participant.getProperty("firstName") + " " + participant.getProperty("lastName"));
		}
	}

	private List<Entity> getParticipants(String eventId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(eventId).addSort("id");
		// Query q = new Query(eventId);
		PreparedQuery participants = datastore.prepare(q);
		return participants.asList(FetchOptions.Builder.withDefaults());
	}

	private Entity addParticipant(String id, String eventId) {
		Entity p = new Entity(eventId);
		p.setProperty("id", String.format("%7s", id.trim()));
		// custom properties
		p.setProperty("riderNumber", "");
		p.setProperty("status", "active");
		return p;
	}

	private Entity getParticipantDetails(Entity p) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getRegistrant?registrantID=" + p.getProperty("id").toString().trim() + "&Source=&uniqueID=");
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url.openStream());
			p.setProperty("firstName", doc.getElementsByTagName("firstName").item(0).getTextContent().trim());
			p.setProperty("lastName", doc.getElementsByTagName("lastName").item(0).getTextContent().trim());
			p.setProperty("participantLink", doc.getElementsByTagName("participantLink").item(0).getTextContent().trim());
			p.setProperty("registrationDate", doc.getElementsByTagName("RegistrationDate").item(0).getTextContent().trim());
			p.setProperty("address1", doc.getElementsByTagName("address1").item(0).getTextContent().trim());
			p.setProperty("address2", doc.getElementsByTagName("address2").item(0).getTextContent().trim());
			p.setProperty("address3", doc.getElementsByTagName("address3").item(0).getTextContent().trim());
			p.setProperty("address4", doc.getElementsByTagName("address4").item(0).getTextContent().trim());
			p.setProperty("city", doc.getElementsByTagName("city").item(0).getTextContent().trim());
			p.setProperty("stateProvince", doc.getElementsByTagName("stateProvince").item(0).getTextContent().trim());
			p.setProperty("postal", doc.getElementsByTagName("postal").item(0).getTextContent().trim());
			p.setProperty("country", doc.getElementsByTagName("country").item(0).getTextContent().trim());
			p.setProperty("email", doc.getElementsByTagName("email").item(0).getTextContent().trim());
			p.setProperty("totalRaised", doc.getElementsByTagName("totalRaised").item(0).getTextContent().trim());
			p.setProperty("totalSponsors", doc.getElementsByTagName("totalSponsors").item(0).getTextContent().trim());
			p.setProperty("fundraisingGoal", doc.getElementsByTagName("fundraisingGoal").item(0).getTextContent().trim());
			p.setProperty("displayOptin", doc.getElementsByTagName("displayOptin").item(0).getTextContent().trim());
			p.setProperty("emailOptin", doc.getElementsByTagName("emailOptin").item(0).getTextContent().trim());
			// custom properties
			org.jsoup.nodes.Document participantPage = Jsoup.connect(p.getProperty("participantLink").toString()).timeout(10000).get();
			Elements udfanswers = participantPage.select("div.udfanswerRow");
			for (Element udfanswer : udfanswers) {
				if (udfanswer.previousElementSibling() != null && udfanswer.previousElementSibling().text().trim().equals("Rider Years")) {
					p.setProperty("riderYears", udfanswer.text().trim());
				}
				if (udfanswer.previousElementSibling() != null && udfanswer.previousElementSibling().text().trim().equals("Rider Number")) {
					p.setProperty("riderNumber", udfanswer.text().trim());
				}
			}
			Elements teamInfo = participantPage.select("a#ctl00_ctl00_mainContent_bodyContentPlaceHolder_hyperLinkViewTeamPage");
			if (teamInfo.size() > 0) {
				p.setProperty("teamPageURL", teamInfo.get(0).attributes().get("href").trim());
				p.setProperty("teamId", teamInfo.get(0).attributes().get("href").split("=")[1].trim());
			}

			// p.setProperty("status", "active");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

}
