$(document).ready(function() {
	$.getJSON('http://localhost:8888/bikerally_routedetails?routeid=' + getParameterByName('routeid'), function(jsonRoute) {
		document.write('Hello there...');
		document.write(jsonRoute.routeName);
	});
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}