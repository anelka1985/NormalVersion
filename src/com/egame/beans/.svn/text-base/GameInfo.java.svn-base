package com.egame.beans;

import java.text.DecimalFormat;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 游戏详情实体类
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
public class GameInfo extends IconBeanImpl {
	/**
	 * 游戏id
	 * */
	private String gameId;

	/**
	 * 游戏名称
	 * */
	private String gameName;

	/**
	 * 游戏提供商
	 * */
	private String provider;

	/**
	 * 游戏介绍
	 * */
	private String introduction;

	/**
	 * 产品编号 对应 游戏编号
	 * */
	private String serviceCode;

	/**
	 * 游戏安装程序文件编号
	 * */
	private String entityId;

	/**
	 * 最新游戏安装程序文件名称
	 * */
	private String apkname;

	/**
	 * 游戏安装程序文件大小
	 * */
	private int fileSize;

	private String fileSizeM;

	/**
	 * 游戏类型编号
	 * */
	private String typeCode;

	/**
	 * 游戏类型名称
	 * */
	private String typeName;

	/**
	 * 游戏分类编号（游戏类型子类）
	 * */
	private String classCode;

	/**
	 * 游戏分类名称
	 * */
	private String className;

	/**
	 * 付费代码
	 * */
	private String feeCode;

	/**
	 * 付费名称
	 * */
	private String feeName;

	/**
	 * 支付代码
	 * */
	private String payCode;

	/**
	 * 支付名称
	 * */
	private String payName;

	/**
	 * 计费金额
	 * */
	private String money;

	/**
	 * 星级评价
	 * */
	private String score;

	/**
	 * 图片路径
	 * */
	private String picturePath;

	/**
	 * WAP游戏的服务器地址
	 * */
	private String wapURL;

	/**
	 * 推广渠道来源
	 * */
	private String channelCode;

	/**
	 * 收藏状态(0:已收藏 1:未收藏)
	 * */
	private String favoriteFlag;

	/**
	 * 热度标签(0:火热 1:凉爽)
	 * */
	private String hotFlag;

	/**
	 * 是否入包（0：包外，1：包内
	 * */
	private String packageFlag;

	/**
	 * 游戏包名称
	 * */
	private String packageName;

	/**
	 * 游戏包版本
	 * */
	private String packageVersion;

	/**
	 * 下载次数
	 * */
	private String downloadTimes;

	/**
	 * 分享次数
	 * */
	private String sharedTimes;

	/**
	 * 评论次数
	 * */
	private String commentTimes;

	/**
	 * 修改日期 YYYY-MM-DD
	 * */
	private String deployeeTime;

	/**
	 * 修改日期 YYYY-MM-DD HH24:MI:SS
	 * */
	private String modifyTime;

	/**
	 * 屏幕截图
	 * */
	private IconBeanImpl[] screenShotIcon;

	/**
	 * 游戏标签
	 * */
	private String label;

	private String osName;
	private String cpCode;
	private String cpId;
	private String osCode;
	/**
	 * 订购状态
	 * */
	private String downOrderStatus;

	/**
	 * 订购的URL
	 * */
	private String orderURL;

	/**
	 * 点播的URL
	 * */
	private String dianboURL;

	private int systemMessage;
	private int friendMessage;

