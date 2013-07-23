/**
 * @author 姚朋朋
 *
 */

var userID;
var friendId;

$(document).ready(function() {
	userID = GetQueryString("USERID");
	friendId = GetQueryString("friendId");
	$("#dyn_more").attr("href", "goodfriend-alldyn.html?USERID="+userID+"&friendId="+friendId);
	$("#hisallgame").attr("href", "my_ta_game.html?USERID="+userID+"&friendId="+friendId);
	$("#hisallcollect").attr("href", "collection.html?USERID="+userID+"&friendId="+friendId);
	$("#hisallfriend").attr("href", "hisfriend.html?USERID="+userID+"&friendId="+friendId);
	$("#dazhaohu").attr("href", "individualcenters-friendgreet.html?USERID="+userID+"&friendId="+friendId);
	init();
});
// 初始化动态详情页面
function init() {
	$("#allContent").hide();
	$("#frienddetail").hide();
	$("#persondetail").hide();
	$("#hispic").hide();
	$("#userFriend").hide();
	$("#userGame").hide();
	$("#userCollect").hide();
	$("#userDynamics").hide();
	$("#dyn_more_block").hide();
	getNewData();
}

function getNewData() {
	var interf=String.format(userdetail_url, friendId, userID);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			fillData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		success = "1";
		pageLoadFinish();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#allContent").html(err_str);
		$("#allContent").show();
	});
}

function fillData(data) {
	if(data.result.resultcode=='0') {
    var expStr = data.friendDetail.exp.split("/")[0];
	var content = "";
	var content = '<div class="picture_pLeft picture_pCom"><a href="goodfriend-deta.html?friendId='+data.friendDetail.userId+'&USERID='+userID+'"><span class="head_mask_76c"></span><img src="' + data.friendDetail.icon + '" alt=""/></a></div>' + '<div class="picture_pMid picture_pCom">' + '<h2>' + data.friendDetail.userName + '<span class="color_1"><a href="">(Lv' + data.friendDetail.lvl + ')</a></span></h2>' + '<h3 class="color_4"> ' +ismanorwoman( data.friendDetail.gender ) + ' ' + showProvinecOrCity(data.friendDetail.province) + ' ' + showProvinecOrCity(data.friendDetail.city) + ' ' + isAge(data.friendDetail.age) + '</h3><h3 class="color_4">经验：'+expStr+'</h3></div>';
	$("#hobby").html(data.friendDetail.hobby);
	if (data.friendDetail.hobby == "" | data.friendDetail.hobby == "null" | data.friendDetail.hobby == null) {
		$("#hobby").html("Ta是游戏菜鸟，没有填写爱好哦!<span class=\"mood_triangle\"></span><span class=\"mood_absolute mood_top_left\"></span><span class=\"mood_absolute mood_top_right\"></span><span class=\"mood_absolute mood_bottom_left\"></span><span class=\"mood_absolute mood_bottom_right\"></span>");
	};
	$("#persondetail").html(content);
	$("#persondetail").show();
	var sendMessageStr = '<a onclick="showInputPopup(\'true,,请输入消息内容,发送,sendMessage(%1$s,%2$s,%3$s)_'+data.friendDetail.userId+'\')"><span class="btu_man_com2 btu_green_com2 btu_senmes_left"></span><span class="btu_mood_right"></span><span class="m_l_20">发 消 息</span></a>'
	$("#sendMessage").html(sendMessageStr);
	var addFriend = 
	       '<a onclick="addFriend()">'
          +  '<span class="fri_join_left"></span>'
          +  '<span class="fri_join_right"></span>'
          +  '<span class="addfrie">加为好友</span>'
          +'</a>';
	$("#addFriend").html(addFriend);
	$("#frienddetail").show();
	if (1 == data.isFriend) {
		$("#jiahaoyouAble").show();
	};
    userCollect(data);
	userGame(data);
	userDynamics(data);
	userFriend(data);
	getUserPhoto();
	$("#allContent").show();
	}
	else {
		err_str = '<div class="text_pk"><p>'+data.result.resultmsg+'</p></div>';
		$("#allContent").html(err_str);
		$("#allContent").show();
	}
}

//获得用户相册
function getUserPhoto() {
	var interf=String.format(getUserPhoto_url, friendId, userID);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			userPhotoList(data);
		},
		dataType : "json"
	});

}

