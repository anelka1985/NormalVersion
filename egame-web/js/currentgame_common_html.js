//url链接定义
//游戏圈url连接
//获取游戏圈主页内容
//var currentGameCircleIndex_url = 'http://localhost:8080/Egame4Web/egame-web/data/subjectList.json?startIndex=%1&pageSize=%2';
var currentGameCircleIndex_url = base_url+'/sns-client/SDK/gameHub/subjectList.json?startIndex=%1&pageSize=%2';

//发布主题
var sendSubject_url = base_url+'/sns-client/SDK/gameHub/publishSubject.json?subjectTittle=%1&subjectContent=%2&USERID=%3';

//发布回复
var sendReply_url = base_url+'/sns-client/SDK/gameHub/replaySubject.json?subjectId=%1&USERID=%2&repcontent=%3';

//发布星级评论
var sendEvaluationLevel_url = base_url+'/sns-client/four/game/publishGameComment.json?gameId=%1&score=%2&USERID=%3&comment=%4&gameName=%5&nickName=%6';
//获取星级评论
var getEvaluationLevel_url = base_url+'/sns-client/four/game/publishGameComment.json?gameId=%1&USERID=%2&type=0';

//获取游戏圈详细主题
//var currentGameCircleDetail_url = 'http://localhost:8080/Egame4Web/egame-web/data/subjectDetail.json?subjectId=%1&startIndex=%2&pageSize=%3';
var currentGameCircleDetail_url = base_url+'/sns-client/SDK/gameHub/subjectDetail.json?subjectId=%1&startIndex=%2&pageSize=%3';

//获取成就列表
//var currentGameAchievement_url = 'data/achievement.json?USERID=%1&startIndex=%2&pageSize%3';
var currentGameAchievement_url = base_url+'/sns-client/SDK/game/archieveList.json?&startIndex=%1&pageSize=%2';

//获取成就用户列表
//var currentGameAchievementReached_url = 'data/achievementReached.json?USERID=%1&archieveId=%2&startIndex=%3&pageSize=%4&type=%5';
var currentGameAchievementReached_url = base_url+'/sns-client/SDK/game/archieveFriendList.json?USERID=%1&archieveId=%2&startIndex=%3&pageSize=%4&type=%5';

//url链接定义结束

//当前游戏主页
var currentGameIndex_html = 'currentgame-index.html?USERID=%1';

//游戏圈动态连接
//游戏圈首页链接
var currentGameCircleIndex_html = 'currentgame-circle-index.html?USERID=%1';

//发布主题
var circlePublishKeyNote_html = 'currentgame-keynote.html?USERID=%1';

//主题详细
var currentgameCircleDetail_html = 'currentgame-circle-detail.html?subjectId=%1&USERID=%2';

//回复主题
var currentGameReply_html = 'currentgame-reply.html?subjectId=%1&USERID=%2';

//成就主页
var currentGameAchievement_html = 'currentgame-achievement.html?USERID=%1';

//达成此成就用户
var currentGameAchievementReached_html = 'currentgame-achievement-reached.html?USERID=%1&userType=%2&archieveId=%3';

