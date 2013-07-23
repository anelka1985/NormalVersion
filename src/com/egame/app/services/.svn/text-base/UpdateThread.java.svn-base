package com.egame.app.services;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.webkit.URLUtil;

import com.egame.config.Const;
import com.egame.utils.common.L;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4
 * 
 * @Author Administrator
 * 
 * @timer 2012-3-16
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class UpdateThread implements Runnable {
	private static final String TAG = "update";
	private String url;
	private String tmpfile;
	boolean isAlive = true;
	UpdateService updateService;
	long downsize = 0;
	int totalsize = 3400000;
	long lastnotifysize = 0;

	public UpdateThread(UpdateService updateService) {
		L.d("update","start");
		this.updateService = updateService;
		url = updateService.getUrl();
		tmpfile = updateService.getTmpfile();
	}

	@Override
	public void run() {
		if (!URLUtil.isNetworkUrl(url)) {

		} else {
			// 取得URL
			URL myURL = null;
			HttpURLConnection conn = null;
			InputStream is = null;
			RandomAccessFile oSavedFile = null;
			try {
				myURL = new URL(url);
				conn = (HttpURLConnection) myURL.openConnection();
				if (conn != null) {
					File dir = new File(updateService.getPath() + "/egame/");
					if(!dir.exists()){
						dir.mkdir();
					}
					L.d("update","tmp file:" + tmpfile);
					File tmp = new File(tmpfile);
					long position = 0;
					if (tmp.exists()) {
						position = tmp.length();
					}
					L.d(TAG, "position:" + position);
					conn.setRequestProperty("Range", "bytes=" + position + "-");
					conn.setConnectTimeout(1000 * 20);
					conn.setReadTimeout(1000 * 20);
					conn.connect();
					int size = conn.getContentLength();
					totalsize = (int)(size + position);
					onTotalSizeChange();
					is = conn.getInputStream();
					byte buf[] = new byte[Const.BUFFER_SIZE];
					oSavedFile = new RandomAccessFile(tmpfile, "rw");
					oSavedFile.seek(position);
					downsize = position;
					onDownSizeChange();
					int nRead;
					while ((nRead = is.read(buf, 0, Const.BUFFER_SIZE)) > 0 && isAlive) {
						oSavedFile.write(buf, 0, nRead);
						downsize = downsize + nRead;
						onDownSizeChange();
					}
				}
				onFinish();
			} catch (Exception e) {
				e.printStackTrace();
				onError();
			} finally{
				try {
					if (is != null)
						is.close();
					if (conn != null)
						conn.disconnect();
					if(oSavedFile != null){
						oSavedFile.close();
					}
				} catch (Exception ex) {
					Log.e("getDataSource", "error: " + ex.getMessage(), ex);
				} 
			}
		}
	}

	/**
	 * 总数量发生变化
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-16
	 */
	void onTotalSizeChange() {
//		L.d(TAG, "total:" + totalsize);
	}

	/**
	 * 下载进度发生变化
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-16
	 */
	void onDownSizeChange() {
		if(downsize - lastnotifysize > 80000){
			L.d(TAG, "down size:" + downsize);
			String percentage = (downsize * 100 / totalsize) + "";
			updateService.notifyMessage("爱游戏更新中..." + percentage + "%");
			lastnotifysize = downsize;
		}
		
	}

	/**
	 * 出现异常
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-16
	 */
	void onError() {
		updateService.onError();
	}

	/**
	 * 结束
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-16
	 */
	void onFinish() {
		L.d(TAG, "finish");
		updateService.onFinish();
	}

}
