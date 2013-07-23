package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.IconBean;

/**
 * @desc 读取大家还喜欢中游戏图片的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-15
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class MoreLikeIconTask<T extends IconBean> extends
		AsyncTask<String, Integer, String> {
	List<T> list;
	GamesDetailActivity context;

	boolean isAlive = true;

	public MoreLikeIconTask(List<T> list, GamesDetailActivity ctx) {
		this.list = list;
		this.context = ctx;
	}

	@Override
	protected String doInBackground(String... arg0) {
		// 增加一个缓存List避免列表长度变化时遍历发生错误
		L.d("icon", "start");
		List<T> cacheList = new ArrayList<T>();
		cacheList.addAll(list);
		Iterator<T> it = cacheList.iterator();
		while (it.hasNext()) {
			/*
			 * 避免重复下载,如果有新的线程,则停止老的线程
			 */
			if (!isAlive) {
				L.d("icon", "thread exit!");
				return "";
			}
			try {
				IconBean bean = (IconBean) it.next();
				if (bean.getIcon() == null) {
					BitmapDrawable drawable = HttpConnect.getHttpDrawable(bean
							.getIconurl());
					bean.setIcon(drawable);
					L.d("icon", "pic get:" + bean.getIconurl());
					publishProgress(0);
				} else {
					L.d("icon", "pic exist!");
				}
			} catch (Exception e) {
				L.d("icon", "no pic!");
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		context.setMoreGame();
	}

	public void stop() {
		isAlive = false;
	}
}
