package com.egame.beans;

import java.text.DecimalFormat;

import android.database.Cursor;

import com.egame.app.services.DBService;
import com.egame.utils.ui.IconBeanImpl;

/**
 * @desc 下载列表实体
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
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
public class DownloadListBean extends IconBeanImpl {

	/** 游戏名称 */
	private String name;

	/** 已下载大小 */
	private long downsize;

	/** 总大小 */
	private long totalsize;

	/** 状态 */
	private int state;

	/** 提示语 */
	private String hint;

	/** cpcode */
	private String cpcode;

	private String serviceid;

	private String serviceCode;
	
	private String cpid;
	
	private String channelid;
	
	private String packageName;
	
	private int versionCode;
	
	private int localVersionCode;
	
	private String install;
	
	private String sortName;
	
	private String cancelUpdateTime;
	
	private String downloadUrl ;

	public DownloadListBean(String name, long downsize, long totalsize, int state, String hint, String cpcode, String serviceid, String serviceCode,String cpid,String channelid,String picpath,String downloadUrl) {
		super(picpath);
		this.name = name;
		this.downsize = downsize;
		this.totalsize = totalsize;
		this.state = state;
		this.hint = hint;
		this.cpcode = cpcode;
		this.serviceCode = serviceCode;
		this.serviceid = serviceid;
		this.cpid = cpid;
		this.channelid = channelid;
		this.downloadUrl = downloadUrl;
	}
	
	public DownloadListBean(Cursor cursor){
		super(cursor.getString(cursor.getColumnIndex(DBService.KEY_PICPATH)));
		this.name = cursor.getString(cursor.getColumnIndex(DBService.KEY_GAMENAME));
		this.downsize = Long.parseLong(cursor.getString(cursor.getColumnIndex(DBService.KEY_DOWNSIZE)));
		this.totalsize = Long.parseLong(cursor.getString(cursor.getColumnIndex(DBService.KEY_TOTALSIZE)));
		this.state = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBService.KEY_STATE)));
		this.hint = cursor.getString(cursor.getColumnIndex(DBService.KEY_STATE_HINT));
		this.serviceid = cursor.getString(cursor.getColumnIndex(DBService.KEY_SERVICEID));
		this.cpcode = cursor.getString(cursor.getColumnIndex(DBService.KEY_CPCODE));
		this.serviceCode = cursor.getString(cursor.getColumnIndex(DBService.KEY_SERVICECODE));
		this.cpid = cursor.getString(cursor.getColumnIndex(DBService.KEY_CPID));
		this.channelid = cursor.getString(cursor.getColumnIndex(DBService.KEY_CHANNELID));
		this.packageName = cursor.getString(cursor.getColumnIndex(DBService.KEY_PACKAGENAME));
		this.versionCode = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBService.KEY_VERSION)));
		this.localVersionCode = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBService.KEY_LOACL_VERSION)));
		this.install = cursor.getString(cursor.getColumnIndex(DBService.KEY_INSTALL));
		this.sortName = cursor.getString(cursor.getColumnIndex(DBService.KEY_SORT_NAME));
		this.cancelUpdateTime = cursor.getString(cursor.getColumnIndex(DBService.KEY_CANCEL_UPDATE_TIME));
		this.downloadUrl = cursor.getString(cursor.getColumnIndex(DBService.KEY_DOWNLOAD_URL));
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDownsize() {
		return downsize;
	}

	public void setDownsize(long downsize) {
		this.downsize = downsize;
	}

	public long getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(long totalsize) {
		this.totalsize = totalsize;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getCpcode() {
		return cpcode;
	}

	public void setCpcode(String cpcode) {
		this.cpcode = cpcode;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	
	public int getLocalVersionCode() {
		return localVersionCode;
	}

	public void setLocalVersionCode(int localVersionCode) {
		this.localVersionCode = localVersionCode;
	}

	public String getInstall() {
		return install;
	}

	public void setInstall(String install) {
		this.install = install;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getCancelUpdateTime() {
		return cancelUpdateTime;
	}

	public void setCancelUpdateTime(String cancelUpdateTime) {
		this.cancelUpdateTime = cancelUpdateTime;
	}
	
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * 获取游戏大小 按M为单位
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-30
	 */
	public String getGameSizeM() {
		float gameSize = getTotalsize() * 1.0f / (1024 * 1024);
		return new DecimalFormat("#0.00").format(gameSize) + "M";
	}

	/**
	 * 是否属于下载完成未安装
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-30
	 */
	public boolean isDownFinishAndNotInstall() {
		if (state == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否属于下载中
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-30
	 */
	public boolean isDownloading() {
		if (state == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否属于下载中断
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-30
	 */
	public boolean isDownError() {
		if (state == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否属于已安装
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-30
	 */
	public boolean isInstalled() {
		if (install.equals(DBService.INSTALL_INSTALL) && state == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否是下载已卸载
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-2-8
	 */
	public boolean isUnInstalled(){
		if(install.equals(DBService.INSTALL_UNINSTALL)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否是暂停
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-31
	 */
	public boolean isPause(){
		if (state == 4) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否是升级
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-2-8
	 */
	public boolean isUpdate(){
//		L.d("update",state + "|" + versionCode +"|"+localVersionCode +"|"+cancelUpdateTime+"|"+(System.currentTimeMillis() - Long.parseLong(cancelUpdateTime)));
		try{
			if (state == 3 && versionCode > localVersionCode && (System.currentTimeMillis() - Long.parseLong(cancelUpdateTime)) > 604800000) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 获取百分比
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-30
	 */
	public int getPercentage() {
		try {
			float percentage = downsize * 100 / totalsize;
//			L.d("download", "" + downsize + "|" + totalsize + "|" + percentage);
			return (int) Math.floor(percentage);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
