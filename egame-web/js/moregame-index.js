var cpId;

$(document).ready(function(){
	init();
})

function init(){
	$("#game_list").hide();	
	$("#page").hide();
	
	//cpId = GetQueryString("cpId");
	
	$("#moreGames").attr("href",String.format(moregame_developers,cpId));	
	$("#moreGames-developer").attr("href",String.format(moregame_developers_index,cpId));
	
	var interf = moregame_developers_index_url;
	printToConsole(interf);
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
		$("#body").append('<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>');
	});
}

function setDataAndShow(data){
	
	var gl = data.moreProducts;
	if(gl.length>0){
		var strArray = [];
		for(var i = 0; i<gl.length; i++){
			var myWay = '';
			if(gl[i].productType == 1){
				myWay = 'showDetail(\'gameId\','+gl[i].productId + ')';
			}else if(gl[i].productType == 2){
				myWay = 'showSysBrowse(\'' + gl[i].linkUrl + '\')'; 
			}
			
			var str = '<div class="line_1"></div><div class="list pos"><a href="javascript:'+myWay+'" class="coll_bg P_tb_10 blohi"><div class="collLeft pos"><span class="head_mask_76c"></span><img src="'+gl[i].picture+'" alt=""/></div><div class="listRight pos"><P class="font_20 P_t_25">'+gl[i].productName+'</P></div><div class="narrow_r"></div></a></div>';	
			
			strArray.push(str);
			
		}	
		$("#game_list").empty().show().append(strArray.join(''));
 	}
}