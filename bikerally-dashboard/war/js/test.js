$('document').ready(function() {
    var teamId = parseInt('580814');
    var languageCode = '';
    var sourceReferrerUrl = 'http://bikerally.org/pledge-a-participant.html';
    var anonymousText = 'Anonymous';
    var isOrderByAmount = 'True' == "True";

    var parameters = "{'teamID':" + teamId +
        ",'languageCode':'" + languageCode + "'" +
        ",'sourceReferrerUrl':'" + sourceReferrerUrl + "'" +
        ",'anonymousText':'" + anonymousText + "'" +
        ",'isOrderByAmount':" + isOrderByAmount + "}";

    $.ajax({
        type: "POST",
        url: "https://secure.e2rm.com/registrant/WebServices/RegistrantWebService.asmx/GetTeamMembers",
        dataType: "json",
        data: parameters,
        contentType: "application/json; charset=utf-8",
        success: function (data) {
        	console.log(JSON.parse(data.d));
         }
    });
});
