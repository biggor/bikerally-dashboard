$(document).ready(function() {

	$.getJSON('bikerally_dashboard?riderEventId=' + getParameterByName('ridereventid') + '&crewEventId=' + getParameterByName('creweventid'), function(jsonData) {
		
		switch (getParameterByName('ridereventid')) {
		case "177536":
		case "179193":
			eventYear = "#F4LBR18 - 2016";
			break;
		case "148513":
			eventYear = "#F4LBR17 - 2015";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		case "124639":
			eventYear = "#F4LBR16 - 2014";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		case "96529":
			eventYear = "#F4LBR15 - 2013";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		case "71589":
			eventYear = "#F4LBR14 - 2012";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		case "52935":
			eventYear = "#F4LBR13 - 2011";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		case "39408":
			eventYear = "#F4LBR12 - 2010";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		case "2846":
			eventYear = "#F4LBR7 - 2005";
			$("#1day").addClass('hidden');
			$("#riderGauge").addClass('col-xs-offset-2');
			break;

		default:
			var eventYear = "#F4LBR18 - 2016";
			$("#1day").removeClass('hidden');
			$("#riderGauge").removeClass('col-xs-offset-2');
			break;
		}
		

				$('#eventYear').html(eventYear);
		$('#recacheTime').html((jsonData.recacheTime ? jsonData.recacheTime : ''));
		$('#totalRaised').html((jsonData.totalRaised ? jsonData.totalRaised : 0));
		$('#ridersRegistered').html((jsonData.activeRiders ? jsonData.activeRiders : 0));
		$('#ridersRaised').html((jsonData.ridersTotalCollected ? jsonData.ridersTotalCollected : 0));
		$('#ridersRegistered1d').html((jsonData.activeRiders1d ? jsonData.activeRiders1d : 0));
		$('#ridersRaised1d').html((jsonData.ridersTotalCollected1d ? jsonData.ridersTotalCollected1d : 0));
		$('#crewRegistered').html((jsonData.activeCrew ? jsonData.activeCrew : 0));
		$('#crewRaised').html((jsonData.crewTotalCollected ? jsonData.crewTotalCollected : 0));

		$('#byDate').html((jsonData.byDate ? jsonData.byDate : "-"));

		$('#riders2010').html((jsonData.riders2010 ? jsonData.riders2010 + "<span class=\"gray\">/300 (314)</span>" : 0));
		$('#crew2010').html((jsonData.crew2010 ? jsonData.crew2010 + "<span class=\"gray\">/86 (63)</span>" : 0));
		$('#riders2011').html((jsonData.riders2011 ? jsonData.riders2011 + "<span class=\"gray\">/*298 (355)</span>" : 0));
		$('#crew2011').html((jsonData.crew2011 ? jsonData.crew2011 + "<span class=\"gray\">/*103 (73)</span>" : 0));
		$('#riders2012').html((jsonData.riders2012 ? jsonData.riders2012 + "<span class=\"gray\">/283 (334)</span>" : 0));
		$('#crew2012').html((jsonData.crew2012 ? jsonData.crew2012 + "<span class=\"gray\">/113 (110)</span>" : 0));
		$('#riders2013').html((jsonData.riders2013 ? jsonData.riders2013 + "<span class=\"gray\">/302 (390)</span>" : 0));
		$('#crew2013').html((jsonData.crew2013 ? jsonData.crew2013 + "<span class=\"gray\">/135 (102)</span>" : 0));
		$('#riders2014').html((jsonData.riders2014 ? jsonData.riders2014 + "<span class=\"gray\">/*184 (254)</span>" : 0));
		$('#crew2014').html((jsonData.crew2010 ? jsonData.crew2014 + "<span class=\"gray\">/*95 (95)</span>" : 0));
		$('#riders2015').html((jsonData.riders2015 ? jsonData.riders2015 + "<span class=\"gray\">/201 (272)</span>" : 0));
		$('#crew2015').html((jsonData.crew2015 ? jsonData.crew2015 + "<span class=\"gray\">/100 (100)</span>" : 0));
		$('#riders2016').html((jsonData.riders2016 ? jsonData.riders2016 + "<span class=\"gray\"> (" + jsonData.riders2016 + ")</span>" : 0));
		$('#riders20161d').html((jsonData.riders20161d ? jsonData.riders20161d + "<span class=\"gray\"> (" + jsonData.riders20161d + ")</span>" : 0));
		$('#crew2016').html((jsonData.crew2016 ? jsonData.crew2016 + "<span class=\"gray\"> (" + jsonData.crew2016 + ")</span>" : 0));

		Morris.Line({
			element: 'chart',
			data: [
				{ month: '2014-07', 2010:   0, 2011:   4, 2012:   0, 2013:  11, 2014:   1, 2015:   0, 2016: (jsonData.regAug0 ? jsonData.regAug0 : 0) },
				{ month: '2014-08', 2010:   1, 2011:  26, 2012:  76, 2013:  63, 2014:  45, 2015:  82, 2016: (jsonData.regSep0 ? jsonData.regSep0 : 0) },
				{ month: '2014-09', 2010:  69, 2011:  43, 2012:  84, 2013: 112, 2014:  68, 2015: 156, 2016: (jsonData.regOct0 ? jsonData.regOct0 : 0) },
				{ month: '2014-10', 2010:  77, 2011:  65, 2012:  89, 2013: 123, 2014:  75, 2015: 179, 2016: (jsonData.regNov0 ? jsonData.regNov0 : 0) },
				{ month: '2014-11', 2010:  84, 2011:  83, 2012: 107, 2013: 152, 2014:  93, 2015: 189, 2016: (jsonData.regDec0 ? jsonData.regDec0 : 0) },
				{ month: '2014-12', 2010: 111, 2011: 108, 2012: 131, 2013: 192, 2014: 119, 2015: 204, 2016: (jsonData.regJan ? jsonData.regJan : 0) },
				{ month: '2015-01', 2010: 190, 2011: 150, 2012: 238, 2013: 308, 2014: 160, 2015: 242, 2016: (jsonData.regFeb ? jsonData.regFeb : 0) },
				{ month: '2015-02', 2010: 234, 2011: 255, 2012: 260, 2013: 333, 2014: 202, 2015: 246, 2016: (jsonData.regMar ? jsonData.regMar : 0) },
				{ month: '2015-03', 2010: 274, 2011: 299, 2012: 284, 2013: 365, 2014: 225, 2015: 256, 2016: (jsonData.regApr ? jsonData.regApr : 0) },
				{ month: '2015-04', 2010: 298, 2011: 332, 2012: 303, 2013: 383, 2014: 240, 2015: 269, 2016: (jsonData.regMay ? jsonData.regMay : 0) },
				{ month: '2015-05', 2010: 306, 2011: 347, 2012: 323, 2013: 387, 2014: 248, 2015: 274, 2016: (jsonData.regJun ? jsonData.regJun : 0) },
				{ month: '2015-06', 2010: 310, 2011: 353, 2012: 328, 2013: 391, 2014: 254, 2015: 276, 2016: (jsonData.regJul ? jsonData.regJul : 0) },
				{ month: '2015-07', 2010: 315, 2011: 356, 2012: 335, 2013: 392, 2014: 256, 2015: 277, 2016: (jsonData.regAug ? jsonData.regAug : 0) },
				{ month: '2015-08', 2010: 300, 2011: 298, 2012: 283, 2013: 302, 2014: 184, 2015: 201, 2016: (jsonData.regSep ? jsonData.regSep : 0) }
			],
			xkey: 'month',
			ykeys: ['2010', '2011', '2012', '2013', '2014', '2015', '2016'],
			labels: ['2010', '2011', '2012', '2013', '2014', '2015', '2016'],
			dateFormat: function (x) { var IndexToMonth = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]; return IndexToMonth[new Date(x).getMonth()]; },
			xLabelFormat: function (x) { var IndexToMonth = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]; return IndexToMonth[x.getMonth()]; },
			lineColors: ['#BC74C3','#872C91','#9A47A3','#74167E','#EA89AB','#7a92a3','#0b62a4'],
			pointSize: 2,
			hideHover: 'auto',
			resize: true,
			behaveLikeLine: true
			});
	});
	
});