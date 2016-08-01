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
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class Bikerally_util {
	
	public static final String LOVEBOWL_EVENT_ID_2017 = "0";
	public static final String RIDER_EVENT_ID_2017 = "200862";
	public static final String RIDER_EVENT_ID_ONE_DAY_2017 = "200879";
	public static final String CREW_EVENT_ID_2017  = "0";

	public static final String LOVEBOWL_EVENT_ID_2016 = "186742";
	public static final String RIDER_EVENT_ID_2016 = "177536";
	public static final String RIDER_EVENT_ID_ONE_DAY_2016 = "179193";
	public static final String CREW_EVENT_ID_2016  = "179191";
	
	public static final String RIDER_EVENT_ID_2015 = "148513";
	public static final String CREW_EVENT_ID_2015  = "153652";
	
	public static final String RIDER_EVENT_ID_2014 = "124639";
	public static final String CREW_EVENT_ID_2014  = "125616";
	
	public static final String RIDER_EVENT_ID_2013 = "96529";
	public static final String CREW_EVENT_ID_2013  = "97260";
	
	public static final String RIDER_EVENT_ID_2012 = "71589";
	public static final String CREW_EVENT_ID_2012  = "75996";
	
	public static final String RIDER_EVENT_ID_2011 = "52935";
	public static final String CREW_EVENT_ID_2011  = "57207";
	
	public static final String RIDER_EVENT_ID_2010 = "39408";
	public static final String CREW_EVENT_ID_2010  = "39410";
	
	public static final String RIDER_EVENT_ID_2009 = "22590";
	public static final String CREW_EVENT_ID_2009  = "22819";
	
	public static final String RIDER_EVENT_ID_2008 = "15307";
	public static final String CREW_EVENT_ID_2008  = "0";
	
	public static final String RIDER_EVENT_ID_2007 = "9755";
	public static final String CREW_EVENT_ID_2007  = "0";
	
	public static final String RIDER_EVENT_ID_2006 = "5250";
	public static final String CREW_EVENT_ID_2006  = "0";
	
	public static final String RIDER_EVENT_ID_2005 = "2846";
	public static final String CREW_EVENT_ID_2005  = "2848";
	
	public static final String DEFAULT_RIDER_EVENT_ID = RIDER_EVENT_ID_2017;
	public static final String DEFAULT_RIDER_ONE_DAY_EVENT_ID = RIDER_EVENT_ID_ONE_DAY_2017;
	public static final String DEFAULT_CREW_EVENT_ID = CREW_EVENT_ID_2017;
	public static final String DEFAULT_LOVEBOWL_EVENT_ID = LOVEBOWL_EVENT_ID_2017;
	public static final String LASTYEAR_RIDER_EVENT_ID = RIDER_EVENT_ID_2016;
	

	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

	static Integer getFamilyCount(Query query) {
		Integer familyCount = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(query);
		for (Entity result : pq.asIterable()) {
			String lastName = (String) result.getProperty("lastName");
			if (lastName.contains("and ")) {
				familyCount++;
			}
		}

		return familyCount;
	}

	static Integer getParticipantCount(String eventId) {
		Integer participantCount = (Integer) memcache.get(eventId + "-participantCount");
		if (participantCount == null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query query = new Query(eventId).setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
			participantCount = datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults()) + getFamilyCount(query);
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
		// 2017
		case Bikerally_util.RIDER_EVENT_ID_2017:
		case Bikerally_util.CREW_EVENT_ID_2017:
			byDate.add(Calendar.YEAR, 0);
			break;

		// 2016
		case Bikerally_util.RIDER_EVENT_ID_2016:
		case Bikerally_util.CREW_EVENT_ID_2016:
			byDate.add(Calendar.YEAR, -1);
			break;

		// 2015
		case Bikerally_util.RIDER_EVENT_ID_2015:
		case Bikerally_util.CREW_EVENT_ID_2015:
			byDate.add(Calendar.YEAR, -2);
			break;

		// 2014
		case Bikerally_util.RIDER_EVENT_ID_2014:
		case Bikerally_util.CREW_EVENT_ID_2014:
			byDate.add(Calendar.YEAR, -3);
			break;

		// 2013
		case Bikerally_util.RIDER_EVENT_ID_2013:
		case Bikerally_util.CREW_EVENT_ID_2013:
			byDate.add(Calendar.YEAR, -4);
			break;

		// 2012
		case Bikerally_util.RIDER_EVENT_ID_2012:
		case Bikerally_util.CREW_EVENT_ID_2012:
			byDate.add(Calendar.YEAR, -5);
			break;

		// 2011
		case Bikerally_util.RIDER_EVENT_ID_2011:
		case Bikerally_util.CREW_EVENT_ID_2011:
			byDate.add(Calendar.YEAR, -6);
			break;

		// 2010
		case Bikerally_util.RIDER_EVENT_ID_2010:
		case Bikerally_util.CREW_EVENT_ID_2010:
			byDate.add(Calendar.YEAR, -7);
			break;

		// 2005
		case Bikerally_util.RIDER_EVENT_ID_2005:
			byDate.add(Calendar.YEAR, -12);
			break;

		default:
			break;
		}

		String byDateString = artezDateFormat.format(byDate.getTime());

		String countRegistrationByDate = (String) memcache.get(eventId + "-" + byDateString);
		if (countRegistrationByDate == null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query query = new Query(eventId).setFilter(new FilterPredicate("registrationDate", FilterOperator.LESS_THAN, byDateString));
			countRegistrationByDate = Integer.toString(datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults()) + getFamilyCount(query));
			memcache.put(eventId + "-" + byDateString, countRegistrationByDate);
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

	static String getJsonRoute(String routeId) throws IOException, ParserConfigurationException, SAXException {
		String jsonRoute = (String) memcache.get(routeId + "-jsonRoute");
		if (jsonRoute == null) {
			Route route = new Route(routeId);
			jsonRoute = route.toJson();
			memcache.put(routeId + "-jsonRoute", jsonRoute);
		}
		return jsonRoute;
	}

	static void deleteJsonRoute(String routeId) {
		memcache.delete(routeId + "-jsonRoute");
	}

	static String getJsonParticipants(String riderEventId, String crewEventId) {
		String jsonParticipants = (String) memcache.get(riderEventId + "-" + crewEventId + "-jsonParticipants");
		if (jsonParticipants == null) {
			Participants participants = new Participants(riderEventId, crewEventId);
			jsonParticipants = participants.toJson();
			memcache.put(riderEventId + "-" + crewEventId + "-jsonParticipants", jsonParticipants);
		}
		return jsonParticipants;
	}

	static void deleteJsonParticipants(String riderEventId, String crewEventId) {
		memcache.delete(riderEventId + "-" + crewEventId + "-jsonParticipants");
	}

	static String getJsonTeams(String eventId) {
		String jsonTeams = (String) memcache.get(eventId + "-jsonTeams");
		if (jsonTeams == null) {
			Teams teams = new Teams(eventId);
			jsonTeams = teams.toJson();
			memcache.put(eventId + "-jsonTeams", jsonTeams);
		}
		return jsonTeams;
	}

	static void deleteJsonTeams(String eventId) {
		memcache.delete(eventId + "-jsonTeams");
	}

	static String getJsonLevels(String riderEventId) {
		String jsonLevels = (String) memcache.get(riderEventId + "-jsonLevels");
		if (jsonLevels == null) {
			Levels levels = new Levels(riderEventId);
			jsonLevels = levels.toJson();
			memcache.put(riderEventId + "-jsonLevels", jsonLevels);
		}
		return jsonLevels;
	}

	static void deleteJsonLevels(String riderEventId, String crewEventId) {
		memcache.delete(riderEventId + "-jsonLevels");
	}

	static String getJsonDashboard(String riderEventId, String crewEventId) {
		String jsonDashboard = (String) memcache.get(riderEventId + "-" + crewEventId + "-jsonDashboard");
		if (jsonDashboard == null) {
			Dashboard dashboard = new Dashboard(riderEventId, crewEventId);
			jsonDashboard = dashboard.toJson();
			memcache.put(riderEventId + "-" + crewEventId + "-jsonDashboard", jsonDashboard);
		}
		return jsonDashboard;
	}

	static void deleteJsonDashboard(String riderEventId, String crewEventId) {
		memcache.delete(riderEventId + "-" + crewEventId + "-jsonDashboard");
	}

	static void deleteEventTotalsMemcache(String eventId) {
		memcache.delete(eventId + "-participantCount");
		memcache.delete(eventId + "-eventParticipantCount");
		memcache.delete(eventId + "-jsonTeams");
		memcache.delete(eventId + "-jsonLevels");
		memcache.delete("recacheTime");
	}
}
