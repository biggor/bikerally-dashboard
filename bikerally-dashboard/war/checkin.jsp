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
<p>Bikerally - checkin</p>
<p>Artez id: ${param.id}</p>

<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>

<%
	Date date = new Date();
	Timestamp timestamp = new Timestamp(date.getTime());
 %>
<p>Timestamp: <%= timestamp %></p>
<p><span>Geolocation: </span><span id="geo"></span></p>

<div>
<%
		URL url = new URL("http://my.e2rm.com//webgetservice/get.asmx/getRegistrant?registrantID=" + request.getParameter("id") + "&Source=&uniqueID=");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		while ((line = reader.readLine()) != null) {
%>
<%= line %>
<%
		}
%>
</div>
</body>
</html>