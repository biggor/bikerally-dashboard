package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class Bikerally_checkinServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");
		String latitude = req.getParameter("latitude");
		String longitude = req.getParameter("longitude");
		String firstName = null;
		String lastName = null;
		String fullName = null;
		String checkinSign = "&#10004;";
		String checkinSigncolor = "green";
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
//	 		riderEventId = "124639";
			riderEventId = "148513";
		}
		String crewEventId = req.getParameter("crewEventId");
		if (crewEventId == null) {
//	 		crewEventId = "125616";
			crewEventId = "153652";
		}

		if (id != null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query q = new Query(riderEventId).addSort("id").setFilter(new FilterPredicate("id", FilterOperator.EQUAL, id));
			Entity participant = datastore.prepare(q).asSingleEntity();
			if (participant != null) {
				firstName = (String) participant.getProperty("firstName");
				lastName = (String) participant.getProperty("lastName");
				fullName = firstName + " " + lastName;
			} else {
				q = new Query(crewEventId).addSort("id").setFilter(new FilterPredicate("id", FilterOperator.EQUAL, id));
				participant = datastore.prepare(q).asSingleEntity();
				if(participant != null) {
					firstName = (String) participant.getProperty("firstName");
					lastName = (String) participant.getProperty("lastName");
					fullName = firstName + " " + lastName;
					checkinSigncolor = "blue";
				} else {
					fullName = "unknown";
					checkinSign = "&#10008;";
					checkinSigncolor = "red";
				}
			}
		}

		resp.getWriter().println("<html><head><title>Check-in</title><style> .center {text-align: center;} .tick { font-size: 20vw;} h1 {font-size: 10vw;} h2 {font-size: 4vw;} h3 {font-size: 3vw;} body {font-family: sans-serif;}</style></head><body>");
		resp.getWriter().println("<div class='center'>");
		resp.getWriter().println("<div class='tick' style=color:" + checkinSigncolor + ";>" + checkinSign + "</div>");
		resp.getWriter().println("<h1>" + fullName + "</h1>");
		resp.getWriter().println("<h3>" + timestamp + "</h3>");
		resp.getWriter().println("<h2>" + latitude + ", " + longitude + "</h2>");
		resp.getWriter().println("</div>");
		resp.getWriter().println("</body></html>");
		
	}

}
