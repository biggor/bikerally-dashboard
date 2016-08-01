package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class Dashboard {
	private String riderEventId;
	private String riderEventId1d;
	private String crewEventId;
	private String recacheTime;
	private Integer ridersTotalCollected;
	private Integer ridersTotalCollected1d;
	private Integer crewTotalCollected;
	private Integer activeRiders;
	private Integer activeRiders1d;
	private Integer activeCrew;
	private Integer registeredRiders;
	private Integer registeredCrew;
	private Integer totalRaised;
	private String byDate;
	private String riders2010;
	private String crew2010;
	private String riders2011;
	private String crew2011;
	private String riders2012;
	private String crew2012;
	private String riders2013;
	private String crew2013;
	private String riders2014;
	private String crew2014;
	private String riders2015;
	private String crew2015;
	private String riders2016;
	private String riders20161d;
	private String crew2016;
	private String riders2017;
	private String riders20171d;
	private String crew2017;
	private String regAug0;
	private String regSep0;
	private String regOct0;
	private String regNov0;
	private String regDec0;
	private String regJan;
	private String regFeb;
	private String regMar;
	private String regApr;
	private String regMay;
	private String regJun;
	private String regJul;
	private String regAug;
	private String regSep;

	public Dashboard(String riderEventId, String crewEventId) {

		Calendar today = new GregorianCalendar();
		SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMM-dd");

		this.riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId : Bikerally_util.DEFAULT_RIDER_EVENT_ID;
		this.crewEventId = (crewEventId != null && !crewEventId.trim().isEmpty()) ? crewEventId : Bikerally_util.DEFAULT_CREW_EVENT_ID;
		if (this.riderEventId.equals(Bikerally_util.DEFAULT_RIDER_EVENT_ID) || this.riderEventId.equals(Bikerally_util.DEFAULT_CREW_EVENT_ID)) {
			this.riderEventId = Bikerally_util.DEFAULT_RIDER_EVENT_ID;
			this.riderEventId1d = Bikerally_util.DEFAULT_CREW_EVENT_ID;
		} else {
			this.riderEventId1d = "0";
		}

		this.recacheTime = Bikerally_util.getRecacheTime();
		this.activeRiders = Bikerally_util.getParticipantCount(this.riderEventId);
		this.activeRiders1d = Bikerally_util.getParticipantCount(this.riderEventId1d);
		this.activeCrew = Bikerally_util.getParticipantCount(this.crewEventId);
		try {
			this.ridersTotalCollected = Bikerally_util.getEventTotalCollected(this.riderEventId);
			this.ridersTotalCollected1d = Bikerally_util.getEventTotalCollected(this.riderEventId1d);
			this.crewTotalCollected = Bikerally_util.getEventTotalCollected(this.crewEventId);
			this.registeredRiders = Bikerally_util.getEventParticipantCount(this.riderEventId);
			this.registeredCrew = Bikerally_util.getEventParticipantCount(this.crewEventId);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.totalRaised = this.ridersTotalCollected + this.ridersTotalCollected1d + this.crewTotalCollected;

		this.byDate = monthDayFormat.format(new GregorianCalendar().getTime());

		this.riders2010 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2010, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2010 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2010, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2011 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2011, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2011 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2011, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2012 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2012, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2012 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2012, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2013 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2013, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2013 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2013, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2014 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2014, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2014 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2014, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2015 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2015, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2015 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2015, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2016 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2016, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders20161d = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2016, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2016 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2016, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2017 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_2017, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders20171d = Bikerally_util.getCountRegistrationByDate(Bikerally_util.RIDER_EVENT_ID_ONE_DAY_2017, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2017 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.CREW_EVENT_ID_2017, new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));

		this.regAug0 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2015, Calendar.AUGUST, 1, 0, 0, 0));
		this.regSep0 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2015, Calendar.SEPTEMBER, 1, 0, 0, 0));
		this.regOct0 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2015, Calendar.OCTOBER, 1, 0, 0, 0));
		this.regNov0 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2015, Calendar.NOVEMBER, 1, 0, 0, 0));
		this.regDec0 = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2015, Calendar.DECEMBER, 1, 0, 0, 0));
		this.regJan = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.JANUARY, 1, 0, 0, 0));
		this.regFeb = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.FEBRUARY, 1, 0, 0, 0));
		this.regMar = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.MARCH, 1, 0, 0, 0));
		this.regApr = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.APRIL, 1, 0, 0, 0));
		this.regMay = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.MAY, 1, 0, 0, 0));
		this.regJun = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.JUNE, 1, 0, 0, 0));
		this.regJul = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.JULY, 1, 0, 0, 0));
		this.regAug = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.AUGUST, 1, 0, 0, 0));
		this.regSep = Bikerally_util.getCountRegistrationByDate(Bikerally_util.DEFAULT_RIDER_EVENT_ID, new GregorianCalendar(2016, Calendar.SEPTEMBER, 1, 0, 0, 0));
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
