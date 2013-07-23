package com.egame.app.tasks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.egame.app.uis.GamePackageSortActivity;
import com.egame.beans.GamePackageBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 游戏包页面的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2011-12-27
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GamePackageTask extends AsyncTask<String, Integer, List<GamePackageBean>> {
	GamePackageSortActivity context;
	List<GamePackageBean> list;
	String MSISDN;
	String userId;
	String UA;
	public static final String KEY_GAME_PACKAGE = "gamePackageList";
	public String dataCode = "1";

//	public GamePackageTask(GamePackageActivity ctx, List<GamePackageBean> list, String MSISDN, String userId, String UA) {
//		this.context = ctx;
//		this.list = list;
//		this.MSISDN = MSISDN;
//		this.userId = userId;
//		this.UA = UA;
//	}
	public GamePackageTask(GamePackageSortActivity ctx, List<GamePackageBean> list, String MSISDN, String userId, String UA) {
		this.context = ctx;
		this.list = list;
		this.MSISDN = MSISDN;
		this.userId = userId;
		this.UA = UA;
	}
	@Override
	protected List<GamePackageBean> doInBackground(String... params) {
		try {
			// context.dataCodeProcess("");
			String s = HttpConnect.getHttpString(Urls.getGamePackageUrl(context, MSISDN, userId, UA));
			JSONObject obj = new JSONObject(s);
			JSONArray array = obj.getJSONArray(KEY_GAME_PACKAGE);
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				GamePackageBean bean = new GamePackageBean(json);
				list.add(bean);
			}
		} catch (Exception e) {
			dataCode = "0";
			e.printStackTrace();
		}

		return list;
	}

	@Override
	protected void onPostExecute(List<GamePackageBean> result) {
		super.onPostExecute(result);
		context.dataCodeProcess(dataCode);
	}

}
