package ca.biggor.bikerally.dashboard;

import java.util.ArrayList;
import java.util.HashMap;

import org.datanucleus.sco.simple.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

public class Participants {

	private String riderEventId;
	private String crewEventId;
	private ArrayList<Participant> participants;

	public Participants(String riderEventId, String crewEventId) {
		this.riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId : "148513";
		this.crewEventId = (crewEventId != null && !crewEventId.trim().isEmpty()) ? crewEventId : "153652";
		this.participants = new ArrayList<>();
		PreparedQuery riders = getParticipants(this.riderEventId);
		addParticipants(riders);
		PreparedQuery crew = getParticipants(this.crewEventId);
		addParticipants(crew);
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
			System.out.println(id + " " + firstName + " " + lastName + " " + riderNumber + " " + teamId + " " + getTeamName(teamId));

			this.participants.add(new Participant(id, firstName, lastName, riderNumber, getTeamName(teamId)));
		}
	}

	private String getTeamName(String teamId) {
		// TODO - Team objects, memcache, etc...

		HashMap<String, String> teams = new HashMap<String, String>();

		teams.put("582041", "EDELRIDERS");
		teams.put("582044", "Electrolytes");
		teams.put("582038", "Kicking Asphalt");
		teams.put("582040", "Moustache Riders");
		teams.put("582043", "Road Snakes");
		teams.put("582032", "Team A- Andrew Braithwaite/David Morris");
		teams.put("582037", "Team E Matthew Hague/Matt Lamb");
		teams.put("582039", "Team G- Rob Veinott/Wil Zoller");
		teams.put("582042", "Team J- Carey Heeney/Kai Riecken");
		teams.put("582036", "That's all Spokes!");
		teams.put("582034", "The Cressendos");
		teams.put("582033", "VELOCIPOSSE");
		teams.put("580814", "Road Support");
		teams.put("580815", "Wellness Crew");
		teams.put("580817", "Rubbermaid Rustlers");
		teams.put("580816", "Food Crew");
		teams.put("580786", "PWA Staff Team");

		return "" + teams.get(teamId);
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
