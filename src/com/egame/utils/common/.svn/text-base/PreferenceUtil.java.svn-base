package com.egame.utils.common;

import com.egame.beans.GameInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 类说明：
 * 
 * @创建时间 2012-1-4 上午11:13:32
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class PreferenceUtil {

	/** 和分享有关的share数据的存储文件名称 */
	public static String StoreDataName = "data_store";

	public static final String SHARED_GAME = "game";
	/** 是否弹出流量提示框,布尔类型 */
	public static final String KEY_ALERT_BOOLEAN = "alert";

	public static final String KEY_1STDOWN = "firstdown";

	public static final String KEY_PHONENUM_STRING = "phoneNum";

	public static final String PHONE_IS_REGISTER = "register";

	public static final String REGISTER_COME_OUT = "register";

	public static final String PHONE_VALIDATECODE = "verificater";

	/** 日志相关share内容 */
	public static final String SHARE_LOG_NAME = "log";

	/** UA */
	public static final String SHARE_LOG_KEY_UA = "UA";

	/** 版本 */
	public static final String SHARE_LOG_KEY_VERSION = "VERSION";

	/** 　来源 */
	public static final String SHARE_LOG_KEY_FROMER = "FROMER";

	/** imsi */
	public static final String SHARE_LOG_KEY_IMSI = "IMSI";

	/** 渠道 */
	public static final String SHARE_LOG_KEY_CHANNEL = "CHANNEL";

	/** 子渠道 */
	public static final String SHARE_LOG_KEY_SUBCHANNEL = "SUBCHANNEL";

	public static final String SHARE_UPDATE_NAME = "update";

	public static final String SHARE_UPDATE_KEY_NEW = "newVersion";

	public static final String SHARE_UPDATE_KEY_FORCE = "compelVersion";

	public static final String SHARE_UPDATE_KEY_PATH = "softurl";
	/** 软件更新内容 */
	public static final String SHARE_UPDATE_KEY_REMARK = "remark";
	public static final String SHARE_UPDATE_KEY_TYPE = "updatecode";
	/**
	 * 保存用户登录有关信息 请务在该文件中添加别的存储内容
	 */
	public static String StoreUserData = "user";

	/** 存储广告内容 */
	public static final String KEY_AD = "advertisment";
	/** 记录成功登录的用户的账号 */
	public static final String ACCOUNT_ARRAY = "account_array";
	public static final String SEG_CHAR_PHONES = "-";
	/** QAS集成相关统计：程序卸载或安装 */
	public static final String KET_QAS_INSTALL = "0050020004";
	/** QAS集成相关统计：搜索关键字 */
	public static final String KEY_QAS_SEARCH = "0050020001";
	/** QAS集成相关统计：点击广告 */
	public static final String KEY_QAS_AD = "0050020003";
	/** QAS集成相关统计：进入活动详 */
	public static final String KEY_QAS_ACTIVITY = "0050020002";

	/** 存储当前手机的imsi号 */
	public static final String IMSI_NUMBER = "imsi";

	private static final String FAIL_KEY = "fail_key";

	/**
	 * 得到后台常驻内存服务运行次数
	 * 
	 * @param context
	 * @return
	 */
	public static int getBackRunCount(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getInt("count", 0);
	}

	/**
	 * 是否第一次运行4版客户端
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isFrist(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isFristV4", true);
	}

	/**
	 * 设置欢迎界面实现过
	 * 
	 * @param context
	 */
	public static void setFrist(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isFristV4", false).commit();
	}

	/**
	 * 是否第一次运行4版客户端
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isFristDown(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean(KEY_1STDOWN, true);
	}

	/**
	 * 设置欢迎界面实现过
	 * 
	 * @param context
	 */
	public static void setFristDown(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean(KEY_1STDOWN, false).commit();
	}

	/**
	 * 保存后台常驻内存服务运行次数
	 * 
	 * @param context
	 * @param count
	 */
	public static void setBackRunCount(Context context, int count) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putInt("count", count).commit();
	}

	/**
	 * 得到后台常驻内存服务设置
	 * 
	 * @param context
	 * @return
	 */
	public static boolean[] getBackRunSetting(Context context) {
		boolean[] settings = new boolean[2];
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		settings[0] = prefs.getBoolean("push_game", true);
		settings[1] = prefs.getBoolean("push_message", true);
		return settings;
	}

	/**
	 * 设置后台常驻内存新游戏提示开关
	 * 
	 * @param context
	 * @param pushGame
	 */
	public static void setBackRunPushGame(Context context, boolean pushGame) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("push_game", pushGame).commit();
	}

	/**
	 * 设置后台常驻内存社区消息提示开关
	 * 
	 * @param context
	 * @param pushMessage
	 */
	public static void setBackRunPushMessage(Context context, boolean pushMessage) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("push_message", pushMessage).commit();
	}

	/**
	 * 得到上次消息id
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getSignIds(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		int[] ids = new int[3];
		ids[0] = prefs.getInt("newGameSign", 0);
		ids[1] = prefs.getInt("newActivitySign", 0);
		ids[2] = prefs.getInt("newSystemSign", 0);
		return ids;
	}

	/**
	 * 设置当前消息id
	 * 
	 * @param context
	 * @param ids
	 */
	public static void setSignIds(Context context, int[] ids) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putInt("newGameSign", ids[0]).putInt("newActivitySign", ids[1]).putInt("newSystemSign", ids[2]).commit();
	}

	/**
	 * 设置搜索标签
	 * 
	 * @param context
	 * @param label
	 */
	public static void setSearchLabel(Context context, String label) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putString("search_label", label).commit();
	}

	/**
	 * 得到搜索标签
	 * 
	 * @param context
	 * @return
	 */
	public static String getSearchLabel(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString("search_label", "");
	}

	/**
	 * 功能：和分享的类型相关的SharePreferences数值存储函数
	 * 
	 * @param activity
	 * @param str
	 *            分享类型：“game” 游戏分享 ；“client” 客户端的分享
	 */
	public static void storeType(Context activity, String str) {
		SharedPreferences settings = activity.getSharedPreferences(StoreDataName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("share_type", str);
		editor.commit();
	}

	/**
	 * 和分享的类型相关的SharePreferences数值获取函数
	 * 
	 * @param context
	 * @return 分享的類型 “game” 游戏分享 ；“client” 客户端的分享
	 */
	public static String fetchType(Context context) {
		SharedPreferences settings = context.getSharedPreferences(StoreDataName, Context.MODE_PRIVATE);
		return settings.getString("share_type", null);
	}

	/**
	 * 功能：和新浪微博 ，腾讯微博 的 token 的存储函数
	 * 
	 * @param activity
	 * @param token
	 * @param token_secret
	 * @param flag
	 *            ：微博的标识-->”sina”新浪微博 “tencent”腾讯微博
	 */
	public static void store(Context activity, String token, String token_secret, String flag) {
		SharedPreferences settings = activity.getSharedPreferences(StoreDataName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		if ("tencent".equals(flag)) {
			editor.putString("tencent_oauth_token", token);
			editor.putString("tencent_oauth_token_secret", token_secret);
		} else if ("sina".equals(flag)) {
			editor.putString("sina_token", token);
			editor.putString("sina_token_secret", token_secret);
		}
		editor.commit();
	}

	/**
	 * 功能：新浪微博 ，腾讯微博 的 token 的取值函数
	 * 
	 * @param context
	 * @param flag
	 * @return 存储 token 和 token_secret 数组
	 */
	public static String[] fetch(Context context, String flag) {
		SharedPreferences settings = context.getSharedPreferences(StoreDataName, Context.MODE_PRIVATE);
		String token = "";
		String token_secret = "";
		if ("tencent".equals(flag)) {
			token = settings.getString("tencent_oauth_token", null);
			token_secret = settings.getString("tencent_oauth_token_secret", null);
		} else if ("sina".equals(flag)) {
			token = settings.getString("sina_token", null);
			token_secret = settings.getString("sina_token_secret", null);
		}

		return new String[] { token, token_secret };
	}

	/**
	 * 功能：清空制定名称的保存SharePreference数值文件的内容
	 * 
	 * @param context
	 * @param spName
	 */
	public static void clear(Context context, String spName) {
		SharedPreferences settings = context.getSharedPreferences(StoreDataName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 存储和手机号对应的最近时间里申请的验证码
	 * 
	 * @param activity
	 * @param str
	 *            验证码
	 * @param phoneMgr
	 */
	public static void storePhonevalidate(Context activity, String str, String phoneMgr) {
		SharedPreferences settings = activity.getSharedPreferences(PreferenceUtil.SHARED_GAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PreferenceUtil.PHONE_VALIDATECODE + phoneMgr, str);
		editor.commit();
	}

	/**
	 * 取出和手机号对应的最近时间里申请的验证码
	 * 
	 * @param context
	 * @param phoneMgr
	 * @return
	 */
	public static String fetchPhonevalidate(Context context, String phoneMgr) {
		SharedPreferences settings = context.getSharedPreferences(PreferenceUtil.SHARED_GAME, Context.MODE_PRIVATE);
		return settings.getString(PreferenceUtil.PHONE_VALIDATECODE + phoneMgr, "");
	}

	// 保存帐号是不是 进行过新手任务================================================
	/**
	 * 存储该帐号是否取消过新手任务
	 * 
	 * @param context
	 * @param account
	 */
	public static void storeAccountIsReceive(Context context, String account) {
		SharedPreferences settings = context.getSharedPreferences(PreferenceUtil.StoreUserData, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(account + "had_receive", true).commit();
	}

	/**
	 * 完成新手任务返回是否取消过新手任务 以及新手任务是否要打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean fetchAccountIsReceive(Context context, String account) {
		SharedPreferences settings = context.getSharedPreferences(PreferenceUtil.StoreUserData, Context.MODE_PRIVATE);
		LoginDataStoreUtil.User user = LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME);
		if (1 == user.getIsCompleteNoviceTask()) {
			return true;
		} else {
			return settings.getBoolean(account + "had_receive", false);
		}
	}

	public static void setUpdateShrare(Context context, int type, int newVersion, int forceVersion, String path, String remark) {
		Editor edit = context.getSharedPreferences(SHARE_UPDATE_NAME, 0).edit();
		edit.putInt(SHARE_UPDATE_KEY_TYPE, type);
		edit.putInt(SHARE_UPDATE_KEY_NEW, newVersion);
		edit.putInt(SHARE_UPDATE_KEY_FORCE, forceVersion);
		edit.putString(SHARE_UPDATE_KEY_PATH, path);
		edit.putString(SHARE_UPDATE_KEY_REMARK, remark);
		edit.commit();
	}

	public static int getUpdateType(Context context) {
		int type = context.getSharedPreferences(SHARE_UPDATE_NAME, 0).getInt(SHARE_UPDATE_KEY_TYPE, 0);
		return type;
	}

	public static int getNewVersion(Context context) {
		int newVersion = context.getSharedPreferences(SHARE_UPDATE_NAME, 0).getInt(SHARE_UPDATE_KEY_NEW, 0);
		return newVersion;
	}

	public static int getForceVersion(Context context) {
		int forceVersion = context.getSharedPreferences(SHARE_UPDATE_NAME, 0).getInt(SHARE_UPDATE_KEY_FORCE, 0);
		return forceVersion;
	}

	public static String getUpdatePath(Context context) {
		String path = context.getSharedPreferences(SHARE_UPDATE_NAME, 0).getString(SHARE_UPDATE_KEY_PATH, "");
		return path;
	}

	/**
	 * 获取升级内容
	 * */
	public static String getUpdateRemark(Context context) {
		String remark = context.getSharedPreferences(SHARE_UPDATE_NAME, 0).getString(SHARE_UPDATE_KEY_REMARK, "");
		return remark;
	}

	public static String getLastUa(Context context) {
		SharedPreferences share = context.getSharedPreferences(PreferenceUtil.SHARE_LOG_NAME, 0);
		String ua = share.getString(PreferenceUtil.SHARE_LOG_KEY_UA, "MOT-XT800");
		return ua;
	}

	/**
	 * 是否第一次登陆社区
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isFristWeb(Context context) {
		SharedPreferences share = context.getSharedPreferences(PreferenceUtil.SHARE_LOG_NAME, 0);
		boolean result = share.getBoolean("FRIST_WEB", true);
		if (result) {
			share.edit().putBoolean("FRIST_WEB", false).commit();
		}
		return result;
	}

	/**
	 * 得到上次检查游戏升级的id
	 * 
	 * @param context
	 * @return
	 */
	public static int getUpgradeId(Context context) {
		SharedPreferences share = context.getSharedPreferences(PreferenceUtil.SHARE_LOG_NAME, 0);
		return share.getInt("UPGRADE_ID", 0);
	}

	/**
	 * 设置本次检查游戏升级的id
	 * 
	 * @param context
	 * @param upId
	 */
	public static void setUpgradeId(Context context, int upId) {
		SharedPreferences share = context.getSharedPreferences(PreferenceUtil.SHARE_LOG_NAME, 0);
		share.edit().putInt("UPGRADE_ID", upId).commit();
	}

	public static void storeCurrentPhone(Context context, String phone) {
		SharedPreferences settings = context.getSharedPreferences("current_phone_now", Context.MODE_PRIVATE);
		Editor edit = settings.edit();
		edit.putString("phone", phone);
		edit.commit();
	}

	public static String fetchCurrentPhone(Context context) {
		SharedPreferences settings = context.getSharedPreferences("current_phone_now", Context.MODE_PRIVATE);
		return settings.getString("phone", "");
	}

	/**
	 * 记录所有连续5次找回密码失败的手机号 和 邮箱
	 * 
	 * @param context
	 * @param phoneOremail
	 * @param nowTime
	 */
	public static void storeFindFailKey(Context context, String phoneOremail, int nowTime) {
		SharedPreferences settings = context.getSharedPreferences(PreferenceUtil.FAIL_KEY, Context.MODE_PRIVATE);

		Editor edit = settings.edit();
		edit.putInt("Value" + phoneOremail, nowTime);
		edit.commit();
	}

	public static void storeFindFailKstr(Context context, String phoneOremail) {
		SharedPreferences settings = context.getSharedPreferences(PreferenceUtil.FAIL_KEY, Context.MODE_PRIVATE);
		String strArray = settings.getString("Key", "");
		if (isExist(phoneOremail, strArray)) {
			if ("".equals(strArray)) {
				strArray = phoneOremail;
			} else {
				strArray = strArray + PreferenceUtil.SEG_CHAR_PHONES + phoneOremail;
			}
		}
		settings.edit().putString("Key", strArray).commit();
	}

	public static int fetchFindTime(Context context, String phoneOremail) {
		SharedPreferences settings = context.getSharedPreferences(PreferenceUtil.FAIL_KEY, Context.MODE_PRIVATE);
		String strArray = settings.getString("Key", "");
		if ("".equals(strArray)) {
			return -1;
		} else {
			boolean isHere = false;
			String[] des = strArray.split(PreferenceUtil.SEG_CHAR_PHONES);
			if (null != des && des.length > 0) {
				for (int i = 0; i < des.length; i++) {
					if (phoneOremail.equals(des[i])) {
						isHere = true;
					}
				}

				if (isHere) {
					return settings.getInt("Value" + phoneOremail, -1);
				} else {
					return -1;
				}
			} else {
				return -1;
			}

		}
	}

	/**
	 * 一次发送验证吗没有成功的时间记录
	 */
	public static void storeGetVerificateFail(Context context, String key, int time) {
		Log.i("key", key);
		SharedPreferences settings = context.getSharedPreferences("VER", Context.MODE_PRIVATE);
		String strArray = settings.getString("verificate", "");
		if (!isExist(key, strArray)) {
			if ("".equals(strArray)) {
				strArray = key;
			} else {
				strArray = strArray + PreferenceUtil.SEG_CHAR_PHONES + key;
			}
		}

		Editor edit = settings.edit();
		edit.putString("verificate", strArray);
		edit.putInt("ver" + key, time);
		edit.commit();
	}

	public static int fetchVerificateFailTime(Context context, String key) {
		Log.i("key", key);
		SharedPreferences settings = context.getSharedPreferences("VER", Context.MODE_PRIVATE);
		String strArray = settings.getString("verificate", "");
		Log.i("strArray", strArray);
		if ("".equals(strArray)) {
			// 搜索为空 说明当前要查找的 关键字不再其中
			return -1;
		} else {
			boolean isHere = false;
			String[] des = strArray.split(PreferenceUtil.SEG_CHAR_PHONES);
			Log.i("des.length", des.length + "");
			if (null != des && des.length > 0) {
				for (int i = 0; i < des.length; i++) {
					if (key.equals(des[i])) {
						isHere = true;
						Log.i("des[i]", des[i] + "");
					}
				}
				if (isHere) {
					Log.i("fetch", "true");
					return settings.getInt("ver" + key, -1);
				} else {
					return -1;// 搜索空间为空
				}
			} else {
				return -1;// 搜索空间为空
			}

		}
	}

	public static boolean isExist(String key, String desStr) {
		String[] des;
		if (null != desStr && !"".equals(desStr)) {
			des = desStr.split(PreferenceUtil.SEG_CHAR_PHONES);
			if (null != des && des.length >= 1) {
				for (int i = 0; i < des.length; i++) {
					if (key.equals(des[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void storeFindPasswordTime(Context context, String key, int time) {
		Log.i("key", key + "//" + time);
		SharedPreferences settings = context.getSharedPreferences("FIND", Context.MODE_PRIVATE);
		String strArray = settings.getString("find_array", "");
		// if (isExist(key, strArray)) {
		if ("".equals(strArray)) {
			strArray = key;
		} else {
			strArray = strArray + PreferenceUtil.SEG_CHAR_PHONES + key;
		}
		// }
		Editor edit = settings.edit();
		edit.putString("find_array", strArray);
		edit.putInt("find" + key, time);
		Log.i("storeFindPasswordTime", time + "");
		edit.commit();
	}

	public static int fetchFindPasswordTime(Context context, String key) {
		Log.i("key", key);
		SharedPreferences settings = context.getSharedPreferences("FIND", Context.MODE_PRIVATE);
		String strArray = settings.getString("find_array", "");
		Log.i("strArray", strArray);
		if ("".equals(strArray)) {
			// 搜索为空 说明当前要查找的 关键字不再其中
			return -1;
		} else {
			boolean isHere = false;
			String[] des = strArray.split(PreferenceUtil.SEG_CHAR_PHONES);
			Log.i("des.length", des.length + "");
			if (null != des && des.length > 0) {
				for (int i = 0; i < des.length; i++) {
					if (key.equals(des[i])) {
						isHere = true;
						Log.i("des[i]", des[i] + "");
					}
				}
				if (isHere) {
					Log.i("fetch", "true");
					Log.i("storeFindPasswordTime", settings.getInt("find" + key, -1) + "");
					return settings.getInt("find" + key, -1);

				} else {
					return -1;// 搜索空间为空
				}
			} else {
				return -1;// 搜索空间为空
			}

		}
	}

	// 是否展示过下载得经验的popupwindow
	public static boolean isShowDownExpPop(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowDownExpPop", true);
	}

	public static void setShowDownExpPop(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowDownExpPop", false).commit();
	}

	// 是否展示过下载得经验的dialog
	public static boolean isShowDownExpDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowDownExpDialog", true);
	}

	public static void setShowDownExpDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowDownExpDialog", false).commit();
	}

	// 是否展示过分享得经验的popup
	public static boolean isShowShareExpPop(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowShareExpPop", true);
	}

	public static void setShowShareExpPop(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowShareExpPop", false).commit();
	}

	// 是否展示过分享得经验的dialog
	public static boolean isShowShareExpDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowShareExpDialog", true);
	}

	public static void setShowShareExpDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowShareExpDialog", false).commit();
	}

	// 是否展示过推荐得经验的popup
	public static boolean isShowRecoExpPop(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowRecoExpPop", true);
	}

	public static void setShowRecoExpPop(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowRecoExpPop", false).commit();
	}

	// 是否展示过推荐得经验的dialog
	public static boolean isShowRecoExpDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowRecoExpDialog", true);
	}

	public static void setShowRecoExpDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowRecoExpDialog", false).commit();
	}
	
	// 是否展示推帮顶手机号的dialog
	public static boolean isShowBindPhoneDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("isShowBindPhoneDialog", true);
	}

	public static void setShowBindPhoneDialog(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("isShowBindPhoneDialog", false).commit();
	}

	/**
	 * 保存下载到rom的游戏信息
	 * 
	 * @param gameInfo
	 * @author zhouzhou@lenovo-cw.com
	 * @time 2012-6-20
	 */
	public static void saveRomDownGame(Context context, GameInfo gameInfo, String packageName) {
		SharedPreferences share = context.getSharedPreferences("romdown", 0);
		Editor edit = share.edit();
		edit.putString("gameId", gameInfo.getGameId());
		edit.putString("cpid", gameInfo.getCpId());
		edit.putString("cpcode", gameInfo.getCpCode());
		edit.putString("servicecode", gameInfo.getServiceCode());
		edit.putString("gamename", gameInfo.getGameName());
		edit.putString("channelcode", gameInfo.getChannelCode());
		edit.putString("classname", gameInfo.getClassName());
		edit.putString("filesize", gameInfo.getFileSize() * 1024 + "");
		edit.putString("iconurl", gameInfo.getIconurl());
		edit.putString("packagename", packageName);
		edit.commit();
	}

}
