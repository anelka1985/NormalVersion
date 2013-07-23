package com.egame.utils.ui;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.egame.app.tasks.GetPhoneNumberTask;
import com.egame.app.uis.EgameWebActivity;
import com.egame.app.uis.FinishRegisterActivity;
import com.egame.app.uis.UserLoginActivity;
import com.egame.beans.UserBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;

/**
 * 描述：触发社区行为的事件处理类
 * 
 * @author LiuHan
 * @version 1.0 create on:2012/3/29
 * 
 */
public class EnterCommunity {
	private static final String LOG_TAG = EnterCommunity.class.getCanonicalName();
	public static EnterCommunity instance;
	private RegisterUtils registerUtils;
	/** 当前请求登录社区的帐号（手机号形式) */
	private Activity homeActivity;
	private String flag = "";
	private String friendid = "";
	// 登录到社区时的有效的用户ID
	private String userID = LoginDataStoreUtil.NULL_VALUE;
	String account = LoginDataStoreUtil.NULL_VALUE;
	String imsi1 = LoginDataStoreUtil.NULL_VALUE;
	String imsi2 = LoginDataStoreUtil.NULL_VALUE;
	public static final int RCODE_ENTERCOMUNITY_LOGIN = 100;

	/**
	 * @param homeActivity
	 */
	public EnterCommunity(Activity homeActivity, String flag) {
		this.homeActivity = homeActivity;
		this.registerUtils = new RegisterUtils(homeActivity);
		this.flag = flag;
		instance = this;
		this.imsi1 = LoginDataStoreUtil.fetchUser(homeActivity, LoginDataStoreUtil.PAG_FILE_NAME).getCurrentImsi();
		this.imsi2 = LoginDataStoreUtil.fetchUser(homeActivity, LoginDataStoreUtil.LOG_FILE_NAME).getCurrentImsi();
	}

	public EnterCommunity(Activity homeActivity, String flag, String friendid) {
		this(homeActivity, flag);
		this.friendid = friendid;
	}

	/**
	 * 进入流程的判断
	 */
	public void enter() {
		if (LoginDataStoreUtil.PAGGING_STATE == 2 || LoginDataStoreUtil.PAGGING_STATE == 0) {
			// 手机没有有效的手机卡 或者 反查手机号成功
			Log.i(LOG_TAG, "enterCommunityOne() excute");
			enterCommunityOne();
		} else {
			Log.i(LOG_TAG, "GetPhoneNumberTask() excute");
			// 手机有卡反查手机号失败 （失败：可能是由于网络问题 接口没有正常返回
			new GetPhoneNumberTask(homeActivity, instance).execute("");
		}
	}

	/**
	 * 从目标文件中读取用户id 和 帐号
	 * 
	 * @param user
	 */
	public void initIdAccount(LoginDataStoreUtil.User user) {
		userID = user.getUserId() == null ? LoginDataStoreUtil.NULL_VALUE : user.getUserId();
		account = user.getAccountName() == null ? LoginDataStoreUtil.NULL_VALUE : user.getAccountName();
		LoginDataStoreUtil.storeUser(homeActivity, user, LoginDataStoreUtil.LOG_FILE_NAME);
	}

	/**
	 * 反查手机号成功 确定登录的用户ID 更新用户信息进入社区
	 */
	public void enterCommunityOne() {
		// 给要等录到社区的用户 id 和 帐号 赋值
		if (LoginDataStoreUtil.PAGGING_STATE == 2) {
			initIdAccount(LoginDataStoreUtil.fetchUser(homeActivity, LoginDataStoreUtil.LOG_FILE_NAME));
		} else {
			// 有手机卡
			if (!imsi1.equals(imsi2)) {
				initIdAccount(LoginDataStoreUtil.fetchUser(homeActivity, LoginDataStoreUtil.PAG_FILE_NAME));
			} else {
				initIdAccount(LoginDataStoreUtil.fetchUser(homeActivity, LoginDataStoreUtil.LOG_FILE_NAME));
			}
		}
		// 执行登录判断操作
		if (!LoginDataStoreUtil.NULL_VALUE.equals(userID)) {
			// 用户信息不为空 更新用户信息进入社区
			new UpdateUserInfo().execute(userID);
		} else {
			if (!LoginDataStoreUtil.NULL_VALUE.equals(account)) {
				// 查到的手机号没有注册 启动一般用户注册界面
				Intent intent = new Intent(homeActivity, FinishRegisterActivity.class);
				intent.putExtra("bundle", FinishRegisterActivity.getFinishRegisterBunder(account));
				homeActivity.startActivity(intent);
			} else {
				if (LoginDataStoreUtil.PAGGING_STATE == 2) {
					// 没有有效的手机卡
					registerUtils.showPromptRegister();
				} else {
					// 没有查到手机号
					Intent intent = new Intent(homeActivity, UserLoginActivity.class);
					homeActivity.startActivityForResult(intent,RCODE_ENTERCOMUNITY_LOGIN);
				}
			}
		}
	}

	/**
	 * 更新用户的信息
	 * 
	 * @author LiuHan
	 * @version 1.0
	 */
	class UpdateUserInfo extends AsyncTask<String, Integer, UserBean> {
		/** 用户信息实体类 */
		private UserBean userBean;
		private ProgressDialog pDialog;

		public UpdateUserInfo() {
			pDialog = new ProgressDialog(homeActivity);
			pDialog.setMessage("请稍候...");
			pDialog.show();
		}

		@Override
		protected UserBean doInBackground(String... arg0) {
			try {
				String s = HttpConnect.getHttpString(Urls.getUserInfoUrl(homeActivity, arg0[0]));
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
			try {
				LoginDataStoreUtil.User user = LoginDataStoreUtil.fetchUser(homeActivity, LoginDataStoreUtil.LOG_FILE_NAME);
				user.setNickName(result.getUserName());
				LoginDataStoreUtil.storeUser(homeActivity, user, LoginDataStoreUtil.LOG_FILE_NAME);
			} catch (Exception e) {
				// e.printStackTrace();
				Log.i(LOG_TAG, "数据获取失败，以原来的数据进行登录");
			}
			/*
			 * if (null != result && !"".equals(result.getUserId())) {
			 * LoginDataStoreUtil.User user = LoginDataStoreUtil.fetchUser(
			 * homeActivity, LoginDataStoreUtil.LOG_FILE_NAME);
			 * user.setNickName(result.getUserName()); } else { // 获取数据失败
			 * 以原来的数据信息进行登录 }
			 */
			// 未进行过新手任务且对于该账号是第一次提示
			if (!PreferenceUtil.fetchAccountIsReceive(homeActivity, userID)) {
				// 弹出新手任务提示
				DialogUtil.showEnterNoviceDialog(homeActivity, flag);
				PreferenceUtil.storeAccountIsReceive(homeActivity, userID);
			} else {
				Intent communityIntent = new Intent();
				communityIntent.setClass(homeActivity, EgameWebActivity.class);
				communityIntent.putExtra("page", flag);
				// 是否绑定手机号
				if (result != null) {
					communityIntent.putExtra("isCheckPhone", result.getPhoneCheck() == 1);
				}
				if (!TextUtils.isEmpty(friendid)) {
					communityIntent.putExtra("friendid", friendid);
				}
				homeActivity.startActivity(communityIntent);
			}
		}

	}
}
