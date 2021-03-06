package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class Bikerally_jsdataDollarChartServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String riderEventId = req.getParameter("riderEventId");
		if (riderEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		}
		String crewEventId = req.getParameter("crewEventId");
		if (crewEventId == null) {
			riderEventId = Bikerally_util.DEFAULT_CREW_EVENT_ID;
		}

		String recacheTime = "";
		Integer ridersTotalCollected = 0;
		Integer crewTotalCollected = 0;
		Integer ridersRegistered = 0;
		Integer crewRegistered = 0;
		try {
			recacheTime = Bikerally_util.getRecacheTime();
			ridersTotalCollected = Bikerally_util.getEventTotalCollected(riderEventId);
			crewTotalCollected = Bikerally_util.getEventTotalCollected(crewEventId);
			ridersRegistered = Bikerally_util.getEventParticipantCount(riderEventId);
			crewRegistered = Bikerally_util.getEventParticipantCount(crewEventId);
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		Integer totalRaised = ridersTotalCollected + crewTotalCollected;

		resp.getWriter().println("document.getElementById('recacheTime').innerHTML='" + recacheTime + "';");
		resp.getWriter().println("document.getElementById('totalRaised').innerHTML='$" + totalRaised + "';");
		resp.getWriter().println("document.getElementById('ridersRaised').innerHTML='$" + ridersTotalCollected + "';");
		resp.getWriter().println("document.getElementById('crewRaised').innerHTML='$" + crewTotalCollected + "';");
		resp.getWriter().println("document.getElementById('ridersRegistered').innerHTML='" + ridersRegistered + "';");
		resp.getWriter().println("document.getElementById('crewRegistered').innerHTML='" + crewRegistered + "';");
		resp.getWriter().println("");

		resp.getWriter().println("Morris.Line({");
		resp.getWriter().println("element: 'dollar-chart',");
		resp.getWriter().println("data: [");
		resp.getWriter().println("{ month: '2014-07', 2010:   0, 2011:   4, 2012:   0, 2013:  11, 2014:   1, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2014-08', 2010:   1, 2011:  26, 2012:  76, 2013:  63, 2014:  44, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2014-09', 2010:  69, 2011:  43, 2012:  84, 2013: 112, 2014:  66, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2014-10', 2010:  77, 2011:  65, 2012:  89, 2013: 123, 2014:  73, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2014-11', 2010:  84, 2011:  83, 2012: 107, 2013: 152, 2014:  91, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2014-12', 2010: 111, 2011: 108, 2012: 131, 2013: 192, 2014: 116, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-01', 2010: 190, 2011: 150, 2012: 237, 2013: 307, 2014: 157, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-02', 2010: 234, 2011: 254, 2012: 259, 2013: 332, 2014: 199, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-03', 2010: 274, 2011: 298, 2012: 283, 2013: 363, 2014: 222, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-04', 2010: 298, 2011: 331, 2012: 302, 2013: 381, 2014: 237, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-05', 2010: 305, 2011: 346, 2012: 322, 2013: 385, 2014: 245, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-06', 2010: 309, 2011: 352, 2012: 327, 2013: 389, 2014: 251, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-07', 2010: 314, 2011: 355, 2012: 334, 2013: 390, 2014: 254, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + " },");
		resp.getWriter().println("{ month: '2015-08', 2010: 300, 2011: 298, 2012: 283, 2013: 302, 2014: 184, 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.SEPTEMBER, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("],");
		resp.getWriter().println("xkey: 'month',");
		resp.getWriter().println("ykeys: ['2010', '2011', '2012', '2013', '2014', '2015'],");
		resp.getWriter().println("labels: ['2010', '2011', '2012', '2013', '2014', '2015'],");
		resp.getWriter().println("dateFormat: function (x) { var IndexToMonth = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]; return IndexToMonth[new Date(x).getMonth()]; },");
		resp.getWriter().println("xLabelFormat: function (x) { var IndexToMonth = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]; return IndexToMonth[x.getMonth()]; },");
		// resp.getWriter().println("lineColors: ['#0b62a4','#7a92a3','#4da74d','#afd8f8','#edc240','#cb4b4b','#9440ed'],");
		resp.getWriter().println("lineColors: ['#afd8f8','#0b62a4','#7a92a3','#872d91','#e21836','#4da74d','#9440ed'],");
		resp.getWriter().println("pointSize: 2,");
		resp.getWriter().println("hideHover: 'auto',");
		resp.getWriter().println("resize: true,");
		resp.getWriter().println("behaveLikeLine: true");
		resp.getWriter().println("});");
	}


}
