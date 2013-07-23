var userid = "";
var systemMessageList;
function fillData(data) {
	pageLoadFinish();
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
	
	
	$("#msg_list").html("");
	systemMessageList = data.systemAllMessageList;
	$("#msg_count").html(systemMessageList.length);
	$("#all_msg_count").html(systemMessageList.length);
	
	if(systemMessageList.length == 0)
	{
		var bodyContent = '<div class="fri_text">您还没有系统消息</div>';
		$("#bodyContent").html(bodyContent);
	}
	for(i in systemMessageList) {
		p = data.systemAllMessageList[i];
		if(p.messageKey == 'beAddFriend') {// 被人加为好友,需要同意或拒绝
			msg = p.beAddFriendMessage;
			icon = msg.sendIcon;
			name = msg.sender;
			online = "";
			if(msg.sendOnlinestatus == "1") {
				online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
			}
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			detail = String.format(userDetail_html,msg.sendId,userid);
			content = '<P class="font_18 color_4 m_tb_2">'+msg.message+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	'<div id="right_'+p.messageId+'" class="symRight pos">'+
                    isAgreeAddFriend(p.feedback)+
                  '</div>'+
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt=""/></a></div>'+
                                '<div class="listRight marginRight">'+
                                    '%3'+
                                    '%4'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,detail,icon,nameline,content,msg.sendId,p.messageId);
			$("#msg_list").append(str);
		}
		else if(p.messageKey == 'greet'){ //动态
			msg = p.greetMessage;
			icon = msg.sendIcon;
			name = msg.sender;
			online = "";
			if(msg.sendOnlinestatus == "1") {
				online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
			}
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			// detail = 'goodfriend-deta.html?USER=' + msg.sendUserId;
			detail = String.format(userDetail_html,msg.sendId,userid);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt=""/></a></div>'+
                                '<div class="listRight marginRight">'+
                                    '%3'+
                                    '%4'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,detail,icon,nameline,content);
			$("#msg_list").append(str);
		}
		else if(p.messageKey == 'buddyFeedback'){ //加好友反馈信息
			msg = p.buddyFeedbackMessage;
			icon = msg.sendIcon;
			name = msg.sender;
			online = "";
			if(msg.sendOnlinestatus == "1") {
				online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
			}
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			// detail = 'goodfriend-deta.html?USER=' + msg.sendUserId;
			detail = String.format(userDetail_html,msg.sendId,userid);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt=""/></a></div>'+
                                '<div class="listRight marginRight">'+
                                    '%3'+
                                    '%4'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,detail,icon,nameline,content);
			$("#msg_list").append(str);
		}
		else if(p.messageKey == 'shareGame'){ //分享游戏
			msg = p.shareGameMessage;
			icon = msg.sendIcon;
			name = msg.sender;
			online = "";
			if(msg.sendOnlinestatus == "1") {
				online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
			}
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			// detail = 'goodfriend-deta.html?USER=' + msg.friendUserId;
			detail = String.format(userDetail_html,msg.sendId,userid);
			content = '<P class="font_18 color_4 m_tb_2 p_r_10">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt=""/></a></div>'+
                                '<div class="listRight marginRight">'+
                                    '%3'+
                                    '%4'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,detail,icon,nameline,content);
			$("#msg_list").append(str);
			
		} else if(p.messageKey == 'prize'){
			msg = p.prizeMessage;
			icon = "images/headpic1.png";
			//name = "系统管理员";
			name = msg.sender;
			online = "";
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><span class="head_mask_76c"></span><img src="%1" alt=""/></div>'+
                                '<div class="listRight marginRight">'+
                                    '%2'+
                                    '%3'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,icon,nameline,content);
			$("#msg_list").append(str);
		} else if(p.messageKey == 'dynamic'){ // 动态回复
			msg = p.dynamicCommentMessage;
			icon = "images/headpic1.png";
			//name = "系统管理员";
			name = msg.sender;
			online = "";
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><span class="head_mask_76c"></span><img src="%1" alt=""/></div>'+
                                '<div class="listRight marginRight">'+
                                    '%2'+
                                    '%3'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,icon,nameline,content);
			$("#msg_list").append(str);
		} else if(p.messageKey == 'system'){ //系统消息
			msg = p.systemMessage;
			icon = msg.sendIcon;
			//name = "系统管理员";
			name = msg.sender;
			online = "";
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><span class="head_mask_76c"></span><img src="%1" alt=""/></div>'+
                                '<div class="listRight marginRight">'+
                                    '%2'+
                                    '%3'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,icon,nameline,content);
			$("#msg_list").append(str);
		}else if(p.messageKey == 'pkInvite'){//PK邀请
			msg = p.pkinviteMessage;
			icon = msg.sendIcon;
			name = msg.sender;
			online = "";
			if(msg.sendOnlinestatus == "1") {
				online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
			}
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			// detail = 'goodfriend-deta.html?USER=' + msg.inviteUserId;
			detail = String.format(userDetail_html,msg.sendId,userid);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	'<div id="right_'+p.messageId+'" class="symRight pos">'+
                   // '<div class="btu_g_com btu_g_mar1 pos "><a href=""><span class="btu_man_com2 btu_green_com2 btu_g_left"></span><span class="btu_g_right"></span>同 意</a></div>'+
                    '<div class="btu_green_com btu_g_mar2 pos "><a href=""><span class="btu_man_com2 btu_green_com2 btu_man_left"></span><span class="btu_mood_right"></span>现在没空</a></div>'+
                  '</div>'+
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt=""/></a></div>'+
                                '<div class="listRight marginRight">'+
                                    '%3'+
                                    '%4'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,detail,icon,nameline,content);
			$("#msg_list").append(str);
		}else if(p.messageKey == 'pkResult'){//PK结果
			msg = p.pkresultMessage;
			icon = "images/headpic1.png";
			name = msg.sender;
			online = "";
			if(msg.sendsendOnlinestatus == "1") {
				online = '<span><img src="images/icon_online.png" width="26" height="20" /></span>';
			}
			time = msg.sendTime;
			isnew =  isNew(p.feedback);
			// detail = 'goodfriend-deta.html?USER=' + msg.otherUserId;
			detail = String.format(userDetail_html,msg.sendId,userid);
			content = '<P class="font_18 color_4 m_tb_2">'+analyGreetImage(msg.message)+'</P>';
			nameline = '<P class="font_20 m_tb_2">%1%2 <span class="font_18 color_4">（%3）%4</P>';
			nameline = String.format(nameline,name,online,time,isnew);
			str = '<div class="line_1"></div>'+
        		  '<div class="list pos P_tb_10 sym_bg">'+
                	
                  '<div class="friLeft">'+
                    '<form id="form1" method="post" action="">'+
                        '<input type="checkbox" name="no" id="no'+i+'"  class="float_l sym_deinput" onchange="check()"/>'+
                        '<label for="no'+i+'">'+
                            '<div class="list pos font_w">'+
                                '<div class="collLeft pos"><a href="%1"><span class="head_mask_76c"></span><img src="%2" alt=""/></a></div>'+
                                '<div class="listRight marginRight">'+
                                    '%3'+
                                    '%4'+
                                 '</div>'+
                            '</div>'+
                         '</label>'+
                	'</form>'+
				  '</div>'+
			    '</div>';
        	str = String.format(str,detail,icon,nameline,content);
			$("#msg_list").append(str);
		}
	}
}

