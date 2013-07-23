var USERID = "";
var ip = "";
var key = "";
var value = "";
var provinceId = null;
var provinceName = null;
var schoolInitial = null;
var schoolId = null;
var schoolName = null;
// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });


//初始化页面
function init(){

	//从URL 里面获取用户自己的ID, 这个只在个人中心有需要
	USERID = GetQueryString("USERID");
	
	//
	$("#form1").addClass("hide");
	//
	schoolInitial = (GetQueryStringNoEscape("schoolInitial"));
	provinceId  = (GetQueryStringNoEscape("provinceId"));
	provinceName = decodeURIComponent(GetQueryStringNoEscape("provinceName"));

	
	if(schoolInitial!=null&&schoolInitial!="null"){
		$("#alpha").html(schoolInitial);
		$("#alpha").removeClass("hide");
		// 这个时候要去读取学校信息
		getDataAndShow();
	}else{
		$("#alpha").addClass("hide");
	}

	 $("#selAlpha").click(function() {
		this.href =	appendParaToURL(this.href,"provinceId="+provinceId+"&provinceName="+encodeURIComponent(provinceName));
	 });

	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID )   ;
	 });
		//
	 pageLoadFinish();
}


// 从接口获取数据，并且显示出来
function getDataAndShow(){
	var interf = String.format(shcoolList_url,provinceId,schoolInitial);

	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
		   showData(data);
		 },
	   dataType: "json"
	});

	request.fail(function(textStatus) {
	 
	  addLoadErrTo("body_id");
	  //
	  pageLoadFinish();
	});
}

function sel(id){

	schoolName = data[id].schoolName;
	schoolId =  data[id].schoolId;
	window.location.href="individual-centers-profile1.html?"+"provinceId="+provinceId+"&provinceName="+encodeURIComponent(provinceName)+"&USERID="+USERID+"&schoolId="+schoolId+"&schoolName="+encodeURIComponent(schoolName);
	return;
}


// 显示 从接口获取的数据
function showData(json){
	//
	// 先填充数据
	//
	data = json.shcoolList;
	//
	$("#form1").html("");

	for(i=0;i<data.length;i++){
		p = data[i];
		str ='<div class="line_1"></div><div onclick="sel(\'%1\')" class="coll_bg blohi P_tb_10"><label for="no%1">'+
			'<div class="float_l P_lr_10"><P class="font_w font_20 m_tb_10">%2</P></div>'+
            '</label><input type="radio" name="no" id="no%1"  class="float_r m_10" /></div>';
		str =  String.format(str, i, p.schoolName);
		$("#form1").append(str);
	}

	$("#form1").removeClass("hide")
	//
	pageLoadFinish();
}


