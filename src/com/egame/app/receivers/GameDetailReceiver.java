package com.egame.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.egame.app.uis.GamesDetailActivity;

/**
 * @desc 接收广播并刷新游戏详情中下载按钮的状态
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-23
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameDetailReceiver extends BroadcastReceiver {
	GamesDetailActivity context;

	public GameDetailReceiver(GamesDetailActivity ctx) {
		this.context = ctx;
	}

	@Override
	public void onReceive(Context arg0, Intent intent) {
		if (intent.getStringExtra("msg").equals("change")) {
			context.InitDownloadBtn();
		}
	}

}
