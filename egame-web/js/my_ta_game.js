var USERID = "";
var friendId = "";

// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });


// 初始化页面
function init(){

	// 从URL 里面获取用户自己的ID, 这个只在个人中心有需要
	USERID = GetQueryString("USERID");
	friendId = GetQueryString("friendId");
	//alert('USERID='+USERID);
	getDataAndShow();
	//fresh();
	
	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID);
	 });

}



// 从接口获取数据，并且显示出来
function getDataAndShow(){
	
	var interf = String.format(userGameList_url,USERID,friendId,0,999);
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
	  pageLoadFinish();
	});

}


// 显示 从接口获取的数据
function showData(json){
	data = json.myCenter;

	$("#totalRecord").html(json.totalRecord);
	
	for (i in json.userGameList)
	{
		p = json.userGameList[i];
		str = '<li class="pos"><a onclick="showDetail(\'gameId\',\'%3\')"> <span class="head_mask_76"></span><img src="%1" width="76" height="76"/><P>%2</P></a></li>';
		str =  String.format(str, p.gamePic, p.gameName,p.gameId);
		$("#userGameList").append(str);
	}

	$("#content").removeClass("hide")
	pageLoadFinish();
}
