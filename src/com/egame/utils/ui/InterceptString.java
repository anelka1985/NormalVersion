package com.egame.utils.ui;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.utils.URLEncodedUtils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

/**
 * @author JaffersonLiu 截取指定长度的任意字符串
 * @date:2011/6/30
 */
public class InterceptString {
	/**
	 * @param string
	 * @param length
	 * @return
	 */
	public static String newSubString(String string, int length) {
		if (string == null) {
			return null;
		}
		if (string.length() == 0) {
			return string;
		}
		if (length < 0) {
			throw new IllegalArgumentException("要截取的长度不能小于零");
		}
		int i = 0;
		int len = 0;
		char[] chs = string.toCharArray();
		while ((len < length) && (i < chs.length)) {
			len = (chs[i++] > 0xff) ? (len + 2) : (len + 1);
		}
		if (len > length) {
			i--;
		}
		return new String(chs, 0, i);
	}

	// 取得给定的字符串中中文的个数
	public static int chineseNums(String str) {
		byte b[] = str.getBytes();
		int byteLength = b.length;
		int strLength = str.length();
		return (byteLength - strLength) / 2;
	}

	/**
	 * 高亮显示匹配的字符串
	 * 
	 * @param str1
	 *            //待被批配的字符串
	 * @param str2
	 *            //匹配关键字字符串 割绳子-实验室版（Cut the Rope Experiments)
	 * @return
	 */
	public static SpannableString getSpannableString(String str1, String str2) {
		// 取得SpannableString对象
		
		SpannableString s = new SpannableString(str1);
		Pattern p = Pattern.compile(URLEncoder.encode(str2));
		Matcher m = p.matcher(s);

		while (m.find()) {
			int start = m.start();
			int end = m.end();
			s.setSpan(new ForegroundColorSpan(Color.RED), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return s;
	}

	// 过滤特殊字符
	public static String stringFilter(String str) {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?_~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

}
