$(document).ready(function() {

	$.getJSON('bikerally_participants?riderEventId=' + getParameterByName('ridereventid') + '&crewEventId=' + getParameterByName('creweventid'), function(jsonData) {
	for (var i=0; i<jsonData.participants.length; i++) {
		$('#participants').append('<li class="list-group-item"><span class="badge">' + jsonData.participants[i].riderNumber + '</span><h4 class="list-group-item-heading">' + jsonData.participants[i].firstName + ' ' + jsonData.participants[i].lastName + '</h4><p class="list-group-item-text">' + jsonData.participants[i].teamName + '</p></li>')
	}

	});

	
	(function($) {

		$('#filter').keyup(function() {

			var rex = new RegExp($(this).val(), 'i');
			$('.list-group li').hide();
			$('.list-group li').filter(function() {
				return rex.test($(this).text());
			}).show();

		})

	}(jQuery));

});