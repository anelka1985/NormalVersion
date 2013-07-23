package com.egame.app.tasks;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.egame.R;
import com.egame.app.widgets.ProgressListener;
import com.egame.utils.common.HttpConnect;

public class GetImageTask extends AsyncTask<String, Integer, Drawable> {

	private ProgressListener mListener;
	private String mUrl;
	private ImageView mView;

	public GetImageTask(ProgressListener listener, String url, ImageView view) {
		mListener = listener;
		mUrl = url;
		mView = view;
	}

	@Override
	protected Drawable doInBackground(String... arg0) {
		BitmapDrawable bitmap = null;

		if (mUrl.indexOf("portrait") > 0) {
			try {
				String mUrlBig = mUrl.replace("portrait", "portrait_original");
				System.out.println("url:" + mUrlBig);
				bitmap = HttpConnect.getHttpDrawable(mUrlBig);
			} catch (Exception e) {
				Log.d("icon", "no big pic!");
			}
		} else if (mUrl.indexOf("pic1.jpg") > 0) {
			try {
				// String mUrlBig = mUrl.replace("pic1.jpg", "jietu1.jpg");
				// System.out.println("url:"+mUrlBig);
				// bitmap = HttpConnect.getHttpDrawable(mUrlBig);
				Bitmap image = HttpConnect.getHttpBitmap(mUrl);
				Matrix matrix = new Matrix();
				matrix.postScale(3, 3);
				Bitmap resizeBmp = Bitmap.createBitmap(image, 0, 0,
						image.getWidth(), image.getHeight(), matrix, true);
				bitmap = new BitmapDrawable(resizeBmp);
//				bitmap = HttpConnect.getHttpDrawable(mUrl);
			} catch (Exception e) {
				Log.d("icon", "no big pic!");
			}
		}
		if (bitmap == null) {
			try {
				bitmap = HttpConnect.getHttpDrawable(mUrl);
			} catch (Exception e) {
				Log.d("icon", "no pic!");
			}
		}
		return bitmap;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mListener.showProgress(R.string.egame_loadData);
	}

	@Override
	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		mListener.hideProgress();
		if (result != null) {
			mListener.setTaskResult(true, 0);
			if (mView != null) {
				mView.setBackgroundDrawable(result);
				// mView.seti
			}
		} else {
			mListener.setTaskResult(false, R.string.egame_net_error);
		}
	}
}
