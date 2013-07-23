package com.egame.app.tasks;

import android.os.Handler;
import android.os.Message;

import com.egame.config.Const;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 请求url,不做处理
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
public class GetRunnable implements Runnable {

	String url;

	public GetRunnable(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		try {
			HttpConnect.getHttpString(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
