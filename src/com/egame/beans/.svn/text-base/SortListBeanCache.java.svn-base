package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc	存放游戏分类列表内容的缓存对象
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-1
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class SortListBeanCache {
	
	/**
	 * 数据列表
	 */
	List<GameSortBean> list;
	
	/**
	 * 是否读取完毕
	 */
	boolean finish;
	
	public SortListBeanCache(){
		this.finish=false;
		list=new ArrayList<GameSortBean>();
	}

	/**
	 * @return 返回 list
	 */
	public List<GameSortBean> getList() {
		return list;
	}

	/**
	 * @param 对list进行赋值
	 */
	public void setList(List<GameSortBean> list) {
		this.list = list;
	}

	/**
	 * @return 返回 finish
	 */
	public boolean isFinish() {
		return finish;
	}

	/**
	 * @param 对finish进行赋值
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
	/**
	 * 释放专题图片
	 * 
	 */
	public void releaseIcon(){
		try{
			for(GameSortBean page : list){
				if(page.getIcon()!=null&&page.getIcon().getBitmap().isRecycled()){
					page.getIcon().getBitmap().recycle();
					page.setIcon(null);
				}
			}
		}catch(Exception e){
			
		}
	}
}
