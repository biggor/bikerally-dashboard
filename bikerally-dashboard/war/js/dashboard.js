$(document).ready(function() {

	$.getJSON('bikerally_dashboard?riderEventId=' + getParameterByName('ridereventid') + '&crewEventId=' + getParameterByName('creweventid'), function(jsonData) {
		$('#recacheTime').html((jsonData.recacheTime ? jsonData.recacheTime : ''));
		$('#totalRaised').html((jsonData.totalRaised ? jsonData.totalRaised : 0));
		$('#ridersRegistered').html((jsonData.activeRiders ? jsonData.activeRiders : 0));
		$('#ridersRaised').html((jsonData.ridersTotalCollected ? jsonData.ridersTotalCollected : 0));
		$('#ridersRegistered1d').html((jsonData.activeRiders1d ? jsonData.activeRiders1d : 0));
		$('#ridersRaised1d').html((jsonData.ridersTotalCollected1d ? jsonData.ridersTotalCollected1d : 0));
		$('#crewRegistered').html((jsonData.activeCrew ? jsonData.activeCrew : 0));
		$('#crewRaised').html((jsonData.crewTotalCollected ? jsonData.crewTotalCollected : 0));

	});
});