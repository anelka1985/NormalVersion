package com.egame.app.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.utils.common.PreferenceUtil;
import com.eshore.network.stat.NetStat;

/**
 * 类说明：
 * 
 * @创建时间 2012-2-17 下午05:15:28
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class WelcomeActivity extends Activity implements OnClickListener {
	private ImageView view;
	private static int[] imageIds = { R.drawable.egame_welcom1,
			R.drawable.egame_welcom2, R.drawable.egame_welcom3,
			R.drawable.egame_welcom4, R.drawable.egame_welcom5 };
	private int index;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new ImageView(this);
		view.setScaleType(ScaleType.FIT_XY);
		view.setOnClickListener(this);
		index = getIntent().getIntExtra("index", 0);
		view.setBackgroundResource(imageIds[index]);
		setContentView(view);
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
		NetStat.onPausePage("WelcomeActivity");
	}

	@Override
	public void onClick(View v) {
		index++;
		if (index >= imageIds.length) {
			PreferenceUtil.setFrist(getApplicationContext());
			Intent intent = new Intent(this, EgameHomeActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(this, WelcomeActivity.class);
			intent.putExtra("index", index);
			startActivity(intent);
			finish();
		}
	}
}
