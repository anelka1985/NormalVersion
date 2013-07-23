package com.egame.utils.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 类说明：读取缓存图片和保存图片
 * 
 * @创建时间 2012-1-10 下午04:44:36
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class ImageCacheUtils {
	public static final String EGAME_CACHE_ROOT_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/egame/cache/";

	private static final int JPG_FILE_FORMAT = 1;

	private static final int PNG_FILE_FORMAT = 2;

	/**
	 * 根据url从缓存加载图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapFormCache(String url) {
		try {
			if (!TextUtils.isEmpty(url)) {
				if (url.indexOf("jietu") > 0)
					return null;
				String tempUrl = url.replace(".jpg", ".temp");
				StringBuffer buf = new StringBuffer(tempUrl);
				if (tempUrl.startsWith("http://")) {
					buf.delete(0, "http://".length());
				}
				return decodeFile(EGAME_CACHE_ROOT_PATH + buf.toString());
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将制定url和图片添加到缓存
	 * 
	 * @param url
	 * @return
	 */
	public static void saveBitmapToCache(String url, Bitmap bitmap) {
		try {
			if (!TextUtils.isEmpty(url)) {
				if (url.indexOf("jietu") > 0)
					return;
				String tempUrl = url.replace(".jpg", ".temp");
				String[] info = getDirAndFileName(tempUrl);
				if (info != null) {
					saveBitmap(bitmap, info[0], info[1]);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * 根据指定的文件路径加载图片
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static Bitmap decodeFile(String path) throws Exception {
//		L.d("time", "" + path);
		File file = new File(path);
		if (file.exists()) {
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = 1;
			o2.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(path, o2);
		}
		return null;
	}

	/**
	 * 得到文件类型
	 * 
	 * @param filename
	 * @return
	 */
	private static int getFileFormat(String filename) {
		if (filename.toUpperCase().endsWith(".PNG")) {
			return PNG_FILE_FORMAT;
		}
		return JPG_FILE_FORMAT;
	}

	/**
	 * 保存图片到指定位置
	 * 
	 * @param bitmap
	 * @param path
	 * @param filename
	 */
	private static void saveBitmap(Bitmap bitmap, String path, String filename) {
		FileOutputStream fOut = null;
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File f = new File(path, filename);
			f.createNewFile();
			fOut = new FileOutputStream(f);
			int format = getFileFormat(filename);
			if (format == JPG_FILE_FORMAT) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			} else if (format == PNG_FILE_FORMAT) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			}
			fOut.flush();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			try {
				if (fOut != null) {
					fOut.close();
					fOut = null;
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 根据文件路径得到文件目录和文件名
	 * 
	 * @param url
	 * @return String[0]-目录，String[1]-文件名
	 */
	private static String[] getDirAndFileName(String url) {
		try {
			StringBuffer buf = new StringBuffer(url);
			if (url.startsWith("http://")) {
				buf.delete(0, "http://".length());
			}
			String path = EGAME_CACHE_ROOT_PATH + buf.toString();
			int index = path.lastIndexOf("/");
			if (index > 0) {
				String dir = path.substring(0, index);
				String fileName = path.substring(index + 1);
				return new String[] { dir, fileName };
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}
}
