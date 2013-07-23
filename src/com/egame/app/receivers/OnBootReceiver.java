package com.egame.app.receivers;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.egame.app.FlashScreenActivity;
import com.egame.app.services.BackRunService;
import com.egame.app.uis.EgameHomeActivity;

public class OnBootReceiver extends BroadcastReceiver {
	public final static String PUSH_MSG = "com.egame.app.receivers.PUSH_MSG";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent intentService = new Intent(context, BackRunService.class);
			context.startService(intentService);
		} else if (action.equals(Intent.ACTION_USER_PRESENT)) {
			if (!isServiceRun(context)) {
//				Intent intentService = new Intent(context, BackRunService.class);
//				context.startService(intentService);
			}
		} else if (action.equals(PUSH_MSG)) {
			int type = intent.getIntExtra("type", 0);
			String link = intent.getStringExtra("link");
			if (type > 0) {
				if (isServiceRun(context)) {
					Intent i = new Intent(context, FlashScreenActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("type", type);
					i.putExtra("link", link);
					context.startActivity(i);
				} else {
					Intent i = new Intent(context, EgameHomeActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtra("type", type);
					i.putExtra("link", link);
					context.startActivity(i);
				}
			}
		}
	}

	private boolean isServiceRun(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : appList) {
			if (info.processName.equals("com.egame")) {
				return true;
			}
		}
		List<RunningServiceInfo> list = am.getRunningServices(100);
		for (RunningServiceInfo info : list) {
			if (info.service.getClassName().equals(
					"com.egame.app.services.BackRunService")) {
				return true;
			}
		}
		return false;
	}

}
