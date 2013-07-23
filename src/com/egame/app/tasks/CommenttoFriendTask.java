package com.egame.app.tasks;

import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

import android.os.AsyncTask;
import android.util.Log;

public class CommenttoFriendTask extends AsyncTask<String, Integer, String> {
	private String UserId;
	public CommenttoFriendTask(String UserId){
		this.UserId = UserId;
	}
	@Override
	protected String doInBackground(String... params) {
		try {
			Log.i("BBBBBB", "BBBBBB="+UserId);
			return HttpConnect.getHttpString(Urls.gettoFrinendNumber(UserId));
		} catch (Exception e) {
			return "";
		}
	}
}
