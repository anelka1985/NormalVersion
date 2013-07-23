package com.egame.beans.biz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.widget.Toast;

import com.egame.app.EgameApplication;
import com.egame.beans.GameInfo;
import com.egame.beans.interfaces.DialogAble;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.ToastUtil;

/**
 * @desc 下载到手机内存的业务类
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzh@lenovo-cw.com
 * 
 * @timer 2012-7-1
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class DownloadToRomBizBean {

	/** 初始化总大小 */
	private final int MSG_INIT_TOTAL_SIZE = 0;

	/** 下载大小发生变化 */
	private final int MSG_DOWN_SIZE_CHANGE = 1;

	/** 下载完成 */
	private final int MSG_DOWN_FINISH = 2;

	/** 下载出现异常 */
	private final int MSG_DOWN_ERROR = -1;

	/** 下载取消 */
	private final int MSG_DOWN_CANCEL = 3;
	/**下载游戏名称*/
	private final String GAME_NAME = "temp";
	/** 程序包名 */
	String apkPkgName = "";

	/** 下载进度框 */
	ProgressDialog downloadProgress;

	/** 接收UI事件 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				if (msg.what == MSG_INIT_TOTAL_SIZE) {
					downloadProgress.setMax(gameInfo.getFileSize() * 1024);
				} else if (msg.what == MSG_DOWN_SIZE_CHANGE) {
					downloadProgress.setProgress(downLoadFileSize);
				} else if (msg.what == MSG_DOWN_FINISH) {
					downloadProgress.cancel();
					PackageManager pm = activity.getPackageManager();
					PackageInfo info = pm.getPackageArchiveInfo(Const.NoSDCardDIRECTORY + "/" + GAME_NAME + ".apk", PackageManager.GET_ACTIVITIES);
					if (info != null) {
						apkPkgName = info.packageName;
						PreferenceUtil.saveRomDownGame(activity, gameInfo, apkPkgName);
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.parse("file://" + Const.NoSDCardDIRECTORY + "/" + GAME_NAME + ".apk"), "application/vnd.android.package-archive");
						
						activity.startActivityForResult(intent, 1);
					} else {
						// 解析包错误,需要重新下载
						ToastUtil.show(activity, "解析包错误,请重新下载");
					}
				} else if (msg.what == -1) {
					String error = msg.getData().getString("下载错误");
					Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
				} else if (msg.what == 3) {
					Toast.makeText(activity, "下载已取消", Toast.LENGTH_SHORT).show();
				}
			}
			super.handleMessage(msg);
		}
	};

	/** 是否取消 */
	boolean flag = true;

	/** 下载文件大小 */
	int downLoadFileSize = 0;

	/** 游戏详情 */
	GameInfo gameInfo;

	Activity activity;

	EgameApplication application;

	String downloadFromer;

	public DownloadToRomBizBean(DialogAble dialogAble, GameInfo gameInfo, String downloadFromer) {
		this.activity = dialogAble.getDialogActivity();
		this.application = (EgameApplication) this.activity.getApplication();
		this.gameInfo = gameInfo;
		this.downloadFromer = downloadFromer;
	}

	public void init(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	/**
	 * 启动下载到手机内存的流程
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-6-20
	 */
	public void startDownloadToRom() {
		int fileSize = gameInfo.getFileSize() * 1024;
		if (fileSize >= 10485760) {
			Toast.makeText(activity, "游戏过大无法下载，请插入SD卡再试", Toast.LENGTH_SHORT).show();
			return;
		}
		if (fileSize >= this.getAvailMemory()) {
			Toast.makeText(activity, "内存不足，下载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		if (fileSize <= 0) {
			Toast.makeText(activity, "无法获知文件大小", Toast.LENGTH_SHORT).show();
			return;
		}

		downLoadFileSize = 0;
		flag = true;
		downloadProgress = new ProgressDialog(activity);
		downloadProgress.setTitle("下载中");
		downloadProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downloadProgress.setButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				downloadProgress.cancel();
				flag = false;
			}
		});
		downloadProgress.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				downloadToRom(Urls.getDownloadUrl(activity, gameInfo.getCpCode(), gameInfo.getServiceCode(), gameInfo.getChannelCode(),
						application.getUA(), CommonUtil.getUserId(activity), downloadFromer));
			}
		}).start();
	}

	/**
	 * 下载到手机内存
	 * 
	 * @param url
	 * @throws IOException
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-6-20
	 */
	public void downloadToRom(String url) {
		L.d("DownloadToRomBizBean", "下载地址是:" + url);
		URL myURL;
		try {
			myURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
			conn.setConnectTimeout(10000); // 请求超时
			conn.setReadTimeout(10000); // 文件读取超时
			conn.connect();
			InputStream is = conn.getInputStream();
			int fileSize = gameInfo.getFileSize() * 1024;
			if (conn.getResponseCode() == 200) {
				fileSize = conn.getContentLength();
				FileOutputStream fos = activity.openFileOutput(GAME_NAME + ".apk", Context.MODE_PRIVATE + Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
				// 把数据存入路径+文件名 
				byte buf[] = new byte[1024 * 5];
				sendMsg(MSG_INIT_TOTAL_SIZE);
				do {
					// 循环读取
					int numread = is.read(buf);
					if (numread == -1) {
						break;
					}
					fos.write(buf, 0, numread);
					downLoadFileSize += numread;
					sendMsg(MSG_DOWN_SIZE_CHANGE);// 更新进度条
				} while (flag);
				if (downLoadFileSize == fileSize) {
					sendMsg(MSG_DOWN_FINISH);// 通知下载完成
				} else {
					sendMsg(MSG_DOWN_CANCEL);
				}
			}
			try {
				if (is != null) {
					is.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMsg(MSG_DOWN_ERROR);
		}

	}

	/**
	 * 检查rom可用空间
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-7-1
	 */
	private long getAvailMemory() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 发送消息到handler
	 * 
	 * @param what
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-7-1
	 */
	private void sendMsg(int what) {
		Message msg = new Message();
		msg.what = what;
		handler.sendMessage(msg);
	}
}
