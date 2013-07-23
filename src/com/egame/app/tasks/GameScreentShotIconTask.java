package com.egame.app.tasks;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.GameInfo;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.IconBeanImpl;

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
public class GameScreentShotIconTask extends AsyncTask<String, Integer, String> {
	private GameInfo gameInfo;
	GamesDetailActivity context;

	public GameScreentShotIconTask(GamesDetailActivity ctx, GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		this.context = ctx;
	}
	@Override
	protected String doInBackground(String... params) {
		try {
			for (int i = 0; i < gameInfo.getScreenShotIcons().length; i++) {
				IconBeanImpl icon = gameInfo.getScreenShotIcons()[i];
				if (icon.getIcon() == null) {
					BitmapDrawable drawable = HttpConnect.getHttpDrawable(icon
							.getIconurl());
					icon.setIcon(drawable);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			L.d("icon", "no pic!");
		} catch (OutOfMemoryError error) {
			System.gc();
		}
		return null;
	}

	/**
	 * @param result
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		context.setScreenShot();
	}
}
