google.load('visualization', '1', {
	'packages' : [ 'corechart' ]
});

google.setOnLoadCallback(drawDashboard);

function drawDashboard() {

	$.getJSON('bikerally_routedetails?routeid=' + getParameterByName('routeid'), function(jsonData) {

		data = new google.visualization.DataTable();
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
		ticks = [];
		var minElevation = 9999999999999;
		var maxElevation = 0;
		var breakCount = 0;
		var minutes = $('#currentValue').html();
		var showRS = getParameterByName('showrs');
		var showStretch = getParameterByName('showstretch');
		var predictedSlowSpeed = $('#slowSpeed').html();
		var predictedFastSpeed = $('#fastSpeed').val();
		var predictedSlowDistance = minutes * predictedSlowSpeed / 60;
		var predictedFastDistance = minutes * predictedFastSpeed / 60;
		var inPredictedStretch = false;
		var actualSlowDistance = predictedSlowDistance + 20;
		var actualFastDistance = predictedFastDistance + 20;
		var inActualStretch = false;
		var certainty = true;
		var j = 0;
		var newj = true;

		for (var i = 0; i < jsonData.track.length; i++) {
			var distance = parseFloat('' + (jsonData.track[i].distance ? ' ' + jsonData.track[i].distance + ' ' : 0));
			var elevation = Math.round(parseFloat('' + (jsonData.track[i].elevation ? ' ' + jsonData.track[i].elevation + ' ' : 0)));
			var elevation2 = null;
			var elevation3 = null;
			var coursePointIndex = parseInt(jsonData.track[i].coursePointIndex != 0 ? jsonData.track[i].coursePointIndex - 1: 0);

			var annotation = null;
			var annotationText = null;

			if (distance >= predictedSlowDistance && distance <= predictedFastDistance) {
				elevation2 = 200;
				if (!inPredictedStretch) {
					ticks.push(Math.round(distance));
				}
				inPredictedStretch = true;
			} else {
				if (inPredictedStretch) {
					ticks.push(Math.round(distance));
					elevation2 = 200;
					inPredictedStretch = false;
				}
			}

			if (distance >= actualSlowDistance && distance <= actualFastDistance) {
				elevation3 = 250;
				if (!inActualStretch) {
					ticks.push(Math.round(distance));
				}
				inActualStretch = true;
			} else {
				if (inActualStretch) {
					ticks.push(Math.round(distance));
					elevation3 = 250;
					inActualStretch = false;
				}
			}

			if (i == 0) {
				annotation = 'S';
				annotationText = jsonData.cuesheet[coursePointIndex].notes;
				ticks.push(Math.round(distance));
			}
			if (jsonData.cuesheet[coursePointIndex].notes == 'End of route') {
				annotation = 'E';
				annotationText = jsonData.cuesheet[coursePointIndex].notes;
				ticks.push(Math.round(distance));
			}
			if (jsonData.cuesheet[coursePointIndex].notes.split(' ')[0] == 'Lunch') {
				annotation = 'L';
				annotationText = jsonData.cuesheet[coursePointIndex].notes;
				ticks.push(Math.round(distance));
			}
			if (jsonData.cuesheet[coursePointIndex].notes.split(' ')[0] == 'Break') {
				annotation = '' + ++breakCount;
				annotationText = jsonData.cuesheet[coursePointIndex].notes;
				ticks.push(Math.round(distance));
			}
			if (jsonData.cuesheet[coursePointIndex].rs == '1' && (showRS.toLowerCase() == 'yes' || showRS.toLowerCase() == 'true')) {
				annotation = '*';
				annotationText = jsonData.cuesheet[coursePointIndex].notes;
				ticks.push(Math.round(distance));
			}

//			if (coursePointIndex != 0) {
				data.addRow([ distance, elevation, annotation, annotationText, certainty, elevation2, elevation3 ]);
//			}
			if (elevation < minElevation) {
				minElevation = elevation;
			}
			if (elevation > maxElevation) {
				maxElevation = elevation;
			}

		}

		options = {
			annotations : {
				textStyle : {
					bold : true,
				}
			},
			hAxis : {
				ticks : ticks
			},
			vAxis : {
				viewWindow : {
					max : ((maxElevation * 1 + 99) / 100) * 100,
					min : minElevation - 5
				}
			},
			chartArea : {
				width : '90%'
			},
			legend : 'none',
			curveType : 'function',
			height : 200
		};

		chart = new google.visualization.AreaChart(document.getElementById('chart_div'));

		chart.draw(data, options);

	});
}

function drawSpread() {
	
	chart.draw(data, options);
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