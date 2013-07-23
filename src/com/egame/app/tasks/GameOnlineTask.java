package com.egame.app.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;

import com.egame.app.uis.GameOnlineActivity;
import com.egame.beans.GameListBean;
import com.egame.beans.GameSortBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @Author yangky
 */
public class GameOnlineTask extends AsyncTask<String, Integer, Map<String, Integer>> {
	GameOnlineActivity context;
	List<GameListBean> list;
	String dataCode = "1";
	String UA;
	private int page = 0;
	private int orderType = 9;
	Handler handler;
	Map<String, Integer> map = new HashMap<String, Integer>();
	public static final String GAMESORT_KEY = "classFicationGame";

	public GameOnlineTask(GameOnlineActivity ctx, List<GameListBean> onlineList, String UA, Handler handler) {
		this.context = ctx;
		this.list = onlineList;
		this.UA = UA;
		this.handler = handler;
	}

	@Override
	protected Map<String, Integer> doInBackground(String... params) {
		try {
			String s = HttpConnect.getHttpString(Urls.getGameSortUrl(context, UA));
			JSONObject obj = new JSONObject(s);
			JSONArray array = obj.getJSONArray(GAMESORT_KEY);
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				GameSortBean bean = new GameSortBean(json);
				if (bean.getGameClassName().equals("联网游戏")) {
					map.put("typeCode", bean.getTypeCode());
					map.put("classCode", bean.getClassCode());
					map.put("totalNumber", bean.getTotalNumber());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataCode = "0";
		}
		return map;
	}

	/**
	 * @param result
	 */
	@Override
	protected void onPostExecute(Map<String, Integer> result) {
		super.onPostExecute(result);
		new Thread(new GameRecommendRunnable(handler, Urls.getGameSortDetailUrl(this.context, page, orderType, "desc",
				map.get("typeCode"), map.get("classCode"), this.UA))).start();
	}
}
