package com.egame.beans;

import java.text.DecimalFormat;

import org.json.JSONObject;

import android.text.SpannableString;

import com.egame.utils.common.CommonUtil;
import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 游戏列表实体
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-27
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameListBean extends IconBeanImpl {

	/** 游戏id */
	private int gameId;

	/** 游戏名称 */
	private String gameName;

	/** 游戏类型代码 */
	private int typeCode;

	/** 游戏类型名称 */
	private String typeName;

	/** 游戏子类型名称 */
	private String className;

	/** 子类型代码 */
	private int classCode;

	/** 产品编号 */
	private String serviceCode;

	/** 游戏包名称 */
	private String packageName;

	/** 游戏包versionName */
	private String packageVersionName;

	/** 游戏包versionCode */
	private int packageVersionCode;

	/** 提供商 */
	private String provider;

	private String cpid;

	private String cpCode;

	/** 价格 */
	private int money;

	/** 计费类型名称 */
	private String payName;

	/** 计费类型代码 */
	private int payCode;

	/** 计费代码 */
	private int feeCode;

	/** 计费名称 */
	private String feeName;

	/** 星级评分 */
	private int score;

	/** 分享次数 */
	private int sharedTimes;

	/** 评论次数 */
	private int commentTimes;

//	/** 下载次数 */
//	private int downloadTimes;

	/** 是否收藏 */
	private boolean favorite;

	/** 是否热门 */
	private boolean hot;

	/** 实体id */
	private int entityId;

	/** 文件大小 */
	private int fileSize;

	/** apk名称 */
	private String apkname;

	/** 渠道代码 */
	private String channelCode;

	/** 修改时间 */
	private String modifyTime;

	/** 发布时间 */
	private String deployeeTime;

	/** 是否包内 */
	private boolean packaged;

	// /** 图片路径 */
	// private String picturePath;

	/** wap地址 */
	private String wapURL;

	private String info;

	private String actionFlag;
	private String servicedsc;
	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public GameListBean(JSONObject obj) {
		super(obj.optString("picPath"));
		this.gameId = obj.optInt("productId");
		this.gameName = obj.optString("productName");
		this.typeCode = obj.optInt("typeCode");
		this.typeName = obj.optString("typeName");
		this.className = obj.optString("className");
		this.classCode = obj.optInt("classCode");
		this.serviceCode = obj.optString("serviceCode");
		this.packageName = obj.optString("packageName");
		this.provider = obj.optString("cpname");
		this.money = obj.optInt("money");
		this.payName = obj.optString("payName");
		this.payCode = obj.optInt("payCode");
		this.feeCode = obj.optInt("feeCode");
		this.feeName = obj.optString("feeName");
		this.score = obj.optInt("score");
		this.sharedTimes = obj.optInt("sharedTimes");
		this.commentTimes = obj.optInt("commentTimes");
//		this.downloadTimes = obj.optInt("counts");
		this.favorite = CommonUtil.getBoolean(obj.optInt("isFavorite"));
		this.hot = CommonUtil.getBoolean(obj.optInt("hotFlag"));
		this.entityId = obj.optInt("entityId");
		this.fileSize = obj.optInt("fileSize");
		this.apkname = obj.optString("apkname");
		this.packageVersionName = obj.optString("packageVersionName");
		this.packageVersionCode = obj.optInt("packageVersionCode");
		// this.packageVersionCode = 100;
		this.channelCode = obj.optString("channelcode");
		this.modifyTime = obj.optString("modifyDate");
		this.deployeeTime = obj.optString("deployTime");
		this.packaged = CommonUtil.getBoolean(obj.optInt("isPackageFlag"));
		this.cpCode = obj.optString("cpcode");
		this.cpid = obj.optString("cpId");
		this.wapURL = obj.optString("wapUrl");
		this.servicedsc = obj.optString("servicedsc");
		StringBuffer buf = new StringBuffer();
		if (classCode > 0) {
			buf.append(className + "  ");
		} else {
			buf.append(typeName + "  ");
		}
		if (typeCode != 20) {
			DecimalFormat dcmFmt = new DecimalFormat("0.00");
			buf.append(dcmFmt.format((float) fileSize / (float) 1024) + "M");
		}
		info = buf.toString();
	}

	public GameListBean() {

	}

	public String getGameInfo() {
		return info;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	// 搜索用
	private SpannableString spanName;
	private SpannableString spanInfo;

	public void setSpanName(SpannableString name) {
		spanName = name;
	}

	public SpannableString getSpanName() {
		return spanName;
	}

	public void setSpanInfo(SpannableString info) {
		spanInfo = info;
	}

	public SpannableString getSpanInfo() {
		return spanInfo;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getClassCode() {
		return classCode;
	}

	public void setClassCode(int classCode) {
		this.classCode = classCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public int getPayCode() {
		return payCode;
	}

	public void setPayCode(int payCode) {
		this.payCode = payCode;
	}

	public int getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(int feeCode) {
		this.feeCode = feeCode;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSharedTimes() {
		return sharedTimes;
	}

	public void setSharedTimes(int sharedTimes) {
		this.sharedTimes = sharedTimes;
	}

	public int getCommentTimes() {
		return commentTimes;
	}

	public void setCommentTimes(int commentTimes) {
		this.commentTimes = commentTimes;
	}

//	public int getDownloadTimes() {
//		return downloadTimes;
//	}
//
//	public void setDownloadTimes(int downloadTimes) {
//		this.downloadTimes = downloadTimes;
//	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getApkname() {
		return apkname;
	}

	public void setApkname(String apkname) {
		this.apkname = apkname;
	}

	public String getPackageVersionName() {
		return packageVersionName;
	}

	public void setPackageVersionName(String packageVersionName) {
		this.packageVersionName = packageVersionName;
	}

	public int getPackageVersionCode() {
		return packageVersionCode;
	}

	public void setPackageVersionCode(int packageVersionCode) {
		this.packageVersionCode = packageVersionCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDeployeeTime() {
		return deployeeTime;
	}

	public void setDeployeeTime(String deployeeTime) {
		this.deployeeTime = deployeeTime;
	}

	// public String getPicturePath() {
	// return picturePath;
	// }
	//
	// public void setPicturePath(String picturePath) {
	// this.picturePath = picturePath;
	// }

	public String getWapURL() {
		return wapURL;
	}

	public void setWapURL(String wapURL) {
		this.wapURL = wapURL;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isPackaged() {
		return packaged;
	}

	public void setPackaged(boolean packaged) {
		this.packaged = packaged;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getCpCode() {
		return cpCode;
	}

	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}

	public String getServicedsc() {
		return servicedsc;
	}

	public void setServicedsc(String servicedsc) {
		this.servicedsc = servicedsc;
	}

	/**
	 * 判断是否属于wap游戏
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-2-3
	 */
	public boolean isWap() {
		if (typeCode == 20) {
			return true;
		} else {
			return false;
		}
	}

}
