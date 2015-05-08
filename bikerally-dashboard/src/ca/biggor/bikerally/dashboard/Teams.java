package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class Teams {

	public HashMap<String, String> teams;

	public Teams() {
	}

	public Teams(String eventId) {

		try {
			teams = new HashMap<String, String>();
			URL teamsUrl = new URL("http://my.e2rm.com/webgetservice/get.asmx/getTeams?eventID="+ eventId + "&captainFirstName=&captainLastName=&teamName=&locationID=&source=");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(teamsUrl.openStream());
			NodeList nodes = doc.getElementsByTagName("teams");
			for (int i = 0; i < nodes.getLength(); i++) {
				teams.put(nodes.item(i).getChildNodes().item(5).getTextContent(), nodes.item(i).getChildNodes().item(1).getTextContent());
			}
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
