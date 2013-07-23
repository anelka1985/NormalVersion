(function(){
	//cite global object
	var $=globalObject;
	var clickTimes=true;
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
		var regExp=RegExp('^([1-9]\\d)\\d{1,}$');
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
					if(Number(inputObj.value)){
						$.prompt('输入PK币不足100');
					}else{
						$.prompt('请输入您的押注金额');
					}			
				}
			}
		}
		inputObj.onkeyup=function(){
			inputObj.value=inputObj.value.replace(/[^0-9]/g,'');
		};
		var releaseBut=$.dollar('releaseId');
		releaseBut.onclick=function(){
			if(!clickTimes){
				return false;
			}
			if(regExp.test(inputObj.value)){
				//release pk
				var releaseUrl=String.format($.releaseUrl,$.USERID,inputObj.value,$.score,$.gameId);
				function callBack(_xmlHttp){
					var jsonData=$.parse(_xmlHttp.responseText);
					if(typeof jsonData == 'object'){
						var result=Number(jsonData.result.resultcode);
						var mes=jsonData.result.resultmsg;
						switch(result){
							case 0:
								clickTimes=false;
								$.prompt(mes)
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
						$.dispose();
					}
				}
				$.AJAX('GET',releaseUrl,callBack,null);
			}else{
				if(inputObj.value==''){
					$.prompt('请输入您的押注金额');
				}
				if(Number(inputObj.value)){
					$.prompt('输入PK币不足100');
				}else{
					$.prompt('请输入您的押注金额');
				}	
			}
		}
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();