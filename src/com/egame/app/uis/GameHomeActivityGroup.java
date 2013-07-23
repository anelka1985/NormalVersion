package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.utils.common.L;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.UIUtils;
import com.eshore.network.stat.NetStat;

/**
 * @desc 存放游戏的五个栏目的ActivityGroup
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-26
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameHomeActivityGroup extends ActivityGroup implements BaseActivity, OnClickListener {

	/** 左起第一个按钮 */
	private TextView tvTab1;

	/** 左起第二个按钮 */
	private TextView tvTab2;

	/** 左起第三个按钮 */
	private TextView tvTab3;

	/** 左起第四个按钮 */
	private TextView tvTab4;

	/** 左起第五个按钮 */
	private TextView tvTab5;

	/** 存放activity的布局 */
	private LinearLayout llMain;

	private List<View> mViewList;

	private OnPreDrawListener mOnPreDrawListener;

	public static GameHomeActivityGroup INSTANCE;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_game_home);
		INSTANCE = this;
		initData();
		initView();
		initEvent();
		initViewData();

		// llMain.removeAllViews();
		// llMain.removeViewAt(llMain.getChildCount()-1);
		Bundle bundle = GameRecommendActivity.getBundle(true);
		Intent intent = new Intent(GameHomeActivityGroup.this, GameRecommendActivity.class);
		intent.putExtras(bundle);
		View view = getLocalActivityManager().startActivity("GameRecommendActivity", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		// 存储这个View
		// llMain.addView(view);
		llMain.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
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
		NetStat.onPausePage("GameHomeActivityGroup");
	}

	public void initData() {

	}

	public void initView() {
		tvTab1 = (TextView) findViewById(R.id.tab1);
		tvTab2 = (TextView) findViewById(R.id.tab2);
		tvTab3 = (TextView) findViewById(R.id.tab3);
		tvTab4 = (TextView) findViewById(R.id.tab4);
		tvTab5 = (TextView) findViewById(R.id.tab5);
		llMain = (LinearLayout) findViewById(R.id.main);

		mViewList = new ArrayList<View>();
		mViewList.add(tvTab1);
		mViewList.add(tvTab2);
		mViewList.add(tvTab3);
		mViewList.add(tvTab4);
		mViewList.add(tvTab5);
		mOnPreDrawListener = UIUtils.initButtonState(tvTab1);
	}

	public void initViewData() {

	}

	public void initEvent() {
		tvTab1.setOnClickListener(this);
		tvTab2.setOnClickListener(this);
		tvTab3.setOnClickListener(this);
		tvTab4.setOnClickListener(this);
		tvTab5.setOnClickListener(this);
	}

	public void returnGameTab() {
		UIUtils.buttonStateChange(mViewList, tvTab1, mOnPreDrawListener);
		// llMain.removeAllViews();
		llMain.removeViewAt(llMain.getChildCount() - 1);
		Bundle bundle = GameRecommendActivity.getBundle(true);
		Intent intent = new Intent(GameHomeActivityGroup.this, GameRecommendActivity.class);
		intent.putExtras(bundle);
		llMain.addView(getLocalActivityManager().startActivity("GameRecommendActivity", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
	}

	public void onClick(View v) {
		if (v == tvTab1 && !tvTab1.isSelected()) {
			UIUtils.buttonStateChange(mViewList, tvTab1, mOnPreDrawListener);
			// llMain.removeAllViews();

			llMain.removeViewAt(llMain.getChildCount() - 1);
			Bundle bundle = GameRecommendActivity.getBundle(true);
			Intent intent = new Intent(GameHomeActivityGroup.this, GameRecommendActivity.class);
			intent.putExtras(bundle);
			llMain.addView(getLocalActivityManager().startActivity("GameRecommendActivity", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		} else if (v == tvTab2 && !tvTab2.isSelected()) {
			UIUtils.buttonStateChange(mViewList, tvTab2, mOnPreDrawListener);
			// llMain.removeAllViews();

			llMain.removeViewAt(llMain.getChildCount() - 1);
			Bundle bundle = GameRecommendActivity.getBundle(false);
			Intent intent = new Intent(GameHomeActivityGroup.this, GameRecommendActivity.class);
			intent.putExtras(bundle);
			llMain.addView(getLocalActivityManager().startActivity("GameRecommendActivity", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		} else if (v == tvTab3 && !tvTab3.isSelected()) {
			L.d("dd", "tab3 selection=" + tvTab3.isSelected());
			UIUtils.buttonStateChange(mViewList, tvTab3, mOnPreDrawListener);
			// llMain.removeAllViews(); 

			llMain.removeViewAt(llMain.getChildCount() - 1);
			llMain.addView(
					getLocalActivityManager().startActivity("GameRecommendActivity",
							new Intent(GameHomeActivityGroup.this, GameTopicActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		} else if (v == tvTab4 && !tvTab4.isSelected()) {
			UIUtils.buttonStateChange(mViewList, tvTab4, mOnPreDrawListener);
			// llMain.removeAllViews();

			llMain.removeViewAt(llMain.getChildCount() - 1);
			llMain.addView(
					getLocalActivityManager().startActivity("GameRecommendActivity",
							new Intent(GameHomeActivityGroup.this, GameSortActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		} else if (v == tvTab5 && !tvTab5.isSelected()) {
			UIUtils.buttonStateChange(mViewList, tvTab5, mOnPreDrawListener);
			llMain.removeViewAt(llMain.getChildCount() - 1);
			Intent intent = new Intent(GameHomeActivityGroup.this, GameOnlineActivity.class);
			llMain.addView(getLocalActivityManager().startActivity("GameOnlineActivity", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		}
	}

}
