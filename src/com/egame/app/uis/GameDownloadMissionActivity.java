package com.egame.app.uis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.GameDownloadMissionListAdapter;
import com.egame.app.services.DBService;
import com.egame.beans.DownloadListBean;
import com.egame.config.Const;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.BaseActivity;
import com.eshore.network.stat.NetStat;

public class GameDownloadMissionActivity extends Activity implements BaseActivity, OnClickListener {

	/**
	 * 游戏列表实体列表
	 */
	List<DownloadListBean> downloadList = new ArrayList<DownloadListBean>();

	/**
	 * 下载任务适配器
	 */
	GameDownloadMissionListAdapter gamedownloadListAdapter;

	ListView gamedownloadList;

	/**
	 * 下载列表图片缓存
	 */
	protected Map<String, Bitmap> iconMap = new HashMap<String, Bitmap>();

	DBService dbService;

	int expendIndex = -1;

	String[] downstate = null;

	private TextView noDownloadHint = null;

	// private int downPicCount = 0;

	private List<String> downloaded = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_game_downloadmisson);

		initData();
		initView();
		initEvent();
		initViewData();
		IntentFilter intentFilter = new IntentFilter(Const.ACTION_DOWNLOAD_STATE);
		this.registerReceiver(br, intentFilter);
		downstate = new String[] { getResources().getString(R.string.egame_DownloadMission_xzwcdjjxaz),
				getResources().getString(R.string.egame_DownloadMission_xzzdjqx), getResources().getString(R.string.egame_DownloadMission_yczzdjcs), "", "已暂停" };
		dbService = new DBService(this);
		dbService.open();
		downloadList.addAll(getData());
		gamedownloadListAdapter.notifyDataSetChanged();
		EgameApplication.Instance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		dbService.close();
		unregisterReceiver(br);
		super.onDestroy();
	}

	BroadcastReceiver br = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (GameDownloadMissionActivity.this.isFinishing()) {
				return;
			}
			if (intent.getExtras().getString("msg").equals("refresh") || intent.getExtras().getString("msg").equals("change")) {
				downloadList.clear();
				downloadList.addAll(getData());
				gamedownloadListAdapter.notifyDataSetChanged();
				// new GetDownloadingIconTask().execute();
			}
		}
	};

	private List<DownloadListBean> getData() {
		List<DownloadListBean> list = new ArrayList<DownloadListBean>();
		Cursor cursor = dbService.getNotInstalledGame();
		if (cursor.moveToFirst()) {
			do {
				DownloadListBean bean = new DownloadListBean(cursor);
				list.add(bean);
				if (null == iconMap.get(bean.getServiceid())) {
					if (!downloaded.contains(bean.getServiceid())) {
						new Thread(new GetPic(bean)).start();
						downloaded.add(bean.getServiceid());
					}
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		L.d("update", "down list is:" + list.size());
		if (list.size() < 1) {
			noDownloadHint.setVisibility(View.VISIBLE);
			noDownloadHint.setText(R.string.egame_manager_nodownload);
			gamedownloadList.setVisibility(View.GONE);
		} else {
			gamedownloadList.setVisibility(View.VISIBLE);
			noDownloadHint.setVisibility(View.GONE);
		}
		// iconCache.clear();
		return list;
	}

	class GetPic implements Runnable {

		DownloadListBean bean;

		GetPic(DownloadListBean bean) {
			this.bean = bean;
		}

		@Override
		public void run() {
			Bitmap icon;
			try {
				icon = HttpConnect.getHttpBitmap(bean.getIconurl());
				iconMap.put(bean.getServiceid(), icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		noDownloadHint = (TextView) findViewById(R.id.nodownloadhint);
		gamedownloadList = (ListView) findViewById(R.id.gameList);
		gamedownloadListAdapter = new GameDownloadMissionListAdapter(downloadList, iconMap, GameDownloadMissionActivity.this);
		gamedownloadList.setAdapter(gamedownloadListAdapter);
	}

	@Override
	public void initViewData() {
	}

	@Override
	public void initEvent() {
		gamedownloadList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (gamedownloadListAdapter.getExpendIndex() == arg2) {
					gamedownloadListAdapter.setExpendIndex(-999);
					gamedownloadListAdapter.notifyDataSetChanged();
				} else {
					gamedownloadListAdapter.setExpendIndex(arg2);
					gamedownloadListAdapter.notifyDataSetChanged();
					if (arg2 == downloadList.size() - 1) {
						arg0.setSelection(100);
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
		L.d("download", "activity onResume");
	}

	/**
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("GameDownloadMissionActivity");
	}
}
