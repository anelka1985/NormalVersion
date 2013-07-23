(function(){
	//cite global object
	var $=globalObject;
	var resultId=$.variable('resultId');
	var myScore=$.variable('myScore');
	var pkUserId=$.variable('targetUserId');
	//get html attribute
	function globalAjaxRequest(){
		var endAcceptUrl=String.format($.endAcceptUrl,$.USERID,resultId,myScore);
		function callBack(_xmlHttp){
			var jsonData=$.parse(_xmlHttp.responseText);
			setPkUserName(jsonData.gameName);
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				var mes=jsonData.result.resultmsg;
				switch(result){
					case 0:
						var resultKey=Number(jsonData.resultKey);
						switch(resultKey){
							case 0:
								var resultIcon='<div class="icon_bq_b">'
										+'<img src="images/icon_bq03_b.png" width="100" height="100"/>'
									+'</div>'
        						+'<P class="text_align_cen color_2 font_20 font_lin30">胜利</P>';
        						var resultInfor='<P class="text_align_cen color_2 font_20">'
									+'恭喜你以<span class="color_3">'+jsonData.myScore+'</span>'
									+'的成绩打赢<span class="color_3">'+jsonData.targetNickName+'</span>'
								+'</P>'
       							+'<P class="text_align_cen color_2 font_20 font_lin58">赢得PK币 '+jsonData.pkCoinChange+'</P>';
       							var resultBut='<div class="btu_gr pos m_t_20"><a href="'+ String.format(currentGameShare_html,$.USERID,jsonData.myScore) +'">'
							        +'<span class="btu_gr_l"></span>'
							        +'<span class="btu_gr_r"></span>'
							       	+'炫成绩'
							    +'</a></div>';
								break;
							case 1:
								var resultIcon='<div class="icon_bq_b">'
											+'<img src="images/icon_bq12_b.png" width="100" height="100"/>'
										+'</div>'
        								+'<P class="text_align_cen color_2 font_20 font_lin30">平局</P>';
        						var resultInfor='<P class="text_align_cen color_2 font_20">'
									+'你以 <span class="color_3">'+jsonData.myScore+'</span>'
									+'的成绩打平<span class="color_3">'+jsonData.targetNickName+'</span>'
								+'</P>'
       							+'<P class="text_align_cen color_2 font_20 font_lin58">pk币返还</P>';
       							var resultBut='<div id="againStart" class="btu_pkbegin pos"><a>'
							       +'<span class="btu_g_l"></span>'
							       +'<span class="btu_g_r"></span>'
							       +'不服气，再来'
							    +'</a></div>';
								break;
							case 2:
								var resultIcon='<div class="icon_bq_b">'
											+'<img src="images/icon_bq10_b.png" width="100" height="100"/>'
										+'</div>'
        								+'<P class="text_align_cen color_2 font_20 font_lin30">失败</P>';
        						var resultInfor='<P class="text_align_cen color_2 font_20">'
									+'你以 <span class="color_3">'+jsonData.myScore+'</span>'
									+'的成绩输给 <span class="color_3">'+jsonData.targetNickName+'</span>'
								+'</P>'
       							+'<P class="text_align_cen color_2 font_20 font_lin58">失去PK币 '+jsonData.pkCoinChange+'</P>';
       							var resultBut='<div id="againStart" class="btu_pkbegin pos"><a>'
							       +'<span class="btu_g_l"></span>'
							       +'<span class="btu_g_r"></span>'
							       +'不服气，再来'
							    +'</a></div>';
								break;
							case -1:
								$.prompt('异常');
						}
						var friendly=Number(jsonData.friendly);
						switch(friendly){
							case 0:
								var addFriendBut='<div class="btu_gr pos m_t_20"><a id="addFirendA">'
							        	+'<span class="btu_gr_l"></span>'
							        	+'<span class="btu_gr_r"></span>'
							        	+'加TA好友'
							    +'</a></div>';
								break;
							case 1:
								var addFriendBut='';
								break;
						}
						$.text('resultIconTextId',resultIcon,false);
						$.text('resultInforId',resultInfor,false);
						$.text('resultBut',resultBut,false);
						$.text('addFirendId',addFriendBut,false);
						var againStartObj=$.dollar('againStart');
						if(againStartObj!=null){
							againStartObj.onclick=function(){
								var startChallengeUrl=String.format($.startChallengeUrl,$.USERID,jsonData.pkId);
								function callBack(_xmlHttp){
									var jsonData=$.parse(_xmlHttp.responseText);
									if(typeof jsonData == 'object'){
										var result=Number(jsonData.result.resultcode);
										var mes=jsonData.result.resultmsg;
										var resultKey=Number(jsonData.resultKey);
										if(resultKey){
											switch(resultKey){
													case -1:
														$.prompt('数据异常，发起挑战失败');
														break;
													case 0:
														$.prompt('发起挑战失败，请稍后再试');
														break;
													case 1:
														startPk(2,jsonData.resultId);
														break;
													case 2:
														$.prompt('您的PK币余额不足');
														break;
													case 3:
														$.prompt('Ta正在接受挑战，请稍后再试');
														break;
													case 4:
														$.prompt('该挑战已经下线');
														break;
												}
										}else{
											if(result == -1){
												$.prompt(mes);
											}else if(result == 1){
												$.prompt(mes);
											}
										}
										if(result == -1){
												$.prompt(mes);
										}
										
									}else{
										$.dispose();	
									}
								};
								$.AJAX('GET',startChallengeUrl,callBack,null);
							}
						}
						var addFirendIdObj=$.dollar('addFirendA');
						if(addFirendIdObj!=null){
							addFirendIdObj.onclick=function(){
								var addFriendUrl=String.format(addFriend_Request_url,jsonData.targetUserId,$.USERID);
								printToConsole(addFriendUrl);
								function callBack(_xmlHttp){
									var jsonData=$.parse(_xmlHttp.responseText);
									if(typeof jsonData == 'object'){
										var result=Number(jsonData.result.resultcode);
										var mes=jsonData.result.resultmsg;
										$.prompt(mes);
										
									}
								}
								$.AJAX('GET',addFriendUrl,callBack,null);
							}
						}
						break;
					case 1:
						$.prompt(mes);
						break;
					case -1:
						$.prompt(mes);
						break;
				}
			}else{
				$.dispose();
			}
		}
		$.AJAX('GET',endAcceptUrl,callBack,null);
	}
	//set preload function
	function globalPreload(){
		//a href attribute
		$.href('backHomePageId',$.pkIndexHtml,$.USERID,$.gameId);
		globalAjaxRequest();
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();