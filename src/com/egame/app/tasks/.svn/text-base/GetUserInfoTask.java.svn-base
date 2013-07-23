/**
 * 
 */
package com.egame.app.tasks;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.NovicePrimaryActivity;
import com.egame.beans.UserBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * 描述：取得用户信息的异步任务
 * 
 * @author LiuHan
 * @version 1.0 create on:Mon 27 Feb,2012
 */
public class GetUserInfoTask extends AsyncTask<String, Integer, UserBean> {
	/** 上下文 */
	private NovicePrimaryActivity context;
	/** 用户的ID */
	private String userId;
	/** 用户信息实体类 */
	private UserBean userBean;

	private ProgressDialog pDialog;

	public GetUserInfoTask(NovicePrimaryActivity context, String userId) {
		this.context = context;
		this.userId = userId;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(context.getResources().getString(R.string.egame_progress_dialog));
		pDialog.show();
	}

	@Override
	protected UserBean doInBackground(String... arg0) {
		try {
			String s = HttpConnect.getHttpString(Urls.getUserInfoUrl(context, userId),20000);
			JSONObject userObj = new JSONObject(s).getJSONObject("myInfo");
			userBean = new UserBean(userObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}

	@Override
	protected void onPostExecute(UserBean result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if (null == result) {
			Toast.makeText(context,context.getResources().getString(R.string.egame_net_error),Toast.LENGTH_SHORT).show();
			context.finish();
		} else {
			context.addDataControlUI(result);
		}

	}

}
