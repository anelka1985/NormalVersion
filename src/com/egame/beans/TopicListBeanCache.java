package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 用来缓存专题列表对象
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-1-19
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class TopicListBeanCache {

	/**
	 * 数据列表
	 */
	List<TopicPageBean> list;

	/**
	 * 当前条
	 */
	int index;

	/**
	 * 是否读取完毕
	 */
	boolean finish;

	public TopicListBeanCache() {
		this.index = 0;
		this.finish = false;
		this.list = new ArrayList<TopicPageBean>();
	}

	public List<TopicPageBean> getList() {
		return list;
	}

	public void setList(List<TopicPageBean> list) {
		this.list = list;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	/**
	 * 释放专题图片
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-29
	 */
	public void releaseIcon(){
		try{
			for(TopicPageBean page : list){
				for(TopicBean topic : page.getTopicList()){
					if(topic.getIcon() != null && !topic.getIcon().getBitmap().isRecycled()){
						topic.getIcon().getBitmap().recycle();
						topic.setIcon(null);
					}
				}
			}
		}catch(Exception e){
			
		}
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

}
