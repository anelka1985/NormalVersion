package com.egame.app.services;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.egame.app.FlashScreenActivity;
import com.egame.config.Const;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;

public class DBService {

	/**
	 * 一些规则： 已经安装列表:state=3 install=1 升级中列表:state!=3 install=1 下载列表:state!=3
	 * install!=
	 */
	private static final String LOG_TAG = "DBAdapter";

	/**
	 * 下载状态 0 完成未安装 1 下载中 2 中断 3 已安装 4 暂停
	 */
	public static final String DOWNSTATE_FINISH = "0";

	public static final String DOWNSTATE_DOWNLOAD = "1";

	public static final String DOWNSTATE_BREAK = "2";

	public static final String DOWNSTATE_INSTALLED = "3";

	public static final String DOWNSTATE_PAUSE = "4";

	public static final String INSTALL_INSTALL = "1";

	public static final String INSTALL_NOTINSTALL = "0";

	public static final String INSTALL_UNINSTALL = "-1";

	private static final String DATABASE_NAME = "egame";// 数据库名

	public static final String DATABASE_TABLE = "download";// 表名

	public static final String KEY_ID = "_id";// 主键

	public static final String KEY_CPID = "cpid";

	public static final String KEY_CPCODE = "cpcode";

	public static final String KEY_SERVICEID = "serviceid";

	public static final String KEY_SERVICECODE = "servicecode";

	public static final String KEY_GAMENAME = "gamename";

	public static final String KEY_PACKAGENAME = "packagename";

	public static final String KEY_DOWNSIZE = "downsize";

	public static final String KEY_TOTALSIZE = "totalsize";

	public static final String KEY_CHANNELID = "channelid";

	public static final String KEY_STATE_HINT = "hint";

	public static final String KEY_PICPATH = "picpath";

	public static final String KEY_STATE = "state";// 数据库中记录的下载状态 下载状态 0 完成 1

	public static final String KEY_INSTALL = "install";

	public static final String KEY_VERSION = "version";

	public static final String KEY_LOACL_VERSION = "localversion";

	public static final String KEY_SORT_NAME = "sortname"; // 分类名称

	public static final String KEY_CANCEL_UPDATE_TIME = "cancelupdatetime";// 取消升级时间

	public static final String KEY_DOWNLOAD_URL = "downloadurl";

	// 下载中 2 中断 3 已安装
	private static final int DATABASE_VERSION = 13;

	private final Context context;

	private DatabaseHelper DBHelper;

	private SQLiteDatabase db;

	private static final String DATABASE_CREATE = "create table if not exists download "
			+ "(_id integer primary key autoincrement, "
			+ "cpid text, "
			+ "cpcode text, serviceid text, "
			+ "servicecode text, "
			+ "gamename text, "
			+ "packagename text,"
			+ "downsize text, "
			+ "totalsize text,"
			+ "channelid text,"
			+ "hint text,"
			+ "state text,"
			+ "version integer,"
			+ "localversion integer,"
			+ "sortname text,"
			+ "cancelupdatetime text,"
			+ "install text,"
			+ "picpath text ," + "downloadurl text " + " );";

	// 保存节日开机图片
	private static final String IMAGE_TABLE = "logo_image";
	private static final String IMAGE_DATA = "imagedata";
	private static final String BEGIN_TIME = "begintime";
	private static final String END_TIME = "endtime";

	private static final String IMAGE_CREATE = "create table if not exists "
			+ IMAGE_TABLE + "(" + KEY_ID
			+ " integer primary key autoincrement, " + IMAGE_DATA + " text, "
			+ BEGIN_TIME + " bigint, " + END_TIME + " bigint );";

	private static final String[] colums = new String[] { KEY_ID, KEY_CPID,
			KEY_CPCODE, KEY_SERVICEID, KEY_SERVICECODE, KEY_GAMENAME,
			KEY_PACKAGENAME, KEY_DOWNSIZE, KEY_TOTALSIZE, KEY_STATE,
			KEY_CHANNELID, KEY_STATE_HINT, KEY_PICPATH, KEY_INSTALL,
			KEY_VERSION, KEY_LOACL_VERSION, KEY_SORT_NAME,
			KEY_CANCEL_UPDATE_TIME, KEY_DOWNLOAD_URL };

