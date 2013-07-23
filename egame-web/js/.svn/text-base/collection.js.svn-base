var USERID="";
var friendId="";

// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });


// 初始化页面
function init(){

	// 从URL 里面获取用户自己的ID, 这个只在个人中心有需要
	USERID = GetQueryString("USERID");
	friendId = GetQueryString("friendId");
	id = GetQueryString("id");
	getDataAndShow();
	
	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID);
	 });

}



// 从接口获取数据，并且显示出来
function getDataAndShow(){
	
	var interf =String.format(userCollectList_url,USERID,friendId,0);

	printToConsole(interf);
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
		   showData(data);
		 },
	   dataType: "json"
	});

	request.fail(function(textStatus) {
	 
	  addLoadErrTo("body_id");
	  //
	  pageLoadFinish();
	});

	//  直接用 json
	//$.getJSON('_data_individual-centers1.html', function(data){
		// fillData(data);
	//});   
}


// 显示 从接口获取的数据
function showData(data){
	//
	// 先填充数据
	//
	$("#totalRecord").html(data.totalRecord);
	
	// 
	for (i in data.userCollectList)
	{
		p = data.userCollectList[i];
		str = '<div class="line_1"></div>';
        str += '<div class="list pos"><a href="#" onclick="showDetail(\'gameId\',\'%6\')" class="coll_bg P_tb_10 blohi">';
        str += '	<div class="collLeft pos"><span class="head_mask_76c"></span><img src="%1" alt=""/></div>';
        str += '    <div class="listRight">';
        str += '   	<P class="font_20 m_tb_2">%2</P>';
        str += '       <P class="font_18 color_4 m_tb_2">%3 %4</P>';
        str += '       <div class="coll_img m_tb_2">';
        str += '       	<div class="coll_hid" style="width:%5px;"><img src="images/star_y.png" /></div>';
        str += '      </div>';
        str += '   </div>';
        str += ' </a></div>';

		//
		str =  String.format(str, p.gamePic, p.gameName, p.className, p.size, p.score,p.gameId);
		$("#userCollectList").append(str);
	}

	//
	// 显示
	//

	$("#content").removeClass("hide")
	//
	pageLoadFinish();
}
