package com.egame.app.tasks;

import android.os.AsyncTask;

import com.egame.R;
import com.egame.beans.GameInfo;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.DialogAbleActivity;
import com.egame.utils.ui.UIUtils;

/**
 * @desc 检查游戏与机型是否适配
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-6
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public abstract class CheckUaTask2 extends AsyncTask<String, Integer, String> {
	GameInfo gameInfo;
	String UA;
	DialogAbleActivity context;

	public CheckUaTask2(DialogAbleActivity ctx, GameInfo gameInfo, String UA) {
		this.context = ctx;
		this.gameInfo = gameInfo;
		this.UA = UA;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {
		if (gameInfo.isWap()) {
			return "true";
		}
		try {
			L.d("dd", HttpConnect.getHttpString(Urls.getCheckUaUrl(context, gameInfo.getGameId(), UA)) + "");
			return HttpConnect.getHttpString(Urls.getCheckUaUrl(context, gameInfo.getGameId(), UA));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result == null) {
			UIUtils.showMessage(context, R.string.egame_menu_tip, R.string.egame_match_exception);
		} else {
			if("true".equals(result)){
				onFinish(0);
			}else if ("false".equals(result)) {
				UIUtils.showMessage(context, R.string.egame_menu_tip, R.string.egame_not_match);
				onFinish(1);
			} else {
				UIUtils.showMessage(context, R.string.egame_menu_tip, R.string.egame_match_exception);
				onFinish(2);
			}
		}
	}

	/**
	 * 请求结束
	 * 
	 * @param code=0成功,1失败,2异常
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-3-21
	 */
	abstract public void onFinish(int code);
}
