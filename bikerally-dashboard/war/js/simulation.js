google.load('visualization', '1', {
	'packages' : [ 'corechart' ]
});

google.setOnLoadCallback(drawDashboard);

function drawDashboard() {

	$.getJSON('bikerally_routedetails?routeid=' + getParameterByName('routeid'), function(jsonData) {

		var data = new google.visualization.DataTable();
		data.addColumn('number', 'Km');
		data.addColumn('number', 'Elevation');
		data.addColumn({
			type : 'string',
			role : 'annotation'
		});
		data.addColumn({
			type : 'string',
			role : 'annotationText'
		});
		data.addColumn({
			type : 'boolean',
			role : 'certainty'
		});
		data.addColumn('number', 'Elevation');
		data.addColumn('number', 'Elevation');

		$('#totalDistance').html(Math.round(parseFloat('' + (jsonData.distance ? ' ' + jsonData.distance + ' ' : 0))));
		var totalDistance = $('#totalDistance').html();
		var ticks = [];
		var maxElevation = 0;
		var breakCount = 0;
		var minutes = $('#currentValue').html();
		var predictedSlowSpeed = $('#slowSpeed').html();
		var predictedFastSpeed = $('#fastSpeed').val();
		var predictedSlowDistance = minutes * predictedSlowSpeed / 60;
		var predictedFastDistance = minutes * predictedFastSpeed / 60;
		var inPredictedStretch = false;
		var actualSlowDistance = predictedSlowDistance;
		var actualFastDistance = predictedFastDistance;
		var inActualStretch = false;
		var certainty = true;
		var j = 0;
		var newj = true;

		for (var i = 1; i <= totalDistance; i++) {
			var distance = i;
			var d = (Math.ceil (parseFloat('' + (jsonData.cuesheet[j].distance ? ' ' + jsonData.cuesheet[j].distance + ' ' : 0))));
			if (distance > d) {
				j++;
				annotation = '.';
				newj = true;
			}
			var elevation = Math.round(parseFloat('' + (jsonData.cuesheet[j].elevation ? ' ' + jsonData.cuesheet[j].elevation + ' ' : 0)));
			var elevation2 = null;
			var elevation3 = null;
			var annotation = null;
			var annotationText = null;

			if (distance >= predictedSlowDistance && distance <= predictedFastDistance) {
				elevation2 = 1500;
				if (!inPredictedStretch) {
					ticks.push(distance);
				}
				inPredictedStretch = true;
			} else {
				if (inPredictedStretch) {
					ticks.push(distance);
					elevation2 = 1500;
					inPredictedStretch = false;
				}
			}

			if (distance >= actualSlowDistance && distance <= actualFastDistance) {
				elevation3 = 2000;
				if (!inActualStretch) {
					ticks.push(distance);
				}
				inActualStretch = true;
			} else {
				if (inActualStretch) {
					ticks.push(distance);
					elevation3 = 2000;
					inActualStretch = false;
				}
			}


			if (i == 1) {
				annotation = 'S';
				annotationText = jsonData.cuesheet[j - 1].notes;
				ticks.push(distance);
			}
			if (i == totalDistance) {
				annotation = 'E';
				annotationText = jsonData.cuesheet[j].notes;
				ticks.push(distance);
			}
			if (jsonData.cuesheet[j].notes.split(' ')[0] == 'Lunch' && newj) {
				annotation = 'L';
				annotationText = jsonData.cuesheet[j].notes;
				ticks.push(distance);
				newj = false;
			}
			if (jsonData.cuesheet[j].notes.replace('\n', ' ').split(' ')[0] == 'Break' && newj) {
				annotation = '' + ++breakCount;
				annotationText = jsonData.cuesheet[j].notes;
				ticks.push(distance);
				newj = false;
			}
			if (jsonData.cuesheet[j].rs == '1' && newj) {
				annotation = '*';
				annotationText = jsonData.cuesheet[j].notes;
				ticks.push(distance);
				newj = false;
			}

			data.addRow([ distance, elevation, annotation, annotationText, certainty, elevation2, elevation3 ]);

			if (elevation > maxElevation) {
				maxElevation = elevation;
			}
			
		}

		var options = {
			annotations : {
				textStyle : {
					bold : true,
				}
			},
			hAxis : {
				ticks : ticks
			},
			vAxis : {
				minValue : 0,
				maxValue : 1500
			},
			legend : 'none'
		};

		var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));

		chart.draw(data, options);

	});
}

$(function() {

	var currentValue = $('#currentValue');
	var slowSpeedValue = $('#slowSpeed');
	var totalDistanceValue = $('#totalDistance');

	$('#slider').change(function() {
		currentValue.html(this.value);
		slowSpeedValue.html(totalDistanceValue.html() / 8);
		drawDashboard();
	});

	$('#slider').mousemove(function() {
		currentValue.html(this.value);
		slowSpeedValue.html(totalDistanceValue.html() / 8);
		drawDashboard();
	});

	$('#fastSpeed').change(function() {
		drawDashboard();
	});

	// Trigger the event on load, so
	// the value field is populated:

	$('#slider').change();

});