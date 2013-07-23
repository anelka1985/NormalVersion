package com.egame.app.tasks;

import weibo4android.org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.egame.R;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;

/**
 * 描述：用户注册的异步任务
 * 
 * @author LiuHan
 * @version 1.0 create on:Wed 29 Feb,2012
 */
public class UserRegisterAsyncTask extends AsyncTask<String, Integer, String> {
	/** 上下文 */
	private Activity context;
	/** 手机号 */
	private String phone;
	/** 性别 */
	private int grender;
	/** 注册提示对话框 */
	private ProgressDialog pDialog;

	public UserRegisterAsyncTask(Activity context, String phone, int grender) {
		this.context = context;
		this.phone = phone;
		this.grender = grender;
		this.pDialog = new ProgressDialog(context);
		pDialog.setMessage(context.getResources().getString(R.string.egame_progress_dialog));
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getRegisterUrl(context, phone, grender), 30000);
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
			Toast.makeText(this.context, this.context.getResources().getString(R.string.egame_net_error), Toast.LENGTH_SHORT).show();
		} else {
			try {
				TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String imsi = telManager.getSubscriberId();
				if (null == imsi || "".equals(imsi)) {
					imsi = LoginDataStoreUtil.NULL_VALUE;
				}
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (0 == jobj.optInt("resultcode", 1)) {
					LoginDataStoreUtil.User user = new LoginDataStoreUtil.User();
					user.setAccountName(json.optString("mobilephone", LoginDataStoreUtil.NULL_VALUE));
					user.setUserId(json.optString("userId", LoginDataStoreUtil.NULL_VALUE));
					user.setPhone(json.optString("mobilephone", LoginDataStoreUtil.NULL_VALUE));
					user.setNickName(json.optString("nickName", LoginDataStoreUtil.NULL_VALUE));
					user.setIsCompleteNoviceTask(json.optInt("isCompleteNoviceTask", 0));
					user.setGender(json.optInt("gender", 1));
					user.setCurrentImsi(imsi);

					// LoginDataStoreUtil.storeUser(context, user,
					// LoginDataStoreUtil.PAG_FILE_NAME);
					LoginDataStoreUtil.storeUser(context, user, LoginDataStoreUtil.LOG_FILE_NAME);
					// TODO isWebStart 有什么作用 
					Const.isWebStart = "";
					context.setResult(Activity.RESULT_OK);
					context.finish();
				} else {
					// 注册失败
					Log.i(UserRegisterAsyncTask.class.getCanonicalName(), json.optString("resultmsg", ""));
				}
				Toast.makeText(this.context, jobj.optString("resultmsg", ""), Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this.context, this.context.getResources().getString(R.string.egame_json_error), Toast.LENGTH_SHORT).show();
			}
		}
	}

}
