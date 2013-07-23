package com.egame.app.tasks;

import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.egame.app.adapters.AdGallaryAdapter;
import com.egame.beans.AdBean;
import com.egame.beans.AdPageBean;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.FlingGallery;

public class GetAdIconAsyncTask extends AsyncTask<String, Integer, String> {

	List<AdPageBean> list;

	AdGallaryAdapter adapter;

	FlingGallery gallery;

	boolean isAlive = true;

	/**
	 * 下载List中的图片到IconBean中,并且通过adapter刷新ui
	 */
	public GetAdIconAsyncTask(List<AdPageBean> list, FlingGallery gallery,
			AdGallaryAdapter adapter) {
		this.list = list;
		this.gallery = gallery;
		this.adapter = adapter;
	}

	/**
	 * 下载list中的图片到iconbean中,但是不刷新ui
	 */
	public GetAdIconAsyncTask(List<AdPageBean> list) {
		this.list = list;
	}

	@Override
	protected String doInBackground(String... arg0) {
		Iterator<AdPageBean> it = list.iterator();
		L.i("icon", list.size() + "");
		while (it.hasNext() && isAlive) {
			try {
				AdPageBean bean = (AdPageBean) it.next();
				for (AdBean ad : bean.getAdList()) {
					if (!isAlive) {
						return null;
					}
					if (ad.getIcon() == null) {
						try {
							BitmapDrawable drawable = HttpConnect
									.getHttpDrawable(ad.getIconurl());
							ad.setIcon(drawable);
//							Log.i("icon", "pic get:" + ad.getIconurl());
							publishProgress(0);
						} catch (OutOfMemoryError oom) {
							oom.printStackTrace();
						}

					} else {
						Log.i("icon", "pic exist!");
					}
				}
			} catch (Exception e) {
				Log.d("icon", "no pic!");
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (adapter != null && gallery != null) {
			// adapter.notifyDataSetChanged();
			gallery.setAdapter(adapter);
		} else {
			L.d("icon", "adapter is null");
		}
	}

	public void stop() {
		isAlive = false;
	}
}