function userPhotoList(data) {
	if(data.result.resultcode=='0') {
		var content = '<ul>';
		for(var i = 0; i < data.userPhotoList.photos.length; i++) {
			if (i >=5) {break;};
			content = content + '<li class="pos"><a onclick="showImage(\''+data.userPhotoList.photos[i]+'\')"> <span class="head_mask_76"></span><img src="'+data.userPhotoList.photos[i]+'" width="76" height="76" alt=""/></a></li>'
		};
		content = content + '</ul>';
		$("#hispic").html(content);
		$("#hispic").show();
	}
}

//获得好友最多5个

function getUserFriend() {
	var interf=String.format(getUserFriend_url,friendId,userID,0,5);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			userFriend(data);
		},
		dataType : "json"
	});

}

function userFriend(data) {
	if(data.result.resultcode=='0') {
		
		if(data.friendList.length>0)
		{
			
		    var content = '<ul>';
		    for(var i = 0; i < data.friendList.length; i++)
		    {
			   content = content + '<li class="pos"><a href="goodfriend-deta.html?friendId='+data.friendList[i].userId+'&USERID='+userID+'"> <span class="head_mask_76"></span><img src="' + data.friendList[i].icon + '" width="76" height="76" alt=""/><P>'+isOnline(data.friendList[i].userName,data.friendList[i].onlineStatus) +'</P></a></li>';
		    }
		    content = content + '</ul>';
		    $("#friend").html(content);
		}
		else
		{
			var noStr='<p class="texalce color_2 ">Ta还没有好友哦！</p>';
			$("#friend").html(noStr);
		}
		if(data.friendSize==0) {
			$("#hisallfriendcount").html("");
		}
		else {
			$("#hisallfriendcount").html("("+data.friendSize+")");
		}
		if(0 == data.friendList.length){
			$("#hisallfriend").css('display', 'none');
		}
		
		$("#userFriend").show();
	}
}

//他的游戏
function userGame(data) {
	if(data.result.resultcode=='0') {
		
		if(data.userGameList.length>0)
		{
			var content = '<ul class="P_l_10">';
		    for(var i = 0; i < data.userGameList.length; i++)
		    {
			   content = content + '<li class="pos"><a onclick="showDetail(\'gameId\',\''+data.userGameList[i].gameId+'\')"> <span class="head_mask_76"></span><img src="'+data.userGameList[i].gamePic+'" width="76" height="76" alt=""/><P>' + data.userGameList[i].gameName + '</P></a></li>';
		    }
		    content = content + '</ul>';
		    $("#game").html(content);
		}
		else
		{
			var noStr='<p class="texalce color_2 ">Ta还没有开始玩游戏哦！</p>';
			$("#game").html(noStr);
		}
		if(data.userGameSize==0) {
			$("#hisallgamecount").html("");
		}
		else {
			$("#hisallgamecount").html("("+data.userGameSize+")");
		}
		if(0 == data.userGameSize){
			$("#hisallgame").css('display', 'none');
		}
		
		$("#userGame").show();
	}
	//getUserCollect();
}

//他的收藏
function userCollect(data) {
	
	if(data.userCollectList.length>0)
	{
	   var content = '<ul>';
	   for (i in data.userCollectList)
	   {
		   content = content + '<li class="pos"><a onclick="showDetail(\'gameId\',\''+data.userCollectList[i].gameId+'\')"> <span class="head_mask_76"></span><img src="'+data.userCollectList[i].gamePic+'" width="76" height="76" alt=""/><P>' + data.userCollectList[i].gameName + '</P></a></li>';
	    }
	    content = content + '</ul>';
	    $("#collect").html(content);
	}
	else
	{
		var noStr='<p class="texalce color_2 ">Ta还没有收藏游戏哦！</p>';
		$("#collect").html(noStr);
	}
	if(data.userCollectSize==0) {
		$("#hiscollectcount").html("");
	}
	else {
		$("#hiscollectcount").html("("+data.userCollectSize+")");
	}
	if(0 == data.userCollectSize)
	{
		$("#hisallcollect").css('display', 'none');
	}
	$("#userCollect").show();
}

