package com.egame.app.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.egame.app.uis.AlertActivity;
import com.egame.beans.DownloadListBean;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.ToastUtil;

public class DownloadService extends Service {
	private final static String LOG_TAG = "DownloadService";

	public List<DownThread> downThreadList;

	public DBService dbService;

	public String phoneNumber = "";

	public static int CONNECTION_RETRY_NUMBER = 10;// 连接重连次数

	public static final long CONNECTION_RETRY_TIME = 10 * 1000L;// 连接重连时间间隔

	public static final String TEMP_FILE_FIX = ".ct";// 下载未完成文件名，例如 愤怒的小鸟.apk

	// 未完成时为 愤怒的小鸟.apk.ct
	protected long finishSize;// 下载完成的文件大小

	BroadcastReceiver br = null;
	BroadcastReceiver br1 = null;
	private static long CONFIG_BLOCK_SIZE = 2 * 1024 * 1024L;// 每次请求的下载分块大小

	// private NotificationManager notificationManager; // 通知管理器

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		L.d(LOG_TAG, "DownloadService2 onCreate");
		this.dbService = new DBService(this);
		this.downThreadList = new ArrayList<DownThread>();
		dbService.open();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.egame.app.services.DownloadService");

		IntentFilter intentFilter1 = new IntentFilter();
		intentFilter1.addAction("android.net.conn.CONNECTIVITY_CHANGE");

