//100 android
//200 wp7
var osfromer = 100;
var f = GetOsFromer("OSFROMER");
if (f != null)
	osfromer = f;

// 正则表达式, 获取 URL 中的参数
function GetOsFromer(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
/**
 * 显示图片
 * 
 * @param imageUrl
 *            图片URL
 */
function showImage(imageUrl) {
	if (osfromer == 100) {
		window.jsCall.showImage(imageUrl);
	} else if (osfromer == 200) {
		window.external.notify('showImage;' + imageUrl);
	}
}

/**
 * 显示新消息标签
 */
function showNewMessage() {
	if (osfromer == 100) {
		window.jsCall.showNewMessage();
	} else if (osfromer == 200) {
		window.external.notify('showNewMessage;');
	}
}

/**
 * 后退
 */
function goBack() {
	if (osfromer == 100) {
		window.jsCall.goBack();
	} else if (osfromer == 200) {
		window.external.notify('goBack');
	}
}

function getHeader() {
	if (osfromer == 100) {
		return window.jsCall.getHeader();
	} else if (osfromer == 200) {
		window.external.notify('getHeader');
		return '';
	}
}

/**
 * 打开系统浏览器
 * 
 * @param url
 *            URL
 */
function showSysBrowse(url) {
	if (osfromer == 100) {
		window.jsCall.showSysBrowse(url);
	} else if (osfromer == 200) {
		window.external.notify('showSysBrowse;' + url);
	}
}

/**
 * Toast提示
 */
function showToast(msg) {
	if (osfromer == 100) {
		window.jsCall.toast(msg);
	} else if (osfromer == 200) {
		window.external.notify('toast;' + msg);
	}
}

/**
 * 弹出选择对话框
 */
function showChoiceDialog(msg, callBack) {
	if (osfromer == 100) {
		window.jsCall.showChoiceDialog(msg, callBack);
	} else if (osfromer == 200) {
		window.external.notify('showChoiceDialog;' + msg + ',' + callBack);
	}
}

/**
 * 打开日期选择对话框 callBack-insertCommon(%1$s,%2$s,%3$s)
 */
function showDateDialog(year, month, day, callBack) {
	if (osfromer == 100) {
		window.jsCall.openDateDialog(year, month, day, callBack);
	} else if (osfromer == 200) {
		window.external.notify('openDateDialog;' + year + ',' + month + ','
				+ day + ',' + callBack);
	}
}

/**
 * 打开性别选择对话框 callBack-insertCommon(%1$s)
 */
function showGenderDialog(callBack) {
	if (osfromer == 100) {
		window.jsCall.openGenderDialog(callBack);
	} else if (osfromer == 200) {
		window.external.notify('openGenderDialog;' + callBack);
	}
}

/**
 * final String title, final String text, final String hint, final String
 * patternStr, final String formatErrorTip, final String callBack
 * 
 * callBack-insertCommon(%1$s)
 * 
 */
function showTextDialog(title, text, hint, patternStr, formatErrorTip, callBack) {
	if (osfromer == 100) {
		window.jsCall.openTextDialog(title, text, hint, patternStr,
				formatErrorTip, callBack);
	} else if (osfromer == 200) {
		window.external.notify('openTextDialog;' + title + ',' + text + ','
				+ hint + ',' + patternStr + ',' + formatErrorTip + ','
				+ callBack);
	}
}

/**
 * 弹出输入对话框,js中调用
 * 
 * @param param
 *            true|false,text,textHint,buttonText,callService,callbackFunction
 *            true|false - 是否可以输入表情，true-可以，false-不可以 text - 输入框显示内容 textHint -
 *            输入框内容为空时提示语 callbackFunction -
 *            不为null表示提交成功后需要回调js方法，如果需要在回调必须预留1个位置插入输入内容
 * 
 * 例1： showInputPopup('true,,请输入评论内容,发送,insertCommon(%1$s,%2$D,%3$S)') 可以输入表情
 * EditText没有默认内容 EditText的hint为请输入评论内容 Button的文字为发送
 * 回调js方法insertCommon()，必须预留1个位置输入内容
 */
function showInputPopup(param) {
	if (osfromer == 100) {
		window.jsCall.showPopupInput(param);
	} else if (osfromer == 200) {
		window.external.notify('showPopupInput;' + param);

	}
}

/**
 * 弹出照片选择对话框,js中调用
 */
function showPicDialog() {
	if (osfromer == 100) {
		window.jsCall.showPicDialog();
	} else if (osfromer == 200) {
		window.external.notify('showPicDialog');

	}
}

/**
 * 控制台打印
 * 
 * @param param
 */
function printToConsole(param) {
	if (osfromer == 100) {
		window.jsCall.println(param);
	} else if (osfromer == 200) {
		window.external.notify('println;' + param);
	}
}

/**
 * 页面开始加载调用显示加载进度条
 */
function pageLoadStart() {
	if (osfromer == 100) {
		window.jsCall.pageLoadStart();
	} else if (osfromer == 200) {
		window.external.notify('pageLoadStart');
	}
}

/**
 * 页面加载完成调用关闭加载进度条
 */
function pageLoadFinish() {
	if (osfromer == 100) {
		window.jsCall.pageLoadFinish();
	} else if (osfromer == 200) {
		window.external.notify('pageLoadFinish');
	}
}

/**
 * 得到本地通讯录 姓名,手机号,姓名,手机号,姓名,手机号
 */
function getContacts() {
	if (osfromer == 100) {
		window.jsCall.getContacts();
		return '';
	} else if (osfromer == 200) {
		window.external.notify('getContacts');
		return '';
	}
}

/**
 * 打开详情界面，这里包括游戏详情，活动详情等等
 */
function showDetail(type, id) {
	if (osfromer == 100) {
		window.jsCall.showDetail(type, id);
	} else if (osfromer == 200) {
		window.external.notify('showDetail;' + type + ',' + id);
	}
}

/**
 * 发送短信 phoneNumbers - 电话号码数组 手机号,手机号,手机号,手机号,手机号,手机号 content - 短信内容
 */
function sendSms(phoneNumbers, content) {
	if (osfromer == 100) {
		window.jsCall.sendSms(phoneNumbers, content);
	} else if (osfromer == 200) {
		window.external.notify('sendSms;' + phoneNumbers + ',' + content);
	}
}

/**
 * 打开新手任务
 */
function openNewTask() {
	if (osfromer == 100) {
		window.jsCall.openNewTask();
	}
	else if(osfromer==200) {
		window.external.notify('openNewTask');
	}
 }

/**
 * 得到推荐游戏，page-页数，type-1出现在我的游戏，type-2出现在我的收藏
 */
function getHotGames() {
	if (osfromer == 100) {
		window.jsCall.getHotGames();
	} else if(osfromer==200) {
		window.external.notify('getHotGames');
	}
 }
 

function setNickname(nickname) {
	if (osfromer == 100) {
		window.jsCall.setNickname(nickname);
	} else if(osfromer==200) {
		window.external.notify('setNickname;'+nickname);
	}
}

 /**
  * 弹出对话框
  */
function showDialog(content) {
	if(osfromer==100) {
 		window.jsCall.showDialog(content);
 	}
 	else if(osfromer==200) {
		window.external.notify('showDialog;'+content);
 	}
}
 
 /**
  * 下载游戏，SDK游戏详情调用，type - ‘web或者client二选一’，id - gameId
  */
function downloadGame(type,id) {
 	if(osfromer==100) {
        window.jsCall.downloadGame(type,id);
	}
	else if(osfromer==200) {
		window.external.notify('downloadGame;'+type+','+id);
	}
}

/**
 * 有pk大厅发起pk或接受挑战,SDK调用
 * 
 * @param type 1=发起pk,2=接受挑战
 * @param pkId 接受的挑战对应的pkid,当上述type=1 发起pk时,此参数为0
 * @author zhouzhe@lenovo-cw.com
 * @time 2012-4-17
 */
function startPk(pkType,pkId) {
	if (osfromer == 100) {
		window.jsCall.startPk(pkType,pkId);
	} else if (osfromer == 200) {
		window.external.notify('startPk;'+pkType+','+pkId);
	}
}

/**
 * 获取游戏名称
 * @returns
 */
function getGameName(){
	if (osfromer == 100) {
		return window.jsCall.getGameName();
	} else if (osfromer == 200) {
	}
}

/**
 * 保存PK用户名
 */
function setPkUserName(username){
	if (osfromer == 100) {
		window.jsCall.setPkUserName(username);
	} else if (osfromer == 200) {
	}
}

/**
 * 获取pk用户名
 * @returns
 */
function getPkUserName(){
	if (osfromer == 100) {
		return window.jsCall.getPkUserName();
	} else if (osfromer == 200) {
	}
}
