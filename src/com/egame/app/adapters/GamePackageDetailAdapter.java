package com.egame.app.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @desc 游戏包详情中的adapter
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2011-12-27
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GamePackageDetailAdapter extends BaseAdapter {
	Context ctx;
	List<Map<String, Object>> list;	
	public GamePackageDetailAdapter(Context context,List<Map<String, Object>> list){
		ctx=context;
		this.list=list;
	}
	public int getCount() {
		return list.size();
	}
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		return convertView;
	}

}
