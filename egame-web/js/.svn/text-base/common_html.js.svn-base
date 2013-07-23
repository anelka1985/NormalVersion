//在线图标+在线文字
var online_html = '<span class=\"online online_56\">在线</span>';

//在线图标
var onlinIcon_html = 'vicon_online';
	
//预留位置用于插入新评论
//%1-id,动态设置id
var hidecommon_html = '<div id=\"dynamicId_%1\" class=\"hide\"></div>';
	
//评论
//%1-用户昵称
//%2-评论内容
//%3-评论时间
var common_html =  '<div class=\"line_1\"></div><div class=\"words P_tb_10\"><span class=\"text_color_blue %1\"><a onclick=\"showDetail(\'friendId\',\'%2\')\">%3</a></span><span class="font_16 float_r">%5</span><P>%4</P></div>';
    
//动态图片
//%1-图片src
//%2-图片src
var picContext_html = '<p><img src=\"%1\" onclick=\"showImage(\'%2\')\" alt=\"\" width=\"114\" /></p>';
    
//测试服
var base_url = 'http://202.102.111.26:9058';

//开发服
//var base_url = 'http://221.226.177.158:9010';

//验证服
//var base_url = 'http://202.102.39.18:9084';
//url链接定义
//广场动态列表url
//var guangchang_dynamics_url = base_url+'/sns-clientV4/four/dynamic/userDynamics.json?type=1&classType=0&startIndex=%1&pageSize=%2&USERID=%3';

var guangchang_dynamics_url = base_url+'/sns-clientV4/four/dynamic/dynamicList.json?type=0&typeCode=&commentFetchSize=3&markDynamicInfoId=%1&direction=%2&pageSize=20&USERID=%3&'+getHeader();

var guangchang_friend_dyns_url = base_url+'/sns-clientV4/four/dynamic/dynamicList.json?type=1&typeCode=&commentFetchSize=3&markDynamicInfoId=%1&direction=%2&pageSize=20&USERID=%3&'+getHeader();

var guangchang_user_dyns_url = base_url+'/sns-clientV4/four/dynamic/dynamicList.json?type=2&typeCode=&commentFetchSize=3&markDynamicInfoId=%1&direction=%2&pageSize=20&USERID=%3&friendId=%4&'+getHeader();

//系统消息
var systemMessage_url = base_url+'/sns-clientV4/four/msg/systemAllMessageList.json?USERID=%1&'+getHeader();

//同意好友申请
var agreeFriendInvite_url = base_url+'/sns-clientV4/four/user/addFriendReply.json?friendId=%1&USERID=%2&agree=%3&messageId=%4&'+getHeader();

//好友消息
var friendMessage_url = base_url+'/sns-clientV4/four/msg/friendMessageList.json?USERID=%1&'+getHeader();

//删除消息
var delMessage_url = base_url+'/sns-clientV4/four/msg/messageOperate.json?messageIdArray=%1&USERID=%2&'+getHeader();

//删除好友消息
var delFriMessage_url = base_url+'/sns-clientV4/four/msg/deleteFriendMessage.json?friendIdArr=%1&USERID=%2&'+getHeader();

//动态详情
var guangchang_dyn_deta_url = base_url+'/sns-clientV4/four/dynamic/dynamicDetail.json?dynamicId=%1&USERID=%2&type=%3&'+getHeader();

//动态详情评论
var guangchang_dyn_deta_comment_url = base_url+'/sns-clientV4/four/dynamic/commentList.json?dynamicId=%1&startIndex=%2&pageSize=%3&USERID=%4&'+getHeader();

//我的好友列表url
var goodfriend_myfriend_url= base_url+'/sns-clientV4/four/user/friendList.json?startIndex=%1&pageSize=%2&USERID=%3&'+getHeader();

//广场首页url
var guangchang_index_url = base_url+'/sns-clientV4/four/dynamic/publishComment.json?dynamicId=%1&content=%2&USERID=%3&'+getHeader();

