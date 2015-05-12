google.load('visualization', '1.1', {
	packages : [ 'line' ]
});
google.setOnLoadCallback(drawChart);

function drawChart() {

	document.getElementById('route_name').innerHTML = getJsonData(getParameterByName("routeid")).routeName;

	var data = new google.visualization.DataTable();
	data.addColumn('number', 'km');
	data.addColumn('number', 'Elevation');

	data.addRows([ [ 1, 37.8 ], [ 13, 4.8 ], [ 14, 4.2 ] ]);

	var options = {
		chart : {
			title : 'Box Office Earnings in First Two Weeks of Opening',
			subtitle : 'in millions of dollars (USD)'
		},
		width : 900,
		height : 500
	};

	var chart = new google.charts.Line(document.getElementById('elevation_chart'));

	chart.draw(data, options);
}
