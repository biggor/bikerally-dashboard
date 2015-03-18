package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class Bikerally_jsonRouteDetailsServlet extends HttpServlet {

	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String routeId = req.getParameter("routeid");
		String recache = req.getParameter("recache");
		if (routeId != null && !routeId.trim().isEmpty()) {
			if (recache != null && recache.equals("true")) {
				Bikerally_util.deleteJsonRoute(routeId);
			}
			String jsonRoute = Bikerally_util.getJsonRoute(routeId);

			resp.setContentType("text/plain");
			PrintWriter out = resp.getWriter();
			out.print(jsonRoute);
		}
	}
}
