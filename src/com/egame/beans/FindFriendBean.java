/**
 * 
 */
package com.egame.beans;
import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * 描述：新手任务找朋友 实体bean
 * 
 * @author LiuHan
 * 
 */
public class FindFriendBean extends IconBeanImpl {
	/** 用户名 */
	private String userName;
	/** 来自那里 */
	private String reason;
	/** 用户头像路径 */
	private String iconPath;
	/** 用户id */
	private String userId;
	/** 在线状态 */
	private String onlineStatus;
	private String accessTime;

	/**
	 * @param obj
	 */
	public FindFriendBean(JSONObject obj) {
		super(obj.optString("icon"));
		this.userName = obj.optString("userName");
		this.accessTime=obj.optString("accessTime","");
		this.iconPath=obj.optString("icon");
		this.onlineStatus=obj.optString("onlineStatus");
		this.reason = obj.optString("reason");
	}
	
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
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
	 * @return the onlineStatus
	 */
	public String getOnlineStatus() {
		return onlineStatus;
	}

	/**
	 * @param onlineStatus
	 *            the onlineStatus to set
	 */
	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	/**
	 * @return the accessTime
	 */
	public String getAccessTime() {
		return accessTime;
	}

	/**
	 * @param accessTime
	 *            the accessTime to set
	 */
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath the iconPath to set
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
