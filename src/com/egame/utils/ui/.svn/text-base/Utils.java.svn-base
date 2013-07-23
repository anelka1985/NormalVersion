package com.egame.utils.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-23 下午04:44:56
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class Utils {
	public static final String RECEIVER_CHANGE_SEL_TAB = "com.egame.app.CHANGE_SEL_TAB";
	public static final String KEY_TAB_NAME = "tabName";
	public static final String RECEIVER_SEARCH_KEY = "com.egame.app.SEARCH_KEY";
	public static final String RECEIVER_DOWNLOAD = "com.egame.app.DOWNLOAD";
	public static final String RECEIVER_START_BACKRUN = "com.egame.app.START_BACKRUN";
	public static final String RECEIVER_UPGRADE = "com.egame.app.UPGRADE";
	public static final String RECEIVER_DOWNTASK = "com.egame.app.DOWNTASK";
	public static final String KEY_SEARCH_KEY = "searchKey";
	public static final String KEY_SEARCH_TYPE = "searchType";
	public static final String EGAME_WEB_RECEIVER = "com.egame.web.receiver";
	public static final String RECEIVER_MESSAGE = "com.egame.app.MESSAGE";

	public static String getVga(Context context) {
		String vga = "480*800";
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int maxY = (int) (dm.heightPixels);
		int maxX = (int) (dm.widthPixels);
		if (maxY > 1000) {
			if (maxX < 750) {
				vga = "720*1280";
			} else {
				vga = "800*1280";
			}

		} else if (maxY > 810 && maxY < 871) {
			vga = "480*854";
		} else if (maxY > 870) {
			if (maxX > 485) {
				vga = "540*960";
			} else {
				vga = "480*960";
			}
		} else if (maxY < 490 && maxY > 460) {
			vga = "320*480";
		} else if (maxY < 360 && maxY > 300) {
			vga = "240*320";
		} else if (maxY < 420 && maxY > 380) {
			vga = "240*400";
		}
		return vga;
	}

	/**
	 * 是否有手机卡
	 */
	public static boolean existSIMCard(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (null != telManager.getSubscriberId()
				&& !"".equals(telManager.getSubscriberId() + "")) {
			Log.i(Utils.class.getCanonicalName(), "sim card exist");
			return true;
		} else {
			Log.i(Utils.class.getCanonicalName(), "sim card no exist");
			return false;
		}
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 取得指定时间的星座
	 * 
	 * @param dayOfMonth
	 *            日
	 * @param i
	 *            月
	 */
	public static String getConstellation(int month, int day) {
		String mConstellation = "";
		if (month == 1 && day >= 20 || month == 2 && day <= 18) {
			mConstellation = "水瓶座";
		}

		if (month == 2 && day >= 19 || month == 3 && day <= 20) {
			mConstellation = "双鱼座";
		}

		if (month == 3 && day >= 21 || month == 4 && day <= 19) {
			mConstellation = "白羊座";
		}

		if (month == 4 && day >= 20 || month == 5 && day <= 20) {
			mConstellation = "金牛座";
		}

		if (month == 5 && day >= 21 || month == 6 && day <= 21) {
			mConstellation = "双子座";
		}

		if (month == 6 && day >= 22 || month == 7 && day <= 22) {
			mConstellation = "巨蟹座";
		}

		if (month == 7 && day >= 23 || month == 8 && day <= 22) {
			mConstellation = "狮子座";
		}

		if (month == 8 && day >= 23 || month == 9 && day <= 22) {
			mConstellation = "处女座";
		}

		if (month == 9 && day >= 23 || month == 10 && day <= 22) {
			mConstellation = "天秤座";
		}

		if (month == 10 && day >= 23 || month == 11 && day <= 21) {
			mConstellation = "天蝎座";
		}

		if (month == 11 && day >= 22 || month == 12 && day <= 21) {
			mConstellation = "射手座";
		}

		if (month == 12 && day >= 22 || month == 1 && day <= 19) {
			mConstellation = "摩羯座";
		}

		return mConstellation;

	}

	/** 录入省会信息 */
	public static Map<String, String> addCapital() {
		Map<String, String> mCapital = new HashMap<String, String>();
		mCapital.put("福建", "福州");
		mCapital.put("江西", "南昌");
		mCapital.put("浙江", "杭州");
		mCapital.put("内蒙古", "呼和浩特");
		mCapital.put("安徽", "合肥");
		mCapital.put("河北", "石家庄");
		mCapital.put("山西", "太原");
		mCapital.put("北京", "北京");
		mCapital.put("山东", "济南");
		mCapital.put("天津", "天津");
		mCapital.put("辽宁", "沈阳");
		mCapital.put("湖南", "长沙");
		mCapital.put("湖北", "武汉");
		mCapital.put("河南", "郑州");
		mCapital.put("宁夏", "银川");
		mCapital.put("新疆", "乌鲁木齐");
		mCapital.put("甘肃", "兰州");
		mCapital.put("青海", "西宁");
		mCapital.put("陕西", "西安");
		mCapital.put("广西", "南宁");
		mCapital.put("广东", "广州");
		mCapital.put("海南", "海口");
		mCapital.put("吉林", "长春");
		mCapital.put("黑龙江", "哈尔滨");
		mCapital.put("江苏", "南京");
		mCapital.put("上海", "上海");
		mCapital.put("四川", "成都");
		mCapital.put("贵州", "贵阳");
		mCapital.put("云南", "昆明");
		mCapital.put("西藏", "拉萨");
		mCapital.put("重庆", "重庆");
		return mCapital;
	}

	/**
	 * 取得用户的年龄
	 */
	public static int getAge(String str) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		java.util.Date mydate;
		try {
			mydate = myFormatter.parse(str);
			long day = (date.getTime() - mydate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = new java.text.DecimalFormat("#.00")
					.format(day / 365f);
			return "".equals(year) ? 25 : Integer.parseInt(year.substring(0,
					year.indexOf(".")));
		} catch (ParseException e) {
			e.printStackTrace();
			return 25;
		}
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param s
	 *            需要得到长度的字符串
	 * @return i得到的字符串长度
	 */
	public static int strlength(String s) {
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

	public static boolean isNickname(String nickname) {
		if (strlength(nickname) < 4) {
			return false;
		} else if (strlength(nickname) > 16) {
			return false;
		}
		String all = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,16}$";
		Pattern pattern = Pattern.compile(all);
		return pattern.matcher(nickname).matches();
	}

	/**
	 * 检查EMAIL地址 用户名和网站名称必须>=1位字符
	 * 地址结尾必须是以com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn结尾
	 * */
	public static boolean checkEmailWithSuffix(String email) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean checkUsername(String username) {
		// String reg = "^[a-zA-Z][0-9A-Za-z_]{6,25}";有问题,首字母+后面的6-25 结果是7-26位
		String reg = "[0-9A-Za-z_]{5,24}";
		Pattern pattern = Pattern.compile(reg);
		Pattern emailPattern = Pattern
				.compile("(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}");
		if (pattern.matcher(username).matches()
				|| emailPattern.matcher(username).matches())
			return true;
		return false;
	}

}