package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Bikerally_jsonDashboard extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		}
		String crewEventId = req.getParameter("crewEventId");
		if (crewEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_CREW_EVENT_ID;
		}
	
		String jsonDashboard = Bikerally_util.getJsonDashboard(riderEventId, crewEventId);

		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		out.print(jsonDashboard);
	}
}
