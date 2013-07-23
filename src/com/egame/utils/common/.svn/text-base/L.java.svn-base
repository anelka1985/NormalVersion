package com.egame.utils.common;

import android.util.Log;

/**
 * 日志工具类 简化日志输出,控制发布版本的日志输出
 * 
 * @author zhouzhe@lenovo-cw.com
 * 
 */
public class L {

	/**
	 * 默认标签
	 */
	public static String DEFAULT_TAG = "DEBUG";

	/**
	 * 是否开启debug,不开启debug则不输出L.d()信息
	 */
	public static boolean DEBUG = false;

	/**
	 * 是否开启info,不开启info则不输出L.i()信息
	 */
	public static boolean INFO = false;

	/**
	 * 输出debug信息
	 * 
	 * @param key
	 * @param value
	 */
	public static void d(String key, String value) {
		if (DEBUG) {
			Log.d(key, value);
		}
	}

	/**
	 * 用默认tag输出debug信息
	 * 
	 * @param value
	 */
	public static void d(String value) {
		if (DEBUG) {
			Log.d(DEFAULT_TAG, value);
		}
	}

	/**
	 * 输出info信息
	 * 
	 * @param key
	 * @param value
	 */
	public static void i(String key, String value) {
		if (INFO) {
			Log.v(key, value);
		}
	}

	/**
	 * 用默认tag输出info信息
	 * 
	 * @param value
	 */
	public static void i(String value) {
		if (INFO) {
			Log.v(DEFAULT_TAG, value);
		}
	}

}
