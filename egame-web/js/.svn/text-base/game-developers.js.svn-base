

var cpId;
//每页数量
var pageSize = "";
//开始页数
var startIndex = 0;
//总条数
var totalRecord="";
//默认每页显示数
var defaultPageSize=15;
var defaultStartIndex = 0;



$(document).ready(function(){
	init();
})

function init(){
	$("#game_list").hide();	
	$("#page").hide();
	startIndex = GetQueryString("startIndex");
	pageSize = GetQueryString("pageSize");
	cpId = GetQueryString("cpId");
	//userID = GetQueryString("USERID");
	//if(userID==null) showToast("userID is null");
	if(pageSize==null) pageSize=defaultPageSize;
	if(cpId==null) cpId="54";
	if(startIndex==null) startIndex=defaultStartIndex;
	//var interf = String.format(moregame_developers_url,startIndex,pageSize,userID);
	var interf = String.format(moregame_developers_url,pageSize,startIndex,cpId);
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
	$("#game_num").html(data.totalRecord);
	var gl = data.playList;
	if(gl.length>0){
		var strArray = [];
		for(var i = 0; i<gl.length; i++){
			//var str = '<div class="line_1"></div><div class="list pos"><a  onclick="showDetail(\'gameId\',\'%1\')" class="coll_bg P_tb_10 blohi"><div class="collLeft pos"><span class="head_mask_76c"></span><img src="%2" alt=""/></div><div class="listRight pos"><P class="font_20 P_t_25">%3</P></div><div class="narrow_r"></div></a></div>';	
			var str = '<div class="line_1"></div><div class="list pos"><a href="gamedetails.html?gameId='+gl[i].gameId+'" class="coll_bg P_tb_10 blohi"><div class="collLeft pos"><span class="head_mask_76c"></span><img src="'+gl[i].picturePath+'" alt=""/></div><div class="listRight pos"><P class="font_20 P_t_25">'+gl[i].gameName+'</P></div><div class="narrow_r"></div></a></div>';	
			//str =  String.format(str, gl[i].gameId, gl[i].picturePath, gl[i].gameName);
			strArray.push(str);
		}	
		$("#game_list").empty().show().append(strArray.join(''));
		var pageHome = $("#pageHome"),pageUp = $("#pageUp"),pageDown = $("#pageDown"),pageEnd = $("#pageEnd");
		startIndex = data.startIndex;
		pageSize = data.pageSize;
		totalRecord = data.totalRecord;
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
			setBtnUse(pageUp,String.format(moregame_developers,Math.abs(startIndex-pageSize),pageSize,userID));
			setBtnUse(pageHome,String.format(moregame_developers));
		}else if(startIndex ==0 &&totalRecord>pageSize){//第一页
			setBtnUnus(pageUp);
			setBtnUnus(pageHome);
			setBtnUse(pageDown,String.format(moregame_developers,startIndex+pageSize,pageSize,userID));
			setBtnUse(pageEnd,String.format(moregame_developers,totalRecord,pageSize,userID));
		}else{
			$("#page").show();
			setBtnUse(pageDown,String.format(moregame_developers,startIndex+pageSize,pageSize,userID));
			setBtnUse(pageEnd,String.format(moregame_developers,totalRecord,pageSize,userID));
			setBtnUse(pageUp,String.format(moregame_developers,Math.abs(startIndex-pageSize),pageSize,userID));
			setBtnUse(pageHome,String.format(moregame_developers));
		}
	}
}

function setBtnUse(obj,href){//使按钮变绿可点
	$(obj).attr("href",href).parent().addClass("btu_green_s").removeClass("btu_gra_s btu_page_com").find("span.btu_man_com2").addClass("btu_green_s_com btu_green_s_left").removeClass("btu_gra_s_com btu_gra_s_left").siblings("span").addClass("btu_green_s_right").removeClass("btu_gra_s_right");	
}

function setBtnUnus(obj){//使按钮变灰不可点
	$(obj).attr("href","").parent().addClass("btu_gra_s btu_page_com").removeClass("btu_green_s").find("span.btu_man_com2").addClass("btu_gra_s_com btu_gra_s_left").removeClass("btu_green_s_com btu_green_s_left").siblings("span").addClass("btu_gra_s_right").removeClass("btu_green_s_right");	
}

