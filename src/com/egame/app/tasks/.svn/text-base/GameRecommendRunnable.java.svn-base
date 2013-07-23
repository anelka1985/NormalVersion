package com.egame.app.tasks;

import android.os.Handler;
import android.os.Message;

import com.egame.app.uis.GameRecommendActivity;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 读取游戏推荐
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author Administrator
 * 
 * @timer 2012-1-10
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameRecommendRunnable implements Runnable {
	
	Handler handler;
	String url;

	public GameRecommendRunnable(Handler handler,String url) {
		this.handler = handler;
		this.url = url;
	}

	@Override
	public void run() {
		Message msg = new Message();
		try {
			String s = HttpConnect.getHttpString(url);
			msg.what = GameRecommendActivity.MSG_GET_RECOMMEND_LIST_SUCCESS;
			msg.obj = s;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg.what = GameRecommendActivity.MSG_GET_RECOMMEND_LIST_FAIL;
			handler.sendMessage(msg);
		}
	}

}
