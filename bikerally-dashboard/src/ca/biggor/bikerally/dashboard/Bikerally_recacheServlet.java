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
		out.println("recached: 148513, 153652");
		Bikerally_util.deleteEventTotalsMemcache("153652");
	}
}