//广场好友url
var guangchang_friend_url = base_url+'/sns-clientV4/four/dynamic/publishComment.json?dynamicId=%1&content=%2&USERID=%3&'+getHeader();

//广场动态详细发送url
var guangchangdyn_deta_url = base_url+'/sns-clientV4/four/dynamic/publishComment.json?dynamicId=%1&content=%2&USERID=%3&'+getHeader();
//活动列表url 
var guangchang_activity_url = base_url+'/sns-clientV4/four/activity/activitiesList.json?USERID=%1&'+getHeader();

//可能认识
var goodfriend_possiblefriend_url =base_url+'/sns-clientV4/four/user/impossbleFriendList.json?USERID=%1&'+getHeader();

//我的好友管理
var goodfriend_myfriendma_url = base_url+'/sns-clientV4/four/user/friendList.json?startIndex=%1&pageSize=%2&USERID=%3&'+getHeader();

//消息详情
var centers_frienddtea_url = base_url+'/sns-clientV4/four/msg/friendMessageDetail.json?messageId=%1&USERID=%2&'+getHeader();

var addFriend_Request_url = base_url+'/sns-clientV4/four/user/requestAddFriend.json?friendId=%1&USERID=%2&'+getHeader();

var sendMessage_url = base_url+'/sns-clientV4/four/msg/sendMsg.json?USERID=%1&friendId=%2&content=%3&'+getHeader();

// 获取工资
var get_wage_url =base_url+'/sns-clientV4/four/user/getWage.json?USERID=%1&'+getHeader();

// 个人中心
var myCenter_url =base_url+'/sns-clientV4/four/user/myCenter.json?USERID=%1&'+getHeader();

// 个人资料获取
var myInfo_url =base_url+'/sns-clientV4/four/user/myInfo.json?USERID=%1&'+getHeader();

// 修改个人资料
var modifyMyInfo_url =base_url+'/sns-clientV4/four/user/modifyMyInfo.json?USERID=%1&%2=%3&'+getHeader();

// 获取学校字母
var schoolInitial_url =base_url+'/sns-clientV4/four/basic/schoolInitial.json?provinceId=%1&'+getHeader();

// 获取学校列表
var shcoolList_url = base_url+'/sns-clientV4/four/basic/shcoolList.json?provinceId=%1&schoolInitial=%2&'+getHeader();

// 获取省份
var provinceAndCityList_url = base_url+'/sns-clientV4/four/basic/provinceAndCityList.json?fetchCityFlag=1&'+getHeader();

// 获取省份2
var provinceAndAllCityList_url = base_url+'/sns-clientV4/four/basic/provinceAndCityList.json?fetchCityFlag=0';

// 获取爱好列表
var getHobbyList_url = base_url+'/sns-clientV4/four/basic/getHobbyList.json?'+getHeader();

// 我的游戏
var userGameList_url = base_url+'/sns-clientV4/four/user/userGameList.json?USERID=%1&friendId=%2&startIndex=%3&pageSize=%4&'+getHeader();

// 我的收藏
var userCollectList_url = base_url+'/sns-clientV4/four/user/userCollectList.json?USERID=%1&friendId=%2&num=%3&'+getHeader();

//用户所有动态
var useralldyn_url = base_url+'/sns-clientV4/four/dynamic/userDynamics.json?friendId=%1&type=%2&classType=%3&startIndex=%4&pageSize=%5&USERID=%6&'+getHeader();

//获得省市级联数据
var getProvinceAndCity_url = base_url+'/sns-clientV4/four/basic/provinceAndCityList.json?fetchCityFlag=0&'+getHeader();

//找朋友搜索结果
var finfir_url = base_url+'/sns-clientV4/four/user/searchFriend.json?USERID=%1&gender=%2&age=%3&province=%4&city=%5&hobby=%6&nickName=%7&'+getHeader();

