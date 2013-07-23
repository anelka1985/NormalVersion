/**   
 * @Title: BroswerUtil.java
 * @Package com.lenovocw.android
 * @Description: 浏览器工具类
 * @author zhouzhe@lenovo-cw.com   
 * @date 2011-8-17 下午07:02:17
 * @version V1.0   
 */


package com.egame.utils.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.egame.config.Urls;

/**
 * @ClassName: BroswerUtil
 * @Description: 浏览器工具类
 * @author zhouzhe@lenovo-cw.com
 * @date 2011-8-17 下午07:02:17
 */

public class BroswerUtil {
	public static void startIeDefault(String url, Context context) {
		Uri uri = addParams(url, context);
		Intent it=new Intent();
		it.setAction("android.intent.action.VIEW");
		it.setData(uri);
		it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
		context.startActivity(it);
	}
	
	public static Uri addParams(String url, Context context) {
		if(url.indexOf("?") == -1){
			return Uri.parse(url + "?" + Urls.getLogParams(context));
		}else{
			return Uri.parse(url + "&" + Urls.getLogParams(context));
		}
	}
}
