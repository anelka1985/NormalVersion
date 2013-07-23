var gameId;
var userId;

$(document).ready(function(){
	init();
})

function init(){
	gameId = GetQueryString("gameId");
	userId = GetQueryString("userId");
	if(gameId == null) gameId = 100300;
	if(userId == null) userId = 100300;
	var interf = String.format(moregame,gameId,userId);
	printToConsole(interf);
	$("#download").click(function(){
			downloadGame('client',gameId);
		});
	$("#downloadofweb").click(function(){
			downloadGame('web',gameId);
		});
	var request = $.ajax({//获取json数据
		url : interf,
		type : "GET",
		success : function(data){
			pageLoadFinish();	
			setDataAndShow(data);
		},
		dataType:'json'
	})
	request.fail(function(textStatus) {
		pageLoadFinish();	
		// 把LOADING 换成加载出错提示语
		$("#body").append("<p style=\"text-align: center\">数据加载异常！</p>");
	});
}
function setDataAndShow(data){
	var game_detail = $("#game_detail");
	var gamedata = data.gameDetail;
	game_detail.find("img").attr("src",gamedata.picturePath + 'pic1.jpg');
	game_detail.find("h2").html(gamedata.gameName);
	game_detail.find("h3").html(gamedata.provider + "  提供");
	
	$("#game_text").html(gamedata.introduction);
	
	var silStr = '';
	for(var i = 1;i <= 6; i++){
		var srcImg = gamedata.picturePath + 'jietu'+i+'.jpg';
		$("#slider img").eq(i-1).attr("src",srcImg)
	}
}