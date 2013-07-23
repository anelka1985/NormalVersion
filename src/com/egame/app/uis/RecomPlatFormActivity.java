/**
 * 
 */
package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.ShareFriendAdapter;
import com.egame.app.tasks.GetListIconAsyncTask;
import com.egame.app.tasks.GetPlatForFriendTask;
import com.egame.app.tasks.SharePlatFormTask;
import com.egame.app.widgets.MyListView;
import com.egame.beans.ShareFriendBean;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.Loading;

/**
 * @author LiuHan
 * 
 */
public class RecomPlatFormActivity extends Activity implements OnClickListener {

	private ImageView mAllSelect;
	/** 返回按钮 */
	private TextView mBack;
	/** 分享按钮 */
	private RelativeLayout mShare;
	/** 好友列表 */
	private MyListView mListView;
	private ShareFriendAdapter adapter;
	private List<ShareFriendBean> friendList = new ArrayList<ShareFriendBean>();
	private GetListIconAsyncTask<ShareFriendBean> getListIconTask;
	private TextView selectNums;
	private int page = 0;
	private boolean isLastPage = false;
	private int mCurrenNum = 0;// 当前列表的好友数
	private Loading loading;

	/**
	 * 取得UI控件的引用
	 */
	private void initControlUI() {
		loading = new Loading(RecomPlatFormActivity.this);
		mBack = (TextView) this.findViewById(R.id.m_back);
		mBack.setOnClickListener(this);
		mShare = (RelativeLayout) this.findViewById(R.id.m_share);
		mShare.setOnClickListener(this);
		mListView = (MyListView) this.findViewById(R.id.m_friend_list);
		mAllSelect = (ImageView) this.findViewById(R.id.m_all_icon);
		mAllSelect.setOnClickListener(this);
		selectNums = (TextView) this.findViewById(R.id.m_number);
		updateSelectNum();
		addListViewEvent();
		addLoadingEvent();

	}

	private void addListViewEvent() {
		mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				updateList(arg2);
			}

		});
		mListView.setonRefreshListener(new MyListView.OnRefreshListener() {

			@Override
			public void onFootRefresh() {
				Log.i("RecomPlatFormActivity", "执行");
				if (isLastPage) {
					// 如果没有下一页了,就不再进行滚动加载
					L.d("view", "is last page");
				} else {
					Log.i(RecomPlatFormActivity.class.getCanonicalName(),
							"page==" + page);
					page = page + 1;
					new GetPlatForFriendTask(RecomPlatFormActivity.this, page)
							.execute(LoginDataStoreUtil.fetchUser(
									RecomPlatFormActivity.this,
									LoginDataStoreUtil.LOG_FILE_NAME)
									.getUserId());
					mListView.onFootRefreshComplete(12);
				}

			}
		});
	}

	private void addLoadingEvent() {
		loading.setOnReloadClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = 0;
				loading.showLoading();
				new GetPlatForFriendTask(RecomPlatFormActivity.this, page)
						.execute(LoginDataStoreUtil.fetchUser(
								RecomPlatFormActivity.this,
								LoginDataStoreUtil.LOG_FILE_NAME).getUserId());
			}
		});
	}

	private void updateList(int position) {
		if (friendList.get(position).isSelect()) {
			// 选中
			friendList.get(position).setSelect(false);
		} else {
			friendList.get(position).setSelect(true);
		}
		adapter.notifyDataSetChanged();
		if (isSelectAll()) {
			mAllSelect.setImageDrawable(this.getResources().getDrawable(
					R.drawable.egame_lselect_contactson));
		} else {
			mAllSelect.setImageDrawable(this.getResources().getDrawable(
					R.drawable.egame_select_contactsoff));
		}
		updateSelectNum();
	}

	private List<String> getSelectList() {
		List<String> list = new ArrayList<String>();
		if (list.size() > 0) {
			list.clear();
		}
		for (int i = 0; i < friendList.size(); i++) {
			if (friendList.get(i).isSelect()) {
				String userId = String.valueOf(friendList.get(i).getUserId());
				list.add(userId);
			}
		}

		return list;
	}

	private void updateSelectNum() {
		if (getSelectList().size() == 0) {
			selectNums.setText("您还没有选择任何好友哦");
			selectNums.setTextColor(Color.GRAY);
		} else {
			selectNums.setText("您选中的好友：" + getSelectList().size());
			selectNums.setTextColor(Color.BLUE);
		}
	}

	private boolean isSelectAll() {
		for (int i = 0; i < friendList.size(); i++) {
			if (!friendList.get(i).isSelect()) {
				return false;
			}
		}
		return true;
	}

	private void selectOrcancelAll(boolean blean) {
		for (int i = 0; i < friendList.size(); i++) {
			friendList.get(i).setSelect(blean);
		}
		if (null != adapter) {
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 好友列表不为空时调用该函数
	 * 
	 * @param list
	 * @param index
	 */
	public void loadFriendData(List<ShareFriendBean> list, int index) {
		// this.friendList = list;
		this.friendList.addAll(list);
		adapter = new ShareFriendAdapter(RecomPlatFormActivity.this,
				this.friendList);
		mListView.setAdapter(adapter);
		mListView.onFootRefreshComplete(13);
		getListIconTask = new GetListIconAsyncTask<ShareFriendBean>(
				this.friendList, adapter);
		getListIconTask.execute("");
		if (index == 0) {
			mCurrenNum = this.friendList.get(0).getTotalNum();
		}
		// 判断是不是加载完成
		if (this.friendList.size() >= mCurrenNum) {
			isLastPage = true;
			mListView.onFootRefreshComplete(9);
		}

		if (isSelectAll()) {
			mAllSelect.setImageDrawable(this.getResources().getDrawable(
					R.drawable.egame_lselect_contactson));
		} else {
			mAllSelect.setImageDrawable(this.getResources().getDrawable(
					R.drawable.egame_select_contactsoff));
		}

	}

	public void updateLoading(int type) {
		if (type == 0) {
			// 加载数据成功
			loading.setVisibility(View.GONE);
		} else {
			// 加载数据失败
			if (page == 0) {
				loading.showReload();
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == mBack) {
			RecomPlatFormActivity.this.finish();
		} else if (arg0 == mShare) {
			// 分享游戏到好友
			if (getSelectList().size() == 0) {
				Toast.makeText(RecomPlatFormActivity.this, "您还没有选中任何好友哦！",
						Toast.LENGTH_SHORT).show();
			} else {
				new SharePlatFormTask(RecomPlatFormActivity.this,
						getSelectList()).execute(this.getIntent()
						.getStringExtra("gameId"));
			}
		} else if (mAllSelect == arg0) {
			if (isSelectAll()) {
				selectOrcancelAll(false);
				mAllSelect.setImageDrawable(this.getResources().getDrawable(
						R.drawable.egame_select_contactsoff));
			} else {
				selectOrcancelAll(true);
				mAllSelect.setImageDrawable(this.getResources().getDrawable(
						R.drawable.egame_lselect_contactson));
			}
			updateSelectNum();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_share_friend_layot);
		initControlUI();
		if (!"".equals(this.getIntent().getStringExtra("gameId"))) {
			loading.showLoading();
			new GetPlatForFriendTask(this, page).execute(LoginDataStoreUtil
					.fetchUser(RecomPlatFormActivity.this,
							LoginDataStoreUtil.LOG_FILE_NAME).getUserId());
		} else {
			Toast.makeText(this, "数据获取失败，请稍候重试", Toast.LENGTH_SHORT).show();
		}

		EgameApplication.Instance().addActivity(this);
	}
}
