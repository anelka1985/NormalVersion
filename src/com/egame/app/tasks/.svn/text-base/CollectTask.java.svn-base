package com.egame.app.tasks;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.GameInfo;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.DialogUtil;

/**
 * @desc 收藏/取消收藏游戏的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-15
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class CollectTask extends AsyncTask<String, Integer, String> {
	private ProgressDialog mPDialog;
	GamesDetailActivity context;
	GameInfo gameInfo;
	String userId;

	public CollectTask(GamesDetailActivity ctx, GameInfo gameInfo, String userId) {
		this.context = ctx;
		this.gameInfo = gameInfo;
		this.userId = userId;
		mPDialog = DialogUtil.getProgressDialog(context);
		mPDialog.setMessage("请稍候...");
		mPDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			String s = HttpConnect.getHttpString(Urls.getGameCollect(context, gameInfo.getGameId(), userId));
			JSONObject obj = new JSONObject(s);
			if (obj.getJSONObject("result").getInt("resultcode") == 0) {
				return "success";
			} else {
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mPDialog.dismiss();
		context.getCollectResult(result);
	}

}
