package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.adapters.GameCommentAdapter;
import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.CommentBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @desc 获取游戏评论列表的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenvov-cw.com
 * 
 * @timer 2012-1-14
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameCommentTask extends AsyncTask<String, Integer, List<CommentBean>> {
	GamesDetailActivity context;
	List<CommentBean> list;
	GameCommentAdapter adapter;
	int gameId;
	String userId;
	int commentTimes = 0;
	public final String KEY_COMMENT = "commentList";
	public static final int PAGESIZE = 10;

	public GameCommentTask(GamesDetailActivity ctx, List<CommentBean> list, GameCommentAdapter adapter, int gameId,
			String userId) {
		this.context = ctx;
		this.list = list;
		this.adapter = adapter;
		this.gameId = gameId;
		this.userId = userId;
	}

	@Override
	protected List<CommentBean> doInBackground(String... params) {
		try {
			List<CommentBean> mList = new ArrayList<CommentBean>();
			String s = HttpConnect.getHttpString(Urls.getGameCommentUrl(context, gameId, userId));
			JSONObject obj = new JSONObject(s);
			commentTimes = obj.optInt("totalRecord");
			JSONArray array = obj.getJSONArray(KEY_COMMENT);
			// commentTimes = obj.optInt("commentTimes");
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				CommentBean bean = new CommentBean(json);
				mList.add(bean);
			}
			return mList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void onPostExecute(List<CommentBean> result) {
		super.onPostExecute(result);
		if (result == null) {
//			context.changeFooterStatus("exception");
		} else {
			context.setCommentTimes(commentTimes);
			list.addAll(result);
//			context.changeFooterStatus("");
		}
		adapter.notifyDataSetChanged();
	}

}
