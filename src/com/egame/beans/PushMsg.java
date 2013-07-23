package com.egame.beans;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-30 上午10:12:11
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class PushMsg {
	// 新活动推送
	public static final int PUSH_NEW_ACTIVITY = 1;
	// 新游戏推送
	public static final int PUSH_NEW_GAME = 2;
	// 游戏升级推送
	public static final int PUSH_GAME_UPGRADE = 3;
	// 社区消息推送
	public static final int PUSH_MESSAGE = 4;
	// 系统通知
	public static final int PUSH_SYSTEM = 5;
	// 客户端升级通知
	public static final int PUSH_CLIENT_UPGRADE = 6;
	// 切换到搜索页面,根据标签搜索
	public static final int SWITCH_SEARCH_LABEL = 7;
	// 切换到搜索页面,根据提供商搜索
	public static final int SWITCH_SEARCH_PROVIDER = 8;
	public int mType;
	public String mTitle;
	public String mBody;
	public String mLink;

	public PushMsg(int type, String title, String body, String link) {
		mType = type;
		mTitle = title;
		mBody = body;
		mLink = link;
	}
}
