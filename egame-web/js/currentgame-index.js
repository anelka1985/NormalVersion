var userId;
var gameId;

var links;

$(document).ready(function(){
	init();
});

function init(){
	userId = GetQueryString("USERID");
	gameId = GetQueryString("gameId");
	
	links = [
	     String.format(indexSDKGame_url,userId,gameId),
	     String.format(indexButtonlist_url,userId,gameId),
	     String.format(indexJoinUserList_url,userId,gameId),
	     String.format(indexdaRRenRankAllList_url,userId,gameId),
	     String.format(indexSubjectList_url,userId,gameId)		
	];
	
	getLinks();
	pageLoadStart();
};



var jsons = {}

function getLinks(){
	getData0(links[0]);
	getData1(links[1]);
	getData2(links[2]);
	getData3(links[3]);
	getData4(links[4]);
}

function getData0(interf){
	
 	printToConsole(interf);
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			pageLoadFinish();	
			setDataAndShow0(data)
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		pageLoadFinish();	
		// 把LOADING 换成加载出错提示语
		$("#body").empty().append('<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>');
	});
}
function getData1(interf){
 	printToConsole(interf);
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			setDataAndShow1(data)
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		showToast('异常，数据出了些小问题，请稍后再试!');
	});
}
function getData2(interf){
 	printToConsole(interf);
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			setDataAndShow2(data)
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		showToast('异常，数据出了些小问题，请稍后再试!');
	});
}
function getData3(interf){
 	printToConsole(interf);
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			pageLoadFinish();	
			setDataAndShow3(data)
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		showToast('异常，数据出了些小问题，请稍后再试!');
	});
}
function getData4(interf){
 	printToConsole(interf);
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			pageLoadFinish();	
			setDataAndShow4(data)
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		showToast('异常，数据出了些小问题，请稍后再试!');
	});
}
function setDataAndShow0(json){
	
	if(json.result.resultcode == 1){
		pageLoadFinish();
		showToast("网络异常，请稍后再试");
		$("body").html("");
		return;
	}

	var g_c = json.gameInfo;
	var game_detail = $("#gamedetail");
	$("#avader_icon").attr("src",g_c.gamePic);
	$("#titleIn").html(g_c.gameName);
	game_detail.show().find("h2").parent().find("span.color_3").html(json.myBestScore);
	$("#gameHome").attr("href",'gamedetails.html?gameId='+gameId+'&USERID='+userId +'&' + getHeader());
	$("#mood_num").show();
	setPkUserName(g_c.gameName);
	
	var starUrl = String.format(currentGameEva,userId,gameId,encodeURI(json.userName),encodeURI(g_c.gameName)) ;
	if(json.gameCommentFlag == 0){
		$("#starEva").attr("href",starUrl);
	}else if(json.gameCommentFlag == 1){
		$('#starHtml').html('您已经评价');
	}
	
	//star score
	var gameInfo_score = g_c.score;
	var score_width = gameInfo_score * 20 + 'px';
	$("#starScore").css('width',score_width);

	
}
function setDataAndShow1(json){
	var g_button = json.buttonList;
	var g_button_str = ["c","a","d","p"];
	for(var i=0;i<(g_button.length-1);i++){
		if(g_button[i].displayflag)	{$("#game_" + g_button_str[i]).html(g_button[i].msgCount);}else{ $("#game_" + g_button_str[i]).parent().hide();}
	}
	
	if(!g_button[3].displayflag){
		$("#linkPk").parent().parent().hide();		
	}
	
	if(g_button[0].msgCount <= 0){
		$("#game_c").hide();
	}
	
	if(g_button[1].msgCount <= 0){
		$("#game_a").hide();
	}

		if(g_button[2].msgCount == -1) {
			$("#game_d").html("").addClass("icon_game3").removeClass("icon_game2").html("");
		}else if(g_button[2].msgCount == 1){
			$("#game_d").html("").addClass("icon_game2").removeClass("icon_game3").html("");
		}else{
			$("#game_d").html("").removeClass("icon_game2").removeClass("icon_game3");
		}
	
	
	$('#linkCircleIndex').attr("href",String.format(currentGameCircleIndex_html,userId,gameId));
	$('#idAchieveIndex').attr("href",String.format(currentGameAchievement_html,userId,gameId));
	$('#linkDaren').attr("href",String.format(darenListHtml_html,userId,1,0,gameId,10));
	$('#linkPk').attr("href",String.format(pkIndexHtml_html,userId,gameId));
	
}
function setDataAndShow2(json){
	
	//玩家的拼装
	if(json.joinUserList.length > 0){
	
		var strArray = [];
		for(var i = 0; i<json.joinUserList.length; i++){ 
			var online = "";
			if(json.joinUserList[i].onlineStatus == 1){
				online = '<span class="online online_76">';
			}
			var str	= '<li class="pos"><a href="javascript:showDetail(\'friendId\','+ json.joinUserList[i].userId +')"> <span class="head_mask_76"></span><img width="76" height="76" src="'+json.joinUserList[i].icon+'" alt=""/><P>'+ online + json.joinUserList[i].userName + '</span></P></a></li>';
			strArray.push(str);
		}
		$("#addin_just").show().find("ul").append(strArray.join(''));
		
		$("#linkMorePlayer").attr("href",String.format(moreplayers,userId,15,0,0,gameId));	
	}else{
		$("#linkMorePlayer").attr("href",String.format(currentGameShare_html,userId,undefined));
		$("#linkMorePlayer").html(' <span class="btu_g_l"></span><span class="btu_g_r"></span>邀请玩家');
	}
	
}
function setDataAndShow3(json){
	
	//达人榜
	var darenlist = json.daRRenRankList;
	if(darenlist.length>0){
		var classArray = ['j','y','t'];
		var darenStr = '';
		var myDarenStr = '';
		for(var i=0;darenlist[i];i++){
			darenStr += '<div class="P_tb_10 blohi"><div class="listLeft"><img src="images/icon_'+ classArray[i] +'.png" width="58" height="58" /></div><div class="listRight font_20 color_6"><a href="javascript:showDetail(\'friendId\','+ darenlist[i].reachId +')"><span class="float_l font_lin58">'+darenlist[i].reachName+'</span></a><span class=" float_r font_lin58">'+darenlist[i].reachScore+'</span></div></div><div class="line_1"></div>';
		}
		if(json.myBestScore!=null&&json.myRanking!=null&&json.myRanking>3){
			myDarenStr = '<div class="P_tb_10 blohi bg_color3""><div class="listLeft"><img src="images/icon_more.png" alt="more" width="58" height="58" /></div><div class="listRight font_20 color_7"><span class="float_l font_lin58">我 ('+json.myRanking+'名)</span><span class=" float_r font_lin58">'+json.myBestScore+'分</span></div></div><div class="line_1"></div>';
		}
		$("#daren_list").html('<div class="nav_title">达人榜</div>'+darenStr+myDarenStr+' <div class="btu_g pos btu_game_mar"><a href="currentgame-darenlist.html?USERID='+userId+'&gameId='+gameId+'"><span class="btu_g_l"></span><span class="btu_g_r"></span>查看全站达人榜</a></div>');
	}else{
		$("#daren_list").html('<div class="nav_title">达人榜</div><div class="btu_g pos btu_game_mar"> <span class="btu_g_l"></span><span class="btu_g_r"></span><a href="currentgame-darenlist.html?USERID='+userId+'&gameId='+gameId+'">暂时没有分数，快去游戏一下吧</a></div>');	
	}
	
	
}
function setDataAndShow4(json){
	//游戏圈
	var gamecircledata= json.subjectList;
	if(gamecircledata.length>0){
		var strArray = [];
		for(var i = 0; i<gamecircledata.length; i++){
			var str = 	'<div class="list pos p_10"><a href="currentgame-circle-detail.html?USERID='+userId+'&subjectId='+gamecircledata[i].subjectId +'" class="P_tb_10 blohi"><span class="font_20 m_t_11">'+gamecircledata[i].subjectTittle +'</span><div class="narrow_r"></div></a></div><div class="line_1"></div>';
			strArray.push(str);
		}	
		$("#current_circle").find("div.nav_title").after(strArray.join(''));
		
		$("#findMoreCircle").attr("href",String.format(currentGameCircleIndex_html,userId,gameId))
	}else{
		$("#current_circle").html(' <div class="nav_title">游戏圈</div><div class="btu_g pos btu_game_mar"> <span class="btu_g_l"></span><span class="btu_g_r"></span><a href="currentgame-keynote.html?USERID='+userId+'">暂时没有游戏圈，你去发布一个吧</a></div>');
	}
	
	$("#shareFirends").attr("href",String.format(currentGameShare_html,userId,"undefined"));
	//moregame-developers
	$("#moregame_developers").attr("href",String.format(gameListDeveloper_html,userId));
	$("#moregame_developers").html('<span class="btu_gr_l"></span><span class="btu_gr_r"></span>开发者更多游戏');
	
	
	titleRoll();
}
