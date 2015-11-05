package ca.biggor.bikerally.dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class _updateParticipantServlet extends HttpServlet {
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
		
//		resp.getWriter().println(String.format("%2s", "*") + " " + participant.getProperty("id") + ": " + participant.getProperty("firstName") + " " + participant.getProperty("lastName"));
	}

}
