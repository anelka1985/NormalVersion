package com.egame.app.tasks;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.adapters.TestGameListAdapter;
import com.egame.app.uis.GamePackageDetailActivity;
import com.egame.beans.PackageDetailBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 游戏包详情的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-12
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GamePackageDetailTask extends AsyncTask<String, Integer, PackageDetailBean> {
	private PackageDetailBean packageDetailBean;
	private TestGameListAdapter adapter;
	GamePackageDetailActivity context;
	String MSISDN;
	String userId;
	String UA;
	private String packageId;

	// public static String dataCode = "1";

	public GamePackageDetailTask(GamePackageDetailActivity ctx, PackageDetailBean packageDetailBean,
			TestGameListAdapter adapter, String MSISDN, String packageId, String userId, String UA) {
		this.adapter = adapter;
		this.context = ctx;
		this.packageDetailBean = packageDetailBean;
		this.MSISDN = MSISDN;
		this.userId = userId;
		this.UA = UA;
		this.packageId = packageId;
	}

	@Override
	protected PackageDetailBean doInBackground(String... params) {
		try {
			String s = HttpConnect.getHttpString(Urls.getGamePackageDetailUrl(context, MSISDN, packageId, userId, UA));
			JSONObject obj = new JSONObject(s);
			packageDetailBean = new PackageDetailBean(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return packageDetailBean;
	}

	protected void onPostExecute(PackageDetailBean result) {
		super.onPostExecute(result);
		// L.d("dd", "" + dataCode);
		adapter.notifyDataSetChanged();
		context.dataCodeProcess(result);
	}
}
