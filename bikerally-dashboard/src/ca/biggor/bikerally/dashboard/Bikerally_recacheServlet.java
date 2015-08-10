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
		
		Bikerally_util.deleteEventTotalsMemcache("148513");
		Bikerally_util.deleteEventTotalsMemcache("153652");
		Bikerally_util.deleteJsonParticipants("148513", "153652");
		Bikerally_util.deleteJsonDashboard("148513", "153652");
		out.println("recached: 148513, 153652");

		Bikerally_util.deleteEventTotalsMemcache("177536");
		Bikerally_util.deleteEventTotalsMemcache("179193");
		Bikerally_util.deleteEventTotalsMemcache("0");
		Bikerally_util.deleteJsonParticipants("177536", "0");
		Bikerally_util.deleteJsonParticipants("179193", "0");
		Bikerally_util.deleteJsonDashboard("177536", "0");
		Bikerally_util.deleteJsonDashboard("179193", "0");
		out.println("recached: 177536, 179193, 0");

	}
}
