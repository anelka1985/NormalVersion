//set $
var globalObject=globalObject ||(function () {
	var individualCentersFriendgreetHtml_html='individualcenters-friendgreet.html?USERID=%1&friendId=%2';
	//inside object
	var inside={
		/*private object attribute name
		 * baseLocationUrl: server location
		 * baseRequestUrl:set request base url
	  	 * pageSize:every style numbers
		 */
		//set base url
		baseLocationUrl:'',
		startIndex:0,
		pageSize:10,
		/*private object method name
		 * variable:get url variable
		 */
		//get url variable
		variable:function(_argName){
			if(arguments[1]=='undefined'){
				return GetQueryString(_argName)
			}else{
				return GetQueryString(_argName)?GetQueryString(_argName):arguments[1];
			}
		}
	};
	//new object
	var $={
		/*user defined object attribute
		* attribute name:
		* xmlHttp:get and set AJAX object
		*/
		//set base variable
		startIndex:inside.startIndex,
		pageSize:inside.pageSize,
		//pk html
		pkIndexHtml:pkIndexHtml_html,
		pkMypkHtml:pkMypkHtml_html,
		pkExchangepkCurrencyHtml:pkExchangepkCurrencyHtml_html,
		pkModeHtml:pkModeHtml_html,
		pkRecordHtml:pkRecordHtml_html,
		pkScoreHtml:pkScoreHtml_html,
		pkReleaseHtml:pkReleaseHtml_html,
		pkSelectFriendHtml:pkSelectFriendHtml_html,
		fullValueHtml:fullValueHtml_html,
		pkInviteGoodFriendHtml:pkInviteGoodFriendHtml_html,
		pkChallengeresultsHtml:pkChallengeresultsHtml_html,
		pkChallengeHtml:pkChallengeHtml_html,
		darenListHtml:darenListHtml_html,
		individualCentersFriendgreetHtml:individualCentersFriendgreetHtml_html,
		//pk request url
		pkIndexUrl:pkIndexUrl_url,
		pkMypkUrl:pkMypkUrl_url,
		pkRecordUrl:pkRecordUrl_url,
		pkExchangepkCurrencyUrl:pkExchangepkCurrencyUrl_url,
		accountUrl:accountUrl_url,
		removeChallengeUrl:removeChallengeUrl_url,
		startChallengeUrl:startChallengeUrl_url,
		releaseUrl:releaseUrl_url,
		pkSelectFriendUrl:pkSelectFriendUrl_url,
		pkReleaseFriendsUrl:pkReleaseFriendsUrl_url,
		changeAidouUrl:changeAidouUrl_url,
		endAcceptUrl:endAcceptUrl_url,
		freeReceivePkbiUrl:freeReceivePkbiUrl_url,
		darenAllListUrl:darenAllListUrl_url,
		darenMyListUrl:darenMyListUrl_url,
		//pk request variable
		USERID:inside.variable('USERID'),
		gameId:inside.variable('gameId'),
	    score:inside.variable('score'),
	    resultId:inside.variable('resultId'),
		//set tag object
		xmlHttp:(function(){
			var xmlHttp=null;
			if(window.ActiveXObject){
				var xmlHttpTypes=['Microsoft.XMLHTTP','Msxml2.XMLHTTP.6.0','Msxml2.XMLHTTP.5.0','Msxml2.XMLHTTP.4.0','Msxml2.XMLHTTP.3.0','Msxml2.XMLHTTP']
				for(var i in xmlHttpTypes){
					try{
						xmlHttp=new window.ActiveXObject(xmlHttpTypes[i]);
						if(xmlHttp!=null){
							break;
						}
					}catch(ex){
					}
				}
			}else if(window.XMLHttpRequest){
				xmlHttp=new window.XMLHttpRequest();
			}
			return xmlHttp;
		})(),
		/*user defined object method
		* method name:
		* dollar:get tag object
		* href:set attribute
		* AJAX:request url method
		* parse:parse string data
		* date:format date 
		* text:add data
		* clear:clear content
		* style:style number style
		* pages:pages number
		* variable:get location variable,
		* load:document load
		* finish:close load icon
		* start:open load icon
		* confirm:confirm messages
		*/
		//get tag object
		dollar:function(_id){
			return document.getElementById(_id);
		},
		//set A's href of attribute
		href:function(_id,_url){
			var obj=$.dollar(_id);
			var arg=[];
			for(var i=1; i<arguments.length; i++){
				arg.push(arguments[i]);
			}
			var url=String.format.apply(arguments.callee,arg);
			if(obj.nodeType==1){
				if(obj.nodeName=='A'){
					obj.setAttribute('href',url);
				}else{
					window.location=url;
				}				
			}
			return url;
		},
		//request url method
		AJAX:function(_method,_requestUrl,_callBack,_data){
			printToConsole(_requestUrl);
			var xmlHttp=$.xmlHttp;
			$.start();
			if(xmlHttp!=null){
				xmlHttp.open(_method,_requestUrl,true);
				if(_data){
					xmlHttp.setRequestHeader('content-type','application/x-www-form-urlencoded');
				}
				xmlHttp.onreadystatechange=function(){
					if(xmlHttp.readyState==4){
						if(xmlHttp.status==200){
							_callBack(xmlHttp);
						}else{
							$.dispose();
						}
					}
				}
				xmlHttp.send(_data);
				$.finish();
			}else{
				return false;
			}
			},
		//parse json data
		parse:function(_strData){
			eval('var obj='+_strData);
			return obj;
		},
		//add data
		text:function(_id,_str,_boolean){
			var obj=$.dollar(_id);
			if(obj.nodeName=='IMG'){
				obj.src=_str;
				return;
			}
			if(_boolean){
				obj.innerHTML='';
			}
			obj.innerHTML+=_str;
		},
		//clear text
		clear:function(_id){
			var obj=$.dollar(_id);
			obj.innerHTML='';
			return obj;
		},
		//style number style
		style:function(_id,_arg1,_arg2){
			var obj=$.dollar(_id);
			var span=obj.getElementsByTagName('span');
			if(arguments[1]==undefined && arguments[2]==undefined){
				obj.style.display='none';
			}else{
				if(_arg1>_arg2){
					obj.className='btu_page btu_green_s pos';
					span[0].className='btu_man_com2 btu_green_s_com btu_green_s_left';
					span[1].className='btu_green_s_right';
				}else{
					obj.className='btu_page btu_gra_s pos btu_page_com';
					span[0].className='btu_man_com2 btu_gra_s_com btu_gra_s_left';
					span[1].className='btu_gra_s_right';
				}		
			}
		},
		//pages number 
		pages:function(_fun,_tailEnd){
			var tailPageObj=$.dollar('tailPageId');
			var nextPageObj=$.dollar('nextPageId');
			var previousPageObj=$.dollar('previousPageId');
			var firstPageObj=$.dollar('firstPageId');
			if(_tailEnd/$.pageSize==0){
				_tailEnd=_tailEnd-$.pageSize;
			}
			nextPageObj.onclick=function(){
				$.startIndex+=$.pageSize;
				if($.startIndex>_tailEnd){
					$.startIndex=_tailEnd;
				}
				_fun($.startIndex,false);
			}
			tailPageObj.onclick=function(){
				_fun(_tailEnd,false);
				$.startIndex=_tailEnd;
			}
			previousPageObj.onclick=function(){
				$.startIndex-=$.pageSize;
				if($.startIndex<0){
					$.startIndex=0;
				}
				_fun($.startIndex,false);
			}
			firstPageObj.onclick=function(){
				_fun(0,false);
				$.startIndex=0;
			}
		},
		//prompt error information
		prompt:function(_meg){
			showToast(_meg);
		},
		//get location variable
		variable:inside.variable,
		//onload document
		load:function(){
			if(typeof arguments[0]=='function'){
				window.onload=arguments[0];	
			}
		},
		display:function(){
			$.style('firstButId');
			$.style('previousButId');
			$.style('nextButId');
			$.style('tailButId');
		},
		//public andriod
		finish:function(){
			pageLoadFinish();
		},
		start:function(){
			pageLoadStart();
		},
		confirm:function(_mes,_callback){
			showChoiceDialog(_mes,_callback);
		},
		dispose:function(){
			var body=document.body;
			body.innerHTML='<p style=\"text-align: center\;margin-top:100px\">网络异常！请稍后再试！</p>';
		}
	};
	return $;
})();
