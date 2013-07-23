package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @desc 存放一页广告的bean
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-28
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class AdPageBean {
	
	/** 推荐位置代码 */
	public static final int AREA_RECOMMOND = 1;
	
	/** 新品位置代码 */
	public static final int AREA_NEW = 2;
	
	/** 每一页的id */
	private int id;
	
	/** 位置代码 1:推荐,2:新品*/
	private int areaCode;
	
	/** 广告列表 可能是1张也可能是2张*/
	private List<AdBean> adList;
	
	public AdPageBean(JSONObject obj){
		this.id = obj.optInt("id");
		this.areaCode = obj.optInt("areaCode");
		this.adList = new ArrayList<AdBean>();
		try {
			JSONArray array = obj.getJSONArray("adList");
			for(int i = 0;i < array.length();i++){
				JSONObject json = array.getJSONObject(i);
				AdBean adBean = new AdBean(json);
				adList.add(adBean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public List<AdBean> getAdList() {
		return adList;
	}

	public void setAdList(List<AdBean> adList) {
		this.adList = adList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	
	

}
