package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.AdGallaryAdapter;
import com.egame.app.adapters.TestGameListAdapter;
import com.egame.app.receivers.ListNotifyReceiver;
import com.egame.app.tasks.GameRecommendRunnable;
import com.egame.app.tasks.GetAdIconAsyncTask;
import com.egame.app.tasks.GetListIconAsyncTask;
import com.egame.beans.AdPageBean;
import com.egame.beans.AdTouchBean;
import com.egame.beans.GameListBean;
import com.egame.beans.ListBeanCache;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.BroswerUtil;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.common.SourceUtils;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.FlingGallery;
import com.egame.utils.ui.Footer;
import com.egame.utils.ui.Loading;
import com.egame.utils.ui.OnGalleryChangeListener;
import com.egame.utils.ui.ToastUtil;
import com.egame.utils.ui.UIUtils;
import com.eshore.network.stat.NetStat;

/**
 * @desc 游戏推荐
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-27
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameRecommendActivity extends Activity implements BaseActivity, OnClickListener {

	/**
	 * 游戏推荐列表
	 */
	ListView lvRecommendList;

	Footer footer;

	/**
	 * 顶部广告布局
	 */
	LinearLayout llHead;

	/**
	 * 顶部广告画廊
	 */
	LinearLayout llAdGallary;

	/**
	 * 顶部广告亮点
	 */
	LinearLayout llAdGallaryPoint;

	/**
	 * 游戏列表实体列表
	 */
	List<GameListBean> recommendList = new ArrayList<GameListBean>();

	/**
	 * 顶部广告画廊
	 */
	FlingGallery gallery;

	/**
	 * 画廊适配器
	 */
	AdGallaryAdapter adGallaryAdapter;

	/**
	 * 推荐列表适配器
	 */
	TestGameListAdapter gameListAdapter;

	private Loading loading;
	/**
	 * 存放亮点的list
	 */
	List<View> pointList = new ArrayList<View>();

	/**x
	 * 获取数据成功
	 */
	public static final int MSG_GET_RECOMMEND_LIST_SUCCESS = 1;

	/**
	 * 获取数据失败
	 */
	public static final int MSG_GET_RECOMMEND_LIST_FAIL = 2;

	/**
	 * json对象中列表的key
	 */
	public static final String KEY_RECOMMEND_LIST = "gameListBySearch";

	/** 总条目 */
	private int totalRecord = 0;
	/**
	 * 新品和推荐共用一个activity,true表示是推荐列表
	 */
	private boolean isRecommend = true;

	/** 起始页 */
	private int page = 1;

	/** 是否是最后一页　 */
	private boolean isLastPage = false;

	private GetListIconAsyncTask<GameListBean> getListIconTask;

	private EgameApplication application;

	private GetAdIconAsyncTask getAdIconTask;

	public AdTouchBean bean = new AdTouchBean();

	public AdTouchBean getBean() {
		return bean;
	}

	public void setBean(AdTouchBean bean) {
		this.bean = bean;
	}

	ListNotifyReceiver br;
	SharedPreferences preference = null;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (isFinishing()) { // activity已退出不做任何处理
				return;
			}
			if (msg.what == MSG_GET_RECOMMEND_LIST_SUCCESS) { // 读取成功
				String s = msg.obj.toString();
				// List<GameListBean> resultList = new
				// ArrayList<GameListBean>();
				try {
					JSONObject obj = new JSONObject(s);
					JSONArray array = null;
					if (isRecommend) {
						array = obj.getJSONArray("gameListByHot");
						if (!TextUtils.isEmpty(s)) {
							application.setRecoListJson(s);
						}
					} else {
						array = obj.getJSONArray("gameListByNew");
					}
					for (int i = 0; i < array.length(); i++) {
						JSONObject json = array.getJSONObject(i);
						GameListBean bean = new GameListBean(json);
						recommendList.add(bean);
						gameListAdapter.notifyDataSetChanged();
					}
					// recommendList.addAll(resultList);
					// L.d("dd", "page=" + page);
					if (page == 1) {
						totalRecord = obj.getInt(Const.KEY_TOTALRECORD);
						lvRecommendList.setVisibility(View.VISIBLE);
						loading.setVisibility(View.GONE);
					}
					if (totalRecord == 0) {
						isLastPage = true;
						footer.showNoData();
						return;
					}
					if (recommendList.size() >= totalRecord) { // 读取完毕
						footer.setVisibility(View.GONE);
						lvRecommendList.removeFooterView(footer.getFooter());
						isLastPage = true;
//						lvRecommendList.onFootRefreshComplete(9);
					}
					gameListAdapter.notifyDataSetChanged();
					downloadIcon();
				} catch (Exception e) {
					e.printStackTrace();
					if (page == 1) {
						loading.showReload();
					}
					// 这里需要处理解析异常！
					footer.showReload();
				}
			} else if (msg.what == MSG_GET_RECOMMEND_LIST_FAIL) { // 网络异常
				ToastUtil.show(GameRecommendActivity.this, R.string.egame_request_wrong);
				if (page == 1) {
					loading.showReload();
				}
				footer.showReload();
			}
