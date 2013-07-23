/**
 * @author 姚朋朋
 *
 */

var phonebook=new Array();
var isquanxuan =0;
$(document).ready(function() {
	init();
});


function init() {
	USERID = GetQueryString("USERID");
	$("#myfriend_index").attr("href", "goodfriend-myfriend.html?USERID=" + USERID);
	$("#possiblefriend_index").attr("href", "goodfriend-possiblefriend.html?USERID=" + USERID);
	$("#searchfriend_index").attr("href", "goodfriend-findfri.html?USERID=" + USERID);
	$("#invitefriend").attr("href", "goodfriend-invfri.html?USERID=" + USERID);
	//phonebook = [["aa"],["bb"]],[["cc"],["dd"]];
	//$("#phonebook").html("test:"+phonebook[0]);
	getContacts();
}

function fillData() 
{
	var content = "";
	//alert(phonebook.length);
	if(1 == phonebook.length) 
	{
		$("#contactcount").hide();
		$("#yaoqingdiv").hide();
		var findFriend = String.format(findfri_html, USERID);
		content = '<div class="fri_text">你的电话簿空空如也</div>' + '<div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="' + findFriend + '">' + ' <span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>';
	} 
	else 
	{
		$("#callcount").html(phonebook.length / 2);
		var j = 0;
		for(var i = 0; i < phonebook.length; i++) 
		{
			 //content = content + '<div class="blohi P_tb_10" onclick="setCheck(no' + j + ')" ontouchstart="this.style.backgroundColor=\'#D2D2D2\'" ontouchend="this.style.backgroundColor=\'#ebebeb\'">' + 
			 //'<label for="no' + j + '">' + '<div class="float_l P_lr_10"><table class="font_18 color_2 font_w m_tb_10"><tr><td class="wid_155">' 
			 //+ phonebook[i] + '</td><td class="wid_140 color_4">' + phonebook[++i] + '</td></tr></table></div></label> <input type="checkbox" name="no" id="no' + j + '"  class="float_r m_10" />' + ' </div>' + ' <div class="line_1"></div>'
				content = content + '<div class="line_1"></div>'
			                  + '<div class="blohi P_tb_10" onclick="setCheck(\'noa'+j+'\',\'div_noa'+j+'\')" ontouchstart="this.style.backgroundColor=\'#D2D2D2\'" ontouchend="this.style.backgroundColor=\'#ebebeb\'">'
			                  + '<label class="blohi">'
			                  + '<div class="float_l P_lr_10"><table class="font_18 color_2 font_w m_tb_10"><tr><td class="wid_155">' 
			                  + phonebook[i]+'</td><td class="wid_140 color_4">'+phonebook[++i]+'</td></tr></table></div>'
			                  + '<div class="icon_checkbox1 float_r m_tr_810"  id="div_noa'+j+'" name="checked"><input type="checkbox" name="noa" id="noa'+j+'" class="hide" /></div>'
			                  +'</label></div>';
			j++;
		};
	}
	$("#phonebook").html(content);
}

//发送邀请短信
function sendInvSms() {
	var smsStr = "";

	var count = 0;
	for(var i = 0; i < phonebook.length; i++) {
		if($("#noa" + i).attr("checked")) {
			//smslist.push(phonebook[i][0]);
			//smsStr = smsStr+','+phonebook[2*i]+','+phonebook[2*i+1];
			smsStr = smsStr + ',' + phonebook[2 * i + 1];
			count++;
			if(smsStr.charAt(0) == ",") {
				smsStr = smsStr.substring(1);
			}
		};
	};
	if(count == 0) {
		showToast("您没有选择好友，请选择好友！");
	} else {
		sendSms(smsStr, '我正在玩爱游戏社区http://wapgame.189.cn，还挺有意思哦，还等什么啊，快加入来找我吧。');
	}
}

function setCheck(checkBoxId, div_noaId) {
	var check = $("#" + checkBoxId).attr("checked");
	if(check == 'checked') {
		$("#" + checkBoxId).attr("checked", false);
		$("#" + div_noaId).attr("class","");
		$("#" + div_noaId).addClass("icon_checkbox1 float_r m_tr_810");
	} else {
		$("#" + checkBoxId).attr("checked", true);
		$("#" + div_noaId).attr("class","");
		$("#" + div_noaId).addClass("icon_checkbox2 float_r m_tr_810");
	}
}

//全选取消按钮
function quanxuan() {
	if(isquanxuan == 0)
	{
		$("#quanxuanCheckBox").attr("class","");
		$("#quanxuanCheckBox").addClass("icon_checkbox2 float_r");
		$("[name='noa']").attr("checked", 'true');
		$("[name='checked']").attr("class","");
		$("[name='checked']").addClass("icon_checkbox2 float_r m_tr_810");
		
		isquanxuan = 1;
	}
	else
	{
		$("#quanxuanCheckBox").attr("class","");
		$("#quanxuanCheckBox").addClass("icon_checkbox1 float_r");
		$("[name='noa']").removeAttr("checked");
		$("[name='checked']").attr("class","");
		$("[name='checked']").addClass("icon_checkbox1 float_r m_tr_810");
	    isquanxuan = 0;
	}

}

function setContacts(contacts)
{
    pageLoadFinish();
	if(contacts== null || contacts == "" )
	{
		$("#contactcount").hide();
		$("#yaoqingdiv").hide();
		var findFriend = String.format(findfri_html, USERID);
		var noContent = '<div class="fri_text">你的电话簿空空如也</div>' + '<div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="' + findFriend + '">' + ' <span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>';
	    pageLoadFinish();
	    $("#phonebook").html(noContent);
	}
	else
	{
	  phonebook = contacts.split(',');
	  fillData();
	}
}

