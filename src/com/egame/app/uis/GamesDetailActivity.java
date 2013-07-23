package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.ImageAdapter;
import com.egame.app.receivers.GameDetailReceiver;
import com.egame.app.services.DBService;
import com.egame.app.services.DownloadService;
import com.egame.app.tasks.CheckUaTask;
import com.egame.app.tasks.CheckUaTask.CheckUaResultListener;
import com.egame.app.tasks.CollectTask;
import com.egame.app.tasks.GameDetailTask;
import com.egame.app.tasks.GameDetailTask.GameResultListener;
import com.egame.app.tasks.GameIconTask;
import com.egame.app.tasks.GameScreentShotIconTask;
import com.egame.app.tasks.MoreLikeTask;
import com.egame.app.tasks.UrlInfoTask;
import com.egame.beans.DownloadListBean;
import com.egame.beans.GameInfo;
import com.egame.beans.MoreLikeBean;
import com.egame.beans.MoreUserBean;
import com.egame.beans.PushMsg;
import com.egame.beans.biz.DownloadToRomBizBean;
import com.egame.beans.interfaces.DownGameAble;
import com.egame.config.Urls;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.sys.ApnUtils;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.BundleUtils;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.EnterCommunity;
import com.egame.utils.ui.IconBeanImpl;
import com.egame.utils.ui.ToastUtil;
import com.egame.utils.ui.UIUtils;
import com.egame.utils.ui.Utils;
import com.egame.weibos.MyOAuth;
import com.egame.weibos.ShareWindows;
import com.eshore.network.stat.NetStat;

/**
 * 描述：游戏详情
 * 
 * @author LiuHan
 * @version 1.0 create on:2012/1/18
 */
