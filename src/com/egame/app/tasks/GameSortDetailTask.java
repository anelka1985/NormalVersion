package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.adapters.TestGameListAdapter;
import com.egame.app.uis.GameSortDetailActivity;
import com.egame.beans.GameListBean;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 游戏分类详情的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-4
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameSortDetailTask extends
		AsyncTask<String, Integer, List<GameListBean>> {
	private GameSortDetailActivity context;
	private List<GameListBean> list;
	List<GameListBean> mList;
	private TestGameListAdapter adapter;
	private int orderType;
	private String orderDesc;
	private int page;
	private int typeCode;
	private int classCode;
	private String UA;
	public static final String KEY_GAME_SORT_DETAIL = "gameListBySearch";
	public String dataCode = "";

	public GameSortDetailTask(GameSortDetailActivity ctx,
			List<GameListBean> list, TestGameListAdapter adapter, int page,
			int orderType, String orderDesc, int typeCode, int classCode,
			String UA) {
		this.context = ctx;
		this.list = list;
		this.adapter = adapter;
		this.page = page;
		this.typeCode = typeCode;
		this.classCode = classCode;
		this.UA = UA;
		this.orderType = orderType;
		this.orderDesc = orderDesc;
	}

	@Override
	protected List<GameListBean> doInBackground(String... arg0) {
		try {
			mList = new ArrayList<GameListBean>();
			String s = HttpConnect.getHttpString(Urls.getGameSortDetailUrl(
					context, page * Const.PAGE_SIZE, orderType, orderDesc,
					typeCode, classCode, UA));
			JSONObject obj = new JSONObject(s);
			obj.optInt("totalRecord");
			JSONArray array = obj.getJSONArray(KEY_GAME_SORT_DETAIL);
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				GameListBean bean = new GameListBean(json);
				mList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return mList;
	}

	@Override
	protected void onPostExecute(List<GameListBean> result) {
		super.onPostExecute(result);
		if (result == null) {
			context.dataCodeProcess("exception");
		} else {
			list.addAll(result);
			adapter.notifyDataSetChanged();
			if (result.size() < Const.PAGE_SIZE) {
				context.dataCodeProcess("loadOver");
			} else {
				context.dataCodeProcess("");
			}
			new GetListIconAsyncTask<GameListBean>(result, adapter).execute("");
		}

	}
}
