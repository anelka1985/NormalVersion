package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.GameTopicListAdapter;
import com.egame.app.tasks.GetStringRunnable;
import com.egame.app.tasks.GetTopicIconAsyncTask;
import com.egame.beans.TopicBean;
import com.egame.beans.TopicPageBean;
import com.egame.config.Urls;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.Loading;
import com.eshore.network.stat.NetStat;

/**
 * @desc 游戏专题页面
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-29
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameTopicActivity extends Activity implements BaseActivity, OnClickListener {

	EgameApplication application;
	/**
	 * 专题的listview
	 */
	ExpandableListView elvTopic;

	/**
	 * 存放专题的列表
	 */
	List<TopicPageBean> topicPageList = new ArrayList<TopicPageBean>();

	/**
	 * 专题列表适配器
	 */
	GameTopicListAdapter topicListAdapter;

	/**
	 * 底部加载
	 */
	// Footer footer;
	private Loading loading;
	/**
	 * 获取数据成功
	 */
	public static final int MSG_GET_LIST_SUCCESS = 1;

	/**
	 * 获取数据失败
	 */
	public static final int MSG_GET_LIST_FAIL = 2;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_GET_LIST_SUCCESS) { // 读取成功
				String s = msg.obj.toString();
				try {
					JSONObject obj = new JSONObject(s);
					JSONArray array = obj.getJSONArray("topicsList");
					//预制版去掉排行 从1开始循环  正常版本从1开始循环
					for (int i = 1; i < array.length(); i++) {
						JSONObject json = array.getJSONObject(i);
						TopicPageBean topicPageBean = new TopicPageBean(json);
						topicPageList.add(topicPageBean);
					}
					// footer.setVisibility(View.GONE);
					// elvTopic.removeFooterView(footer.getFooter());
					topicListAdapter.notifyDataSetChanged();
					elvTopic.setVisibility(View.VISIBLE);
					loading.setVisibility(View.GONE);
					expend();
					application.getTopicPageCache().setFinish(true);
					getTopicIconTask = new GetTopicIconAsyncTask(topicPageList, topicListAdapter);
					getTopicIconTask.execute("");
				} catch (Exception e) {
					e.printStackTrace();
					// 这里处理解析异常
					// footer.showReload();
					loading.showReload();
				}
			} else if (msg.what == MSG_GET_LIST_FAIL) { // 网络异常
				// footer.showReload();
				loading.showReload();
			}
		};
	};

	GetTopicIconAsyncTask getTopicIconTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EgameApplication) getApplication();
		setContentView(R.layout.egame_game_topic);

		initData();
		initView();
		initEvent();
		initViewData();

		// elvTopic.addFooterView(footer.getFooter(), null, false);
		topicListAdapter = new GameTopicListAdapter(topicPageList, this);
		// elvTopic.setSelector(new ColorDrawable(R.color.egame_transparent));
		elvTopic.setCacheColorHint(Color.TRANSPARENT);

		elvTopic.setAdapter(topicListAdapter);

		expend();

		if (application.getTopicPageCache().isFinish()) {
			if (application.getTopicPageCache().getList().size() > 0) {
				topicPageList.addAll(application.getTopicPageCache().getList());
				topicListAdapter.notifyDataSetChanged();
				elvTopic.setVisibility(View.VISIBLE);
				loading.setVisibility(View.GONE);
				// footer.setVisibility(View.GONE);
				// elvTopic.removeFooterView(footer.getFooter());
				expend();
				getTopicIconTask = new GetTopicIconAsyncTask(topicPageList, topicListAdapter);
				getTopicIconTask.execute("");
			} else {
				loading.setVisibility(View.GONE);
//				 footer.showNoData();
			}
		} else {
			excute();
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
		NetStat.onPausePage("GameTopicActivity");
	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		loading = new Loading(GameTopicActivity.this);
		elvTopic = (ExpandableListView) findViewById(R.id.topic);
		loading.showLoading();
		// footer = new Footer(this);
	}

	@Override
	public void initViewData() {

	}

	@Override
	public void initEvent() {
		elvTopic.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		});

		elvTopic.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				TopicBean bean = topicPageList.get(groupPosition).getTopicList().get(childPosition);
				if (bean.isRankTopic()) {
					Intent intent = new Intent(GameTopicActivity.this, GameRankActivity.class);
					Bundle bundle = GameRankActivity.getBundle();
					intent.putExtras(bundle);
					intent.putExtra("isShowTitle", false);
					startActivity(intent);
				} else {
					Intent intent = new Intent(GameTopicActivity.this, GameTopicDetailActivity.class);
					Bundle bundle = GameTopicDetailActivity.getBundle(bean.getContentId());
					intent.putExtras(bundle);
					startActivity(intent);
				}
				return false;
			}
		});

		elvTopic.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				for (int i = 0; i < topicPageList.size(); i++) {
					elvTopic.expandGroup(i);
				}
			}
		});
		loading.setOnReloadClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loading.showLoading();
				excute();
			}
		});
	}

	void excute() {
		new Thread(new GetStringRunnable(handler, Urls.getGameTopicUrl(GameTopicActivity.this, application.getUA())))
				.start();
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 展开二级ListView
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-1-10
	 */
	void expend() {
		for (int i = 0; i < topicPageList.size(); i++) {
			elvTopic.expandGroup(i);
		}
	}

	@Override
	protected void onDestroy() {
		if (getTopicIconTask != null) {
			getTopicIconTask.stop();
		}
		List<TopicPageBean> cacheList = application.getTopicPageCache().getList();
		cacheList.clear();
		cacheList.addAll(topicPageList);
		application.getTopicPageCache().releaseIcon();
		super.onDestroy();
	}
}
