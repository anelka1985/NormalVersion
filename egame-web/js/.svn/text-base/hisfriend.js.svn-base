
var url=window.location.href;

var USERID="";

var friendId="";
//好友总数
var totalRecored="";

//已加载朋友的数量
var friendCount ="";

//开始页数
var startIndex="";

var pageSize= 10;

$(document).ready(function()
 {
	init();
});
// 初始化动态详情页面
function init() 
{
	USERID = GetQueryString("USERID");
	friendId = GetQueryString("friendId");
	$("#friendHeader").hide();
	$("#friendBody").hide();
	startIndex = GetQueryString("startIndex");
	if(startIndex == null) startIndex = 0;
     getNewData(startIndex);
}

function getNewData(startIndex)
{
    var interf = String.format(getUserFriend_url, friendId, startIndex, pageSize);
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
		pageLoadFinish();
		$("#friendBody").show();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#friendBody").html(err_str);
	});
}

function fillData(data)
{
	var friendList = data.friendList;
	$("#friendHeader").show();
	$("#friendBody").show();
	totalRecored = data.totalRecored;
	$("#friendCount").html(totalRecored);
	showButton(totalRecored);
	var str = "";
	
	for (var i=0; i < friendList.length; i++)
	 {
	 	str = str + '<div class="line_1"></div><div class="list pos sym_bg"><div class="sendmesage">'
	 	          + '<div class="but_man btu_green_com pos "><a onclick="showInputPopup(\'true,,请输入消息内容,发送,sendMessage(%1$s,%2$s,%3$s)_'+friendList[i].userId+'\')" ><span class="btu_man_com2 btu_green_com2 btu_senmes_left">'
	 	          + '</span><span class="btu_mood_right"></span><span class="m_l_20">发消息</span></a></div></div> <div class="P_tb_10 blohi">'
	 	          + '<div class="collLeft pos"><a href="goodfriend-deta.html?friendId='+ friendList[i].userId +'&USERID='+USERID+'"><span class="head_mask_76c"></span><img src="'+friendList[i].icon+'" alt=""/></a></div>'
	 	          + '<div class="listRight"><P class="font_20 P_tb_5">' + friendList[i].userName 
	 	          + isOnline(friendList[i].onlineStatus) + '</P>'
	 	          + '<P class="font_18 color_4 m_tb_2 p_r_10">' +friendList[i].starName +'</P>'
	 	          + '<P class="font_18 color_4 m_tb_2 p_r_10">' +friendList[i].dynamicDes + '</P>'
	 	          + '</div></div></div>';
	};
	
	if("" == str)
	{
		var findFriend ="goodfriend-findfri.html?USERID="+USERID;
		str = '<div class="fri_text">您还没有好友哦，立即去找朋友吧</div>'
		     +'<div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="'+findFriend+'">'
		     +' <span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>';
	};
	$("#friendList").html(str);
	pageLoadFinish();
	if (data.totalRecored == friendList.length) 
	{
		$("#button").hide();
	};
	startIndex = data.startIndex;
	var totalPage = Math.ceil(data.totalRecored/pageSize);
	var start = startIndex+1;
	var pageDownIndex = startIndex + pageSize;
	var pageUpIndex =0;
	var page = Math.ceil(start/pageSize);
	var lastPageIndex = (totalPage -1) * 10;
	if (startIndex-pageSize>0)
	{
		 pageUpIndex=startIndex-pageSize;
	}
	pageChangeOther(start,pageSize,totalPage);
	
	
	if(startIndex ==0)
	{
	   $("#pageHomeIndex").attr("href","#");
	   $("#pageUpIndex").attr("href","#");
	}
	else
	{
		//首页
	   $("#pageHomeIndex").attr("href","hisfriend.html?USERID=" + USERID + "&friendId=" + friendId + "&startIndex=0");
	   //上一页
	   $("#pageUpIndex").attr("href","hisfriend.html?USERID=" + USERID + "&friendId=" + friendId + "&startIndex=" + pageUpIndex);
	}
	
	if(page == totalPage)
	{
		 $("#pageDownIndex").attr("href","#");
		 $("#lastPageIndex").attr("href","#");
	}
	else
	{
	  //下一页
	  $("#pageDownIndex").attr("href","hisfriend.html?USERID=" + USERID + "&friendId=" + friendId + "&startIndex=" + pageDownIndex);
	  //末页
	  $("#lastPageIndex").attr("href","hisfriend.html?USERID=" + USERID + "&friendId=" + friendId + "&startIndex=" + lastPageIndex);
	}
}
function isOnline(onlineStatus)
{
	if(onlineStatus == 1)
	{
		return '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
	}
	else
	{
		return "";
	};
}

function showButton (count) {
  
  if (startIndex == 1 && count <=  pageSize) {
  	$("#button").hide();
  };
}

function sendMessage(friendId,nickName,content)
{
	printToConsole("nickName:" + nickName + "[content]:" + content);
	var encontent = UrlEncode(content);
	var interf = String.format(sendMessage_url,USERID,friendId,encontent);
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
