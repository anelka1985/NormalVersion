
var url=window.location.href;

var USERID="";
//好友总数
var totalRecored="";

//已加载朋友的数量
var friendCount ="";

//开始页数
var startIndex= 0;

var pageSize= 10;
var temp=0;
$(document).ready(function()
 {
	init();
});
// 初始化动态详情页面
function init() 
{
	USERID = GetQueryString("USERID");
	if(USERID==null)showToast("用户ID为空");
	startIndex = GetQueryString("startIndex");
	if(startIndex == null) startIndex = 0;
	$("#manage").attr("href", String.format(myfriend_html,USERID)); 
	$("#myfriend_index").attr("href", String.format(myfriend_html,USERID)); 
	$("#possiblefriend_index").attr("href", String.format(possiblefriend_html,USERID)); 
	$("#searchfriend_index").attr("href", String.format(findfri_html,USERID)); 
	$("#invitefriend").attr("href", String.format(invfri_html,USERID)); 
	$("#friendHeader").hide();
	$("#myfriend").hide();
     getNewData(startIndex);
}

function getNewData(startIndex)
{
	var interf = String.format(goodfriend_myfriendma_url,startIndex,pageSize,USERID);
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

function fillData(data)
{
	var friendList = data.friendList;
	$("#friendHeader").show();
	$("#myfriend").show();
	totalRecored = data.totalRecored;
	$("#friendCount").html(totalRecored);
	var str = "";
	temp = friendList.length;
	for (var i=0; i < friendList.length; i++)
	 {
	 	str =str +'<div id="'+friendList[i].userId+'">'
	 	         +'<div class="line_1"></div>'
	 	         +' <div class="list pos sym_bg">'
	 	         +'<div class="sendmesage">'
	 	         +'<div class="but_man btu_green_com pos "><a onclick="delFriend('+friendList[i].userId+')"><span class="btu_man_com2 btu_green_com2 btu_dele_left"></span><span class="btu_mood_right"></span><span class="m_l_20">删 除</span></a></div>'
	 	         +'</div>'
	 	         +'<div class="P_tb_10 blohi">'
	 	         +'<div class="collLeft pos"><a href="'+String.format(userDetail_html,friendList[i].userId,USERID)+'"><span class="head_mask_76c"></span><img src="'+friendList[i].icon+'" alt=""/></a></div>'
	 	         +' <div class="listRight">'
	 	         +'<P class="font_20 P_tb_5">'+ friendList[i].userName +isOnline(friendList[i].onlineStatus)+'</P>'
	 	         +' <P class="font_18 color_4 m_tb_2 p_r_10">' +friendList[i].starName +'</P>'
	 	         +'<P class="font_18 color_4 m_tb_2 p_r_10">' +friendList[i].dynamicDes + ' </P>'
	 	         +' </div></div></div>'
	 	         +'</div>';
	};
	$("#friendList").html(str);
	if("" == str)
	{
		var findFriend ="goodfriend-findfri.html?USERID="+USERID;
		str = '<div class="fri_text">您还没有好友哦，立即去找朋友吧</div>'
		     +'<div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="'+findFriend+'">'
		     +' <span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>';
	   $("#myfriend").html(str);
	};
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
	   $("#pageHomeIndex").attr("href","goodfriend-myfriendma.html?USERID=" + USERID + "&startIndex=0");
	   //上一页
	   $("#pageUpIndex").attr("href","goodfriend-myfriendma.html?USERID=" + USERID + "&startIndex=" + pageUpIndex);
	}
	
	if(page == totalPage)
	{
		 $("#pageDownIndex").attr("href","#");
		 $("#lastPageIndex").attr("href","#");
	}
	else
	{
	  //下一页
	  $("#pageDownIndex").attr("href","goodfriend-myfriendma.html?USERID=" + USERID + "&startIndex=" + pageDownIndex);
	  
	  //末页
	  $("#lastPageIndex").attr("href","goodfriend-myfriendma.html?USERID=" + USERID + "&startIndex=" + lastPageIndex);
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

function delFriend(friendId)
{
	showChoiceDialog("您确定要删除此好友吗？","isDeleteFriend(\'"+friendId+"\')");
	
}

function isDeleteFriend(friendId)
{
	pageLoadStart();
	var interf = String.format(delFriend_url,friendId,USERID);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			delFriendResult(data,friendId);
		},
		dataType : "json"
	});
}
function delFriendResult(data,friendId)
{
    var resultcode=data.result.resultcode
    if(resultcode == 0)
    {
      showToast("删除好友成功");
      $("#"+friendId).hide()
      temp = temp-1;
      totalRecored = totalRecored-1
      	$("#friendCount").html(totalRecored);
      if(temp==0)
      {
      	$("#managPart").hide();
	    location.reload();
      }
    }
    else
    {
    	 showToast("删除好友失败");
    }
}
