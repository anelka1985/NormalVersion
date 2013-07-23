(function(){
	//cite global object
	var $=globalObject;
	var searchType=$.variable('searchType',0);
	var rankType=$.variable('rankType',0);
	var gameName = $.variable('gameName',0);
	var arrowflag = false;
	//private method
	function menuStyle(_id,_menuSize,_selectNum,_norclass,_selclass){
		for(var i=0; i<_menuSize; i++){
			var obj=$.dollar(_id+i);
			var spanObj=obj.getElementsByTagName('span');
			if(i==_selectNum){
				obj.className=_selclass;
				try{
					if(spanObj[0].className=='head_mask_pknarrow'){
						spanObj[0].style.display='block';
					}
					if(spanObj.length>1){
						spanObj[0].className='btu_man_com2 btu_green_s_com btu_green_ss_left';
						spanObj[1].className='btu_green_ss_right';
					}
				}catch(ex){
					//nothing
				}
			}else{
				obj.className=_norclass;
				try{
					if(spanObj[0].className=='head_mask_pknarrow'){
						spanObj[0].style.display='none';
					}
					if(spanObj.length>1){
						spanObj[0].className='btu_man_com2 btu_green_s_com btu_green_s_left';
						spanObj[1].className='btu_green_s_right';
					}
				}catch(ex){
					//nothing
				}
			}
		}
		
	}
	function globalAjaxRequest(_rankType,_direction,_rank){
		//set request url
		if(searchType==0){
			var darenListUrl=String.format($.darenAllListUrl,$.USERID,_rankType,$.gameId,_direction,_rank,$.pageSize);
		}else{
			var darenListUrl=String.format($.darenMyListUrl,$.USERID,_rankType,$.gameId,_direction,_rank,$.pageSize);
		}
		//set callback function
		function callBack(_xmlHttp){
			var jsonData=$.parse(_xmlHttp.responseText);
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				var mes=jsonData.result.resultmsg;
				if(arrowflag==true){
					if(result!=0){
						return;
					}
				}
				var detailObj=$.clear('listDetail');
				switch(result){
					case 0:
						var dataList=jsonData.daRRenRankList;
					 	for(var i=0; i<$.pageSize; i++){
					 		try{
								function riseIcon(_num){
									switch(_num){
										case -1:
											var riseStr='<span><img src="images/icon_down.png" width="26" height="20" /></span>'
											break;
										case 0:
											var riseStr='<span><img src="images/icon_con.png" width="26" height="20" /></span>'
											break;
										case 1:
											var riseStr='<span><img src="images/icon_up.png" width="26" height="20" /></span>'
											break;
									}
									return riseStr;
								}
								if(dataList[i].placing <= 3){
									switch(dataList[i].placing){
										case 1:
											var riseStr=riseIcon(dataList[i].isRise);
											dataList[i].placing='<img src="images/icon_j_s.png" width="26" height="20" />';
											break;
										case 2:
											var riseStr=riseIcon(dataList[i].isRise);
											dataList[i].placing='<img src="images/icon_y_s.png" width="26" height="20" />';
											break;
										case 3:
											var riseStr=riseIcon(dataList[i].isRise);
											dataList[i].placing='<img src="images/icon_t_s.png" width="26" height="20" />';
											break;
									}
								}else{
									var riseStr='';
								}
								
								
								
								if(String(dataList[i].reachId)==$.USERID){
									var addBoolean=true;
									var riseStr=riseIcon(dataList[i].isRise);
									var userStyle='bg_color4';	
									if(i>0){
										$.text('otherRankId',' 即将赶超'+jsonData.myLastName,true);
									}
								}else{
									var userStyle='coll_bg';
								}
								if(Number(jsonData.myRanking)>0){
									$.text('myRankTextId','我的排名',true);
									$.text('myRankId','第 <span class="color_3">'+(Number(jsonData.myRanking))+'</span> 名',true);
									if(Number(jsonData.myRanking)==1){
										$.text('otherRankId',"我已天下无敌",true);
									}else if(Number(jsonData.myRanking)>1){
										if(jsonData.myLastName){
											$.text('otherRankId',' 即将赶超'+jsonData.myLastName,true);
										}else{
											$.text('otherRankId',' 即将赶超无名氏',true);
										}
									}
								}else{
									$.text('otherRankId',"我还有没有上传成绩哦",true);
								}
								var reachNameX = "";
								if($.USERID == dataList[i].reachId){
									reachNameX = "我";
								}else{
									reachNameX = dataList[i].reachName;
								}
								dataStr='<div class="line_1"></div>'
						        	+'<div id="list'+i+'" class="list pos">'
						        		+'<a class="'+userStyle+' P_tb_10 blohi">'
						        			+'<div id="userIconId'+i+'" class="collLeft pos">'
						        				+'<span class="head_mask_76c"></span>'
						        					+'<img src="'+dataList[i].reachIcon+'" alt=""/>'
						        			+'</div>'
						        			+'<div class="listRight pos">'
						        				+'<p class="font_20 m_tb_2">'
						        					+'<span>'
						        						+dataList[i].placing+' '
						        					+'</span>'
						        					+'<span id="userNameId'+i+'">'+reachNameX+'</span>'
						        					+riseStr
						        				+'</p>'
						                		+'<p class="font_18 color_4 m_tb_2 m_t_20 P_l_10">'
						                			+dataList[i].reachTime+'达成'
						                		+'</p>'
						                		+'<p class="game_integral font_20">'
						                			+dataList[i].reachScore
						                		+'</p>'
						             		+'</div>'
						             		+'<div class="narrow_r"></div>'
						        		+'</a>'
						        	+'</div>';
						        $.text('listDetail',dataStr);
							}catch(ex){
								break;
							}
					 	}
					 	var regExp=/\d{1,}$/;
					 	for(var j=0; j<$.pageSize; j++){
					 		try{
						 		var userIconObj=$.dollar('userIconId'+j);
						 		var userNameObj=$.dollar('userNameId'+j);
						 		var listConObj=$.dollar('list'+j);
						 		userIconObj.onclick=function(){
						 			var num=this.id.match(regExp);
						 			showDetail('friendId',dataList[num].reachId);
						 		}
						 		userNameObj.onclick=function(){
						 			var num=this.id.match(regExp);
						 			showDetail('friendId',dataList[num].reachId);
						 		}
						 		listConObj.onclick=function(){
						 			var num=this.id.match(regExp);
						 			showDetail('friendId',dataList[num].reachId);
						 		}
					 		}catch(e){
					 			break;
					 		}
					 	}
					 	if(_direction == 0){
					 		if(dataList.length>9){
						 		var tailPageObj=$.dollar('tailPageId');
								var nextPageObj=$.dollar('nextPageId');
								var previousPageObj=$.dollar('previousPageId');
								var firstPageObj=$.dollar('firstPageId');
								nextPageObj.onclick=function(){
									globalAjaxRequest(rankType,2,jsonData.lastNum);
									arrowflag = true;
								}
								tailPageObj.onclick=function(){
									globalAjaxRequest(rankType,3,jsonData.lastNum);
									arrowflag = true;
								}
								previousPageObj.onclick=function(){
									globalAjaxRequest(rankType,1,jsonData.firstNum);
									arrowflag = true;
								}
								firstPageObj.onclick=function(){
									globalAjaxRequest(rankType,0,jsonData.firstNum);
									arrowflag = true;
								}
					 		}else{
					 			$.display();
					 		}
					 	}else{
					 		var tailPageObj=$.dollar('tailPageId');
							var nextPageObj=$.dollar('nextPageId');
							var previousPageObj=$.dollar('previousPageId');
							var firstPageObj=$.dollar('firstPageId');
							nextPageObj.onclick=function(){
								globalAjaxRequest(rankType,2,jsonData.lastNum);
							}
							tailPageObj.onclick=function(){
								globalAjaxRequest(rankType,3,jsonData.lastNum);
							}
							previousPageObj.onclick=function(){
								globalAjaxRequest(rankType,1,jsonData.firstNum);
							}
							firstPageObj.onclick=function(){
								globalAjaxRequest(rankType,0,jsonData.firstNum);
							}
					 	}
					 	
						break;
					case 1:
						if(Number(jsonData.gangProperties)==0){
							$.text('listDetail','<div class="fri_text">您还没有好友哦，立即去找朋友吧</div><div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="'+String.format(findfri_html,$.USERID)+'"><span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>')
						}
						if(Number(jsonData.gangProperties)==1){
							$.text('listDetail','<div class="text_pk" id="nofriend">还没有朋友达到分数，邀请朋友一起来玩哦 ！<div class="btu_gr pos m_t_20"><a href="'+ String.format(currentGameShare_html,$.USERID,"undefined") + '"><span class="btu_gr_l"></span><span class="btu_gr_r"></span>邀请朋友</a></div></div>')
						}
						$.display();
						$.prompt(mes)
						break;
					case -1:
						$.display();
						$.prompt(mes)
						break;
				}
			}else{
				$.dispose();
			}
		}
		//ajax request
		$.AJAX('GET',darenListUrl,callBack,null);
	}
	//set preload function
	function globalPreload(){
		//a href attribute
		//load a add href attribute
		$.href('allFirendId',$.darenListHtml,$.USERID,0,0,$.gameId,$.pageSize);
		$.href('myFriendId',$.darenListHtml,$.USERID,1,0,$.gameId,$.pageSize);
		$.href('rankTodayId',$.darenListHtml,$.USERID,searchType,0,$.gameId,$.pageSize);
		$.href('rankWeekId',$.darenListHtml,$.USERID,searchType,1,$.gameId,$.pageSize);
		$.href('rankMonthId',$.darenListHtml,$.USERID,searchType,2,$.gameId,$.pageSize);
		$.href('rankGlobalId',$.darenListHtml,$.USERID,searchType,3,$.gameId,$.pageSize);
		menuStyle('liMenu',2,searchType,'title_pk_nor','title_pk_select');
		menuStyle('secMenu',4,rankType,'btu_game  pos','btu_game_select pos');
		globalAjaxRequest(rankType,0,0);
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();