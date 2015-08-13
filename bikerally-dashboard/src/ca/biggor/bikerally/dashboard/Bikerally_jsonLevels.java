package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Bikerally_jsonLevels extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		}
	
		String jsonLevels = Bikerally_util.getJsonLevels(riderEventId);

		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		out.print(jsonLevels);
	}
}
