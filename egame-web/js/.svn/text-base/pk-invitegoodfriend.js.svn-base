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
			var mes=jsonData.result.resultmsg;
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				switch(result){
					case 0:
						$.text('myPkbi',jsonData.pkBalance,true);
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
		//a href attribute
		$.text('myScoreId',$.score,true);
		$.href('pkChangeId',$.pkExchangepkCurrencyHtml,$.USERID,$.gameId);
		globalAjaxRequest();
		var inputObj=$.dollar('bonusId');
		var regExp=RegExp('^([1-9])\\d{1,}$');
		var value=inputObj.value;
		inputObj.onfocus=function(){
			if(this.value==value){
				this.value='';
			}
		}
		inputObj.onblur=function(){
			if(this.value==''){
				this.value=value;
			}else{
				if(!regExp.test(inputObj.value)){
					$.prompt('您要输入不小于10的整数');			
				}
			}
		}
		var releaseBut=$.dollar('releaseId');
		releaseBut.onclick=function(){
			if(regExp.test(inputObj.value)){
				//release pk
				var pkReleaseFriendsUrl=String.format($.pkReleaseFriendsUrl,$.USERID,inputObj.value,$.score,$.gameId);
				function callBack(_xmlHttp){
					var jsonData=$.parse(_xmlHttp.responseText);
					if(typeof jsonData == 'object'){
						var result=Number(jsonData.result.resultcode);
						var mes=jsonData.result.resultmsg;
						switch(result){
							case 0:
								$.prompt(mes)
								//release success
								window.location=String.format($.pkIndexHtml,$.USERID,$.gameId);
								break;
							case 1:
								$.prompt(mes)
								break;
							case -1:
								$.prompt(mes)
								break;
						}
					}else{
						$.prompt($.mes);
					}
				}
				$.AJAX('GET',pkReleaseFriendsUrl,callBack,null);
			}else{
				$.prompt('您要输入不小于10的整数');
			}
		}
	}
	$.finish();
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();