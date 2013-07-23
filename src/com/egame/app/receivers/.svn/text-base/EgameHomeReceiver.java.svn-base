package com.egame.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.egame.app.uis.EgameHomeActivity;
import com.egame.utils.ui.Utils;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-23 下午04:56:33
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class EgameHomeReceiver extends BroadcastReceiver {
	private EgameHomeActivity mHomeActivity;

	public EgameHomeReceiver(EgameHomeActivity homeActivity) {
		mHomeActivity = homeActivity;
	}

	@Override
	public void onReceive(Context content, Intent intent) {
		if (intent.getAction().equals(Utils.RECEIVER_CHANGE_SEL_TAB)) {
			String tabName = intent.getStringExtra(Utils.KEY_TAB_NAME);
			if (!TextUtils.isEmpty(tabName)) {
				mHomeActivity.setSelTab(tabName);
			}
		} else if (intent.getAction().equals(Utils.RECEIVER_DOWNLOAD)) {
			mHomeActivity.showDownloadIcon();
		} else if (intent.getAction().equals(Utils.RECEIVER_MESSAGE)) {
//			mHomeActivity.showMessageIcon();
		}
	}
}