	public GameInfo(JSONObject obj, JSONObject orderObj, JSONObject messageObj) {
		super(obj.optString("picturePath") + "pic1.jpg");
		screenShotIcon = new IconBeanImpl[6];
		for (int i = 0; i < screenShotIcon.length; i++) {
			screenShotIcon[i] = new IconBeanImpl(obj.optString("picturePath") + "jietu" + (i + 1) + ".jpg");
		}
		this.gameId = obj.optString("gameId");
		this.gameName = obj.optString("gameName");
		this.provider = obj.optString("provider");
		this.introduction = obj.optString("introduction");
		this.serviceCode = obj.optString("serviceCode");
		this.entityId = obj.optString("entityId");
		this.apkname = obj.optString("apkname");
		this.fileSize = obj.optInt("fileSize");
		try {
			DecimalFormat dcmFmt = new DecimalFormat("0.00");
			this.fileSizeM = dcmFmt.format(fileSize / (float) 1024) + "M";
		} catch (Exception e) {
			this.fileSizeM = fileSize + "KB";
		}
		this.typeCode = obj.optString("typeCode");
		this.typeName = obj.optString("typeName");
		this.classCode = obj.optString("classCode");
		this.className = obj.optString("className");
		this.feeCode = obj.optString("feeCode");
		this.feeName = obj.optString("feeName");
		this.payCode = obj.optString("payCode");
		this.payName = obj.optString("payName");
		this.money = obj.optString("money");
		this.score = obj.optString("score");
		this.picturePath = obj.optString("picturePath");
		this.wapURL = obj.optString("wapURL");
		this.channelCode = obj.optString("channelCode");
		this.favoriteFlag = obj.optString("favoriteFlag");
		this.hotFlag = obj.optString("hotFlag");
		this.packageFlag = obj.optString("packageFlag");
		this.packageName = obj.optString("packageName");
		this.packageVersion = obj.optString("packageVersion");
		this.downloadTimes = obj.optString("downloadTimes");
		this.sharedTimes = obj.optString("sharedTimes");
		this.commentTimes = obj.optString("commentTimes");
		this.deployeeTime = obj.optString("deployeeTime");
		this.modifyTime = obj.optString("modifyTime");
		this.label = obj.optString("label");
		this.osCode = obj.optString("osCode");
		this.osName = obj.optString("osName");
		this.cpCode = obj.optString("cpCode");
		this.cpId = obj.optString("cpId");
		this.downOrderStatus = orderObj.optString("downOrderStatus");
		this.orderURL = orderObj.optString("orderURL");
		this.dianboURL = orderObj.optString("dianboURL");
		this.systemMessage = messageObj.optInt("systemMessage");
		this.friendMessage = messageObj.optInt("friendMessage");
	}

	public IconBeanImpl[] getScreenShotIcons() {
		return screenShotIcon;
	}

	/**
	 * @return 返回 osName
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * @param 对osName进行赋值
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * @return 返回 cpCode
	 */
	public String getCpCode() {
		return cpCode;
	}

