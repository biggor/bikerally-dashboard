package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Bikerally_jsonRouteDetailsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String routeId = req.getParameter("routeid");

		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		Route route = new Route(routeId);
		out.print(route.toJson());
	}
}
