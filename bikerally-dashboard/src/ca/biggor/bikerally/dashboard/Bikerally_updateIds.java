package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class Bikerally_updateIds extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//
		updateIds(Bikerally_util.RIDER_EVENT_ID_2005);
		updateIds(Bikerally_util.CREW_EVENT_ID_2005);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2010);
		updateIds(Bikerally_util.CREW_EVENT_ID_2010);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2011);
		updateIds(Bikerally_util.CREW_EVENT_ID_2011);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2012);
		updateIds(Bikerally_util.CREW_EVENT_ID_2012);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2013);
		updateIds(Bikerally_util.CREW_EVENT_ID_2013);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2014);
		updateIds(Bikerally_util.CREW_EVENT_ID_2014);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2015);
		updateIds(Bikerally_util.CREW_EVENT_ID_2015);
		updateIds(Bikerally_util.RIDER_EVENT_ID_2016);
		updateIds(Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2016);
		updateIds(Bikerally_util.CREW_EVENT_ID_2016);
	}

	private void updateIds(String eventId) {
		System.out.println("eventId: " + eventId);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Query query = new Query(eventId).setFilter(new
		// FilterPredicate("status", FilterOperator.EQUAL, "active"));
		Query query = new Query(eventId);
		PreparedQuery pq = datastore.prepare(query);
		for (Entity result : pq.asIterable()) {
			String id = (String) result.getProperty("id");
			switch (id.trim().length()) {
			case 1:
				id = "      " + id.trim();
				break;

			case 2:
				id = "     " + id.trim();
				break;

			case 3:
				id = "    " + id.trim();
				break;

			case 4:
				id = "   " + id.trim();
				break;

			case 5:
				id = "  " + id.trim();
				break;

			case 6:
				id = " " + id.trim();
				break;

			default:
				break;
				
			}
			result.setProperty("id", id);
			datastore.put(result);

			System.out.println(id);
		}
	}

}
