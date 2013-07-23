package com.egame.app.tasks;

import org.apache.http.client.methods.HttpGet;

import weibo4android.org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.UserLoginActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;

/**
 * 用户登录异步任务
 * 
 * @author LiuHan
 * 
 */
public class UserLoginAsyncTask extends AsyncTask<String, Integer, String> {
	/** 上下文 */
	private UserLoginActivity context;
	/** 手机号 */
	private String account;
	/** 性别 */
	private String clipher;
	/** 注册提示对话框 */
	private ProgressDialog pDialog;
//	private String where = "";

	public UserLoginAsyncTask(UserLoginActivity context, String account, String clipher) {
		this.context = context;
		this.account = account;
		this.clipher = clipher;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(context.getResources().getString(R.string.egame_progress_dialog));
		pDialog.show();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if ("exception".equals(result)) {
			Toast.makeText(context, this.context.getResources().getString(R.string.egame_net_error), Toast.LENGTH_SHORT).show();
		} else if ("输入不合法，请重新输入".equals(result)) {
			Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				enterWhere(json, jobj);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(context, context.getResources().getString(R.string.egame_json_error), Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 进入社区
	 */
	private void enterWhere(JSONObject json, JSONObject jobj) {
		if (0 == jobj.optInt("resultcode", 1)) {
			LoginDataStoreUtil.User user1 = LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.PAG_FILE_NAME);
			LoginDataStoreUtil.User user = new LoginDataStoreUtil.User();
			user.setUserId(json.optString("userId", LoginDataStoreUtil.NULL_VALUE));
			user.setNickName(json.optString("nickName", LoginDataStoreUtil.NULL_VALUE));
			user.setAccountName(account);
			user.setGender(json.optInt("gender", 1));
			//TODO 是否获完成了新手任务的默认值为1似乎更合适
			user.setIsCompleteNoviceTask(json.optInt("isCompleteNoviceTask", 1));
			user.setCurrentImsi(user1.getCurrentImsi());
			LoginDataStoreUtil.storeUser(context, user, LoginDataStoreUtil.LOG_FILE_NAME);
			context.setResult(Activity.RESULT_OK);
			context.finish();
		} else {
			Toast.makeText(context, jobj.optString("resultmsg", ""), Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imsi = telManager.getSubscriberId() + "";
			String str = Urls.getLoginUrl(context, account, clipher, imsi);
			try {
				// 请求参数合法性的检查 针对登录输入 不对HttpConnect.getHttpString()异常抛出处理
				new HttpGet(str);
			} catch (IllegalArgumentException ex) {
				return "输入不合法，请重新输入";
			}
			return HttpConnect.getHttpString(str);
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

}
