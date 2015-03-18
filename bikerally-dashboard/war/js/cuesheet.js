google.load("visualization", "1", {packages:["table"]});
google.setOnLoadCallback(drawTable);

function drawTable() {
	$.getJSON('http://localhost:8888/bikerally_routedetails?routeid=' + getParameterByName('routeid'), function(jsonData) {
		document.getElementById('route_name').innerHTML = jsonData.routeName;
		document.getElementById('route_distance').innerHTML = jsonData.distance;
		document.getElementById('route_metric').innerHTML = jsonData.metric;
		document.getElementById('emergency_phone').innerHTML = getParameterByName('phone');

		var data = new google.visualization.DataTable();
		data.addColumn('number', 'Map');
		data.addColumn('number', 'Go');
		data.addColumn('string', jsonData.metric.capitalize());
		data.addColumn('string', 'Notes');
		for (var i=0; i<jsonData.cuesheet.length; i++) {
			data.addRow([jsonData.cuesheet[i].index, jsonData.cuesheet[i].distance, typeToTurn(jsonData.cuesheet[i].type), shortNotes(jsonData.cuesheet[i].notes)]);
		}
		
		var table = new google.visualization.Table(document.getElementById('cuesheet_table'));
		var distanceFormatter = new google.visualization.NumberFormat({fractionDigits: 1});
		distanceFormatter.format(data, 1);
		
		table.draw(data, {showRowNumber: false});
	});
}

function typeToTurn(type) {
	var turn = "";
	switch (type) {
	case 'Left':
		turn = 'L';
		break;
	case 'Right':
		turn = 'R';
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
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}