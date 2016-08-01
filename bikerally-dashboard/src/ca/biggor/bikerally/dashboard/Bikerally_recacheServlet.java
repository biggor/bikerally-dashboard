package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.biggor.bikerally.dashboard.Bikerally_util;

@SuppressWarnings("serial")
public class Bikerally_recacheServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		
		Bikerally_util.deleteEventTotalsMemcache(Bikerally_util.RIDER_EVENT_ID_2016);
		Bikerally_util.deleteEventTotalsMemcache(Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2016);
		Bikerally_util.deleteEventTotalsMemcache(Bikerally_util.CREW_EVENT_ID_2016);
		Bikerally_util.deleteJsonParticipants(Bikerally_util.RIDER_EVENT_ID_2016, Bikerally_util.CREW_EVENT_ID_2016);
		Bikerally_util.deleteJsonParticipants(Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2016, "0");
		Bikerally_util.deleteJsonDashboard(Bikerally_util.RIDER_EVENT_ID_2016, Bikerally_util.CREW_EVENT_ID_2016);
		Bikerally_util.deleteJsonDashboard(Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2016, "0");
		out.println("recached: " + Bikerally_util.RIDER_EVENT_ID_2016 + ", " + Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2016 + ", " + Bikerally_util.CREW_EVENT_ID_2016);

		Bikerally_util.deleteEventTotalsMemcache(Bikerally_util.DEFAULT_RIDER_EVENT_ID);
		Bikerally_util.deleteEventTotalsMemcache(Bikerally_util.DEFAULT_RIDER_ONE_DAY_EVENT_ID);
		Bikerally_util.deleteEventTotalsMemcache(Bikerally_util.DEFAULT_CREW_EVENT_ID);
		Bikerally_util.deleteJsonParticipants(Bikerally_util.DEFAULT_RIDER_EVENT_ID, Bikerally_util.DEFAULT_CREW_EVENT_ID);
		Bikerally_util.deleteJsonParticipants(Bikerally_util.DEFAULT_RIDER_ONE_DAY_EVENT_ID, "0");
		Bikerally_util.deleteJsonDashboard(Bikerally_util.DEFAULT_RIDER_EVENT_ID, Bikerally_util.DEFAULT_CREW_EVENT_ID);
		Bikerally_util.deleteJsonDashboard(Bikerally_util.DEFAULT_RIDER_ONE_DAY_EVENT_ID, "0");
		out.println("recached: " + Bikerally_util.DEFAULT_RIDER_EVENT_ID + " ," + Bikerally_util.DEFAULT_RIDER_ONE_DAY_EVENT_ID + " , " + Bikerally_util.DEFAULT_CREW_EVENT_ID);

	}
}
