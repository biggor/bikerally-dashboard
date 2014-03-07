package ca.biggor.bikerally.dashboard;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

@SuppressWarnings("serial")
public class Bikerally_checkinServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String firstName = null;
		String lastName = null;
		String fullName = null;
		String participantLink = null;
		String registrationDate = null;
		String totalRaised = null;
		String artezId = req.getParameter("id");
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b;
		try {
			b = f.newDocumentBuilder();
			Document doc = b.parse("http://my.e2rm.com/webgetservice/get.asmx/getRegistrant?registrantID=" + artezId + "&Source=&uniqueID=");
			doc.getDocumentElement().normalize();
			firstName = doc.getElementsByTagName("firstName").item(0).getFirstChild().getNodeValue();
			lastName = doc.getElementsByTagName("lastName").item(0).getChildNodes().item(0).getNodeValue();
			participantLink = doc.getElementsByTagName("participantLink").item(0).getChildNodes().item(0).getNodeValue();
			registrationDate = doc.getElementsByTagName("RegistrationDate").item(0).getChildNodes().item(0).getNodeValue();
			totalRaised = doc.getElementsByTagName("totalRaised").item(0).getChildNodes().item(0).getNodeValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.setContentType("text/plain");
		if (firstName != null) {
			fullName = firstName + " " + lastName;
		} else {
			fullName = "unknown user";
		}
		
		resp.getWriter().println(fullName);
		resp.getWriter().println("47.000, 39.123");
		resp.getWriter().println(timestamp);
		resp.getWriter().println("Thank you for checking-in!");
		resp.getWriter().println();
		resp.getWriter().println("Debug:");
		resp.getWriter().println("Artez Id: " + artezId);
		resp.getWriter().println("Timestamp: " + timestamp);
		resp.getWriter().println("Geolocation: ");
		resp.getWriter().println("firstName: " + firstName);
		resp.getWriter().println("lastName: " + lastName);
		resp.getWriter().println("participantLink: " + participantLink);
		resp.getWriter().println("registrationDate: " + registrationDate);
		resp.getWriter().println("totalRaised: " + totalRaised);

		// URL url = new
		// URL("http://my.e2rm.com/webgetservice/get.asmx/getRegistrant?registrantID="
		// + artezId + "&Source=&uniqueID=");
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(url.openStream()));
		// String line;
		// while ((line = reader.readLine()) != null) {
		// resp.getWriter().println(line);
		// }

	}

}
