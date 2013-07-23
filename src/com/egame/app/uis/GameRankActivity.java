package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.TestGameListAdapter;
import com.egame.app.receivers.ListNotifyReceiver;
import com.egame.app.tasks.GetListIconAsyncTask;
import com.egame.app.tasks.GetStringRunnable;
import com.egame.app.widgets.MyListView;
import com.egame.beans.GameListBean;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.L;
import com.egame.utils.common.SourceUtils;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.Footer;
import com.egame.utils.ui.Loading;
import com.egame.utils.ui.ReleaseListIcon;
import com.egame.utils.ui.ToastUtil;
import com.egame.utils.ui.UIUtils;
import com.eshore.network.stat.NetStat;

/**
 * @desc 游戏排行列表
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-1-4
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none 【liuhan:增加三条语句：==1，==2，==3去除列表下边的空白】
 */
public class GameRankActivity extends Activity implements BaseActivity,
		OnClickListener {

	/** 周排行 */
	private TextView tvWeekRank;

	/** 月排行 */
	private TextView tvMonthRank;

	/** 总排行 */
	private TextView tvTotalRank;

	/** 排行列表 */
	private ListView lvRank;

	// 标题栏View
	private View mTitle;

	/**
	 * 返回按钮
	 */
	View btnBack;

	/**
	 * 顶部图片
	 */
	View ivTitle;

	/**
	 * 顶部标题内容
	 */
	TextView tvTitle;

	/**
	 * 游戏列表适配器
	 */
	private TestGameListAdapter adapter;

	private GetListIconAsyncTask<GameListBean> getListIconTask;

	/**
	 * 存放游戏实体列表
	 */
	private List<GameListBean> list = new ArrayList<GameListBean>();

	/**
	 * 另一个list,缓存数据用来释放图片用
	 */
	private List<GameListBean> cachelist = new ArrayList<GameListBean>();

	private List<View> mViewList;

	private OnPreDrawListener mOnPreDrawListener;

	private GetStringRunnable runnable;

	private Footer footer;

	private int page = 1;

	/** 是否是最后一页　 */
	private boolean isLastPage = false;

	private int rankType = 1;

	private int totalRecord = 0;
	ListNotifyReceiver br;
	Loading loading;
	String downloadFromer;
	EgameApplication application;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Const.MSG_GET_SUCCESS) { // 读取成功
				String s = msg.obj.toString();
				try {
					JSONObject obj = new JSONObject(s);
					JSONArray array = obj.getJSONArray("gameList");
					for (int i = 0; i < array.length(); i++) {
						JSONObject json = array.getJSONObject(i);
						GameListBean bean = new GameListBean(json);
						list.add(bean);
						adapter.notifyDataSetChanged();
						L.d("dd",
								"page ==" + page + "&&list.size=" + list.size()
										+ "&&position="
										+ lvRank.getSelectedItemPosition());
					}
					if (page == 1) {
						totalRecord = obj.getInt(Const.KEY_TOTALRECORD);
						lvRank.setVisibility(View.VISIBLE);
						loading.setVisibility(View.GONE);
						// 首次请求数据或者切换标签时，列表滚到顶部
						lvRank.setSelection(0);

					}
					if (totalRecord == 0) {
						isLastPage = true;
						footer.showNoData();
						return;
					}
					if (list.size() >= totalRecord) { // 读取完毕
						isLastPage = true;
						footer.setVisibility(View.GONE);
						lvRank.removeFooterView(footer.getFooter());
						// lvRank.onFootRefreshComplete(9);
					}
					downloadIcon();
				} catch (Exception e) {
					e.printStackTrace();
					// 这里需要处理解析异常！
					if (page == 1) {
						loading.showReload();
					} else {
						footer.showReload();
					}
				}
			} else if (msg.what == Const.MSG_GET_FAIL) {
				ToastUtil.show(GameRankActivity.this,
						R.string.egame_request_wrong);
				if (page == 1) {
					loading.showReload();
				} else {
					footer.showReload();
				}
				// 网络异常
			}
			// lvRank.onFootRefreshComplete(13);
		};
	};

	public static Bundle getBundle() {
		Bundle bundle = new Bundle();
		return bundle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EgameApplication) getApplication();
		setContentView(R.layout.egame_game_rank);
		initData();
		initView();
		initEvent();

		adapter = new TestGameListAdapter(list, GameRankActivity.this, 2,
				downloadFromer, false);
		// lvRank.addFooterView(footer.getFooter(), null, false);
		lvRank.setAdapter(adapter);
		br = new ListNotifyReceiver(adapter);
		IntentFilter intentFilter = new IntentFilter(
				"com.egame.app.uis.GameDownloadMissionActivity");
		this.registerReceiver(br, intentFilter);
		excute();
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
		NetStat.onPausePage("GameRankActivity");
	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		loading = new Loading(GameRankActivity.this);

		btnBack = findViewById(R.id.top_back);
		tvTitle = (TextView) findViewById(R.id.title_text);
		ivTitle = findViewById(R.id.top_no_text_title);
		if (this.getIntent().getBooleanExtra("isShowTitle", false)) {
			mTitle = findViewById(R.id.top_title);
			mTitle.setVisibility(View.VISIBLE);
		} else {
			ivTitle.setVisibility(View.VISIBLE);
			tvTitle.setVisibility(View.VISIBLE);
			btnBack.setVisibility(View.VISIBLE);
		}

		tvTitle.setText(getResources().getString(R.string.egame_rank_title));
		tvWeekRank = (TextView) findViewById(R.id.weekRank);
		tvMonthRank = (TextView) findViewById(R.id.monthRank);
		tvTotalRank = (TextView) findViewById(R.id.totalRank);
		lvRank = (MyListView) findViewById(R.id.rank);
		footer = new Footer(this);
		lvRank.addFooterView(footer.getFooter());
		// footer.footerLayout.setPadding(0, 0, 0,
		// -footer.footerLayout.getMeasuredHeight());
		mViewList = new ArrayList<View>();
		mViewList.add(tvWeekRank);
		mViewList.add(tvMonthRank);
		mViewList.add(tvTotalRank);
		mOnPreDrawListener = UIUtils.initButtonState(tvWeekRank);
		downloadFromer = SourceUtils.RANK_WEEK;
	}

	@Override
	public void initViewData() {

	}

	@Override
	public void initEvent() {
		tvWeekRank.setOnClickListener(this);
		tvMonthRank.setOnClickListener(this);
		tvTotalRank.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		// lvRank.setonRefreshListener(new OnRefreshListener() {
		//
		// public void onFootRefresh() {
		// if (isLastPage) {
		// // 如果没有下一页了,就不再进行滚动加载
		// L.d("view", "is last page");
		// // lvRank.onFootRefreshComplete(9);
		// } else {
		// page = page + 1;
		// excute();
		// }
		// }
		// });
		loading.setOnReloadClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = 1;
				loading.showLoading();
				excute();
			}
		});
		lvRank.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int arg1) {
				L.d("view",
						view.getLastVisiblePosition() + "|" + view.getCount()
								+ "||footer size="
								+ lvRank.getFooterViewsCount() + "||total="
								+ totalRecord);
				if (isLastPage) {
					// 如果没有下一页了,就不再进行滚动加载
					L.d("view", "is last page");
				} else {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						if (view.getCount() < page * Const.PAGE_SIZE) {
							// 如果页数已经+1了,数据没有读取出来之前,不再+1
							L.d("view", view.getCount() + "|" + page
									* Const.PAGE_SIZE + "|" + page);
						} else {
							L.d("view", "page + 1");
							page = page + 1;
							excute();
						}
					}
				}
			}
		});
		footer.setReloadButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				excute();
			}
		});
		lvRank.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (list.size() > 0) {
					GameListBean bean = list.get(position);
					Bundle bundle = GamesDetailActivity.makeIntentData(
							bean.getGameId(), downloadFromer);
					Intent intent = new Intent(GameRankActivity.this,
							GamesDetailActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}

		});
	}

	@Override
	public void onClick(View v) {
		if (v == tvWeekRank && !tvWeekRank.isSelected()) {
			downloadFromer = SourceUtils.RANK_WEEK;
			UIUtils.buttonStateChange(mViewList, tvWeekRank, mOnPreDrawListener);
			cachelist.addAll(list);
			list.clear();
			adapter.notifyDataSetChanged();
			new ReleaseListIcon<GameListBean>(cachelist).release();
			cachelist.clear();
			rankType = 1;
			page = 1;
			isLastPage = false;
			excute();
		} else if (v == tvMonthRank && !tvMonthRank.isSelected()) {
			downloadFromer = SourceUtils.RANK_MONTH;
			UIUtils.buttonStateChange(mViewList, tvMonthRank,
					mOnPreDrawListener);
			cachelist.addAll(list);
			list.clear();
			adapter.notifyDataSetChanged();
			new ReleaseListIcon<GameListBean>(cachelist).release();
			cachelist.clear();
			rankType = 2;
			page = 1;
			isLastPage = false;
			excute();
		} else if (v == tvTotalRank && !tvTotalRank.isSelected()) {
			downloadFromer = SourceUtils.RANK_ALL;
			UIUtils.buttonStateChange(mViewList, tvTotalRank,
					mOnPreDrawListener);
			cachelist.addAll(list);
			list.clear();
			adapter.notifyDataSetChanged();
			new ReleaseListIcon<GameListBean>(cachelist).release();
			cachelist.clear();
			rankType = 3;
			page = 1;
			isLastPage = false;
			excute();
		} else if (v == btnBack) {
			finish();
		}
	}

	void excute() {
		if (runnable != null) {
			runnable.stop();
		}
		footer.showLoading();
		if (page == 1) {
			if (lvRank.getFooterViewsCount() == 0) {
				lvRank.addFooterView(footer.getFooter());
			}
			lvRank.setVisibility(View.GONE);
			loading.showLoading();
		}
		runnable = new GetStringRunnable(handler, Urls.getGameRankUrl(
				GameRankActivity.this, rankType, page, application.getUA()));
		// if (lvRank.getFooterViewsCount() == 0) {
		// lvRank.addFooter();
		// lvRank.onFootRefreshComplete(13);
		// }

		new Thread(runnable).start();
	}

	void downloadIcon() {
		if (getListIconTask != null) {
			getListIconTask.stop();
		}
		getListIconTask = new GetListIconAsyncTask<GameListBean>(list, adapter);
		getListIconTask.execute("");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.getDbService().close();
		unregisterReceiver(br);
	}

}
