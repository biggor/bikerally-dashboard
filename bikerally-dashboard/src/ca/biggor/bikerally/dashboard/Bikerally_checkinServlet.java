package ca.biggor.bikerally.dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Bikerally_checkinServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/plain");
		resp.getWriter().println("Bikerally - checkin");
		resp.getWriter().println("-------------------");
		resp.getWriter().println();
		
		resp.getWriter().print("artez id: ");
		String artezId = req.getParameter("id");
		resp.getWriter().println(artezId);
		
		resp.getWriter().print("timestamp: ");
		Date date = new Date();
		resp.getWriter().println(new Timestamp(date.getTime()));
		resp.getWriter().println();
			
		
		URL url = new URL("http://my.e2rm.com//webgetservice/get.asmx/getRegistrant?registrantID=" + artezId + "&Source=&uniqueID=");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			resp.getWriter().println(line);
		}
	}

}
