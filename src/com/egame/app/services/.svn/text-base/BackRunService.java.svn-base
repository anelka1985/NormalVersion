package com.egame.app.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.egame.R;
import com.egame.app.receivers.OnBootReceiver;
import com.egame.beans.PushMsg;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.ImageUtils;
import com.egame.utils.ui.Utils;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-29 下午03:41:56
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class BackRunService extends Service implements Runnable {
	private final static int MAX_COUNT = 100;
	private final static long SLEEP = 2 * 60 * 60 * 1000L;
	private boolean mRun = false;
	private int mCount = 0;
	private Thread mThread;
	/** 新游戏上线 */
	private boolean mPushGame = true;

	/** 社区消息 */
	private boolean mPushMessage = true;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mRun = true;
		mCount = PreferenceUtil.getBackRunCount(getApplicationContext());
		boolean[] settings = PreferenceUtil.getBackRunSetting(getApplicationContext());
		if (settings.length == 2) {
			mPushGame = settings[0];
			mPushMessage = settings[1];
		}
		if (mPushGame || mPushMessage) {
			mThread = new Thread(this);
			mThread.start();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mRun = false;
		PreferenceUtil.setBackRunCount(getApplicationContext(), mCount);
		try {
			if (mThread != null)
				mThread.interrupt();
		} catch (Exception e) {
		}
	}

	private void work() {
		try {
			LoginDataStoreUtil.User user = LoginDataStoreUtil.fetchUser(getApplicationContext(), LoginDataStoreUtil.LOG_FILE_NAME);
			TelephonyManager telManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
			String imsi = telManager.getSubscriberId() + "";
			// String lastImsi = PreferenceUtil
			// .fetchCurrentImsi(getApplicationContext());
			// LoginUserBean user = PreferenceUtil
			// .fetchLastSuccessLoginUser(getApplicationContext());
			// int userId = user.getUserId();
			// if (!lastImsi.equals(imsi)) {
			// userId = 0;
			// }
			int[] ids = PreferenceUtil.getSignIds(getApplicationContext());

			StringBuffer buf = new StringBuffer();
			int upId = PreferenceUtil.getUpgradeId(getApplicationContext());
			List<GameBean> gameList = new ArrayList<GameBean>();
			try {
				DBService db = new DBService(getApplicationContext());
				db.open();
				Cursor cursor = db.getAtMostFiveGames(upId);
				if (cursor != null) {
					if (cursor.getCount() > 0) {
						cursor.moveToFirst();
						do {
							int id = cursor.getInt(cursor.getColumnIndex(DBService.KEY_ID));
							int versionCode = cursor.getInt(cursor.getColumnIndex(DBService.KEY_LOACL_VERSION));
							String packageName = cursor.getString(cursor.getColumnIndex(DBService.KEY_PACKAGENAME));
							int gameId = cursor.getInt(cursor.getColumnIndex(DBService.KEY_SERVICEID));
							if (gameId > 0 && !TextUtils.isEmpty(packageName)) {
								buf.append(gameId + "-" + packageName + ",");
								gameList.add(new GameBean(id, gameId, versionCode));
								if (id > upId) {
									upId = id;
								}
							}
						} while (cursor.moveToNext());
					}
					cursor.close();
				}
				db.close();
				PreferenceUtil.setUpgradeId(getApplicationContext(), upId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (buf.length() > 0) {
				buf.deleteCharAt(buf.length() - 1);
			}

			SharedPreferences share = getApplicationContext().getSharedPreferences(PreferenceUtil.SHARED_GAME, 0);
			String phone = share.getString(PreferenceUtil.KEY_PHONENUM_STRING, "00000000000");
			String s = HttpConnect.getHttpString(Urls.getBackRunUrl(BackRunService.this, mPushGame, mPushMessage, buf.toString(), ids[0], ids[1], ids[2],
					user.getUserId(), phone, PreferenceUtil.getLastUa(getApplicationContext()), Utils.getVga(getApplicationContext()),
					Build.VERSION.SDK, imsi));
			JSONObject obj = new JSONObject(s);
			if (mPushGame && obj.has("game")) {
				JSONObject game = obj.getJSONObject("game");
				if (game.has("newGame")) {
					JSONObject newGame = game.getJSONObject("newGame");
					showNotification(new PushMsg(PushMsg.PUSH_NEW_GAME, newGame.getString("msgTitle"), newGame.getString("msgContent"),
							newGame.getString("gameId")));
					ids[0] = Integer.parseInt(newGame.getString("newGameSign"));
				}
				if (game.has("newActivity")) {
					JSONObject newActivity = game.getJSONObject("newActivity");
					showNotification(new PushMsg(PushMsg.PUSH_NEW_ACTIVITY, newActivity.getString("msgTitle"), newActivity.getString("msgContent"),
							newActivity.getString("activityId")));
					ids[1] = Integer.parseInt(newActivity.getString("newActivitySign"));
				}
				try {
					if (game.has("gameUpgrade")) {
						JSONArray gameUpgrade = game.getJSONArray("gameUpgrade");
						boolean hasUpgrade = false;
						if (gameUpgrade.length() == gameList.size()) {
							DBService db = new DBService(getApplicationContext());
							db.open();
							for (int i = 0; i < gameUpgrade.length(); i++) {
								JSONObject json = gameUpgrade.getJSONObject(i);
								int gameId = Integer.parseInt(json.getString("gameId"));
								String version = json.getString("gameVersion");
								if (!TextUtils.isEmpty(version)) {
									int gameVersion = Integer.parseInt(version);
									GameBean gameBean = gameList.get(i);
									if (gameBean.gameId == gameId) {
										if (gameVersion > gameBean.currentVersion) {
											hasUpgrade = true;
											db.updateVersion("" + gameId, "" + gameVersion);

										}
									}
								}
							}
							db.close();
						}
						if (hasUpgrade) {
							showNotification(new PushMsg(PushMsg.PUSH_GAME_UPGRADE, "游戏升级提醒", "发现可以升级的游戏，去看看吧", ""));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (obj.has("updateSystemMessage")) {
				JSONObject sysInfo = obj.getJSONObject("updateSystemMessage");
				showNotification(new PushMsg(PushMsg.PUSH_SYSTEM, sysInfo.getString("msgTitle"), sysInfo.getString("msgContent"), sysInfo.getString("linkUrl")));
				ids[2] = Integer.parseInt(sysInfo.getString("newSystemSign"));
			}
			if (mPushMessage && obj.has("SNSmessage")) {
				JSONObject message = obj.getJSONObject("SNSmessage");
				showNotification(new PushMsg(PushMsg.PUSH_MESSAGE, message.getString("msgTitle"), message.getString("msgContent"), ""));
			}
			if (obj.has("updateClient")) {
				JSONObject updateClient = obj.getJSONObject("updateClient");
				showNotification(new PushMsg(PushMsg.PUSH_CLIENT_UPGRADE, updateClient.getString("msgTitle"), updateClient.getString("msgContent"), ""));
			}
			if (obj.has("updateBackPicMap")) {
				JSONObject image = obj.getJSONObject("updateBackPicMap");
				String imageUrl = image.getString("picPath");
				String beginTimeStr = image.getString("startTime");
				String endTimeStr = image.getString("endTime");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				long beginTime = format.parse(beginTimeStr).getTime();
				long endTime = format.parse(endTimeStr).getTime();
				DBService db = new DBService(getApplicationContext());
				db.open();
				boolean has = db.checkImage(beginTime, endTime);
				db.close();
				if (!has) {
					// new LogoImageTask(this, beginTime,
					// endTime, imageUrl).execute();
					// 获取图片
					try {
						Bitmap bitmap = HttpConnect.getHttpBitmap(imageUrl);
						if (bitmap != null) {
							String imageData = ImageUtils.encodeBase64(ImageUtils.convertBitmapToBinaryArray(bitmap));
							if (!TextUtils.isEmpty(imageData)) {
								DBService mDb = new DBService(getApplicationContext());
								mDb.open();
								mDb.insertImage(beginTime, endTime, imageData);
								mDb.close();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			PreferenceUtil.setSignIds(getApplicationContext(), ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showNotification(PushMsg msg) {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification();
		n.when = System.currentTimeMillis();
		n.flags = Notification.FLAG_AUTO_CANCEL;
		n.icon = R.drawable.icon;
		Intent i = new Intent(OnBootReceiver.PUSH_MSG);
		i.putExtra("type", msg.mType);
		i.putExtra("link", msg.mLink);
		PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), msg.mType, i, PendingIntent.FLAG_UPDATE_CURRENT);
		n.setLatestEventInfo(getApplicationContext(), msg.mTitle, msg.mBody, pIntent);
		nm.notify(msg.mType, n);
	}

	@Override
	public void run() {
		while (mRun) {
			mCount++;
			if (mCount > MAX_COUNT) {
				mRun = false;
				stopSelf();
				return;
			}
			work();
			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}
	}

	class GameBean {
		public int id;
		public int gameId;
		public int currentVersion;

		public GameBean(int id, int gameId, int currentVersion) {
			this.id = id;
			this.gameId = gameId;
			this.currentVersion = currentVersion;
		}
	}
}
