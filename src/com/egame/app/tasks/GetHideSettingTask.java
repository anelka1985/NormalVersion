/**
 * 
 */
package com.egame.app.tasks;

import org.json.JSONObject;

import com.egame.app.uis.HideSettingActivity;
import com.egame.beans.HideBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * @author LiuHan
 * 
 */
public class GetHideSettingTask extends AsyncTask<String, Integer, HideBean> {
	@Override
	protected void onPostExecute(HideBean result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if (null != result) {
			activity.initData(result);
		} else {
			// ToastUtil.show(activity, "数据加载失败");
		}
	}

	private HideSettingActivity activity;
	private ProgressDialog pDialog;

	public GetHideSettingTask(HideSettingActivity activity) {
		this.activity = activity;
		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("请稍候...");
		pDialog.show();
	}

	@Override
	protected HideBean doInBackground(String... params) {
		HideBean hBean = null;
		try {
			if (LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil
					.fetchUser(activity, LoginDataStoreUtil.LOG_FILE_NAME)
					.getUserId())) {

			} else {
				String hideUrl = Urls.getHideUrl(
						LoginDataStoreUtil.fetchUser(activity,
								LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
						activity);
				String str = HttpConnect.getHttpString(hideUrl, 15000);
				JSONObject obj = new JSONObject(str);
				hBean = new HideBean(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hBean;
	}

}
