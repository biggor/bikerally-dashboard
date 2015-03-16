package ca.biggor.bikerally.dashboard;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.google.gson.Gson;

public class Route {
	private String id;
	private String routeName;
	private String routeDescription;
	private float distance;
	private GregorianCalendar date;
	private String startTime;
	private String endTime;
	private ArrayList<CoursePoint> cuesheet;
	
	public Route(String id) {
		getRouteDetails(id);
	}

	private void getRouteDetails(String id) {
		this.id = id;
		this.routeName = "No route name";
		this.routeDescription = "No route description";
		this.distance = (float)0.0;
		this.date = new GregorianCalendar(2015, 07, 26);
		this.startTime = "00:00";
		this.endTime = "00:00";
		this.cuesheet = new ArrayList<>();
		this.cuesheet.add(new CoursePoint("No type", "No notes", "No description", 0, 0, 0, 0));
	}
	
	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}

}

class CoursePoint {
	private String type;
	private String notes;
	private String description;
	private float distance;
	private float elevation;
	private float latitude;
	private float longitude;
	
	public CoursePoint(String type, String notes, String description, float distance, float elevation, float latitude, float longitude) {
		super();
		this.type = type;
		this.notes = notes;
		this.description = description;
		this.distance = distance;
		this.elevation = elevation;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}