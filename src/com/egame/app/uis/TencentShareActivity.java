/**
 * 
 */
package com.egame.app.uis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.egame.R;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.MyTextWatcher;
import com.egame.utils.ui.ToastUtil;
import com.egame.weibos.ShareWindows;
import com.egame.weibos.TencentMicroblogUtil;
import com.eshore.network.stat.NetStat;
import com.tencent.weibo.api.T_API;
import com.tencent.weibo.utils.Configuration;

/**
 * 描述：分享到腾讯微博
 * 
 * @author LiuHan
 * @version 1.0 create on :2012/01/17
 * 
 */
public class TencentShareActivity extends Activity {
	/** 文字信息UI */
	private EditText mEditText;
	/** 触发取消分享操作的UI */
	private Button mCancelButton;
	/** 触发发布微博操作的UI */
	private Button mPublishButton;
	/** 图片路径 */
	private String mPicPath = "";
	public T_API tapi;
	public String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_tencent_layout);
		if (null != TencentMicroblogUtil.tencentDialog) {
			TencentMicroblogUtil.tencentDialog.dismiss();
		}
		SharedPreferences preference = getSharedPreferences(
				PreferenceUtil.StoreDataName, 0);
		mEditText = (EditText) this.findViewById(R.id.myword);
		mEditText
				.addTextChangedListener(new MyTextWatcher(this, 140, mEditText));
		mEditText.setText(preference.getString("tencent", ""));
		mEditText.requestFocus();
		mPicPath = preference.getString("picture", "");
		Intent intent = getIntent();
		if (intent.hasExtra("oauth_token")) {
			String oauth_token = intent.getStringExtra("oauth_token");
			String oauth_token_secret = intent
					.getStringExtra("oauth_token_secret");
			Log.i("oauth_token//oauth_token_secret", oauth_token + "//"
					+ oauth_token_secret);
			TencentMicroblogUtil.setOAuthToken(ShareWindows.oauth, oauth_token,
					oauth_token_secret);
		}
		mCancelButton = (Button) this.findViewById(R.id.cancel_button);
		mCancelButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				TencentShareActivity.this.finish();
			}
		});
		mPublishButton = (Button) this.findViewById(R.id.publish_button);
		mPublishButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				TencentShareActivity.this.finish();
				tapi = new T_API();
				content = mEditText.getText().toString();
				if ("game".equals(PreferenceUtil
						.fetchType(TencentShareActivity.this))) {
					if (null != mPicPath && !"".equals(mPicPath)) {
						Log.i("post pic", mPicPath);
						new PostMyMicroblog().execute("pictrue");
					}
				} else if ("client".equals(PreferenceUtil
						.fetchType(TencentShareActivity.this))) {
					new PostMyMicroblog().execute("word");
				}
			}
		});
	}

	/**
	 * call back from qweibo page
	 */
	public void onResume() {
		super.onResume();
		NetStat.onResumePage();
		Uri uri = this.getIntent().getData();
		if (uri != null) {
			String oauth_verifier = uri.getQueryParameter("oauth_verifier");
			String oauth_token = uri.getQueryParameter("oauth_token");
			ShareWindows.oauth = TencentMicroblogUtil.getOAuthToken(
					ShareWindows.oauth, ShareWindows.auth, this, "127.0.0.1",
					oauth_verifier, oauth_token);
		}
	}

	/**
 * 
 */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("TencentShareActivity");
	}

	/**
	 * 发布微博的异步任务
	 * 
	 * @author LiuHan
	 * @version 1.0 create on:2012/1/18
	 */
	class PostMyMicroblog extends AsyncTask<String, Integer, String> {
		private File file, sendFile;
		private URL url;

		@Override
		protected String doInBackground(String... arg0) {
			try {
				if ("client".equals(PreferenceUtil
						.fetchType(TencentShareActivity.this))) {
					// 只发布文字信息
					tapi.add(ShareWindows.oauth, "json", content,
							Configuration.wifiIp, "", "");
				} else {
					// 发送图片和文字
					url = new URL(mPicPath + "pic1.jpg");
					file = new File(TencentMicroblogUtil.PIC_PATH);// 创建文件
					if (file.exists()) {
						file.delete();
					}
					// 创建URL
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();// 获得连接
					con.setRequestMethod("GET");// get方式
					con.setReadTimeout(5 * 1000);// 请求最大时间
					InputStream is = con.getInputStream();// 获得流
					byte[] data = readinputstream(is);// 读取流到二进制数组
					FileOutputStream outputstream = TencentShareActivity.this
							.openFileOutput("pic.jpg",
									Context.MODE_WORLD_READABLE
											| Context.MODE_WORLD_WRITEABLE);// 创建文件输出流
					outputstream.write(data);// 将二进制文件输出
					outputstream.close();// 关闭
					sendFile = new File(TencentMicroblogUtil.GET_PATH);// 创建文件
					if (sendFile.exists()) {
						tapi.add_pic(ShareWindows.oauth, "json", content,
								Configuration.wifiIp,
								sendFile.getAbsolutePath());
					} else {
						Log.i("Tencent", "游戏图片不存在");
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				return "发布失败";
			}
			return "发布成功";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if ("game".equals(PreferenceUtil
					.fetchType(TencentShareActivity.this))) {
				try {
					if (null != sendFile && sendFile.exists()) {
						sendFile.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			ToastUtil.show(TencentShareActivity.this, result);
		}

		public byte[] readinputstream(InputStream is) throws Exception {
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];// 缓存区
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outstream.write(buffer, 0, len);// 写入内存
			}
			is.close();
			return outstream.toByteArray();
		}

	}

}