public class GamesDetailActivity extends Activity implements OnClickListener,
		GameResultListener, CheckUaResultListener, DownGameAble {

	EgameApplication application;
	/** 列表显示游戏评论的UI控件 */
	private ListView listview;
	/** 游戏介绍和游戏评论Tab数据集合 */
	private List<View> viewList;
	private OnPreDrawListener onPreDrawListener;
	/** 游戏介绍Tab */
	private TextView mGameIntroTab;
	/** 游戏评论 */
	// private TextView mGameCommentTab;
	/** 存放游戏评论的数据集合 */
	// private List<CommentBean> commentList = new ArrayList<CommentBean>();
	/** 游戏评论数据适配器 */
	// private GameCommentAdapter commentAdapter;
	/** 游戏评论布局 */
	// private View mGameCommentLayout;
	/** 更新时间 */
	private TextView updatetime;
	/** 上线时间 */
	private TextView deployeeTime;
	/** 游戏介绍 */
	private View mGameIntro;
	/** 游戏截图数据适配器 */
	private ImageAdapter imageAdapter;
	private float mDensity;
	/** 游戏图片的list */
	private List<BitmapDrawable> imageList = new ArrayList<BitmapDrawable>();
	/** 展示游戏图片 */
	private Gallery glrGallery;
	/** 游戏截图上的进度框 */
	private ProgressBar pbScreenShot;
	private Bundle bundle;
	/** 存储游戏信息的java bean */
	private GameInfo gameInfo;

	/** 大家还喜欢的list */
	List<MoreLikeBean> moreLikeList = new ArrayList<MoreLikeBean>();
	/** 还有谁在玩 */
	List<MoreUserBean> moregameuser = new ArrayList<MoreUserBean>();
	/** 游戏的|ID */
	private String gameId;
	/** 游戏名称 */
	private TextView mGameName;
	/** 游戏星级 */
	private ImageView mGameScore;
	/** 游戏评论模块中的星级 */
	// private ImageView mGameStar;
	/** 游戏计费情况 */
	private TextView mGameCost;
	/** 收藏 */
	private LinearLayout mGameCollect;
	/** 取消收藏 */
	private LinearLayout mGameDiscollect;
	/** 下载次数 */
	private TextView mGameDownloadtimes;
	/** 游戏介绍 全部 */
	private TextView mGameDescAll;
	/** 提供商 */
	private TextView mGameProvider;
	/** 游戏大小 */
	private TextView mGameSize;
	/** 游戏类型 */
	private TextView mGameType;
	/** 游戏标签 */
	private TextView[] mGameTag;
	/** 游戏介绍 2行 */
	private TextView mGameDesc;
	/** 游戏评论模块中的评论数 */
	// private TextView mCommentCounts;
	/** 游戏图标 */
	private ImageView mGameIcon;
	/** 还有谁在玩-游戏图标5 */
	private ImageView mGameIcon5;
	/** 还有谁在玩-游戏名称5 */
	private TextView mGameName5;
	/** 还有谁在玩-游戏图标6 */
	private ImageView mGameIcon6;
	/** 还有谁在玩-游戏名称6 */
	private TextView mGameName6;
	/** 还有谁在玩-游戏图标7 */
	private ImageView mGameIcon7;
	/** 还有谁在玩-游戏名称7 */
	private TextView mGameName7;
	/** 还有谁在玩-游戏图标8 */
	private ImageView mGameIcon8;
	/** 还有谁在玩-游戏名称8 */
	private TextView mGameName8;
	/** 大家还喜欢-游戏图标1 */
	private ImageView mGameIcon1;
	/** 大家还喜欢-游戏名称1 */
	private TextView mGameName1;
	/** 大家还喜欢-游戏图标2 */
	private ImageView mGameIcon2;
	/** 大家还喜欢-游戏名称 2 */
	private TextView mGameName2;
	/** 大家还喜欢-游戏图标3 */
	private ImageView mGameIcon3;
	/** 大家还喜欢-游戏名称3 */
	private TextView mGameName3;
	/** 大家还喜欢-游戏图标4 */
	private ImageView mGameIcon4;
	/** 大家还喜欢-游戏名称 4 */
	private TextView mGameName4;
	private LinearLayout Linea1;
	private LinearLayout Linea2;
	private LinearLayout Linea3;
	private LinearLayout Linea4;
	private LinearLayout MoreUserGame;
//	private RelativeLayout moreUserrela;
	private Button mmorebutton;
	/** 游戏介绍展开按钮 */
	private Button mOpenDesc;
	/** 大家还喜欢按钮 */
	private View mMoreLike;
	/** 大家还喜欢模块 */
	private LinearLayout mMoreLikeGame;
	private Button mMoreButton;
	/** 返回按钮 */
	private TextView mBackButton;
	/** 立即下载UI控件 */
	private LinearLayout mDownloadNow;
	/** 底部下载按钮布局 */
	private RelativeLayout mBottom;
	/** 分享UI控件 */
	private LinearLayout mGameShare;
	/** 评论 */
	// private RelativeLayout mGameComment;
	private RelativeLayout rlCommentBg;
	/** 下载按钮 */
	private LinearLayout mGameDownload;
	/** 更新按钮 */
	private LinearLayout mGameUpdate;
	/** 运行按钮 */
	private LinearLayout mGameStart;
	/** 安装按钮 */
	private LinearLayout mGameInstall;
	/** 游戏评论footer */
	// private Footer footer;

	private ScrollView scroll;
	private View inner;
	private String packageName;
	private GameDetailReceiver gr;
	long contentLength = 100;
	String downloadFromer;
	private ProgressDialog mPDialog;

	DownloadToRomBizBean downToRomBizBean;

	private final int REQUESTCODE_COMMENT = 0;
	private final int REQUESTCODE_COLLECT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EgameApplication) getApplication();
		setContentView(R.layout.egame_game_detail);
		// 取得UI控件的引用
		initUIControl();
		// 读取游戏详情
		bundle = this.getIntent().getExtras();
		downloadFromer = bundle.getString("downloadFromer");
		gr = new GameDetailReceiver(GamesDetailActivity.this);
		IntentFilter intentFilter = new IntentFilter(
				"com.egame.app.uis.GameDownloadMissionActivity");
		this.registerReceiver(gr, intentFilter);
		new GameDetailTask(GamesDetailActivity.this, GamesDetailActivity.this,
				bundle.getInt("gameId") + "", application.getPhoneNum(),
				LoginDataStoreUtil.fetchUser(GamesDetailActivity.this,
						LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
				application.getUA(), true).execute("");
		EgameApplication.Instance().addActivity(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
		InitDownloadBtn();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("GamesDetailActivity");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(gr);
		super.onDestroy();
		if (null != MyOAuth.instance) {
			MyOAuth.instance.unRegister();
		}
	}

	public static Bundle makeIntentData(int gameId, String downloadFromer) {
		Bundle bundle = new Bundle();
		bundle.putInt("gameId", gameId);
		bundle.putString("downloadFromer", downloadFromer);
		return bundle;
	}

	/**
	 * 取得UI控件的引用
	 */
	private void initUIControl() {
		mPDialog = DialogUtil.getProgressDialog(GamesDetailActivity.this);
		scroll = (ScrollView) findViewById(R.id.scroll);
		inner = findViewById(R.id.inner);
		// 数据集合
		viewList = new ArrayList<View>();
		// footer = new Footer(this);
		// footer.setReloadButtonListener(this);
		// ListView
		listview = (ListView) this.findViewById(R.id.egame_game_detail_list);
		mBackButton = (TextView) this.findViewById(R.id.m_back);
		mBackButton.setOnClickListener(this);// 返回按钮
		mBottom = (RelativeLayout) findViewById(R.id.bottom);
		mDownloadNow = (LinearLayout) this.findViewById(R.id.downloadnow);
		mDownloadNow.setOnClickListener(this);// 立即下载
		// 游戏介绍Tab
		mGameIntroTab = (TextView) findViewById(R.id.gamedesctab);
		// mGameIntroTab.setOnClickListener(this);
		// 游戏评论Tab
		// mGameCommentTab = (TextView) findViewById(R.id.gamecommenttab);
		// mGameCommentTab.setOnClickListener(this);
		// 游戏描述布局
		mGameIntro = findViewById(R.id.desc);
		glrGallery = (Gallery) findViewById(R.id.gallery);
		pbScreenShot = (ProgressBar) findViewById(R.id.screenshortProgress);
		// 游戏名称
		mGameName = (TextView) findViewById(R.id.name);
		mGameName.setMovementMethod(ScrollingMovementMethod.getInstance());
		mGameScore = (ImageView) findViewById(R.id.score);
		// mGameStar = (ImageView) findViewById(R.id.star);
		mGameCost = (TextView) findViewById(R.id.cost);
		mGameCollect = (LinearLayout) findViewById(R.id.collect);
		mGameCollect.setOnClickListener(this);// 收藏
		mGameDiscollect = (LinearLayout) findViewById(R.id.discollect);
		MoreUserGame = (LinearLayout) findViewById(R.id.moreuserGame);
//		moreUserrela = (RelativeLayout) findViewById(R.id.moreuser);
		mmorebutton = (Button) findViewById(R.id.moreuser11);
//		moreUserrela.setOnClickListener(this);
		mGameDiscollect.setOnClickListener(this);// 取消收藏
		// 游戏下载次数
		mGameDownloadtimes = (TextView) findViewById(R.id.downloadtimes);
		mGameDescAll = (TextView) findViewById(R.id.gameDescAll);
		mGameProvider = (TextView) findViewById(R.id.provider);
		updatetime = (TextView) findViewById(R.id.updatetime);
		deployeeTime = (TextView) findViewById(R.id.deployeetime);
		mGameSize = (TextView) findViewById(R.id.gameSize);// 游戏的大小
		mGameType = (TextView) findViewById(R.id.gameType);// 游戏的类型
		mGameTag = new TextView[4];
		mGameTag[0] = (TextView) findViewById(R.id.gameTag1);
		mGameTag[1] = (TextView) findViewById(R.id.gameTag2);
		mGameTag[2] = (TextView) findViewById(R.id.gameTag3);
		mGameTag[3] = (TextView) findViewById(R.id.gameTag4);

		mGameDesc = (TextView) findViewById(R.id.gameDesc);
		// mCommentCounts = (TextView) findViewById(R.id.commentCount);
		mGameIcon = (ImageView) findViewById(R.id.icon);
		mGameIcon1 = (ImageView) findViewById(R.id.gameIcon1);
		mGameName1 = (TextView) findViewById(R.id.gameName1);
		mGameIcon2 = (ImageView) findViewById(R.id.gameIcon2);
		mGameName2 = (TextView) findViewById(R.id.gameName2);
		mGameIcon3 = (ImageView) findViewById(R.id.gameIcon3);
		mGameName3 = (TextView) findViewById(R.id.gameName3);
		mGameIcon4 = (ImageView) findViewById(R.id.gameIcon4);
		mGameName4 = (TextView) findViewById(R.id.gameName4);
		mGameIcon5 = (ImageView) findViewById(R.id.gameIcon5);
		mGameName5 = (TextView) findViewById(R.id.gameName5);
		mGameIcon6 = (ImageView) findViewById(R.id.gameIcon6);
		mGameName6 = (TextView) findViewById(R.id.gameName6);
		mGameIcon7 = (ImageView) findViewById(R.id.gameIcon7);
		mGameName7 = (TextView) findViewById(R.id.gameName7);
		mGameIcon8 = (ImageView) findViewById(R.id.gameIcon8);
		mGameName8 = (TextView) findViewById(R.id.gameName8);
		Linea1 = (LinearLayout) findViewById(R.id.linea1);
		Linea2 = (LinearLayout) findViewById(R.id.linea2);
		Linea3 = (LinearLayout) findViewById(R.id.linea3);
		Linea4 = (LinearLayout) findViewById(R.id.linea4);
		glrGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(GamesDetailActivity.this,
						EnlargeImageActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
		mGameIcon1.setOnClickListener(this);
		mGameIcon2.setOnClickListener(this);
		mGameIcon3.setOnClickListener(this);
		mGameIcon4.setOnClickListener(this);
		mGameIcon5.setOnClickListener(this);
		mGameIcon6.setOnClickListener(this);
		mGameIcon7.setOnClickListener(this);
		mGameIcon8.setOnClickListener(this);
		mGameTag[0].setOnClickListener(this);
		mGameTag[1].setOnClickListener(this);
		mGameTag[2].setOnClickListener(this);
		mGameTag[3].setOnClickListener(this);

		mOpenDesc = (Button) findViewById(R.id.desczhankai);
		mOpenDesc.setOnClickListener(this);
		mMoreLike = findViewById(R.id.moreLike);
		mMoreLike.setOnClickListener(this);
		mMoreLikeGame = (LinearLayout) findViewById(R.id.moreLikeGame);
		mMoreButton = (Button) findViewById(R.id.more);
		mMoreButton.setOnClickListener(this);
		mGameShare = (LinearLayout) findViewById(R.id.share);
		mGameShare.setOnClickListener(this);
		// mGameComment = (RelativeLayout) findViewById(R.id.comment);
		// mGameComment.setOnClickListener(this);
		// rlCommentBg = (RelativeLayout) findViewById(R.id.commentBg);
		// rlCommentBg.setOnClickListener(this);
		mGameDownload = (LinearLayout) findViewById(R.id.download);
		mGameDownload.setOnClickListener(this);
		mGameUpdate = (LinearLayout) findViewById(R.id.update);
		mGameUpdate.setOnClickListener(this);
		mGameStart = (LinearLayout) findViewById(R.id.start);
		mGameStart.setOnClickListener(this);
		mGameInstall = (LinearLayout) findViewById(R.id.install);
		mGameInstall.setOnClickListener(this);
		// 给列表添加headerview
		// listview.addFooterView(footer.getFooter(), null, false);
		// footer.setVisibility(View.GONE);
		// commentAdapter = new GameCommentAdapter(commentList,
		// GamesDetailActivity.this);
		// listview.setAdapter(commentAdapter);
		// 游戏评论
		// mGameCommentLayout = findViewById(R.id.m_game_comment);
		mDensity = this.getResources().getDisplayMetrics().densityDpi;
		imageAdapter = new ImageAdapter(GamesDetailActivity.this, imageList,
				mDensity / 2);
		glrGallery.setAdapter(imageAdapter);
		viewList.add(mGameIntroTab);
		// viewList.add(mGameCommentTab);
		onPreDrawListener = UIUtils.initButtonState(mGameIntroTab);
	}

	/**
	 * 设置游戏icon
	 */
	public void setIcon() {
		if (null == gameInfo.getIcon()) {
			mGameIcon.setBackgroundResource(R.drawable.egame_defaultgamepic);
		} else {
			mGameIcon.setBackgroundDrawable(gameInfo.getIcon());
		}
	}

	/**
	 * 设置游戏截图
	 */
	public void setScreenShot() {
		// 游戏截图
		if (imageList.size() == 0) {
			for (int i = 0; i < gameInfo.getScreenShotIcons().length; i++) {
				IconBeanImpl icon = gameInfo.getScreenShotIcons()[i];
				if (icon.getIcon() != null) {
					imageList.add(gameInfo.getScreenShotIcons()[i].getIcon());
				}
			}
			application.setBitmap(imageList);
			imageAdapter.notifyDataSetChanged();
			pbScreenShot.setVisibility(View.GONE);
			glrGallery.setSelection(imageList.size() / 2);
			glrGallery.setUnselectedAlpha(0.3f);
		}
	}

	public void setMoreUser() {
		if (moregameuser.size() <= 4) {
			// mGameName5.setText(moregameuser.get(0).getUserName());
			// mGameName6.setText(moregameuser.get(1).getUserName());
			// mGameName7.setText(moregameuser.get(2).getUserName());
			// mGameName8.setText(moregameuser.get(3).getUserName());
			// if (moregameuser.get(0).getIcon() == null) {
			// mGameIcon5.setBackgroundResource(R.drawable.egame_defaultgamepic);
			// } else {
			// mGameIcon5.setBackgroundDrawable(moregameuser.get(0).getIcon());
			// }
			// if (moregameuser.get(1).getIcon() == null) {
			// mGameIcon6.setBackgroundResource(R.drawable.egame_defaultgamepic);
			// } else {
			// mGameIcon6.setBackgroundDrawable(moregameuser.get(1).getIcon());
			// }
			// if (moregameuser.get(2).getIcon() == null) {
			// mGameIcon7.setBackgroundResource(R.drawable.egame_defaultgamepic);
			// } else {
			// mGameIcon7.setBackgroundDrawable(moregameuser.get(2).getIcon());
			// }
			// if (moregameuser.get(3).getIcon() == null) {
			// mGameIcon8.setBackgroundResource(R.drawable.egame_defaultgamepic);
			// } else {
			// mGameIcon8.setBackgroundDrawable(moregameuser.get(3).getIcon());
			// }
			ImageView[] button = new ImageView[] { mGameIcon5, mGameIcon6,
					mGameIcon7, mGameIcon8 };
			TextView[] textviewbutton = new TextView[] { mGameName5,
					mGameName6, mGameName7, mGameName8 };
			LinearLayout[] linear = new LinearLayout[] { Linea1, Linea2,
					Linea3, Linea4 };
			if (moregameuser.size() != 0) {
				MoreUserGame.setVisibility(View.VISIBLE);
//				moreUserrela.setVisibility(View.VISIBLE);
			}
			for (int i = 0; i < moregameuser.size(); i++) {
				linear[i].setVisibility(View.VISIBLE);
				textviewbutton[i].setText(moregameuser.get(i).getUserName());
				if (moregameuser.get(i).getIcon() == null) {
					button[i]
							.setBackgroundResource(R.drawable.egame_defaultgamepic);
				} else {
					button[i].setBackgroundDrawable(moregameuser.get(i)
							.getIcon());
				}
			}
		}
	}

	/**
	 * 设置更多游戏
	 */
	public void setMoreGame() {
		try {
			if (moreLikeList.size() >= 4) {
				mGameName1.setText(moreLikeList.get(0).getGameName());
				mGameName2.setText(moreLikeList.get(1).getGameName());
				mGameName3.setText(moreLikeList.get(2).getGameName());
				mGameName4.setText(moreLikeList.get(3).getGameName());
				if (moreLikeList.get(0).getIcon() == null) {
					mGameIcon1
							.setBackgroundResource(R.drawable.egame_defaultgamepic);
				} else {
					mGameIcon1.setBackgroundDrawable(moreLikeList.get(0)
							.getIcon());
				}
				if (moreLikeList.get(1).getIcon() == null) {
					mGameIcon2
							.setBackgroundResource(R.drawable.egame_defaultgamepic);
				} else {
					mGameIcon2.setBackgroundDrawable(moreLikeList.get(1)
							.getIcon());
				}
				if (moreLikeList.get(2).getIcon() == null) {
					mGameIcon3
							.setBackgroundResource(R.drawable.egame_defaultgamepic);
				} else {
					mGameIcon3.setBackgroundDrawable(moreLikeList.get(2)
							.getIcon());
				}
				if (moreLikeList.get(3).getIcon() == null) {
					mGameIcon4
							.setBackgroundResource(R.drawable.egame_defaultgamepic);
				} else {
					mGameIcon4.setBackgroundDrawable(moreLikeList.get(3)
							.getIcon());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能：给UI控件添加数据
	 */
	public void addDataToUI() {
		pbScreenShot.setVisibility(View.VISIBLE);
		// 取得游戏ID
		gameId = gameInfo.getGameId();
		// 游戏的名称
		mGameName.setText(gameInfo.getGameName());
		if ("0".equals(gameInfo.getScore())) {
			mGameScore.setBackgroundResource(R.drawable.egame_star0);
			// mGameStar.setBackgroundResource(R.drawable.egame_bigstar0);
		} else if ("1".equals(gameInfo.getScore())) {
			mGameScore.setBackgroundResource(R.drawable.egame_star1);
			// mGameStar.setBackgroundResource(R.drawable.egame_bigstar1);
		} else if ("2".equals(gameInfo.getScore())) {
			mGameScore.setBackgroundResource(R.drawable.egame_star2);
			// mGameStar.setBackgroundResource(R.drawable.egame_bigstar2);
		} else if ("3".equals(gameInfo.getScore())) {
			mGameScore.setBackgroundResource(R.drawable.egame_star3);
			// mGameStar.setBackgroundResource(R.drawable.egame_bigstar3);
		} else if ("4".equals(gameInfo.getScore())) {
			mGameScore.setBackgroundResource(R.drawable.egame_star4);
			// mGameStar.setBackgroundResource(R.drawable.egame_bigstar4);
		} else {
			mGameScore.setBackgroundResource(R.drawable.egame_star5);
			// mGameStar.setBackgroundResource(R.drawable.egame_bigstar5);
		}
		// 游戏计费情况
		mGameCost.setText(gameInfo.getPayName());
		// 游戏的收藏状态
		InitFavBtn();
		InitDownloadBtn();
		// 下载次数
		mGameDownloadtimes.setText(gameInfo.getDownloadTimes());
		mGameProvider.setText(Html.fromHtml("<u>" + gameInfo.getProvider()
				+ "</u>"));
		mGameProvider.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// finish();
				Intent intent = new Intent(getApplicationContext(),
						EgameHomeActivity.class);
				intent.putExtra("type", PushMsg.SWITCH_SEARCH_PROVIDER);
				intent.putExtra("link", gameInfo.getProvider());
				startActivity(intent);
				finish();
			}
		});
		mGameSize.setText(gameInfo.getFileSizeM());
		mGameType.setText(gameInfo.getTypeName());
		updatetime.setText(gameInfo.getModifyTime());
		deployeeTime.setText(gameInfo.getDeployeeTime());
		String label = gameInfo.getLabel();
		String[] labelArray = label.split(",");
		int length;
		if (labelArray.length > 4) {
			length = 4;
		} else {
			length = labelArray.length;
		}
		for (int i = 0; i < length; i++) {
			final String labelString = labelArray[i];
			mGameTag[i].setVisibility(View.VISIBLE);
			mGameTag[i].setText(Html.fromHtml("<u>" + labelString + "</u>"));
			mGameTag[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							EgameHomeActivity.class);
					intent.putExtra("type", PushMsg.SWITCH_SEARCH_LABEL);
					intent.putExtra("link", labelString);
					startActivity(intent);
					finish();
				}
			});
		}
		mGameDesc.setText(gameInfo.getIntroduction());
		mGameDescAll.setText(gameInfo.getIntroduction());
		scroll.scrollTo(0, 0);
		boolean isLogin = !LoginDataStoreUtil
				.fetchUser(GamesDetailActivity.this,
						LoginDataStoreUtil.LOG_FILE_NAME).getUserId()
				.equals(LoginDataStoreUtil.NULL_VALUE);
		// 注释掉后预制版本不显示经验值popwindow框
		// if (isLogin &&
		// PreferenceUtil.isShowShareExpPop(GamesDetailActivity.this)) {//
		// 已登录,未展示分享得经验的pop框
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// float density = dm.density;
		// View popView =
		// LayoutInflater.from(GamesDetailActivity.this).inflate(R.layout.egame_exppopup,
		// null);
		// popView.setBackgroundResource(R.drawable.egame_exppopup_bg_right);
		// TextView tv = (TextView) popView.findViewById(R.id.pop_text);
		// tv.setText("立即分享马上获得经验");
		// final PopupWindow pw = new PopupWindow(popView);
		// pw.setOutsideTouchable(true);
		// pw.setBackgroundDrawable(new BitmapDrawable());
		// pw.setAnimationStyle(R.style.egame_Popup_fade);
		// pw.showAtLocation(mGameShare, Gravity.RIGHT | Gravity.TOP, 0, 0); //
		// 在父视图的左上方显示
		// int pwH = Math.round(28 * density);
		// int pwW = Math.round(141 * density);
		// pw.update(Math.round(37 * density), Math.round(110 * density), pwW,
		// pwH); // 更新位置。
		// popView.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		// pw.dismiss();
		// }
		// });
		// new PopUpTask(pw,5000).execute("");
		// PreferenceUtil.setShowShareExpPop(this);
		// } else if (!isLogin &&
		// PreferenceUtil.isShowShareExpDialog(GamesDetailActivity.this)) {//
		// 未登录.为展示分享得经验的登录dialog框
		// 注释掉后预制版本不显示经验值dialog框
		// ExpDialog expDialog = new ExpDialog(this, 2);
		// expDialog.show();
		// PreferenceUtil.setShowShareExpDialog(this);
		// }
	}

	/**
	 * 相关UI控件单击事件
	 */
	@Override
	public void onClick(View v) {
		// if (v == mGameIntroTab) {
		// 游戏介绍Tab
		// UIUtils.buttonStateChange(viewList, v, onPreDrawListener);
		// mGameIntroTab.setTextColor(0xffffffff);
		// mGameCommentTab.setTextColor(0xffc8c8c8);
		// mGameCommentLayout.setVisibility(View.GONE);
		// mGameIntro.setVisibility(View.VISIBLE);
		// }
		// else if (v == mGameCommentTab) {
		// UIUtils.buttonStateChange(viewList, v, onPreDrawListener);
		// mGameIntroTab.setTextColor(0xffc8c8c8);
		// mGameCommentTab.setTextColor(0xffffffff);
		// mGameIntro.setVisibility(View.GONE);
		// mGameCommentLayout.setVisibility(View.VISIBLE);
		// }
		if (v == mOpenDesc) {
			if (mGameDesc.getVisibility() == View.VISIBLE) {
				mGameDescAll.setVisibility(View.VISIBLE);
				mGameDesc.setVisibility(View.GONE);
				mOpenDesc.setBackgroundResource(R.drawable.egame_zhankai);
			} else if (mGameDesc.getVisibility() == View.GONE) {
				mGameDescAll.setVisibility(View.GONE);
				mGameDesc.setVisibility(View.VISIBLE);
				mOpenDesc.setBackgroundResource(R.drawable.egame_shouqi);
			}
		} else if (v == mMoreLike) {
			if (mMoreLikeGame.getVisibility() == View.GONE) {
				mMoreLikeGame.setVisibility(View.VISIBLE);
				mMoreButton.setBackgroundResource(R.drawable.egame_jiantouxia);
				scroll.post(new Runnable() {
					@Override
					public void run() {
						scrollToBottom(scroll, inner);
					}
				});
			} else {
				mMoreLikeGame.setVisibility(View.GONE);
				mMoreButton.setBackgroundResource(R.drawable.egame_jiantou);
			}
		} else if (v == mBackButton) {
			GamesDetailActivity.this.finish();
		} else if (v == mGameShare) {
			PreferenceUtil.storeType(GamesDetailActivity.this, "game");
			new ShareWindows(GamesDetailActivity.this, "", gameInfo,
					CommonUtil.getUserId(GamesDetailActivity.this)).show();
		} else if (v == rlCommentBg) {
			if (!LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil
					.fetchUser(GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				Intent intent = new Intent(GamesDetailActivity.this,
						GameCommentActivity.class);
				Bundle bundle = BundleUtils
						.createPostCommentBundle(
								"" + gameInfo.getGameId(),
								LoginDataStoreUtil.fetchUser(
										GamesDetailActivity.this,
										LoginDataStoreUtil.LOG_FILE_NAME)
										.getUserId(),
								LoginDataStoreUtil.fetchUser(
										GamesDetailActivity.this,
										LoginDataStoreUtil.LOG_FILE_NAME)
										.getNickName(), gameInfo.getGameName());
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			} else {
				Intent intent = new Intent(GamesDetailActivity.this,
						UserLoginActivity.class);
				startActivityForResult(intent, REQUESTCODE_COMMENT);
			}

		} else if (v == mGameDiscollect || v == mGameCollect) {
			if (!LoginDataStoreUtil
					.fetchUser(GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId()
					.equals(LoginDataStoreUtil.NULL_VALUE)) {
				new CollectTask(GamesDetailActivity.this, gameInfo,
						LoginDataStoreUtil.fetchUser(GamesDetailActivity.this,
								LoginDataStoreUtil.LOG_FILE_NAME).getUserId())
						.execute();
			} else {
				Intent intent = new Intent(GamesDetailActivity.this,
						UserLoginActivity.class);
				startActivityForResult(intent, REQUESTCODE_COLLECT);
			}

		}
//		else if (v == moreUserrela) {
//			if (MoreUserGame.getVisibility() == View.GONE) {
//				MoreUserGame.setVisibility(View.VISIBLE);
//				mmorebutton.setBackgroundResource(R.drawable.egame_jiantouxia);
//				scroll.post(new Runnable() {
//					@Override
//					public void run() {
//						scrollToBottom(scroll, inner);
//					}
//				});
//			} else {
//				MoreUserGame.setVisibility(View.GONE);
//				mmorebutton.setBackgroundResource(R.drawable.egame_jiantou);
//			}
//		}
		else if (v == mGameDownload || v == mDownloadNow) {
			if (DBService.DOWNSTATE_DOWNLOAD.equals(GameState(gameInfo
					.getGameId()))) {
				ToastUtil.show(GamesDetailActivity.this, String.format(
						getResources().getString(
								R.string.egame_manager_downloading), "请勿重复下载"));
			} else {
				if (ApnUtils.checkNetWorkStatus(GamesDetailActivity.this)) {
					showWaiting(true);
					new CheckUaTask(GamesDetailActivity.this,
							GamesDetailActivity.this, gameInfo,
							application.getUA()).execute("GameDetail");
				} else {
					ToastUtil.show(GamesDetailActivity.this, "无法连接到网络，请检查网络配置");
				}
			}
		} else if (v == mGameUpdate) {
			downloadGame(2);
		} else if (v == mGameStart) {
			startDownloadStep1();
		} else if (v == mGameInstall) {
			CommonUtil.installGames(gameInfo.getGameId() + "",
					GamesDetailActivity.this);
		} else if (v == mGameIcon1) {
			clearCurrentData(0);
			new GameDetailTask(GamesDetailActivity.this,
					GamesDetailActivity.this, gameId,
					application.getPhoneNum(), LoginDataStoreUtil.fetchUser(
							GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA(), true).execute("");
		} else if (v == mGameIcon2) {
			clearCurrentData(1);
			new GameDetailTask(GamesDetailActivity.this,
					GamesDetailActivity.this, gameId,
					application.getPhoneNum(), LoginDataStoreUtil.fetchUser(
							GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA(), true).execute("");
		} else if (v == mGameIcon3) {
			clearCurrentData(2);
			new GameDetailTask(GamesDetailActivity.this,
					GamesDetailActivity.this, gameId,
					application.getPhoneNum(), LoginDataStoreUtil.fetchUser(
							GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA(), true).execute("");
		} else if (v == mGameIcon4) {
			clearCurrentData(3);
			new GameDetailTask(GamesDetailActivity.this,
					GamesDetailActivity.this, gameId,
					application.getPhoneNum(), LoginDataStoreUtil.fetchUser(
							GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA(), true).execute("");
		} else if (v == mGameIcon5) {
			MoreUserBean user = moregameuser.get(0);
			EnterCommunity ec = new EnterCommunity(this, "friendDetail",
					user.getUserId());
			ec.enter();
		} else if (v == mGameIcon6) {
			MoreUserBean user = moregameuser.get(1);
			EnterCommunity ec = new EnterCommunity(this, "friendDetail",
					user.getUserId());
			ec.enter();
		} else if (v == mGameIcon7) {
			MoreUserBean user = moregameuser.get(2);
			EnterCommunity ec = new EnterCommunity(this, "friendDetail",
					user.getUserId());
			ec.enter();
		} else if (v == mGameIcon8) {
			MoreUserBean user = moregameuser.get(3);
			EnterCommunity ec = new EnterCommunity(this, "friendDetail",
					user.getUserId());
			ec.enter();
		}
	}

	/**
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			// commentList.clear();
			// commentAdapter.notifyDataSetChanged();
			// new GameCommentTask(GamesDetailActivity.this, commentList,
			// commentAdapter, Integer.parseInt(gameInfo
			// .getGameId()),
			// LoginDataStoreUtil.fetchUser(GamesDetailActivity.this,
			// LoginDataStoreUtil.LOG_FILE_NAME).getUserId()).execute("");
		} else if (resultCode == 2) {
			new GameDetailTask(GamesDetailActivity.this,
					GamesDetailActivity.this, bundle.getInt("gameId") + "",
					application.getPhoneNum(), LoginDataStoreUtil.fetchUser(
							GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA(), true).execute("");
		} else if (requestCode == REQUESTCODE_COMMENT
				&& resultCode == RESULT_OK) { // 如果是之前进行评论时跳转到登录,登录成功以后,跳转到评论页面
			if (!LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil
					.fetchUser(GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
				Intent intent = new Intent(GamesDetailActivity.this,
						GameCommentActivity.class);
				Bundle bundle = BundleUtils
						.createPostCommentBundle(
								"" + gameInfo.getGameId(),
								LoginDataStoreUtil.fetchUser(
										GamesDetailActivity.this,
										LoginDataStoreUtil.LOG_FILE_NAME)
										.getUserId(),
								LoginDataStoreUtil.fetchUser(
										GamesDetailActivity.this,
										LoginDataStoreUtil.LOG_FILE_NAME)
										.getNickName(), gameInfo.getGameName());
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		}
	}

	/**
	 * 点击大家好喜欢中推荐游戏时清除当前页面的相关数据
	 * 
	 * @param position
	 *            大家还喜欢中推荐游戏的位置
	 */
	public void clearCurrentData(int position) {
		// commentList.clear();
		// commentAdapter.notifyDataSetChanged();
		try {
			imageList.clear();
			imageAdapter.notifyDataSetChanged();
			gameId = moreLikeList.get(position).getGameId();
			moreLikeList.clear();
		} catch (Exception e) {

		}
	}

	/**
	 * 通过收藏游戏的异步任务返回的结果设置页面内容
	 */
	public void getCollectResult(String result) {
		if ("success".equals(result)) {
			if ("0".equals(gameInfo.getFavoriteFlag())) {
				ToastUtil.show(GamesDetailActivity.this,
						R.string.egame_discollect_success);
				gameInfo.setFavoriteFlag("1");
			} else {
				ToastUtil.show(GamesDetailActivity.this,
						R.string.egame_collect_success);
				gameInfo.setFavoriteFlag("0");
			}
			InitFavBtn();
		} else if ("fail".equals(result)) {
			if ("0".equals(gameInfo.getFavoriteFlag())) {
				ToastUtil.show(GamesDetailActivity.this,
						R.string.egame_discollect_fail);
			} else {
				ToastUtil.show(GamesDetailActivity.this,
						R.string.egame_collect_fail);
			}
		} else {
			ToastUtil.show(GamesDetailActivity.this,
					R.string.egame_request_wrong);
		}
	}

	/**
	 * 刷新收藏按钮的状态
	 */
	public void InitFavBtn() {
		// 收藏按钮不显示
		// if ("0".equals(gameInfo.getFavoriteFlag())) {
		// mGameDiscollect.setVisibility(View.VISIBLE);
		// mGameCollect.setVisibility(View.GONE);
		// } else {
		// mGameDiscollect.setVisibility(View.GONE);
		//
		// mGameCollect.setVisibility(View.VISIBLE);
		// }
	}

	/**
	 * 刷新下载按钮的状态
	 */
	public void InitDownloadBtn() {
		if (gameInfo != null) {
			if (gameInfo.isWap()
					|| DBService.DOWNSTATE_INSTALLED.equals(GameState(gameInfo
							.getGameId()))) {
				mGameDownload.setVisibility(View.GONE);
				mGameInstall.setVisibility(View.GONE);
				mGameUpdate.setVisibility(View.GONE);
				mGameStart.setVisibility(View.VISIBLE);
				mBottom.setVisibility(View.GONE);
			} else if ("update".equals(GameState(gameInfo.getGameId()))) {
				mGameDownload.setVisibility(View.GONE);
				mGameInstall.setVisibility(View.GONE);
				mGameUpdate.setVisibility(View.VISIBLE);
				mGameStart.setVisibility(View.GONE);
				mBottom.setVisibility(View.GONE);
			} else if (DBService.DOWNSTATE_FINISH.equals(GameState(gameInfo
					.getGameId()))) {
				mGameDownload.setVisibility(View.GONE);
				mGameStart.setVisibility(View.GONE);
				mGameUpdate.setVisibility(View.GONE);
				mGameInstall.setVisibility(View.VISIBLE);
				mBottom.setVisibility(View.GONE);
			} else if (DBService.INSTALL_UNINSTALL.equals(GameState(gameInfo
					.getGameId()))) {
				mGameDownload.setVisibility(View.VISIBLE);
				mGameStart.setVisibility(View.GONE);
				mGameUpdate.setVisibility(View.GONE);
				mGameInstall.setVisibility(View.GONE);
				mBottom.setVisibility(View.GONE);
			} else {
				mGameDownload.setVisibility(View.VISIBLE);
				mGameStart.setVisibility(View.GONE);
				mGameUpdate.setVisibility(View.GONE);
				mGameInstall.setVisibility(View.GONE);
				mBottom.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 检查适配的结果
	 * */
	public void checkResult(String result) {
		mPDialog.dismiss();
		if ("false".equals(result)) {
			UIUtils.showMessage(GamesDetailActivity.this,
					R.string.egame_menu_tip, R.string.egame_not_match);
		} else {
			UIUtils.showMessage(GamesDetailActivity.this,
					R.string.egame_menu_tip, R.string.egame_match_exception);
		}
	}

	/**
	 * 通过异步任务返回的结果设置页面内容
	 */
	public void getGameResult(String result, GameInfo gameInfo) {
		if ("1".equals(result)) {

			this.gameInfo = gameInfo;
			// 显示数据
			addDataToUI();
			if (gameInfo.canSendMessage()) {
				sendBroadcast(new Intent(Utils.RECEIVER_MESSAGE));
			}
			new GameIconTask(GamesDetailActivity.this, gameInfo).execute("");
			new GameScreentShotIconTask(GamesDetailActivity.this, gameInfo)
					.execute("");
			new MoreLikeTask(GamesDetailActivity.this, moreLikeList,
					application.getUA()).execute("");

			// 注释掉还有谁在玩
			/*
			 * new MoreUserTask(GamesDetailActivity.this, moregameuser,
			 * application.getUA(), bundle.getInt("gameId") + "",
			 * application.getPhoneNum(),
			 * LoginDataStoreUtil.fetchUser(GamesDetailActivity.this,
			 * LoginDataStoreUtil.LOG_FILE_NAME).getUserId()).execute("");
			 */

			// 显示加载的footer
			// footer.setVisibility(View.VISIBLE);
			// footer.showLoading();
			// commentList.clear();
			// commentAdapter.notifyDataSetChanged();
			// 启动异步任务取得游戏的评论
			// new GameCommentTask(GamesDetailActivity.this, commentList,
			// commentAdapter, Integer.parseInt(gameInfo
			// .getGameId()),
			// LoginDataStoreUtil.fetchUser(GamesDetailActivity.this,
			// LoginDataStoreUtil.LOG_FILE_NAME).getUserId()).execute("");
		} else if ("exception".equals(result)) {
			ToastUtil.show(GamesDetailActivity.this, "获取游戏详情失败");
			this.finish();
		}
	}

	/** 设置评论次数 */
	public void setCommentTimes(int commentTimes) {
		// mCommentCounts.setText("共" + commentTimes + "条评论");
	}

	/**
	 * 改变列表footer的状态 result:exception:加载异常
	 */
	// public void changeFooterStatus(String result) {
	// if ("exception".equals(result)) {
	// if (commentList.size() == 0) {
	// footer.showNoData();
	// } else {
	// footer.showReload();
	// }
	// }
	// else if (commentList.size() == 0) {
	// footer.showNoData();
	// }
	// else {
	// if (listview.getFooterViewsCount() > 0) {
	// listview.removeFooterView(footer.getFooter());
	// }
	//
	// }
	// }

	public void scrollToBottom(View scroll, View inner) {
		if (scroll == null || inner == null) {
			return;
		}
		int offset = inner.getMeasuredHeight() - scroll.getHeight();
		if (offset < 0) {
			offset = 0;
		}
		scroll.scrollTo(0, offset);
	}

	public void startDownloadStep1() {
		mPDialog.dismiss();
		L.d("down", "ischeck: true");
		// 已安装,直接启动
		if (DBService.DOWNSTATE_INSTALLED
				.equals(GameState(gameInfo.getGameId()))) {
			mGameDownload.setVisibility(View.GONE);
			mGameStart.setVisibility(View.VISIBLE);
			playGame();
			return;
		}
		if (gameInfo.isPackageGame()) {// 包内游戏
			startPackageGame();
		} else if (gameInfo.isPay()) {
			L.d("down", "needpay game");
			startDownloadPayGame();
		} else {
			startGame();
		}
	}

	/**
	 * 启动按次下载计费的
	 */
	private void startDownloadPayGame() {
		int money;
		try {
			money = Integer.parseInt(gameInfo.getMoney());
		} catch (Exception e) {
			money = 0;
		}

		if (gameInfo.getDownOrderStatus().equals("0")) {// 未订购,开始订购流程
			L.d("down", "not order");
			if (ApnUtils.isCtwap(GamesDetailActivity.this)) {
				showDownloadOrderDialog(money);
			} else {
				DialogUtil.showDialog(GamesDetailActivity.this,
						R.string.str_ctwapalertdialog_body);
			}
		} else {// 已订购,开始下载
			L.d("down", "isorder----start down");
			startGame();
		}
	}

	/**
	 * 显示下载按次计费的对话框
	 */
	private void showDownloadOrderDialog(int money) {
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (gameInfo.getOrderURL().equals("")) {
					DialogUtil.showDialog(GamesDetailActivity.this,
							"下载计费游戏只供电信用户下载");
				} else {
					// 订购点播异步任务 需要订购 点播的url
					new UrlInfoTask(gameInfo, GamesDetailActivity.this,
							application.getPhoneNum()).execute("");
				}

			}
		};
		DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		};
		DialogStyle ds = new DialogStyle();
		AlertDialog.Builder builder = ds.getBuilder(GamesDetailActivity.this,
				"确定", "取消", comfirmL, cancelL);
		builder.setTitle(R.string.egame_app_name)
				.setMessage(
						"本次下载将收取"
								+ money
								+ "元信息费。在首次下载收费后，今后重复下载该游戏不再收取任何信息费（下载产生的流量费用按当地运营商资费标准收取）")
				.create().show();
	}

	/**
	 * 启动游戏
	 */
	private void startGame() {
		if (gameInfo.isWap()) {
			startWapGame();
		} else {
			downloadGame(1);
		}
	}

	/**
	 * 启动wap游戏
	 */
	private void startWapGame() {
		if (gameInfo.getWapURL() != null) {
			Uri uri = Uri.parse(gameInfo.getWapURL());
			Intent it = new Intent(Intent.ACTION_VIEW);
			it.setData(uri);
			startActivity(it);
		} else {
			ToastUtil.show(GamesDetailActivity.this,
					R.string.egame_click_run_later);
		}
	}

	// 下载游戏 type：1 下载，2 更新
	@Override
	public void downloadGame(int type) {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			if (getAvailableStore(android.os.Environment
					.getExternalStorageDirectory().getAbsolutePath()) < contentLength) {
				ToastUtil.show(GamesDetailActivity.this, "游戏过大无法下载，请插入SD卡再试");
				return;
			} else { // 有sd卡且空间足够
				if (!LoginDataStoreUtil
						.fetchUser(GamesDetailActivity.this,
								LoginDataStoreUtil.LOG_FILE_NAME).getUserId()
						.equals(LoginDataStoreUtil.NULL_VALUE)) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							Urls.gameDownloadLog(
									GamesDetailActivity.this,
									LoginDataStoreUtil.fetchUser(
											GamesDetailActivity.this,
											LoginDataStoreUtil.LOG_FILE_NAME)
											.getUserId(), gameId);
						}
					}).start();
				}
				if (type == 1) {
					ToastUtil.show(GamesDetailActivity.this,
							R.string.egame_manager_add_download);
				} else if (type == 2) {
					ToastUtil.show(GamesDetailActivity.this,
							R.string.egame_addUpdate_hint);
				}

				Bundle bundle = DownloadService.getBundle(
						GamesDetailActivity.this,
						Integer.parseInt(gameInfo.getGameId()),
						gameInfo.getFileSize(),
						application.getPhoneNum(),
						gameInfo.getCpId(),
						gameInfo.getCpCode(),
						gameInfo.getServiceCode(),
						gameInfo.getGameName(),
						gameInfo.getChannelCode(),
						gameInfo.getIconurl(),
						application.getUA(),
						LoginDataStoreUtil.fetchUser(GamesDetailActivity.this,
								LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
						downloadFromer, gameInfo.getClassName());
				Intent intent = new Intent(GamesDetailActivity.this,
						DownloadService.class);
				intent.putExtras(bundle);
				startService(intent);
				sendBroadcast(new Intent(Utils.RECEIVER_DOWNLOAD));
			}
		} else { // 无sd卡下载流程
			if (!LoginDataStoreUtil
					.fetchUser(GamesDetailActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId()
					.equals(LoginDataStoreUtil.NULL_VALUE)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Urls.gameDownloadLog(
								GamesDetailActivity.this,
								LoginDataStoreUtil.fetchUser(
										GamesDetailActivity.this,
										LoginDataStoreUtil.LOG_FILE_NAME)
										.getUserId(), gameId);
					}
				}).start();
			}

			if (downToRomBizBean != null) {
				downToRomBizBean.init(gameInfo);
				downToRomBizBean.startDownloadToRom();
			} else {
				downToRomBizBean = new DownloadToRomBizBean(this, gameInfo,
						downloadFromer);
				downToRomBizBean.startDownloadToRom();
			}
		}

	}

	private String GameState(String gameId) {
		DBService dbService = null;
		Cursor cursor = null;
		DownloadListBean downloadBean = null;
		String state = "";
		try {
			dbService = new DBService(GamesDetailActivity.this).open();
			cursor = dbService.getGameByServiceId(gameId);
			if (cursor == null) {
				L.d("cursor ====", "null");
			} else {
				L.d("cursor size  :::", cursor.getCount() + "");
				if (cursor.getCount() > 0) {
					downloadBean = new DownloadListBean(cursor);
					state = cursor.getString(cursor
							.getColumnIndex(DBService.KEY_STATE));
					L.d("current game state :::", state);
					if (downloadBean.isDownFinishAndNotInstall()) {
						return DBService.DOWNSTATE_FINISH;
					}
					if (downloadBean.isInstalled()) {
						packageName = cursor.getString(cursor
								.getColumnIndex(DBService.KEY_PACKAGENAME));
						L.d("onResume packageName =", packageName);
						if (downloadBean.isUpdate()) {
							return "update";
						} else {
							return DBService.DOWNSTATE_INSTALLED;
						}
					}
					if (downloadBean.isUnInstalled()) {
						return DBService.INSTALL_UNINSTALL;
					}
				} else {
					L.d("cursor size ===", "0");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			L.d("数据库", "查询数据库操作异常......");
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (dbService != null) {
				dbService.close();
			}
		}
		return state;
	}

	/**
	 * true: 显示等待 false:消失
	 * */
	public void showWaiting(boolean show) {
		if (show) {
			mPDialog.show();
		} else {
			if (mPDialog.isShowing()) {
				mPDialog.dismiss();
			}
		}
	}

	/**
	 * 启动包内游戏
	 */
	private void startPackageGame() {
		if (CommonUtil.canPlayPackage(gameInfo.getDownOrderStatus())) {
			startGame();
		} else {
			new AlertDialog.Builder(GamesDetailActivity.this)
					.setTitle(R.string.egame_menu_tip)
					.setMessage(R.string.egame_package_not_order)
					.setNegativeButton("确定", null).show();
		}
	}

	private void playGame() {
		Intent intent = this.getPackageManager().getLaunchIntentForPackage(
				packageName);
		this.startActivity(intent);
	}

	/**
	 * 
	 * 获取存储卡的剩余容量，单位为字节
	 * 
	 * @param filePath
	 * 
	 * @return availableSpare
	 */
	public long getAvailableStore(String filePath) {
		StatFs statFs = new StatFs(filePath);// 取得sdcard文件路径
		long blocSize = statFs.getBlockSize();// 获取block的SIZE
		long availaBlock = statFs.getAvailableBlocks();// 可使用的Block的数量
		long availableSpare = availaBlock * blocSize;
		return availableSpare;
	}

	@Override
	public Activity getDialogActivity() {
		return this;
	}

}
