package com.egame.app.tasks;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.egame.R;
import com.egame.beans.GameInfo;
import com.egame.utils.common.PreferenceUtil;
import com.egame.weibos.SinaMicroblogUtil;

/**
 * 描述:帮定新浪微博得异步任务
 * 
 * @author LiuHan
 * @version 1.0 create on :2012/1/11
 * 
 */
public class BindSinaAsyncTask extends AsyncTask<String, Integer, String> {
	private GameInfo gameInfo;
	private boolean isException = false;
	private ProgressDialog progressDialog;
	private Context context;
	private AccessToken accessToken;
	/** 控制变量 0：错误没有 出现 1：账号密码有误 2：内容重复 */
	public static int promptShow = 0;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// 账号密码错误
				Toast.makeText(context, R.string.egame_account_pass_wrong,
						Toast.LENGTH_SHORT).show();
				SinaMicroblogUtil.inputDialog.show();
				break;
			}

		}

	};

	public BindSinaAsyncTask(final Context context, final GameInfo gameInfo) {
		this.context = context;
		this.progressDialog = new ProgressDialog(this.context);
		this.progressDialog.setMessage(this.context.getResources().getString(R.string.egame_progress_dialog));
		this.gameInfo = gameInfo;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		SinaMicroblogUtil.addKeyAndSecret();
		Weibo weibo = new Weibo();
		try {
			accessToken = weibo.getXAuthAccessToken(arg0[0], arg0[1],
					"client_auth");
			String Token = accessToken.getToken();
			String TokenSecret = accessToken.getTokenSecret();
			/**
			 * 储存Token 与 TokenSecret
			 */
			PreferenceUtil.store(context, Token, TokenSecret, "sina");
		} catch (WeiboException e) {
			e.printStackTrace();
			if (e.getStatusCode() == 403) {
				Message message = handler.obtainMessage();
				message.what = 1;
				handler.sendMessage(message);
				promptShow = 1;
			} else {
				isException = true;
			}
		}
		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		if (accessToken != null) {
			Toast.makeText(context, R.string.egame_sina_bind_success,
					Toast.LENGTH_SHORT).show();
			if ("game".equals(PreferenceUtil.fetchType(context))) {
				/**
				 * 绑定成功 弹出发送分享用户输入对话框
				 */
				SinaMicroblogUtil.sendShareWeiboDialog(context, gameInfo,
						"game");
			} else {
				SinaMicroblogUtil.sendShareWeiboDialog(context, gameInfo,
						"client");
				// // 启动发送分享的异步任务
				// new SinaShareAsyncTask(context,
				// null).execute(SinaMicroblogUtil
				// .getShareWeiboWord());
			}
		} else if (isException) {
			Toast.makeText(context, R.string.egame_sina_network_wrong,
					Toast.LENGTH_SHORT).show();
		} else {
			if (promptShow == 0) {
				Toast.makeText(context, R.string.egame_sina_bind_failure,
						Toast.LENGTH_SHORT).show();
			}

		}

	}
}