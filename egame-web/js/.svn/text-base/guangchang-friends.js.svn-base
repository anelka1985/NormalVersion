//用户自己的ID
var userID = "";

//每页数量
var pageSize = "";

//第一条id
var firstNum = "";

//最后一条
var lastNum = "";

//默认每页显示数
var defaultPageSize=10;

var markDynamicInfoId = 0;
var direction = 0;

$(document).ready(function()
{
	getNewData();
});

function getNewData()
{
	userID = GetQueryString("USERID");
	if(userID==null) showToast("userID is null");
	$("#button").hide();
	$("#guangchang_index").attr("href", String.format(guangchangIndex_html,userID)); 
	$("#friend_index").attr("href", String.format(guangchangFriend_html,userID));
	$("#activity_index").attr("href", String.format(guangchangActivity_html,userID));
	markDynamicInfoId = 0;
	direction = 0;
	markDynamicInfoId = GetQueryString("markInfoId");
	if(markDynamicInfoId==null) {
		markDynamicInfoId = 0;
	}
	direction = GetQueryString("direction");
//	alert(direction);
	if(direction==null) {
		direction = 0;
	}
//	alert(direction);
	var interf = String.format(guangchang_friend_dyns_url,markDynamicInfoId,direction,userID);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			fillData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus)
    {
		success="1";
		// 把LOADING 换成加载出错提示语
		$("#body").append("<p style=\"text-align: center;font-size:20px;padding-top:30px;\">数据加载异常！</p>");
		pageLoadFinish();
	});
}

function toUrl(url) 
{
	window.location.href=url;
}

/**
 * 动态插入评论
 * dynamicId-动态id
 * nickname-用户昵称
 * content-内容
 */
function insertCommon(dynamicId,nickname,content)
{
	var send_dynamics_url = guangchang_friend_url;
	var encontent = UrlEncode(content);
	var interf = String.format(send_dynamics_url,dynamicId,encontent,userID);
	pageLoadStart();
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			if(data.result.resultcode=='0') {
				content = analyFaceImage(content);
				var insertHtml = String.format(common_html,onlinIcon_html,userID,nickname,content,'刚刚');
				$("#dynamicId_"+dynamicId).after(insertHtml);
				$("#dynamicId_"+dynamicId).parent().find(".commonNum").html("查看全部评论");
				showToast("评论成功");
			}
			else {
				showToast(data.result.resultmsg);
			}
		},
		dataType : "json"
	});

	request.fail(function(textStatus)
    {
    	pageLoadFinish();
		success="1";
		showToast("网络异常，请检查网络");
	});
}

function fillData(data)
{
	pageLoadFinish();
	//解析json数据
    //%1-动态详情链接
    //%2-用户详情链接
    //%3-用户头像src
    //%4-用户是否在线，在线插入 var online
    //%5-动态内容
    //%6-动态图片，有图片插入 var picContext 
    //%7-动态发表时间
    //%8-用户评论列表
    //%9-动态评论总数
    //%10-动态ID
	if(data.result.resultcode=='0') {
	    var htmlStr = "";
	    
	    for (i in data.dynamicList)
		{
			dynamic = data.dynamicList[i];
			var onlineStr = "";
			if(dynamic.onlineStatus=="1") {
				onlineStr = online_html;
			}
			
			var dynDetailStr = String.format(dynDetail_html,dynamic.dynamicId,userID,1);
			var adminCheck = dynamic.managerSign;
			if(adminCheck == 0){
				if(dynamic.userType=='1') {
					//跳转到好友详情
					userDetailStr = 'showDetail(\'friendId\','+dynamic.userId+')';
				}
				else if(dynamic.userType=='2') {
					//跳转到游戏详情
					userDetailStr = 'showDetail(\'gameId\','+dynamic.userId+')';
				}
				else if(dynamic.userType=='3') {
					//跳转到活动详情
					userDetailStr = 'showDetail(\'activityId\','+dynamic.userId+')';
				}
			}
			var picStr = "";
			if(!(dynamic.objectPic == null||dynamic.objectPic=="")) {
				picStr = String.format(picContext_html,dynamic.objectPic,dynamic.objectPic);
			}
			var commonStr = String.format(hidecommon_html,dynamic.dynamicId);
			for(j in dynamic.commentList) {
				if(j>=3) break;
				commentObj =  dynamic.commentList[j];
				var onlineIconStr = "";
				if(commentObj.onlineStatus=="1") {
					onlineIconStr = onlinIcon_html;
				}
				commonStr =commonStr+String.format(common_html,onlineIconStr,commentObj.userId,commentObj.userName,commentObj.comment,commentObj.submitTime);
			}			
			var commonNum = "";
			if(dynamic.commentNum>0) {
				commonNum="查看全部评论("+dynamic.commentNum+"条)";
			}
			else {
				commonNum="暂无评论";
			}
			htmlStr = '<div class="line_1"></div><div class="P_tb_102"><div class="listLeft"><a onclick="'+userDetailStr+'"><span class="webo_kk"></span><img src="'+dynamic.headIcon+'" alt=""/></a>'+onlineStr+'</div><div class="listRight"><div class="words"><a onclick="'+ userDetailStr +'"><span class="text_color_blue"> '+dynamic.userName+'</span></a>说：<P>'+analyFaceImage(dynamic.content)+'</P></div>'+picStr+'<p class="P_tb_10 text_align_right">'+dynamic.sendTime+'</p>'+analyFaceImage(commonStr)+'<div class="line_1"></div><span class="text_color_gray font_lin58 commonNum" onclick="toUrl(\''+dynDetailStr+'\')">'+commonNum+'</span><a onclick="showInputPopup(\'true,,请输入评论内容,发送,insertCommon(%1$s,%2$s,%3$s)_'+dynamic.dynamicId+'\')" class="pl">评论</a></div></div></div>';
			$("#dynamic_list").append(htmlStr);
		}
		firstNum = data.firstNum;
		lastNum = data.lastNum;
		markDynamicInfoId = 0;
		direction = 0;
		var homeUrl = String.format(guangchangFriend1_html,userID,markDynamicInfoId,direction);
		$("#pageHome").attr("href", homeUrl); 
		markDynamicInfoId = firstNum;
		direction = 1;
		var upUrl = String.format(guangchangFriend1_html,userID,markDynamicInfoId,direction);
		$("#pageUp").attr("href", upUrl);
		
		markDynamicInfoId = lastNum;
		direction = 2;
		downUrl = String.format(guangchangFriend1_html,userID,markDynamicInfoId,direction);
		$("#pageDown").attr("href", downUrl);
		
		markDynamicInfoId = 0;
		direction = 3;
		endUrl = String.format(guangchangFriend1_html,userID,markDynamicInfoId,direction);
		$("#pageEnd").attr("href", endUrl);
		
		$("#button").show();
	}
	else {
		$("#body").append('<div class="text_pk"><p>'+data.result.resultmsg+'</p></div>');
	}
}
