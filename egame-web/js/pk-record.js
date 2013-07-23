(function(){
	//cite global object
	var $=globalObject;
	var totalRecord=0;
	function globalAjaxRequest(_startIndex,_boolean){
		//set request url
		var pkRecordUrl=String.format($.pkRecordUrl,$.USERID,$.pageSize,_startIndex,$.gameId);
		//set callback function
		function callBack(_xmlHttp){
			var jsonData=$.parse(_xmlHttp.responseText);
			if(typeof jsonData == 'object'){
				var result=Number(jsonData.result.resultcode);
				var mes=jsonData.result.resultmsg;
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
						var dataList=jsonData.PkRecordList;
						for(var i=0; i<$.pageSize; i++){
							try{
								switch(dataList[i].myResult){
									case 0:
										var bonusStr='<span class="color_7">'+dataList[i].bonus+'</span>';
										break;
									case 1:
										var bonusStr='<span class="color_3">'+dataList[i].bonus+'</span>';
										break;
									case 2:
										var bonusStr='<span class="color_3">'+dataList[i].bonus+'</span>';
										break;
								}
								var dataStr='<tr class="dunit1_out2">'
										+'<td class="dunit1_inner3">'
											+'<div id="userIconId'+i+'" class="collLeft pos">'
												+'<span class="head_mask_76c"></span>'
													+'<img src="'+dataList[i].rivalIcon+'" alt=""/>'
											+'</div>'
											+'<div class="listRight">'
												+'<P class="font_20 P_t_25 text_align_left text_hide">'
													+dataList[i].rivalName
												+'</P>'
											+'</div>'
										+'</td>'
										+'<td class="dunit1_inner3">'
											+bonusStr
										+'</td>'
										+'<td class="dunit1_inner3">'
											+'<div class="wid_95 btu_green_com pos ">'
												+'<a id="op'+i+'">'
													+'<span class="btu_man_com2 btu_green_com2 btu_man_left"></span>'
													+'<span class="btu_mood_right"></span>'
													+'<span class="icon_zh">打招呼</span>'
												+'</a>'
											  +'</div>';
										+'</td>'
									+'</tr>';
								$.text('listDetail',dataStr,false);
								$.href('op'+i,$.individualCentersFriendgreetHtml,$.USERID,dataList[i].rivalUserId);
							}catch(ex){
								break;
							}
						}
						var regExp=/\d{1,}$/;
					 	for(var j=0; j<$.pageSize; j++){
					 		var userIconObj=$.dollar('userIconId'+j);
					 		userIconObj.onclick=function(){
					 			var num=this.id.match(regExp);
					 			showDetail('friendId',dataList[num].rivalUserId);
					 		}
					 	}
						break;
					case 1:
						$.display();
						$.prompt(mes);
						break;
					case -1:
						$.display();
						$.prompt(mes);
						break;
				}
			}else{
				$.dispose();
			}
		}
		//ajax request
		$.AJAX('GET',pkRecordUrl,callBack,null);
	}
	//set preload function
	function globalPreload(){
		//a href attribute
		globalAjaxRequest(0,true);
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();