function getNewData() {

	var interf = String.format(systemMessage_url,userid);
	printToConsole("sys-->"+interf);
	// interf="_SystemMsgList.json";
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			fillData(data);
			hideManager();
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		// 把LOADING 换成加载出错提示语
		pageLoadFinish();
		showToast("加载异常");
		// $("#visit_total_count_loading").html(err_str);
	});
	//  直接用 json
	//$.getJSON('_data_individual-centers1.html', function(data){
	// fillData(data);
	//});
}

function fresh() {
	userid = GetQueryString("USERID");
	// userid = "10700142";
	if(userid == null || userid == ""){
		showToast("USERID IS NULL");
	}else{
		$("#friendMessage").attr("href", "individual-centers-friendnd.html?USERID="+userid);
		$("#systemMessage").attr("href", "individual-centers-symedit2.html?USERID="+userid);
		getNewData();
	}
}


$(document).ready(function() {
	$("#del").hide();
	$("#all_head").hide();
	$("#total_head").hide();
	// setTimeout("fresh()", 1000);
	fresh();
});

function showManager(){
	$("[name='no']").show();
	$("#del").show();
	$("#all_head").show();
	$("#total_head").hide();
}

function hideManager(){
	$("[name='no']").hide();
	$("#del").hide();
	$("#all_head").hide();
	$("#total_head").show();
}

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
	for(var i = 0; i < systemMessageList.length; i++) {
		if($("#no" + i).attr("checked")) {
			count++;
		};
	};

	if(count == systemMessageList.length && count != 0){
		$("#select_all").attr("checked",'true');
	}else{
		$("#select_all").removeAttr("checked");
	}
}

function addFriend(friendid,agree,messageId){
	var interf = String.format(agreeFriendInvite_url,friendid,userid,agree,messageId);
	printToConsole(interf);
	pageLoadStart();
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			//alert(data.result.resultcode);
			pageLoadFinish();
			if(data.result.resultcode == '0'){//成功
				if(agree == '0'){
					$("#right_" + messageId).html('<P class="color_3">您已同意加为好友</P>');
				}else{
					$("#right_" + messageId).html('<P class="color_3">您已拒绝加为好友</P>');
				}
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


function acceptPk(friendid,agree,messageId){
	var interf = String.format(agreeFriendInvite_url,friendid,userid,agree);
//	showToast(interf);
	pageLoadStart();
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			//alert(data.result.resultcode);
			if(data.result.resultcode == '0'){//成功
				if(agree == '0'){
					$("#right_" + messageId).html('<P class="color_3">已添加</P>');
				}else{
					$("#right_" + messageId).html('<P class="color_3">已拒绝</P>');
				}
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

//删除消息
function delMessage(){
	var arr = "";
	var count=0;
	for(var i = 0; i < systemMessageList.length; i++) {
		if($("#no" + i).attr("checked")) {
			count++;
			msg = systemMessageList[i];
			if(arr == ""){
				arr = arr + msg.messageId;
			}else{
				arr = arr + "," + msg.messageId;
			}
		};
	};
	
	if(count ==0 )
	{
		showToast("请选择你要删除的消息！");
		return;
	}
	var interf = String.format(delMessage_url,arr,userid);
	printToConsole(interf);
	pageLoadStart();
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

function isNew(feedback)
{
	if(feedback == 2)
	{
		return '</span><span class="font_16 color_5">NEW</span>';
	}
	else
	{
		return "";
	}
	
}

function isAgreeAddFriend(feedback)
{
	if(feedback != 3 && feedback != 4)
	{
		return '<div class="btu_g_com btu_g_mar1 pos "><a onclick="addFriend(%5,0,%6)"><span class="btu_man_com2 btu_green_com2 btu_g_left"></span><span class="btu_g_right"></span>同 意</a></div>'+
                    '<div class="btu_green_com btu_g_mar2 pos "><a onclick="addFriend(%5,1,%6)"><span class="btu_man_com2 btu_green_com2 btu_man_left"></span><span class="btu_mood_right"></span>拒 绝</a></div>';
	}
	else
	{
	  return "";
	}
}
