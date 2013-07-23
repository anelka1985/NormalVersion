package com.egame.app.uis;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.adapters.GameTopicDetailAdapter;
import com.egame.app.receivers.ListNotifyReceiver;
import com.egame.app.tasks.GameTopIconTask;
import com.egame.app.tasks.GetStringRunnable;
import com.egame.beans.GameTopListBean;
import com.egame.beans.TopicDetailBean;
import com.egame.beans.interfaces.DialogAble;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.SourceUtils;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.Loading;
import com.eshore.network.stat.NetStat;

/**
 * @desc 专题详情
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
 * @Modified by none
 */
public class GameTopicDetailActivity extends Activity implements BaseActivity, OnClickListener,DialogAble {
	/**
	 * 专题名称
	 */
	TextView tvTopicName;

	/**
	 * 专题内容
	 */
	TextView tvTopicContenxt;

	/**
	 * 专题游戏列表
	 */
	ListView lvTopicGames;

	/**
	 * 专题列表头部
	 */
	LinearLayout llTopicListHead;

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
	 * 整页读取提示框
	 */
	Loading loading;

	GameTopicDetailAdapter adapter;

	TopicDetailBean bean;

	ListNotifyReceiver br;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (!isFinishing()) {
				if (msg.what == Const.MSG_GET_SUCCESS) {
					try {
						bean = new TopicDetailBean(new JSONObject(msg.obj.toString()));
						loading.setVisibility(View.GONE);
						lvTopicGames.setVisibility(View.VISIBLE);
						initViewData();
						br = new ListNotifyReceiver(adapter);
						IntentFilter intentFilter = new IntentFilter(Const.ACTION_DOWNLOAD_STATE);
						registerReceiver(br, intentFilter);
					} catch (JSONException e) {
						e.printStackTrace();
						loading.showReload();
					}
				} else if (msg.what == Const.MSG_GET_FAIL) {
					loading.showReload();
				}
			}

		};
	};

	/**
	 * 专题id
	 */
	int topicId;

	public static Bundle getBundle(int topicId) {
		Bundle bundle = new Bundle();
		bundle.putInt("id", topicId);
		return bundle;
	}

	@Override
	public void initData() {
		Bundle bundle = getIntent().getExtras();
		topicId = bundle.getInt("id");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_game_topic_detail);

		initData();
		initView();
		initEvent();

		lvTopicGames.addHeaderView(llTopicListHead, null, false);
		excute();

	}

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
		NetStat.onPausePage("GameTopicDetailActivity");
	}

	@Override
	public void initView() {
		lvTopicGames = (ListView) findViewById(R.id.topicGames);
		btnBack = findViewById(R.id.top_back);
		tvTitle = (TextView) findViewById(R.id.title_text);
		ivTitle = findViewById(R.id.top_no_text_title);
		ivTitle.setVisibility(View.VISIBLE);
		tvTitle.setVisibility(View.VISIBLE);
		btnBack.setVisibility(View.VISIBLE);
		tvTitle.setText(getResources().getString(R.string.egame_topic_title));
		llTopicListHead = (LinearLayout) LayoutInflater.from(GameTopicDetailActivity.this).inflate(R.layout.egame_game_topic_detail_head, null);
		tvTopicName = (TextView) llTopicListHead.findViewById(R.id.topicName);
		tvTopicContenxt = (TextView) llTopicListHead.findViewById(R.id.topicContenxt);
		loading = new Loading(GameTopicDetailActivity.this);
	}

	@Override
	public void initViewData() {
		if (bean != null) {
			tvTopicName.setText(bean.getTopicName());
			tvTopicContenxt.setText(bean.getTopicDesc());
			adapter = new GameTopicDetailAdapter(bean.getGameList(), GameTopicDetailActivity.this, SourceUtils.TOPIC_LIST);
			lvTopicGames.setAdapter(adapter);
			new GameTopIconTask(bean.getGameList(), adapter).execute("");
		}
	}

	@Override
	public void initEvent() {
		btnBack.setOnClickListener(this);
		loading.setOnReloadClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loading.showLoading();
				excute();
			}
		});
		lvTopicGames.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
				GameTopListBean game = bean.getGameList().get(index - 1);
				Bundle bundle = GamesDetailActivity.makeIntentData(game.getGameId(), SourceUtils.TOPIC_LIST);
				Intent intent = new Intent(GameTopicDetailActivity.this, GamesDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}

	@Override
	public void onClick(View v) {
		if (v == btnBack) {
			finish();
		}
	}

	void excute() {
		loading.showLoading();
		new Thread(new GetStringRunnable(handler, Urls.getGameTopicDetailUrl(GameTopicDetailActivity.this, topicId + ""))).start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bean != null) {
			for(GameTopListBean bean : this.bean.getGameList()){
				if(bean.getIcon()!=null&& !bean.getIcon().getBitmap().isRecycled()){
					bean.getIcon().getBitmap().recycle();
				}
				if(bean.getPreview()!=null&&!bean.getPreview().getBitmap().isRecycled()){
					bean.getPreview().getBitmap().recycle();
				}
			}
			adapter.getDbService().close();
			unregisterReceiver(br);
		}
	}

	@Override
	public Activity getDialogActivity() {
		return this;
	}

}
