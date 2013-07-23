(function(){
	//cite global object
	var $=globalObject;
	var totalRecord=0;
	function globalAjaxRequest(_startIndex,_boolean){
		//set request url
		var pkMypkUrl=String.format($.pkMypkUrl,$.USERID,$.pageSize,_startIndex,$.gameId);
		//set callback function
		function callBack(_xmlHttp){
			var jsonData=$.parse(_xmlHttp.responseText);
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				var mes=jsonData.result.resultmsg;
				$.text('myPkbiId',jsonData.pkBalance,true);
				switch(result){
					case 0:
						//get json data
						if(_boolean){
							totalRecord=jsonData.totalRecord;
						}
						if(totalRecord<=$.pageSize){
							$.display();
						}else{
							var tailEnd=Math.floor(totalRecord/$.pageSize)*$.pageSize;
							$.pages(globalAjaxRequest,tailEnd);
							$.style('firstButId',$.startIndex,0);
							$.style('previousButId',$.startIndex,0);
							$.style('nextButId',tailEnd,$.startIndex);
							$.style('tailButId',tailEnd,$.startIndex);
						}
						//a tag href attribute and table content
						
						var detailObj=$.clear('listDetail');
						var dataList=jsonData.pkList;
						for(var i=0; i<$.pageSize; i++){
							try{
								switch(dataList[i].op){
									case 1:
										var operateBtn='<div class="wid_95 btu_g_com pos ">'
												+'<a id="op'+i+'">'
													+'<span class="btu_man_com2 btu_green_com2 btu_g_left"></span>'
													+'<span class="btu_g_right"></span>'
													+'<span class="icon_tz">挑 战</span>'
												+'</a>'
											+'</div>';
										break;
									case 2:
										var operateBtn='<div class="wid_95 btu_green_com pos ">'
												+'<a id="op'+i+'">'
													+'<span class="btu_man_com2 btu_green_com2 btu_man_left"></span>'
													+'<span class="btu_mood_right"></span>'
													+'<span class="icon_cx"> 撤 销</span>'
												+'</a>'
											+'</div>';
										break;
								}
								var dataStr='<tr id="list'+i+'" class="dunit1_out2">'
					            	+'<td class="P_tb_10">'+dataList[i].initialDate+'</td>'
					                +'<td class="P_tb_10">'+dataList[i].bonus+'</td>'
					                +'<td class="P_tb_10">'
					                	+operateBtn
					                +'</td>'
					            +'</tr>';
								$.text('listDetail',dataStr,false);
							}catch(ex){
								break;
							}
						}
						//a tag onclick event
						for(var j=0; j<$.pageSize; j++){
							try{
								var opObj=$.dollar('op'+j);
								var regExp=/\d{1,}$/;
								switch(dataList[j].op){
									case 1:
										opObj.onclick=function(){
											var pkBalance=Number($.dollar('myPkbiId').innerHTML);
											var num=this.id.match(regExp);
											var bonus=dataList[num].bonus;
											if(pkBalance<bonus){
												$.sikpUrl=function(){
													window.location=String.format($.fullValueHtml,$.USERID);
												}
												$.confirm('您的pk币余额不够，请充值或兑换。','globalObject.sikpUrl()');
											}else{
												window.location=String.format($.pkChallengeHtml,$.USERID,dataList[num].bonus,escape(dataList[num].nickName),jsonData.myIcon,dataList[num].userIcon,dataList[num].pkId,$.gameId);
											}
										};
										break;
									case 2:
										opObj.onclick=function(){
											var num=this.id.match(regExp);
											 $.removeChallenge=function(){
												var removeChallengeUrl=String.format($.removeChallengeUrl,dataList[num].pkId);
												function callBack(_xmlHttp){
													var jsonData=$.parse(_xmlHttp.responseText);
													if(typeof jsonData == 'object'){
														var result=jsonData.result.resultcode;
														var mes=jsonData.result.resultmsg;
														switch(result){
															case 0:
																(function(){
																	var row=$.dollar('list'+num);
																	detailObj.removeChild(row);
																	if(detailObj.innerHTML==''){
																		$.startIndex=0;
																		globalAjaxRequest(0,false);
																	}else{
																		globalAjaxRequest($.startIndex,false)
																	}
																	var accountUrl=String.format($.accountUrl,$.USERID);
																	function callBack(_xmlHtttp){
																		var jsonData=$.parse(_xmlHttp.responseText);
																		if(typeof jsonData == 'object'){
																			var pkBalance=jsonData.pkBalance
																			$.text('myPkbiId',pkBalance,true);
																		}else{
																			$.dispose();
																		}
																	};
																	$.AJAX('GET',accountUrl,callBack,null);	
																})();
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
												};
												$.AJAX('GET',removeChallengeUrl,callBack,null);
											}
											$.confirm('您确定撤销此PK吗？','globalObject.removeChallenge()');
											$.fisish();
										}
										break;
								}
							}catch(ex){
								break;
							}
						}
						break;
					case 1:
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
		$.AJAX('GET',pkMypkUrl,callBack,null);
	}
	//set preload function
	function globalPreload(){
		//a href attribute
		$.href('pkIndexId',$.pkIndexHtml,$.USERID,$.gameId);
		$.href('pkMypkId',$.pkMypkHtml,$.USERID,$.gameId);
		$.href('pkChangeId',$.pkExchangepkCurrencyHtml,$.USERID,$.gameId);
		$.href('pkModeId',$.pkModeHtml,$.USERID,$.gameId);
		$.href('pkRecord',$.pkRecordHtml,$.USERID,$.gameId);
		globalAjaxRequest(0,true);
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();