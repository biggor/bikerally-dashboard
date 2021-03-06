package ca.biggor.bikerally.dashboard;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

public class Levels {

	private String riderEventId;
	private int activeRiders = 0;
	private int estimatedActiveRiders = 0;
	private int level2500 = 0;
	private int level1500 = 0;
	private int level1000 = 0;
	private int level500 = 0;
	private int level1 = 0;
	private int level0 = 0;

	public Levels(String riderEventId) {
		this.riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId : Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(this.riderEventId).addSort("id").setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
		PreparedQuery riders = datastore.prepare(q);
		for (Entity rider : riders.asIterable()) {
			float totalRaised = Float.parseFloat((String) rider.getProperty("totalRaised"));
			if (totalRaised >= 2500) {
				level2500++;
			}
			if (totalRaised >= 1500 && totalRaised < 2500) {
				level1500++;
			}
			if (totalRaised >= 1000 && totalRaised < 1500) {
				level1000++;
			}
			if (totalRaised >= 500 && totalRaised < 1000) {
				level500++;
			}
			if (totalRaised > 0 && totalRaised < 500) {
				level1++;
			}
			if (totalRaised == 0) {
				level0++;
			}
			String lastName = (String) rider.getProperty("lastName");
			if (lastName.contains("and ")) {
				if (totalRaised >= 5000) {
					level2500++;
				}
				if (totalRaised >= 4000 && totalRaised < 5000) {
					level1500++;
				}
				if (totalRaised >= 3500 && totalRaised < 4000) {
					level1000++;
				}
				if (totalRaised >= 3000 && totalRaised < 3500) {
					level500++;
				}
				if (totalRaised > 2500 && totalRaised < 3000) {
					level1++;
				}
				if (totalRaised <= 2500) {
					level0++;
				}
			}
		}
		
		this.activeRiders = Bikerally_util.getParticipantCount(this.riderEventId);
		this.estimatedActiveRiders = 0; // TODO - some smart routine to determine the estimated number of riders that will actual ride
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
