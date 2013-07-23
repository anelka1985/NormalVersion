/**
 * 
 */
package com.egame.utils.ui;

import android.os.Bundle;

/**
 * 描述：页面之间跳转的Bundle 对象的获取的工具类
 * 
 * @author LiuHan
 * @version 1.0 create on ：2012、1、14
 * 
 */
public class BundleUtils {
	/**
	 * 功能：和游戏评论相关的数据封装的Bundle对象
	 * 
	 * @param gameId
	 * @param userId
	 * @return
	 */
	public static Bundle createPostCommentBundle(String gameId, String userId, String nickName, String gameName) {
		Bundle bundle = new Bundle();
		bundle.putString("gameId", gameId);
		bundle.putString("userId", userId);
		bundle.putString("nickName", nickName);
		bundle.putString("gameName", gameName);
		return bundle;
	}

	/**
	 * 功能：游戏详情Bundle对象
	 */
//	public static Bundle createGamesDetialBundle(int gameId) {
//		Bundle bd = new Bundle();
//		bd.putInt("gameId", gameId);
//		return bd;
//	}
}
