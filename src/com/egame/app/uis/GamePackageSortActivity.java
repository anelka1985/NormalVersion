package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.GamePackageAdapter;
import com.egame.app.receivers.ListNotifyReceiver;
import com.egame.app.tasks.GamePackageTask;
import com.egame.app.tasks.GetListIconAsyncTask;
import com.egame.beans.GameInPackageBean;
import com.egame.beans.GamePackageBean;
import com.egame.config.Const;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.Loading;
import com.egame.utils.ui.StringUtil;
import com.eshore.network.stat.NetStat;

public class GamePackageSortActivity extends Activity implements BaseActivity, OnItemClickListener, OnClickListener {
	EgameApplication application;
	/** 顶部返回按钮 */
	private Button btnBack;
	/** 标题 */
	private TextView tvTitle;
	/** 游戏分类详情列表 */
	private ListView lvGamePackage;
	private List<GamePackageBean> list = new ArrayList<GamePackageBean>();
	private List<GetListIconAsyncTask<GameInPackageBean>> taskList = new ArrayList<GetListIconAsyncTask<GameInPackageBean>>();
	private GamePackageAdapter adapter;
	Loading loading;
	int selectedItem = 0;
	ListNotifyReceiver br;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EgameApplication) getApplication();
		setContentView(R.layout.egame_gamesortpackage);
		initView();
		adapter = new GamePackageAdapter(GamePackageSortActivity.this, list);
		if (adapter == null) {
			Toast.makeText(GamePackageSortActivity.this, adapter.toString(), Toast.LENGTH_SHORT).show();
		} else {
			lvGamePackage.setAdapter(adapter);
			initData();
			initViewData();
			initEvent();
		}
		EgameApplication.Instance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("GamePackageActivity");
	}

	@Override
	protected void onDestroy() {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i) != null) {
				taskList.get(i).stop();
			}
		}
		List<GamePackageBean> cacheList = application.getPackageListBeanCache().getList();
		cacheList.clear();
		cacheList.addAll(list);
		application.getPackageListBeanCache().releaseIcon();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v == btnBack) {
			finish();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(GamePackageSortActivity.this, GamePackageDetailActivity.class);
		intent.putExtras(GamePackageDetailActivity.makeIntentData(list.get(arg2).getPackageId()));
		startActivityForResult(intent, 0);
	}

	/**
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		L.d("游戏包", "" + resultCode);
		if (resultCode == Const.MSG_GET_SUCCESS) {
			taskList.clear();
			list.clear();
			adapter.notifyDataSetChanged();
			lvGamePackage.setVisibility(View.GONE);
			loading.showLoading();
			new GamePackageTask(GamePackageSortActivity.this, list, application.getPhoneNum(), LoginDataStoreUtil
					.fetchUser(GamePackageSortActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA()).execute();
		}
	}

	@Override
	public void initData() {
		loading.setVisibility(View.VISIBLE);
		if (application.getPackageListBeanCache().isFinish()) {
			if (application.getPackageListBeanCache().getList().size() > 0) {
				list.addAll(application.getPackageListBeanCache().getList());
				adapter.notifyDataSetChanged();
				lvGamePackage.setVisibility(View.VISIBLE);
				loading.setVisibility(View.GONE);
			}
			for (int i = 0; i < list.size(); i++) {
				taskList.add(new GetListIconAsyncTask<GameInPackageBean>(list.get(i).getList(), adapter));
				taskList.get(i).execute("");
			}

		} else {
			new GamePackageTask(GamePackageSortActivity.this, list, application.getPhoneNum(), LoginDataStoreUtil
					.fetchUser(GamePackageSortActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
					application.getUA()).execute();
		}
	}

	@Override
	public void initView() {
		lvGamePackage = (ListView) findViewById(R.id.gameSortDetail);
		btnBack = (Button) findViewById(R.id.back);
		tvTitle = (TextView) findViewById(R.id.title);
		loading = new Loading(GamePackageSortActivity.this);
	}

	@Override
	public void initViewData() {
		tvTitle.setText("分类-" + "游戏包");
	}

	@Override
	public void initEvent() {
		btnBack.setOnClickListener(this);
		lvGamePackage.setOnItemClickListener(this);
		loading.setOnReloadClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loading.showLoading();
				new GamePackageTask(GamePackageSortActivity.this, list, application.getPhoneNum(), LoginDataStoreUtil
						.fetchUser(GamePackageSortActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
						application.getUA()).execute();
			}
		});
	}

	public void dataCodeProcess(String dataCode) {
		if (dataCode.equals("0")) {
			loading.showReload();
		} else if (dataCode.equals("1")) {
			application.getPackageListBeanCache().setFinish(true);
			adapter.notifyDataSetChanged();
			lvGamePackage.setVisibility(View.VISIBLE);
			loading.setVisibility(View.GONE);
			for (int i = 0; i < list.size(); i++) {
				taskList.add(new GetListIconAsyncTask<GameInPackageBean>(list.get(i).getList(), adapter));
				L.d("游戏包", i + "");
				taskList.get(i).execute("");
			}
		}
	}
}