package com.egame.app.tasks;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.GameInfo;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.IconBean;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
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
public class GameIconTask extends AsyncTask<String, Integer, String> {
	private GameInfo gameInfo;
	GamesDetailActivity context;

	public GameIconTask(GamesDetailActivity ctx, GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		this.context = ctx;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			IconBean bean = (IconBean) gameInfo;
			if (bean.getIcon() == null) {
				BitmapDrawable drawable = HttpConnect.getHttpDrawable(bean
						.getIconurl());
				bean.setIcon(drawable);
			} else {
				Log.d("icon", "pic exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("icon", "no pic!");
		}
		return null;
	}

	/**
	 * @param result
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		context.setIcon();
	}
}
