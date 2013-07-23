package com.egame.beans;

import java.util.ArrayList;
import java.util.List;

import android.os.Debug;

import com.egame.utils.common.L;
import com.egame.utils.ui.IconBean;

/**
 * @desc 用来缓存列表对象
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-1-19
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ListBeanCache<T extends IconBean> {

	/**
	 * 数据列表
	 */
	List<T> list;

	/**
	 * 总记录数,未读取时默认为-1,读取后无数据为0
	 */
	int totalRecord;

	/**
	 * 当前条
	 */
	int index;

	/**
	 * 是否读取完毕
	 */
	boolean finish;

	public ListBeanCache() {
		this.index = 0;
		this.totalRecord = -1;
		this.finish = false;
		this.list = new ArrayList<T>();
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public void releaseIcon() {
		long time = System.currentTimeMillis();
		L.d("heap", "start free heap:" + Debug.getNativeHeapFreeSize() + " | " + "heap allocate:" + Debug.getNativeHeapAllocatedSize());
		for (T t : list) {
			try {
				IconBean bean = t;
				if (bean.getIcon() != null && !bean.getIcon().getBitmap().isRecycled()) {
					// L.d("heap", "icon recycled!");
					bean.getIcon().getBitmap().recycle();
					bean.setIcon(null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		L.d("heap", "end time:" + (System.currentTimeMillis() - time));
		L.d("heap", "end free heap:" + Debug.getNativeHeapFreeSize() + " | " + "heap allocate:" + Debug.getNativeHeapAllocatedSize());
		System.gc();
		L.d("heap", "end time after GC:" + (System.currentTimeMillis() - time));
		L.d("heap", "end heap after GC:" + Debug.getNativeHeapFreeSize() + " | " + "heap allocate:" + Debug.getNativeHeapAllocatedSize());
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

}
