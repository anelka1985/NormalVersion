package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.IconBean;


public class GetListIconAsyncTask<T extends IconBean> extends AsyncTask<String, Integer, String>{
	
	List<T> list;

	BaseAdapter adapter;
	
	boolean isAlive = true;
	
	/**
	 * 下载List中的图片到IconBean中,并且通过adapter刷新ui
	 */
	public GetListIconAsyncTask(List<T> list,BaseAdapter adapter){
		this.list = list;
		this.adapter = adapter;
	}
	
	/**
	 * 下载list中的图片到iconbean中,但是不刷新ui
	 */
	public GetListIconAsyncTask(List<T> list){
		this.list = list;
	}
	
	
	@Override
	protected String doInBackground(String... arg0) {
		// 增加一个缓存List避免列表长度变化时遍历发生错误
		L.i("icon","start");
		List<T> cacheList = new ArrayList<T>();
		cacheList.addAll(list);
		Iterator<T> it = cacheList.iterator();
		while (it.hasNext()) {
			/*
			 * 避免重复下载,如果有新的线程,则停止老的线程
			 */
			if(!isAlive){
				L.i("icon","thread exit!");
				return "";
			}
			try {
				T bean = it.next();
				if (bean.getIcon() == null) {
					BitmapDrawable drawable = HttpConnect.getHttpDrawable(bean.getIconurl());
					bean.setIcon(drawable);
					L.i("icon","pic get:" + bean.getIconurl());
					publishProgress(0);
				}else{
					L.i("icon","pic exist!");
				}
			} catch (Exception e) {
				L.i("icon","no pic!");
			}
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}
	}
	
	public void stop(){
		isAlive = false;
	}
}
