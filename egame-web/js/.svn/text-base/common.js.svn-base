//页面开始加载
pageLoadStart();

//email正则表达式
var emailReg = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

//手机号正则表达式
var phoneReg = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}";

//昵称正则表达式
var nicknameReg = "[\\d\\w\u4E00-\u9FFF]{1,15}";

//公司正则表达式
var companyReg = "[\\d\\w\u4E00-\u9FFF]{1,20}";

//签名
var signatureReg = ".{1,60}";

function closeAd() {
	$("#ad").hide();
	$("#ad_shadow").hide();
}

// 正则表达式, 获取 URL 中的参数 
function     GetQueryString(name)   
{   
     var     reg     =   new   RegExp("(^|&)"+     name     +"=([^&]*)(&|$)");   
     var     r     =     window.location.search.substr(1).match(reg);   
     if     (r!=null)   return     unescape(r[2]);   return   null;   
}   

// 正则表达式, 获取 URL 中的参数 
function     GetQueryStringNoEscape(name)   
{   
     var     reg     =   new   RegExp("(^|&)"+     name     +"=([^&]*)(&|$)");   
     var     r     =     window.location.search.substr(1).match(reg);   
     if     (r!=null)   return   (r[2]);   return   null;   
}

// 把参数加到URL里面去
function  appendParaToURL(url, para){
	if(url.indexOf("?")>=0){
		return url+"&"+para;
	}else{
		return url+"?"+para;
	}
}

//字符串格式化函数：
String.format = function(str) {
    var args = arguments, re = new RegExp("%([1-" + args.length + "])", "g");
    return String(str).replace(
        re,
        function($1, $2) {
            return args[$2];
        }
    );
};

/**
 * 给文本中url链接添加a标签onclick，点击在系统浏览器中打开
 * 示例
 * var v = "爱游戏网站：http://game.189.cn/";
 * alert(v.httpHtml());
 */
String.prototype.httpAddOnclick = function(){
	var reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)/g;
	return this.replace(reg, '<a onClick="showSysBrowse(\'$1$2\')" class="color_1">$1$2</a>');
};

/**
 * 给文本中url链接添加a标签href
 * 示例
 * var v = "爱游戏网站：http://game.189.cn/";
 * alert(v.httpHtml());
 */
String.prototype.httpAddHref = function(){
	var reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)/g;
	return this.replace(reg, '<a href="$1$2" class="color_1">$1$2</a>');
};

//分页
//startIndex-当前页开始索引
//pageSize-每页数量
//totalPage-总数
function pageChange (startIndex,pageSize,totalPage) 
{
  var page = Math.ceil(startIndex/pageSize);
  
  if (page == 0)
  {
   	page =1;
  };
  
  var pageHome=$("#pageHome");
  var pageHomeLeft=$("#pageHomeLeft");
  var pageHomeRight=$("#pageHomeRight");
  
  var pageDown=$("#pageDown");
  var pageDownLeft=$("#pageDownLeft");
  var pageDownRight=$("#pageDownRight");
  
  var pageUp=$("#pageUp");
  var pageUpLeft=$("#pageUpLeft");
  var pageUpRight=$("#pageUpRight");
  
  $("#currentPage").text(page);
  $("#totalPage").text("/" + totalPage);
  //只有一页
  if (startIndex=="0" && totalPage=="1") 
  {
  	//隐藏分页bar
  }
  //最后一页
  else if(page == totalPage)
  {
  	//显示分页bar
	$("#button").removeClass("hide_gone");
  	//首页
  	pageHome.attr("class", ""); 
  	pageHomeLeft.attr("class", ""); 
  	pageHomeRight.attr("class", "");
  	
  	pageHome.addClass("but_man btu_green_s pos");
  	pageHomeLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageHomeRight.addClass("btu_green_s_right");
  	
  	//下一页
  	pageDown.attr("class", ""); 
  	pageDownLeft.attr("class", ""); 
  	pageDownRight.attr("class", "");
  	pageDown.attr("onclick", ""); 
  	
  	pageDown.addClass("but_man btu_gra_s pos btu_page_com");
  	pageDownLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageDownRight.addClass("btu_gra_s_right");
  	
  	//上一页
  	pageUp.attr("class", ""); 
  	pageUpLeft.attr("class", ""); 
  	pageUpRight.attr("class", "");
  	
  	pageUp.addClass("but_man btu_green_s pos");
  	pageUpLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageUpRight.addClass("btu_green_s_right");
  	pageUp.attr("onclick", "pageUp()");
  	pageHome.attr("onclick", "pageHome()"); 
  }
  //第一页
  else if(page=="1") {
  	$("#button").removeClass("hide_gone");
  	//首页
  	pageHome.attr("class", ""); 
  	pageHomeLeft.attr("class", ""); 
  	pageHomeRight.attr("class", "");
  	pageHome.attr("onclick", ""); 
  	
  	pageHome.addClass("but_man btu_gra_s pos btu_page_com");
  	pageHomeLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageHomeRight.addClass("btu_gra_s_right");
  	
  	//下一页
  	pageDown.attr("class", ""); 
  	pageDownLeft.attr("class", ""); 
  	pageDownRight.attr("class", "");
  	
  	pageDown.addClass("but_man btu_green_s pos");
  	pageDownLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageDownRight.addClass("btu_green_s_right");
  	
  	//上一页
  	pageUp.attr("class", ""); 
  	pageUpLeft.attr("class", ""); 
  	pageUpRight.attr("class", "");
  	pageUp.attr("onclick", ""); 
  	
  	pageUp.addClass("but_man btu_gra_s pos btu_page_com");
  	pageUpLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageUpRight.addClass("btu_gra_s_right");
  	pageDown.attr("onclick", "pageDown()"); 
  }
  //中间页
  else
  {
	$("#button").removeClass("hide_gone");
  	//首页
  	pageHome.removeClass("but_man btu_gra_s pos btu_page_com");
  	pageHomeLeft.removeClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageHomeRight.removeClass("btu_gra_s_right");
  	
  	pageHome.addClass("but_man btu_green_s pos");
  	pageHomeLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageHomeRight.addClass("btu_green_s_right");
  	
  	//上一页
  	pageUp.removeClass("but_man btu_gra_s pos btu_page_com");
  	pageUpLeft.removeClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageUpRight.removeClass("btu_gra_s_right");
  	
  	pageUp.addClass("but_man btu_green_s pos");
  	pageUpLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageUpRight.addClass("btu_green_s_right");
  	
  	pageHome.attr("onclick", "pageHome()"); 
  	pageDown.attr("onclick", "pageDown()"); 
  	pageUp.attr("onclick", "pageUp()"); 
  };
}


