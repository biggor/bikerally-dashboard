package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
public class Bikerally_updateDatabaseServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - updateDatabase");
		resp.getWriter().println();

		// get the participant list from artez
		List<String> artezRiderList = new ArrayList<String>();
		List<String> artezCrewList = new ArrayList<String>();

		URL ridersUrl = new URL("http://my.e2rm.com/webgetservice/get.asmx/getAllRegIDs?eventID=124639&Source=");
		URL crewUrl = new URL("http://my.e2rm.com/webgetservice/get.asmx/getAllRegIDs?eventID=125616&Source=");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(ridersUrl.openStream());
			NodeList nodes = doc.getElementsByTagName("registrantID");
			for (int i = 0; i < nodes.getLength(); i++) {
				artezRiderList.add(nodes.item(i).getTextContent());
			}
			doc = db.parse(crewUrl.openStream());
			nodes = doc.getElementsByTagName("registrantID");
			for (int i = 0; i < nodes.getLength(); i++) {
				artezCrewList.add(nodes.item(i).getTextContent());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		resp.getWriter().println("Artez db (master): " + artezRiderList.size() + " riders, " + artezCrewList.size() + " crew");

		// get the participant list from the local db		
		List<Entity> riderList = getParticipants("124639");
		List<Entity> crewList = getParticipants("125616");
		resp.getWriter().println("Local db (slave): " + riderList.size() + " riders, " + crewList.size() + " crew");

		// compare and sync (both ways in case participants drop - check how the artez api handles this)
		resp.getWriter().println();
		resp.getWriter().println("Rider updates");
		syncDatabases(artezRiderList, riderList, "124639", resp);

		resp.getWriter().println();
		resp.getWriter().println("Crew updates");
		syncDatabases(artezCrewList, crewList, "125616", resp);
	}

	private void syncDatabases(List<String> master, List<Entity> slave, String eventId, HttpServletResponse resp) throws IOException {
		int x = 0;
		int y = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		while ((x < slave.size()) || (y < master.size())) {
			// If the master list is exhausted, delete the current element from the slave list
			if (y >= master.size()) {
				datastore.delete(slave.get(x).getKey());
				resp.getWriter().println(slave.get(x).getProperty("id") + "-");
				x += 1;

				// otherwise, if the slave list is exhausted, insert the current element from the master list
			} else if (x >= slave.size()) {
				Entity participant = addParticipant(master.get(y), eventId);
				datastore.put(participant);
				resp.getWriter().println(master.get(y) + "+");
				y += 1;

				// otherwise, if the current slave element precedes the current master element, delete the current slave element.
			} else if (Integer.valueOf(slave.get(x).getProperty("id").toString()) < Integer.valueOf(master.get(y))) {
				datastore.delete(slave.get(x).getKey());
				resp.getWriter().println(slave.get(x).getProperty("id") + "-");
				x += 1;

				// # otherwise, if the current slave element follows the current master element, insert the current master element.
			} else if (Integer.valueOf(slave.get(x).getProperty("id").toString()) > Integer.valueOf(master.get(y))) {
				Entity participant = addParticipant(master.get(y), eventId);
				datastore.put(participant);
				resp.getWriter().println(master.get(y) + "+");
				y += 1;

				// otherwise the current elements match; consider the next pair
			} else {
				x += 1;
				y += 1;
			}
		}
	}

	private List<Entity> getParticipants(String eventId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(eventId).addSort("id");
		PreparedQuery participants = datastore.prepare(q);
		return participants.asList(FetchOptions.Builder.withDefaults());
	}
	
	private Entity addParticipant(String id, String eventId){
		Entity p = new Entity(eventId);
		p.setProperty("id", id);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getRegistrant?registrantID=" + id + "&Source=&uniqueID=");
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url.openStream());
			p.setProperty("firstName", doc.getElementsByTagName("firstName").item(0).getTextContent());
			p.setProperty("lastName", doc.getElementsByTagName("lastName").item(0).getTextContent());
			p.setProperty("participantLink", doc.getElementsByTagName("participantLink").item(0).getTextContent());
			p.setProperty("registrationDate", doc.getElementsByTagName("RegistrationDate").item(0).getTextContent());
			p.setProperty("address1", doc.getElementsByTagName("address1").item(0).getTextContent());
			p.setProperty("address2", doc.getElementsByTagName("address2").item(0).getTextContent());
			p.setProperty("address3", doc.getElementsByTagName("address3").item(0).getTextContent());
			p.setProperty("address4", doc.getElementsByTagName("address4").item(0).getTextContent());
			p.setProperty("city", doc.getElementsByTagName("city").item(0).getTextContent());
			p.setProperty("stateProvince", doc.getElementsByTagName("stateProvince").item(0).getTextContent());
			p.setProperty("postal", doc.getElementsByTagName("postal").item(0).getTextContent());
			p.setProperty("country", doc.getElementsByTagName("country").item(0).getTextContent());
			p.setProperty("email", doc.getElementsByTagName("email").item(0).getTextContent());
			p.setProperty("totalRaised", doc.getElementsByTagName("totalRaised").item(0).getTextContent());
			p.setProperty("totalSponsors", doc.getElementsByTagName("totalSponsors").item(0).getTextContent());
			p.setProperty("fundraisingGoal", doc.getElementsByTagName("fundraisingGoal").item(0).getTextContent());
			p.setProperty("displayOptin", doc.getElementsByTagName("displayOptin").item(0).getTextContent());
			p.setProperty("emailOptin", doc.getElementsByTagName("emailOptin").item(0).getTextContent());
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

