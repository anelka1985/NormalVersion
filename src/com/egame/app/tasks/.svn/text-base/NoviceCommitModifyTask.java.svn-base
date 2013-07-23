/**
 * 
 */
package com.egame.app.tasks;

import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.NoviceGrendActivity;
import com.egame.app.uis.NovicePrimaryActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;

/**
 * 描述：新手任务中提交修改相关信息
 * 
 * @author LiuHan
 * @version 1.0 create on:Fri 2 Mar,2012
 */
public class NoviceCommitModifyTask extends AsyncTask<String, Integer, String> {
	private Activity context;
	private String userid;
	private int showAge;
	private int showLocation;
	private String birthday;
	private String nickName;
	private String province;
	private String city;
	private String interestId;
	private int grend;
	private ProgressDialog pDialog;
	private String tyle;

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if ("exception".equals(result) || "".equals(result)) {
			Toast.makeText(
					this.context,
					this.context.getResources().getString(
							R.string.egame_net_error), Toast.LENGTH_SHORT)
					.show();
		} else {

			try {
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (0 == jobj.optInt("resultcode", 1)) {
					NovicePrimaryActivity.instance.refreshUI(tyle,nickName);
					if ("hobby".equals(tyle)) {
						NoviceGrendActivity.instance.backActivity();
					} else if ("name".equals(tyle)) {
						//修改用户昵称成功 保存修改值
						LoginDataStoreUtil.User user = LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME);
						user.setNickName(nickName);
					}

				} else {
					Toast.makeText(this.context, jobj.optString("resultmsg"),
							Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(
						this.context,
						this.context.getResources().getString(
								R.string.egame_json_error), Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	/**
	 * 新手任务提交用户的修改
	 * 
	 * @param userid
	 *            用户id
	 * @param showAge
	 *            是否显示年龄91=显示；92=隐藏
	 * @param showLocation
	 *            是否显示所在地101=显示；102=隐藏
	 * @param birthday
	 *            生日
	 * @param nickName
	 *            昵称
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param hobby
	 *            爱好
	 * @return
	 */
	public NoviceCommitModifyTask(Activity context, String userid, int showAge,
			int showLocation, String birthday, String nickName,
			String province, String city, String interestId, int grend,
			String tyle) {
		this.context = context;
		this.userid = userid;
		this.city = city;
		this.nickName = nickName;
		this.province = province;
		this.showAge = showAge;
		this.showLocation = showLocation;
		this.birthday = birthday;
		this.interestId = interestId;
		this.grend = grend;
		this.tyle = tyle;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("请稍候....");
		pDialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getModifyUrl(context, userid,
					showAge, showLocation, birthday, nickName, province, city,
					interestId, grend, tyle));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

}
