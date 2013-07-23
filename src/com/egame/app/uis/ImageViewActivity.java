package com.egame.app.uis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.tasks.GetImageTask;
import com.egame.app.widgets.ProgressListener;
import com.eshore.network.stat.NetStat;

/**
 * 类说明：
 * 
 * @创建时间 2012-2-13 下午02:01:41
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class ImageViewActivity extends Activity implements ProgressListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_imageview);
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			Toast.makeText(getApplicationContext(), "参数不全", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			String url = bundle.getString("url");
			ImageView image = (ImageView) findViewById(R.id.imageView);
			new GetImageTask(this, url, image).execute();
			findViewById(R.id.back).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});

		}
		EgameApplication.Instance().addActivity(this);
	}

	/**
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	/**
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("ImageViewActivity");
	}

	public static Bundle getBundle(String url) {
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		return bundle;
	}

	private ProgressDialog mProgressDialog;

	@Override
	public void showProgress(int msgResId) {
		if (mProgressDialog != null) {
			mProgressDialog.setMessage(getResources().getString(msgResId));
			mProgressDialog.show();
		} else {
			mProgressDialog = ProgressDialog.show(this, null, getResources().getString(msgResId), true);
		}
	}

	@Override
	public void hideProgress() {
		try {
			mProgressDialog.dismiss();
		} catch (Exception e) {
		}
	}

	@Override
	public void setTaskResult(boolean result, int msgResId) {
		if (msgResId != 0) {
			Toast.makeText(getApplicationContext(), msgResId, Toast.LENGTH_SHORT).show();
		}
		if (!result) {
			finish();
		}
	}
}
