package com.egame.beans;

import org.json.JSONObject;

public class CityBean {
	/** 城市的Id */
	private String cityId;
	/** 城市名称 */
	private String cityName;
	
	public CityBean(JSONObject obj){
		this.cityId = obj.optString("cityId", "");
		this.cityName=obj.optString("cityName", "");
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