		br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent intent) {
				if (intent.getExtras().getString("msg").equals("cancel")) {
					int gameid = Integer.parseInt(intent.getExtras().getString(
							"gameid"));
					L.d(LOG_TAG, "onReceive cancel gameid = " + gameid);
					cancelDownload(gameid);
				} else if (intent.getExtras().getString("msg").equals("stop")) {
					L.d(LOG_TAG, "onReceive:" + "stop");
					stopAll();
				} else if (intent.getExtras().getString("msg").equals("pause")) { // 下载暂停\
					int gameid = Integer.parseInt(intent.getExtras().getString(
							"gameid"));
					L.d(LOG_TAG, "onReceive pause,gameid = " + gameid);
					pauseDownload(gameid);
				} else if (intent.getExtras().getString("msg")
						.equals("stopservice")) {
					DownloadService.this.stopSelf();
				}

			}
		};
		this.registerReceiver(br, intentFilter);

		// add by turnoff phone net is Available
		BroadcastReceiver br1 = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager cm = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo nf = cm.getActiveNetworkInfo();
				Cursor cr = dbService.getGameByBreakTask();
				boolean exit = getSharedPreferences(PreferenceUtil.SHARED_GAME,
						0).getBoolean("downexit", false);
				if (nf != null && exit) {
					while (cr.moveToNext()) {
						DownloadListBean downloadBean = new DownloadListBean(cr);
						dbService.updateData(downloadBean.getServiceid() + "",
								DBService.KEY_STATE,
								DBService.DOWNSTATE_DOWNLOAD);
						CommonUtil.sendChangeBroadCast(DownloadService.this);
						DownThread downThread = new DownThread(
								downloadBean.getDownloadUrl(),
								Integer.parseInt(downloadBean.getServiceid()));
						downThreadList.add(downThread);
						dbService.updateTotalSize(downloadBean.getServiceid()
								+ "", downloadBean.getTotalsize() + "");
						new Thread(downThread).start();
					}
				}
			}
		};

		this.registerReceiver(br1, intentFilter1);
	}

	/**
	 * 取消下载
	 * 
	 * @param intent
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-18
	 */
	void cancelDownload(int gameid) {
		for (int i = 0; i < downThreadList.size(); i++) {
			DownThread downThread = downThreadList.get(i);
			L.d(LOG_TAG, "downThread:" + downThread.isAlive);
			if (downThread.getGameId() == gameid && downThread.isAlive) {
				downThread.downloadStop();
				downThreadList.remove(i);
			}
		}
	}

	/**
	 * 停止所有下载
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-18
	 */
	void stopAll() {
		L.d(LOG_TAG, "stop");
		for (int i = 0; i < downThreadList.size(); i++) {
			DownThread downThread = downThreadList.get(i);
			downThread.downloadPause();
		}
		downThreadList.clear();
	}

	/**
	 * 暂停下载
	 * 
	 * @param gameid
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-18
	 */
	void pauseDownload(int gameid) {
		for (int i = 0; i < downThreadList.size(); i++) {
			DownThread downThread = downThreadList.get(i);
			if (downThread.getGameId() == gameid) {
				downThread.downloadPause();
				downThreadList.remove(i);
			}
		}
	}

	@Override
	public void onDestroy() {
		// stopAll();
		dbService.close();
		super.onDestroy();
	}

	public static Bundle getBundle(Context context, int id, int filesize,
			String phoneNum, String cpid, String cpCode, String serviceCode,
			String gameName, String channelCode, String picPath, String ua,
			String userid, String downloadFromer, String sortName) {
		Bundle bundle = new Bundle();
		bundle.putString("url", Urls.getDownloadUrl(context, cpCode,
				serviceCode, channelCode, ua, userid, downloadFromer));
		bundle.putInt("id", id);
		bundle.putInt("filesize", filesize);
		bundle.putString("phoneNum", phoneNum);
		bundle.putString("cpid", cpid);
		bundle.putString("cpCode", cpCode);
		bundle.putString("serviceCode", serviceCode);
		bundle.putString("gameName", gameName);
		bundle.putString("channelCode", channelCode);
		bundle.putString("picPath", picPath);
		bundle.putString("sortName", sortName);
		return bundle;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		L.d(LOG_TAG, "onStartCommand");
		Cursor cursor = null;
		try {
			Bundle bundle = intent.getExtras();
			String url = bundle.getString("url") + "";// service被kill以后会抛出异常
			int gameid = bundle.getInt("id");
			long filesize = bundle.getInt("filesize") * 1024;
			String cpid = bundle.getString("cpid");
			String cpCode = bundle.getString("cpCode");
			String serviceCode = bundle.getString("serviceCode");
			String gameName = bundle.getString("gameName");
			String channelCode = bundle.getString("channelCode");
			String picPath = bundle.getString("picPath");
			String sortName = bundle.getString("sortName");
			phoneNumber = bundle.getString("phoneNum");
			L.d("phone num is:" + phoneNumber);
			cursor = dbService.getGameByServiceId(gameid + "");
			if (cursor == null || cursor.getCount() < 1) {// 记录不存在,添加记录重新下载
				L.d(LOG_TAG, "DownloadService2 记录不存在,添加记录重新下载");
				dbService.insert(gameid + "", cpid, cpCode, serviceCode,
						gameName, channelCode, picPath, sortName, url);
				DownThread downThread = new DownThread(url, gameid);
				dbService.updateTotalSize(gameid + "", filesize + "");
				dbService.updateDownSize(gameid + "", 0 + "");
				downThreadList.add(downThread);
				new Thread(downThread).start();
			} else {
				DownloadListBean downloadBean = new DownloadListBean(cursor);
				if (downloadBean.isDownError() || downloadBean.isPause()) {// 已中断或暂停,重新下载
					L.d(LOG_TAG, "DownloadService2 已中断,重新下载");
					dbService.updateData(gameid + "", DBService.KEY_STATE,
							DBService.DOWNSTATE_DOWNLOAD);
					CommonUtil.sendChangeBroadCast(DownloadService.this);
					DownThread downThread = new DownThread(url, gameid);
					downThreadList.add(downThread);
					dbService.updateTotalSize(gameid + "", filesize + "");
					new Thread(downThread).start();
				} else if (downloadBean.isUnInstalled()) { // 下载已卸载,先删除老的记录,再进行下载
					L.d(LOG_TAG, "DownloadService2 存在已卸载的记录,先删除老的条目,再重新下载");
					dbService.delGameByServiceId(gameid);
					dbService.insert(gameid + "", cpid, cpCode, serviceCode,
							gameName, channelCode, picPath, sortName, url);
					DownThread downThread = new DownThread(url, gameid);
					dbService.updateTotalSize(gameid + "", filesize + "");
					dbService.updateDownSize(gameid + "", 0 + "");
					downThreadList.add(downThread);
					new Thread(downThread).start();
				} else if (downloadBean.isUpdate()) {
					L.d(LOG_TAG, "DownloadService2 记录存在,进行升级");
					dbService.updateData(gameid + "", DBService.KEY_STATE,
							DBService.DOWNSTATE_DOWNLOAD);
					dbService.updateTotalSize(gameid + "", filesize + "");
					dbService.updateDownSize(gameid + "", 0 + "");
					CommonUtil.sendChangeBroadCast(DownloadService.this);
					// 属于更新下载
					DownThread downThread = new DownThread(url, gameid, true);
					downThreadList.add(downThread);
					dbService.updateTotalSize(gameid + "", filesize + "");
					new Thread(downThread).start();
				}
			}

			// 刷新下载列表ui
			CommonUtil.sendChangeBroadCast(DownloadService.this);
		} catch (Exception e) {
			e.printStackTrace();
			cursor = dbService.getAllGame();
			if (cursor.moveToFirst()) {
				do {
					if (cursor.getString(
							cursor.getColumnIndex(DBService.KEY_STATE)).equals(
							DBService.DOWNSTATE_DOWNLOAD)) {
						// 如果是正在下载中的任务
						int gameid = cursor.getInt(cursor
								.getColumnIndex(DBService.KEY_SERVICEID));
						// String servicename =
						// cursor.getString(cursor.getColumnIndex(DBService.KEY_GAMENAME));
						// int contentLength =
						// cursor.getInt(cursor.getColumnIndex(DBService.KEY_TOTALSIZE));
						// int downloadLength =
						// cursor.getInt(cursor.getColumnIndex(DBService.KEY_DOWNSIZE));
						dbService.updateData(gameid + "", DBService.KEY_STATE,
								DBService.DOWNSTATE_BREAK);
						CommonUtil.sendChangeBroadCast(DownloadService.this);
						dbService.updateDownloadHint(gameid + "", "下载异常中断");
					}
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public class DownThread implements Runnable {
		String url;

		int gameid;

		boolean isAlive = true;

		boolean isPause = false;

		long downloadLength = 0;

		long lastUpdateTime = 0;

		long contentLength = 100;

		Notification notification;

		int notifitotaltime = 0;

		int notifipertime = 10;

		int filesize = 0;// 文件总大小

		int blockSize = 0;// 每次请求的返回的分块大小

		boolean isUpdate = false;

		DownThread(String url, int gameid) {
			this.url = url;
			this.gameid = gameid;
		}

		DownThread(String url, int gameid, boolean isUpdate) {
			this.url = url;
			this.gameid = gameid;
			this.isUpdate = isUpdate;
		}

		boolean createFile(int name) {
			File dir = new File(Const.DIRECTORY);
			if (!dir.exists()) {
				try {
					dir.mkdirs();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						throw new IOException("创建文件失败！");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			File file = new File(Const.DIRECTORY + "/" + name + ".apk"
					+ DownloadService.TEMP_FILE_FIX);
			if (!file.exists()) {
				try {
					return file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					try {
						throw new IOException("创建文件失败！");
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
			return file.exists();
		}

		/**
		 * 
		 * 获取存储卡的剩余容量，单位为字节
		 * 
		 * @param filePath
		 * 
		 * @return availableSpare
		 */
		public long getAvailableStore(String filePath) {
			StatFs statFs = new StatFs(filePath);// 取得sdcard文件路径
			long blocSize = statFs.getBlockSize();// 获取block的SIZE
			long availaBlock = statFs.getAvailableBlocks();// 可使用的Block的数量
			long availableSpare = availaBlock * blocSize;
			return availableSpare;
		}

		@Override
		public synchronized void run() {
			try {
				Cursor cursor = dbService.getNotInstalledGame(gameid);
				if (cursor.moveToFirst()) {
					L.d("DownloadService2 run totalsize = "
							+ cursor.getColumnIndex(DBService.KEY_TOTALSIZE));
					filesize = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(DBService.KEY_TOTALSIZE)));
					if (filesize == 0) {
						try {
							// throw new GameSizeIsZeroException("异常终止-游戏大小异常");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					downloadLength = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(DBService.KEY_DOWNSIZE)));
					L.d("gameid=" + gameid + "downloadLength=" + downloadLength
							+ "filesize" + filesize);
				}
				cursor.close();
				createFile(this.gameid);
				do {
					if (!isAlive)
						break;
					long length = downloadLength;
					Tiled tiled = new Tiled(length, length
							+ DownloadService.CONFIG_BLOCK_SIZE - 1);
					download(url, tiled);
				} while (blockSize == DownloadService.CONFIG_BLOCK_SIZE
						&& isAlive);

				if (isPause) {
					dbService.updateData(gameid + "", DBService.KEY_STATE,
							DBService.DOWNSTATE_PAUSE);
					CommonUtil.sendChangeBroadCast(DownloadService.this);
				} else {
					// 临时文件改名
					L.d(LOG_TAG, "gameid=" + gameid
							+ "(DownloadTread)(runs)renameTo");
					File tempFile = new File(Const.DIRECTORY + "/"
							+ this.gameid + ".apk"
							+ DownloadService.TEMP_FILE_FIX);
					tempFile.renameTo(new File(Const.DIRECTORY + "/"
							+ this.gameid + ".apk"));
					L.d(LOG_TAG, "gameid=" + gameid
							+ "(DownloadTread)(runs)delete");
					if (isAlive) {// 下载完成,更新记录状态
						L.d(LOG_TAG, "download finish");
						dbService.updateData(gameid + "", DBService.KEY_STATE,
								DBService.DOWNSTATE_FINISH);
						CommonUtil.sendChangeBroadCast(DownloadService.this);
						dbService.updateDownSize(gameid + "", filesize + "");
						String packageName = "";
						try {
							// 判断当前acvitity是否是爱游戏客户端
							ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
							ComponentName comName = ((ActivityManager.RunningTaskInfo) mActivityManager
									.getRunningTasks(1).get(0)).topActivity;
							packageName = comName.getPackageName();
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (packageName.equals("com.egame")) {
							// 弹出是否立刻安装对话框
							Intent it = new Intent();
							it.putExtra("gameid", String.valueOf(gameid));
							it.setClass(getApplicationContext(),
									AlertActivity.class);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(it);
						}

						String path = Const.DIRECTORY + "/" + gameid + ".apk";
						String apkName = "";
						PackageManager pm = DownloadService.this
								.getPackageManager();// 获取APK包名
						PackageInfo info = pm.getPackageArchiveInfo(path,
								PackageManager.GET_ACTIVITIES);
						if (info != null) {
							apkName = info.packageName;
							L.d("install", "info1:" + apkName);
							dbService.updateData(gameid + "",
									DBService.KEY_PACKAGENAME, apkName);
						} else {
							L.d("install", "info is null");
							Toast.makeText(getApplicationContext(),
									"解析包错误,请重新下载", Toast.LENGTH_SHORT);
							dbService.updateData(gameid + "",
									DBService.KEY_STATE,
									DBService.DOWNSTATE_BREAK);
							CommonUtil
									.sendChangeBroadCast(DownloadService.this);
						}
					} else {// 用户自行退出,删除记录
						if (isUpdate) { // 属于更新,将原任务状态还原
							dbService.updateData(gameid + "",
									DBService.KEY_STATE,
									DBService.DOWNSTATE_INSTALLED);
							dbService.updateData(gameid + "",
									DBService.KEY_CANCEL_UPDATE_TIME,
									System.currentTimeMillis() + "");
							new File(Const.DIRECTORY + "/" + gameid + ".apk")
									.delete();
						} else { // 属于下载,直接删除任务
							dbService.delGameByServiceId(gameid);
							L.d(LOG_TAG, "download user cancel " + gameid);
							// 删除临时文件
							new File(Const.DIRECTORY + "/" + gameid + ".apk")
									.delete();
						}
						CommonUtil.sendChangeBroadCast(DownloadService.this);
					}
				}
				// 刷新下载列表ui
				CommonUtil.sendChangeBroadCast(DownloadService.this);
			} catch (Exception e1) {
				e1.printStackTrace();
				downloadExcepitonDel("下载异常中断");
			}
			// catch (FileNotFoundException e) {
			// e.printStackTrace();
			// downloadExcepitonDel("下载异常中断");
			// } catch (IOException e3) {
			// e3.printStackTrace();
			// downloadExcepitonDel("下载异常中断");
			// } catch (ClassNotFoundException e4) {
			// e4.printStackTrace();
			// downloadExcepitonDel("下载异常中断");
			// } catch (Exception e5) {
			// e5.printStackTrace();
			// downloadExcepitonDel("下载异常中断");
			// }
			finally {
				isAlive = false;
				downThreadList.remove(this);
			}
		}

		/**
		 * 下载任务中出现的异常处理，并发送通知
		 * 
		 * @param tip
		 *            通知时的提示信息
		 */
		void downloadExcepitonDel(String tip) {
			if (filesize == 0) {

			} else {
				if (isAlive) {
					dbService.updateDownloadHint(gameid + "", tip);
					dbService.updateData(gameid + "", DBService.KEY_STATE,
							DBService.DOWNSTATE_BREAK);
					CommonUtil.sendChangeBroadCast(DownloadService.this);
					if (downloadLength == filesize) {
						dbService.updateDownSize(gameid + "", 0 + "");
						downloadLength = 0;
					}
				} else {
					// if (isUpdate) { // 属于更新,将原任务状态还原
					// dbService.updateData(gameid + "", DBService.KEY_STATE,
					// DBService.DOWNSTATE_FINISH);
					// dbService.updateData(gameid + "",
					// DBService.KEY_CANCEL_UPDATE_TIME,
					// System.currentTimeMillis() + "");
					// } else { // 属于下载,直接删除任务
					// dbService.delGameByServiceId(gameid);
					// }
					// new File(Const.DIRECTORY + "/" + gameid + ".apk" +
					// DownloadService.TEMP_FILE_FIX).delete();
					// notificationManager.cancel(gameid);
					if (isPause) {
						dbService.updateData(gameid + "", DBService.KEY_STATE,
								DBService.DOWNSTATE_BREAK);
						CommonUtil.sendChangeBroadCast(DownloadService.this);
					}
				}
			}
		}

		public void downloadStop() {
			this.isAlive = false;
			L.d(LOG_TAG, "downloadStop gameid = " + gameid);
		}

		public void downloadPause() {
			this.isAlive = false;
			this.isPause = true;
			L.d(LOG_TAG, "downloadPause gameid = " + gameid);
		}

		public int getGameId() {
			return this.gameid;
		}

		private boolean download(String url, Tiled _tiled)
				throws MalformedURLException, FileNotFoundException,
				IOException, ClassNotFoundException, Exception {
			URL myURL = new URL(url);

			TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			String imsi = telManager.getSubscriberId() + "";
			L.d(LOG_TAG, phoneNumber + "|" + imsi);
			HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(20 * 1000);
				conn.setReadTimeout(20 * 1000);
				conn.setRequestProperty("x-up-calling-line-id", phoneNumber);
				conn.setRequestProperty("start", "" + _tiled.beginPos);
				conn.setRequestProperty("imsi", imsi);
				conn.setRequestProperty("Range", "bytes=" + _tiled.beginPos
						+ "-" + _tiled.endPos);
				conn.setRequestProperty("Accept-Encoding", "identity");
				conn.connect();
				L.d(LOG_TAG, conn.getResponseCode() + "");
				if (conn.getResponseCode() != 206) {
					InputStream is = conn.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					BufferedInputStream bis = new BufferedInputStream(is,
							Const.BUFFER_SIZE);
					int i = -1;
					byte data[] = new byte[Const.ARRAY_SIZE];
					while ((i = bis.read(data)) != -1) {
						out.write(data, 0, i);
					}
					String s = new String(out.toByteArray(), "utf-8");
					L.d(LOG_TAG, "http state error:" + conn.getContentLength());
					L.d(LOG_TAG, "http state error:" + s + "|" + phoneNumber
							+ "|" + imsi);
					throw new IOException();
				}
				// blockSize = conn.getContentLength() - 1;
				// if (blockSize < DownloadService.CONFIG_BLOCK_SIZE) {
				// conn = (HttpURLConnection) myURL.openConnection();
				// conn.setConnectTimeout(20 * 1000);
				// conn.setReadTimeout(20 * 1000);
				// conn.setRequestProperty("x-up-calling-line-id", phoneNumber);
				// conn.setRequestProperty("imsi", imsi);
				// conn.setRequestProperty("start", "" + _tiled.beginPos);
				// conn.setRequestProperty("Range", "bytes=" + _tiled.beginPos +
				// "-");
				// conn.connect();
				// if (conn.getResponseCode() != 206) {
				// L.d(LOG_TAG, "http state error");
				// // throw new SDCardIsFullException("创建下载文件失败！");
				// }
				// }
				if (Environment.getExternalStorageState().equals(
						android.os.Environment.MEDIA_MOUNTED)) {
					if (getAvailableStore(android.os.Environment
							.getExternalStorageDirectory().getAbsolutePath()) < contentLength) {
						ToastUtil.show(getApplicationContext(), "SD卡空间不足");
						throw new Exception("存储空间不足");
					}
				} else {
					// throw new SDCardNotFoundException("未发现SD卡。");
					// ToastUtil.show(getApplicationContext(), "未发现SD卡。");
				}

				RandomAccessFile oSavedFile = new RandomAccessFile(
						Const.DIRECTORY + "/" + this.gameid + ".apk"
								+ DownloadService.TEMP_FILE_FIX, "rw");
				oSavedFile.seek(_tiled.beginPos);
				InputStream is = conn.getInputStream();
				int nStartPos = 0;
				int nRead = 0;
				long nEndPos = conn.getContentLength();
				L.d("(DownloadTread)(runs)nEndPos = " + nEndPos);
				byte[] b = new byte[Const.BUFFER_SIZE];
				int totalSize = 0;
				// 读取网络文件,写入指定的文件中
				while (((nRead = is.read(b, 0, Const.BUFFER_SIZE)) > 0)
						&& nStartPos < nEndPos && isAlive) {
					totalSize += nRead;
					if (!isAlive) {
						if (oSavedFile != null) {
							oSavedFile.close();
						}
						if (is != null) {
							is.close();
						}
						if (conn != null) {
							conn.disconnect();
						}
						return false;
					}
					// L.d("read size is", "" + nRead);
					oSavedFile.write(b, 0, nRead);
					downloadLength = downloadLength + nRead;
					long time = System.currentTimeMillis();
					if (downloadLength <= filesize
							&& (time - lastUpdateTime) > 500) {
						// L.d("download", "downloadLength:" + downloadLength);
						dbService.updateDownSize(gameid + "", downloadLength
								+ "");
						lastUpdateTime = time;
					}
				}
				blockSize = totalSize;
				oSavedFile.close();

				if (is != null) {
					is.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			}
			return true;
		}
	}

	static class Tiled {
		long beginPos;// 起始下载字节数

		long endPos;// 结束下载字节数

		public Tiled(long _beginPos, long _endPos) {
			beginPos = _beginPos;
			endPos = _endPos;
		}
	}

}
