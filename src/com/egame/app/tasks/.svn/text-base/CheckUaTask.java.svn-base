package com.egame.app.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.egame.beans.GameInfo;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 检查游戏与机型是否适配
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-6
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class CheckUaTask extends AsyncTask<String, Integer, String> {
	GameInfo gameInfo;
	String UA;
	Activity act;
	CheckUaResultListener gurl;

	public CheckUaTask(CheckUaResultListener gurl, Activity act, GameInfo gameInfo, String UA) {
		this.gurl = gurl;
		this.act = act;
		this.gameInfo = gameInfo;
		this.UA = UA;
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getCheckUaUrl(act, gameInfo.getGameId(), UA));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result == null) {
			gurl.checkResult("");
		} else {
			if ("true".equals(result)) {
				gurl.startDownloadStep1();
			} else if ("false".equals(result)) {
				gurl.checkResult(result);
			} else {
				gurl.checkResult("");
			}
		}
	}

	public interface CheckUaResultListener {
		void checkResult(String s);

		void startDownloadStep1();
	}
}
