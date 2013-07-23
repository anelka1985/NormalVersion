package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.egame.utils.common.L;
import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 游戏包的实体bean
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2011-12-29
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GamePackageBean extends IconBeanImpl {
	/**
	 * 游戏包编号
	 * */
	private String packageId;

	/**
	 * 游戏包名称
	 * */
	private String packageName;

	/**
	 * 游戏包价格
	 * */
	private String price;
	/**
	 * 是否订购
	 * */
	private String isorder;

	/**
	 * 推荐理由
	 * */
	private String desc;


	/**
	 * 游戏信息
	 * */

	List<GameInPackageBean> list;
	
	public GamePackageBean(JSONObject obj) {
		this.packageId = obj.optString("packageId");
		this.packageName = obj.optString("packageName");
		this.price = obj.optString("price");
		this.desc = obj.optString("desc");
		this.isorder = obj.optString("isOrder");
		list=new ArrayList<GameInPackageBean>();
		try {
			JSONArray array=obj.getJSONArray("gamelist");
			for(int i=0;i<array.length();i++){
				JSONObject json=array.getJSONObject(i);
				L.d("d", json.toString());
				GameInPackageBean bean=new GameInPackageBean(json);
				list.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @return 返回 packageId
	 */
	public String getPackageId() {
		return packageId;
	}

	/**
	 * @param 对packageId进行赋值
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
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

	public void setList(List<GameInPackageBean> list){
		this.list=list;
	}
	public List<GameInPackageBean> getList(){
		return list;
	}

	/**
	 * @return 返回 desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param 对desc进行赋值
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getTotal(){
		return list.size();
	}

	public String getIsorder() {
		return isorder;
	}

	public void setIsorder(String isorder) {
		this.isorder = isorder;
	}

}
