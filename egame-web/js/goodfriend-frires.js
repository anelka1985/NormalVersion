/**
 * @author Administrator
 */
var USERID="";
var gender="";
var age="";
var city="";
var province="";
var hobby="";
var nickName="";
$(document).ready(function() {
	gender = GetQueryString("gender");
    age = GetQueryString("age");
    hobby = GetQueryString("hobby");
    city = decodeURIComponent(GetQueryStringNoEscape("city"));
    province = decodeURIComponent(GetQueryStringNoEscape("province"));
    nickName = decodeURIComponent(GetQueryStringNoEscape("nickName"));
    if(nickName == null)nickName="";
    if(nickName!=null) {
    	printToConsole(nickName);
    }
    if(city!=null) {
    	printToConsole(nickName);
    }
    if(province!=null) {
    	printToConsole(province);
    }
    printToConsole();
    
	USERID = GetQueryString("USERID");
	$("#myfriend_index").attr("href", "goodfriend-myfriend.html?USERID=" + USERID);
	$("#possiblefriend_index").attr("href", "goodfriend-possiblefriend.html?USERID=" + USERID);
	$("#searchfriend_index").attr("href", "goodfriend-findfri.html?USERID=" + USERID);
	$("#invitefriend").attr("href", "goodfriend-invfri.html?USERID=" + USERID);
	getNewData();
});
// 初始化动态详情页面
function getNewData() {
	//var interf = "_goodfriend-frires.html";
	var interf= String.format(finfir_url, USERID, gender, age, encodeURIComponent(province), encodeURIComponent(city), hobby, encodeURIComponent(nickName));
//	alert(interf);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			fillData(data);
		},
		dataType : "json"
	});
}

function fillData (data) {
  var content = "";
  if (data.searchFriendList.length == 0) {
  	content = content 
  	+ '<div class="fri_text">很抱歉,没有搜索到符合您的条件的社区朋友</div>'
    + '<div class="br_color3 p_10">'
        + '<div class="btu_gr pos btu_gr_100"><a href="goodfriend-findfri.html?USERID=' + USERID+'">'
           + ' <span class="btu_gr_l"></span>'
            + '<span class="btu_gr_r"></span>'
            + '重新搜索'
        + '</a></div>'
	+ '</div>';
  	$("#friendList").html(content);
  };
  for (var i=0; i < data.searchFriendList.length; i++) {
    content = content + '<div class="line_1"></div>'
        + '<div class="list pos sym_bg">'
        	+ '<div class="sendmesage" id="friendId_'+i+'" >'
               + ' <div class="but_man btu_green_com pos "><a href="#" onclick="addFriend(' + data.searchFriendList[i].userId +','+ i+')" ><span class="btu_man_com2 btu_green_com2 btu_addfri_left"></span><span class="btu_mood_right"></span><span class="m_l_20">加好友</span></a></div>'
            + '</div>'
        	 + '<div class="P_tb_10 blohi">'
                + '<div class="collLeft pos"><a href="'+String.format(userDetail_html,data.searchFriendList[i].userId,USERID)+'"><span class="head_mask_76c"></span><img src="'+data.searchFriendList[i].icon+'" alt=""/></a></div>'
                + '<div class="listRight">'
                   + ' <P class="font_20 P_tb_5">'+ data.searchFriendList[i].userName ;
                   if (data.searchFriendList[i].onlineStatus == '1') {
                   	content = content + '<span><img src="images/icon_online.png" width="26" height="20" /></span>'
                   };
                   content = content+'</P>'
                    + '<P class="font_18 color_4 m_tb_2 p_r_10">通过搜索匹配</P>'
                + ' </div>'
        	+ '</div>'
       + ' </div>';
  };
  pageLoadFinish();
  $("#friendList").html(content);
}

function addFriend(friendId,i)
{
	pageLoadStart();
	
	var interf=String.format(addFriend_Request_url,friendId,USERID);
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
		$("#friendId_"+i).attr("onclick","#");
		//成功
		showToast(data.result.resultmsg);
	}
	else
	{
		//失败
		showToast(data.result.resultmsg);
	}
}
