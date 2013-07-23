package com.egame.utils.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author LiuHan
 * @version 1.0 create on Thursday 14:19,2012/4/19
 */
public class LoginDataStoreUtil {
	private static final String LOG_TAG = LoginDataStoreUtil.class.getCanonicalName();
	public static final String NULL_VALUE = "0";
	public static final String PAG_FILE_NAME = "pagging_user";
	public static final String LOG_FILE_NAME = "login_user";

	private static final String IMSI = "imsi";
	private static final String ID = "userid";
	private static final String NICK_NAME = "nickname";
	private static final String ACCOUNT = "account";
	private static final String GENDER = "gender";
	private static final String NOVICE = "novice";

	/** 反查的状态 0：反查成功 1：反查失败 2：没有手机卡 */
	public static int PAGGING_STATE = -1;

	/**
	 * 私有构造函数 不能创建对象
	 */
	private LoginDataStoreUtil() {
		Log.i(LOG_TAG, "不能创建 LoginDataStoreUtil 类的实例!");
	}

	/**
	 * 存储反手机号成功得到用户信息
	 * 
	 * @param context
	 * @param user
	 *            存储用户信息的实体类
	 * @param fileName
	 *            信息存储的文件名称 其值为：PAG_FILE_NAME 或 LOG_FILE_NAME
	 */
	public static void storeUser(Context context, User user, String fileName) {
		if (isCanEffect(fileName, "storeUser()")) {
			SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(IMSI, user.getCurrentImsi());
			editor.putString(ID, user.getUserId());
			editor.putString(NICK_NAME, user.getNickName());
			editor.putString(ACCOUNT, user.getAccountName());
			editor.putInt(GENDER, user.getGender());
			editor.putInt(NOVICE, user.getIsCompleteNoviceTask());
			editor.commit();
		}
	}

	/**
	 * 取出反查手机号成功得到的用户信息
	 * 
	 * @param context
	 * @param fileName
	 *            信息存储的文件名称 其值为：PAG_FILE_NAME 或 LOG_FILE_NAME
	 * @return
	 */
	public static User fetchUser(Context context, String fileName) {
		User user = new User();
		user.setUserId(NULL_VALUE);
		user.setCurrentImsi(NULL_VALUE);
		user.setUserId(NULL_VALUE);
		user.setNickName(NULL_VALUE);
		user.setAccountName(NULL_VALUE);
		if (isCanEffect(fileName, "fetchUser()") && null != user.getUserId() && !"".equals(user.getUserId()) && !"null".equals(user.getUserId())) {
			SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			user.setCurrentImsi(settings.getString(IMSI, NULL_VALUE));
			user.setUserId(settings.getString(ID, NULL_VALUE));
			user.setNickName(settings.getString(NICK_NAME, NULL_VALUE));
			user.setAccountName(settings.getString(ACCOUNT, NULL_VALUE));
			user.setGender(settings.getInt(GENDER, 1));
			user.setIsCompleteNoviceTask(settings.getInt(NOVICE, 0));
			return user;
		} else {
			return user;
		}
	}

	/**
	 * 清空反查手机号成功得到的用户信息的存储
	 * 
	 * @param context
	 * @param fileName
	 *            要清除内容存储的文件名称 其值为：PAG_FILE_NAME 或 LOG_FILE_NAME
	 */
	public static void clearUser(Context context, String fileName) {
		if (isCanEffect(fileName, "clearUser()")) {
			SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.clear();
			editor.commit();
		}
	}

	public static void logOffUser(Activity context) {
		LoginDataStoreUtil.User user = new LoginDataStoreUtil.User();
		LoginDataStoreUtil.User user1 = LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME);
		clearUser(context, LoginDataStoreUtil.LOG_FILE_NAME);
		user.setCurrentImsi(user1.getCurrentImsi());
		user.setUserId(NULL_VALUE);
		user.setAccountName(NULL_VALUE);
		user.setGender(1);
		user.setNickName(NULL_VALUE);
		user.setIsCompleteNoviceTask(0);
		LoginDataStoreUtil.storeUser(context, user, LoginDataStoreUtil.LOG_FILE_NAME);
	}

	/**
	 * 更新用户信息存储
	 */
	public static void updateUser(Context context, String fileName) {

	}

	/**
	 * 判断用户文件名称的合法性
	 */
	private static boolean isCanEffect(String fileName, String functName) {
		if (PAG_FILE_NAME.equals(fileName) || LOG_FILE_NAME.equals(fileName)) {
			return true;
		} else {
			Log.i(LOG_TAG, functName + "参数 fileName 不正确 ：PAG_FILE_NAME  或  LOG_FILE_NAME");
			return false;
		}
	}

	/**
	 * 用户信息实体类
	 */
	public static class User {
		/** 和用户相关联的当前设备的imsi号 */
		private String currentImsi = "0";
		/** 用户的ID */
		private String userId = "0";
		/** 用户的昵称 */
		private String nickName = "0";
		/** 帐号 */
		private String accountName = "0";
		private int gender = 1;
		private int isCompleteNoviceTask = 0;

		private String phone = "0";

		/**
		 * @return the currentImsi
		 */
		public String getCurrentImsi() {
			return currentImsi;
		}

		/**
		 * @param currentImsi
		 *            the currentImsi to set
		 */
		public void setCurrentImsi(String currentImsi) {
			this.currentImsi = currentImsi;
		}

		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * @param userId
		 *            the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}

		/**
		 * @return the nickName
		 */
		public String getNickName() {
			return nickName;
		}

		/**
		 * @param nickName
		 *            the nickName to set
		 */
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		/**
		 * @return the accountName
		 */
		public String getAccountName() {
			return accountName;
		}

		/**
		 * @param accountName
		 *            the accountName to set
		 */
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}

		/**
		 * @return the gender
		 */
		public int getGender() {
			return gender;
		}

		/**
		 * @param gender
		 *            the gender to set
		 */
		public void setGender(int gender) {
			this.gender = gender;
		}

		/**
		 * @return the isCompleteNoviceTask
		 */
		public int getIsCompleteNoviceTask() {
			return isCompleteNoviceTask;
		}

		/**
		 * @param isCompleteNoviceTask
		 *            the isCompleteNoviceTask to set
		 */
		public void setIsCompleteNoviceTask(int isCompleteNoviceTask) {
			this.isCompleteNoviceTask = isCompleteNoviceTask;
		}

		/**
		 * @return the phone
		 */
		public String getPhone() {
			return phone;
		}

		/**
		 * @param phone
		 *            the phone to set
		 */
		public void setPhone(String phone) {
			this.phone = phone;
		}
	}

}