//他得好友
var hisfriend_url = base_url+'/sns-clientV4/four/user/friendList.json?startIndex=%1&pageSize=%2&friendId=%3&'+getHeader();

//他得好友
var cometo_url = base_url+'/sns-clientV4/four/user/userVisitList.json?USERID=%1&startIndex=%2&pageSize=%3&'+getHeader();

//打招呼
var dazhaohu_url = base_url+'/sns-clientV4/four/msg/Greeting.json?USERID=%1&friendId=%2&actionId=%3&'+getHeader();

//搜索——爱好
var hobby_url = base_url+'/sns-clientV4/four/basic/getHobbyList.json?'+getHeader();

//用户详情
var userdetail_url = base_url+'/sns-clientV4/four/user/friendDetail.json?friendId=%1&USERID=%2&'+getHeader();

//获得用户照片
var getUserPhoto_url = base_url+'/sns-clientV4/four/user/userPhotoList.json?friendId=%1&USERID=%2&'+getHeader();


//更改用户照片
var changeUserPhoto_url = base_url+'/sns-clientV4/four/user/chooseHeadPortrait.json?USERID=%1&headPortraitPath=%2&'+getHeader();

//获得用户好友
var getUserFriend_url = base_url+'/sns-clientV4/four/user/userFriendList.json?friendId=%1&startIndex=%2&pageSize=%3&'+getHeader();

//删除好友
var delFriend_url = base_url+'/sns-clientV4/four/user/deleteFriend.json?friendId=%1&USERID=%2&'+getHeader();

//充值记录
var recharge_url = base_url + '/sns-clientV4/four/user/rechargeList.json?USERID=%1&startIndex=%2&pageSize=%3&'+getHeader();

//消费记录
var consume_url = base_url + '/sns-clientV4/four/user/consumeList.json?USERID=%1&startIndex=%2&pageSize=%3&'+getHeader();
//url链接定义结束
    
//动态详情链接
var dynDetail_html = 'guangchangdyn-deta.html?dynId=%1&USERID=%2&type=%3&OSFROMER='+osfromer;
    
//用户详情链接
var userDetail_html = 'goodfriend-deta.html?friendId=%1&USERID=%2&OSFROMER='+osfromer;

//广场首页链接
var guangchangIndex_html = 'guangchang-index.html?USERID=%1&OSFROMER='+osfromer;
var guangchangIndex1_html = 'guangchang-index.html?USERID=%1&markInfoId=%2&direction=%3&OSFROMER='+osfromer;

//广场好友动态链接
var guangchangFriend_html = 'guangchang-friends.html?USERID=%1&OSFROMER='+osfromer;
var guangchangFriend1_html = 'guangchang-friends.html?USERID=%1&markInfoId=%2&direction=%3&OSFROMER='+osfromer;

var goodfriend_alldyn_html = 'goodfriend-alldyn.html?USERID=%1&markInfoId=%2&direction=%3&friendId=%4&OSFROMER='+osfromer;

//广场活动页链接
var guangchangActivity_html = 'guangchang-activity.html?USERID=%1&OSFROMER='+osfromer;

//我的系统消息链接
var systemMessage_html = 'individual-centers-symedit2.html?USERID=%1&OSFROMER='+osfromer;

//我的好友消息链接
var friendMessage_html = 'individual-centers-friend.html?USERID=%1&OSFROMER='+osfromer;

//我的好友
var myfriend_html = 'goodfriend-myfriend.html?USERID=%1&OSFROMER='+osfromer;

//我的好友管理
var myfriendma_html = 'goodfriend-myfriendma.html?USERID=%1&OSFROMER='+osfromer;

//可能认识的好友
var possiblefriend_html = 'goodfriend-possiblefriend.html?USERID=%1&OSFROMER='+osfromer;

//搜索好友
var findfri_html = 'goodfriend-findfri.html?USERID=%1&OSFROMER='+osfromer;

