(function(){
	//cite global object
	var $=globalObject;
	//ajax request
	function globalAjaxRequest(){
		//set request url
		var accountUrl=String.format($.accountUrl,$.USERID);
		//set callback function
		function callBack(_xmlHttp){
			var jsonData=$.parse(_xmlHttp.responseText);
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				var mes=jsonData.result.resultmsg;
				switch(result){
					case 0:
						//get json data
						if(jsonData.pkBalance>=105){
							 //start pk
							startPk(1,0);
						}else{
							if(jsonData.acquireCount>=jsonData.pkCurrencyNum){
								$.sikpUrl=function(){
									window.location=String.format($.pkExchangepkCurrencyHtml,$.USERID,$.gameId);
								}
								$.confirm('您今天已经领取'+jsonData.acquireCount+'次PK币了,请兑换','globalObject.sikpUrl()');
							}else{
								$.sikpUrl=function(){
									var freeReceivePkbiUrl=String.format($.freeReceivePkbiUrl,$.USERID,$.gameId);
									function callback(_xmlHttp){
										var jsonData=$.parse(_xmlHttp.responseText);
										if(typeof jsonData == 'object'){
											var result=jsonData.result.resultcode;
											var meg=jsonData.result.resultmsg;
											switch(result){
												case 0:
													$.prompt(meg);	
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
									}
									$.AJAX('GET',freeReceivePkbiUrl,callback,null)
								};
								$.confirm('每天都有'+jsonData.pkCurrencyNum+'次免费获得PK币的机会哦！快来领取吧!','globalObject.sikpUrl()');
							}
						}
						break;
					case 1:
						$.prompt(mes)
						break;
					case -1:
						$.prompt(mes)
						break;
				}
			}else{
				$.dispose();
			}
		}
		//ajax request
		$.AJAX('GET',accountUrl,callBack,null);
	}
	//set preload function
	function globalPreload(){
		var startModeObj=$.dollar('startModeId');
		startModeObj.onclick=function(){
			//game over cite url
			globalAjaxRequest();
		}
		$.href('backHomeId',$.pkIndexHtml,$.USERID,$.gameId);
		$.finish();
	}
	$.load(function(){
		globalPreload();
	});
})();
