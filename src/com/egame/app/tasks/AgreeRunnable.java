package com.egame.app.tasks;

import android.content.Context;

import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;

/**
 * 同意/不同意流量协议
 * @author zhouzhe@lenovo-cw.com
 *
 */
public class AgreeRunnable implements Runnable {
	
	int agree;
	Context context;
	
	/**
	 * 记录同意/不同意流量协议
	 * @param agree 0:同意  1不同意
	 * @param context
	 */
	public AgreeRunnable(int agree,Context context) {
		this.agree = agree;
		this.context = context;
	}

	@Override
	public void run() {
		try {
			HttpConnect.getHttpString(Urls.getAgreeUrl(agree, context));
		} catch (Exception e) {
			e.printStackTrace();
			L.d("AgreeRunnable","同意流量协议:" + agree);
		}
	}

}