	public DBService(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private Context myctx = null;

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			myctx = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//删除之前的表
			if (PreferenceUtil.isFrist(myctx)) {
				db.execSQL("DROP TABLE IF EXISTS download");
			}
			db.execSQL(DATABASE_CREATE);
			db.execSQL(IMAGE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			L.d(LOG_TAG, "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");
			if (oldVersion == 6) {
				try {
					db.execSQL("ALTER TABLE download add column version integer;");
					db.execSQL("ALTER TABLE download add column localversion integer;");
					db.execSQL("ALTER TABLE download add column install text;");
					db.execSQL("ALTER TABLE download add column picpath text;");
					db.execSQL("ALTER TABLE download add column sortname text;");
					db.execSQL("ALTER TABLE download add column cancelupdatetime text;");
					db.execSQL("UPDATE download set version=1,localversion=1000,install='1' where state='3'");
					db.execSQL("DELETE from download where state!='3'");
				} catch (Exception e) {
					e.printStackTrace();
				}
				db.execSQL(IMAGE_CREATE);
			} else if (oldVersion == 8) {
				db.execSQL("ALTER TABLE download add column version integer;");
				db.execSQL("ALTER TABLE download add column localversion integer;");
				db.execSQL("ALTER TABLE download add column install text;");
				db.execSQL("ALTER TABLE download add column sortname text;");
				db.execSQL("ALTER TABLE download add column cancelupdatetime text;");
				db.execSQL("UPDATE download set version=1,localversion=1000,install='1' where state='3'");
				db.execSQL("DELETE from download where state!='3'");
				db.execSQL(IMAGE_CREATE);
			} else {
				db.execSQL("DROP TABLE IF EXISTS download");
				onCreate(db);
			}
		}
	}