//解析表情
function analyFaceImage(str){
	var ex = /\[([^\[\]]){1,}\]/g;
	var output = [];
	var imgHtml;
    if(str == null || str =='') return str;
	if(str.match(ex)){
		output = str.match(ex);
	}else{
		return str;
	}
	for(var i = 0; output[i]; i++){
		var resultImageId = null;
		switch(output[i]){
			case "[微笑]":
				resultImageId = 0;
				break;
			case "[大笑]":
				resultImageId = 1;
				break;
			case "[害羞]":
				resultImageId = 2;
				break;
			case "[开心]":
				resultImageId = 3;
				break;
			case "[哇喔]":
				resultImageId = 4;
				break;
			case "[惊讶]":
				resultImageId = 5;
				break;
			case "[花心思]":
				resultImageId = 6;
				break;
			case "[幸运]":
				resultImageId = 7;
				break;
			case "[媚眼]":
				resultImageId = 8;
				break;
			case "[喜欢]":
				resultImageId = 9;
				break;
			case "[酷]":
				resultImageId = 10;
				break;
			case "[汗]":
				resultImageId = 11;
				break;
			case "[恐慌]":
				resultImageId = 12;
				break;
			case "[发火]":
				resultImageId = 13;
				break;
			case "[坏笑]":
				resultImageId = 14;
				break;
			case "[难过]":
				resultImageId = 15;
				break;
			case "[委屈]":
				resultImageId = 16;
				break;
			case "[大哭]":
				resultImageId = 17;
				break;
			case "[哼哼]":
				resultImageId = 18;
				break;
			case "[不理你]":
				resultImageId = 19;
				break;
			case "[折磨]":
				resultImageId = 20;
				break;
		}
		if(resultImageId !== null){
			imgHtml = '<span><img width="35" src="images/egame_face' + resultImageId.toString() + '.png" style="vertical-align:bottom;"/><span/>'
			str = str.replace(output[i],imgHtml);
			
		}
	}
	return str;
}

//解析打招呼
function analyGreetImage(str){
	var ex = /\[([^\[\]]){1,}\]/g;
	var output = [];
	var imgHtml;
	if(str.match(ex)){
		output = str.match(ex);
	}else{
		return str;
	}
	for(var i = 0; output[i]; i++){
		var resultImageId = null;
		switch(output[i]){
			case "[动一下]":
				resultImageId = 1;
				break;
			case "[捏一下]":
				resultImageId = 2;
				break;
			case "[抛媚眼]":
				resultImageId = 3;
				break;
			case "[踩一踩]":
				resultImageId = 4;
				break;
			case "[握个手]":
				resultImageId = 5;
				break;
			case "[摸一下]":
				resultImageId = 6;
				break;
			case "[拥抱]":
				resultImageId = 7;
				break;
			case "[飞吻]":
				resultImageId = 8;
				break;
			case "[打招呼]":
				resultImageId = 9;
				break;
			case "[挠痒痒]":
				resultImageId = 10;
				break;
			case "[给一拳]":
				resultImageId = 11;
				break;
			case "[电一下]":
				resultImageId = 12;
				break;
			case "[微笑]":
				resultImageId = 13;
				break;
			case "[捏捏脚]":
				resultImageId = 14;
				break;
			case "[咬一口]":
				resultImageId = 15;
				break;
		}
		if(resultImageId !== null){
			imgHtml = '<span><img width="30" height="30" src="images/icon_greet' + resultImageId.toString() + '.png" style="vertical-align:bottom;"/><span/>'
			str = str.replace(output[i],imgHtml);
			
		}
	}
	return str;
}


