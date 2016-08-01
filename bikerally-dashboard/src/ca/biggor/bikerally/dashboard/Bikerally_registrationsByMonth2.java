package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class Bikerally_registrationsByMonth2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("got here...");

		resp.getWriter().println("month: '2015-07': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2015-08': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2015, Calendar.SEPTEMBER, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2015-09': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2015, Calendar.OCTOBER, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2015-10': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2015, Calendar.NOVEMBER, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2015-11': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2015, Calendar.DECEMBER, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2015-12': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.JANUARY, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-01': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.FEBRUARY, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-02': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.MARCH, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-03': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.APRIL, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-04': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.MAY, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-05': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.JUNE, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-06': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.JULY, 1, 0, 0, 0)));
		resp.getWriter().println("month: '2016-07': " + Bikerally_util.getCountRegistrationByDate("177536", new GregorianCalendar(2016, Calendar.AUGUST, 1, 0, 0, 0)));
	}
	
}
