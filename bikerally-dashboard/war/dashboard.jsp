<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bike Rally 2014 - Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>
<body>
	<%@ page import="java.io.IOException"%>
	<%@ page import="javax.servlet.http.HttpServlet"%>
	<%@ page import="javax.servlet.http.HttpServletRequest"%>
	<%@ page import="javax.servlet.http.HttpServletResponse"%>
	<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
	<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
	<%@ page import="com.google.appengine.api.datastore.Entity"%>
	<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
	<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
	<%@ page import="com.google.appengine.api.datastore.Query"%>
	<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
	<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate"%>

	<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
	<%@ page import="org.xml.sax.SAXException"%>
	<%@ page import="javax.xml.parsers.DocumentBuilder"%>
	<%@ page import="javax.xml.parsers.DocumentBuilderFactory"%>
	<%@ page import="org.w3c.dom.Document"%>
	<%@ page import="java.net.URL"%>

	<%!private PreparedQuery getParticipants(String eventId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(eventId).addSort("firstName").addSort("lastName").setFilter(new FilterPredicate("status", FilterOperator.EQUAL, "active"));
		return datastore.prepare(q);
	}%>
	<%!private Integer getEventTotalCollected(String eventId) throws ParserConfigurationException, SAXException, IOException {
		URL url = new URL("http://my.e2rm.com/webgetservice/get.asmx/getParticipantScoreBoard?eventID=" + eventId + "&languageCode=&sortBy=&listItemCount=&externalQuestionID=&externalAnswerID=&Source=");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(url.openStream());
		Integer eventOnlineTotalCollected = Float.valueOf(doc.getElementsByTagName("eventOnlineTotalCollected").item(0).getTextContent()).intValue();
		Integer eventOfflineTotalCollected = Float.valueOf(doc.getElementsByTagName("eventOfflineTotalCollected").item(0).getTextContent()).intValue();
		return eventOnlineTotalCollected + eventOfflineTotalCollected;
	}%>

	<%
	String riderEventId = request.getParameter("riderEventId");
	if (riderEventId == null) {
// 		riderEventId = "124639";
		riderEventId = "148513";
	}
	String crewEventId = request.getParameter("crewEventId");
	if (crewEventId == null) {
// 		crewEventId = "125616";
		crewEventId = "153652";
	}

		PreparedQuery riders = getParticipants(riderEventId);
		PreparedQuery crew = getParticipants(crewEventId);
	%>
	<!-- Home page -->
	<div id="home" data-role="page" style="text-align: center">
		<div data-role="header" data-position="fixed">
			<h3>Bike Rally dashboard</h3>
		</div>
		<div role="main" class="ui-content">
			<div class="ui-grid-a" style="height: 125px">
				<div class="ui-block-a" style="height: 100%">
					<h1 is="gk-text"><%=riders.countEntities(FetchOptions.Builder.withDefaults())%></h1>
					<h5 is="gk-text">riders</h5>
				</div>
				<div class="ui-block-b" style="height: 100%">
					<h1 is="gk-text"><%=crew.countEntities(FetchOptions.Builder.withDefaults())%></h1>
					<h5 is="gk-text">crew</h5>
				</div>
			</div>
			<h1 is="gk-text">$<%=getEventTotalCollected(riderEventId) + getEventTotalCollected(crewEventId)%></h1>
		</div>
		<div data-position="fixed" data-role="footer">
			<div data-role="navbar">
				<ul>
					<li><a href="#riders" data-icon="user">riders</a></li>
					<li><a href="#crew" data-icon="user">crew</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- /page -->

	<!-- Riders page -->
	<div id="riders" data-role="page">
		<div data-role="header" data-position="fixed">
			<h3>Riders</h3>
		</div>
		<div role="main" class="ui-content">
			<ul data-role="listview" data-inset="true" data-autodividers="false" data-filter="true" class="">
				<%
					for (Entity participant : riders.asIterable()) {
						String id = (String) participant.getProperty("id");
						String firstName = (String) participant.getProperty("firstName");
						String lastName = (String) participant.getProperty("lastName");
						String riderNumber = (String) participant.getProperty("riderNumber");
				%>
				<li><a href="#"><%=firstName + " " + lastName%><span class="ui-li-count"><%=riderNumber%></span> </a></li>
				<%
					}
				%>
			</ul>
		</div>
		<div data-position="fixed" data-role="footer">
			<div data-role="navbar">
				<ul>
					<li><a href="#home" data-icon="carat-l">home</a></li>
					<li><a href="#crew" data-icon="user">crew</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- /page -->

	<!-- Crew page -->
	<div id="crew" data-role="page">
		<div data-role="header" data-position="fixed">
			<h3>Crew</h3>
		</div>
		<div role="main" class="ui-content">
			<ul data-role="listview" data-inset="true" data-autodividers="false" data-filter="true" class="">
				<%
					for (Entity participant : crew.asIterable()) {
						String id = (String) participant.getProperty("id");
						String firstName = (String) participant.getProperty("firstName");
						String lastName = (String) participant.getProperty("lastName");
						String riderNumber = (String) participant.getProperty("riderNumber");
				%>
				<li><a href="#"><%=firstName + " " + lastName%><span class="ui-li-count"><%=riderNumber%></span> </a></li>
				<%
					}
				%>
			</ul>
		</div>
		<div data-position="fixed" data-role="footer">
			<div data-role="navbar">
				<ul>
					<li><a href="#home" data-icon="carat-l">home</a></li>
					<li><a href="#riders" data-icon="user">riders</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- /page -->
</body>
</html>