//			lvRecommendList.onFootRefreshComplete(13);
		};
	};

	public static Bundle getBundle(boolean isRecommend) {
		Bundle bundle = new Bundle();
		bundle.putBoolean("isRecommend", isRecommend);
		return bundle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_game_recommend);

		application = (EgameApplication) getApplication();
		preference = getSharedPreferences(PreferenceUtil.SHARED_GAME, 0);
		// adPageList.addAll(application.getAdRecommondPageCache());

		initData();
		initView();
		initEvent();
		initViewData();

		adGallaryAdapter = new AdGallaryAdapter(getAdList(), this);
		gallery.setAdapter(adGallaryAdapter);
		llAdGallary.addView(gallery, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		llHead.setClickable(false);
		lvRecommendList.addHeaderView(llHead, null, false);
		lvRecommendList.addFooterView(footer.getFooter(), null, false);
		if (isRecommend) {
			gameListAdapter = new TestGameListAdapter(recommendList, this, 0, SourceUtils.HOT_LIST, true);
		} else {
			gameListAdapter = new TestGameListAdapter(recommendList, this, 1, SourceUtils.NEW_LIST, true);
		}

		lvRecommendList.setAdapter(gameListAdapter);

		getAdIconTask = new GetAdIconAsyncTask(getAdList(), gallery, adGallaryAdapter);
		getAdIconTask.execute("");

		// 读缓存
		ListBeanCache<GameListBean> cache;
		if (isRecommend) {
			cache = application.getGameRecommendCache();
		} else {
			cache = application.getGameNewCache();
		}

		if (cache.getTotalRecord() < 0) {
			excute();
		} else if (cache.isFinish()) {
			if (cache.getTotalRecord() == 0) {
				footer.showNoData();
			} else {
				lvRecommendList.setVisibility(View.VISIBLE);
				loading.setVisibility(View.GONE);
				lvRecommendList.removeFooterView(footer.getFooter());
			}
		} else {
			totalRecord = cache.getTotalRecord();
			recommendList.addAll(cache.getList());
			page = recommendList.size() / Const.PAGE_SIZE;
			gameListAdapter.notifyDataSetChanged();
			lvRecommendList.setVisibility(View.VISIBLE);
			loading.setVisibility(View.GONE);
			downloadIcon();
		}

		br = new ListNotifyReceiver(gameListAdapter);
		IntentFilter intentFilter = new IntentFilter("com.egame.app.uis.GameDownloadMissionActivity");
		this.registerReceiver(br, intentFilter);
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
		NetStat.onPausePage("GameRecommendActivity");
	}

	public void excute() {
		// footer.showLoading();
		if (isRecommend) {
			new Thread(new GameRecommendRunnable(handler, Urls.getRecommendGameUrl(GameRecommendActivity.this, page, application.getUA()))).start();
		} else {
			new Thread(new GameRecommendRunnable(handler, Urls.getNewGameUrl(GameRecommendActivity.this, page, application.getUA()))).start();
		}
	}

	@Override
	public void initData() {
		Bundle bundle = getIntent().getExtras();
		isRecommend = bundle.getBoolean("isRecommend");
	}

	@Override
	public void initView() {
		loading = new Loading(GameRecommendActivity.this);
		lvRecommendList = (ListView) findViewById(R.id.gameList);
		llHead = (LinearLayout) LayoutInflater.from(GameRecommendActivity.this).inflate(R.layout.egame_game_recommend_head, null);
		llAdGallary = (LinearLayout) llHead.findViewById(R.id.adGallary);
		llAdGallaryPoint = (LinearLayout) llHead.findViewById(R.id.adGallaryPoint);
		gallery = new FlingGallery(this, true);
		gallery.setPaddingWidth(5);
		gallery.setAnimationDuration(500);
		footer = new Footer(this);
	}

	@Override
	public void initViewData() {
		float density = UIUtils.getDensity(this);
		loading.showLoading();
		if (getAdList() != null && getAdList().size() > 0) {
			for (int i = 0; i < getAdList().size(); i++) {
				LinearLayout v = new LinearLayout(GameRecommendActivity.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(Math.round(10 * density), Math.round(10 * density)));
				lp.setMargins(Math.round(3 * density), 0, Math.round(3 * density), 0);
				pointList.add(v);
				llAdGallaryPoint.addView(v, lp);
			}
			setPoint(0);
			if (getAdList().size() > 1) {
				gallery.autoScroll();
			}
		}
	}

	@Override
	public void initEvent() {
		loading.setOnReloadClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = 1;
				loading.showLoading();
				excute();
			}
		});
		lvRecommendList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position < 1) {
					return;
				}
				GameListBean bean = recommendList.get(position - 1);
				Bundle bundle;
				if (isRecommend) {
					bundle = GamesDetailActivity.makeIntentData(bean.getGameId(), SourceUtils.HOT_LIST);
				} else {
					bundle = GamesDetailActivity.makeIntentData(bean.getGameId(), SourceUtils.NEW_LIST);
				}
				Intent intent = new Intent(GameRecommendActivity.this, GamesDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		gallery.addGalleryChangeListener(new OnGalleryChangeListener() {

			@Override
			public void onGalleryChange(int currentItem) {
				setPoint(currentItem);
			}
		});

		llHead.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				L.d("Rcomm", event.getAction() + "|" + event.getX());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bean.setX(event.getX());
					bean.setY(event.getY());
				} else if (bean.getAd() != null && event.getAction() == MotionEvent.ACTION_UP
						&& isClickDistence(bean.getX(), bean.getY(), event.getX(), event.getY())) {
					try {
						if (bean.getAd().isWapAd()) {
							NetStat.onEvent(PreferenceUtil.KEY_QAS_AD, "点击广告", bean.getAd().getActionContent());
							
//							BroswerUtil.startIeDefault(bean.getAd().getActionContent(), GameRecommendActivity.this);
							Uri uri = null;
							String u = bean.getAd().getActionContent();
							if (u.indexOf("?") == -1) {
								uri = Uri.parse(u + "?"
										+ Urls.getLogParams(GameRecommendActivity.this));
							} else {
								uri = Uri.parse(u + "&"
										+ Urls.getLogParams(GameRecommendActivity.this));
							}
							Intent it = new Intent(Intent.ACTION_VIEW);
							it.setData(uri);
							startActivity(it);
						} else if (bean.getAd().isActiveAd()) {
							BroswerUtil.startIeDefault(bean.getAd().getActionContent(), GameRecommendActivity.this);
						} else if (bean.getAd().isTopicAd()) {
							Bundle bundle = GameTopicDetailActivity.getBundle(Integer.parseInt(bean.getAd().getActionContent()));
							Intent intent = new Intent(GameRecommendActivity.this, GameTopicDetailActivity.class);
							intent.putExtras(bundle);
							GameRecommendActivity.this.startActivity(intent);
						} else if (bean.getAd().isGameAd()) {

							Bundle bundle;
							if (isRecommend) {
								bundle = GamesDetailActivity.makeIntentData(Integer.parseInt(bean.getAd().getActionContent()), SourceUtils.HOT_AD);
							} else {
								bundle = GamesDetailActivity.makeIntentData(Integer.parseInt(bean.getAd().getActionContent()), SourceUtils.NEW_AD);
							}

							Intent intent = new Intent(GameRecommendActivity.this, GamesDetailActivity.class);
							intent.putExtras(bundle);
							GameRecommendActivity.this.startActivity(intent);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// else if (!isClickDistence(bean.getX(), bean.getY(),
				// event.getX(), event.getY())) {
				// bean.setAd(null);
				// bean.setX(-999);
				// bean.setY(-999);
				// }
				return gallery.onGalleryTouchEvent(event);
			}
		});

		lvRecommendList.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return gallery.onGalleryTouchEvent(event, true);

			}
		});

		footer.setReloadButtonListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				footer.showLoading();
				excute();
			}
		});

		lvRecommendList.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int arg1) {
				L.d("view", view.getLastVisiblePosition() + "|" + view.getCount());
				if (isLastPage) {
					// 如果没有下一页了,就不再进行滚动加载
					L.d("view", "is last page");
				} else {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						if (view.getCount() < page * Const.PAGE_SIZE) {
							// 如果页数已经+1了,数据没有读取出来之前,不再+1
							L.d("view", view.getCount() + "|" + page * Const.PAGE_SIZE + "|" + page);
						} else {
							L.d("view", "page + 1");
							page = page + 1;
							excute();
						}
					}
				}

			}
		});

	}

	@Override
	public void onClick(View v) {

	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// return gallery.onGalleryTouchEvent(event);
	// }

	void setPoint(int currentItem) {
		if (pointList.size() > 0) {
			for (View v : pointList) {
				v.setBackgroundResource(R.drawable.egame_xuanzeoff);
			}
			pointList.get(currentItem).setBackgroundResource(R.drawable.egame_xuanzeon);
		}
	}

	void downloadIcon() {
		if (getListIconTask != null) {
			getListIconTask.stop();
		}
		getListIconTask = new GetListIconAsyncTask<GameListBean>(recommendList, gameListAdapter);
		getListIconTask.execute("");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (getListIconTask != null) { // 停止图片读取
			getListIconTask.stop();
		}
		if (getAdIconTask != null) { // 停止广告读取
			getAdIconTask.stop();
		}
		gallery.autoGallery.cancel();
		ListBeanCache<GameListBean> cache;
		if (isRecommend) {
			cache = application.getGameRecommendCache();
		} else {
			cache = application.getGameNewCache();
		}
		cache.getList().clear();
		// lvRecommendList.removeAllViews();
		cache.getList().addAll(recommendList);
		cache.releaseIcon();
		cache.setTotalRecord(totalRecord);
		gameListAdapter.getDbService().close();
		unregisterReceiver(br);
	}

	private List<AdPageBean> getAdList() {
		if (isRecommend) {
			if (application.getAdRecommondPageCache().size() <= 0) {
				try {
					String s = preference.getString(PreferenceUtil.KEY_AD, "");
					JSONObject obj = new JSONObject(s);
					JSONArray array = obj.getJSONArray("advertList");
					for (int i = 0; i < array.length(); i++) {
						JSONObject json = array.getJSONObject(i);
						AdPageBean page = new AdPageBean(json);
						// 推荐页面广告
						if (page.getAreaCode() == AdPageBean.AREA_RECOMMOND) { // 推荐页面广告
							application.getAdRecommondPageCache().add(page);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return application.getAdRecommondPageCache();
		} else {
			if (application.getAdNewPageCache().size() <= 0) {
				try {
					String s = preference.getString(PreferenceUtil.KEY_AD, "");
					JSONObject obj = new JSONObject(s);
					JSONArray array = obj.getJSONArray("advertList");
					for (int i = 0; i < array.length(); i++) {
						JSONObject json = array.getJSONObject(i);
						AdPageBean page = new AdPageBean(json);
						// 新品页面广告
						if (page.getAreaCode() == AdPageBean.AREA_NEW) { // 新品页面广告
							application.getAdNewPageCache().add(page);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return application.getAdNewPageCache();
		}
	}

	/**
	 * 滑动的时候判断是否属于点击事件
	 * 
	 * @param x0
	 *            起始x
	 * @param y0
	 *            起始y
	 * @param x
	 *            目标x
	 * @param y
	 *            目标y
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-29
	 */
	private boolean isClickDistence(float x0, float y0, float x, float y) {
		double distenceX = x0 - x;
		double distenceY = y0 - y;
		double distence = Math.sqrt(Math.pow(distenceX, 2) + Math.pow(distenceY, 2));
		L.d("Rcomm", distenceX + "|" + distenceY + "|" + distence);
		float density = UIUtils.getDensity(this);
		if (distence < 20 * density) {
			return true;
		} else {
			return false;
		}
	}

}
