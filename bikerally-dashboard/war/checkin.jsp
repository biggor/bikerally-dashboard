<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true"></script>
<script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// all jQuery code goes here
	
		if(navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				$("#geo").append(position.coords.latitude + ", " + position.coords.longitude);
			});
		} else {
			// Browser doesn't support Geolocation
			$("#geo").append("browser doesn't support Geolocation");
		}
	});
</script>
<title>Check-in</title>
</head>
<body>

<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="org.w3c.dom.Document" %>

<%
String firstName = null;
String lastName = null;
String fullName = null;
String participantLink = null;
String registrationDate = null;
String totalRaised = null;
Date date = new Date();
Timestamp timestamp = new Timestamp(date.getTime());

DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
DocumentBuilder b;
try {
	b = f.newDocumentBuilder();
	Document doc = b.parse("http://my.e2rm.com/webgetservice/get.asmx/getRegistrant?registrantID=" + request.getParameter("id") + "&Source=&uniqueID=");
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

if (firstName != null) {
	fullName = firstName + " " + lastName;
} else {
	fullName = "unknown user";
}
 %>
	<center>
		<div>
			<img style="width: 20%;" alt="" src="check.png">
		</div>
		<h1><%= fullName %></h1>
		<h3 id="geo" />
		<h3><%= timestamp %></h3>
	</center>

</body>
</html>