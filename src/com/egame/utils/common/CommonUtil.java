package com.egame.utils.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;

import com.egame.R;
import com.egame.app.services.DBService;
import com.egame.config.Const;
import com.egame.utils.ui.ToastUtil;

/**
 * 一些常用的方法
 * 
 * @author zhouzhe
 * 
 */
public class CommonUtil {
	/**
	 * 是否能使用包内游戏
	 * 
	 * @param orderState
	 *            0:没订购 1:已订购 2:订购已退订还能使用 -1:没取到手机号
	 * @return
	 */
	public static boolean canPlayPackage(String orderState) {
		if ("1".equals(orderState) || "2".equals(orderState)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否能订购包
	 * 
	 * @param orderState
	 *            0:没订购 1:已订购 2:订购已退订还能使用 -1:没取到手机号
	 * @return
	 */
	public static boolean canOrderPackage(int orderState) {
		if (orderState == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否已安装
	 * 
	 * @param serviceid
	 * @return
	 */
	public static boolean isInstall(String serviceid) {
		return false;
	}

	/**
	 * 字符串转码URLDecoder
	 * 
	 * @param str
	 * @return
	 */
	public static String getURLDecoder(String str) {
		String newStr = "";

		if (null == str || "".equals(str)) {
			return str;
		}
		try {
			newStr = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}

		return newStr;
	}

	/**
	 * PHP时间转JAVA时间
	 * 
	 * @param date
	 * @return
	 */
	public static String phpToJava(String date) {
		if ("null".equals(date)) {
			return "";
		} else {
			String newDate;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (date.length() <= 10) {
				newDate = date + "000";
			} else {
				newDate = date;
			}
			String dateTime = df.format(Long.valueOf(newDate));
			return dateTime;
		}
	}

	/**
	 * 获取用户满意度
	 * 
	 * @param which
	 * @return
	 */
	public static String getSatisfaction(int which) {
		String satisfactio = null;
		switch (which) {
		case 0:
			satisfactio = "1";
			break;

		case 1:
			satisfactio = "2";
			break;
		case 2:
			satisfactio = "3";
			break;
		case 3:
			satisfactio = "4";
			break;
		}

		return satisfactio;
	}

	/**
	 * 根据标识获得具体的满意度描述
	 * 
	 * @param flag
	 * @return
	 */
	public static String getSatisfactionDetail(Context ctx, String flag) {
		String satisfactio = null;
		switch (Integer.parseInt(flag)) {
		case 1:
			satisfactio = ctx.getResources().getString(R.string.egame_fcmy);
			break;

		case 2:
			satisfactio = ctx.getResources().getString(R.string.egame_my);
			break;
		case 3:
			satisfactio = ctx.getResources().getString(R.string.egame_yb);
			break;
		case 4:
			satisfactio = ctx.getResources().getString(R.string.egame_bmy);
			break;
		}

		return satisfactio;
	}

	public static boolean getBoolean(int i) {
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 安装游戏函数
	 */
	public static void installGames(String serviceid, Activity context) {
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		DBService dbService = new DBService(context);
		dbService.open();
		// sd卡存在
		if (sdCardExist) {
			// 安装游戏
			String path = Const.DIRECTORY + "/" + serviceid + ".apk";
			String apkName = "";
			// 获取APK包名
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
			if (info != null) {
				apkName = info.packageName;
				// 更新记录中的包名
				L.d("install", "info1" + apkName);
				dbService.updateData(serviceid + "", DBService.KEY_PACKAGENAME, apkName);

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
				intent.putExtra("serviceid", serviceid);
				context.startActivityForResult(intent, 1);
			} else {
				// 解析包错误,需要重新下载
				L.d("install", "info is null");
				ToastUtil.show(context, "解析包错误,请重新下载");
				dbService.updateData(serviceid + "", DBService.KEY_STATE, DBService.DOWNSTATE_BREAK);
				dbService.updateDownSize(serviceid + "", 0 + "");
				sendChangeBroadCast(context);
				// notificationManager.cancel(Integer.parseInt(serviceid));
			}
		} else {
			// sd卡不存在
			ToastUtil.show(context, "sd卡不存在,无法读取已下载文件,请检查sd卡");
		}
		dbService.close();
	}
	
	/**
	 * 无SD卡下载安装
	 */
	public static void installGamesNoSdcard(String path,Activity context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
		context.startActivityForResult(intent, 1);
	}

	public static void sendBroadCast(String msg, Context context) {
		Intent intent = new Intent("com.egame.app.uis.GameDownloadMissionActivity");
		intent.putExtra("msg", msg);
		context.sendBroadcast(intent);
	}

	/**
	 * 进度发生变化时刷新
	 * 
	 * @param context
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-2-1
	 */
	public static void sendRefreshBroadCast(Context context) {
		sendBroadCast("refresh", context);
	}

	/**
	 * 下载状态发生变化时刷新
	 * 
	 * @param context
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-2-1
	 */
	public static void sendChangeBroadCast(Context context) {
		sendBroadCast("change", context);
	}

	public static int getVersionCode(Context context, String packageName) throws NameNotFoundException {
		PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
		return info.versionCode;
	}
	
	/**
	 * 获取userid
	 * 
	 * @param context
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-7-3
	 */
	public static String getUserId(Context context){
		return LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME).getUserId();
	}
}
