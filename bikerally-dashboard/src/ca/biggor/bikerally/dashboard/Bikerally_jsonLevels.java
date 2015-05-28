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
		String riderEventId = req.getParameter("eventId");
		riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId: "148513";
	
		String jsonLevels = Bikerally_util.getJsonLevels(riderEventId);

		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		out.print(jsonLevels);
	}
}
