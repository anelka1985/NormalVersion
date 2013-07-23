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
	USERID = GetQueryString("USERID");
	// alert("USERID "+USERID);
	//
	// 做点延迟，让loading 方便看出来
	setTimeout( "getDataAndShow()",0);
	//fresh();

	 // 把每个链接都加上 自己的ID
	 $("a").click(function() {
		this.href =	appendParaToURL(this.href,"USERID="+USERID )   ;
	 });
}



function initHeadArea(list){
	$("#head_part").html("");
	
	var len =0;
	for(i=0;i<list.length;i++){
		len++;
		if(len<5){
				$("#head_part").append('<li class="pos" id="show_head_'+i+'"> <a href="#" > <span class="head_mask_76"></span><img src="'+list[i]+' " width="76" height="76" alt=""/></a></li>');
		}
	
	}
	$("#head_part").append('<li class="pos"  id="head_pic"> <a> <span class="head_mask_76"></span><img src="images/img_add.png" alt=""/></a></li>');


	$(".pos").click(function() {
		if(this.id!=null){
			if(this.id=="head_pic"){
				showPicDialog();
			}else if(this.id.indexOf("show_head_")!=-1){
				//showImage(this.lastChild.lastChild.src.replace("portrait","portrait_original"));
				changeHead(this.lastChild.lastChild.src);
			}
		}
	 });
}


function changeHead(url){
	//alert("picCallback");
	var interf =format(changeUserPhoto_url,USERID,url);//     
	//showToast(interf);
	printToConsole(interf);
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
		  showToast(data.result.resultmsg);// printToConsole("aaaa"+data);
		 // initHeadArea(data.userPhotoList.photos);
			picCallback();
		//  alert("data.userPhotoList.photos "+data.userPhotoList.photos);
		 },
	   dataType: "json"
	});

	request.fail(function(textStatus) {
	});
}

function picCallback(){
	//alert("picCallback");
	var interf =   format(getUserPhoto_url,USERID,USERID);
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
		 // showToast(data);
		  initHeadArea(data.userPhotoList.photos);
		//  alert("data.userPhotoList.photos "+data.userPhotoList.photos);
		 },
	   dataType: "json"
	});

	request.fail(function(textStatus) {
	});
}

