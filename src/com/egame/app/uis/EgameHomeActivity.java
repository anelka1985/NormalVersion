package com.egame.app.uis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.test.PerformanceTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.receivers.CollectedGamesReceiver;
import com.egame.app.receivers.EgameHomeReceiver;
import com.egame.app.services.BackRunService;
import com.egame.app.services.DBService;
import com.egame.beans.PushMsg;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.common.SourceUtils;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.EnterCommunity;
import com.egame.utils.ui.UIUtils;
import com.egame.utils.ui.Utils;
import com.eshore.network.stat.NetStat;

public class EgameHomeActivity extends TabActivity implements OnClickListener,
		BaseActivity {
	public static EgameHomeActivity INSTANCE;
	public static final String TAB_GAME = "game";
	public static final String TAB_SEARCH = "search";
	public static final String TAB_MANAGER = "manager";
	public static final String TAB_COMMUNITY = "community";
	public static final String TAB_MORE = "more";
	private TabHost mTabHost;
	private View mGameView;
	private View mSearchView;
	private View mManagerView;
	private View mCommunityView;
	private View mMoreView;
	private View mDownloadIcon;
	// private View mMessageIcon;
	private ImageView downtip;
	private List<View> mViewList;
	private OnPreDrawListener mOnPreDrawListener;
	private EgameHomeReceiver mReceiver;
	private Timer timer;
	private String page = "";

	private String exitTag = "";
	private String homeExitTag;
	private SharedPreferences sharedPreferences = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NetStat.onError(this);
		setContentView(R.layout.egame_home);
		INSTANCE = this;
		// initData();
		sharedPreferences = getSharedPreferences(PreferenceUtil.SHARED_GAME, 0);
		exitTag = sharedPreferences.getString("exitTag", "game");
		// homeExitTag = sharedPreferences.getString("homeexit", "normal");
		initView();
		setDownloadIcon();
		initViewData(exitTag);
		initEvent();
		sharedPreferences.edit().putBoolean("downexit", true).commit();
		timer = new Timer();
		timer.schedule(new RefreshTimeTask(), 0, 3000);
		EgameApplication.Instance().addActivity(this);
	}

	private void setDownloadIcon() {
		mDownloadIcon = findViewById(R.id.download_icon);
		int count = 0;
		DBService dbService = new DBService(this);
		dbService.open();
		try {
			Cursor cursor = dbService.getNotInstalledGame();
			count = cursor.getCount();
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbService.close();
		mDownloadIcon.setVisibility(View.GONE);
		if (count > 0) {
			mDownloadIcon.setVisibility(View.VISIBLE);
		}
	}

	public void initData() {

	}

	public void initView() {
		mTabHost = getTabHost();
		mGameView = findViewById(R.id.game);
		mSearchView = findViewById(R.id.search);
		mManagerView = findViewById(R.id.manager);
		mCommunityView = findViewById(R.id.community);
		downtip = (ImageView) findViewById(R.id.download_tip);
		mMoreView = (ImageButton) findViewById(R.id.more);
		// mMessageIcon = findViewById(R.id.message_icon);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Utils.RECEIVER_CHANGE_SEL_TAB);
		filter.addAction(Utils.RECEIVER_DOWNLOAD);
		filter.addAction(Utils.RECEIVER_MESSAGE);
		mReceiver = new EgameHomeReceiver(this);
		registerReceiver(mReceiver, filter);

		// 停止后台Service
		// stopService(new Intent(this, BackRunService.class));

	}

	public void initViewData(String exitTag) {
		mViewList = new ArrayList<View>();
		mViewList.add(mGameView);
		mViewList.add(mSearchView);
		mViewList.add(mManagerView);
		mViewList.add(mCommunityView);
		mViewList.add(mMoreView);
		/******************** modify for samsung 20130104 ****************/
		if (exitTag.equals("game")) {
			mOnPreDrawListener = UIUtils.initButtonState(mGameView);
		} else if (exitTag.equals("search")) {
			mOnPreDrawListener = UIUtils.initButtonState(mSearchView);
		} else if (exitTag.equals("manager")) {
			mOnPreDrawListener = UIUtils.initButtonState(mManagerView);
		} else if (exitTag.equals("community")) {
			mOnPreDrawListener = UIUtils.initButtonState(mCommunityView);
		} else if (exitTag.equals("more")) {
			mOnPreDrawListener = UIUtils.initButtonState(mMoreView);
		}
		/******************************* end ***************************/

		TabWidget tabWidget = mTabHost.getTabWidget();
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			View view = tabWidget.getChildAt(i);
			view.setBackgroundColor(0);
		}

		Intent gameIntent = new Intent();
		gameIntent.setClass(getApplicationContext(),
				GameHomeActivityGroup.class);
		final TabHost.TabSpec gameSpec = mTabHost.newTabSpec(TAB_GAME);
		gameSpec.setIndicator("");
		gameSpec.setContent(gameIntent);
		mTabHost.addTab(gameSpec);

		Intent searchIntent = new Intent();
		searchIntent.setClass(getApplicationContext(), SearchActivity.class);
		final TabHost.TabSpec searchSpec = mTabHost.newTabSpec(TAB_SEARCH);
		searchSpec.setIndicator("");
		searchSpec.setContent(searchIntent);
		mTabHost.addTab(searchSpec);

		Intent managerIntent = new Intent();
		managerIntent.setClass(getApplicationContext(),
				ManagerActivityGroup.class);
		final TabHost.TabSpec managerSpec = mTabHost.newTabSpec(TAB_MANAGER);
		managerSpec.setIndicator("");
		managerSpec.setContent(managerIntent);
		mTabHost.addTab(managerSpec);

		Intent rankTopIntent = new Intent();
		rankTopIntent.putExtra("isShowTitle", true);
		rankTopIntent.setClass(getApplicationContext(), GameRankActivity.class);
		final TabHost.TabSpec rankTopSpec = mTabHost.newTabSpec(TAB_COMMUNITY);
		rankTopSpec.setIndicator("");
		rankTopSpec.setContent(rankTopIntent);
		mTabHost.addTab(rankTopSpec);

		Intent moreIntent = new Intent();
		moreIntent.setClass(getApplicationContext(), MoreActivity.class);
		final TabHost.TabSpec moreSpec = mTabHost.newTabSpec(TAB_MORE);
		moreSpec.setIndicator("");
		moreSpec.setContent(moreIntent);
		mTabHost.addTab(moreSpec);

		loadIntentData(getIntent());

		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();

	}

	private void loadIntentData(Intent intent) {
		int type = intent.getIntExtra("type", 0);
		String link = intent.getStringExtra("link");
		if (type > 0) {
			System.out.println("link = " + link);
			System.out.println("type = " + type);
			switch (type) {
			case PushMsg.PUSH_GAME_UPGRADE: {
				// 到管理，游戏升级界面
				setSelTab(TAB_MANAGER);
				// 发送消息切换页卡到升级界面
				sendBroadcast(new Intent(Utils.RECEIVER_UPGRADE));
			}
				break;
			case PushMsg.PUSH_NEW_ACTIVITY: {
				enterCommunity("activity");
			}
				break;
			case PushMsg.PUSH_NEW_GAME: {
				// 到游戏详情界面
				try {
					Bundle bundle = GamesDetailActivity.makeIntentData(
							Integer.parseInt(link), SourceUtils.BACK_UP);
					Intent activityIntent = new Intent(this,
							GamesDetailActivity.class);
					activityIntent.putExtras(bundle);
					startActivity(activityIntent);
				} catch (Exception e) {
				}
			}
				break;
			case PushMsg.PUSH_MESSAGE: {
				enterCommunity("msg");
			}
				break;
			case PushMsg.PUSH_SYSTEM: {
				if (!TextUtils.isEmpty(link)) {
					try {
						if (!link.startsWith("http://")) {
							link = "http://" + link;
						}
						if (link.indexOf("?") > 0) {
							link += "&" + Urls.getLogParams(this);
						} else {
							link += "?" + Urls.getLogParams(this);
						}
						Uri uri = Uri.parse(link);
						Intent it = new Intent(Intent.ACTION_VIEW);
						it.setData(uri);
						startActivity(it);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
				break;
			case PushMsg.SWITCH_SEARCH_LABEL: {
				// 切换到搜索页面
				setSelTab(TAB_SEARCH);
				// 发送消息
				if (!TextUtils.isEmpty(link)) {
					Intent broadcast = new Intent(Utils.RECEIVER_SEARCH_KEY);
					broadcast.putExtra(Utils.KEY_SEARCH_KEY, link);
					broadcast.putExtra(Utils.KEY_SEARCH_TYPE, type);
					sendBroadcast(broadcast);
				}
			}
				break;
			case PushMsg.SWITCH_SEARCH_PROVIDER: {
				// 切换到搜索页面
				setSelTab(TAB_SEARCH);
				// 发送消息
				if (!TextUtils.isEmpty(link)) {
					Intent broadcast = new Intent(Utils.RECEIVER_SEARCH_KEY);
					broadcast.putExtra(Utils.KEY_SEARCH_KEY, link);
					broadcast.putExtra(Utils.KEY_SEARCH_TYPE, type);
					sendBroadcast(broadcast);
				}
			}
				break;
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		loadIntentData(intent);
	}

	public void initEvent() {
		mGameView.setOnClickListener(this);
		mSearchView.setOnClickListener(this);
		mManagerView.setOnClickListener(this);
		mCommunityView.setOnClickListener(this);
		mMoreView.setOnClickListener(this);
		downtip.setOnClickListener(this);
	}

	private final static int MENU_FEEDBACK = 1;
	private final static int MENU_SETTING = 2;
	private final static int MENU_ABOUT = 3;
	private final static int MENU_EXIT = 4;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_FEEDBACK: {
			Intent intent = new Intent(this, MyReplyActivity.class);
			startActivity(intent);
		}
			break;
		case MENU_SETTING: {
			Intent intent = new Intent(this, SystemSettingActivity.class);
			startActivity(intent);
		}
			break;
		case MENU_ABOUT: {
			
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
		}
			break;
		case MENU_EXIT: {
			DialogUtil.showExitDialog(this);
		}
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 去掉menu按钮中的使用反馈和系统设置
		// menu.add(0, MENU_FEEDBACK, Menu.NONE, R.string.egame_menu_feedback);
		// menu.add(0, MENU_SETTING, Menu.NONE, R.string.egame_menu_setting);
		menu.add(0, MENU_ABOUT, Menu.NONE, R.string.egame_menu_about);
		menu.add(0, MENU_EXIT, Menu.NONE, R.string.egame_menu_exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("EgameHomeActivity");
		/******************** modify for samsung 20130104 ****************/
		String exitActivity = this.getCurrentActivity().getLocalClassName()
				.toString();
		String str = "game";
		if (exitActivity.equals("app.uis.GameHomeActivityGroup")) {
			exitTag = "game";
		} else if (exitActivity.equals("app.uis.GameRankActivity")) {
			exitTag = "community";
		} else if (exitActivity.equals("app.uis.ManagerActivityGroup")) {
			exitTag = "manager";
		} else if (exitActivity.equals("app.uis.SearchActivity")) {
			exitTag = "search";
		} else if (exitActivity.equals("app.uis.MoreActivity")) {
			exitTag = "more";
		}

		boolean flag = sharedPreferences.edit().putString("exitTag", exitTag)
				.commit();

		/********************* end ************************************/

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetStat.exit();
		if (timer != null) {
			timer.cancel();
		}
		PreferenceUtil.setBackRunCount(getApplicationContext(), 0);
		String str = sharedPreferences.getString("homeexit", "fail");
		if (str.equals("normal")) {
			sharedPreferences.edit().putString("exitTag", "game").commit();
		} else {
			sharedPreferences.edit().putString("exitTag", exitTag).commit();
		}
		// sharedPreferences.edit().putString("homeexit", "fail").commit();

		// 预制版本不需要后台运行
		// startService(new Intent(this, BackRunService.class));
		unregisterReceiver(mReceiver);
		EgameApplication application = (EgameApplication) getApplication();
		application.initCache();
		try {
			File file = new File(Const.NoSDCardDIRECTORY + "/temp.apk");
			L.i(Const.NoSDCardDIRECTORY + "/temp.apk");
			file.delete();
		} catch (Exception e) {
			L.i("内存没有下载数据");
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (mTabHost.getCurrentTabTag().equals(TAB_SEARCH)) {
				return mTabHost.getCurrentView().dispatchKeyEvent(event);
			} else {
				DialogUtil.showExitDialog(this);
				return true;
			}
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT
				|| event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	public void onClick(View v) {
		if (v == mGameView) {
			DialogUtil.closeSoft(this);
			setSelTab(TAB_GAME);
		} else if (v == mSearchView) {
			setSelTab(TAB_SEARCH);
		} else if (v == mManagerView) {
			DialogUtil.closeSoft(this);
			setSelTab(TAB_MANAGER);
		} else if (v == mCommunityView) {
			DialogUtil.closeSoft(this);
			setSelTab(TAB_COMMUNITY);
		} else if (v == mMoreView) {
			DialogUtil.closeSoft(this);
			setSelTab(TAB_MORE);
		} else if (v == downtip) {
			downtip.setVisibility(View.GONE);
		}
	}

	/**
	 * 完成新手任务调用的方法
	 */
	public void gotoGame() {
		setSelTab(TAB_GAME);
	}

	public void showDownloadIcon() {
		if (PreferenceUtil.isFristDown(this)) {
			downtip.setVisibility(View.VISIBLE);
			PreferenceUtil.setFristDown(this);
		}
		mDownloadIcon.setVisibility(View.VISIBLE);
	}

	public void showMessageIcon() {
		// 不显示排行上的new图标
		// mMessageIcon.setVisibility(View.VISIBLE);
	}

	public void setSelTab(String tabName) {
		if (tabName.equals(TAB_GAME)) {
			mTabHost.setCurrentTabByTag(TAB_GAME);
			UIUtils.buttonStateChange(mViewList, mGameView, mOnPreDrawListener);
			GameHomeActivityGroup.INSTANCE.returnGameTab();
		} else if (tabName.equals(TAB_SEARCH)) {
			mTabHost.setCurrentTabByTag(TAB_SEARCH);
			UIUtils.buttonStateChange(mViewList, mSearchView,
					mOnPreDrawListener);
		} else if (tabName.equals(TAB_MANAGER)) {
			mTabHost.setCurrentTabByTag(TAB_MANAGER);
			UIUtils.buttonStateChange(mViewList, mManagerView,
					mOnPreDrawListener);
			if (mDownloadIcon.getVisibility() == View.VISIBLE) {
				mDownloadIcon.setVisibility(View.GONE);
				sendBroadcast(new Intent(Utils.RECEIVER_DOWNTASK));
			}
		} else if (tabName.equals(TAB_COMMUNITY)) {

			// if (mMessageIcon.getVisibility() == View.VISIBLE) {
			// mMessageIcon.setVisibility(View.GONE);
			// enterCommunity("msg");
			// } else {
			// enterCommunity("");
			// }
			mTabHost.setCurrentTabByTag(TAB_COMMUNITY);
			UIUtils.buttonStateChange(mViewList, mCommunityView,
					mOnPreDrawListener);
		} else if (tabName.equals(TAB_MORE)) {
			mTabHost.setCurrentTabByTag(TAB_MORE);
			UIUtils.buttonStateChange(mViewList, mMoreView, mOnPreDrawListener);
		}
	}

	class RefreshTimeTask extends TimerTask {
		@Override
		public void run() {
			// L.d("download", "send refresh");
			Intent intent = new Intent(Const.ACTION_DOWNLOAD_STATE);
			intent.putExtra("msg", "refresh");
			sendBroadcast(intent);
		}
	}

	/**
	 * 进入社区的流程
	 */
	private void enterCommunity(String enterPage) {
		page = enterPage;
		EnterCommunity enterCommunity = new EnterCommunity(
				EgameHomeActivity.this, enterPage);
		enterCommunity.enter();
	}

	private void enterCommunity() {
		EnterCommunity enterCommunity = new EnterCommunity(
				EgameHomeActivity.this, page);
		enterCommunity.enter();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EnterCommunity.RCODE_ENTERCOMUNITY_LOGIN
				&& resultCode == RESULT_OK) {
			enterCommunity();
		}
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub

	}
}