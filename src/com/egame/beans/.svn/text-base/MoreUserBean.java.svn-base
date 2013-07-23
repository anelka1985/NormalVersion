package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 还有谁在玩 实体类
 * @Author yangky
 */
public class MoreUserBean extends IconBeanImpl {
	private String userId;
	private String userName;

	public MoreUserBean(JSONObject obj) {
		super(obj.optString("icon"));
		this.userId = obj.optString("userId");
		this.userName = obj.optString("userName");
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
