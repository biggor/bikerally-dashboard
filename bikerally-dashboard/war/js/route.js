function getJsonData(routeid) {
	$.getJSON('/bikerally_routedetails?routeid=' + routeid), function(jsonData) {
		return jsonData;
	};
}

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}