<!DOCTYPE html>
<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
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
<style>
.center {
	text-align: center;
}

.tick {
	font-size: 20vw;
}

h1 {
	font-size: 10vw;
}

h2 {
	font-size: 4vw;
}

h3 {
	font-size: 3vw;
}

body {
	font-family: sans-serif;
}
</style></head>
<body>

<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="org.w3c.dom.Document" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate" %>

<%
String firstName = null;
String lastName = null;
String fullName = null;
String participantLink = null;
String registrationDate = null;
String totalRaised = null;
String checkinSign = "&#10004;";
String checkinSigncolor = "green";
Date date = new Date();
Timestamp timestamp = new Timestamp(date.getTime());

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
Query q = new Query("124639").addSort("id").setFilter(new FilterPredicate("id", FilterOperator.EQUAL, request.getParameter("id")));
Entity participant = datastore.prepare(q).asSingleEntity();
if (participant != null) {
	firstName = (String) participant.getProperty("firstName");
	lastName = (String) participant.getProperty("lastName");
	participantLink = (String) participant.getProperty("participantLink");
	fullName = firstName + " " + lastName;
} else {
	q = new Query("125616").addSort("id").setFilter(new FilterPredicate("id", FilterOperator.EQUAL, request.getParameter("id")));
	participant = datastore.prepare(q).asSingleEntity();
	if(participant != null) {
		firstName = (String) participant.getProperty("firstName");
		lastName = (String) participant.getProperty("lastName");
		participantLink = (String) participant.getProperty("participantLink");
		fullName = firstName + " " + lastName;
		checkinSigncolor = "blue";
	} else {
		fullName = "unknown user";
		checkinSign = "&#10008;";
		checkinSigncolor = "red";
	}
}

 %>
	<div class="center">
		<div class="tick" style="color:<%= checkinSigncolor %>;"><%= checkinSign %></div>
		<h1><%= fullName %></h1>
		<h3><%= timestamp %></h3>
		<h2 id="geo"></h3>
	</div>
</body>
</html>