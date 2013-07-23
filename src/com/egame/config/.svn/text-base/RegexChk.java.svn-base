package com.egame.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类说明：
 * 
 * @创建时间 2011-6-21 下午02:23:35
 * @创建人： 陆林
 * @邮箱：15301586841@189.cn
 */
public class RegexChk {
	private static boolean startCheck(String reg, String string) {
		boolean tem = false;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);
		tem = matcher.matches();
		return tem;
	}

	public static boolean checkUsername(String username) {
		// String reg = "^[a-zA-Z][0-9A-Za-z_]{6,25}";有问题,首字母+后面的6-25 结果是7-26位
		String reg = "^[a-zA-Z][0-9A-Za-z_]{5,24}";
		return startCheck(reg, username);
	}

	public static boolean checkNickname(String nickname) {
		String reg = "[\u4E00-\u9FA5]{2,8}";
		return startCheck(reg, nickname);
	}

	/**
	 * 密码验证，6-25位，数字字母组成
	 * 
	 * @param pw
	 * @return
	 */
	public static boolean checkPw(String pw) {
		String reg = "[0-9A-Za-z]{6,25}";
		return startCheck(reg, pw);
	}

	/**
	 * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
	 * */
	public static boolean checkCellPhone(String cellPhoneNr) {
		String reg = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		return startCheck(reg, cellPhoneNr);
	}

	/**
	 * 检查EMAIL地址 用户名和网站名称必须>=1位字符
	 * 地址结尾必须是以com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn结尾
	 * */
	public static boolean checkEmailWithSuffix(String email) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return startCheck(regex, email);
	}

	/**
	 * 公司合法性验证
	 */
	public static boolean checkCompany(String company) {
		String str = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,20}$";
		return startCheck(str, company);
	}

	public static boolean checkPlatitude(String des) {
		String str = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,40}$";
		return startCheck(str, des);
	}

	/**
	 * 1-昵称，2-8个汉字或者4-16个字符 2-手机号码 3-邮箱 4-公司名称，20个汉字或者40个字符 5-签名，60个汉字或者120个字符
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static boolean checkInput(String type, String value) {
		if ("1".equals(type)) {
			if (strlength(value) < 4) {
				return false;
			} else if (strlength(value) > 16) {
				return false;
			}
			String all = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,16}$";
			Pattern pattern = Pattern.compile(all);
			return pattern.matcher(value).matches();
		} else if ("2".equals(type)) {
			return checkCellPhone(value);
		} else if ("3".equals(type)) {
			return checkEmailWithSuffix(value);
		} else if ("4".equals(type)) {

			return checkCompany(value);
		} else if ("5".equals(type)) {
			return checkPlatitude(value);

		}
		return true;
	}

	private static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	private static int strlength(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}
}
