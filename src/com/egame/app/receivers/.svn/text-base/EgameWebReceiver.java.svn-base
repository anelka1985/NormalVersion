package com.egame.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.egame.app.uis.EgameWebActivity;
import com.egame.utils.ui.Utils;

/**
 * 类说明：
 * 
 * @创建时间 2012-3-18 下午04:02:53
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class EgameWebReceiver extends BroadcastReceiver {
	private EgameWebActivity mActivity;

	public EgameWebReceiver(EgameWebActivity activity) {
		mActivity = activity;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		if (arg1.getAction().equals(Utils.EGAME_WEB_RECEIVER)) {
			mActivity.reload();
		}
	}
}
