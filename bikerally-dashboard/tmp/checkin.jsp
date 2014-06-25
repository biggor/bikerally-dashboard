<!DOCTYPE html>
<html>
<head>
<title>checkin</title>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true"></script>
<script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $( window ).load(function() {
		if(navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				document.form1.action="/bikerally_checkin";
				document.getElementById("lat").value = position.coords.latitude;
				document.getElementById("lng").value = position.coords.longitude;
				document.form1.submit();
			});
		}
    });

</script>
</head>
<body>
	<form name="form1" method="POST">
		<input type="hidden" name="id" value="<%= request.getParameter("id") %>">
		<input type="hidden" name="latitude" id="lat">
		<input type="hidden" name="longitude" id="lng">
	</form>
</body>
</html>