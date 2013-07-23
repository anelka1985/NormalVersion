var USERID = "";
var ip = "";
var key = "";
var value = "";



// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });



// 初始化页面
function init(){

	// 从URL 里面获取用户自己的ID, 这个只在个人中心有需要
	//alert(window.location);
	USERID = GetQueryString("USERID");
	//alert("USERID：" +USERID);
	value = decodeURIComponent(GetQueryStringNoEscape("hobbydata"));
	//alert(value);
	$('#form2').addClass("hide");

	setTimeout( "getDataAndShow()",0);

	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID )   ;
	 });

}



// 从接口获取数据，并且显示出来
function getDataAndShow(){
	
	var interf =  getHobbyList_url;//   "_data_hobby.html"; //   

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

	//  直接用 json
	//$.getJSON('_data_individual-centers1.html', function(data){
		// fillData(data);
	//});   
}


// 显示 从接口获取的数据
function showData(json){
	
	$(".coll_bg").click(function() {
		
		// 先做成单选的，一点，所有的都取消
		$(".icon_radio2").addClass("icon_radio1");
		$(".icon_radio2").removeClass("icon_radio2");
		$(".pos_tx_select").addClass("pos_tx_nor");
		$(".pos_tx_select").removeClass("pos_tx_select");
	
		$(this).find("div:eq(0)").addClass("pos_tx_select");
		$(this).find("div:eq(0)").removeClass("pos_tx_nor");
		$(this).find("div:eq(1)").addClass("icon_radio2");
		$(this).find("div:eq(1)").removeClass("icon_radio1");  
	 });
	 

	 if(value!=null&&value!=""&&value.indexOf("男")!=-1){
		 if(value.indexOf("全")!=-1){
		 	$(".coll_bg:eq(1)").click();
		 }else{
		 	$(".coll_bg:eq(2)").click();
		 }
	 }else{
		 $(".coll_bg:eq(0)").click();
	 }
	

	var isFound = false;
	$('#form2_area').html('');
	for(i=0;i<json.hobbyList.length;i++){
		hobby = json.hobbyList[i];
		
		if(i%3==0){
			$('#form2_area').append('<div class="P_tb_5 fri_greet_a" id="tmp_'+((i-i%3)/3)+'">');
		}
		str = '<span class="fri_greet game_checkbox" id="fri_greet_%4"><div class="float_l m_tlr_5 icon_checkbox1"><input type="checkbox" name="game" id="no%1" class="hide"/></div><label for="no%2" class="fri_greet_t">%3</label></span>';
	  
	  	str =  format(str, hobby.interestId, hobby.interestId,hobby.interest,i);
		$('#tmp_'+((i-i%3)/3)).append(str);

		if(!isFound&&value!=null&&value!=""&&value.indexOf(hobby.interest)!=-1){
			//alert(hobby.interest);
			$(".fri_greet").find("div").removeClass("icon_checkbox2");
			$(".fri_greet").find("div").addClass("icon_checkbox1");
			$(".fri_greet").find("input").attr("checked",false);

			$('#fri_greet_'+(i)).find("div").removeClass("icon_checkbox1");
			$('#fri_greet_'+(i)).find("div").addClass("icon_checkbox2");
			$('#fri_greet_'+(i)).find("input").attr("checked",true);
			//isFound = true;
		}
		
	}

	$('#form2').removeClass("hide");

	
	$(".fri_greet").bind("click",function(event){   //.click(function() {
		  event.stopPropagation();
		// 先做成单选的，一点，所有的都取消
		$(".fri_greet").find("div").removeClass("icon_checkbox2");
		$(".fri_greet").find("div").addClass("icon_checkbox1");
		$(".fri_greet").find("input").attr("checked",false);
		//
		$(this).find("div").removeClass("icon_checkbox1");
		$(this).find("div").addClass("icon_checkbox2");
		$(this).find("input").attr("checked",true);
	 });

	pageLoadFinish();

	
}

function isCheck(arr){
	for(i=0;i<arr.length;i++){
		r = arr[i];
		if(r.checked){
			return true;
		}
	}
	return false;
}

function getCheckId(arr){
	for(i=0;i<arr.length;i++){
		r = arr[i];
		if(r.checked){
			return r.id;
		}
	}
	return 0;
}

function modify(){
	// 先检查有没有没点的
	var f1 = $("form#form1 input[type=radio]");
	if(!isCheck(f1)){
		showToast("请选择您要找的好友性别！");
		return;
	}
	var f2 = $("form#form2 input[type=checkbox]");
	if(!isCheck(f2)){
		showToast("请选择您的爱好!");
		return;
	}

	//
	var sex = getCheckId(f1).replace("gender","");
	var interestId = getCheckId(f2).replace("no","");

	// 调本地LOAD
	pageLoadStart();

	// 调接口	 

	var interf =  String.format(modifyMyInfo_url,USERID ,"sex",sex); 
	interf = interf+'&interestId='+interestId;

	//     '_data_getWage.html'; //  
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
