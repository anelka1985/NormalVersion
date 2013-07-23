var userID = "";


function fillData(data) {
	pageLoadFinish();
	
	$("#visit_count_h3").show();
	
	$("#visit_count").html(data.visitTotal);
	$("#visit_total_count").html(data.browCount);
	
	$("#visit_list").html("");
	for(i in data.userVisitList) {
		p = data.userVisitList[i];
		icon = p.icon;
		str = '<li class="pos"><a href="goodfriend-deta.html?friendId=%1&USERID='+userID+'"><span class="head_mask_76"></span><img  width="76" height="76" src="%2" alt=""/><P>%3%4</span></P></a></li>';
		online = "";
		if(p.onlineStatus == "1") {
			online = '<span class="online online_76">';
		}
		str = String.format(str, p.userId, icon, online, p.userName);
		$("#visit_list").append(str);
	}
}



function getNewData() {

//	var interf = 'http://221.226.177.158:9010/sns-client/four/user/userVisitList.json?USERID='+userID+'&startIndex=0&pageSize=5';
	var interf = String.format(cometo_url,userID,0,999);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			fillData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		// 把LOADING 换成加载出错提示语
		err_str = '<p style="text-align: center">数据加载异常！</p>';
		$("#visit_count_loading").html(err_str);
		pageLoadFinish();
	});
}

function fresh() {
	getNewData();
}

$(document).ready(function() {
	//
	$("#visit_count_h3").hide();
	userID = GetQueryString("USERID");
	fresh();
	
});