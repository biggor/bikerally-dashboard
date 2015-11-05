package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class _deleteParticipantServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String registrantId = req.getParameter("registrantId");
		String eventId = req.getParameter("eventId");
		if (registrantId == null) {
			throw new NullPointerException("_addParticipantServlet: null registrantId");
		}
		if (eventId == null) {
			throw new NullPointerException("_addParticipantServlet: null eventId");
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(eventId).addSort("id");
		// Query q = new Query(eventId);
		PreparedQuery participants = datastore.prepare(q);
//		Entity participant = participants.asList(FetchOptions.Builder.withDefaults()).;
//		datastore.get(key)

//		resp.getWriter().println(String.format("%2s", "-") + " " + participant.getProperty("id") + ": " + participant.getProperty("firstName") + " " + participant.getProperty("lastName"));
	}
}
