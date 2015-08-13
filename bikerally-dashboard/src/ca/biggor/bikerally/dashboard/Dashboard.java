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
	private Integer familyRiders;
	private Integer familyRiders1d;
	private Integer activeRiders;
	private Integer activeRiders1d;
	private Integer activeCrew;
	private Integer registeredRiders;
	private Integer registeredCrew;
	private Integer totalRaised;
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
	private String riders2016_1day;
	private String crew2016;

	public Dashboard(String riderEventId, String crewEventId) {

		Calendar today = new GregorianCalendar();
		SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMM-dd");

		this.riderEventId = (riderEventId != null && !riderEventId.trim().isEmpty()) ? riderEventId : "148513";
		if (riderEventId.equals("177536") || riderEventId.equals("179193")) {
			this.riderEventId = "177536";
			this.riderEventId1d = "179193";
		} else {
			this.riderEventId1d = "0";
		}
		this.crewEventId = (crewEventId != null && !crewEventId.trim().isEmpty()) ? crewEventId : "153652";
		
		this.recacheTime = Bikerally_util.getRecacheTime();
		this.familyRiders = Bikerally_util.getFamilyCount(this.riderEventId);
		this.familyRiders1d = Bikerally_util.getFamilyCount(this.riderEventId1d);
		this.activeRiders = Bikerally_util.getParticipantCount(this.riderEventId) + this.familyRiders;
		this.activeRiders1d = Bikerally_util.getParticipantCount(this.riderEventId1d) + this.familyRiders1d;
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
		
		this.riders2010 = Bikerally_util.getCountRegistrationByDate("39408", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2010 = Bikerally_util.getCountRegistrationByDate("39410", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2011 = Bikerally_util.getCountRegistrationByDate("52935", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2011 = Bikerally_util.getCountRegistrationByDate("57207", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2012 = Bikerally_util.getCountRegistrationByDate("71589", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2012 = Bikerally_util.getCountRegistrationByDate("75996", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2013 = Bikerally_util.getCountRegistrationByDate("96529", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2013 = Bikerally_util.getCountRegistrationByDate("97260", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2014 = Bikerally_util.getCountRegistrationByDate("124639", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2014 = Bikerally_util.getCountRegistrationByDate("125616", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.riders2015 = Bikerally_util.getCountRegistrationByDate("148513", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		this.crew2015 = Bikerally_util.getCountRegistrationByDate("153652", new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59));

	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
