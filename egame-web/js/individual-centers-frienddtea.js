var userid="";
var messageId="";
var startIndex=0;
var pageSize=10;
var friendId="";
function fillData(data) {
	$("#msg_list").html("");
	for(i in data.friendMessageDetail) {
		msg = data.friendMessageDetail[i];
		user = msg.userLittleInfoBean;
		icon = user.icon;
		isOther = msg.isOther;
		if(isOther == "1"){ //别人的消息
			detail = String.format(userDetail_html,user.userId,userid);
			name = user.userName;
			content = analyFaceImage(msg.message);
			str = '<div class="list pos P_tb_10">'
	        	+'<div class="friRight pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt="" width="76" height="76"/></a></div>'
	        	+'<div class="friLeft">'
	        	+'<div class="fri_detailsr pos">'
	        	+'<span class="fri_tl1"></span>'
	        	+'      <span class="fri_tr1"></span>'
	        	+'      <span class="fri_bl1"></span>'
	        	+'      <span class="fri_br1"></span>'
	        	+'      <span class="fri_r_narrow"></span>'
	        	+'      <P class="font_20">%3</P>'
	        	+'      <p class="fri_detailsp font_18">%4</p>'
	        	+'  </div>'
	        	+'</div>'
	        	+'</div>';
			str = String.format(str,detail,icon,name,content);
			$("#msg_list").append(str);
		}else{ //自己的消息
			detail = String.format(userDetail_html,user.userId,userid);
			name = user.userName;
			content = analyFaceImage(msg.message);
			str = '<div class="list pos P_tb_10">'
	        	+'<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt="" width="76" height="76"/></a></div>'
	            +'<div class="listRight">'
	            	+'<div class="fri_detailsl pos">'
	            	+'<span class="fri_tl"></span>'
	            	+'<span class="fri_tr"></span>'
	            	+'<span class="fri_bl"></span>'
	            	+'<span class="fri_br"></span>'
	            	+'<span class="fri_l_narrow"></span>'
	            	+'<P class="font_20">%3</P>'
	            	+'<p class="fri_detailsp font_18">%4</p>'
	            	+'</div>'
	            	+'</div>'
	            	+'</div>';
			str = String.format(str,detail,icon,name,content);
			$("#msg_list").append(str);
		}
	}
	pageLoadFinish();
	
}



function getNewData() {
	var interf = String.format(centers_frienddtea_url,messageId,userid);
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
	});
}

function fresh() {
	messageId=GetQueryString("msgId");
	
	if(messageId == null)  showToast("消息ID为空");
	// messageId="101582";
	userid=GetQueryString("USERID");
	if(userid == null)  showToast("用户ID为空");
	
	friendId = GetQueryString("friendId");
	if(friendId == null)  showToast("好友ID为空");
	// userid="10700142";
	$("#sendMessage").html("");
    var sendMessageStr=
		            '<a onclick="showInputPopup(\'true,,请输入消息内容,发送,sendMessage(%1$s,%2$s,%3$s)_'+friendId+'\')">'
            	 	+'<span class="btu_man_com2 btu_green_com2 btu_senmes_left"></span>'
            	 	+'<span class="btu_mood_right"></span><span class="m_l_20">发消息</span>'
            	    +'</a>';
    $("#sendMessage").html(sendMessageStr);
	getNewData();
}

$(document).ready(function() {
	fresh();
	setInterval(fresh,5000);
});

function sendMessage(messageId,nickname,content)
{
	var encontent = UrlEncode(content);
	var interf = String.format(sendMessage_url,userid,messageId,encontent);
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
