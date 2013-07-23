package com.egame.app.uis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.ContactsAdapter;
import com.egame.app.tasks.ContactsAsyncTask;
import com.egame.app.tasks.SendSMSAsyncTask;
import com.egame.beans.ContactsBean;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

/**
 * 描述：推荐到通讯录界面
 * 
 * @author LiuHan
 * @version 1.0 create date : Sat Dec 31 2011
 */
public class RecommContactsActivity extends Activity implements OnClickListener {
	public static RecommContactsActivity instance;
	/** 联系人列表 */
	private ListView mContactsListView;

	/** 触发”返回“事件的UI */
	private TextView mBack;

	/** 触发”分享“事件的UI */
	private RelativeLayout mShareLayout;

	/** 数据适配器 */
	private ContactsAdapter mContactsAdapter;

	/** 列表的形式显示选中的人数的UI */
	private TextView mShowNum;

	/** 全选按钮 */
	private TextView mAllSelect;

	/** 装载控制列表头的选择状态集合 */
	private Map<TextView, Integer> isControl = new HashMap<TextView, Integer>();

	/** 装载联系人信息的数据集合 */
	private List<ContactsBean> list = new ArrayList<ContactsBean>();
	private String gameId, gameName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_recomm_contacts);
		instance = this;
		// 取得UI控件的引用
		initUIControl();
		// 异步任务取得联系人数据
		new ContactsAsyncTask(this, list).execute(0);
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
		NetStat.onPausePage("RecommContactsActivity");
	}

	/**
	 * 功能：取得UI控件的引用
	 */
	public void initUIControl() {
		// UI控件列表显示联系人信息
		mContactsListView = (ListView) this.findViewById(R.id.m_contacts_list);
		// 列表头部布局
		View headerView = getLayoutInflater().inflate(
				R.layout.egame_contacts_head, null);
		// 显示选中联系人的数目
		mShowNum = (TextView) headerView.findViewById(R.id.m_selected_num);
		// 初始化选中0人
		isControl.put(mShowNum, 0);
		// 全选按钮
		mAllSelect = (TextView) headerView.findViewById(R.id.m_contact_all);
		// 添加HeaderView
		mContactsListView.addHeaderView(headerView);
		mBack = (TextView) this.findViewById(R.id.m_back);// 返回按钮
		mBack.setOnClickListener(this);// 添加事件监听
		mShareLayout = (RelativeLayout) this.findViewById(R.id.m_share);// 分享布局
		mShareLayout.setOnClickListener(this);// 添加事件监听
	}

	/**
	 * 功能：为ListView添加数据适配器 ,更新列表数据的显示
	 */
	public void initUIShow() {
		// 创建数据适配器的实例
		mContactsAdapter = new ContactsAdapter(this, list);
		// 添加适配器
		mContactsListView.setAdapter(mContactsAdapter);
		mContactsListView
				.setOnItemClickListener(new MyOnItemClistListener(list));
		mContactsAdapter.notifyDataSetChanged();
		if (list.size() == 0) {

		}
	}

	/**
	 * 功能：改变用户选中状态
	 */
	public void flushHeaderContent(List<ContactsBean> mResult, TextView icon,
			TextView content) {
		int selectNum = 0;
		for (int i = 0; i < mResult.size(); i++) {
			if (mResult.get(i).ismIsSelect()) {
				selectNum++;
			}
		}
		if (selectNum == mResult.size()) {
			icon.setBackgroundResource(R.drawable.egame_lselect_contactson);
			isControl.put(mShowNum, 1);
		} else {
			icon.setBackgroundResource(R.drawable.egame_select_contactsoff);
			isControl.put(mShowNum, 0);
		}
		content.setText("已选中" + selectNum + "位好友");
	}

	/**
	 * 功能：相关UI控件的单击事件
	 */
	@Override
	public void onClick(View v) {

		if (v == mBack) {
			RecommContactsActivity.this.finish();
		} else if (v == mShareLayout) {
			/** 发送短信人的数据集合 */
			List<ContactsBean> selectedList = new ArrayList<ContactsBean>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).ismIsSelect()) {
					selectedList.add(list.get(i));
				}
			}
			if (selectedList.size() > 0) {
				if ("game".equals(PreferenceUtil
						.fetchType(RecommContactsActivity.this))) {
					if (null != RecommContactsActivity.this.getIntent()) {
						gameId = RecommContactsActivity.this.getIntent()
								.getStringExtra("gameId");
						gameName = RecommContactsActivity.this.getIntent()
								.getStringExtra("gameName");
						if (null != gameId && null != gameName
								&& !"".equals(gameId) && !"".equals(gameName)) {
							new SendSMSAsyncTask(RecommContactsActivity.this,
									selectedList, gameId, gameName).execute("");
						}
					}else{
						ToastUtil.show(RecommContactsActivity.this, "数据丢失，请重试");
						RecommContactsActivity.this.finish();
					}

				} else {
					new SendSMSAsyncTask(RecommContactsActivity.this,
							selectedList).execute("");
				}

			} else {
				ToastUtil.show(RecommContactsActivity.this, R.string.egame_nhmyxzyfxdlxro);
			}

		}

	}

	/**
	 * 处理列表事件
	 */
	class MyOnItemClistListener implements ListView.OnItemClickListener {
		List<ContactsBean> mResults;

		public MyOnItemClistListener(List<ContactsBean> mResults) {
			this.mResults = mResults;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == 0) {
				// 列表头部布局单击事件
				if (isControl.get(mShowNum) == 0) {
					mAllSelect
							.setBackgroundResource(R.drawable.egame_lselect_contactson);
					isControl.put(mShowNum, 1);
					for (int i = 0; i < mResults.size(); i++) {
						mContactsAdapter.getItem(i).setmIsSelect(true);
					}
				} else {
					mAllSelect
							.setBackgroundResource(R.drawable.egame_select_contactsoff);
					isControl.put(mShowNum, 0);
					for (int i = 0; i < mResults.size(); i++) {
						mContactsAdapter.getItem(i).setmIsSelect(false);
					}
				}
			} else {
				if (mContactsAdapter.getItem(arg2 - 1).ismIsSelect() == true) {
					mContactsAdapter.getItem(arg2 - 1).setmIsSelect(false);
				} else {
					mContactsAdapter.getItem(arg2 - 1).setmIsSelect(true);
				}
			}
			flushHeaderContent(mResults, mAllSelect, mShowNum);
			mContactsAdapter.notifyDataSetChanged();
		}
	}
}
