$(function() {
	$('#us-map').vectorMap({
		map : 'us_aea_en',
		backgroundColor : 'white',
		zoomOnScroll : 'false',
		regionStyle : {
			initial : {
				fill : 'white',
				"fill-opacity" : 1,
				stroke : 'black',
				"stroke-width" : 1,
				"stroke-opacity" : 1
			},
			hover : {
				"fill-opacity" : 0.8,
				cursor : 'pointer'
			},
			selected : {
				fill : 'yellow'
			},
			selectedHover : {}
		},
		series : {
			regions : [ {
				scale : {
					blue : 'LightSkyBlue',
					white : 'white'
				},
				attribute : 'fill',
				values : {
					"US-VA" : 'blue',
					"US-PA" : 'blue',
					"US-TN" : 'white',
					"US-ID" : 'blue',
					"US-NV" : 'blue',
					"US-TX" : 'white',
					"US-NH" : 'blue',
					"US-NY" : 'blue',
					"US-HI" : 'blue',
					"US-VT" : 'blue',
					"US-NM" : 'blue',
					"US-NC" : 'blue',
					"US-ND" : 'white',
					"US-NE" : 'white',
					"US-LA" : 'white',
					"US-SD" : 'white',
					"US-DC" : 'white',
					"US-DE" : 'blue',
					"US-FL" : 'white',
					"US-WA" : 'blue',
					"US-KS" : 'white',
					"US-WI" : 'blue',
					"US-OR" : 'blue',
					"US-KY" : 'white',
					"US-CO" : 'blue',
					"US-OH" : 'white',
					"US-OK" : 'blue',
					"US-WV" : 'blue',
					"US-WY" : 'blue',
					"US-UT" : 'blue',
					"US-IN" : 'blue',
					"US-IL" : 'blue',
					"US-AK" : 'blue',
					"US-NJ" : 'blue',
					"US-ME" : 'blue',
					"US-MD" : 'blue',
					"US-AR" : 'white',
					"US-MA" : 'blue',
					"US-AL" : 'white',
					"US-MO" : 'white',
					"US-MN" : 'blue',
					"US-CA" : 'blue',
					"US-IA" : 'blue',
					"US-MI" : 'white',
					"US-GA" : 'white',
					"US-AZ" : 'blue',
					"US-MT" : 'blue',
					"US-MS" : 'white',
					"US-SC" : 'blue',
					"US-RI" : 'blue',
					"US-CT" : 'blue'
				},
				legend : {
					horizontal : true,
					title : '',
					labelRender : function(v) {
						return {
							blue : 'yes',
							white : 'no'
						}[v];
					}
				}
			} ]
		},

	});
});
