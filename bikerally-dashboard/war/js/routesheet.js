jqxhr = $.getJSON("/bikerally_routedetails?routeid=" + getParameterByName('routeid'), function() {
	jsonData = jqxhr.responseJSON;
});

jqxhr.complete(function() {
	document.getElementById('route_title').innerHTML = jsonData.routeTitle;
	document.getElementById('route_distance').innerHTML = jsonData.distance;
	document.getElementById('route_metric').innerHTML = jsonData.metric;

	var breaksize = 2000;
	var d = 0;
	for (var i = 0; i < jsonData.cuesheet.length; i++) {
		var td_index = "<td class='col-xs-1 text-right'><button class='btn btn-xs btn-info'>" + (i + 1) + "</button></td>";
		var distance = Math.round(jsonData.cuesheet[i].distance * 10) / 10;
		var btn_color = " btn-default";
		if ((distance - d) <= 0.1) {
			btn_color = " btn-warning";
		}
		var td_distance = "<td class='col-xs-1 text-right'><button class='btn btn-xs" + btn_color + "'>" + distance + "</button></td>";
		var td_direction = "<td class='col-xs-1'>" + typeToTurn(jsonData.cuesheet[i].type) + "</td>";
		var td_note = "<td class='col-xs-9'>" + shortNotes(jsonData.cuesheet[i].notes) + "</td>";
		if ($("#cuesheet1").height() < breaksize) {
			$("#tb1").append("<tr>" + td_index + td_distance + td_direction + td_note + "</tr>");
		} else if ($("#cuesheet2").height() < breaksize) {
			$("#tb2").append("<tr>" + td_index + td_distance + td_direction + td_note + "</tr>");
		} else if ($("#cuesheet3").height() < breaksize) {
			$("#tb3").append("<tr>" + td_index + td_distance + td_direction + td_note + "</tr>");
		} else if ($("#cuesheet4").height() < breaksize) {
			$("#tb4").append("<tr>" + td_index + td_distance + td_direction + td_note + "</tr>");
		} else if ($("#cuesheet5").height() < breaksize) {
			$("#tb5").append("<tr>" + td_index + td_distance + td_direction + td_note + "</tr>");
		} else {
			$("#tb6").append("<tr>" + td_index + td_distance + td_direction + td_note + "</tr>");
		}
		d = distance;
	}

});

function typeToTurn(type) {
	var turn = "";
	switch (type) {
	case 'Left':
		turn = '<span class="glyphicon glyphicon-arrow-left text-danger"></span>';
		break;
	case 'Right':
		turn = '<span class="glyphicon glyphicon-arrow-right text-primary">';
		break;
	default:
		break;
	}
	return turn;
}

function shortNotes(notes) {
	return notes.replace("Turn left onto ", "").replace("Turn left", "turn left").replace("Turn right onto ", "").replace("Turn right", "turn right").replace("Continue onto", "becomes").replace("Continue straight", "continue straight").replace("Take the", "take the").replace("Merge", "merge").replace("Slight", "slight");
}

String.prototype.capitalize = function() {
	return this.charAt(0).toUpperCase() + this.slice(1);
}

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}