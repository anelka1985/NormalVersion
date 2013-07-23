package com.egame.app.tasks;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.EnterCommunity;

/**
 * 启动客户端时反查失败 用户点击社区时 再反查一次 本次反查只设两个状态 ：反查成功 和 没卡 （即如果再此反查失败 就当 没有卡进行处理
 * 
 * @author LiuHan
 * @version 1.0 create on:Friday 09:27
 */
public class GetPhoneNumberTask extends AsyncTask<String, Integer, String> {
	private ProgressDialog pDialog;
	private Activity ehActivity;
	String imsi = "null";
	EnterCommunity enterCommunity;

	public GetPhoneNumberTask(Activity ehActivity, EnterCommunity enterCommunity) {
		this.ehActivity = ehActivity;
		this.enterCommunity = enterCommunity;
		pDialog = new ProgressDialog(ehActivity);
		pDialog.setMessage("请稍候....");
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			TelephonyManager telManager = (TelephonyManager) ehActivity.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = telManager.getSubscriberId() + "";
			return HttpConnect.getHttpString(Urls.getPhoneNumberByImsiUrl(ehActivity, imsi));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

	/**
	 * @param result
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if ("exception".equals(result)) {
			LoginDataStoreUtil.PAGGING_STATE = 2;
			enterCommunity.enterCommunityOne();
		} else {
			try {
				JSONObject obj = new JSONObject(result);
				JSONObject json = obj.getJSONObject("result");
				if (0 == json.optInt("resultcode", -1)) {
					// 反查成功
					LoginDataStoreUtil.User user = new LoginDataStoreUtil.User();
					user.setAccountName(obj.optString("mobilePhone", LoginDataStoreUtil.NULL_VALUE));
					user.setCurrentImsi(imsi);
					user.setUserId(obj.optString("userId", LoginDataStoreUtil.NULL_VALUE));
					user.setNickName(obj.optString("nickName", LoginDataStoreUtil.NULL_VALUE));
					// 存储数据
					LoginDataStoreUtil.storeUser(ehActivity, user, LoginDataStoreUtil.PAG_FILE_NAME);
					LoginDataStoreUtil.PAGGING_STATE = 0;
					enterCommunity.enterCommunityOne();
				} else {
					// 反查失败
					LoginDataStoreUtil.PAGGING_STATE = 2;
					enterCommunity.enterCommunityOne();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				LoginDataStoreUtil.PAGGING_STATE = 2;
				enterCommunity.enterCommunityOne();
			}
		}
	}

}
