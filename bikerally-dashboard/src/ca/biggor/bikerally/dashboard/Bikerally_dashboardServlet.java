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
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - Dashboard");

		resp.getWriter().print("Riders: ");
		printParticipant("Rider", resp);

		resp.getWriter().print("Crew: ");
		printParticipant("Crew", resp);

	}

	private void printParticipant(String participantType, HttpServletResponse resp) throws IOException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(participantType);
		PreparedQuery pq = datastore.prepare(q);
		resp.getWriter().println(pq.countEntities(FetchOptions.Builder.withDefaults()));

		int index = 0;
		for (Entity rider : pq.asIterable()) {
			String firstName = (String) rider.getProperty("firstName");
			String lastName = (String) rider.getProperty("lastName");
			resp.getWriter().println(index++ + ": " + firstName + " " + lastName);
		}
	}
}