	/**
	 * @param 对cpCode进行赋值
	 */
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}

	/**
	 * @return 返回 cpId
	 */
	public String getCpId() {
		return cpId;
	}

	/**
	 * @param 对cpId进行赋值
	 */
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	/**
	 * @return 返回 osCode
	 */
	public String getOsCode() {
		return osCode;
	}

	/**
	 * @param 对osCode进行赋值
	 */
	public void setOsCode(String osCode) {
		this.osCode = osCode;
	}

	/**
	 * @return 返回 gameId
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * @param 对gameId进行赋值
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	/**
	 * @return 返回 gameName
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param 对gameName进行赋值
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return 返回 provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param 对provider进行赋值
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return 返回 introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param 对introduction进行赋值
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return 返回 serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param 对serviceCode进行赋值
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return 返回 entityId
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param 对entityId进行赋值
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return 返回 apkname
	 */
	public String getApkname() {
		return apkname;
	}

	/**
	 * @param 对apkname进行赋值
	 */
	public void setApkname(String apkname) {
		this.apkname = apkname;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSizeM() {
		return fileSizeM;
	}

	public void setFileSizeM(String fileSizeM) {
		this.fileSizeM = fileSizeM;
	}

	/**
	 * @return 返回 typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param 对typeCode进行赋值
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
	 * @return 返回 classCode
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * @param 对classCode进行赋值
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * @return 返回 className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param 对className进行赋值
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return 返回 feeCode
	 */
	public String getFeeCode() {
		return feeCode;
	}

	/**
	 * @param 对feeCode进行赋值
	 */
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * @return 返回 feeName
	 */
	public String getFeeName() {
		return feeName;
	}

	/**
	 * @param 对feeName进行赋值
	 */
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	/**
	 * @return 返回 payCode
	 */
	public String getPayCode() {
		return payCode;
	}

	/**
	 * @param 对payCode进行赋值
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	/**
	 * @return 返回 payName
	 */
	public String getPayName() {
		return payName;
	}

	/**
	 * @param 对payName进行赋值
	 */
	public void setPayName(String payName) {
		this.payName = payName;
	}

	/**
	 * @return 返回 money
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * @param 对money进行赋值
	 */
	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * @return 返回 score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param 对score进行赋值
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @return 返回 picturePath
	 */
	public String getPicturePath() {
		return picturePath;
	}

	/**
	 * @param 对picturePath进行赋值
	 */
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	/**
	 * @return 返回 wapURL
	 */
	public String getWapURL() {
		return wapURL;
	}

	/**
	 * @param 对wapURL进行赋值
	 */
	public void setWapURL(String wapURL) {
		this.wapURL = wapURL;
	}

	/**
	 * @return 返回 channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param 对channelCode进行赋值
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return 返回 favoriteFlag
	 */
	public String getFavoriteFlag() {
		return favoriteFlag;
	}

	/**
	 * @param 对favoriteFlag进行赋值
	 */
	public void setFavoriteFlag(String favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}

	/**
	 * @return 返回 hotFlag
	 */
	public String getHotFlag() {
		return hotFlag;
	}

	/**
	 * @param 对hotFlag进行赋值
	 */
	public void setHotFlag(String hotFlag) {
		this.hotFlag = hotFlag;
	}

	/**
	 * @return 返回 packageFlag
	 */
	public String getPackageFlag() {
		return packageFlag;
	}

	/**
	 * @param 对packageFlag进行赋值
	 */
	public void setPackageFlag(String packageFlag) {
		this.packageFlag = packageFlag;
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
	 * @return 返回 packageVersion
	 */
	public String getPackageVersion() {
		return packageVersion;
	}

	/**
	 * @param 对packageVersion进行赋值
	 */
	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	/**
	 * @return 返回 downloadTimes
	 */
	public String getDownloadTimes() {
		return downloadTimes;
	}

	/**
	 * @param 对downloadTimes进行赋值
	 */
	public void setDownloadTimes(String downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	/**
	 * @return 返回 sharedTimes
	 */
	public String getSharedTimes() {
		return sharedTimes;
	}

	/**
	 * @param 对sharedTimes进行赋值
	 */
	public void setSharedTimes(String sharedTimes) {
		this.sharedTimes = sharedTimes;
	}

	/**
	 * @return 返回 commentTimes
	 */
	public String getCommentTimes() {
		return commentTimes;
	}

	/**
	 * @param 对commentTimes进行赋值
	 */
	public void setCommentTimes(String commentTimes) {
		this.commentTimes = commentTimes;
	}

	/**
	 * @return 返回 deployeeTime
	 */
	public String getDeployeeTime() {
		return deployeeTime;
	}

	/**
	 * @param 对deployeeTime进行赋值
	 */
	public void setDeployeeTime(String deployeeTime) {
		this.deployeeTime = deployeeTime;
	}

	/**
	 * @return 返回 modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param 对modifyTime进行赋值
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return 返回 label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param 对label进行赋值
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return 返回 downOrderStatus
	 */
	public String getDownOrderStatus() {
		return downOrderStatus;
	}

	/**
	 * @param 对downOrderStatus进行赋值
	 */
	public void setDownOrderStatus(String downOrderStatus) {
		this.downOrderStatus = downOrderStatus;
	}

	/**
	 * @return 返回 orderURL
	 */
	public String getOrderURL() {
		return orderURL;
	}

	/**
	 * @param 对orderURL进行赋值
	 */
	public void setOrderURL(String orderURL) {
		this.orderURL = orderURL;
	}

	/**
	 * @return 返回 dianboURL
	 */
	public String getDianboURL() {
		return dianboURL;
	}

	/**
	 * @param 对dianboURL进行赋值
	 */
	public void setDianboURL(String dianboURL) {
		this.dianboURL = dianboURL;
	}

	/**
	 * @return 返回 systemMessage
	 */
	public int getSystemMessage() {
		return systemMessage;
	}

	/**
	 * @param 对systemMessage进行赋值
	 */
	public void setSystemMessage(int systemMessage) {
		this.systemMessage = systemMessage;
	}

	/**
	 * @return 返回 friendMessage
	 */
	public int getFriendMessage() {
		return friendMessage;
	}

	/**
	 * @param 对friendMessage进行赋值
	 */
	public void setFriendMessage(int friendMessage) {
		this.friendMessage = friendMessage;
	}

	/**
	 * 判断是否是WAP游戏
	 */
	public boolean isWap() {
		if ("20".equals(typeCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是收费游戏
	 */
	public boolean isPay() {
		if ("1".equals(payCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是包内游戏
	 */
	public boolean isPackageGame() {
		if ("1".equals(packageFlag)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 需要发送消息广播
	 */
	public boolean canSendMessage() {
		int messageNum = friendMessage + systemMessage;
		if (messageNum > 0) {
			return true;
		} else {
			return false;
		}
	}
}
