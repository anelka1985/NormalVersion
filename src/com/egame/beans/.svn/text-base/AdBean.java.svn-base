package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 存放广告的bean
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-28
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class AdBean extends IconBeanImpl {

	/** 广告id */
	private int id;

	/** 广告名称 */
	private String name;

	/** 广告类型 1:游戏类型 2:活动详情,调wap浏览器 3:专题详情 4:其他类型,调wap浏览器 */
	private int type;
	
	/** 动作内容,如果是调wap传url,其他的传对应的id */
	private String actionContent;
	
	public AdBean(JSONObject obj){
		super(obj.optString("advertPic"));
		this.id = obj.optInt("advertId");
		this.name = obj.optString("advertName");
		this.type = obj.optInt("advertType");
		this.actionContent = obj.optString("actionContent");
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getActionContent() {
		return actionContent;
	}

	public void setActionContent(String actionContent) {
		this.actionContent = actionContent;
	}
	
	public boolean isWapAd(){
		if(type == 4){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isActiveAd(){
		if(type == 2){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isTopicAd(){
		if(type == 3){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isGameAd(){
		if(type == 1){
			return true;
		}else{
			return false;
		}
	}
	
	
}
