package com.egame.app.tasks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.MoreUserBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

public class MoreUserTask extends AsyncTask<String, Integer, List<MoreUserBean>>{
	List<MoreUserBean> list;
	GamesDetailActivity context;
	String UA;
	String gameId;
	public final String KEY_MORELIKE = "players";
	private String MSISDN;
	private String userId;

	public MoreUserTask(GamesDetailActivity ctx, List<MoreUserBean> list, String UA,
			String gameId, String MSISDN, String userId) {
		this.UA = UA;
		this.context = ctx;
		this.list = list;
		this.gameId = gameId;
		this.MSISDN = MSISDN;
		this.userId = userId;
	}

	@Override
	protected List<MoreUserBean> doInBackground(String... params) {
		Log.i("AAAA", gameId+";"+MSISDN+";"+userId);
		try {
			list.clear();
			String s = HttpConnect.getHttpString(Urls.getGameDetailUrl(context, gameId, MSISDN, userId,UA));
//			String s = HttpConnect.getHttpString("http://221.226.177.158:9010/sns-client/four/game/getGameDetail.json?gameId=222042&MSISDN=15301586820&USERID=100216");
			JSONObject json = new JSONObject(s);
			JSONArray array = json.getJSONArray(KEY_MORELIKE);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				MoreUserBean bean = new MoreUserBean(obj);
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	protected void onPostExecute(List<MoreUserBean> result) {
		super.onPostExecute(result);
		new MoreUserIconTask<MoreUserBean>(result, context).execute("");
		context.setMoreUser();
	}

}