package ca.biggor.bikerally.dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;

public class Route {
	private String routeId;
	private String routeName;
	private String routeTitle;
	private String routeDescription;
	private float distance;
	private String metric;
	private GregorianCalendar date;
	private String startTime;
	private String endTime;
	private ArrayList<CoursePoint> cuesheet;
	private ArrayList<Trackpoint> track;

	public Route(String routeId) throws IOException, ParserConfigurationException, SAXException {
		this.routeId = "";
		this.routeName = "";
		this.routeTitle = "";
		this.routeDescription = "";
		this.distance = (float) 0.0;
		this.date = new GregorianCalendar();
		this.metric = "";
		this.startTime = "00:00";
		this.endTime = "00:00";

		if (routeId != null) {
			this.routeId = routeId;
			getRouteDetails(routeId);
			getGpsTrackAndCoursePoints(routeId, metric);
			getCsvCoursePoints(routeId, metric);
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
				if (jsonObject.get("routeTitle") != null) {
					this.routeTitle = jsonObject.get("routeTitle").getAsString();
				}
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

	private void getGpsTrackAndCoursePoints(String routeId, String metric) {
		this.track = new ArrayList<>();
		this.cuesheet = new ArrayList<>();
		ArrayList<Trackpoint> fullTrack = new ArrayList<>();
		try {
			URL tcxUrl = new URL("http://ridewithgps.com/routes/" + routeId + ".tcx");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(tcxUrl.openStream());

			NodeList trackPoints = doc.getElementsByTagName("Trackpoint");
			NodeList coursePoints = doc.getElementsByTagName("CoursePoint");

			// for (int f = 0; f < trackPoints.getLength(); f++) {
			// String distance =
			// trackPoints.item(f).getChildNodes().item(7).getTextContent();
			// String elevation =
			// trackPoints.item(f).getChildNodes().item(5).getTextContent();
			// String latitude =
			// trackPoints.item(f).getChildNodes().item(3).getChildNodes().item(1).getTextContent();
			// String longitude =
			// trackPoints.item(f).getChildNodes().item(3).getChildNodes().item(3).getTextContent();
			// fullTrack.add(new Trackpoint(metric, distance, elevation,
			// latitude, longitude, null));
			// }

			for (int j = 0; j < coursePoints.getLength(); j++) {
				int index = j + 1;
				String type = coursePoints.item(j).getChildNodes().item(7).getTextContent();
				String notes = coursePoints.item(j).getChildNodes().item(9).getTextContent();
				String description = "";
				String distance = "0";
				String elevation = "0";
				String latitude = "0";
				String longitude = "0";
				Position coursePoint = new Position(new BigDecimal(coursePoints.item(j).getChildNodes().item(5).getChildNodes().item(1).getTextContent()), new BigDecimal(coursePoints.item(j).getChildNodes().item(5).getChildNodes().item(3).getTextContent()));
				for (int i = 0; i < trackPoints.getLength(); i++) {
					Position trackPoint = new Position(new BigDecimal(trackPoints.item(i).getChildNodes().item(3).getChildNodes().item(1).getTextContent()), new BigDecimal(trackPoints.item(i).getChildNodes().item(3).getChildNodes().item(3).getTextContent()));
					if (trackPoint.equals(coursePoint)) {
						distance = trackPoints.item(i).getChildNodes().item(7).getTextContent();
						elevation = trackPoints.item(i).getChildNodes().item(5).getTextContent();
						latitude = trackPoints.item(i).getChildNodes().item(3).getChildNodes().item(1).getTextContent();
						longitude = trackPoints.item(i).getChildNodes().item(3).getChildNodes().item(3).getTextContent();
					}
				}
				this.cuesheet.add(new CoursePoint(index, metric, type, notes, description, distance, elevation, latitude, longitude));
			}

			float previousDistance = 0f;
			float averageAltitude = 0f;
			int index = 0;
			for (int t = 0; t < trackPoints.getLength(); t++) {
				String distance = trackPoints.item(t).getChildNodes().item(7).getTextContent();
				String elevation = trackPoints.item(t).getChildNodes().item(5).getTextContent();
				String latitude = trackPoints.item(t).getChildNodes().item(3).getChildNodes().item(1).getTextContent();
				String longitude = trackPoints.item(t).getChildNodes().item(3).getChildNodes().item(3).getTextContent();

				float currentDistance = Float.parseFloat(distance);
				float currentAltitude = Float.parseFloat(elevation);
				averageAltitude = (averageAltitude + currentAltitude) / 2;
				Position trackPoint = new Position(new BigDecimal(trackPoints.item(t).getChildNodes().item(3).getChildNodes().item(1).getTextContent()), new BigDecimal(trackPoints.item(t).getChildNodes().item(3).getChildNodes().item(3).getTextContent()));
				for (int j = 0; j < coursePoints.getLength(); j++) {
					Position coursePoint = new Position(new BigDecimal(coursePoints.item(j).getChildNodes().item(5).getChildNodes().item(1).getTextContent()), new BigDecimal(coursePoints.item(j).getChildNodes().item(5).getChildNodes().item(3).getTextContent()));
					if (trackPoint.equals(coursePoint)) {
						index = j+1;
						this.track.add(new Trackpoint(metric, distance, Float.toString(averageAltitude), latitude, longitude, Integer.toString(index)));
						previousDistance = currentDistance;
						index = 0;
					}
				}
				if (currentDistance - previousDistance > 1000) {
					this.track.add(new Trackpoint(metric, distance, Float.toString(averageAltitude), latitude, longitude, Integer.toString(index)));
					previousDistance = currentDistance;
					index = 0;
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	}

	private void getCsvCoursePoints(String routeId, String metric) throws IOException {

		// this.cuesheet = new ArrayList<>();
		URL csvUrl = new URL("http://ridewithgps.com/routes/" + routeId + ".csv");
		BufferedReader in = new BufferedReader(new InputStreamReader(csvUrl.openStream()));
		CSVReader reader = new CSVReader(in);
		String[] nextLine;
		int index = 0;
		while ((nextLine = reader.readNext()) != null) {
			if (!nextLine[0].equals("Type")) {
				// this.cuesheet.add(new CoursePoint(index, metric, nextLine[0],
				// nextLine[1], nextLine[4], nextLine[2], nextLine[3], "0",
				// "0"));
				this.cuesheet.get(index).setDescription(nextLine[4]);
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
		setDescription(description);
		if (distance != null && !distance.trim().isEmpty()) {
			this.distance = Float.parseFloat(distance) / 1000f;
			if (metric.equals("mi")) {
				this.distance = this.distance / 1.609344f;
			}
		}
		if (elevation != null && !elevation.trim().isEmpty()) {
			this.elevation = Float.parseFloat(elevation);
			if (metric.equals("mi")) {
				this.elevation = this.elevation / 0.3048f;
			}
		}
		if (latitude != null && !latitude.trim().isEmpty()) {
			this.latitude = Float.parseFloat(latitude);
		}
		if (longitude != null && !longitude.trim().isEmpty()) {
			this.longitude = Float.parseFloat(longitude);
		}
	}

	public void setDescription(String description) {
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
	}

}

class Trackpoint {
	private float distance;
	private float elevation;
	private float latitude;
	private float longitude;
	private int coursePointIndex;

	public Trackpoint(String metric, String distance, String elevation, String latitude, String longitude, String coursePointIndex) {
		if (distance != null && !distance.trim().isEmpty()) {
			this.distance = Float.parseFloat(distance) / 1000f;
			if (metric.equals("mi")) {
				this.distance = this.distance / 1.609344f;
			}
		}
		if (elevation != null && !elevation.trim().isEmpty()) {
			this.elevation = Float.parseFloat(elevation);
			if (metric.equals("mi")) {
				this.elevation = this.elevation / 0.3048f;
			}
		}
		if (latitude != null && !latitude.trim().isEmpty()) {
			this.latitude = Float.parseFloat(latitude);
		}
		if (longitude != null && !longitude.trim().isEmpty()) {
			this.longitude = Float.parseFloat(longitude);
		}
		if (coursePointIndex != null && !coursePointIndex.trim().isEmpty()) {
			this.coursePointIndex = Integer.parseInt(coursePointIndex);
		}
	}

	public void setCoursePointIndex(int coursePointIndex) {
		this.coursePointIndex = coursePointIndex;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

}
