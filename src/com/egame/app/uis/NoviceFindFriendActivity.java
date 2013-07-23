package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.adapters.NoviceAdapters;
import com.egame.app.tasks.AddFriendAsyncTask;
import com.egame.app.tasks.GetFindFriendAsyncTask;
import com.egame.app.tasks.GetListIconAsyncTask;
import com.egame.app.widgets.MyListView;
import com.egame.beans.NoviceBean;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.Loading;
import com.eshore.network.stat.NetStat;

/**
 * 描述:新手任务找朋友界面类
 * 
 * @author LiuHan
 * @version 1.0 create on:Friday ,February ,10,2012
 */
public class NoviceFindFriendActivity extends Activity implements OnClickListener {
	/** NoviceFindFriendActivity静态实例 */
	public static NoviceFindFriendActivity instance;
	/** mNoviceNames,欢迎来到爱游戏社区 */
	private TextView mNoviceNames;
	/** 触发返回事件的UI控件 */
	private TextView mFriendBack;
	/** 昵称显示UI控件 */
	private TextView mNoviceName;
	/** 下一步 UI控件 */
	private Button mNextStage;
	/** 数据适配器 */
	private NoviceAdapters mNoviceAdatper;
	/** 好友列表数据集合 */
	public List<NoviceBean> list = new ArrayList<NoviceBean>();
	/** 显示好友列表的UI控件 */
	public MyListView mFriendListview;
	/** 取得好友头像的异步任务 */
	private GetListIconAsyncTask<NoviceBean> getListIconTask;

	public int mCount = 0;
	public int index = 0, mTotalNum = 0;
	private boolean isLastPage = false;
	private Loading loading;
	int age;
	String province;
	String city;
	String userId;
	int grender;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_novice_findfriend);
		instance = this;
		// 取得UI控件的引用
		initControlUI();
		Bundle bundle = getIntent().getBundleExtra("bundle");
		age = bundle.getInt("age");
		province = bundle.getString("province");
		city = bundle.getString("city");
		userId = bundle.getString("userId");
		grender = bundle.getInt("grender");
		// new GetFindFriendAsyncTask(NoviceFindFriendActivity.this,
		// bundle.getInt("age"), bundle.getString("province"),
		// bundle.getLong("userId"),
		// index, bundle.getInt("grender")).execute("");
		new GetFindFriendAsyncTask(NoviceFindFriendActivity.this, age, province, city, userId, index, grender).execute("");
	}

	/**
	 * 功能：取得UI控件的引用
	 */
	private void initControlUI() {
		loading = new Loading(this);
		mNoviceNames = (TextView) this.findViewById(R.id.m_novice_name);
		mNoviceNames.setText(LoginDataStoreUtil.fetchUser(NoviceFindFriendActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getNickName()
				+ "，欢迎来到爱游戏社区");
		mNoviceName = (TextView) this.findViewById(R.id.m_novice_name);
		mNoviceName.setFocusable(true);
		mFriendBack = (TextView) this.findViewById(R.id.m_friend_back);
		mFriendBack.setOnClickListener(this);
		mNextStage = (Button) this.findViewById(R.id.m_next_stage);
		mNextStage.setOnClickListener(this);
		mNextStage.setEnabled(false);
		mNextStage.setTextColor(Color.WHITE);
		mNextStage.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.egame_update_forbid_bg));
		mFriendListview = (MyListView) this.findViewById(R.id.m_friend_listview);
		LayoutInflater factory = LayoutInflater.from(this);
		LinearLayout view = (LinearLayout) factory.inflate(R.layout.egame_novice_findfriend_head, null);
		mFriendListview.addHeaderView(view, null, false);
		addEventListView(mFriendListview);// 给好友列表添加单击事件处理
		addLoadingEvent();

	}

	private void addLoadingEvent() {
		loading.setOnReloadClickListener(new OnClickListener() {

			public void onClick(View v) {
				index = 0;
				loading.showLoading();
				new GetFindFriendAsyncTask(NoviceFindFriendActivity.this, age, province, city, userId, index, grender).execute("");
			}

		});
	}

	public void updateNextButton() {
		mNextStage.setEnabled(true);
		mNextStage.setTextColor(this.getResources().getColor(R.color.egame_next_color));
		mNextStage.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.egame_btn_green_circularity_selector));
	}

	public void updateLoading(int type, int index) {
		if (index == 0) {
			if (type == 0) {
				loading.setVisibility(View.GONE);
			} else {
				loading.showReload();
			}
		}
	}

	/**
	 * 相关UI单机事件处理
	 */

	public void onClick(View v) {
		if (v == mFriendBack) {
			DialogUtil.showNoviceCancelDialog(this);
		} else if (v == mNextStage) {
			Intent intent = new Intent(NoviceFindFriendActivity.this, NoviceUpPhotoActivity.class);
			startActivity(intent);
			NoviceFindFriendActivity.this.finish();
		}
	}

	/**
	 * 好友列表的事件处理函数
	 * 
	 * @param listview
	 */
	public void addEventListView(MyListView listview) {
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				NoviceBean nBean = list.get(arg2 - 1);
				if (!nBean.isAddSuccess()) {
					// 启动异步任务
					new AddFriendAsyncTask(NoviceFindFriendActivity.this, mNoviceAdatper, nBean.getUserId(), LoginDataStoreUtil.fetchUser(
							NoviceFindFriendActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(), arg2 - 1, list).execute("");
				}

			}

		});
		listview.setonRefreshListener(new MyListView.OnRefreshListener() {

			public void onFootRefresh() {
				Log.i("RecomPlatFormActivity", "执行");
				if (isLastPage) {
					// 如果没有下一页了,就不再进行滚动加载
					L.d("view", "is last page");
				} else {
					index = index + 1;
					new GetFindFriendAsyncTask(NoviceFindFriendActivity.this, age, province, city, userId, index, grender).execute("");
					mFriendListview.onFootRefreshComplete(12);
				}
			}
		});
	}

	/**
	 * 返回，菜单物理按键的事件处理
	 */

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_MENU)) {
			DialogUtil.showNoviceCancelDialog(this);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 加載好友列表的數據
	 * 
	 * @param list
	 */
	public void loadFriendList(List<NoviceBean> list, int index) {
		this.list = list;
		mNoviceAdatper = new NoviceAdapters(NoviceFindFriendActivity.this, list);
		mFriendListview.setAdapter(mNoviceAdatper);
		mFriendListview.onFootRefreshComplete(13);
		getListIconTask = new GetListIconAsyncTask<NoviceBean>(list, mNoviceAdatper);
		getListIconTask.execute("");
		if (index == 0) {
			this.mTotalNum = list.get(0).getmNumber();
		}
		if (list.size() >= this.mTotalNum) {
			isLastPage = true;
			mFriendListview.onFootRefreshComplete(9);
		}
	}

	public static Bundle createFindFriendBundle(int grender, int age, String city, String province, String userId) {
		Bundle bundle = new Bundle();
		bundle.putInt("grender", grender);
		bundle.putInt("age", age);
		bundle.putString("city", city);
		bundle.putString("province", province);
		bundle.putString("userId", userId);
		return bundle;
	}

	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("NoviceFindFriendActivity");
	}

}
