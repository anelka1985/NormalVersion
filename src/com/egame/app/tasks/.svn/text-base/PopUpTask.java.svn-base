package com.egame.app.tasks;

import android.os.AsyncTask;
import android.widget.PopupWindow;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author Administrator
 * 
 * @timer 2012-6-18
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class PopUpTask extends AsyncTask<String, Integer, String> {

	PopupWindow pw;
	int time;

	public PopUpTask(PopupWindow pw,int time) {
		this.pw = pw;
		this.time = time;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			pw.dismiss();
		} catch (Exception e) {

		}

	}
}