//邀请好友
var invfri_html = 'goodfriend-invfri.html?USERID=%1&OSFROMER='+osfromer;

//好友消息详情
var centers_frienddtea_html = 'individual-centers-frienddtea.html?msgId=%1&USERID=%2&friendId=%3&OSFROMER='+osfromer;

//我的消息
var centers_friendnd_html = 'individual-centers-friendnd.html?USERID=%1&OSFROMER='+osfromer;

//系统消息
var centers_symedit2_html = 'individual-centers-symedit2.html?USERID=%1&OSFROMER='+osfromer;

//充值记录
var profilerecharge_html = 'individual-centers-profilerecharge.html?USERID=%1&OSFROMER='+osfromer;

//消费记录
var profileco_html = 'individual-centers-profileco.html?USERID=%1&OSFROMER='+osfromer;

//个人资料页
var profile_html ='individual-centers-profile.html?USERID=%1&OSFROMER='+osfromer;

//我的PK页
var centers_pk_html = 'individual-centers-pkno.html?USERID=%1&OSFROMER='+osfromer;

//url链接定义
//游戏圈url连接
//获取游戏圈主页内容
var currentGameCircleIndex_url = base_url+'/sns-clientV4/SDK/gameHub/subjectList.json?startIndex=%1&pageSize=%2&' + getHeader();

//发布主题
var sendSubject_url = base_url+'/sns-clientV4/SDK/gameHub/publishSubject.json?subjectTittle=%1&subjectContent=%2&USERID=%3&' + getHeader();

//发布回复
var sendReply_url = base_url+'/sns-clientV4/SDK/gameHub/replaySubject.json?subjectId=%1&USERID=%2&repcontent=%3&' + getHeader();

//发布星级评论
var sendEvaluationLevel_url = base_url+'/sns-clientV4/four/game/publishGameComment.json?gameId=%1&score=%2&USERID=%3&comment=%4&gameName=%5&nickName=%6&' + getHeader();
//获取星级评论
var getEvaluationLevel_url = base_url+'/sns-clientV4/four/game/publishGameComment.json?gameId=%1&USERID=%2&type=0&' + getHeader();

//获取游戏圈详细主题
var currentGameCircleDetail_url = base_url+'/sns-clientV4/SDK/gameHub/subjectDetail.json?subjectId=%1&startIndex=%2&pageSize=%3&' + getHeader();

//获取成就列表
var currentGameAchievement_url = base_url+'/sns-clientV4/SDK/game/achieveList.json?&startIndex=%1&pageSize=%2&gameId=%3&USERID=%4&' + getHeader();

//获取成就用户列表
var currentGameAchievementReached_url = base_url+'/sns-clientV4/SDK/game/achieveUserList.json?USERID=%1&gameId=%2&achieveId=%3&type=%4&startIndex=%5&pageSize=%6&' + getHeader();

//url链接定义结束

//当前游戏主页
var currentGameIndex_html = 'currentgame-index.html?USERID=%1&gameId=%2&OSFROMER='+osfromer;

//游戏圈动态连接
//游戏圈首页链接
var currentGameCircleIndex_html = 'currentgame-circle-index.html?USERID=%1&gameId=%2&OSFROMER='+osfromer ;

//发布主题
var circlePublishKeyNote_html = 'currentgame-keynote.html?USERID=%1&OSFROMER='+osfromer;

//主题详细
var currentgameCircleDetail_html = 'currentgame-circle-detail.html?subjectId=%1&USERID=%2&OSFROMER='+osfromer;

//回复主题
var currentGameReply_html = 'currentgame-reply.html?subjectId=%1&USERID=%2&OSFROMER='+osfromer;

//成就主页
var currentGameAchievement_html = 'currentgame-achievement.html?USERID=%1&gameId=%2&OSFROMER='+osfromer;

var currentGameEva = 'currentgame-evaluationofgame.html?USERID=%1&gameId=%2&userName=%3&gameName=%4&OSFROMER='+osfromer;

