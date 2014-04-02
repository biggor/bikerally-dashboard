package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class Bikerally_dashboardArtezServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - dashboardArtez");
		resp.getWriter().println();

		String riderEventId = "124639";
		String crewEventId = "125616";
		
		try {
			resp.getWriter().println("Artez: " + getEventParticipantsCount(riderEventId) + " riders, " + getEventParticipantsCount(crewEventId) + " crew");
			resp.getWriter().println(getEventTotalCollected(riderEventId) + getEventTotalCollected(crewEventId));
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
}
	
	private Integer getEventParticipantsCount(String eventId) throws ParserConfigurationException, SAXException, IOException {
		URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getAllRegIDs?eventID=" + eventId + "&Source=");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(url.openStream());
		return doc.getElementsByTagName("registrantID").getLength();
	}
	
	private Integer getEventTotalCollected(String eventId) throws ParserConfigurationException, SAXException, IOException {
		URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getParticipantScoreBoard?eventID=" + eventId + "&languageCode=&sortBy=&listItemCount=&externalQuestionID=&externalAnswerID=&Source=");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(url.openStream());
		Integer eventOnlineTotalCollected = Float.valueOf(doc.getElementsByTagName("eventOnlineTotalCollected").item(0).getTextContent()).intValue();
		Integer eventOfflineTotalCollected = Float.valueOf(doc.getElementsByTagName("eventOfflineTotalCollected").item(0).getTextContent()).intValue();
		
		return eventOnlineTotalCollected + eventOfflineTotalCollected;
	}
	
}
