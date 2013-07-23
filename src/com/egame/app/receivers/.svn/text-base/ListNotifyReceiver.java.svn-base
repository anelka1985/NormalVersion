package com.egame.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;

/**
 * @desc 接收广播刷新UI主要是同步安装状态的变化
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-2-1
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ListNotifyReceiver extends BroadcastReceiver {
	
	BaseAdapter adapter;
	
	public ListNotifyReceiver(BaseAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
//		L.d("listR","" + intent.getStringExtra("msg"));
		if(intent.getStringExtra("msg").equals("change")){
			adapter.notifyDataSetChanged();
		}
	}

}
