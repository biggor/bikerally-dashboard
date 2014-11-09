package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.csvreader.CsvReader;

@SuppressWarnings("serial")
public class Bikerally_cuesheetServletEli extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

		String routeId = req.getParameter("routeid");
		String phone = req.getParameter("phone");
		if (phone == null) {
			phone = "647-993-HELP (4357)";
		}

		if (routeId != null) {
			int i = 0;

			try {
				URL url = new URL("http://ridewithgps.com/routes/" + routeId + ".csv");
				CsvReader cuesheet = new CsvReader(new InputStreamReader(url.openStream()));
				cuesheet.readHeaders();
				while (cuesheet.readRecord()) {
					String type = cuesheet.get("Type");
					String notes = cuesheet.get("Notes");
					Double distance = Double.parseDouble(cuesheet.get("Distance (miles) From Start")) * 1.60934;
					String elevation = cuesheet.get("Elevation (ft)");
					String description = cuesheet.get("Description");
					resp.getWriter().println("" + i + "| " + String.format("%.1f", distance) + " | " + type + " | " + notes);
					i++;
				}
				cuesheet.close();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
