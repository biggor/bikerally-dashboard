package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class Bikerally_registrationsByMonth extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.getWriter().println("{ month: '2014-07'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2014-08'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2014-09'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2014-10'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2014-11'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2014-12'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.JANUARY, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-01'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-02'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.MARCH, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-03'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.APRIL, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-04'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.MAY, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-05'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.JUNE, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-06'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.JULY, 1, 0, 0, 0)) + " }");
		resp.getWriter().println("{ month: '2015-07'" + ", 2010: " + Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2011: " + Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2012: " + Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2013: " + Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2014: " + Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + ", 2015: " + Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)) + " }");
		
	}
	
}
