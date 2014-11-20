package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class Bikerally_jsdataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String byDate = req.getParameter("byDate");
		if (byDate == null) {
			byDate = "Nov 20";
		}
		
		String totalRaised = "$0";
		String ridersRaised = "$0";
		String crewRaised = "$0";
		String ridersRegistered = "0";
		String crewRegistered = "0";

		String riders2010 = registrationByDate("39408");
		String crew2010 = registrationByDate("39410");
		String riders2011 = registrationByDate("52935");
		String crew2011 = registrationByDate("57207");
		String riders2012 = registrationByDate("71589");
		String crew2012 = registrationByDate("75996");
		String riders2013 = registrationByDate("96529");
		String crew2013 = registrationByDate("97260");
		String riders2014 = registrationByDate("124639");
		String crew2014 = registrationByDate("125616");
		String riders2015 = registrationByDate("148513");
		String crew2015 = registrationByDate("153652");

		resp.getWriter().println("Morris.Area({");
		resp.getWriter().println("element: 'area-chart',");
		resp.getWriter().println("data: [");
		resp.getWriter().println("{ y: '2006', a: 100, b: 90 },");
		resp.getWriter().println("{ y: '2007', a: 75,  b: 65 },");
		resp.getWriter().println("{ y: '2008', a: 50,  b: 40 },");
		resp.getWriter().println("{ y: '2009', a: 75,  b: 65 },");
		resp.getWriter().println("{ y: '2010', a: 50,  b: 40 },");
		resp.getWriter().println("{ y: '2011', a: 75,  b: 65 },");
		resp.getWriter().println("{ y: '2012', a: 100, b: 90 }");
		resp.getWriter().println("],");
		resp.getWriter().println("xkey: 'y',");
		resp.getWriter().println("ykeys: ['a', 'b'],");
		resp.getWriter().println("labels: ['Series A', 'Series B']");
		resp.getWriter().println("});");
		resp.getWriter().println("");

		resp.getWriter().println("document.getElementById('totalRaised').innerHTML='" + totalRaised + "';");
		resp.getWriter().println("document.getElementById('ridersRaised').innerHTML='" + ridersRaised + "';");
		resp.getWriter().println("document.getElementById('crewRaised').innerHTML='" + crewRaised + "';");
		resp.getWriter().println("document.getElementById('ridersRegistered').innerHTML='" + ridersRegistered + "';");
		resp.getWriter().println("document.getElementById('crewRegistered').innerHTML='" + crewRegistered + "';");
		resp.getWriter().println("");

		resp.getWriter().println("document.getElementById('byDate').innerHTML='" + byDate + "';");
		resp.getWriter().println("document.getElementById('riders2010').innerHTML='" + riders2010 + "';");
		resp.getWriter().println("document.getElementById('crew2010').innerHTML='" + crew2010 + "';");
		resp.getWriter().println("document.getElementById('riders2011').innerHTML='" + riders2011 + "';");
		resp.getWriter().println("document.getElementById('crew2011').innerHTML='" + crew2011 + "';");
		resp.getWriter().println("document.getElementById('riders2012').innerHTML='" + riders2012 + "';");
		resp.getWriter().println("document.getElementById('crew2012').innerHTML='" + crew2012 + "';");
		resp.getWriter().println("document.getElementById('riders2013').innerHTML='" + riders2013 + "';");
		resp.getWriter().println("document.getElementById('crew2013').innerHTML='" + crew2013 + "';");
		resp.getWriter().println("document.getElementById('riders2014').innerHTML='" + riders2014 + "';");
		resp.getWriter().println("document.getElementById('crew2014').innerHTML='" + crew2014 + "';");
		resp.getWriter().println("document.getElementById('riders2015').innerHTML='" + riders2015 + "';");
		resp.getWriter().println("document.getElementById('crew2015').innerHTML='" + crew2015 + "';");
		resp.getWriter().println("");

	}

	String registrationByDate(String eventId) {

		int i = 0;
		
		Calendar today = Calendar.getInstance();
		Calendar byDate = today;

		SimpleDateFormat artezDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	
		switch (eventId) {
		// 2015
		case "148513":
		case "153652":
			break;

		// 2014
		case "124639":
		case "125616":
			byDate.set(Calendar.YEAR, today.get(Calendar.YEAR)-1);
			break;

		// 2013
		case "96529":
		case "97260":
			byDate.set(Calendar.YEAR, today.get(Calendar.YEAR)-2);
			break;

		// 2012
		case "71589":
		case "75996":
			byDate.set(Calendar.YEAR, today.get(Calendar.YEAR)-3);
			break;

		// 2011
		case "52935":
		case "57207":
			byDate.set(Calendar.YEAR, today.get(Calendar.YEAR)-4);
			break;

		// 2010
		case "39408":
		case "39410":
			byDate.set(Calendar.YEAR, today.get(Calendar.YEAR)-5);
			break;

		default:
			break;
		}

		System.out.println(byDate.getTime());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(eventId).setFilter(new FilterPredicate("registrationDate", FilterOperator.LESS_THAN_OR_EQUAL, artezDateFormat.format(byDate.getTime())));
		i = datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults());

		return Integer.toString(i);
	}
}
