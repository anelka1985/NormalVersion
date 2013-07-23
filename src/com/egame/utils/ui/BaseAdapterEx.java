package com.egame.utils.ui;

import java.util.List;

import android.app.Activity;
import android.widget.BaseAdapter;

/**
 * BaseAdapter的扩展,只需要重写getView方法.
 * @author zhouzhe@lenovo-cw.com
 *
 * @param <T>
 */
public abstract class BaseAdapterEx<T> extends BaseAdapter {
	
	protected List<T> list;
	
	protected Activity context;
	
	public BaseAdapterEx(List<T> list,Activity context) {
		this.list = list;
		this.context = context;
	}
	
	public int getCount() {
		return list.size();
	}

	public Object getItem(int index) {
		return list.get(index);
	}

	public long getItemId(int index) {
		return index;
	}

}
