package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.egame.beans.GameTopListBean;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;


public class GameTopIconTask extends AsyncTask<String, Integer, String>{
	
	List<GameTopListBean> list;

	BaseAdapter adapter;
	
	boolean isAlive = true;
	
	/**
	 * 下载List中的图片到IconBean中,并且通过adapter刷新ui
	 */
	public GameTopIconTask(List<GameTopListBean> list,BaseAdapter adapter){
		this.list = list;
		this.adapter = adapter;
	}
	
	/**
	 * 下载list中的图片到iconbean中,但是不刷新ui
	 */
	public GameTopIconTask(List<GameTopListBean> list){
		this.list = list;
	}
	
	
	@Override
	protected String doInBackground(String... arg0) {
		// 增加一个缓存List避免列表长度变化时遍历发生错误
		L.i("icon","start");
		List<GameTopListBean> cacheList = new ArrayList<GameTopListBean>();
		cacheList.addAll(list);
		Iterator<GameTopListBean> it = cacheList.iterator();
		while (it.hasNext()) {
			/*
			 * 避免重复下载,如果有新的线程,则停止老的线程
			 */
			if(!isAlive){
				L.i("icon","thread exit!");
				return "";
			}
			try {
				GameTopListBean bean = it.next();
				if (bean.getPicPath() != null) {
					BitmapDrawable drawable = HttpConnect.getHttpDrawable(bean.getPicPath());
					bean.setIcon(drawable);
					publishProgress(0);
				}else{
					L.i("icon","pic exist!");
				}
				if (bean.getPicPreview() != null) {
					BitmapDrawable drawable = HttpConnect.getHttpDrawable(bean.getPicPreview());
					bean.setPreview(drawable);
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
