$(document).ready(function() {

	$.getJSON('bikerally_levels?riderEventId=' + getParameterByName('ridereventid'), function(jsonData) {
		$('#activeRiders').html('' + jsonData.activeRiders ? jsonData.activeRiders : 0);
		$('#estimatedActiveRiders').html('' + jsonData.estimatedActiveRiders ? jsonData.estimatedActiveRiders : 0);
		$('#level2500').html('' + jsonData.level2500 ? jsonData.level2500 : 0);
		$('#level1500').html('' + jsonData.level1500 ? jsonData.level1500 : 0);
		$('#level1000').html('' + jsonData.level1000 ? jsonData.level1000 : 0);
		$('#level500').html('' + jsonData.level500 ? jsonData.level500 : 0);
		$('#level1').html('' + jsonData.level1 ? jsonData.level1 : 0);
		$('#level0').html('' + jsonData.level0 ? jsonData.level0 : 0);
	});

});