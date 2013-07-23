package com.egame.app.tasks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.MoreLikeBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;

/**
 * @desc 大家还喜欢 异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-15
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class MoreLikeTask extends AsyncTask<String, Integer, List<MoreLikeBean>> {
	List<MoreLikeBean> list;
	GamesDetailActivity context;
	String UA;
	public final String KEY_MORELIKE = "whatGameList";

	public MoreLikeTask(GamesDetailActivity ctx, List<MoreLikeBean> list, String UA) {
		this.UA = UA;
		this.context = ctx;
		this.list = list;
	}

	@Override
	protected List<MoreLikeBean> doInBackground(String... params) {
		try {
			list.clear();
			String s = HttpConnect.getHttpString(Urls.getMoreLikeUrl(context, UA));
			JSONObject json = new JSONObject(s);
			JSONArray array = json.getJSONArray(KEY_MORELIKE);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				MoreLikeBean bean = new MoreLikeBean(obj);
				L.d("morelike", "" + bean.getGameId());
				L.d("morelike", "" + bean.getGameName());
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	protected void onPostExecute(List<MoreLikeBean> result) {
		super.onPostExecute(result);
		new MoreLikeIconTask<MoreLikeBean>(result, context).execute("");
		context.setMoreGame();
	}
}
