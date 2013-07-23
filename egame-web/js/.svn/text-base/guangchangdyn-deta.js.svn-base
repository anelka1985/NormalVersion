var userID = "";
var dynamicId="";
var success = "0";
var startIndex = 0;
var pageSize = 10;
var type = 0;
var countStr;
// 页面读取完毕的时候，初始化数据
$(document).ready(function() 
{
	initDynamicDetail();
});
// 初始化动态详情页面
function initDynamicDetail() 
{
	$("#dynamicBody").hide();
	$('#button').hide();
	getDynamicData();
}


function getDynamicData()
 {
	userID = GetQueryString("USERID");
	dynamicId = GetQueryString("dynId");
	type = GetQueryString("type");
	
	if(userID == null) showToast("用户ID为空");
	if(dynamicId == null) showToast("动态ID为空");
	if(type==null) showToast("动态类型为空");
	
	var interf = String.format(guangchang_dyn_deta_url , dynamicId , userID,type);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			dynamicData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		success="1";
		$("#dynamicBody").show();
		pageLoadFinish();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#dynamicBody").html(err_str);
		
	});
}

function dynamicData(data) 
{
	var movementDetail = data.MovementDetail;
	var oldTime = movementDetail.sendTime;

	$("#userName").html(movementDetail.userName);
	$("#content").html(movementDetail.content);
	$("#sendTime").html(oldTime);
	
	//好友详情页面
	var userDetail_html_url=String.format(userDetail_html,movementDetail.userId,userID);
	var userPhoto ='<a href="'+userDetail_html_url+'"><span class="webo_kk"></span><img src="'+ movementDetail.headIcon+'" alt=""/></a>'+userOnline(movementDetail.onlineStatus);
	var pic;
	
	if(movementDetail.objectPic != null){
		pic = '<a onclick="showImage(\''+movementDetail.objectPic+'\')"><img src="'+movementDetail.objectPic+'" alt="" width="114" height="154" /></a>';
		$("#pic").append(pic);
	}
	

	$("#userPhoto").append(userPhoto);
	
	
	//加载评论
	getCommentData(startIndex);
}


function getCommentData(ind) 
{
	if (success == "0") 
	{
	   var interf = String.format(guangchang_dyn_deta_comment_url,dynamicId,ind,pageSize,userID);
	  printToConsole(interf);
	   var request = $.ajax({
		   url : interf,
		  type : "GET",
		  timeout:30000,
		  success : function(data) {
		   if(data.totalRecord){countStr = data.totalRecord};
			  commentData(data);
		},
		dataType : "json"
	});
	
	request.fail(function(textStatus) {
		success="1";
		pageLoadFinish();
		$("#dynamicBody").show();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#dynamicBody").html(err_str);
		
	});

	};
}

function insertCommon(dynamicId,nickname,content)
{
    var send_dynamics_url = guangchangdyn_deta_url;
	var encontent = UrlEncode(content);
	var interf = String.format(send_dynamics_url,dynamicId,encontent,userID);
	printToConsole(interf);
	pageLoadStart();
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			pageLoadFinish();
			
			if(data.result.resultcode == 0)
			{
				showToast("发送评论成功");
			    location.reload();
			}
			else
			{
				showToast("发送评论失败," + data.result.resultmsg);
			}
		},
		dataType : "json"
	});

	request.fail(function(textStatus)
    {
    	pageLoadFinish();
		success="1";
		showToast("网络异常，请检查网络");
	});
	
	
}

function commentData(data) 
{
	var commentListStr= data.commentList;
	
	var commentNum = "";
	if(countStr>0) {
		commentNum='<span class="text_color_gray font_lin58 P_l_10" >共'+countStr+'条评论</span>';
	}
	else {
		commentNum='<span class="text_color_gray font_lin58 P_l_10" >暂无评论</span>';
	}
	//评论
	var commentCount = commentNum
	                 +'<a  onclick="showInputPopup(\'true,,请输入评论内容,发送,insertCommon(%1$s,%2$s,%3$s)_'+dynamicId+'\')" class="pl">评论</a>';
	$("#commentCount").html(commentCount);
	var commentStr = "";
	for (var i=0; i < commentListStr.length; i++)
	 {
		commentListStr[i].comment = analyFaceImage(commentListStr[i].comment);
	 	commentStr =commentStr + '<div class="line_1"></div><div class="list pos sym_bg"> <div class="P_tb_10 blohi"><div class="collLeft pos"><a href="'+String.format(userDetail_html,commentListStr[i].userId,userID)+'"><span class="head_mask_76c"></span><img src="'+ commentListStr[i].icon +'"alt=""/></a></div>'
	 	+'<div class="listRight"><P class="font_20 P_tb_5"><a href="'+String.format(userDetail_html,commentListStr[i].userId,userID)+'">'+commentListStr[i].userName + '</a>'
	 	+ isOnline(commentListStr[i].onlineStatus) +'<span class="font_18 color_4 float_r">'+commentListStr[i].submitTime
	 	+'</span></P><P cl7ass="font_18 color_4 m_tb_2 p_r_10">'+commentListStr[i].comment
	 	+'</P></div></div></div>';
	};
	
	$("#list").html(commentStr);
	$("#dynamicBody").show();
	startIndex = data.startIndex;
	pageSize = data.pageSize;
	totalRecord = countStr;
	var totalPage = Math.ceil(totalRecord/pageSize);
	
	if(totalPage == 1 || totalRecord <= 10)
	{
		$("#button").hide();
	}
	else
	{	
		$('#button').show();
	    pageChange((startIndex+1),pageSize,totalPage);
	}
	
	pageLoadFinish();
}

function isOnline(onlineStatus)
{
	if(onlineStatus == 1)
	{
		return '<span><img src="images/icon_online.png" width="26" height="20" alt=""/></span>';
	}
	else
	{
		return "";
	};
}
//首页
function pageHome() 
{
  getCommentData(1);
}

//上一页
function pageUp() 
{
     if (startIndex-pageSize>=0)
	 {
		
		 getCommentData(startIndex-pageSize);
	};
 
}

//下一页
function pageDown() 
{
	getCommentData(startIndex+pageSize);

}

function userOnline(onlineStatus)
{
	if(onlineStatus == 1)
	{
		return online_html;
	}
	else
	{
		return "";
	}
}
