package ca.biggor.bikerally.dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;

public class Route {
	private String routeId;
	private String routeName;
	private String routeDescription;
	private float distance;
	private String metric;
	private GregorianCalendar date;
	private String startTime;
	private String endTime;
	private ArrayList<CoursePoint> cuesheet;

	public Route(String routeId) throws IOException {
		this.routeId = "";
		this.routeName = "";
		this.routeDescription = "";
		this.distance = (float) 0.0;
		this.date = new GregorianCalendar();
		this.metric = "";
		this.startTime = "00:00";
		this.endTime = "00:00";

		if (routeId != null) {
			this.routeId = routeId;
			getRouteDetails(routeId);
			getRouteCoursePoints(routeId, metric);
		}
	}

	private void getRouteDetails(String routeId) throws IOException {
		Document doc = Jsoup.connect("http://ridewithgps.com/routes/" + routeId).get();
		this.routeName = doc.select("h2.asset_name").text();
		Element descriptionField = doc.select("div.raw_asset_description").first();
		if (descriptionField != null) {
			this.routeDescription = descriptionField.text().replaceAll("\\{.*", "");
			String jsonText = descriptionField.text().replace(this.routeDescription, "");
			JsonObject jsonObject = new Gson().fromJson(jsonText, JsonObject.class);
			if (jsonObject != null) {
				if (jsonObject.get("startTime") != null) {
					this.startTime = jsonObject.get("startTime").getAsString();
				}
				if (jsonObject.get("endTime") != null) {
					this.endTime = jsonObject.get("endTime").getAsString();
				}
				if (jsonObject.get("rideDate") != null) {
					String[] rideDate = jsonObject.get("rideDate").getAsString().split("-");
					String[] startTimeHourMinute = this.startTime.split(":");
					this.date = new GregorianCalendar(Integer.parseInt(rideDate[0]), Integer.parseInt(rideDate[1]), Integer.parseInt(rideDate[2]), Integer.parseInt(startTimeHourMinute[0]), Integer.parseInt(startTimeHourMinute[1]));
				}
			}
			this.routeDescription = descriptionField.text().replaceAll("\n", " ");
		}
		Element distanceField = doc.select("tr.hide_unless_distance td.data").first();
		if (distanceField != null) {
			this.distance = new Float(distanceField.text().replaceAll("[^0-9.]", ""));
			this.metric = distanceField.text().replaceAll("[^A-Za-z]", "");
		}
	}

	private void getRouteCoursePoints(String routeId, String metric) throws IOException {

		this.cuesheet = new ArrayList<>();
		URL csvUrl = new URL("http://ridewithgps.com/routes/" + routeId + ".csv");
		BufferedReader in = new BufferedReader(new InputStreamReader(csvUrl.openStream()));
		CSVReader reader = new CSVReader(in);
		String[] nextLine;
		int index = 0;
		while ((nextLine = reader.readNext()) != null) {
			if (!nextLine[0].equals("Type")) {
				this.cuesheet.add(new CoursePoint(index, metric, nextLine[0], nextLine[1], nextLine[4], nextLine[2], nextLine[3], "0", "0"));
				index++;
			}
		}
		reader.close();
		// this.cuesheet.add(new CoursePoint("Start", "Start of route", "", 0,
		// 0, 0, 0));
		// this.cuesheet.add(new CoursePoint("End", "End of route", "", 0, 0, 0,
		// 0));
	}

	public String toJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}

}

class CoursePoint {
	private int index;
	private String type;
	private String notes;
	private String description;
	private float distance;
	private float elevation;
	private float latitude;
	private float longitude;
	private String rs;
	private String rm;
	private String note;
	

	public CoursePoint(int index, String metric, String type, String notes, String description, String distance, String elevation, String latitude, String longitude) {
		this.index = 0;
		this.type = "";
		this.notes = "";
		this.description = "";
		this.distance = 0;
		this.elevation = 0;
		this.latitude = 0;
		this.longitude = 0;
		this.rs = "";
		this.rm = "";
		this.note = "";

		this.index = index;
		if (type != null && !type.trim().isEmpty()) {
			this.type = type;
		}
		if (notes != null && !notes.trim().isEmpty()) {
			this.notes = notes;
		}
		if (description != null && !description.trim().isEmpty()) {
			this.description = description.replaceAll("[\n\r]", "").replaceAll("\\{.*", "");
			String jsonText = description.replace(this.description, "");
			JsonObject jsonObject = new Gson().fromJson(jsonText, JsonObject.class);
			if (jsonObject != null) {
				JsonObject rs = jsonObject.getAsJsonObject("rs");
				if (rs != null) {
					if (rs.get("hotspot") != null) {
						this.rs = rs.get("hotspot").getAsString();
					}
					if (rs.get("rm") != null) {
						this.rm = rs.get("rm").getAsString();
					}
					if (rs.get("note") != null) {
						this.note = rs.get("note").getAsString();
					}
				}
			}
		}
		if (distance != null && !distance.trim().isEmpty()) {
			this.distance = Float.parseFloat(distance);
			if (metric.equals("km")) {
				this.distance = this.distance * Float.parseFloat("1.60934");
			}
		}
		if (elevation != null && !elevation.trim().isEmpty()) {
			this.elevation = Float.parseFloat(elevation);
		}
		if (latitude != null && !latitude.trim().isEmpty()) {
			this.latitude = Float.parseFloat(latitude);
		}
		if (longitude != null && !longitude.trim().isEmpty()) {
			this.longitude = Float.parseFloat(longitude);
		}
	}
}
