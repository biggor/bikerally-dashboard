google.load("visualization", "1", {
	packages : [ "corechart" ]
});

// google.setOnLoadCallback(drawChart);

function drawChart() {
	console.log("drawChart");
	console.log("" + jsonData.track.length + " " + jsonData.cuesheet.length);
	
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

	var ticks = [];
	var minElevation = 9999999999999;
	var maxElevation = 0;
	var breakCount = 0;
	var showRS = getParameterByName('showrs');
	var showStretch = getParameterByName('showstretch');

	var minutes = 2 * 60;
	var slowSpeed = 13;
	var fastSpeed = 30;
	var slowDistance = minutes * slowSpeed / 60;
	var fastDistance = minutes * fastSpeed / 60;
	var inStretch = false;
	var certainty = true;

	for (var i = 0; i < jsonData.track.length; i++) {
		var distance = parseFloat('' + (jsonData.track[i].distance ? ' ' + jsonData.track[i].distance + ' ' : 0));
		var elevation = Math.round(parseFloat('' + (jsonData.track[i].elevation ? ' ' + jsonData.track[i].elevation + ' ' : 0)));
		var elevation2 = null;
		var coursePointIndex = parseInt('' + (jsonData.track[i].coursePointIndex ? ' ' + jsonData.track[i].coursePointIndex + ' ' : 0));

		var annotation = null;
		var annotationText = null;
		if (showStretch.toLowerCase() == 'yes' || showStretch.toLowerCase() == 'true') {
			if (distance >= slowDistance && distance <= fastDistance) {
				elevation2 = elevation;
				if (!inStretch) {
					ticks.push(Math.round(distance));
				}
				inStretch = true;
			} else {
				if (inStretch) {
					ticks.push(Math.round(distance));
					elevation2 = elevation;
					inStretch = false;
				}
			}
			// console.log("" + distance + " " + slowDistance + " " +
			// fastDistance + " " + inStretch + " " + elevation2);
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

		data.addRow([ distance, elevation, annotation, annotationText, certainty, elevation2 ]);
		if (elevation < minElevation) {
			minElevation = elevation;
		}
		if (elevation > maxElevation) {
			maxElevation = elevation;
		}
		// console.log('' + minElevation);
	}
	// data.addRow([ 130, 0, null, null ]);

	options = {
		annotations : {
			// boxStyle: {
			// stroke: 'blue', // Color of the box outline.
			// strokeWidth: 1, // Thickness of the box outline.
			// rx: 5, // x-radius of the corner curvature.
			// ry: 5, // y-radius of the corner curvature.
			// gradient: { // Attributes for linear gradient fill.
			// color1: '#fbf6a7', // Start color for gradient.
			// color2: '#33b679', // Finish color for gradient.
			// x1: '0%', y1: '0%', // Where on the boundary to start and end
			// the
			// x2: '100%', y2: '100%', // color1/color2 gradient, relative
			// to the
			// // upper left corner of the boundary.
			// useObjectBoundingBoxUnits: true // If true, the boundary for
			// x1, y1,
			// // x2, and y2 is the box. If false,
			// // it's the entire chart.
			// }
			// },
			textStyle : {
				// fontName : 'Times-Roman',
				// fontSize : 18,
				bold : true,
			// italic : true,
			// color : 'white', // The color of the text.
			// auraColor : '#d799ae', // The color of the text
			// outline.
			// opacity : 0.8
			// The transparency of the text.
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
		legend : 'none',
		curveType : 'function',
		height : 200
	};

}

$('document').ready(function() {
	console.log("ready");

	jqxhr = $.getJSON("bikerally_routedetails?routeid=7182594", function() {
		console.log("success");
	});

	// Perform other work here ...

	// Set another completion function for the request above
	jqxhr.complete(function() {
		console.log("complete");
		jsonData = $.parseJSON(jqxhr.responseText);
		drawChart();
		console.log($("#chart_div").width());
		console.log($("#chart_div").width());
		chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
		chart.draw(data, options);
	});

});

$(function() {

	$('#slider').change(function() {
		if (typeof data != "undefined") {
			data.setCell(100, 1, this.value);
			chart.draw(data, options);
		}
	});

	$('#slider').mousemove(function() {
		if (typeof data != "undefined") {
			data.setCell(100, 1, this.value);
			chart.draw(data, options);
		}
	});

	// Trigger the event on load, so
	// the value field is populated:

//	$('#slider').change();

});
