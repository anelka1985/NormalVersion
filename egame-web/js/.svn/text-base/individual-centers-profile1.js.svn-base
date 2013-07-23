var USERID = "";
var ip = "";
var key = "";
var value = "";
var provinceName = null;
var provinceId = null;
var schoolName= null;
var schoolId = null;
// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });


//初始化页面
function init(){

	//从URL 里面获取用户自己的ID, 这个只在个人中心有需要
	USERID = GetQueryStringNoEscape("USERID");
	
	//alert("USERID "+USERID);
	provinceName = decodeURIComponent(GetQueryStringNoEscape("provinceName"));
	provinceId = (GetQueryStringNoEscape("provinceId"));
	schoolName = decodeURIComponent(GetQueryStringNoEscape("schoolName"));
	schoolId = (GetQueryStringNoEscape("schoolId"));
	//showToast("provinceId "+provinceId);
	//alert(provinceName+"  "+provinceId);
	if(provinceName!=null&&provinceName!="null"){
		$("#province").html(provinceName);
		$("#province").removeClass("hide");
	
		if(schoolName!=null&&schoolName!="null"){
			$("#school").html(schoolName);
			$("#school").removeClass("hide");
		}
	}

	//alert("USERID：" +USERID);
	$("#school_link").click(function() {
		if(provinceId==null||provinceId=="null"){
			showToast("请先选择省份!");
			return false;
		}
	 });


	

	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID+"&provinceId="+provinceId+"&provinceName="+encodeURIComponent(provinceName));
	 });

	 pageLoadFinish();
}



function modify(){
	
	// 先本地检查有没有数据
	if(provinceId==null||provinceId=="null"){
			showToast("请先选择省份!");
			return false;
		}
	if(schoolId==null||schoolId=="null"){
			showToast("请选择学校!");
			return false;
		}

	//alert("111USERID  "+USERID+" schoolId "+schoolId);
	// 调本地LOAD
	pageLoadStart();

	// 调接口	 
	

	
	var interf = String.format(modifyMyInfo_url,USERID,"schoolId",schoolId); //    '_data_getWage.html'; //  
	
	//
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){

		if(data.result.resultcode == 0)
		{
			window.location.href="individual-centers-profile.html?USERID="+USERID;
		}
		
		// 隐藏本地LOAD
		 pageLoadFinish();

		 // TOAST 显示结果
		showToast(data.result.resultmsg);
		
		 },
	   dataType: "json"
	});

	request.fail(function(textStatus) {

		 // TOAST 显示结果
		showToast("网络异常！");	
		// 隐藏本地LOAD
		pageLoadFinish();
	});
}