	/**
	 * 打开数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DBService open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void dropDataBase() {
		db.execSQL("DROP TABLE IF EXISTS download");
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		DBHelper.close();
	}

	/**
	 * 根据serviceid查找游戏
	 * 
	 * @param serviceid
	 * @return
	 */
	public Cursor getGameByServiceId(String serviceid) {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_SERVICEID
				+ "=\"" + serviceid + "\"", null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	/**
	 * 根据serviceid删除相应scard中的apk文件
	 * 
	 * @param serviceid
	 */
	public void delScardapkfileByServiceId(int serviceid) {
		try {
			new File(Const.DIRECTORY + "/" + serviceid + ".apk").delete();
			L.d("DBService", "删除临时apk文件成功");
		} catch (Exception e) {
		}
	}

	/**
	 * 获取 已录入包名的游戏(已下载完毕并且已点击过安装)
	 * 
	 * @return
	 */
	public Cursor getGameHasPackageName() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_PACKAGENAME
				+ "!=\"" + "1" + "\"", null, null, null, null, null);
		return cursor;
	}

	// /**
	// * 获得已安装游戏
	// *
	// * @return
	// */
	// public Cursor getGameInstalled() {
	// Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
	// KEY_CPID, KEY_CPCODE, KEY_SERVICEID, KEY_SERVICECODE, KEY_GAMENAME,
	// KEY_PACKAGENAME, KEY_STATE, KEY_CHANNELID, KEY_PICPATH }, KEY_STATE +
	// "=\"" + "3" + "\"", null, null, null, null, null);
	// return cursor;
	// }

	/**
	 * 获得已安装游戏
	 * 
	 * @return
	 */
	public Cursor getGameInstalledTest() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_STATE
				+ "=\"" + "3" + "\" and " + KEY_INSTALL + "=\"" + "1" + "\"",
				null, null, null, null, null);
		return cursor;
	}

	/**
	 * 获得未安装游戏
	 * 
	 * @return
	 */
	public Cursor getNotInstalledGame() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_STATE
				+ "!=\"" + "3" + "\" and " + KEY_INSTALL + "=\"" + "0" + "\"",
				null, null, null, KEY_ID + " DESC ", null);
		return cursor;
	}

	/**
	 * 获得升级中的游戏
	 * 
	 * @return
	 */
	public Cursor getUpdateGame() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums,
				"version>localversion and (" + System.currentTimeMillis()
						+ " - cancelupdatetime) > 604800000" + " and "
						+ KEY_INSTALL + "=\"" + "1" + "\"", null, null, null,
				KEY_ID + " DESC ", null);
		return cursor;
	}

	/**
	 * 获得需要升级的游戏
	 * 
	 * @return
	 */
	public Cursor getNeedUpdateGame() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums,
				"version>localversion and (" + System.currentTimeMillis()
						+ " - cancelupdatetime) > 604800000" + " and "
						+ KEY_INSTALL + "=\"" + "1" + "\"" + " and "
						+ KEY_STATE + "!=\"" + "0" + "\"" + " and " + KEY_STATE
						+ "!=\"" + "1" + "\"", null, null, null, KEY_ID
						+ " DESC ", null);

		return cursor;
	}

	/**
	 * 根据包名获取指定的下载记录
	 * 
	 * @param packageName
	 * @return
	 */
	public Cursor getGameByPackageName(String packageName) {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_PACKAGENAME
				+ "=\"" + packageName + "\"", null, null, null, null, null);
		return cursor;
	}

	/**
	 * 通过serviceid获得未安装游戏
	 * 
	 * @param serviceid
	 * @return
	 */
	public Cursor getNotInstalledGame(int serviceid) {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_STATE
				+ "!=\"" + "3" + "\" and " + KEY_SERVICEID + "=\"" + serviceid
				+ "\"", null, null, null, null, null);
		return cursor;
	}

	/**
	 * 通过id获得大于它的至多5款游戏信息
	 * 
	 * @param serviceid
	 * @return
	 */
	public Cursor getAtMostFiveGames(int id) {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_ID + ">"
				+ id, null, null, null, null, null);
		return cursor;
	}

	/**
	 * 增加记录
	 */
	public long insert(String gameid, String cpid, String cpCode,
			String serviceCode, String gameName, String channelCode,
			String picPath, String sortName, String downloadUrl) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_SERVICEID, gameid);
		cv.put(KEY_CPID, cpid);
		cv.put(KEY_CPCODE, cpCode);
		cv.put(KEY_SERVICECODE, serviceCode);
		cv.put(KEY_GAMENAME, gameName);
		cv.put(KEY_CHANNELID, channelCode);
		cv.put(KEY_PACKAGENAME, "1");
		cv.put(KEY_DOWNSIZE, "0");
		cv.put(KEY_TOTALSIZE, "100");
		cv.put(KEY_STATE, "1");
		cv.put(KEY_PICPATH, picPath);
		cv.put(KEY_VERSION, 0);
		cv.put(KEY_LOACL_VERSION, 0);
		cv.put(KEY_INSTALL, "0");
		cv.put(KEY_SORT_NAME, sortName);
		cv.put(KEY_CANCEL_UPDATE_TIME, "0");
		cv.put(KEY_DOWNLOAD_URL, downloadUrl);
		return db.insert(DATABASE_TABLE, null, cv);
	}

	/**
	 * 
	 */
	public long insert(String gameid, String cpid, String cpCode,
			String serviceCode, String gameName, String channelCode,
			String picPath, String sortName) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_SERVICEID, gameid);
		cv.put(KEY_CPID, cpid);
		cv.put(KEY_CPCODE, cpCode);
		cv.put(KEY_SERVICECODE, serviceCode);
		cv.put(KEY_GAMENAME, gameName);
		cv.put(KEY_CHANNELID, channelCode);
		cv.put(KEY_PACKAGENAME, "1");
		cv.put(KEY_DOWNSIZE, "0");
		cv.put(KEY_TOTALSIZE, "100");
		cv.put(KEY_STATE, "1");
		cv.put(KEY_PICPATH, picPath);
		cv.put(KEY_VERSION, 0);
		cv.put(KEY_LOACL_VERSION, 0);
		cv.put(KEY_INSTALL, "0");
		cv.put(KEY_SORT_NAME, sortName);
		cv.put(KEY_CANCEL_UPDATE_TIME, "0");
		cv.put(KEY_DOWNLOAD_URL, "");
		return db.insert(DATABASE_TABLE, null, cv);
	}

	/**
	 * 更新游戏版本信息
	 * 
	 * @param serviceid
	 * @param downsize
	 */
	public void updateVersion(String serviceid, String version) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_VERSION, version);
		db.update(DATABASE_TABLE, cv, KEY_SERVICEID + "='" + serviceid + "'",
				null);
	}

	/**
	 * 更新下载大小记录
	 * 
	 * @param serviceid
	 * @param downsize
	 */
	public void updateDownSize(String serviceid, String downsize) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_DOWNSIZE, downsize);
		db.update(DATABASE_TABLE, cv, KEY_SERVICEID + "='" + serviceid + "'",
				null);
	}

	/**
	 * 更新总大小记录
	 * 
	 * @param serviceid
	 * @param totalsize
	 */
	public void updateTotalSize(String serviceid, String totalsize) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_TOTALSIZE, totalsize);
		db.update(DATABASE_TABLE, cv, KEY_SERVICEID + "='" + serviceid + "'",
				null);
	}

	/**
	 * 更新下载中的提示信息
	 * 
	 * @param serviceid
	 * @param hint
	 */
	public void updateDownloadHint(String serviceid, String hint) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_STATE_HINT, hint);
		db.update(DATABASE_TABLE, cv, KEY_SERVICEID + "='" + serviceid + "'",
				null);
	}

	/**
	 * 更新记录
	 * 
	 * @param serviceid
	 * @param key
	 * @param value
	 */
	public void updateData(String serviceid, String key, String value) {
		ContentValues cv = new ContentValues();
		cv.put(key, value);
		L.d(LOG_TAG, "updateData key = " + key + " , value = " + value);
		db.update(DATABASE_TABLE, cv, KEY_SERVICEID + "='" + serviceid + "'",
				null);
	}

	/**
	 * 更新记录
	 */
	public void updateDownloadingData() {
		ContentValues cv = new ContentValues();
		cv.put(KEY_STATE, DOWNSTATE_BREAK);
		db.update(DATABASE_TABLE, cv, KEY_STATE + "=\"" + DOWNSTATE_DOWNLOAD
				+ "\"", null);
	}

	/**
	 * 获取所有游戏
	 * 
	 * @return
	 */
	public Cursor getAllGame() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CPID,
				KEY_CPCODE, KEY_SERVICEID, KEY_SERVICECODE, KEY_GAMENAME,
				KEY_PACKAGENAME, KEY_DOWNSIZE, KEY_TOTALSIZE, KEY_CHANNELID,
				KEY_STATE, KEY_PICPATH }, null, null, null, null, "_id DESC");
	}

	/**
	 * 删除指定游戏记录
	 * 
	 * @param serviceid
	 */
	public void delGameByServiceId(int serviceid) {
		L.d(LOG_TAG, "删除这条记录 serviceid = " + serviceid);
		db.delete(DATABASE_TABLE, KEY_SERVICEID + "='" + serviceid + "'", null);
	}

	/**
	 * 得到当前可用的图片
	 * 
	 * @return
	 */
	public String getImage() {
		String imageData = "";
		long nowTime = System.currentTimeMillis();
		Cursor cursor = db.query(true, IMAGE_TABLE,
				new String[] { IMAGE_DATA }, BEGIN_TIME + " <= " + nowTime
						+ " AND " + END_TIME + " > " + nowTime, null, null,
				null, null, null);
		if (cursor != null) {
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				imageData = cursor.getString(cursor.getColumnIndex(IMAGE_DATA));
			}
			cursor.close();
		}
		return imageData;
	}

	/**
	 * 检查图片是否已经存在
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public boolean checkImage(long beginTime, long endTime) {
		boolean result = false;
		Cursor cursor = db.query(true, IMAGE_TABLE, new String[] { KEY_ID },
				BEGIN_TIME + " = " + beginTime + " AND " + END_TIME + " = "
						+ endTime, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				result = true;
			}
			cursor.close();
		}
		return result;
	}

	/**
	 * 将未来时间段的图片数据写入数据库，并且清空之前的数据
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param imageData
	 * @return
	 */
	public long insertImage(long beginTime, long endTime, String imageData) {
		db.delete(IMAGE_TABLE, null, null);
		ContentValues cv = new ContentValues();
		cv.put(BEGIN_TIME, beginTime);
		cv.put(END_TIME, endTime);
		cv.put(IMAGE_DATA, imageData);
		return db.insert(IMAGE_TABLE, null, cv);
	}

	/**
	 * 获取下载中断的任务
	 * 
	 * @return
	 */
	public Cursor getGameByBreakTask() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_STATE
				+ "=\"" + DOWNSTATE_BREAK + "\"", null, null, null, null, null);
		return cursor;
	}

	/**
	 * 获取下载中的任务
	 */
	public Cursor getGameByRunningTask() {
		Cursor cursor = db.query(true, DATABASE_TABLE, colums, KEY_STATE
				+ "=\"" + DOWNSTATE_DOWNLOAD + "\"", null, null, null, null,
				null);
		return cursor;
	}
}