//他的动态
function userDynamics(data) {
	var dynSpan = '';
	if(data.userDynamicSize!=0) {
		dynSpan = '<span>(' + data.userDynamicSize + ')</span>';
	}
	var content = '<div class="nav_title">他的动态' +dynSpan+ '<span class="btu_clear"><a href="goodfriend-alldyn.html?USERID='+userID+'&friendId='+friendId+'",id="hisallDynamics">全部</a></span></div>'
	$("#allDynamics").html(content);
	for (i in data.dynamicList) {
		dynamic = data.dynamicList[i];
		var onlineStr = "";
		if(dynamic.onlineStatus=="1") {
			onlineStr = online_html;
		}
		var dynDetailStr = String.format(dynDetail_html,dynamic.dynamicId,userID,0);
		var userDetailStr = '';
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
		var htmlStr = '';
		if(i > 0) {
			htmlStr = '<div class="line_1"></div>';
		}
		htmlStr += '<div class="P_tb_102"><div class="listLeft"><a onclick="'+userDetailStr+'"><span class="webo_kk"></span><img src="'+dynamic.headIcon+'" alt=""/></a>'+onlineStr+'</div><div class="listRight"><div class="words"><a onclick="'+ userDetailStr +'"><span class="text_color_blue vicon_speaker"> '+dynamic.userName+'</span></a>说：<P><span class="color_1">#'+dynamic.typeName+'#</span>'+analyFaceImage(dynamic.content)+'</P></div>'+picStr+'<p class="P_tb_10 text_align_right">'+dynamic.sendTime+'</p>'+analyFaceImage(commonStr)+'<div class="line_1"></div><span class="text_color_gray font_lin58 commonNum" onclick="toUrl(\''+dynDetailStr+'\')">'+commonNum+'</span><a onclick="showInputPopup(\'true,,请输入评论内容,发送,insertCommon(%1$s,%2$s,%3$s)_'+dynamic.dynamicId+'\')" class="pl">评论</a></div></div></div>';
		$("#dynamics").append(htmlStr);
		
		$("#userDynamics").show();
		if(data.userDynamicSize==0){
			$("#hisallDynamics").css('display', 'none');
		}
		if(data.userDynamicSize>5){
	    	$("#dyn_more_block").show();
		}
	}
}
function ismanorwoman (gender){
	if('0' == gender){
		return "男";
	}
	else
	{
		return "女";
	};
}

function showProvinecOrCity (content) {
  if (content == "" | content == "null" | content == null) {
  	return "";
  }else{
  	return content;
  };
}

function isOnline(onlineStatus)
{
	if(onlineStatus == 1)
	{
		return '<span class="online online_56">在线</span>';
	}
	else
	{
		return "";
	};
}

function addFriend()
{
	pageLoadStart();
	var interf=String.format(addFriend_Request_url,friendId,userID);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			addFriendResult(data);
		},
		dataType : "json"
	});

}

function addFriendResult(data)
{
	pageLoadFinish();
	var code =data.result.resultcode;
	
	if(code == null) code=1;
	
	if (code == 0) 
	{
		//成功
		showToast(data.result.resultmsg);
	}
	else
	{
		//失败
		showToast(data.result.resultmsg);
	}
	
	
	
}

/**
 * 动态插入评论
 * dynamicId-动态id
 * nickname-用户昵称
 * content-内容
 */
function insertCommon(dynamicId,nickname,content)
{
	var send_dynamics_url = guangchang_index_url;
	var encontent = UrlEncode(content);
	var interf = String.format(send_dynamics_url,dynamicId,encontent,userID);
	pageLoadStart();
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			
			if(data.result.resultcode=='0') {
				content = analyFaceImage(content);
				var insertHtml = String.format(common_html,onlinIcon_html,userID,nickname,content,'刚刚');
				$("#dynamicId_"+dynamicId).after(insertHtml);
				$("#dynamicId_"+dynamicId).parent().find(".commonNum").html("查看全部评论")
				showToast("评论成功！");
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

function sendMessage(friendId,nickName,content)
{
	printToConsole("nickName:" + nickName + "[content]:" + content);
	var encontent = UrlEncode(content);
	var interf = String.format(sendMessage_url,userID,friendId,encontent);
	printToConsole(interf);
    pageLoadStart();
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			sendMessageResult(data);
		},
		dataType : "json"
	});
	
	request.fail(function(textStatus)
    {
    	pageLoadFinish();
		showToast("网络异常，请检查网络");
	});
}

function sendMessageResult(data)
{
	var code = data.result.resultcode;
	if(code == 0)
	{
		showToast("发送消息成功");
	}
	else
	{
		showToast("发送消息失败," + data.result.resultmsg);
	}
}

function toUrl(url) 
{
	window.location.href=url;
}

function isAge(age)
{
	if(!isNaN(age))
	{
		return age + '岁';
	}
	else
	{
		return age;
	}
}

function isOnline(userName,onlineState)
{
	if(onlineState == 1)
	{
		return '<span class="online online_76">' + userName + '</span> '
	}
	else
	{
		return userName;
	}
}