//达成此成就用户
var currentGameAchievementReached_html = 'currentgame-achievement-reached.html?USERID=%1&userType=%2&achieveId=%3&gameId=%4&OSFROMER='+osfromer;

var pkIndexHtml_html='pk-index.html?USERID=%1&gameId=%2&OSFROMER='+osfromer;
var	pkMypkHtml_html='pk-mypk.html?USERID=%1&gameId=%2&OSFROMER='+osfromer;
var	pkExchangepkCurrencyHtml_html='pk-exchangepkcurrency.html?USERID=%1&gameId=%2&OSFROMER='+osfromer;
var	pkModeHtml_html='pk-pkmode.html?USERID=%1&gameId=%2&OSFROMER='+osfromer;
var	pkRecordHtml_html="pk-record.html?USERID=%1&gameId=%2&OSFROMER="+osfromer;
var	pkScoreHtml_html='pk-score.html?USERID=%1&score=%2&gameId=%3&OSFROMER='+osfromer;
var	pkReleaseHtml_html='pk-release.html?USERID=%1&score=%2&gameId=%3&OSFROMER='+osfromer;
var	pkSelectFriendHtml_html='currentgame-share.html?USERID=%1&score=%2&gameId=%3&tabType=%4&OSFROMER='+osfromer;
var	fullValueHtml_html='http://202.102.39.8/recharge/me/charge/%1?'+getHeader();
var	pkInviteGoodFriendHtml_html='pk-invitegoodfriend.html?USERID=%1&score=%2&gameId=%3&OSFROMER='+osfromer;
var	pkChallengeresultsHtml_html='pk-challengeresults-result.html?USERID=%1&resultId=%2&myScore=%3&gameId=%4&OSFROMER='+osfromer;
var	pkChallengeHtml_html='pk-acceptthechallenge.html?USERID=%1&bonus=%2&pkId=%3&gameId=%4&myIcon=%6&userIcon=%7&OSFROMER='+osfromer;
//daren list
var darenListHtml_html='currentgame-darenlist.html?USERID=%1&searchType=%2&rankType=%3&gameId=%4&pageSize=%5&OSFROMER='+osfromer;
//pk request url
var endAcceptUrl_url=base_url+'/sns-clientV4/SDK/pk/endAccept.json?USERID=%1&resultId=%2&myScore=%3&'+ getHeader();
var pkIndexUrl_url=base_url+'/sns-clientV4/SDK/pk/findAllPKList.json?USERID=%1&pageSize=%2&startIndex=%3&gameId=%4&' + getHeader();
var pkMypkUrl_url=base_url+'/sns-clientV4/SDK/pk/findMyPKList.json?USERID=%1&pageSize=%2&startIndex=%3&gameId=%4&' + getHeader();
var pkRecordUrl_url=base_url+'/sns-clientV4/SDK/pk/PkRecordList.json?USERID=%1&pageSize=%2&startIndex=%3&gameId=%4&' + getHeader();
var pkExchangepkCurrencyUrl_url=base_url+'/sns-clientV4/SDK/pk/exchangeRecordList.json?USERID=%1&pageSize=%2&startIndex=%3&gameId=%4&' + getHeader();
var accountUrl_url=base_url+'/sns-clientV4/SDK/pk/myAccountShow.json?USERID=%1&' + getHeader();
var removeChallengeUrl_url=base_url+'/sns-clientV4/SDK/pk/revokePkInfo.json?pkId=%1&' + getHeader();
var startChallengeUrl_url=base_url+'/sns-clientV4/SDK/pk/startChallenge.json?USERID=%1&pkId=%2&' + getHeader();
var releaseUrl_url=base_url+'/sns-clientV4/SDK/pk/launchToLobby.json?USERID=%1&bet=%2&score=%3&gameId=%4&' + getHeader();
var pkSelectFriendUrl_url=base_url+'/sns-clientV4/four/user/friendList.json?USERID=%1&pageSize=%2&startIndex=%3&gameId=%4&' + getHeader();
var pkReleaseFriendsUrl_url=base_url+'/sns-clientV4/SDK/pk/launchToUsers.json?USERID=%1&bet=%2&targetList=100300,859854,234432&targetType=0&score=%3&gameId=%4&' + getHeader();
var changeAidouUrl_url=base_url+'/sns-clientV4/SDK/pk/aiDouExchangePKBi.json?USERID=%1&AIDou=%2&gameId=%3&' + getHeader();
var freeReceivePkbiUrl_url=base_url+'/sns-clientV4/SDK/pk/freeReceivePKBI.json?USERID=%1&gameId=%2&' + getHeader();
//daren request url
var darenAllListUrl_url=base_url+'/sns-clientV4/SDK/daRRen/daRRenRankAllList.json?USERID=%1&rankType=%2&gameId=%3&direction=%4&rank=%5&pageSize=%6&' + getHeader();
var darenMyListUrl_url=base_url+'/sns-clientV4/SDK/daRRen/daRRenRankFriendList.json?USERID=%1&rankType=%2&gameId=%3&direction=%4&rank=%5&pageSize=%6&' + getHeader();
	
