package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @desc 游戏包详情的实体类
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
public class PackageDetailBean {
	private List<GameListBean> list;
	private int isOrder;
	private String dinggou;
	private String reCommend;
	private String tuiding;
	private String price;
	private String packageName;

	public PackageDetailBean(JSONObject obj) {
		try {
			this.isOrder = obj.optInt("isOrder");
			this.dinggou = obj.optString("dinggou");
			this.reCommend = obj.optString("reCommend");
			this.tuiding = obj.optString("tuiding");
			this.price = obj.optString("price");
			this.packageName = obj.optString("packageName");
			list = new ArrayList<GameListBean>();
			JSONArray array = obj.getJSONArray("gamePkgList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				GameListBean bean = new GameListBean(json);
				list.add(bean);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return 返回 list
	 */
	public List<GameListBean> getList() {
		return list;
	}

	/**
	 * @param 对list进行赋值
	 */
	public void setList(List<GameListBean> list) {
		this.list = list;
	}

	/**
	 * @return 返回 isOrder
	 */
	public int getIsOrder() {
		return isOrder;
	}

	/**
	 * @param 对isOrder进行赋值
	 */
	public void setIsOrder(int isOrder) {
		this.isOrder = isOrder;
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

	public boolean isOrder() {
		if (isOrder == 1) {
			return true;
		}
		return false;
	}
}
