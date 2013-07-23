(function(){
	//cite global object
	var $=globalObject;
	var totalRecord=0;
	function globalAjaxRequest(_startIndex,_boolean){
		//set request url
		var pkExchangepkCurrencyUrl=String.format($.pkExchangepkCurrencyUrl,$.USERID,$.pageSize,_startIndex,$.gameId);
		//set callback function
		function callBack(_xmlHttp){
			var jsonData=$.parse(_xmlHttp.responseText);
			var mes=jsonData.result.resultmsg;
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				$.text('myAidouId',jsonData.aidouBalance,true);
				$.text('myPkbiId',jsonData.pkBalance,true);
				freeChangeButton(Number(jsonData.acquireCount),Number(jsonData.pkCurrencyNum));
				switch(result){
					case 0:
						//get json data
						if(_boolean){
							$.totalRecord=jsonData.totalRecord;
						}
						if($.totalRecord<=$.pageSize){
							$.display();
						}else{
							var tailEnd=Math.floor((Number($.totalRecord)/$.pageSize))*$.pageSize;
							$.pages(globalAjaxRequest,tailEnd);
							$.style('firstButId',$.startIndex,0);
							$.style('previousButId',$.startIndex,0);
							$.style('nextButId',tailEnd,$.startIndex);
							$.style('tailButId',tailEnd,$.startIndex);
						}
						//a tag href attribute and table content
						$.clear('listDetail');
						var dataList=jsonData.exchangeList;
						for(var i=0; i<$.pageSize; i++){
							try{
								var dataStr='<tr class="dunit1_out2">'
					                +'<td class="P_tb_10">'+dataList[i].amount+'</td>'
					                +'<td class="P_tb_10">'+dataList[i].pkNum+'</td>'
					                +'<td class="P_tb_10">'+dataList[i].exchangeTime+'</td>'
					            +'</tr>'
								$.text('listDetail',dataStr,false);
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
		$.AJAX('GET',pkExchangepkCurrencyUrl,callBack,null);
	}
	//freechange button load
	function freeChangeButton(_freeTime,_allTime){
		var freeOperateStr='';
		if(_freeTime>=_allTime){
			freeOperateStr='<p class="text_align_cen color_1 P_tb_10 font_20">明天记得再来领取哦</p>'
	        +'<p class="text_align_cen color_2 p_b_20 font_18">'
	        	 +'今天已免费领取'
	        	+'<span class="color_3">'+_freeTime+'</span>'
	        	 +'次PK币'
	        +'</p>';
			$.text('changeTopId',freeOperateStr,true);
				
		}else{
			freeOperateStr='<div class="btu_ps pos m_tb_10">'
				+'<a id="freeChangeId">'
					+'<span class="btu_grd_l"></span>'
					+'<span class="btu_grd_r"></span>'
					+' 免费领取PK币'
				+'</a>'
			+'</div>';
			$.text('changeTopId',freeOperateStr,true);
			var freeChangeObj=$.dollar('freeChangeId');
			freeChangeObj.onclick=function(){
				var freeReceivePkbiUrl=String.format($.freeReceivePkbiUrl,$.USERID,$.gameId);
				function callback(_xmlHttp){
					var jsonData=$.parse(_xmlHttp.responseText);
					if(typeof jsonData == 'object'){
						var result=jsonData.result.resultcode;
						var meg=jsonData.result.resultmsg;
						switch(result){
							case 0:
								if(jsonData.acquireCount>=jsonData.pkCurrencyNum){
									freeOperateStr='<p class="text_align_cen color_1 P_tb_10 font_20">明天记得再来领取哦</p>'
							        +'<p class="text_align_cen color_2 p_b_20 font_18">'
							        	+'今天已免费领取'
							        	+'<span class="color_3">'+jsonData.pkCurrencyNum+'</span>'
							        	+'次PK币'
							        +'</p>';
									$.text('changeTopId',freeOperateStr,true);
									var accountUrl=String.format($.accountUrl,$.USERID);
									(function(){
										function callBack(_xmlHtttp){
											var jsonData=$.parse(_xmlHttp.responseText);
											if(typeof jsonData == 'object'){
												var pkBalance=jsonData.pkBalance
												$.text('myPkbiId',jsonData.pkBalance,true);
											}else{
													$.dispose();
												}
											};
										$.AJAX('GET',accountUrl,callBack,null);
									})();
								}else{
									var accountUrl=String.format($.accountUrl,$.USERID);
									(function(){
										function callBack(_xmlHtttp){
											var jsonData=$.parse(_xmlHttp.responseText);
											if(typeof jsonData == 'object'){
												var pkBalance=jsonData.pkBalance
												$.text('myPkbiId',jsonData.pkBalance,true);
											}else{
												$.dispose();
											}
										};
										$.AJAX('GET',accountUrl,callBack,null);
									})();	
								}
								break;
							case 1:
								$.prompt(meg);
								break;
							case -1:
								$.prompt(meg);
							break;
						}
					}else{
						$.display();
					}
				};
				$.AJAX('GET',freeReceivePkbiUrl,callback,null);
			};
		}
	}
	//set preload function
	function globalPreload(){
		globalAjaxRequest(0,true);
		$.dollar('fullValueId').onclick=function(){
			var url=String.format($.fullValueHtml,$.USERID);
			showSysBrowse(url)
		}
		var inputObj=$.dollar('changeAidou');
		var tallyObj=$.dollar('tallyAidou');
		var changeButObj=$.dollar('changeButId');
		var value=inputObj.value.toString();
		var regExp=new RegExp('^([1-9])\\d*$');
		var monitor=null;
		var changeBol=false;
		inputObj.onfocus=function(){
			var aidouBalance=Number($.dollar('myAidouId').innerHTML);
			if(monitor!=null){
				clearInterval(monitor);
			}
			if(inputObj.value==value){
				inputObj.value='';
				value='';
				tallyObj.innerHTML='';
			}
			monitor=setInterval(function(){
				if(inputObj.value!=''){
					if(!regExp.test(inputObj.value)){
						tallyObj.innerHTML='请填写爱豆数额.';
					}else{
						if(inputObj.value>aidouBalance){
							tallyObj.innerHTML='您的爱豆余额不足，请先充值。';
							changeBol=false;
						}else{
							//change number
							tallyObj.innerHTML=Number(inputObj.value)*Number(100);
							changeBol=true;
						}
					}
				}else{
					tallyObj.innerHTML='';
				}
			},1);	
		};
		inputObj.onblur=function(){
			if(monitor!=null){
				clearInterval(monitor);
			}
			if(inputObj.value==''){
				inputObj.value=value;
				tallyObj.innerHTML='';
				changeBol=false;
			}
		};
		inputObj.onkeyup=function(){
			inputObj.value=inputObj.value.replace(/[^0-9]/g,'');
		};
		changeButObj.onclick=function(){
			if(changeBol){
				var aidouBalance=Number($.dollar('myAidouId').innerHTML)-Number(inputObj.value);
				if(aidouBalance<0){
					tallyObj.innerHTML='您的爱豆余额不足，请先充值。';
					changeBol=false;
					return;
				}
				var aiDou=inputObj.value;
				var changeAidouUrl=String.format($.changeAidouUrl,$.USERID,aiDou,$.gameId);
				function callBack(_xmlHttp){
					var jsonData=$.parse(_xmlHttp.responseText);
					var result=jsonData.result.resultcode;
					var meg=jsonData.result.resultmsg;
					changeBol=false;
					switch(result){
						case 0:
							var accountUrl=String.format($.accountUrl,$.USERID);
							(function(){
								function callBack(_xmlHtttp){
									var jsonData=$.parse(_xmlHttp.responseText);
									if(typeof jsonData == 'object'){
										var pkBalance=jsonData.pkBalance
										$.text('myAidouId',jsonData.aidouBalance,true);
										$.text('myPkbiId',jsonData.pkBalance,true);
									}else{
										$.dispose();
									}
								};
								$.AJAX('GET',accountUrl,callBack,null);
							})();
							globalAjaxRequest(0,true);
							$.prompt(meg);
							break;
						case 1:
							$.prompt(meg);
							break;
						case -1:
							$.prompt(meg);
							break;
					}
				};
				$.AJAX('GET',changeAidouUrl,callBack,null);
				inputObj.value='';
				tallyObj.innerHTML='';
			}else{
				if(inputObj.value==value){
					tallyObj.innerHTML='请填写爱豆数额.';
				}else{
					return;
				}
			}
		};
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();