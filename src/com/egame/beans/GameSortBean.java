package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 游戏分类的实体
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
public class GameSortBean extends IconBeanImpl{
	/**
	 * 类型名称
	 * */
	private String typeName;

	/**
	 * 类型代码
	 * */
	private int typeCode;

	/**
	 * 子类名称（允许为空）
	 * */
	private String gameClassName;

	/**
	 * 子类代码（允许为空）
	 * */
	private int classCode;

//	/**
//	 * 图片地址
//	 * */
//	private String picturePath;

	/**
	 * 游戏数量
	 * */
	private int totalNumber;
	/**
	 * 分类默认排序
	 * */
	private int orderType;
	/**
	 * 分类默认排序（升序/降序）
	 * */
	private String orderDesc;
	
	public GameSortBean(JSONObject obj){
		super(obj.optString("picturePath"));
		this.typeName=obj.optString("typeName");
		this.typeCode=obj.optInt("typeCode");
		this.gameClassName=obj.optString("className");
		this.classCode=obj.optInt("classCode");
//		this.picturePath=obj.optString("picturePath");
		this.totalNumber=obj.optInt("totalNumber");
		this.orderType=obj.optInt("orderType");
		this.orderDesc=obj.optString("orderDesc");
	}
	/**
	 * @return 返回 orderType
	 */
	public int getOrderType() {
		return orderType;
	}
	/**
	 * @param 对orderType进行赋值
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return 返回 orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}
	/**
	 * @param 对orderDesc进行赋值
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	/**
	 * @return 返回 typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param 对typeName进行赋值
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return 返回 typeCode
	 */
	public int getTypeCode() {
		return typeCode;
	}
	/**
	 * @param 对typeCode进行赋值
	 */
	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @return 返回 gameClassName
	 */
	public String getGameClassName() {
		return gameClassName;
	}
	/**
	 * @param 对gameClassName进行赋值
	 */
	public void setGameClassName(String gameClassName) {
		this.gameClassName = gameClassName;
	}
	/**
	 * @return 返回 classCode
	 */
	public int getClassCode() {
		return classCode;
	}
	/**
	 * @param 对classCode进行赋值
	 */
	public void setClassCode(int classCode) {
		this.classCode = classCode;
	}
//	/**
//	 * @return 返回 picturePath
//	 */
//	public String getPicturePath() {
//		return picturePath;
//	}
//	/**
//	 * @param 对picturePath进行赋值
//	 */
//	public void setPicturePath(String picturePath) {
//		this.picturePath = picturePath;
//	}
	/**
	 * @return 返回 totalNumber
	 */
	public int getTotalNumber() {
		return totalNumber;
	}
	/**
	 * @param 对totalNumber进行赋值
	 */
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
}
