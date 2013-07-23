/**
 * @author 姚朋朋
 * 
 */

var userID;
var friendId;
pageLoadFinish();
$(document).ready(function() {
	$("#no1").attr("checked", 'checked');
	 userID = GetQueryString("USERID");
	 friendId = GetQueryString("friendId");
	// $("#dazhaohu").attr("href",
	// "individualcenters-friendgreet.html?USERID="+userID+"&friendId="+friendId);
});

function dazhaohu() {
	pageLoadStart();
	actionId = $('input:radio[name="no1"]:checked').val(); 
//	alert(actionId);
	var interf = String.format(dazhaohu_url, userID, friendId, actionId);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			dazhaohuResult(data);
		},
		dataType : "json"
	});
}

function dazhaohuResult(data) {
	pageLoadFinish();
	var code = data.result.resultcode;
	showToast(data.result.resultmsg);
	goBack();
}

function setchange(radioid) {
	$("[name='radio']").removeClass("icon_radio2");
	$("[name='radio']").addClass("icon_radio1");
	$("#" + radioid).attr("checked", 'checked');
	$("#radio_" + radioid).removeClass("icon_radio1");
	$("#radio_" + radioid).addClass("icon_radio2");
}