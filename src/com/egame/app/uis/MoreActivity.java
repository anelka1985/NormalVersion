package com.egame.app.uis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.tasks.PopUpTask;
import com.egame.app.widgets.ExpDialog;
import com.egame.config.Urls;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

public class MoreActivity extends Activity implements OnClickListener {
	// 标题栏View
	private View mTitle;

	/** 系统设置 */
	private RelativeLayout rlSystemSetting;

	/** 隐私设置 */
	private RelativeLayout rlHideSetting;

	/** 使用反馈 */
	private RelativeLayout rlMyReply;

	/** 软件升级 */
	private RelativeLayout rlUpdateSoftware;

	/** 产品列表 */
	private RelativeLayout rlProductList;

	/** 帮助中心 */
	private RelativeLayout rlHelp;

	/** 推荐给好友 */
	private RelativeLayout rlRecommend;

	/** 关于 */
	// private RelativeLayout rlAbout;

	/** 更改用户 */
	private RelativeLayout rlChangeUser;

	/** 退出 */
	private RelativeLayout rlExit;

	private final int RCODE_HIDE_LOGIN = 0;
	private final int RCODE_REPLY_LOGIN = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_tabhost_more);
		initView();
		initEvent();
		rlRecommend.post(new Runnable() {

			@Override
			public void run() {
//				showExp();
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
		NetStat.onPausePage("MoreActivity");
	}

	void initView() {
		mTitle = findViewById(R.id.top_title);
		mTitle.setVisibility(View.VISIBLE);
		rlSystemSetting = (RelativeLayout) findViewById(R.id.systemSetting);
		rlHideSetting = (RelativeLayout) findViewById(R.id.hideSetting);
		rlMyReply = (RelativeLayout) findViewById(R.id.myReply);
		rlUpdateSoftware = (RelativeLayout) findViewById(R.id.updateSoftware);
		rlProductList = (RelativeLayout) findViewById(R.id.productList);
		rlHelp = (RelativeLayout) findViewById(R.id.help);
		rlRecommend = (RelativeLayout) findViewById(R.id.recommend);
		// rlAbout = (RelativeLayout) findViewById(R.id.about);
		rlChangeUser = (RelativeLayout) findViewById(R.id.changeUser);
		rlExit = (RelativeLayout) findViewById(R.id.exit);

	}

	void initEvent() {
		rlSystemSetting.setOnClickListener(this);
		rlHideSetting.setOnClickListener(this);
		rlMyReply.setOnClickListener(this);
		rlUpdateSoftware.setOnClickListener(this);
		rlProductList.setOnClickListener(this);
		rlHelp.setOnClickListener(this);
		rlRecommend.setOnClickListener(this);
		// rlAbout.setOnClickListener(this);
		rlChangeUser.setOnClickListener(this);
		rlExit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == rlSystemSetting) {
			Intent intent = new Intent(MoreActivity.this, SystemSettingActivity.class);
			startActivity(intent);
		} else if (v == rlHideSetting) {
			if (LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				Intent intent = new Intent(MoreActivity.this, UserLoginActivity.class);
				startActivityForResult(intent, RCODE_HIDE_LOGIN);
			} else {
				Intent intent = new Intent(MoreActivity.this, HideSettingActivity.class);
				startActivity(intent);
			}
		} else if (v == rlMyReply) {
			if (LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				Intent intent = new Intent(MoreActivity.this, UserLoginActivity.class);
				startActivityForResult(intent, RCODE_REPLY_LOGIN);
			} else {
				Intent intent = new Intent(MoreActivity.this, MyReplyActivity.class);
				startActivity(intent);
			}
		} else if (v == rlUpdateSoftware) {
			Intent intent = new Intent(MoreActivity.this, UpdateSoftWareActivity.class);
			startActivity(intent);
		} else if (v == rlProductList) {
			Intent intent = new Intent(MoreActivity.this, ProductLitActivity.class);
			startActivity(intent);
		} else if (v == rlHelp) {
			Uri uri = Uri.parse(Urls.getHelpURL(MoreActivity.this));
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);

		} else if (v == rlRecommend) {
			Intent intent = new Intent(MoreActivity.this, RecomFriendActivity.class);
			startActivity(intent);
		} else if (v == rlChangeUser) {
			// 更改用户默认情况下进入到社区
			Intent intent = new Intent(MoreActivity.this, UserLoginActivity.class);
			startActivity(intent);
		} else if (v == rlExit) {
			if (LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				ToastUtil.show(MoreActivity.this, "还没有用户成功登录过");
			} else {
				DialogUtil.showLogoffDialog(EgameHomeActivity.INSTANCE, LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getNickName());
			}
		}
	}

	/**
	 * 展示经验登陆框
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-6-26
	 */
	private void showExp() {
		boolean isLogin = !LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId().equals(LoginDataStoreUtil.NULL_VALUE);
		//注释掉后预制版本不显示经验值popwindow框
//			if (isLogin && PreferenceUtil.isShowRecoExpPop(MoreActivity.this)) {// 已登录,未展示分享得经验的pop框
//			DisplayMetrics dm = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay().getMetrics(dm);
//			float density = dm.density;
//			View popView = LayoutInflater.from(MoreActivity.this).inflate(R.layout.egame_exppopup, null);
//			popView.setBackgroundResource(R.drawable.egame_exppopup_bg_left);
//			TextView tv = (TextView) popView.findViewById(R.id.pop_text);
//			tv.setText("将爱游戏推荐给朋友,经验立马拿");
//			final PopupWindow pw = new PopupWindow(popView);
//			pw.setAnimationStyle(R.style.egame_Popup_fade);
//			pw.setOutsideTouchable(true);
//			pw.setBackgroundDrawable(new BitmapDrawable());
//			pw.showAtLocation(rlRecommend, Gravity.LEFT | Gravity.TOP, 0, 0); // 在父视图的左上方显示
//			int pwH = Math.round(28 * density);
//			int pwW = Math.round(200 * density);
//			pw.update(Math.round(60 * density), Math.round(315 * density), pwW, pwH); // 更新位置。
//			popView.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					pw.dismiss();
//				}
//			});
//			new PopUpTask(pw,5000).execute("");
//			PreferenceUtil.setShowRecoExpPop(this);
//		} else if (!isLogin && PreferenceUtil.isShowRecoExpDialog(MoreActivity.this)) {
		// 未登录.为展示分享得经验的登录dialog框
			//注释掉后预制版本不显示经验值dialog框
//			ExpDialog expDialog = new ExpDialog(this, 3);
//			expDialog.show();
//			PreferenceUtil.setShowRecoExpDialog(this);
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 单元测试
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RCODE_HIDE_LOGIN && resultCode == RESULT_OK) { // 登录成功,目标是隐私页面
			if (LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				Intent intent = new Intent(MoreActivity.this, UserLoginActivity.class);
				startActivityForResult(intent, RCODE_HIDE_LOGIN);
			}
		} else if (requestCode == RCODE_REPLY_LOGIN && resultCode == RESULT_OK) { // 登录成功,目标是回执页面
			if (LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil.fetchUser(MoreActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				Intent intent = new Intent(MoreActivity.this, UserLoginActivity.class);
				startActivityForResult(intent, RCODE_REPLY_LOGIN);
			}
		}
	}
}