//wangfan
var currentgameIndex_url = base_url+'/sns-clientV4/SDK/game/SDKWelcome.json?USERID=%1&gameId=%2&' + getHeader();

var indexSDKGame_url = base_url+'/sns-client/SDK/game/SDKGame.json?USERID=%1&gameId=%2&' + getHeader();
var indexButtonlist_url = base_url+'/sns-client/SDK/game/buttonList.json?USERID=%1&gameId=%2&' + getHeader();
var indexJoinUserList_url = base_url+'/sns-client/SDK/game/joinUserList.json?USERID=%1&gameId=%2&' + getHeader();
var indexdaRRenRankAllList_url = base_url+'/sns-client/SDK/daRRen/daRRenRankAllList.json?USERID=%1&gameId=%2&rankType=3&direction=0&rank=0&pageSize=3&' + getHeader();
var indexSubjectList_url = base_url+'/sns-client/SDK/gameHub/subjectList.json?USERID=%1&gameId=%2&startIndex=0&pageSize=3&' + getHeader();

var moregame_developers_url = base_url+'/sns-client/SDK/game/moreGameList.json?pageSize=%1&startIndex=%2&cpId=%3&' + getHeader();

//var moregame_developers_url = base_url+'/sns-clientV4/SDK/game/moreGameList.json?pageSize=%1&startIndex=%2&cpId=%3&' + getHeader();


var moreplayers_url =  base_url+'/sns-clientV4/SDK/game/morePlayerList.json?USERID=%1&pageSize=%2&startIndex=%3&type=%4&gameId=%5&' + getHeader();

var moregame_developers_index_url = base_url+'/sns-clientV4/four/game/moreProduct.json?' + getHeader();

var moregame =  base_url+'/sns-clientV4/four/game/getGameDetail.json?gameId=%1&USERID=%2&'+getHeader();

//分享给好友
var currentGameShare_html = "currentgame-share.html?USERID=%1&Msg=%2&OSFROMER=" + osfromer;
var currentGameShareFirends_html = "currentgame-share-friends.html?USERID=%1&Msg=%2&OSFROMER=" + osfromer;

//开发者更多游戏
var gameListDeveloper_html = "game-developers.html?USERID=%1&OSFROMER=" + osfromer; 
//更多玩家
var moreplayers = "currentgame-moreplayers.html?USERID=%1&pageSize=%2&startIndex=%3&type=%4&gameId=%5&OSFROMER=" + osfromer;

//更多开发员
var moregame_developers = 'moregame-index.html?cpId=%1&OSFROMER='+ osfromer;

var moregame_developers_index = 'moregame-developers.html?cpId=%1&OSFROMER='+ osfromer;








