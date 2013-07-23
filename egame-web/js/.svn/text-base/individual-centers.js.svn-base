var USERID = "";
var rec_game_list = null;

var game_idx = 0;
var collect_idx = 0;

var download_game_size = 0;
var collect_game_size = 0;


// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });


	// 初始化页面
	function init(){

		// 从URL 里面获取用户自己的ID, 这个只在个人中心有需要
		USERID = GetQueryString("USERID");
		//alert("USERID "+USERID);
		//USERID = 16290058;
		
		$("#userGameList_t2").hide();
		$("#userGameList_t1").hide();
		$("#userCollectList_t2").hide();
		$("#userCollectList_t1").hide();
		$("#head_img_id").hide();
		$("#new_task_btn").hide();
		$("#score_link").hide();
		$("#tamenlaiguo").hide();
		$("#wodeyouxi").hide();

		$("#aidou_link").click(
			function() {
			  showDialog('“爱豆”是由中国电信股份有限公司游戏运营中心推出的一种虚拟货币，可以用来支付爱游戏平台游戏虚拟道具购买、游戏购买、游戏包订购等服务。兑换比例为1人民币兑换1爱豆。');
			}
		);

		$("#wodeshoucang").hide();
		setTimeout( "getDataAndShow()",0);
		//fresh();

		$("#getWageBtn").click(
			function() {
			  getWage();
			}
		);
		
		 // 把每个链接都加上 自己的ID
		 $("a").click(function() {
			this.href =	appendParaToURL(this.href,"USERID="+USERID )   ;
		 });
	}


	function setGetWage(time){ 
		if(time!=null&& time!=""){
			var hour = Math.round(time/3600);	
			$("#getWage").html('<p class="m_t_20 color_1 font_18">再次领取还有'+hour+'小时</p>');	
		}		
	}



// 获取工资
function getWage(){

	// 调本地LOAD
	pageLoadStart();	 
	// 调接口	 
	var interf = String.format(get_wage_url,USERID);  //   '_data_getWage.html'; //  
	//
	printToConsole(interf);
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
		if(data.status!=1){
			//
			//setGetWage(data.time);
			getDataAndShow();
		}
		
		// 隐藏本地LOAD
		 pageLoadFinish();

		 // TOAST 显示结果
		showToast(data.content);
		
		 },
	   dataType: "json"
	});

	request.fail(function(textStatus) {

		 // TOAST 显示结果
		showToast("网络异常！");	
				  // 隐藏本地LOAD
		  pageLoadFinish();
	});
}


