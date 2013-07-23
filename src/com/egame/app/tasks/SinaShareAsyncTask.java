/**
 * 
 */
package com.egame.app.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.beans.GameInfo;
import com.egame.utils.ui.ToastUtil;
import com.egame.weibos.SinaMicroblogUtil;

/**
 * @author LiuHan
 * 
 */
public class SinaShareAsyncTask extends AsyncTask<String, Integer, String> {
	private static ProgressDialog progressDialogs;
	public static Context context;
	private GameInfo gameInfo;
	long time = System.currentTimeMillis();

	/**
	 * @param context
	 * @param gameInfo
	 */
	public SinaShareAsyncTask(Context context, GameInfo gameInfo) {
		SinaShareAsyncTask.context = context;
		progressDialogs = new ProgressDialog(SinaShareAsyncTask.context);
		progressDialogs.setMessage("分享发送中，请稍候. . . ");
		this.gameInfo = gameInfo;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialogs.show();
	}

	@Override
	protected String doInBackground(String... params) {
		if (null == this.gameInfo) {
			// 只发送 文字 分享
			return SinaMicroblogUtil.updateState(context, params[0]);
		} else {
			// 发送 文字和图
			return SinaMicroblogUtil.updateState(context, params[0],
					gameInfo.getPicturePath()+"pic1.jpg");
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialogs.dismiss();
		if ("400".equals(result)) {
			// 内容重复
			Toast.makeText(context, R.string.egame_the_content_repeat,
					Toast.LENGTH_SHORT).show();
		} else {
			if ("true".equals(result)) {
				ToastUtil.show(context, "分享成功。");
			} else {
				ToastUtil.show(context, "分享失败，请重新尝试。");
			}
		}
	}
}
