package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class _addParticipantServlet extends HttpServlet {

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

		Key registrandKey = KeyFactory.createKey(eventId, registrantId);
		System.out.println(registrandKey.toString());
		Entity participant;
		try {
			participant = datastore.get(registrandKey);
		} catch (EntityNotFoundException e) {
			System.out.println("Participant not found, adding a new one");
			participant = new Entity(registrandKey);
			participant.setProperty("id", registrantId);
			// custom properties
			participant.setProperty("riderNumber", "");
			participant.setProperty("status", "active");
			datastore.put(participant);
		}
		
		
//		Query query = new Query(eventId).setFilter(new FilterPredicate("id", FilterOperator.EQUAL, registrantId));
//		PreparedQuery pq = datastore.prepare(query);
//		Entity participant = pq.asSingleEntity();
//		if (participant == null) {
//			System.out.println("Participant not found, adding a new one");
//			participant = new Entity(eventId);
//			participant.setProperty("id", registrantId);
//			// custom properties
//			participant.setProperty("riderNumber", "");
//			participant.setProperty("status", "active");
//			datastore.put(participant);
//		}

		resp.getWriter().println(String.format("%2s", "+") + " " + participant.getProperty("id") + ": " + participant.getProperty("firstName") + " " + participant.getProperty("lastName"));
	}
}
