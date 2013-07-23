package com.egame.beans;

import java.util.List;

import org.json.JSONObject;

public class ProvinceBean {
	/** 省份ID */
	private String provinceId;
	/** 省份名称 */
	private String provinceName;
	/** 该省中的城市 */
	private List<CityBean> list;

	public ProvinceBean(JSONObject json,List<CityBean> list) {
		this.provinceId = json.optString("provinceId", "");
		this.provinceName = json.optString("provinceName", "");
		this.list = list;
	}
	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 *            the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the list
	 */
	public List<CityBean> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<CityBean> list) {
		this.list = list;
	}

}
