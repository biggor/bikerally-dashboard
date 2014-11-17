package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


@SuppressWarnings("serial")
public class Bikerally_registrationsByMonth extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String eventId = req.getParameter("eventId");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(eventId).setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
		PreparedQuery participants = datastore.prepare(query);
		
		int[] month = {0,0,0,0,0,0,0,0,0,0,0,0};
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		for (Entity participant : participants.asIterable()) {           	
				String registrationDate = (String) participant.getProperty("registrationDate");
			try {
				Date date = format.parse(registrationDate);
				switch (date.getMonth()) {
				case 0:
					month[5]++;
					break;

				case 1:
					month[6]++;
					break;

				case 2:
					month[7]++;
					break;

				case 3:
					month[8]++;
					break;

				case 4:
					month[9]++;
					break;

				case 5:
					month[10]++;
					break;

				case 6:
					month[11]++;
					break;

				case 7:
					month[0]++;
					break;

				case 8:
					month[1]++;
					break;

				case 9:
					month[2]++;
					break;

				case 10:
					month[3]++;
					break;

				case 11:
					month[4]++;
					break;

				default:
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int total = 0;
		for (int i = 0; i < month.length; i++) {
			total = total + month[i];
			resp.getWriter().println("<p>" + total + "</p>");
		}
	}
}
