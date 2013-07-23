package com.egame.utils.ui;


/**
 * @desc 一套普通activity的格式,所有的activity都继承这个activity
 * 
 * @Copyright lenovo
 * 
 * @Project AndroidUtil2
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-8-18
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public interface BaseActivity {

	/**
	 * 从intent中获取数据
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2011-12-26
	 */
	abstract void initData();

	/**
	 * 存放从xml中获取ui,例如
	 * findViewById
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2011-8-18
	 */
	abstract void initView();

	/**
	 * 存放刷新页面的代码
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2011-8-18
	 */
	abstract void initViewData();

	/**
	 * 初始化页面UI事件,例如
	 * setOnClickListener
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2011-12-26
	 */
	abstract void initEvent();

}
