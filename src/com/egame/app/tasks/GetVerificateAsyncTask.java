/**
 * 
 */
package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weibo4android.org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.BaseInfoRegisterActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.PreferenceUtil;

/**
 * @author LiuHan
 * 
 */
public class GetVerificateAsyncTask extends AsyncTask<String, Integer, String> {
	private BaseInfoRegisterActivity context;
	private String phone;

	public GetVerificateAsyncTask(BaseInfoRegisterActivity context, String phone) {
		this.context = context;
		this.phone = phone;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if ("exception".equals(result)) {
			// 数据请求异常
			Toast.makeText(context, "验证码发送异常，稍候重试!", Toast.LENGTH_SHORT).show();

		} else {
			// 发送验证码成功
			JSONObject json;
			try {
				json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (null == json.optString("validateCode")) {
					PreferenceUtil.storePhonevalidate(context, "", phone);
				} else {
					if (0 == jobj.optInt("resultcode")) {
						this.context.showControlUI();
						Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
						Matcher m = pattern.matcher(json
								.optString("validateCode"));
						List<String> list = new ArrayList<String>();
						if (m.find()) {
							list.add(m.group(1));
						}
						if (null != list && list.size() > 0) {
							PreferenceUtil.storePhonevalidate(context,
									list.get(0), phone);
							Log.i(GetVerificateAsyncTask.class
									.getCanonicalName(), json
									.optString("validateCode"));
						} else {
							Toast.makeText(context, "验证码格式错误",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						if ("-1".equals(json.optString("validateCode"))) {
							// 手机号已经注册过了
							this.context.clearUserInput();
						}
					}

				}

				Toast.makeText(context, jobj.optString("resultmsg", ""),
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(
						context,
						context.getResources().getString(
								R.string.egame_json_error), Toast.LENGTH_SHORT)
						.show();

			}

		}
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			return HttpConnect.getHttpString(Urls.getVerificaterUrl(context,
					phone));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

}
