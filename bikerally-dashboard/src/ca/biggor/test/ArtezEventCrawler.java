package ca.biggor.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class ArtezEventCrawler {
	
	public static void main(String[] args)
			throws ParserConfigurationException, MalformedURLException, InterruptedException {
		Gson gson = new Gson();
		for (int i = 158165; i < 160000; i++) {
			String eventId = Integer.toString(i);
			URL url = new URL(
					"http://my.e2rm.com/webgetservice/get.asmx/getEvent?eventID=" + eventId + "&languageCode=");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = null;
			String str = null;
			try {
				doc = db.parse(url.openStream());
				str = doc.getElementsByTagName("string").item(0).getTextContent();
				str = str.replaceAll("''R", "R");
				str = str.replaceAll("s''", "s");
				str = str.replaceAll("''S ", "'S ");
				str = str.replaceAll("''", "\"\"");
				str = str.replaceAll("eventSettings", "\"eventSettings\"");
				str = str.replaceAll("titleIDs", "\"titleIDs\"");
				str = str.replaceAll("languages", "\"languages\"");
				str = str.replaceAll("columns", "\"columns\"");
				str = str.replaceAll(",rows", ",\"rows\"");
				str = str.replaceAll("paygateways", "\"paygateways\"");
				ArtezEvent artezEvent = gson.fromJson(str, ArtezEvent.class);
				System.out.println(artezEvent.toString());
			} catch (SAXException | IOException e) {
//				System.err.println(eventId + ": ");
			}
			Thread.sleep(100);

		}
	}
}

class EventSettings {
	private int EventID;
	private int OrgID;
	private String EventName;

	public String toString() {
		return EventID + ": " + OrgID + ": " + EventName;
	}
}

class ArtezEvent {
	private EventSettings eventSettings;

	public String toString() {
		return eventSettings.toString();
	}

}
