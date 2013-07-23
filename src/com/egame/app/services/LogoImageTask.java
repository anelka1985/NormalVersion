package com.egame.app.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.ImageUtils;

/**
 * 类说明：
 * 
 * @创建时间 2012-2-9 下午03:40:48
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class LogoImageTask extends AsyncTask<String, Integer, String> {
	private Context mContext;
	private long mBeginTime;
	private long mEndTime;
	private String mImageUrl;

	public LogoImageTask(Context context, long beginTime, long endTime,
			String imageUrl) {
		mContext = context;
		mBeginTime = beginTime;
		mEndTime = endTime;
		mImageUrl = imageUrl;
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			Bitmap bitmap = HttpConnect.getHttpBitmap(mImageUrl);
			if (bitmap != null) {
				String imageData = ImageUtils.encodeBase64(ImageUtils
						.convertBitmapToBinaryArray(bitmap));
				if (!TextUtils.isEmpty(imageData)) {
					DBService db = new DBService(mContext);
					db.open();
					db.insertImage(mBeginTime, mEndTime, imageData);
					db.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
