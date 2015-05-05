package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Bikerally_jsonParticipants extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String riderEventId = req.getParameter("riderEventId");
		String crewEventId = req.getParameter("crewEventId");
		riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId: "";
		crewEventId = (crewEventId != null && !crewEventId.trim().isEmpty()) ? crewEventId: "";
	
		String jsonParticipants = Bikerally_util.getJsonParticipants(riderEventId, crewEventId);

		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		out.print(jsonParticipants);
	}

}
