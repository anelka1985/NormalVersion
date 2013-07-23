package com.egame.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

public class NoviceBean extends IconBeanImpl {
	/**
	 * 用户头像图片地址
	 */
	private String mUserPicUrl;
	/**
	 * 用户名称
	 */
	private String mUserName;
	/**
	 * 用户的性别和年龄
	 */
	private String mUserGexOld;
	/**
	 * 用户的住址和爱玩游戏类型
	 */
	private String mAddressType;
	/**
	 * 用户的Id
	 */
	private long userId;

	private int grend;

	private String birthDay;

	private int age;
	private boolean isAddSuccess = false;
	private int mNumber = -1;

	public NoviceBean(JSONObject obj, int index) {
		super(obj.optString("icon"));
		this.setUserId(obj.optLong("userId"));
		this.mUserName = obj.optString("userName");
//		L.d("NoviceBean","姓名:" + mUserName);
		this.mUserPicUrl = obj.optString("icon");
		this.birthDay = obj.optString("birthday", "");
		if ("".equals(obj.optString("province", "")) || "".equals(obj.optString("city"))) {
			this.mAddressType = obj.optString("province") + obj.optString("city") + "   " + obj.optString("hobby", "");
		} else {
			this.mAddressType = obj.optString("province") + "-" + obj.optString("city") + "   " + obj.optString("hobby", "");
		}

		this.grend = obj.optInt("gender");
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		if (!"".equals(obj.optString("birthday", ""))) {
			Date mydate;
			try {
				mydate = myFormatter.parse(obj.optString("birthday", ""));
				this.age = getAge(mydate);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		this.mUserGexOld = this.grend == 1 ? ("男-" + this.age) : ("女-" + this.age);
		this.isAddSuccess = false;
		if (index == 0) {
			setmNumber(obj.optInt("totalRecored", -1));
		}
	}

	/**
	 * @return the mUserPicUrl
	 */
	public String getmUserPicUrl() {
		return mUserPicUrl;
	}

	/**
	 * @param mUserPicUrl
	 *            the mUserPicUrl to set
	 */
	public void setmUserPicUrl(String mUserPicUrl) {
		this.mUserPicUrl = mUserPicUrl;
	}

	/**
	 * @return the mUserName
	 */
	public String getmUserName() {
		return mUserName;
	}

	/**
	 * @param mUserName
	 *            the mUserName to set
	 */
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	/**
	 * @return the mUserGexOld
	 */
	public String getmUserGexOld() {
		return mUserGexOld;
	}

	/**
	 * @param mUserGexOld
	 *            the mUserGexOld to set
	 */
	public void setmUserGexOld(String mUserGexOld) {
		this.mUserGexOld = mUserGexOld;
	}

	/**
	 * @return the mAddressType
	 */
	public String getmAddressType() {
		return mAddressType;
	}

	/**
	 * @param mAddressType
	 *            the mAddressType to set
	 */
	public void setmAddressType(String mAddressType) {
		this.mAddressType = mAddressType;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the grend
	 */
	public int getGrend() {
		return grend;
	}

	/**
	 * @param grend
	 *            the grend to set
	 */
	public void setGrend(int grend) {
		this.grend = grend;
	}

	/**
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay
	 *            the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}

	/**
	 * @return the isAddSuccess
	 */
	public boolean isAddSuccess() {
		return isAddSuccess;
	}

	/**
	 * @param isAddSuccess
	 *            the isAddSuccess to set
	 */
	public void setAddSuccess(boolean isAddSuccess) {
		this.isAddSuccess = isAddSuccess;
	}

	/**
	 * @return the mNumber
	 */
	public int getmNumber() {
		return mNumber;
	}

	/**
	 * @param mNumber
	 *            the mNumber to set
	 */
	public void setmNumber(int mNumber) {
		this.mNumber = mNumber;
	}

}
