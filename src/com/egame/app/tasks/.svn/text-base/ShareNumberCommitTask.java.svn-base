/**
 * 
 */
package com.egame.app.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @author liuhan
 * 
 */
public class ShareNumberCommitTask extends AsyncTask<String, Integer, String> {
	private String type;
	private String userid;

	public ShareNumberCommitTask(String type,String userid) {
		this.type = type;
		this.userid = userid;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.i("AAAA", Urls.getShareNumber(type,userid));
//			return HttpConnect.getHttpString(Urls.getShareNumber(type,gameId, context));
			return HttpConnect.getHttpString(Urls.getShareNumber(type,userid));
		} catch (Exception e) {
			return "";
		}
	}

}
