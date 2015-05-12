google.load('visualization', '1', {
	packages : [ 'corechart' ]
});
google.setOnLoadCallback(drawChart);

function drawChart() {

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

		var ticks = [];
		var maxElevation = 0;
		for (var i = 1; i < jsonData.cuesheet.length; i++) {
			var distance = '' + (jsonData.cuesheet[i].distance ? ' ' + jsonData.cuesheet[i].distance + ' ' : 0);
			var elevation = '' + (jsonData.cuesheet[i].elevation ? ' ' + jsonData.cuesheet[i].elevation + ' ' : 0);

			var annotation = null;
			var annotationText = null;
			if (i == 1) {
				annotation = 'S';
				annotationText = jsonData.cuesheet[i - 1].notes;
			}
			if (i == jsonData.cuesheet.length - 1) {
				annotation = 'E';
				annotationText = jsonData.cuesheet[i].notes;
			}
			if (jsonData.cuesheet[i].notes.split(' ')[0] == 'Lunch') {
				annotation = 'L';
				annotationText = jsonData.cuesheet[i].notes;
				ticks.push(Math.round(parseFloat(distance)));
			}
			if (jsonData.cuesheet[i].notes.split(' ')[0] == 'Break') {
				annotation = 'B';
				annotationText = jsonData.cuesheet[i].notes;
				ticks.push(Math.round(parseFloat(distance)));
			}
			if (jsonData.cuesheet[i].rs == '1') {
				annotation = 'rs';
				annotationText = jsonData.cuesheet[i].notes;
				ticks.push(Math.round(parseFloat(distance)));
			}

			data.addRow([ Math.round(parseFloat(distance)), Math.round(parseFloat(elevation)), annotation, annotationText ]);
			if (Math.round(parseFloat(elevation)) > maxElevation) {
				maxElevation = Math.round(parseFloat(elevation));
			}
		}
//		data.addRow([ 130, 0, null, null ]);

		var options = {
			annotations : {
				textStyle : {
//					fontName : 'Times-Roman',
//					fontSize : 18,
					bold : true,
//					italic : true,
//					color : '#871b47', // The color of the text.
//					auraColor : '#d799ae', // The color of the text
					// outline.
//					opacity : 0.8
				// The transparency of the text.
				}
			},
			hAxis : {
				ticks : ticks
			},
			vAxis : {
				viewWindow : {
					max: ((maxElevation * 3 + 99) / 100) * 100
				}
			},
			legend : 'none',
			curveType : 'function',
			height : 200
		};

		var chart = new google.visualization.LineChart(document.getElementById('elevation_chart'));

		chart.draw(data, options);

	});
}
