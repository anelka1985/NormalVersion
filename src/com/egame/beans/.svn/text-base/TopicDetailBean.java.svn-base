package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author Administrator
 * 
 * @timer 2012-1-11
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class TopicDetailBean {

	/** 专题id */
	private int topicId;

	/** 游戏列表 */
	private List<GameTopListBean> gameList;

	/** 专题名称 */
	private String topicName;

	/** 专题描述 */
	private String topicDesc;

	public TopicDetailBean(JSONObject obj) {
		this.topicId = obj.optInt("topicId");
		this.topicName = obj.optString("topicName");
		this.topicDesc = obj.optString("topicDesc");
		gameList = new ArrayList<GameTopListBean>();
		try {
			JSONArray array = obj.getJSONArray("gameList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				GameTopListBean bean = new GameTopListBean(json);
				gameList.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public List<GameTopListBean> getGameList() {
		return gameList;
	}

	public void setGameList(List<GameTopListBean> gameList) {
		this.gameList = gameList;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}
	
	

}
