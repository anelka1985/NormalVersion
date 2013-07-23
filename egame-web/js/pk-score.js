(function(){
	//cite global object
	var $=globalObject;
	//set preload function
	function globalPreload(){
		$.text('myScoreId',$.score,true);
		$.href('relaseId',$.pkReleaseHtml,$.USERID,$.score,$.gameId);
		$.finish();
	}
	//document load request url
	$.load(function(){
		globalPreload();
		$.dollar('againStartId').onclick=function(){
			startPk(1,0);
		}
	});
})();