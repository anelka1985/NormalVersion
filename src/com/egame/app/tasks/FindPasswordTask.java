/**
 * 
 */
package com.egame.app.tasks;

import weibo4android.org.json.JSONObject;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.FindPassWordActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * 描述：找回密码异步任务
 * 
 * @author LiuHan
 * @version 1.0 Create On :Thu 8 Mar,2012
 */
public class FindPasswordTask extends AsyncTask<String, Integer, String> {

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if ("exception".equals(result)) {
			Toast.makeText(
					context,
					this.context.getResources().getString(
							R.string.egame_net_error), Toast.LENGTH_SHORT)
					.show();
		} else {
			try {
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (0 == jobj.optInt("resultcode", -1)) {
					Log.i(FindPasswordTask.class.getCanonicalName(),"找回密码成功");
					// 找回密码成功
					this.context.finish();
				}
				Toast.makeText(context, jobj.optString("resultmsg"),
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(
						context,
						this.context.getResources().getString(
								R.string.egame_json_error), Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls
					.getFindPasswordUrl(this.findWay,this.context,arg0[0]));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

	public FindPasswordTask(FindPassWordActivity context, String way) {
		this.context = context;
		this.findWay = way;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(context.getResources().getString(
				R.string.egame_progress_dialog));
		pDialog.show();
	}

	private String findWay;
	private FindPassWordActivity context;
	private ProgressDialog pDialog;

}
