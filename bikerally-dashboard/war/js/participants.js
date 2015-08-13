$(document).ready(function() {

	$.getJSON('bikerally_participants?riderEventId=' + getParameterByName('ridereventid') + '&crewEventId=' + getParameterByName('creweventid'), function(jsonData) {
		for (var i = 0; i < jsonData.participants.length; i++) {
			var riderNumber = '' + (jsonData.participants[i].riderNumber ? ' ' + jsonData.participants[i].riderNumber + ' ' : '');
			var sc = '' + (jsonData.participants[i].steeringCommittee ? ' SC ' : '');
			var tl = '' + (jsonData.participants[i].teamLead ? ' TL ' : '');
			var trc = '' + (jsonData.participants[i].trainingRideCoach ? ' TRC ' : '');
			$('#participants').append('<li class="list-group-item"><span class="badge">' + riderNumber + '</span><span class="badge alert-info">' + sc + '</span><span class="badge alert-success">' + tl + '</span><span class="badge alert-danger">' + trc + '</span><h4 class="list-group-item-heading">' + jsonData.participants[i].firstName + ' ' + jsonData.participants[i].lastName + '</h4><p class="list-group-item-text">' + jsonData.participants[i].teamName + '<span class="invisible"> ' + jsonData.participants[i].fundraisingLevel + '</span></p></li>')
			$('#filterCount').html((jsonData.riderCount ? jsonData.riderCount : '') + '/' + (jsonData.crewCount ? jsonData.crewCount : ''));
		}

	});

	(function($) {

		$('#filter').keyup(function() {

			var rex = new RegExp($(this).val(), 'i');
			$('.list-group li').hide();
			$('.list-group li').filter(function() {
				return rex.test($(this).text());
			}).show();
			$('#filterCount').html($('.list-group li:visible').length);
		})

	}(jQuery));

});