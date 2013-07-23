var USERID="";

// 页面读取完毕的时候，初始化数据
$(document).ready(function() 
{
	getNewData();
});


function getNewData()
{
	USERID = GetQueryString("USERID");
	if(USERID==null) showToast("用户ID为空");
	$("#guangchang_index").attr("href", String.format(guangchangIndex_html,USERID)); 
	$("#friend_index").attr("href", String.format(guangchangFriend_html,USERID));
	$("#activity_index").attr("href", String.format(guangchangActivity_html,USERID));
	$("#activity").hide();
	var interf = String.format(guangchang_activity_url,USERID);
	var request = $.ajax({
		url : interf,
		type : "GET",
		success : function(data) {
			fillData(data);
		},
		dataType : "json"
	});

	request.fail(function(textStatus) {
		success="1";
		pageLoadFinish();
		$("#activity").show();
		// 把LOADING 换成加载出错提示语
		err_str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
		$("#detail").html(err_str);
	});
}

function fillData(data)
{
	var activityList = data.activityList;
	var str="";
	for (var i=0; i < activityList.length; i++) 
	{
		str= str +'<div class="weibo bg_act">'
		         +'<div class="list pos P_t_10"><a onclick=showSysBrowse(\''+activityList[i].activeUrl+'\') class="blohi">'
		         +'<div class="m_1_10"><P class="font_20 m_tb_2">'+activityList[i].activeName+'</P>'
		         +'<P class="font_18 color_2 m_tb_10"><span>截止日期：'+activityList[i].endDate+'</span>'
		         +'<span class="float_r">参与用户：'+activityList[i].joinNumber+'人</span></P></div>'
		         +'<div class="narrow_r act_narrow_r"></div><div class="square_act pos">'
		         +'<img src="'+activityList[i].banner+'" alt="" width="454" height="140" />'
		         +'</div><div class="square_ad_shadow"></div></a></div>';
	};
	$("#detail").html(str);
	$("#activity").show();
	pageLoadFinish();
}
