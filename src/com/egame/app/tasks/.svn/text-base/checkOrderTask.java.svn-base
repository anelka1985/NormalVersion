package com.egame.app.tasks;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.uis.GamePackageDetailActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-30
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class checkOrderTask extends AsyncTask<String, Integer, Boolean> {
	String tip;
	String packageId;
	int orderState;
	String MSISDN;
	String UA;
	String userId;
	GamePackageDetailActivity context;

	public checkOrderTask(GamePackageDetailActivity ctx, String tip, String packageId, int orderState, String MSISDN,
			String UA, String userId) {
		this.tip = tip;
		this.packageId = packageId;
		this.orderState = orderState;
		this.context = ctx;
		this.MSISDN = MSISDN;
		this.UA = UA;
		this.userId = userId;
	}

	@Override
	protected Boolean doInBackground(String... params) {

		try {
			String url = HttpConnect
					.getHttpString(Urls.getGamePackageDetailUrl(context, MSISDN, packageId, userId, UA));
			JSONObject object = new JSONObject(url);
			int newstate = Integer.parseInt(object.getString("isOrder"));
			L.d("DOM", "" + newstate);
			if (orderState == newstate) {
				return false;
			} else {
				orderState = newstate;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		// initButton();
		if (result) {
			context.initData();
			context.showOrderState(orderState, tip);
		} else {
			context.showOrderState(orderState, "操作失败，请重试");
		}

	}
}
