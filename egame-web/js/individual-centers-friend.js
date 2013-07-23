var USERID="";
var startIndex=0;
var pageSize=10;
var friendMsgList;
function fillData(data) {
	if(data.friendTotalRecord == 0){
		$("#friend_msg").hide();
	}else{
		$("#friend_msg").html("("+data.friendTotalRecord+")");
	}
	if(data.systemTotalRecord == 0){
		$("#sys_msg").hide();
	}else{
		$("#sys_msg").html("("+data.systemTotalRecord+")");
	}
	
	$("#form1").html("");
	friendMsgList = data.friendMessageList;
	var quanxuanStr = '全选<span class="color_1">'+friendMsgList.length+'</span>';
	$("#quanxuan").html(quanxuanStr);
	var msg_count_h3_str = '共有<span id="msg_count" class="color_1">'+friendMsgList.length+'</span>条好友消息';
	$("#msg_count_h3").html(msg_count_h3_str);
	if(friendMsgList.length == 0)
	{
		var bodyContent = '<div class="fri_text">您还没有好友消息</div>';
		$("#bodyContent").html(bodyContent);
	}
	if(friendMsgList.length == 0)
	{
		$("#manage").hide();
	}
	for(i in friendMsgList) {
		p = friendMsgList[i];
		user = p.userLittleInfoBean;
		online = "";
		if(user.onlineStatus == "1") {
			online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
		}
		
		detail = String.format(userDetail_html,user.userId,USERID);
		isnew = "";
		if(p.feedback != 1){
			isnew = '</span><span class="font_16 color_5">NEW</span>';
		}
        friendDetail =String.format(centers_frienddtea_html,p.messageId,USERID,user.userId);
        message = p.message;
        if(message.length > 30){
        	message = message.substring(0,30);
        	message = message + '...';
        }
		str = '<div class="coll_bg P_tb_10">'
                +'<input type="checkbox" name="no" id="no'+i+'"  class="float_l fri_deinput2" onchange="check()" />'
                +'<label for="no"'+i+'>'
                    +'<div class="list pos font_w">'
                        +'<div class="collLeft pos"><a href="'+detail+'"><span class="head_mask_76c"></span><img src="'+user.icon+'" alt=""/></a></div>'
                        +'<div class="listRight"><a href="'+friendDetail+'">'
                            +'<P class="font_20 m_tb_2">'+ user.userName + online +' <span class="font_18 color_4">（'+p.sendTime+'）'+isnew+'</P>'
                            +'<P class="font_18 color_4 m_tb_2 p_r_30">'+analyFaceImage(message)+'</P>'
                         +'</a>'
                         +'</div>'
                         +'<div class="narrow_r"></div>'
                    +'</div>'
                 +'</label>'
             +'</div>'
             +'<div class="line_1"></div>'
		$("#form1").append(str);
		$("#del").show();
		
	}
	showCount();
	pageLoadFinish();
}

function getNewData() {
    var interf=String.format(friendMessage_url,USERID);
    printToConsole(interf);
    // interf = "_FriendMsgList.json";
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			$("#bodyContent").show();
			fillData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		$("#form1").html("");
		// 把LOADING 换成加载出错提示语
		err_str = '<p style="text-align: center">数据加载异常！</p>';
		$("#form1").html(err_str);
		pageLoadFinish();
	});
}

function fresh() {
	USERID = GetQueryString("USERID");
	// USERID = "10700142";
	if(USERID==null) showToast("用户ID为空");
	$("#friendMessage").attr("href", String.format(centers_friendnd_html,USERID));
	$("#systemMessage").attr("href", String.format(centers_symedit2_html,USERID));
	$("#del").hide();
	$("#bodyContent").hide();
	getNewData();
}


$(document).ready(function() {
	fresh();
});

//全选取消按钮
function quanxuan() {
	if($("#select_all").attr("checked")){ //选中状态
		$("[name='no']").attr("checked", 'true');
	}else{
		$("[name='no']").removeAttr("checked");
	}
}

function check(){
	var count=0;
	for(var i = 0; i < friendMsgList.length; i++) {
		if($("#no" + i).attr("checked")) {
			count++;
		};
	};
//	alert(count);
//	alert(friendMsgList.length);
	if(count == friendMsgList.length && count != 0){
		$("#select_all").attr("checked",'true');
	}else{
		$("#select_all").removeAttr("checked");
	}
}

function showManager(){
	$("[name='no']").show();
	$("#del").show();
	$("#manager").show();
	$("#count").hide();
	$("[name='no']").removeAttr("checked");
}

function showCount(){
	$("[name='no']").hide();
	$("#del").hide();
	$("#manager").hide();
	$("#count").show();
}

//全选取消按钮
function quanxuan() {
	if($("#select_all").attr("checked")){ //选中状态
		$("[name='no']").attr("checked", 'true');
	}else{
		$("[name='no']").removeAttr("checked");
	}
}

//删除消息
function delMessage(){
	var arr = "";
	var count=0;
	for(var i = 0; i < friendMsgList.length; i++) {
		if($("#no" + i).attr("checked")) {
			count++;
			msg = friendMsgList[i];
			if(arr == ""){
				arr = arr + msg.userLittleInfoBean.userId;
			}else{
				arr = arr + "," + msg.userLittleInfoBean.userId;
			}
		};
	};
	
	if(count ==0 )
	{
		showToast("请选择你要删除的消息！");
		return;
	}
	var interf = String.format(delFriMessage_url,arr,USERID);
	pageLoadStart();
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			//alert(data.result.resultcode);
			if(data.result.resultcode == '0'){//成功
				showToast(data.result.resultmsg);
				fresh();
			}else{ //失败
				showToast(data.result.resultmsg);
			}
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		pageLoadFinish();
		showToast("操作失败");
	});
}