// 从接口获取数据，并且显示出来
function getDataAndShow(){
	//
	var interf = String.format(myCenter_url,USERID); // '_data_individual-centers.html';  //     
	printToConsole(interf);
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
			if(data.result.resultcode!=1){
				showData(data);
			}else{
				// 隐藏本地LOAD
					 pageLoadFinish();
				 // 
				addLoadErrTo("body_id");
			}
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
function showData(json){
	// 
	// 先填充数据
	//
	data = json.myCenter;

	// 工资
	setGetWage(json.myWage.time);

	if(json.isCompleteNoviceTask==0){
		$("#new_task_btn").show();
	}

	$("#name").html(data.userName);
	$("#lv").html("（Lv"+data.lvl+"）");
	$("#exp_value").html(data.exp);
	var exp = data.exp;
	var exp_up = parseInt(exp.substring(0,exp.indexOf("/"))) ;
	var exp_down = parseInt(exp.substring(exp.indexOf("/")+1)) ;
	var per= 88*exp_up/exp_down;
	if(exp_up>exp_down){
		exp_down = exp_up;
		per = 88;
	}
	$("#exp_img").width((per)+"%"); // 只能放入90%
	 
	$("#head_img_id").show();
	$("#head_img_id").attr("src",data.icon);

	if(data.msgNum==0){
		$("#msgNum").html("");
	}else{
		$("#msgNum").html("["+data.msgNum+"]");
	}
	$("#myProfile").attr("href",String.format(profile_html,USERID));
	$("#myAccount").attr("href",String.format(profilerecharge_html,USERID));
	$("#myMessage").attr("href",String.format(centers_friendnd_html,USERID));
	//$("#myPk").attr("href",String.format(centers_pk_html,USERID));
	$("#myPk").click(
		
	function() {
			  showToast("秘密开发中...");
			}
	);

	if(data.pk==0){
		$("#pk").html("");
	}else{
		$("#pk").html("["+data.pk+"]");
	}
	$("#score").html(data.score);
	$("#aidouNum").html(data.aidouNum);
	$("#platitude").html(data.hobby);
	if(data.hobby==null||data.hobby==""){
		$("#id_mood").hide();
	}


	if(data.headPortraitFlag==1){
		closeAd();
	}


		$("#tamenlaiguo").show();
		if(json.userVisitSize==null||json.userVisitSize==0){
			$("#come_btu_clear").hide();
		//	$("#userVisitList").append('<li style="height:0px"></li>');
		}



	 $("#userVisitList").html("");
	if(json.userVisitSize>0) {
		$("#userVisitSize").html("("+json.userVisitSize+")");
		for (i in json.userVisitList)
		{
			p = json.userVisitList[i];
			var userdetail_link = String.format(userDetail_html,p.userId,USERID);
			str = '<li class="pos"><a href="%1"><span class="head_mask_76"></span><img  src="%2"   width="76" height="76"  alt=""/><P>%3%4</span></P></a></li>';
			online = "";
			if(p.onlineStatus=="1"){
				online = '<span class="online online_76">';
			}
			str =  String.format(str, userdetail_link, p.icon, online,p.userName);
			$("#userVisitList").append(str);
		}
	}else{
		$("#userVisitSize").html("");
//		$("#userVisitList").append('<p class="texalce color_2 ">您还没有来访者，<a href="'+format(myfriend_html,USERID)+'" class="color_1">去好友那串串门</a></p>');
		$("#userVisitList").append('<p class="texalce color_2 "><a href="'+format(profile_html,USERID)+'" class="color_1">亲，没有好友访问你哦，完善资料让朋友找到你！</a></p>');
	}
	//

	if(rec_game_list==null){
		if(json.userGameSize==null||json.userGameSize==0||json.userCollectSize==null || json.userCollectSize==0){
			download_game_size = json.userGameSize;
			collect_game_size = json.userCollectSize;
			getHotGames();
		}
	}
		$("#wodeyouxi").show();
		if(json.userGameSize==null||json.userGameSize==0){
			$("#game_btu_clear").hide();
		//	$("#userGameList").append('<li style="height:0px"></li>');
		}
		$("#userGameList").html("");
		if(json.userGameSize>0) {
			$("#userGameSize").html("("+json.userGameSize+")");
			for (i in json.userGameList)
			{
				p = json.userGameList[i];
				str = '<li class="pos"><a onclick="showDetail(\'gameId\',\'%1\')"> <span class="head_mask_76"></span><img src="%2"  width="76" height="76" alt=""><p>%3</p></a></li>';
				str =  String.format(str, p.gameId, p.gamePic, p.gameName);
				$("#userGameList").append(str);
			}
			$("#userGameList_a").attr("href", "my_ta_game.html?friendId="+USERID);
		}else if(rec_game_list!=null){
			$("#userGameSize").html("");
			$("#userGameList_t2").show();
			$("#userGameList_t1").show();
			fresh_userGameList();
		
		}else{
			$("#userGameSize").html("");
			$("#userGameList").append('<p class="texalce color_2 ">亲，你还没有玩过游戏，赶快去看看吧！</p>');
		}
		//
	
		$("#wodeshoucang").show();
		if(json.userCollectSize==null||json.userCollectSize==0){
			$("#collect_btu_clear").hide();
		//	$("#userCollectList").append('<li style="height:0px"></li>');

		}

		$("#userCollectList").html("");

	
	if(json.userCollectSize>0) {
		$("#userCollectSize").html("("+json.userCollectSize+")");
		for (i in json.userCollectList)
		{
			p = json.userCollectList[i];
			str = '<li class="pos"><a  href="#" onclick="showDetail(\'gameId\',\'%1\')"> <span class="head_mask_76"></span><img src="%2"   width="76" height="76" alt=""><p>%3</p></a></li>';
			str =  format(str, p.gameId, p.gamePic, p.gameName);
			$("#userCollectList").append(str);
		}
		$("#userCollectList_a").attr("href", "collection.html?friendId="+USERID);
	}else if(rec_game_list!=null){
			$("#userCollectSize").html("");
			$("#userCollectList_t2").show();
			$("#userCollectList_t1").show();
			fresh_userCollectList();
		
		}else{
		$("#userCollectSize").html("");
			$("#userCollectList").append('<p class="texalce color_2 ">您还没有收藏游戏，赶快去看看吧！</p>');
	}
	//
	// 显示
	//

	$("#content").removeClass("hide")
	//
	pageLoadFinish();
}


function closeAd(){

	$("#ad").hide();
}

function getHotGames2(){
	
	setHotGames('{"gameListByHot":[{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C32022\/110221005745\/pic\/pic1.jpg","productId":231362,"productName":"醉剑逍遥-征途天下"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C11021\/110225843000\/pic\/pic1.jpg","productId":226059,"productName":"乔峰传奇"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C21001\/110221000021\/pic\/pic1.jpg","productId":228380,"productName":"超级街霸快打"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C11065\/110229981000\/pic\/pic1.jpg","productId":228358,"productName":"谁是神枪手"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C09056\/110222361000\/pic\/pic1.jpg","productId":219486,"productName":"黄金矿工"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C31004\/110228833000\/pic\/pic1.jpg","productId":227914,"productName":"游击队员鲍勃"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C00129\/110221003681\/pic\/pic1.jpg","productId":230269,"productName":"墨滴"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C09056\/110228524000\/pic\/pic1.jpg","productId":227701,"productName":"小龟回家"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C11065\/110229901000\/pic\/pic1.jpg","productId":228311,"productName":"愤怒的大兵"},{"picPath":"http:\/\/202.102.39.9\/sharefiles\/igame\/gameentity\/C00107\/110116550000\/pic\/pic1.jpg","productId":226457,"productName":"水浒无双-真龙之剑"}],"pageSize":10,"result":{"resultmsg":"获取热门推荐游戏列表成功","resultcode":0},"startIndex":0,"totalRecord":100}');
}

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"g"),s2); 
}

