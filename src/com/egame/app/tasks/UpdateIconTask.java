package com.egame.app.tasks;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.egame.app.uis.EgameWebActivity;
import com.egame.config.Urls;
import com.egame.utils.common.L;
import com.egame.utils.ui.ImageUtils;

/**
 * 类说明：
 * 
 * @创建时间 2012-3-1 下午03:25:39
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class UpdateIconTask extends AsyncTask<Bitmap, Integer, String> {
	private String mUserId;
	private EgameWebActivity mActivity;
	private String mCallback;

	public UpdateIconTask(EgameWebActivity activity, String userId, String callback) {
		mUserId = userId;
		mActivity = activity;
		mCallback = callback;
	}

	@Override
	protected String doInBackground(Bitmap... arg0) {
		String uploadUrl = Urls.getUpdateIconUrl(mActivity, mUserId);
		L.d("url = " + uploadUrl);
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(uploadUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setConnectTimeout(10 * 1000);
			httpURLConnection.setReadTimeout(120 * 1000);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("USERID", mUserId);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			httpURLConnection.connect();
			DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);

			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"headimg\"" + end);
			dos.writeBytes(end);
			dos.write(ImageUtils.Bitmap2Bytes(arg0[0]));
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();
			System.out.println("updateIconTask responseCode=" + httpURLConnection.getResponseCode());
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return "";
			}
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null)
				httpURLConnection.disconnect();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result == null) {
			mActivity.updateIconfail();
		} else {
			mActivity.updateIconSuccess(mCallback);
		}
	}
}
