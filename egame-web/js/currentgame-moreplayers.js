var userID;
var gameId;
//每页数量
var pageSize;

//开始页数
var startIndex;

//总条数
var totalRecord="";
var type;

$(document).ready(function(){
	$("#page").hide();
	
	myFriends();//默认我的好友
})

function myFriends(){
	startIndex = GetQueryString("startIndex");
	pageSize = GetQueryString("pageSize");
	userID = GetQueryString("USERID");
	type = GetQueryString("type");
	gameId = GetQueryString("gameId");
	
	if(userID == null) userID = 81894508;
	if(gameId == null) gameId = 222042;
	pageSize = 999;
	if(startIndex == null) startIndex = 0;
	if(type == null) type = 1;//默认好友列表
	
	//tab的切换
	var friendsplayer = $("#friendsplayer"),allplayer = $("#allplayer")
	if(type == 1){//好友
		friendsplayer.addClass("title_pk_select").removeClass("title_pk_nor").children("a").attr("href",String.format(moreplayers,userID,pageSize,0,1,gameId));
		allplayer.addClass("title_pk_nor").removeClass("title_pk_select").children("a").attr("href",String.format(moreplayers,userID,pageSize,0,0,gameId));
		friendsplayer.children("span").addClass("head_mask_pknarrow");
		allplayer.children("span").removeClass("head_mask_pknarrow");
	}
	if(type == 0){//所有人
		allplayer.addClass("title_pk_select").removeClass("title_pk_nor").children("a").attr("href",String.format(moreplayers,userID,pageSize,0,0,gameId));
		friendsplayer.addClass("title_pk_nor").removeClass("title_pk_select").children("a").attr("href",String.format(moreplayers,userID,pageSize,0,1,gameId));
		allplayer.children("span").addClass("head_mask_pknarrow");
		friendsplayer.children("span").removeClass("head_mask_pknarrow");
	}
	
	var interf = String.format(moreplayers_url,userID,pageSize,startIndex,type,gameId);
	
	printToConsole(interf);
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			pageLoadFinish();	
			showDatafri(data);
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		pageLoadFinish();	
		// 把LOADING 换成加载出错提示语
		$("#body").append("<p style=\"text-align: center\">数据加载异常！</p>");
	});	
}

function showDatafri(data){
	var json = data.playList;
	if(json.length <= 0){
		if(data.gangProperties == 1){
			$(document.body).append('<div class="text_pk" id="nofriend">还没有朋友玩过此款游，邀请朋友一起来玩哦 ！<div class="btu_gr pos m_t_20"><a href="'+ String.format(currentGameShare_html,userID,"undefined") + '"><span class="btu_gr_l"></span><span class="btu_gr_r"></span>邀请朋友</a></div></div>');
		}else{
			$(document.body).append('<div class="fri_text">您还没有好友哦，立即去找朋友吧</div><div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="'+String.format(findfri_html,userID)+'"><span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>');
		}
	}else{
		if($("#nofriend")){$("#nofriend").hide()}
		$("#myfriends_list").show().find("ul").empty().append(setdata(json));
	}
	var pageHome = $("#pageHome"),pageUp = $("#pageUp"),pageDown = $("#pageDown"),pageEnd = $("#pageEnd");
	startIndex = parseInt(data.startIndex);
	pageSize = parseInt(data.pageSize);
	totalRecord = parseInt(data.totalRecord);
	var totalPage = Math.ceil(totalRecord/pageSize);//总页数
	var page = Math.ceil(startIndex/pageSize);//当前页
	if (page == 0)
	{
		page =1;
	};
	//只有一页，分页隐藏
	if(startIndex ==0 && (totalRecord <pageSize || totalRecord ==pageSize)){
		$("#page").hide();
	}else if(page == totalPage){//最后一页
		$("#page").show();
		setBtnUnus(pageDown);
		setBtnUnus(pageEnd);
		setBtnUse(pageUp,String.format(moreplayers,userID,pageSize,Math.abs(startIndex-pageSize),type,gameId));
		setBtnUse(pageHome,String.format(moreplayers));
	}else if(startIndex ==0 &&totalRecord>pageSize){//第一页
		setBtnUnus(pageUp);
		setBtnUnus(pageHome);
		setBtnUse(pageDown,String.format(moreplayers,pageSize,userIDstartIndex+pageSize,type,gameId));
		setBtnUse(pageEnd,String.format(moreplayers,pageSize,userIDtotalRecord,pageSize,type,gameId));
	}else{
		$("#page").show();
		setBtnUse(pageDown,String.format(moreplayers,userID,pageSize,startIndex+pageSize,type,gameId));
		setBtnUse(pageEnd,String.format(moreplayers,userID,pageSize,totalRecord,userID,gameId));
		setBtnUse(pageUp,String.format(moreplayers,userID,pageSize,Math.abs(startIndex-pageSize),type,gameId));
		setBtnUse(pageHome,String.format(moreplayers));
	}
}

function setBtnUse(obj,href){//使按钮变绿可点
	$(obj).attr("href",href).parent().addClass("btu_green_s").removeClass("btu_gra_s btu_page_com").find("span.btu_man_com2").addClass("btu_green_s_com btu_green_s_left").removeClass("btu_gra_s_com btu_gra_s_left").siblings("span").addClass("btu_green_s_right").removeClass("btu_gra_s_right");	
}
function setBtnUnus(obj){//使按钮变灰不可点
	$(obj).attr("href","").parent().addClass("btu_gra_s btu_page_com").removeClass("btu_green_s").find("span.btu_man_com2").addClass("btu_gra_s_com btu_gra_s_left").removeClass("btu_green_s_com btu_green_s_left").siblings("span").addClass("btu_gra_s_right").removeClass("btu_green_s_right");	
}




function setdata(json){
	var strArray = [];
	for(var i = 0; i<json.length; i++){
		var str = '<li class="pos"><a href="javascript:showDetail(\'friendId\',%1)"> <span class="head_mask_76"></span><img width="76" height="76" src="%2" alt=""/><P>%3%4</span></P></a></li>';
		var online = "";
		if(json[i].onlineStatus == 1){
			online = '<span class="online online_76">';
		}
		str = String.format(str,json[i].userId,json[i].icon,online,json[i].userName);
		strArray.push(str);
	}
	return strArray.join('');
}

