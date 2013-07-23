/**
 * @author Administrator
 */
//城市列表
var provinceAndCityList;
//搜索好友参数
var gender;
var age;
var city;
var province;
var provinceName;
var cityName;

var isInit =true;



$(document).ready(function() {
	USERID = GetQueryStringNoEscape("USERID");
	//alert('GetQueryString("provinceName") '+GetQueryString("provinceName"));
	try{
	provinceName = decodeURIComponent(GetQueryStringNoEscape("provinceName"));
	cityName = decodeURIComponent(GetQueryStringNoEscape("cityName"));
	}catch(ee){
		alert(ee);
	}
	init();
 
	pageLoadFinish();
});
function init() {
 getProvince();
}

function getProvince() {
	var interf=  provinceAndAllCityList_url;
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			provinceAndCityList = data;
	        fillProvince(data);
		},
		dataType : "json"
	});
}

function fillProvince (data) {
	var content = "";
	for (var i=0; i < data.provinceAndCityList.length; i++) {
		content = content 
		+'<option value = "'+i+'">'+data.provinceAndCityList[i].provinceName+'</option>';
	};
	$("#province").html(content);
	
	if(isInit){
	
		if(provinceName==""||provinceName==null){
			provinceName = "北京";
			cityName = "朝阳区";
		
		}

	  var count=$("#province option").length;
	  for(var i=0;i<count;i++)  
		 {  if($("#province").get(0).options[i].text == provinceName)  
			{  
				$("#province").get(0).options[i].selected = true;  
				break;  
			}  
		}
	}

	provincechange();
	
	
}

function provincechange () {
  
	
	var index = $("#province option:selected").val();
  var content = "";
	for (var i=0; i < provinceAndCityList.provinceAndCityList[index].citys.length; i++) {
		content = content 
		+'<option value = "'+i+'">'+provinceAndCityList.provinceAndCityList[index].citys[i].cityName+'</option>';
	};

	$("#city").html(content);
	
	//
	if(isInit){
		isInit = false;

				
					//	$("#province option[text='"+provinceName+"']").attr("selected", true);

			var count=$("#city option").length;
			  for(var i=0;i<count;i++)  
				 {  if($("#city").get(0).options[i].text == cityName)  
					{  
						$("#city").get(0).options[i].selected = true;  
						break;  
					}  
				}
		 

			//	$("#city option[text='"+cityName+"']").attr("selected", true);


		


	}
}




function modify(){
	
	// 先本地检查有没有数据
	var p = provinceAndCityList.provinceAndCityList[$("#province option:selected").val()];
	province = p.provinceName;
	var cs = $("#city option:selected").val();
	var c = p.citys[cs];
	city = c.cityName;



	//alert("111USERID  "+USERID+" schoolId "+schoolId);
	// 调本地LOAD
	pageLoadStart();



	
	var interf = String.format(modifyMyInfo_url,USERID,"province",province); //    '_data_getWage.html'; //  
	interf = interf+'&city='+city;


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