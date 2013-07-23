package com.egame.app.tasks;

import android.os.Handler;
import android.os.Message;

import com.egame.config.Const;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 根据url读字符串返回
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
public class GetStringRunnable implements Runnable {

	Handler handler;
	String url;
	boolean isAlive = true;

	public GetStringRunnable(Handler handler, String url) {
		this.handler = handler;
		this.url = url;
	}

	@Override
	public void run() {
		Message msg = new Message();
		try {
			String s = HttpConnect.getHttpString(url);
			if(isAlive){
				
				msg.what = Const.MSG_GET_SUCCESS;
				msg.obj = s;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.what = Const.MSG_GET_FAIL;
			handler.sendMessage(msg);
		}
	}
	
	public void stop(){
		isAlive = false;
	}

}
