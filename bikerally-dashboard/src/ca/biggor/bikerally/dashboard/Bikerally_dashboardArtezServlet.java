package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class Bikerally_dashboardArtezServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("BikeRally - dashboardArtez");
		resp.getWriter().println();

		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		}
		String crewEventId = req.getParameter("crewEventId");
		if (crewEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_CREW_EVENT_ID;
		}

		try {
			resp.getWriter().println("Artez: " + Bikerally_util.getEventParticipantCount(riderEventId) + " riders, " + Bikerally_util.getEventParticipantCount(crewEventId) + " crew");
			resp.getWriter().println(Bikerally_util.getEventTotalCollected(riderEventId) + Bikerally_util.getEventTotalCollected(crewEventId));
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

}
