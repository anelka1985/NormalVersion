package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc	大家还喜欢的实体类
 * 	
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-15
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class MoreLikeBean extends IconBeanImpl {
	private String gameId;
	private String gameName;
	public MoreLikeBean(JSONObject obj){
		super(obj.optString("gamePic")+ "pic1.jpg");
		this.gameId=obj.optString("gameId");
		this.gameName=obj.optString("gameName");
	}
	/**
	 * @return 返回 gameId
	 */
	public String getGameId() {
		return gameId;
	}
	/**
	 * @param 对gameId进行赋值
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	/**
	 * @return 返回 gameName
	 */
	public String getGameName() {
		return gameName;
	}
	/**
	 * @param 对gameName进行赋值
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	
}
