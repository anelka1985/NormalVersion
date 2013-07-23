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
var hobby;
var nickName;
$(document).ready(function() {
	USERID = GetQueryString("USERID");
	$("#myfriend_index").attr("href", "goodfriend-myfriend.html?USERID=" + USERID);
	$("#possiblefriend_index").attr("href", "goodfriend-possiblefriend.html?USERID=" + USERID);
	$("#searchfriend_index").attr("href", "goodfriend-findfri.html?USERID=" + USERID);
	$("#invitefriend").attr("href", "goodfriend-invfri.html?USERID=" + USERID);
	$("#allContent").hide();
	init();
});
function init() {
	getProvince();
}

function getProvince() {
	var interf= getProvinceAndCity_url;
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
	gethobby();
	provincechange();
}


function gethobby() {
	var interf = hobby_url;
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			fillHobby(data);
		},
		dataType : "json"
	});
}

function fillHobby (data) {
    var content = "";
	for (var i=0; i < data.hobbyList.length; i++) {
		content = content 
		+'<option value = "'+data.hobbyList[i].interestId+'">'+data.hobbyList[i].interest+'</option>';
	};
	$("#hobby").html(content);
}

function provincechange () {
  var index = $("#province option:selected").val();
  var content = "";
	for (var i=0; i < provinceAndCityList.provinceAndCityList[index].citys.length; i++) {
		content = content 
		+'<option value = "'+provinceAndCityList.provinceAndCityList[index].citys[i].cityName+'">'+provinceAndCityList.provinceAndCityList[index].citys[i].cityName+'</option>';
	};
	$("#city").html(content);
	$("#allContent").show();
}

function findfir_serach() {
	gender = $('input:radio[name="no"]:checked').val();
    age = $("#serachage option:selected").val();
    province = provinceAndCityList.provinceAndCityList[$("#province option:selected").val()].provinceName;
    city = $("#city option:selected").val();
    hobby = $("#hobby option:selected").val();
    $("#findfir_serach").attr("href", "goodfriend-frires.html?USERID=" + USERID+"&gender=" + gender+"&nickName=&age=" + age+"&province=" + decodeURIComponent(province)+"&city=" + decodeURIComponent(city)+"&hobby=" + hobby);
}

function findfir_serachbyname () {
	
	if (nickName != null) 
	{
	   $("#findfir_serachbyname").attr("href", "goodfriend-frires.html?USERID=" + USERID+"&nickName=" + decodeURIComponent(nickName));
	}
	else
	{
		nickNameinput();
	}
}

function nickNameinput () {
  var hint = "请输入要搜索的昵称";
  var err = "请输入2-8个汉字（4-16个字母）的昵称";
  var nicknameRegt = "[\\d\\w\u4E00-\u9FFF]{1,15}";
  showTextDialog("","",hint,"1",err,"callBack(\'%1$s\')");
}

function callBack (v) {
  $("#nickNameinput").html(v);
	nickName = v;
}