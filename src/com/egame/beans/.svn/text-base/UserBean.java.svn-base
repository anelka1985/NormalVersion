package com.egame.beans;

import org.json.JSONObject;

/**
 * 描述：存储用户信息的实体类
 * 
 * @author LiuHan
 * @version 1.0 create on:Mon 27 Feb,2012
 */
public class UserBean {
	/** 信息完善度 */
	private String complete;
	private String resultmsg;
	private int resultcode;
	// ====用户信息实体=======
	/** 用户名 */
	private String userName;
	/** 图片路径 */
	private String iconPath;
	/** 用户ID */
	private String userId;
	/** 用户省份 */
	private String province;
	/** 用户所在的城市 */
	private String city;
	/** 用户的性别 */
	private int gender;
	/** 用户在线状态 */
	private int onlineStatus;
	/** 用户所在的公司 */
	private String company;
	/***/
	private int score;
	/** 用户的邮箱 */
	private String email;
	/** 用户的生日 */
	private String birthday;
	/** 用户的心情 */
	private String platitude;
	/** 用户的爱豆数 */
	private int aidouNum;
	/** 用户的年龄 */
	private int age;
	/** 用户的爱好 */
	private String hobby;
	/** 用户的等级 */
	private int lvl;
	/** 用户的学校 */
	private String school;
	private int pk;
	private String exp;
	/** 工具 */
	private String tools;
	/** 用户的消息数量 */
	private int msgNum;
	/** 用户的手机号 */
	private String phone;
	/** 用户手机号是否已验证 */
	private int phoneCheck;
	/** 用户邮箱是否已验证 */
	private int emailCheck;
	private String passWord;
	private String accountStatus;

	/**
	 * 构造函数
	 */
	public UserBean(JSONObject obj) {
		this.age = obj.optInt("age", 0);
		this.aidouNum = obj.optInt("aidouNum", 0);
		this.birthday = obj.optString("birthday", "1980-01-01");
		if (null == this.birthday || "".equals(this.birthday) || !this.birthday.contains("-")) {
			this.birthday = "1980-01-01";
		}
		this.city = obj.optString("city", "");
		this.company = obj.optString("company", "");
		this.email = obj.optString("email", "");
		this.emailCheck = obj.optInt("emailCheck", 0);
		this.exp = obj.optString("exp", "0");
		this.gender = obj.optInt("gender", 0);
		this.hobby = obj.optString("hobby", "");
		this.iconPath = obj.optString("icon", "");
		this.lvl = obj.optInt("lvl", 1);
		this.msgNum = obj.optInt("msgNum", 0);
		this.onlineStatus = obj.optInt("onlineStatus", 0);
		this.phone = obj.optString("phone", "");
		this.phoneCheck = obj.optInt("phoneCheck", 0);
		this.pk = obj.optInt("pk", 0);
		this.platitude = obj.optString("platitude", "");
		this.province = obj.optString("province", "");
		this.school = obj.optString("school", "");
		this.score = obj.optInt("score", 0);
		this.tools = obj.optString("tools", "");
		this.userId = obj.optString("userId", "");
		this.userName = obj.optString("userName", "");
		this.accountStatus = obj.optString("accountStatus", "");
	}

	/**
	 * @return the complete
	 */
	public String getComplete() {
		return complete;
	}

	/**
	 * @param complete
	 *            the complete to set
	 */
	public void setComplete(String complete) {
		this.complete = complete;
	}

	/**
	 * @return the resultmsg
	 */
	public String getResultmsg() {
		return resultmsg;
	}

	/**
	 * @param resultmsg
	 *            the resultmsg to set
	 */
	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}

	/**
	 * @return the resultcode
	 */
	public int getResultcode() {
		return resultcode;
	}

	/**
	 * @param resultcode
	 *            the resultcode to set
	 */
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath
	 *            the iconPath to set
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
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
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @return the onlineStatus
	 */
	public int getOnlineStatus() {
		return onlineStatus;
	}

	/**
	 * @param onlineStatus
	 *            the onlineStatus to set
	 */
	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the platitude
	 */
	public String getPlatitude() {
		return platitude;
	}

	/**
	 * @param platitude
	 *            the platitude to set
	 */
	public void setPlatitude(String platitude) {
		this.platitude = platitude;
	}

	/**
	 * @return the aidouNum
	 */
	public int getAidouNum() {
		return aidouNum;
	}

	/**
	 * @param aidouNum
	 *            the aidouNum to set
	 */
	public void setAidouNum(int aidouNum) {
		this.aidouNum = aidouNum;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the lvl
	 */
	public int getLvl() {
		return lvl;
	}

	/**
	 * @param lvl
	 *            the lvl to set
	 */
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	/**
	 * @return the hobby
	 */
	public String getHobby() {
		return hobby;
	}

	/**
	 * @param hobby
	 *            the hobby to set
	 */
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school
	 *            the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the pk
	 */
	public int getPk() {
		return pk;
	}

	/**
	 * @param pk
	 *            the pk to set
	 */
	public void setPk(int pk) {
		this.pk = pk;
	}

	/**
	 * @return the exp
	 */
	public String getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}

	/**
	 * @return the tools
	 */
	public String getTools() {
		return tools;
	}

	/**
	 * @param tools
	 *            the tools to set
	 */
	public void setTools(String tools) {
		this.tools = tools;
	}

	/**
	 * @return the msgNum
	 */
	public int getMsgNum() {
		return msgNum;
	}

	/**
	 * @param msgNum
	 *            the msgNum to set
	 */
	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
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

	/**
	 * @return the emailCheck
	 */
	public int getEmailCheck() {
		return emailCheck;
	}

	/**
	 * @param emailCheck
	 *            the emailCheck to set
	 */
	public void setEmailCheck(int emailCheck) {
		this.emailCheck = emailCheck;
	}

	/**
	 * @return the phoneCheck
	 */
	public int getPhoneCheck() {
		return phoneCheck;
	}

	/**
	 * @param phoneCheck
	 *            the phoneCheck to set
	 */
	public void setPhoneCheck(int phoneCheck) {
		this.phoneCheck = phoneCheck;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord
	 *            the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

}
