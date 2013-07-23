package com.egame.utils.ui;

import java.util.List;

/**
 * @desc 释放列表图片
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-1-14
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ReleaseListIcon<T extends IconBean> {

	List<T> list;

	public ReleaseListIcon(List<T> list) {
		this.list = list;
	}

	//
	// @Override
	// public void run() {
	// for(T t:list){
	// try{
	// IconBean bean = t;
	// if(bean.getIcon() != null && !bean.getIcon().getBitmap().isRecycled()){
	// bean.getIcon().getBitmap().recycle();
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	//
	// }
	// }
	public void release() {
		for (T t : list) {
			try {
				IconBean bean = t;
				if (bean.getIcon() != null && !bean.getIcon().getBitmap().isRecycled()) {
					bean.getIcon().getBitmap().recycle();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.gc();
	}

}
