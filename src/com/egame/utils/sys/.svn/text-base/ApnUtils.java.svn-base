package com.egame.utils.sys;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;

import com.egame.utils.common.L;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-30
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ApnUtils {

	static Uri uri = Uri.parse("content://telephony/carriers/");

	static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
	public static final String APN_ID = "apn_id";

	static List<APN> apnList;

	public static String USER = "ctwap@mycdma.cn";
	public static String PASS = "vnet.mobi";

	static String CONDITION = "user = ?" + " and password = ?";
	static String VALUES[] = { USER, PASS };

	static String oldKey = "";

	public static boolean wifiEnabled = false;

	public static class APN {
		String id;
		String user;
		String password;
		String apn;
		String type;
	}

	public static boolean isValidApn(Context context) {
		L.d("APN", "check isValidApn");
		boolean result = false;
		Cursor cr = null;
		try {
			cr = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
			if (cr.getCount() <= 0) {
				result = false;
			} else {
				cr.moveToFirst();
				String user = cr.getString(cr.getColumnIndex("user"));
				String pass = cr.getString(cr.getColumnIndex("password"));
				if (user.compareTo(USER) == 0 && pass.compareTo(PASS) == 0) {
					result = true;
				} else {
					result = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cr != null) {
				cr.close();
			}
		}
		return result;
	}

	// 判断是否为ctwap
	public static boolean isCtwap(Context ctx) {
		WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		boolean result = isValidApn(ctx) && wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED
				&& wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLING;
		L.d("APN是", "apn:" + result);
		return result;
	}

	/** 判断网络连接是否通畅 */
	public static boolean checkNetWorkStatus(Context context) {
		boolean result;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			L.d("APN", "net is null");
			return true;
		}
		if (netinfo != null && netinfo.isAvailable()) {
			L.i("APN", "The net was connected");
			result = true;
		} else {
			L.i("APN", "The net was bad!");
			result = false;
		}
		return true;
	}
}
