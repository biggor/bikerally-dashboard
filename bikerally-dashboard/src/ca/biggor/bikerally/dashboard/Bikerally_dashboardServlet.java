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

		PreparedQuery riders = getParticipants("124639");
		PreparedQuery crew = getParticipants("125616");

		resp.getWriter().println();
		resp.getWriter().println("Riders: " + riders.countEntities(FetchOptions.Builder.withDefaults()) + ", Crew: " + crew.countEntities(FetchOptions.Builder.withDefaults()));
		resp.getWriter().println();
		resp.getWriter().println("Rider list");
		printParticipants(riders, resp);
		resp.getWriter().println();
		resp.getWriter().println("Crew list");
		printParticipants(crew, resp);
	}
	
	private PreparedQuery getParticipants(String eventId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(eventId).addSort("id");
		return datastore.prepare(q);		
	}

	private void printParticipants(PreparedQuery participants, HttpServletResponse resp) throws IOException {
		int index = 0;
		for (Entity participant : participants.asIterable()) {
			String id = (String) participant.getProperty("id");
			String firstName = (String) participant.getProperty("firstName");
			String lastName = (String) participant.getProperty("lastName");
			resp.getWriter().println(String.format("%3s", index += 1) + ": " + id + " " + firstName + " " + lastName);
		}
	}
}
