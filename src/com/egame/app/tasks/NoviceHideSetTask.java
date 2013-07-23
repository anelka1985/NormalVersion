package com.egame.app.tasks;

import weibo4android.org.json.JSONObject;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.NovicePrimaryActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * 用户登录异步任务
 * 
 * @author LiuHan
 * 
 */
public class NoviceHideSetTask extends AsyncTask<String, Integer, String> {
	/** 上下文 */
	private NovicePrimaryActivity context;
	String userId;
	int showAge;
	int showLocation;
	private ProgressDialog pDialog;

	public NoviceHideSetTask(NovicePrimaryActivity context, String userId, int showAge, int showLocation) {
		this.context = context;
		this.showAge = showAge;
		this.showLocation = showLocation;
		this.userId = userId;
		pDialog = new ProgressDialog(this.context);
		pDialog.setMessage(this.context.getResources().getString(R.string.egame_progress_dialog));
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getNoviceHideUrl(userId, showAge, showLocation, this.context));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if ("exception".equals(result)) {
			Toast.makeText(context, this.context.getResources().getString(R.string.egame_net_error), Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (0 == jobj.optInt("resultcode", 1)) {
					NovicePrimaryActivity.instance.enterFindFriend();
				} else {
					Toast.makeText(context, this.context.getResources().getString(R.string.egame_net_error), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(context, this.context.getResources().getString(R.string.egame_json_error), Toast.LENGTH_SHORT).show();
			}
		}
	}

}
