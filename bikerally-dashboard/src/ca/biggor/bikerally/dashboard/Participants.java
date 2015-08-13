package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.datanucleus.sco.simple.Map;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import com.google.gson.Gson;

public class Participants {

	private String riderEventId;
	private String crewEventId;
	private String riderCount;
	private String crewCount;
	private HashMap<String, String> teams;
	private ArrayList<Participant> participants;

	public Participants(String riderEventId, String crewEventId) {
		this.riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId : Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		this.crewEventId = (crewEventId != null && !crewEventId.trim().isEmpty()) ? crewEventId : Bikerally_util.DEFAULT_CREW_EVENT_ID;
		this.participants = new ArrayList<>();
		this.teams = new HashMap<String, String>();

		String jsonRiderTeams = Bikerally_util.getJsonTeams(this.riderEventId);
		addTeams(jsonRiderTeams);
		String jsonCrewTeams = Bikerally_util.getJsonTeams(this.crewEventId);
		addTeams(jsonCrewTeams);

		this.riderCount = Integer.toString(Bikerally_util.getParticipantCount(this.riderEventId));
		PreparedQuery riders = getParticipants(this.riderEventId);
		addParticipants(riders, true);

		this.crewCount = Integer.toString(Bikerally_util.getParticipantCount(this.crewEventId));
		PreparedQuery crew = getParticipants(this.crewEventId);
		addParticipants(crew, false);
	}

	private void addTeams(String josnTeams) {
		Gson gson = new Gson();
		Teams teams = gson.fromJson(josnTeams, Teams.class);

		for (String key : teams.teams.keySet()) {
			this.teams.put(key, teams.teams.get(key));
		}
	}

	private PreparedQuery getParticipants(String eventId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(eventId).addSort("id").setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
		return datastore.prepare(q);
	}

	private void addParticipants(PreparedQuery ps, boolean isRider) {
		for (Entity p : ps.asIterable()) {
			String id = (String) p.getProperty("id");
			String firstName = (String) p.getProperty("firstName");
			String lastName = (String) p.getProperty("lastName");
			String riderNumber = (String) p.getProperty("riderNumber");
			String teamId = (String) p.getProperty("teamId");
			Boolean steeringCommittee = (Boolean) p.getProperty("steeringCommittee");
			Boolean teamLead = (Boolean) p.getProperty("teamLead");
			Boolean trainingRideCoach = (Boolean) p.getProperty("trainingRideCoach");
			String fundraisingLevel = "";
			if (isRider) {
				float totalRaised = Float.parseFloat((String) p.getProperty("totalRaised"));
				if (totalRaised >= 2500) {
					fundraisingLevel = "l2500 ";
				}
				if (totalRaised >= 1500 && totalRaised < 2500) {
					fundraisingLevel = "l1500 ";
				}
				if (totalRaised >= 1000 && totalRaised < 1500) {
					fundraisingLevel = "l1000 ";
				}
				if (totalRaised >= 500 && totalRaised < 1000) {
					fundraisingLevel = "l500 ";
				}
				if (totalRaised > 0 && totalRaised < 500) {
					fundraisingLevel = "l1 ";
				}
				if (totalRaised == 0) {
					fundraisingLevel = "l0 ";
				}
			}

			this.participants.add(new Participant(id, firstName, lastName, riderNumber, this.teams.get(teamId), steeringCommittee, teamLead, trainingRideCoach, fundraisingLevel));
		}
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
