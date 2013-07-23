getContacts();
var phonebook=new Array();
function setContacts(contacts)
{
    pageLoadFinish();
	
	if(contacts== null || contacts == "" )
	{
		showToast("无");
	}
	else
	{
	  phonebook = contacts.split(',');
	}
}
(function(){
	//cite global object
	var $=globalObject;
	var totalRecord=0;
	var tabType=Number($.variable('tabType'));
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
	//load my friend list
	function globalAjaxRequest(_startIndex,_boolean){
		//set request url
		var pkSelectFriendUrl=String.format($.pkSelectFriendUrl,$.USERID,$.pageSize,_startIndex,$.gameId);
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
						$.text('nowLineFriendId',jsonData.onlineNum,true);
						var detailObj=$.clear('listDetail');
						var dataList=jsonData.pkList;
						for(var i=0; i<$.pageSize; i++){
							try{
								switch(dataList[i].onlineStatus){
									case 0:
										var online='';
									case 1:
										var online='<p class="online online_56"></p>';
										break;
								}
								var dataStr='<div class="line_1"></div>'
										+'<form id="form1" method="post" action="">'
											+'<div class="blohi P_tb_10">'
												+'<label for="no2">'
													+'<div class="float_l P_lr_10">'
														+'<table class="font_18 color_2 font_w">'
															+'<tr>'
																+'<td class="">'
																	+'<div class="pos">'
																		+'<span class="head_mask_76c"></span>'
																			+'<img src="'+arrayData[i].icon+'" alt=""/>'
																	+'</div>'
																+'</td>'
																+'<td class="P_l_10 color_2">'
																	+'<P class="font_20 text_hide font_w">'
																		+arrayData[i].userName
																	+'</P>'
																	+online
																+'</td>'
															+'</tr>'
														+'</table>'
													+'</div>'
												 +'</label>'
												 +'<input id="friendCheckbox'+i+'" type="checkbox" name="no2" id="no2"  class="float_r m_tr_35" />'
											 +'</div>'
										+'</form>';
								$.text('listDetail',dataStr,false);
							}catch(ex){
								break;
							}
						}
						//a tag onclick event
						var selectAllFirendBut=$.dollar('selectAllFirendId');
						selectAllFirendBut.onclick=function(){
							for(var j=0; j<$.pageSize; j++){
								try{
									var checkboxObj=$.dollar('friendCheckbox'+j);
									if(checkboxObj.checked!='checked'){
										checkboxObj.checked=checked;
									}
								}catch(ex){
									break;
								}
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
		$.AJAX('GET',pkSelectFriendUrl,callBack,null);
	}
	//load address book list
	function addressBook(){
		$.display();
		for(var i=0; i<phonebook.length; i+=2){
			try{
				var dataStr='<div class="line_1"></div>'
					+'<form id="form1" method="post" action="">'
						+'<div class="blohi P_tb_10">'
							+'<label for="no2">'
								+'<div class="float_l P_lr_10">'
									+'<table class="font_18 color_2 font_w">'
										+'<tr>'
											+'<td class="">'
												+'<div class="pos">'
													+'<span class="head_mask_76c"></span>'
														+phonebook[i]
												+'</div>'
											+'</td>'
											+'<td class="P_l_10 color_2">'
												+'<P class="font_20 text_hide font_w">'
														+phonebook[i+1]
												+'</P>'
											+'</td>'
										+'</tr>'
									+'</table>'
								+'</div>'
							+'</label>'
							+'<input id="friendCheckbox'+i+'" type="checkbox" name="no2" id="no2"  class="float_r m_tr_35" />'
						+'</div>'
					+'</form>';
				$.text('listDetail',dataStr,false);
			}catch(ex){
				break;
			}
		}
		var selectAllFirendBut=$.dollar('selectAllFirendId');
		selectAllFirendBut.onclick=function(){
			for(var j=0; j<$.pageSize; j++){
				try{
					var checkboxObj=$.dollar('friendCheckbox'+j);
					if(checkboxObj.checked!='checked'){
						checkboxObj.checked=checked;
					}
				}catch(ex){
					break;
				}
			}
		}
	}
	//set preload function
	function globalPreload(){
		//a href attribute
		$.href('myFriendId',$.pkSelectFriendHtml,$.USERID,$.score,$.gameId,0);
		$.href('addressBookId',$.pkSelectFriendHtml,$.USERID,$.score,$.gameId,1);
		$.href('nextStepId',$.pkInviteGoodFriendHtml,$.USERID,$.score,$.gameId);
		if(tabType==0){
			globalAjaxRequest(0,true);
		}else{
			addressBook();
		}
		menuStyle('liMenu',2,tabType,'title_pk_nor','title_pk_select');
		$.finish();
	}
	//document load request url
	$.load(function(){
		globalPreload();
	});
})();