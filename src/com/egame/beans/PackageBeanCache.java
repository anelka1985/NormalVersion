package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc
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
public class PackageBeanCache {

	/**
	 * 数据列表
	 */
	private List<GamePackageBean> list;

	/**
	 * 是否读取完毕
	 */
	boolean finish;
	
	
	public PackageBeanCache(){
		this.list=new ArrayList<GamePackageBean>();
		this.finish=false;
	}

	/**
	 * @return 返回 list
	 */
	public List<GamePackageBean> getList() {
		return list;
	}

	/**
	 * @param 对list进行赋值
	 */
	public void setList(List<GamePackageBean> list) {
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
	 * 释放游戏包图片
	 */
	public void releaseIcon(){
		try{
			for(GamePackageBean bean : list){
				for(GameInPackageBean innerBean : bean.getList()){
					if(innerBean.getIcon() != null && !innerBean.getIcon().getBitmap().isRecycled()){
						innerBean.getIcon().getBitmap().recycle();
						innerBean.setIcon(null);
					}
				}
			}
		}catch(Exception e){
			
		}
	}
}
