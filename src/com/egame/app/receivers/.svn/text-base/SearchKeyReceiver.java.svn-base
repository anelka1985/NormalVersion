package com.egame.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.egame.app.uis.SearchActivity;
import com.egame.beans.PushMsg;
import com.egame.utils.ui.Utils;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-23 下午05:18:50
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class SearchKeyReceiver extends BroadcastReceiver {
	private SearchActivity mSearchActivity;

	public SearchKeyReceiver(SearchActivity searchActivity) {
		mSearchActivity = searchActivity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Utils.RECEIVER_SEARCH_KEY)) {
			String searchKey = intent.getStringExtra(Utils.KEY_SEARCH_KEY);
			int searchType = intent.getIntExtra(Utils.KEY_SEARCH_TYPE,
					PushMsg.SWITCH_SEARCH_LABEL);
			if (!TextUtils.isEmpty(searchKey)) {
				mSearchActivity.setSearchKeyByBroadcast(searchKey, searchType);
			}
		}
	}
}
