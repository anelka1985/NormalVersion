package com.egame.app.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.EgameApplication;
import com.egame.app.uis.GameSortActivity;
import com.egame.beans.GameSortBean;
import com.egame.config.Urls;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 游戏分类中的异步任务
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
public class GameSortTask extends AsyncTask<String, Integer, List<GameSortBean>> {
	GameSortActivity context;
	List<GameSortBean> list;
	String dataCode = "1";
	String UA;
	public static final String GAMESORT_KEY = "classFicationGame";

	public GameSortTask(GameSortActivity ctx, List<GameSortBean> list, String UA) {
		this.context = ctx;
		this.list = list;
		this.UA = UA;
		
	}

	@Override
	protected List<GameSortBean> doInBackground(String... params) {
		try {
			EgameApplication applictaion = (EgameApplication) context.getApplication();
			// 获取游戏包数据
			String s1 = HttpConnect.getHttpString(Urls.getGamePackageUrl(context, applictaion.getPhoneNum(),CommonUtil.getUserId(context), UA));
			JSONObject obj1 = new JSONObject(s1);
			JSONArray array1 = obj1.getJSONArray("gamePackageList");
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalNumber", array1.length() + "");
			map.put("typeName", "游戏包");
			map.put("picturePath", obj1.getString("gamePackagePic"));
			//TODO 未设置分类中游戏包的Icon
			GameSortBean bean1 = new GameSortBean(new JSONObject(map));
			list.add(bean1);
			// 获取分类列表数据
			String s = HttpConnect.getHttpString(Urls.getGameSortUrl(context, UA));
			JSONObject obj = new JSONObject(s);
			JSONArray array = obj.getJSONArray(GAMESORT_KEY);
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				GameSortBean bean = new GameSortBean(json);
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataCode = "0";
		}
		return list;
	}

	/**
	 * @param result
	 */
	@Override
	protected void onPostExecute(List<GameSortBean> result) {
		super.onPostExecute(result);
		context.dataCodeProcess(dataCode);
	}

}
