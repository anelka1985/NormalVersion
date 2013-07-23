package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 游戏专题实体
 * 
 * @Copyright lenovo
 * 
 * @Project TestQAS
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-29
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class TopicBean extends IconBeanImpl {

	/** 专题id */
	private int contentId;

	/** 专题类型 1=普通专题 2=排行 */
	private int type;

	/** 专题图片显示类型 1=小图片 2=大图片 */
	private int pictype;

	/** 专题标题 */
	private String title;

	/** 专题描述 */
	private String introduce;

	public TopicBean() {

	}

	public TopicBean(JSONObject obj) {
		super(obj.optString("picturePath"));
		this.contentId = obj.optInt("contentId");
		this.type = obj.optInt("type");
		this.title = obj.optString("title");
		this.introduce = obj.optString("introduce");
		this.pictype = obj.optInt("pictype");
	}
	
	/**
	 * 判断返回的专题图片是否做大图展示
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-12
	 */
	public boolean isBigPic(){
		if(this.pictype == 2){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否是排行列表
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-12
	 */
	public boolean isRankTopic(){
		if(this.type == 2){
			return true;
		}else{
			return false;
		}
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getPictype() {
		return pictype;
	}

	public void setPictype(int pictype) {
		this.pictype = pictype;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