// 从接口获取数据，并且显示出来
function getDataAndShow(){
	 
	var interf =  format(myInfo_url,USERID); //  '_data_individual-centers-profile.html';  
	
//	showToast(interf);

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


function callBack(v){
	value= v;

	//showToast(key+" callBack "+value);

	if(value!=""){
		modify();
	}
}

function birthday_callBack(p1,p2,p3){
	value= p1+"-"+p2+"-"+p3;
	//showToast(value);
	if(value!=""){
		modify();
	}
}


// 计算资料的完善度
function calcPercent(){
	var total =new Array("#userName","#gender","#birthday","#phone","#email","#city","#school","#company","#hobby","#platitude");
	percent = 0;
	for(i=0;i<total.length;i++){
		if($(total[i]).html()!=null&&$(total[i]).html()!=""&&$(total[i]).html()!="暂无"){
			percent+= 10;
		}
	}
	setPercent(percent+"%");
}

function setPercent(percent){
	
	w = "高";
	num = percent.replace("%","");
	if(num<80){
		w = "中";
	}
	if(num<40){
		w = "低";
	}
	$("#complete_word").html("完善度："+w);
	$("#complete").html(percent);
	$("#complete_img").attr("style", "width:"+percent) ;
}

function setGender(gender){
	var gender_s = "男";
	if(gender=="2"){
		gender_s = "女";
	}
	$("#gender").html(gender_s);
}

function clearBg(id){

	//$("#"+id).find("a:first").removeClass("bg_color1");
	$("#"+id).removeClass("bg_color1");
}



// 显示 从接口获取的数据
function showData(json){
	//
	// 先填充数据
	//
	setPercent(json.complete);


	data = json.myInfo;

	$("#userName").html(data.userName);
	setGender(data.gender);

	$("#birthday").html(data.birthday);
	$("#phone").html(data.phone);  // color_5 未验证
	if(data.phone!=""&&data.phone!=null){
		if(data.phoneCheck=="1"){
			$("#phoneCheck").html("(已验证)"); 
			$("#phoneCheck").addClass("color_7"); 
		}else{
			$("#phoneCheck").html("(未验证)"); 
			$("#phoneCheck").addClass("color_5"); 
		}
	}else{
		$("#phoneCheck").html(""); 
	}
	$("#email").html(data.email);
	if(data.email!=""&&data.email!=null){		
		if(data.emailCheck=="1"){
			$("#emailCheck").html("(已验证)"); 
			$("#emailCheck").addClass("color_7"); 
		}else{
			$("#emailCheck").html("(未验证)"); 
			$("#emailCheck").addClass("color_5"); 
		}
	}else{
		$("#emailCheck").html(""); 
	}

	$("#city").html(data.province+" "+data.city);
	$("#school").html(data.school);
	$("#company").html(data.company);
	$("#hobby").html(data.hobby);
	$("#platitude").html(data.platitude);
	
	if(data.headPortraitFlag==1){
		closeAd();
	}

	initHeadArea(json.headPortrait.photos);


	

	// 所有以 area 结尾的 DIV 都截取 onclick
		$('div[id*=_area]').click(
				function() {

				
				var defaul = "";
				  key = this.id.replace("_area",""); value="";
				  if(key=="gender"){
					  showGenderDialog("callBack(\'%1$s\')");
				  }else
				  if(key=="birthday"){
					 var d = $("#birthday").html();
					 if(d==""||d==null){
						d = "1980-1-1";
					 }

						 date = d.split("-");
						 showDateDialog(date[0] ,date[1] ,date[2] , "birthday_callBack(\'%1$s\',\'%2$s\',\'%3$s\')");
					 
					 
				  }else{
					var reg = ".*";
					var hint = "";
					var err = "";
					//showToast(key);
					if(key == "userName"){
						defaul = $("#userName").html();
						hint = "请输入您的昵称";
						err = "请输入2-8个汉字（4-16个字母）的昵称";
						reg = nicknameReg;
						reg = 1;
					}
					if(key == "phone"){
						defaul = $("#phone").html();
						hint = "请输入您的手机号码";
						err = "请输入正确的手机号码";
						reg = phoneReg;
						reg = 2;
					}
					if(key == "email"){
						defaul = $("#email").html();
						hint = "请输入您的邮箱";
						err = "请输入正确的邮箱地址";
						reg = emailReg;
						reg = 3;
					}
					if(key == "city"){
					//	hint = "请输入您的所在地";
					//	err = "";
						window.location.href="individual-centers-selcetcity.html?USERID="+USERID+"&provinceName="+encodeURIComponent(data.province)+"&cityName="+encodeURIComponent(data.city);
						return;
					}
					if(key == "company"){
						defaul = $("#company").html();
						hint = "请输入您的公司名称(20字内)";
						err = "公司名称字数限制在20字符内";
						reg = companyReg;
						reg = 4;
					}
					if(key == "hobby"){
						window.location.href= ("individual-centers-profilehob.html?USERID="+USERID+"&hobbydata="+encodeURIComponent(data.hobby));
						return;
					}
					if(key == "school"){
						//	window.location.href="individual-centers-profile1.html?USERID="+USERID;
						return;
					}
					if(key == "platitude"){
						defaul = $("#platitude").html();
						hint = "请输入您的个性签名(60字内)";
						err = "个性签名字数限制在60字符内";
						reg = signatureReg;
						reg = 5;
					}
					showTextDialog(hint,defaul,hint,reg,err,"callBack(\'%1$s\')");
				  }
				}
			);
	
	//
	calcPercent();
	//
	// 显示
	//
	$("#content").removeClass("hide")
	//
	pageLoadFinish();
}

function modify(){
	// 调本地LOAD
	pageLoadStart();

	// 调接口	 
	 

	if(key == "userName"){
		key = "nickName";
	}
	
	var envalue = UrlEncode(value);
	var interf =  String.format(modifyMyInfo_url,USERID,key,envalue); //ip+'user/modifyMyInfo.json?USERID='+USERID+'&'+key+'='+value; //   '_data_getWage.html'; //   key='+key+'&value='+value
    printToConsole("modify--->" + interf);
	if(key == "nickName"){
		key = "userName";
	}

	//
	var request = $.ajax({
	  url: interf,
	  type: "GET",
	  success: function(data){
		if(data.result.resultcode == 0)
		{
 			if(key=="gender"){
				setGender(value);
			}else{
				if(key=="userName"){
					setNickname(value);
				}
				$("#"+key).html(value);
			}


		}
		
		// 隐藏本地LOAD
		 pageLoadFinish();

		 //
		 calcPercent();

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


function closeAd(){

	//
	$("#ad").hide();
}