function UrlEncode(str){
	str = str.replace(/%/g,'*');
	str = str.replace(/</g,'*');
	str = str.replace(/>/g,'*');
	str = str.replace(/ /g,'*');
	
	str = encodeURIComponent(str);
   
   
   return str; 
} 

function handleNewMessage(data){
	if(data.messageMap !== null){
		var friendMessage = data.messageMap.friendMessage;
		var systemMessage = data.messageMap.systemMessage;
		if(friendMessage>0||systemMessage>0){
			showNewMessage();
		}
	}
}

//好友模块，我的账户 分页专用，其他人勿动
function pageChangeOther (startIndex,pageSize,totalPage) 
{
  var page = Math.ceil(startIndex/pageSize);
  
  if (page == 0)
  {
   	page =1;
  };
  
  var pageHome=$("#pageHome");
  var pageHomeLeft=$("#pageHomeLeft");
  var pageHomeRight=$("#pageHomeRight");
  
  var pageDown=$("#pageDown");
  var pageDownLeft=$("#pageDownLeft");
  var pageDownRight=$("#pageDownRight");
  
  var pageUp=$("#pageUp");
  var pageUpLeft=$("#pageUpLeft");
  var pageUpRight=$("#pageUpRight");
  
  var lastPage=$("#lastPage");
  var lastPageLeft=$("#lastPageLeft");
  var lastPageRight=$("#lastPageRight");
  
  //只有一页
  if (startIndex=="0" && totalPage=="1") 
  {
  	//隐藏分页bar
  }
  //最后一页
  else if(page == totalPage)
  {
  	//首页
  	pageHome.attr("class", ""); 
  	pageHomeLeft.attr("class", ""); 
  	pageHomeRight.attr("class", "");
  	
  	pageHome.addClass("btu_page btu_green_s pos");
  	pageHomeLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageHomeRight.addClass("btu_green_s_right");
  	//上一页
  	pageUp.attr("class", ""); 
  	pageUpLeft.attr("class", ""); 
  	pageUpRight.attr("class", "");
  	
  	pageUp.addClass("btu_page btu_green_s pos");
  	pageUpLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageUpRight.addClass("btu_green_s_right");
  	
  	//下一页
  	pageDown.attr("class", ""); 
  	pageDownLeft.attr("class", ""); 
  	pageDownRight.attr("class", "");
  	
  	pageDown.addClass("btu_page btu_gra_s pos btu_page_com");
  	pageDownLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageDownRight.addClass("btu_gra_s_right");
  	
  	//末页
  	lastPage.attr("class", ""); 
  	lastPageLeft.attr("class", ""); 
  	lastPageRight.attr("class", "");
  	
  	lastPage.addClass("btu_page btu_gra_s pos btu_page_com");
  	lastPageLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	lastPageRight.addClass("btu_gra_s_right");
  }
  //第一页
  else if(page=="1") {
  	//首页
  	pageHome.attr("class", ""); 
  	pageHomeLeft.attr("class", ""); 
  	pageHomeRight.attr("class", "");
  	
  	pageHome.addClass("btu_page btu_gra_s pos btu_page_com");
  	pageHomeLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageHomeRight.addClass("btu_gra_s_right");
  	
  	//上一页
  	pageUp.attr("class", ""); 
  	pageUpLeft.attr("class", ""); 
  	pageUpRight.attr("class", "");
  	
  	pageUp.addClass("btu_page btu_gra_s pos btu_page_com");
  	pageUpLeft.addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
  	pageUpRight.addClass("btu_gra_s_right");
  	
  }
  //中间页
  else
  {
  	//首页
  	pageHome.attr("class","");
  	pageHomeLeft.attr("class","");
  	pageHomeRight.attr("class","");
  	
  	pageHome.addClass("btu_page btu_green_s pos");
  	pageHomeLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageHomeRight.addClass("btu_green_s_right");
  	
  	//上一页
  	pageUp.attr("class","");
  	pageUpLeft.attr("class","");
  	pageUpRight.attr("class","");
  	
  	pageUp.addClass("but_man btu_green_s pos");
  	pageUpLeft.addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
  	pageUpRight.addClass("btu_green_s_right");
  	
  }
}
