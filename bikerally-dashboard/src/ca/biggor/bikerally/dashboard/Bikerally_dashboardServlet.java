package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class Bikerally_dashboardServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - Dashboard");

		PreparedQuery participants = getParticipants("Rider");
		resp.getWriter().print("Riders: ");
		resp.getWriter().println(participants.countEntities(FetchOptions.Builder.withDefaults()));
		printParticipants(participants, resp);
		
		participants = getParticipants("Crew");
		resp.getWriter().print("Crew: ");
		resp.getWriter().println(participants.countEntities(FetchOptions.Builder.withDefaults()));
		printParticipants(participants, resp);
	}
	
	private PreparedQuery getParticipants(String participantType) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(participantType);
		return datastore.prepare(q);		
	}

	private void printParticipants(PreparedQuery participants, HttpServletResponse resp) throws IOException {
		int index = 0;
		for (Entity rider : participants.asIterable()) {
			String firstName = (String) rider.getProperty("firstName");
			String lastName = (String) rider.getProperty("lastName");
			resp.getWriter().println(index++ + ": " + firstName + " " + lastName);
		}
	}
}
