package com.egame.app.uis;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.ImageAdapter;
import com.eshore.network.stat.NetStat;

/**
 * @desc 游戏详情中图片放大显示
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-9
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class EnlargeImageActivity extends Activity {
	private Gallery gallery;
	private Button btnBack;
	private ImageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_bigpic);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.densityDpi;
		try {
			adapter = new ImageAdapter(this,
					((EgameApplication) getApplication()).getBitmap(), density);
			// L.d("dd", "" + ((EgameApplication)
			// getApplication()).getBitmap().size() + "||" + density);
			gallery = (Gallery) findViewById(R.id.gallery);
			gallery.setAdapter(adapter);
			gallery.setSelection(this.getIntent().getIntExtra("position", 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnBack = (Button) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
		NetStat.onPausePage("EnlargeImageActivity");
	}
}
