/**
 * 
 */
package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @author LiuHan
 * 
 */
public class ShareFriendBean extends IconBeanImpl {
	/** 用户id */
	private long userId;
	/** 用户名 */
	private String userName;
	/** 在线状态 */
	private int onlineStatus;

	private boolean isSelect = false;

	private int grender = 1;
	/** 好友数目 只会在第一次请求成功后初始化 */
	private int totalNum = -1;

	public ShareFriendBean(JSONObject json1,JSONObject json, int page) {
		super(json.optString("icon"));
		this.userId = json.optLong("userId", 0L);
		this.onlineStatus = json.optInt("onlineStatus", 0);
		this.userName = json.optString("userName", "暂无昵称");
		this.isSelect = false;
		this.setGrender(json.optInt("gender", 1));
		if (page == 0) {
			setTotalNum(json1.optInt("totalRecored", -1));
		}
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
	 * @return the isSelect
	 */
	public boolean isSelect() {
		return isSelect;
	}

	/**
	 * @param isSelect
	 *            the isSelect to set
	 */
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	/**
	 * @return the grender
	 */
	public int getGrender() {
		return grender;
	}

	/**
	 * @param grender
	 *            the grender to set
	 */
	public void setGrender(int grender) {
		this.grender = grender;
	}

	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
