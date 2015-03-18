package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class Bikerally_util {

	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

	static String getParticipantCount(String eventId) {
		String participantCount = (String) memcache.get(eventId + "-participantCount");
		if (participantCount == null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query query = new Query(eventId).setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
			participantCount = Integer.toString(datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults()));
			memcache.put(eventId + "-participantCount", participantCount);
		}

		return participantCount;
	}

	static Integer getEventParticipantCount(String eventId) throws ParserConfigurationException, SAXException, IOException {
		Integer eventParticipantCount = (Integer) memcache.get(eventId + "-eventParticipantCount");
		if (eventParticipantCount == null) {
			URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getAllRegIDs?eventID=" + eventId + "&Source=");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url.openStream());
			eventParticipantCount = doc.getElementsByTagName("registrantID").getLength();
			memcache.put(eventId + "-eventParticipantCount", eventParticipantCount);
		}
		return eventParticipantCount;
	}

	static Integer getEventTotalCollected(String eventId) throws ParserConfigurationException, SAXException, IOException {

		Integer eventTotalCollected = (Integer) memcache.get(eventId + "-eventTotalCollected");
		if (eventTotalCollected == null) {
			URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getParticipantScoreBoard?eventID=" + eventId + "&languageCode=&sortBy=&listItemCount=&externalQuestionID=&externalAnswerID=&Source=");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url.openStream());
			Integer eventOnlineTotalCollected = Float.valueOf(doc.getElementsByTagName("eventOnlineTotalCollected").item(0).getTextContent()).intValue();
			Integer eventOfflineTotalCollected = Float.valueOf(doc.getElementsByTagName("eventOfflineTotalCollected").item(0).getTextContent()).intValue();
			eventTotalCollected = eventOnlineTotalCollected + eventOfflineTotalCollected;
			memcache.put(eventId + "-eventTotalCollected", eventTotalCollected);
		}

		return eventTotalCollected;
	}

	static String getCountRegistrationByDate(String eventId, Calendar byDate) {

		SimpleDateFormat artezDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

		switch (eventId) {
		// 2015
		case "148513":
		case "153652":
			byDate.add(Calendar.YEAR, 0);
			break;

		// 2014
		case "124639":
		case "125616":
			byDate.add(Calendar.YEAR, -1);
			break;

		// 2013
		case "96529":
		case "97260":
			byDate.add(Calendar.YEAR, -2);
			break;

		// 2012
		case "71589":
		case "75996":
			byDate.add(Calendar.YEAR, -3);
			break;

		// 2011
		case "52935":
		case "57207":
			byDate.add(Calendar.YEAR, -4);
			break;

		// 2010
		case "39408":
		case "39410":
			byDate.add(Calendar.YEAR, -5);
			break;

		default:
			break;
		}

		String byDateString = artezDateFormat.format(byDate.getTime());
		
		String countRegistrationByDate = "0";
		if (byDate.compareTo(new GregorianCalendar()) >= 0) {
			try {
				countRegistrationByDate = Integer.toString(getEventParticipantCount(eventId));
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		} else {
			countRegistrationByDate = (String) memcache.get(eventId + "-" + byDateString);
			if (countRegistrationByDate == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Query query = new Query(eventId).setFilter(new FilterPredicate("registrationDate", FilterOperator.LESS_THAN, byDateString));
				countRegistrationByDate = Integer.toString(datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults()));
				memcache.put(eventId + "-" + byDateString, countRegistrationByDate);
			}
		}

		return countRegistrationByDate;
	}
	
	static String getRecacheTime() {
		
		SimpleDateFormat stringDateFormat = new SimpleDateFormat("MMM dd, yyyy - HH:mm:ss");
		String recacheTime = (String) memcache.get("recacheTime");
		if (recacheTime == null) {
			stringDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
			recacheTime = stringDateFormat.format(new GregorianCalendar().getTime());
			memcache.put("recacheTime", recacheTime);
		}
		
		return recacheTime;
	}
	
	static String getJsonRoute(String routeId) throws IOException {
		String jsonRoute = (String) memcache.get(routeId + "-jsonString");
		if (jsonRoute == null) {
			Route route = new Route(routeId);
			jsonRoute = route.toJson();
			memcache.put(routeId + "-jsonString", jsonRoute);
		}
		return jsonRoute;
	}
	
	static void deleteJsonRoute(String routeId) {
		memcache.delete(routeId + "-jsonString");
	}
	
	static void deleteEventTotalsMemcache (String eventId) {
		memcache.delete(eventId + "-participantCount");
		memcache.delete(eventId + "-eventParticipantCount");
		memcache.delete(eventId + "-eventTotalCollected");
		memcache.delete("recacheTime");
	}
}
