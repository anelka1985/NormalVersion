package com.egame.app.tasks;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.egame.beans.GameInfo;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.DialogUtil;

/**
 * @desc 游戏详情的异步任务
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-13
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameDetailTask extends AsyncTask<String, Integer, GameInfo> {

	public static String KEY_GAME_DETAIL = "gameDetail";
	public static String KEY_GAME_ORDER = "mapOrderStatus";
	public static String KEY_MESSAGE = "messageMap";
	private GameInfo gameInfo;
	private ProgressDialog mPDialog;
	private String gameId;
	private String MSISDN;
	private Activity mContext;
	private String gameInfoResult;
	private GameResultListener grl;
	private String userId;
	private String UA;

	public GameDetailTask(GameResultListener grl, Activity ctx, String gameId,
			String MSISDN, String userId, String UA, boolean isShowProgress) {
		this.grl = grl;
		this.gameId = gameId;
		this.MSISDN = MSISDN;
		this.userId = userId;
		this.mContext = ctx;
		this.UA = UA;
		if (isShowProgress) {
			mPDialog = DialogUtil.getProgressDialog(ctx);
			mPDialog.show();
		}
	}

	@Override
	protected GameInfo doInBackground(String... params) {
		try {
			gameInfoResult = "1";
			String s = HttpConnect.getHttpString(Urls.getGameDetailUrl(
					mContext, gameId, MSISDN, userId, UA));
			JSONObject gameInfoObj = new JSONObject(s)
					.getJSONObject(KEY_GAME_DETAIL);
			JSONObject gameOrderObj = new JSONObject(s)
					.getJSONObject(KEY_GAME_ORDER);
			JSONObject messageObj = new JSONObject(s)
					.getJSONObject(KEY_MESSAGE);
			gameInfo = new GameInfo(gameInfoObj, gameOrderObj, messageObj);
		} catch (Exception e) {
			e.printStackTrace();
			gameInfoResult = "exception";
		}
		return gameInfo;
	}

	@Override
	protected void onPostExecute(GameInfo result) {
		super.onPostExecute(result);
		grl.getGameResult(gameInfoResult, result);
		if (mPDialog != null && mContext != null && !mContext.isFinishing()) {
			mPDialog.dismiss();
		}
	}

	/**
	 * 接收查询游戏详情结果的监听
	 */
	public interface GameResultListener {
		void getGameResult(String gameInfoResult, GameInfo result);
	}
}
