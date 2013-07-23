package com.egame.beans;


import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 游戏包内的游戏（图片和名称）
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-12
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameInPackageBean extends IconBeanImpl {
	private String gameName;
//	private String gamePic;

	public GameInPackageBean(JSONObject json){
		super(json.optString("gamePic"));
		this.gameName=json.optString("gameName");
//		this.gamePic=json.optString("gamePic");
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

//	/**
//	 * @return 返回 gamePic
//	 */
//	public String getGamePic() {
//		return gamePic;
//	}
//
//	/**
//	 * @param 对gamePic进行赋值
//	 */
//	public void setGamePic(String gamePic) {
//		this.gamePic = gamePic;
//	}
}
