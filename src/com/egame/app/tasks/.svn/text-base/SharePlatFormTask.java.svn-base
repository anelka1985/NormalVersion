package com.egame.app.tasks;

import java.util.List;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.app.uis.RecomPlatFormActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;

public class SharePlatFormTask extends AsyncTask<String, Integer, String> {
	private ProgressDialog pDialog;
	private RecomPlatFormActivity activity;
	private String msg = "";
	List<String> list;
	int successNum = 0;

	public SharePlatFormTask(RecomPlatFormActivity activity, List<String> list) {
		this.activity = activity;
		this.list = list;
		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("请稍候....");
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		String url = "";
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			url = Urls.getShareToFriendsUrl(LoginDataStoreUtil.fetchUser(activity, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					list.get(i), params[0],this.activity);
			try {
				str = HttpConnect.getHttpString(url, 15000);
				JSONObject obj = new JSONObject(str);
				JSONObject result = obj.getJSONObject("result");
				if (0 == result.optInt("resultcode", 1)) {
					successNum++;
					if (i == list.size() - 1 && successNum != 0) {
						// 有一个分享发送成功 就算发送成功
						msg = result.optString("resultmsg", "");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if(successNum>0){
			Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
			this.activity.finish();
		}else{
			Toast.makeText(activity, "网络异常,分享发送失败", Toast.LENGTH_SHORT).show();
		}
	}

}
