package ca.biggor.bikerally.dashboard;

import java.io.IOException;
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

@SuppressWarnings("serial")
public class Bikerally_updateDatabaseServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - updateDatabase");
		resp.getWriter().println();
		
//		get the participant list from artez		
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

		resp.getWriter().println("Artez db (master):");
		resp.getWriter().println(artezRiderList.size() + " riders");
		resp.getWriter().println(artezCrewList.size() + " crew");
				
//		get the participant list from the local db
		List<String> riderList = new ArrayList<String>();
		List<String> crewList = new ArrayList<String>();
		
		resp.getWriter().println("Local db (slave):");
		resp.getWriter().println(riderList.size() + " riders");
		resp.getWriter().println(crewList.size() + " crew");
		
				
//		compare and sync (both ways in case participants drop - check how the artez api handles this)
	}
}