function setHotGames(s){

	try
	{
/*
		s = s.replaceAll("\\", "");
		s = s.replaceAll("\b", "");
		s = s.replaceAll("\t", "");
		s = s.replaceAll("\n", "");
		s = s.replaceAll("\f", "");
		s = s.replaceAll("\r", "");
		s=s.replaceAll('\"',''); 
		s=s.replaceAll("'",'');

		s=s.replaceAll("productId",'gameId');
		s=s.replaceAll("productName",'gameName');
		s=s.replaceAll("picPath",'gamePic');
*/
		rec_game_list = JSON.parse(s); //   (new Function('return'+s.toLocaleString()))(); //  jQuery.toJSON(s);//(new Function('return '+ s.toLocaleString()+';'))(); 
	//	showToast(rec_game_list);
	//	printToConsole(rec_game_list);
		if(collect_game_size==null||collect_game_size==0)
		{
			$("#userCollectSize").html("");
			$("#userCollectList_t2").show();
			$("#userCollectList_t1").show();
			fresh_userCollectList();
		}
		if(download_game_size==null||download_game_size==0)  {
			$("#userGameSize").html("");
			$("#userGameList_t2").show();
			$("#userGameList_t1").show();
			fresh_userGameList();
		}
	}
	catch(er)
	{
		printToConsole(''+er);
		//alert(er);
	}
}



function fresh_userGameList(){
	$("#userGameList").html("");
	try {
		for (i = 0; i < 5; i++)
			{
				game_idx++;
				p = rec_game_list.gameListByHot[game_idx%rec_game_list.gameListByHot.length];
				str = '<li class="pos"><a onclick="showDetail(\'gameId\',\'%1\')"> <span class="head_mask_76"></span><img src="%2"  width="76" height="76" alt=""><p>%3</p></a></li>';
				str =  String.format(str, p.productId, p.picPath, p.productName);
				$("#userGameList").append(str);
				printToConsole(str);
			}
	}
	catch(er)
	{
		printToConsole(er);
	}
}

function fresh_userCollectList(){
	$("#userCollectList").html("");
	try {
		for (i = 0; i < 5; i++)
		{
			collect_idx++;
			p = rec_game_list.gameListByHot[rec_game_list.gameListByHot.length-1-collect_idx%rec_game_list.gameListByHot.length];
			str = '<li class="pos"><a onclick="showDetail(\'gameId\',\'%1\')"> <span class="head_mask_76"></span><img src="%2"  width="76" height="76" alt=""><p>%3</p></a></li>';
			str =  String.format(str, p.productId, p.picPath, p.productName);
			$("#userCollectList").append(str);
			printToConsole(str);
		}
	}
	catch(er)
	{
		printToConsole(er);
	}
}