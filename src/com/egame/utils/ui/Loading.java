package com.egame.utils.ui;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.egame.R;

/**
 * @desc 整页读取提示
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
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
public class Loading {

	/** 整体布局 */
	private LinearLayout loading;

	/** 提示语 */
	private TextView text;

	/** 提示框 */
	private ProgressBar progress;

	/** 重载按钮 */
	private View button;

	/** 读取提示语 */
	private String loadingText;

	/** 异常提示语 */
	private String errorText;

	public Loading(Activity activity) {
		loadingText = activity.getResources().getString(R.string.egame_menu_sjdqz);
		errorText = activity.getResources().getString(R.string.egame_data_error);
		this.loading = (LinearLayout) activity.findViewById(R.id.loading);
		this.text = (TextView) activity.findViewById(R.id.loadingText);
		this.button = activity.findViewById(R.id.loadingButton);
		this.progress = (ProgressBar) activity.findViewById(R.id.loadingProgress);
		this.text.setText(loadingText);
	}

	public void setVisibility(int i) {
		this.loading.setVisibility(i);
	}

	public void setOnReloadClickListener(View.OnClickListener listener) {
		this.button.setOnClickListener(listener);
	}

	public void showLoading() {
		this.text.setText(loadingText);
		this.progress.setVisibility(View.VISIBLE);
		this.button.setVisibility(View.GONE);
		this.loading.setVisibility(View.VISIBLE);
	}

	public void showReload() {
		this.text.setText(errorText);
		this.progress.setVisibility(View.GONE);
		this.button.setVisibility(View.VISIBLE);
		this.loading.setVisibility(View.VISIBLE);
	}

	public void setLoadingText(String loadingText) {
		this.loadingText = loadingText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

}
