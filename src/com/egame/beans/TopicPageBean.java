package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @desc 游戏专题分类实体
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
public class TopicPageBean {

	/** 主题分类id */
	private int id;

	/** 主题分类名称 */
	private String topicName;

	/** 主题分类包含的专题列表 */
	private List<TopicBean> topicList;

	public TopicPageBean() {

	}

	public TopicPageBean(JSONObject obj) {
		this.id = obj.optInt("id");
		this.topicName = obj.optString("topicName");
		this.topicList = new ArrayList<TopicBean>();
		try {
			JSONArray array = obj.getJSONArray("innerList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				TopicBean topic = new TopicBean(json);
				this.topicList.add(topic);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public List<TopicBean> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<TopicBean> topicList) {
		this.topicList = topicList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	

}
