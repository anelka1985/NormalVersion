(function(){
	//cite global object
	var $=globalObject;
	//variable
	var nickName=getPkUserName();
	var myIcon=$.variable('myIcon');
	var userIcon=$.variable('userIcon');
	var bonus=$.variable('bonus');
	var pkId=$.variable('pkId');
	function globalAjaxRequest(){
		var startChallObj=$.dollar('startChallId');
		startChallObj.onclick=function(){
			var startChallengeUrl=String.format($.startChallengeUrl,$.USERID,pkId);
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
	//set preload function
	function globalPreload(){
		//a href attribute
		$.text('pkUserNameId',nickName,true);
		$.text('myIconId',myIcon,true);
		$.text('userIconId',userIcon,true);
		$.text('pkBonusId',bonus,true);
		globalAjaxRequest();
		$.finish();
	}
	$.load(function(){
		globalPreload();
	});
})();