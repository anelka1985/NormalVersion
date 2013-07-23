/**
 * 
 */
package com.egame.app.tasks;

import weibo4android.org.json.JSONObject;

import com.egame.R;
import com.egame.app.uis.NoviceUpPhotoActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.Utils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * @author liuhan
 * 
 */
public class FinishNoviceTask extends AsyncTask<String, Integer, String> {

	private NoviceUpPhotoActivity context;
	private ProgressDialog pDialog;

	public FinishNoviceTask(NoviceUpPhotoActivity context) {
		this.context = context;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("请稍候...");
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getFinishNoviceUrl(LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME)
					.getUserId(), context));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (context.pDialog.isShowing()) {
			context.pDialog.dismiss();
		}
		if ("exception".equals(result)) {
			Toast.makeText(context, context.getResources().getString(R.string.egame_net_error), Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (0 == jobj.optInt("resultcode")) {
					// 存储用户完成新手任务的记录
					Intent intent = new Intent(Utils.EGAME_WEB_RECEIVER);
					context.sendBroadcast(intent);
					context.finish();
				} else {
					Toast.makeText(context, jobj.optString("resultmsg"), Toast.LENGTH_SHORT).show();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
