package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class Bikerally_dashboardServletByTotalRaised extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - Dashboard");

		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		}
		String crewEventId = req.getParameter("crewEventId");
		if (crewEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_CREW_EVENT_ID;
		}

		PreparedQuery riders = getParticipants(riderEventId);
		PreparedQuery crew = getParticipants(crewEventId);

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
		Query q = new Query(eventId).addSort("id", SortDirection.ASCENDING).setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
		return datastore.prepare(q);
	}

	private void printParticipants(PreparedQuery participants, HttpServletResponse resp) throws IOException {
		resp.getWriter().println("i,rised,name");
		int index = 0;
		for (Entity participant : participants.asIterable()) {
			String id = (String) participant.getProperty("id");
			String firstName = (String) participant.getProperty("firstName");
			String lastName = (String) participant.getProperty("lastName");
			String riderNumber = (String) participant.getProperty("riderNumber");
			String totalRaised = (String) participant.getProperty("totalRaised");
			resp.getWriter().println(String.format("%3s", index += 1) + "," + String.format("%10s", totalRaised) + "," + firstName + " " + lastName);
		}
	}

}
