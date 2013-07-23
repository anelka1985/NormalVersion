package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.IconBean;

public class MoreUserIconTask<T extends IconBean> extends
		AsyncTask<String, Integer, String> {
	List<T> list;
	GamesDetailActivity context;

	boolean isAlive = true;

	public MoreUserIconTask(List<T> list, GamesDetailActivity ctx) {
		this.list = list;
		this.context = ctx;
	}

	@Override
	protected String doInBackground(String... arg0) {
		List<T> cacheList = new ArrayList<T>();
		cacheList.addAll(list);
		Iterator<T> it = cacheList.iterator();
		while (it.hasNext()) {
			if (!isAlive) {
				return "";
			}
			try {
				IconBean bean = (IconBean) it.next();
				if (bean.getIcon() == null) {
					BitmapDrawable drawable = HttpConnect.getHttpDrawable(bean
							.getIconurl());
					bean.setIcon(drawable);
					publishProgress(0);
				} else {
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		context.setMoreUser();
	}

	public void stop() {
		isAlive = false;
	}
}
