package com.egame.beans;

/**
 * @desc 用来存放广告点击位置的bean
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-1-18
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class AdTouchBean {
	
	private AdBean ad;
	
	private float x;
	
	private float y;

	public AdBean getAd() {
		return ad;
	}

	public void setAd(AdBean ad) {
		this.ad = ad;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
