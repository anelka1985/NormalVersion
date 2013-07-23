var url=window.location.href;
//好友总数
var totalRecored="";

//已加载朋友的数量
var friendCount ="";

//开始页数
var startIndex= 0;

var pageSize= 10;

var USERID="";

$(document).ready(function()
{
	init();
});
// 初始化动态详情页面
function init() 
{
	USERID = GetQueryString("USERID");
	if(USERID==null)showToast("用户ID为空");
	$("#myfriend_index").attr("href", String.format(myfriend_html,USERID)); 
	$("#possiblefriend_index").attr("href", String.format(possiblefriend_html,USERID)); 
	$("#searchfriend_index").attr("href", String.format(findfri_html,USERID)); 
	$("#invitefriend").attr("href", String.format(invfri_html,USERID));  
	$("#friendHeader").hide();
	$("#friendBody").hide();
	 getNewData();
}

function getNewData()
{
	var interf = String.format(goodfriend_possiblefriend_url,USERID);
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
	pageLoadFinish();
	var impossbleFriendList = data.impossbleFriendList;
	$("#friendHeader").show();
	$("#friendBody").show();
	totalRecored = data.totalRecored;
	$("#friendCount").html(totalRecored);
	var str = "";
	
	for (var i=0; i < impossbleFriendList.length; i++)
	 {
	 	str = str + '<div class="line_1"></div><div class="list pos sym_bg"><div class="sendmesage" id="friendId_'+i+'">'
	 	          + '<div class="but_man btu_green_com pos "><a onclick="addFriend(' + impossbleFriendList[i].userId +','+ i+')"><span class="btu_man_com2 btu_green_com2 btu_addfri_left"></span>'
	 	          + '<span class="btu_mood_right"></span><span class="m_l_20">加好友</span></a></div></div> <div class="P_tb_10 blohi">'
	 	          + '<div class="collLeft pos"><a href="'+String.format(userDetail_html,impossbleFriendList[i].userId,USERID)+'"><span class="head_mask_76c"></span><img src="'+ impossbleFriendList[i].icon+'" alt=""/></a></div>'
	 	          + '<div class="listRight"><P class="font_20 P_tb_5">' + impossbleFriendList[i].userName 
	 	          + isOnline(impossbleFriendList[i].icon)+'</P>'
	 	          + '<P class="font_18 color_4 m_tb_2 p_r_10">' +impossbleFriendList[i].reason +'</P>'
	 	          + '</div></div></div>';
	};
	
	//如果没有朋友
	if ("" == str)
	 {
	 	str='<div class="fri_text">很遗憾，由于您的资料不完善，我们无法匹配您可能认识的人。</div>'
	 	   +'<div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="individual-centers.html?USERID="'+USERID+'>'
	 	   +'<span class="btu_gr_l"></span><span class="btu_gr_r"></span>立即完善资料 </a></div></div>';
	    $("#myfriend").html(str);
	 }
	
	$("#friendList").html(str);
	
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

function addFriend(friendId,i)
{
	pageLoadStart();
	
	var interf=String.format(addFriend_Request_url,friendId,USERID);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			addFriendResult(data,friendId,i);
		},
		dataType : "json"
	});

}

function addFriendResult(data,friendId,i)
{
	pageLoadFinish();
	var code =data.result.resultcode;
	
	if (code == 0) 
	{
		var str = '<p class="color_4 p_r_10 texalce">请求已发送</p>';
		$("#friendId_"+i).html(str);
		//成功
		showToast("好友请求已发送，请等待对方回应！");
	}
	else
	{
		//失败
		showToast(data.result.resultmsg);
	}
}
