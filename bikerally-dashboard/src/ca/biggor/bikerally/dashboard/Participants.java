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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import com.google.gson.Gson;

public class Participants {

	private String riderEventId;
	private String crewEventId;
	private HashMap<String, String> teams;
	private ArrayList<Participant> participants;

	public Participants(String riderEventId, String crewEventId) {
		this.riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId : "148513";
		this.crewEventId = (crewEventId != null && !crewEventId.trim().isEmpty()) ? crewEventId : "153652";
		this.participants = new ArrayList<>();
		this.teams = new HashMap<String, String>();
		
		String jsonRiderTeams = Bikerally_util.getJsonTeams(this.riderEventId);
		addTeams(jsonRiderTeams);
		String jsonCrewTeams = Bikerally_util.getJsonTeams(this.crewEventId);
		addTeams(jsonCrewTeams);
		
		PreparedQuery riders = getParticipants(this.riderEventId);
		addParticipants(riders);
		PreparedQuery crew = getParticipants(this.crewEventId);
		addParticipants(crew);
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

	private void addParticipants(PreparedQuery ps) {
		for (Entity p : ps.asIterable()) {
			String id = (String) p.getProperty("id");
			String firstName = (String) p.getProperty("firstName");
			String lastName = (String) p.getProperty("lastName");
			String riderNumber = (String) p.getProperty("riderNumber");
			String teamId = (String) p.getProperty("teamId");
			Boolean teamLead = (Boolean) p.getProperty("teamLead");
			Boolean trainingRideLeader = (Boolean) p.getProperty("trainingRideLeader");

			this.participants.add(new Participant(id, firstName, lastName, riderNumber, this.teams.get(teamId), teamLead, trainingRideLeader));
		}
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
