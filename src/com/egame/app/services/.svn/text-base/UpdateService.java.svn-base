package com.egame.app.services;

import java.io.File;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.egame.R;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;

/**
 * @desc 升级服务
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-3-15
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class UpdateService extends Service {

	private String path = "";

	private String tmpfile = "";

	private String apkfile = "";

	private int version;

	private String url;

	private int retryCount = 0;
	
	private String notiTitle = "爱游戏";

	private Notification notification; // 通知
	private NotificationManager notificationManager; // 通知管理器
	private int NF_ID = 100;

	@Override
	public void onCreate() {
		super.onCreate();
		path = Environment.getExternalStorageDirectory().getAbsolutePath();
		notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		retryCount = 0;
		version = PreferenceUtil.getNewVersion(this);
		url = PreferenceUtil.getUpdatePath(this);
		apkfile = path + "/egame/" + "update" + version + ".apk";
		tmpfile = apkfile + ".tmp";
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			PackageManager pm = this.getPackageManager();
			PackageInfo info = pm.getPackageArchiveInfo(apkfile, PackageManager.GET_ACTIVITIES);
			if(info != null){
				Intent intent2 = new Intent(Intent.ACTION_VIEW);
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent2.setDataAndType(Uri.parse("file://" + apkfile), "application/vnd.android.package-archive");
				startActivity(intent2);
			}else{
				new File(apkfile).delete();
				notification = new Notification(R.drawable.icon, "爱游戏开始更新", System.currentTimeMillis());
				notification.icon = R.drawable.icon;
				notification.flags = Notification.FLAG_ONGOING_EVENT;
				Intent intent2 = new Intent();
				PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(getApplicationContext(), notiTitle, "爱游戏更新中...0%", contentIntent);
				notificationManager.notify(NF_ID, notification);
				new Thread(new UpdateThread(this)).start();
			}
		}else{
			notification = new Notification(R.drawable.icon, "爱游戏更新失败", System.currentTimeMillis());
			notification.icon = R.drawable.icon;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			Intent intent2 = new Intent();
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(getApplicationContext(), notiTitle, "未检测到sd卡,请检查sd卡连接", contentIntent);
			notificationManager.notify(NF_ID, notification);
		}
		
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTmpfile() {
		return tmpfile;
	}

	public void setTmpfile(String tmpfile) {
		this.tmpfile = tmpfile;
	}

	public String getApkfile() {
		return apkfile;
	}

	public void setApkfile(String apkfile) {
		this.apkfile = apkfile;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void onError() {
		L.d("update", "onError");
		if (retryCount < 5) {
			new Thread(new UpdateThread(this)).start();
			retryCount++;
		} else {
			L.d("update", "retryCount too many:" + retryCount);
			notification = new Notification(R.drawable.icon, "爱游戏更新出错", System.currentTimeMillis());
			notification.icon = R.drawable.icon;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
//			notification.contentView = new RemoteViews(this.getPackageName(), R.layout.egame_notification_update);
//			notification.contentView.setTextViewText(R.id.content, "爱游戏更新出错,请重新更新");
			Intent intent2 = new Intent();
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//			notification.contentIntent = contentIntent;
			notification.setLatestEventInfo(getApplicationContext(), notiTitle, "爱游戏更新出错,请重新更新", contentIntent);
			notificationManager.notify(NF_ID, notification);
		}

	}

	public void notifyMessage(String s) {
		if (notification != null && notificationManager != null) {
			Intent intent2 = new Intent();
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(getApplicationContext(), notiTitle, s, contentIntent);
			notificationManager.notify(NF_ID, notification);
		} else {
			L.d("update", "notification is null");
		}
	}

	public void onFinish() {
		File tmp = new File(tmpfile);
		tmp.renameTo(new File(apkfile));
		PackageManager pm = this.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkfile, PackageManager.GET_ACTIVITIES);
		if (info != null) {

//			Intent intent3 = new Intent(Intent.ACTION_VIEW);
//			intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent3.setDataAndType(Uri.parse("file://" + apkfile), "application/vnd.android.package-archive");
//			startActivity(intent3);
			
			Intent intent2 = new Intent(Intent.ACTION_VIEW);
			intent2.setDataAndType(Uri.parse("file://" + apkfile), "application/vnd.android.package-archive");
			notification = new Notification(R.drawable.icon, "爱游戏更新完成,点击安装", System.currentTimeMillis());
			notification.icon = R.drawable.icon;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
//			notification.contentView = new RemoteViews(this.getPackageName(), R.layout.egame_notification_update);
//			notification.contentView.setTextViewText(R.id.content, "爱游戏更新完成,点击安装");
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//			notification.contentIntent = contentIntent;
			notification.setLatestEventInfo(getApplicationContext(), notiTitle, "爱游戏更新完成,点击安装", contentIntent);
			notificationManager.notify(NF_ID, notification);
		}else{
			notification = new Notification(R.drawable.icon, "爱游戏更新解析包错误", System.currentTimeMillis());
			notification.icon = R.drawable.icon;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
//			notification.contentView = new RemoteViews(this.getPackageName(), R.layout.egame_notification_update);
//			notification.contentView.setTextViewText(R.id.content, "爱游戏更新解析包错误,请重新更新");
			Intent intent2 = new Intent();
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//			notification.contentIntent = contentIntent;
			notification.setLatestEventInfo(getApplicationContext(), notiTitle, "爱游戏更新解析包错误", contentIntent);
			notificationManager.notify(NF_ID, notification);
			if(tmp.exists()){
				tmp.delete();
			}
			File apk = new File(apkfile);
			if(apk.exists()){
				apk.delete();
			}
		}
	}

}
