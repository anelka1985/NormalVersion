var USERID="";
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
	printToConsole("myfriendUSERID--->" + USERID);
	if(USERID==null) showToast("用户ID为空");
	startIndex = GetQueryString("startIndex");
	if(startIndex == null) startIndex = 0;
	$("#manage").attr("href", String.format(myfriendma_html,USERID)); 
	$("#myfriend_index").attr("href", String.format(myfriend_html,USERID)); 
	$("#possiblefriend_index").attr("href", String.format(possiblefriend_html,USERID)); 
	$("#searchfriend_index").attr("href", String.format(findfri_html,USERID)); 
	$("#invitefriend").attr("href", String.format(invfri_html,USERID)); 
	$("#friendHeader").hide();
	$("#myfriend").hide();
	$("#managPart").hide();
	$("#button").hide();
     getNewData(startIndex);
}

function getNewData(startIndex)
{
	var interf = String.format(goodfriend_myfriend_url,startIndex,pageSize,USERID);
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
		$("#myfriend").show();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#myfriend").html(err_str);
	});
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

function fillData(data)
{
	pageLoadFinish();
	var friendList = data.friendList;
	$("#friendHeader").show();
	$("#myfriend").show();
	totalRecored = data.totalRecored;
	$("#friendCount").html(totalRecored);
	var str = "";
	if(friendList.length==0)
	{
		var findFriend =String.format(findfri_html,USERID);
		printToConsole("findFriendURL--->" + findFriend);
		var noStr = '<div class="fri_text">您还没有好友哦，立即去找朋友吧</div>'
		     +'<div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="'+findFriend+'">'
		     +' <span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>';
	    $("#myfriend").html(noStr);
	}
	else
	{
		for (var i=0; i < friendList.length; i++)
	    {
	     	str = str + '<div class="line_1"></div><div class="list pos sym_bg"><div class="sendmesage">'
	 	          + ' <div class="but_man btu_green_com pos "><a onclick="showInputPopup(\'true,,请输入消息内容,发送,sendMessage(%1$s,%2$s,%3$s)_'+friendList[i].userId+'\')" ><span class="btu_man_com2 btu_green_com2 btu_senmes_left">'
	 	          + '</span><span class="btu_mood_right"></span><span class="m_l_20">发消息</span></a></div></div> <div class="P_tb_10 blohi">'
	 	          + '<div class="collLeft pos"><a href="'+String.format(userDetail_html,friendList[i].userId,USERID)+'"><span class="head_mask_76c"></span><img src="'+friendList[i].icon+'" alt=""/></a></div>'
	 	          + '<div class="listRight"><P class="font_20 P_tb_5">' + friendList[i].userName 
	 	          + isOnline(friendList[i].onlineStatus) + '</P>'
	 	          + '<P class="font_18 color_4 m_tb_2 p_r_10">' +friendList[i].starName +'</P>'
	 	          + '<P class="font_18 color_4 m_tb_2 p_r_10">' +friendList[i].dynamicDes + '</P>'
	 	          + '</div></div></div>';
	    };
	
	$("#friendList").html(str);
		$("#managPart").show();
	}
	
	
	handleNewMessage(data);

	if (data.totalRecored != friendList.length) 
	{
		$("#button").show();
	};
	startIndex = data.startIndex;
	var totalPage = Math.ceil(data.totalRecored/pageSize);
	var start = startIndex+1;
	var pageDownIndex = startIndex + pageSize;
	
	var pageUpIndex =0;
	var page = Math.ceil(start/pageSize);
	
	var lastPageIndex = (totalPage -1) * pageSize;
	
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
	   $("#pageHomeIndex").attr("href","goodfriend-myfriend.html?USERID=" + USERID + "&startIndex=0");
	   //上一页
	   $("#pageUpIndex").attr("href","goodfriend-myfriend.html?USERID=" + USERID + "&startIndex=" + pageUpIndex);
	}
	
	if(page == totalPage)
	{
		 $("#pageDownIndex").attr("href","#");
		 $("#lastPageIndex").attr("href","#");
	}
	else
	{
	  //下一页
	  $("#pageDownIndex").attr("href","goodfriend-myfriend.html?USERID=" + USERID + "&startIndex=" + pageDownIndex);
	  
	  //末页
	  $("#lastPageIndex").attr("href","goodfriend-myfriend.html?USERID=" + USERID + "&startIndex=" + lastPageIndex);
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
