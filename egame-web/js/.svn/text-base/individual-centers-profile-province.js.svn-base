var USERID = "";
var ip = "";
var key = "";
var value = "";
var data ="";

// 页面读取完毕的时候，初始化数据
$(document).ready(function(){
	init();
 });


//初始化页面
function init(){

	//从URL 里面获取用户自己的ID, 这个只在个人中心有需要
	USERID = GetQueryString("USERID");

	getDataAndShow();

	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID )   ;
	 });
}

// 从接口获取数据，并且显示出来
function getDataAndShow(){
	//
	
	var interf = provinceAndCityList_url;// '_data_province.html'; //   

	//
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

function sel(id){
	//alert(id+"  "+data[id].provinceName);
	window.location.href="individual-centers-profile1.html?"+"provinceId="+data[id].provinceId+"&provinceName="+encodeURIComponent(data[id].provinceName)+"&USERID="+USERID;
	return;
}


// 显示 从接口获取的数据
function showData(json){
	//
	// 先填充数据
	//
	data = json.provinceAndCityList;
	//
	$("#form1").html("");

	for(i=0;i<data.length;i++){
		p = data[i];
		str = '<div class="btu_mood_com btu_green_com pos" onclick="sel(\'%3\')" ontouchstart="this.style.border=\'thin solid #D2D2D2\'" ontouchend="this.style.border=\'\'" >'+
        	'<span class="btu_man_com2 btu_green_com2 btu_man_left"></span>'+
            '<span class="btu_mood_right"></span>'+
                '<div class="btu_prof_com">'+
                    '<input type="radio" name="number" id="no%1"  class="float_l pro_deinput" />'+
                    '<label for="no%1" class="btu_prof_tes font_w wid_all">'+
						'%2'+
                     '</label>'+
                 '</div>'+
			'</div>';
		str =  String.format(str, p.provinceId, p.provinceName,i);
		$("#form1").append(str);
	}

	$("#form1").removeClass("hide")
	//
	pageLoadFinish();
}


