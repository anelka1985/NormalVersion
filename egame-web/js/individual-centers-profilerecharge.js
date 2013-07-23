var USERID="";
//开始页数
var startIndex="";

var pageSize= 20;

//总数
var toltalRecordNum;
$(document).ready(function()
{
	init();
});

function init()
{
	USERID =  GetQueryString("USERID");
	if(USERID==null) showToast("用户ID为空");
	toltalRecordNum =  GetQueryString("toltalRecord");
	startIndex = GetQueryString("startIndex");
	if(startIndex == null) startIndex = 0;
	$("#recharge").attr("href", String.format(profilerecharge_html,USERID)); 
	$("#consume").attr("href", String.format(profileco_html,USERID)); 
	$("#balancePart").hide();
	$("#allContent").hide();
	$("#button").hide();
	getNewDate(startIndex);
}

function getNewDate(startIndex)
{
	var interf = String.format(recharge_url,USERID,startIndex,pageSize);
	printToConsole(interf);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			fillData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus)
    {
		pageLoadFinish();
		$("#allContent").show();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#allContent").html(err_str);
	});
}

function fillData(data)
{
	var balanceStr = '当前余额：<span class="color_5">'+data.blance+'</span> 爱豆';
	$("#balance").html(balanceStr);
	if(toltalRecordNum == null )toltalRecordNum = data.toltalRecord;
	var countStr= '共有 '+toltalRecordNum+'条充值记录';
	$("#count").html(countStr);
	var accountStr='<a onclick="showSysBrowse(\''+data.url+'\')"><span class="btu_man_com2 btu_green_com2 btu_g_money"></span><span class="btu_g_right"></span><span class="m_l_20">账户充值</span></a>';
	$("#account").html(accountStr)
	var title = '<tr class="dunit1_out1">'
	           +'<td class="dunit1_inner1 table_line">充值方式</td>'
	           +'<td class="dunit1_inner1 table_line">金额</td>'
	           +'<td class="dunit1_inner2">时间</td>'
	           +'</tr>';
	           
	var rechargeList = data.rechargeList;
	
	var str="";
	if(rechargeList.length > 0)
	{
		str = str+title;
		for (var i=0; i < rechargeList.length; i++) 
		{
			str = str +'<tr class="dunit1_out2">'
			          +'  <td class="dunit1_inner3">'+rechargeList[i].chargeName+'</td>'
			          +'  <td class="dunit1_inner3">'+rechargeList[i].money+'爱豆</td>'
			          +'  <td class="dunit1_inner3">'+rechargeList[i].time+'</td>'
			          +'</tr>';
		}
		$("#detail").html(str);
	}
	else
	{
		var noStr='  <div class="text_pk">您还没有充值过哦！</div>'
		$("#allContent").html(noStr);
	}
	
	
	pageLoadFinish();
	$("#balancePart").show();
	$("#allContent").show();
	if (data.toltalRecord != rechargeList.length) 
	{
		$("#button").show();
	}
	
	startIndex = data.startIndex;
	
	if(toltalRecordNum == null )toltalRecordNum = data.toltalRecord;
	var totalPage = Math.ceil(toltalRecordNum/pageSize);
	var start = startIndex+1;
	var pageDownIndex = startIndex + pageSize;
	var pageUpIndex =0;
	var page = Math.ceil(start/pageSize);
	
	var lastPageIndex = (totalPage -1) * pageSize;
	if (startIndex-pageSize>0)
	{
		 pageUpIndex=startIndex-pageSize;
	}
	pageChangeOther(start,pageSize,totalPage);
	
	
	if(startIndex ==0)
	{
	   $("#pageHomeIndex").attr("href","#");
	   $("#pageUpIndex").attr("href","#");
	}
	else
	{
		//首页
	   $("#pageHomeIndex").attr("href","individual-centers-profilerecharge.html?USERID=" + USERID + "&startIndex=0");
	   //上一页
	   $("#pageUpIndex").attr("href","individual-centers-profilerecharge.html?USERID=" + USERID + "&startIndex=" + pageUpIndex + "&toltalRecord=" + toltalRecordNum);
	}
	
	if(page == totalPage)
	{
		 $("#pageDownIndex").attr("href","#");
		 $("#lastPageIndex").attr("href","#");
	}
	else
	{
	  //下一页
	  $("#pageDownIndex").attr("href","individual-centers-profilerecharge.html?USERID=" + USERID + "&startIndex=" + pageDownIndex + "&toltalRecord=" + toltalRecordNum);
	   //末页
	  $("#lastPageIndex").attr("href","individual-centers-profilerecharge.html?USERID=" + USERID + "&startIndex=" + lastPageIndex + "&toltalRecord=" + toltalRecordNum);
	}
}
