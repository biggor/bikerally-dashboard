google.load("visualization", "1", {
	packages : [ "timeline" ]
});
google.setOnLoadCallback(drawChart);

function drawChart() {
	$.getJSON('bikerally_routedetails?routeid=' + getParameterByName('routeid'), function(jsonData) {
		var container = document.getElementById('timeline_chart');
		var chart = new google.visualization.Timeline(container);
		var dataTable = new google.visualization.DataTable();

		dataTable.addColumn({type : 'string', id : 'Index'});
		dataTable.addColumn({type : 'string', id : 'Crew'});
		dataTable.addColumn({type : 'date', id : 'Start'});
		dataTable.addColumn({type : 'date', id : 'End'});
		
		var routeDistance = jsonData.distance;
		var initialHour = jsonData.date.hourOfDay;
		var initialMinute = jsonData.date.minute;
		var startTime = new Date(0,0,0,initialHour, initialMinute, 0);
		var endTime = new Date(0,0,0,initialHour, initialMinute, 0);
		endTime.setHours(jsonData.endTime.split(':')[0]);
		endTime.setMinutes(jsonData.endTime.split(':')[1]);
		var duration = (endTime - startTime) / 1000 / 60 / 60;
		if (duration <= 0) {
			duration = 8;
			endTime.setHours(startTime.getHours() + duration);
		}
		
		var colors = [];
		
		var minSpeed = routeDistance/duration;
		var maxSpeed = 30;
		for (var i = 0; i < jsonData.cuesheet.length; i++) {
			var fast = new Date(0,0,0,initialHour, initialMinute, 0);
			var slow = new Date(0,0,0,initialHour, initialMinute, 0);
			var fastTime = new Date(0,0,0,initialHour, initialMinute, 0);
			var slowTime = new Date(0,0,0,initialHour, initialMinute, 0);
			var distance = jsonData.cuesheet[i].distance;
			var index = jsonData.cuesheet[i].index.toString();
			var label = '';

			var fastMin = Math.round(distance * 60 / maxSpeed);
			var slowMin = Math.round(distance * 60 / minSpeed);
			fast.setMinutes(fast.getMinutes() + fastMin);
			slow.setMinutes(slow.getMinutes() + slowMin);

			var fastMinutes = Math.floor(distance * 60 / maxSpeed / 15) * 15;
			var slowMinutes = Math.ceil(distance * 60 / minSpeed / 15) * 15;
			fastTime.setMinutes(fastTime.getMinutes() + fastMinutes);
			slowTime.setMinutes(slowTime.getMinutes() + slowMinutes);
			
			console.log("" + formatTime(fast) + " - " + formatTime(slow) + " : " + formatTime(fastTime) + " - " + formatTime(slowTime));

			if (jsonData.cuesheet[i].index == '1') {
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "check-out", new Date(0,0,0,initialHour, initialMinute + 0, 0), new Date(0,0,0,initialHour, initialMinute + 30, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "rs", new Date(0,0,0,initialHour + 1, initialMinute + 0, 0), new Date(0,0,0,initialHour + 1, initialMinute + 1, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "rs*", new Date(0,0,0,initialHour + 1, initialMinute + 1, 0), new Date(0,0,0,initialHour + 1, initialMinute + 2, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "Break", new Date(0,0,0,initialHour + 1, initialMinute + 2, 0), new Date(0,0,0,initialHour + 1, initialMinute + 3, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "Break", new Date(0,0,0,initialHour + 1, initialMinute + 3, 0), new Date(0,0,0,initialHour + 1, initialMinute + 4, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "Break", new Date(0,0,0,initialHour + 1, initialMinute + 4, 0), new Date(0,0,0,initialHour + 1, initialMinute + 5, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "Lunch", new Date(0,0,0,initialHour + 1, initialMinute + 5, 0), new Date(0,0,0,initialHour + 1, initialMinute + 6, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "check-in", new Date(0,0,0,initialHour + 1, initialMinute + 6, 0), new Date(0,0,0,initialHour + 1, initialMinute + 7, 0) ]);
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), "rs", new Date(0,0,0,17,0,0), new Date(0,0,0,18,0,0) ]);
			}
			if (jsonData.cuesheet[i].notes.split(' ')[0] == 'Lunch') {
				label = "Lunch";
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), label, fastTime, slowTime ]);
			}
			if (jsonData.cuesheet[i].notes.split(' ')[0] == 'Break') {
				label = "Break";
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), label, fastTime, slowTime ]);
			}
			if (jsonData.cuesheet[i].rs == '1') {
				label = 'rs';
				var minutes = (slowTime - fastTime) / 1000 / 60;
				if (minutes > 120 ) {
					var midTime = new Date(0,0,0,initialHour, initialMinute, 0);
					midTime.setMinutes(midTime.getMinutes() + Math.round((fastMinutes + minutes / 2 ) / 15) * 15);
					label = 'rs';
					dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), label, fastTime, midTime ]);
					label = 'rs*';
					dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), label, midTime, slowTime ]);
				} else {
					label = 'rs';
					dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), label, fastTime, slowTime ]);
				}
			}
			if (jsonData.cuesheet[i].type == 'End') {
				label = 'check-in';
				dataTable.addRow(["" + formatIndex(index) + " " + formatDistance(distance) + " | " + formatTimeInterval(fastTime, slowTime), label, fastTime, endTime ]);
			}
			
		}
		console.log(colors);

		var options = {
			    colors: ['#D9853B', '#BCBCBA', '#9C9C9A', '#74AFAD', '#558C89', '#D9853B'],
				height: 1000
		};
		
		chart.draw(dataTable, options);
	});

}

function formatIndex(index) {
	return "" + (index < 10) ? " " + index : index;
}

function formatTime(date) {
	var hours = (date.getHours() > 12) ? date.getHours()-12 : date.getHours();
	hours = (hours < 10) ? "." + hours : hours;
	var minutes = (date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes();
	return "" + hours + ":" + minutes;
}

function formatDistance(distance) {
	var km = distance.toFixed(1);
	var leadingSpace = "...";
	if (km < 100) {
		leadingSpace = "....";
	}
	if (km < 10) {
		leadingSpace = ".....";
	}
	return "" + leadingSpace + km + " km";
}

function formatTimeInterval(fastTime, slowTime) {
		return "" + formatTime(fastTime) + "-" + formatTime(slowTime) + "";
}
