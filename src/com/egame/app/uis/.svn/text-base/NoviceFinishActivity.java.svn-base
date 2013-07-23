package com.egame.app.uis;

import weibo4android.org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.Utils;
import com.eshore.network.stat.NetStat;

/**
 * 描述：异步任务完成后跳到的界面
 * 
 * @author LiuHan
 * @version 1.0 create on：Mon 12 Mar,2012
 */
public class NoviceFinishActivity extends Activity implements OnClickListener {
	/** 返回UI控件 */
	private TextView mFinishBack;
	/** 去下载游戏UI控件 */
	private RelativeLayout mGoForGame;
	/** 标题用户的昵称 */
	private TextView mNoviceName;
	/** 完成新手任务以后用户的等级显示UI控件 */
	private TextView mlv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_novice_finish);
		initControlUI();
		// 调用异步任务 完成新手任务
		new FinishNoviceAsyncTask().execute("");
	}

	/**
	 * 取得UI控件的引用
	 */
	private void initControlUI() {
		mFinishBack = (TextView) this.findViewById(R.id.m_finish_back);
		mFinishBack.setOnClickListener(this);
		mGoForGame = (RelativeLayout) this.findViewById(R.id.m_finish);
		mGoForGame.setOnClickListener(this);
		mNoviceName = (TextView) this.findViewById(R.id.m_novice_name);
		mNoviceName.setText(LoginDataStoreUtil.fetchUser(NoviceFinishActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getNickName() + "，欢迎来到爱游戏社区");
		mlv = (TextView) this.findViewById(R.id.m_prompt_text3);
	}

	public void onClick(View v) {
		if (v == mFinishBack) {
			DialogUtil.showNoviceCancelDialog(this);
		} else if (v == mGoForGame) {
			Intent communityIntent = new Intent(NoviceFinishActivity.this, EgameWebActivity.class);
			startActivity(communityIntent);
			NoviceFinishActivity.this.finish();
		}
	}

	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("NoviceFinishActivity");
	}

	public class FinishNoviceAsyncTask extends AsyncTask<String, Integer, String> {

		private ProgressDialog pDialog;

		public FinishNoviceAsyncTask() {
			pDialog = new ProgressDialog(NoviceFinishActivity.this);
			pDialog.setMessage(NoviceFinishActivity.this.getResources().getString(R.string.egame_progress_dialog));
			pDialog.show();
		}

		protected String doInBackground(String... arg0) {
			try {
				return HttpConnect.getHttpString(Urls.getFinishNoviceUrl(
						LoginDataStoreUtil.fetchUser(NoviceFinishActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
						NoviceFinishActivity.this));
			} catch (Exception e) {
				e.printStackTrace();
				return "exception";
			}
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if ("exception".equals(result)) {
				Toast.makeText(NoviceFinishActivity.this, NoviceFinishActivity.this.getResources().getString(R.string.egame_net_error),
						Toast.LENGTH_SHORT).show();
			} else {
				try {
					JSONObject json = new JSONObject(result);
					String lv = json.optString("LV");
					mlv.setText("等级飙升至" + lv + "级");
					JSONObject jobj = json.getJSONObject("result");
					if (0 == jobj.optInt("resultcode")) {
						if ("web".equals(Const.isWebStart)) {
							Intent intent = new Intent(Utils.EGAME_WEB_RECEIVER);
							sendBroadcast(intent);
							finish();
						}
					} else {
						finish();
					}
					Toast.makeText(NoviceFinishActivity.this, jobj.optString("resultmsg"), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(NoviceFinishActivity.this, NoviceFinishActivity.this.getResources().getString(R.string.egame_json_error),
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
