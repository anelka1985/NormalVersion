package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.common.L;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-30
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class PackageInfoHead {
	private String isOrder;
	private String price;
	private String tuiding;
	private String packageName;
	private String reCommend;
	private String dinggou;
	private int totalRecord;

	public PackageInfoHead(JSONObject obj) {
		L.d("ee", obj.toString());
		isOrder = obj.optString("isOrder");
		price = obj.optString("price");
		tuiding = obj.optString("tuiding");
		packageName = obj.optString("packageName");
		L.d("ee", packageName);
		reCommend = obj.optString("reCommend");
		dinggou = obj.optString("dinggou");
		totalRecord = obj.optInt("totalRecord");
	}

	/**
	 * @return 返回 isOrder
	 */
	public String getIsOrder() {
		return isOrder;
	}

	/**
	 * @param 对isOrder进行赋值
	 */
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	/**
	 * @return 返回 price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param 对price进行赋值
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return 返回 tuiding
	 */
	public String getTuiding() {
		return tuiding;
	}

	/**
	 * @param 对tuiding进行赋值
	 */
	public void setTuiding(String tuiding) {
		this.tuiding = tuiding;
	}

	/**
	 * @return 返回 packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param 对packageName进行赋值
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return 返回 reCommend
	 */
	public String getReCommend() {
		return reCommend;
	}

	/**
	 * @param 对reCommend进行赋值
	 */
	public void setReCommend(String reCommend) {
		this.reCommend = reCommend;
	}

	/**
	 * @return 返回 dinggou
	 */
	public String getDinggou() {
		return dinggou;
	}

	/**
	 * @param 对dinggou进行赋值
	 */
	public void setDinggou(String dinggou) {
		this.dinggou = dinggou;
	}

	/**
	 * @return 返回 totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * @param 对totalRecord进行赋值
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

}
