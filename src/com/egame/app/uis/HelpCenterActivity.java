/**
 * 
 */
package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ListView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.HelpAdapter;
import com.egame.beans.HelpListBean;
import com.egame.utils.ui.UIUtils;
import com.eshore.network.stat.NetStat;

/**
 * 描述:帮助中心界面
 * 
 * @author LiuHan
 * @version 1.0 create date 2011-12-28
 * 
 */
public class HelpCenterActivity extends Activity implements OnClickListener {
	/** 触发“返回”事件的UI控件 */
	private TextView mBack;
	/** 接受用户信息输入UI控件 */
//	private EditText mInputEdit;
	/** 触发“搜索”事件的UI控件 */
	private TextView mSearchHelp;
	/** "疑问帮助"UI控件 */
	private TextView mDoubtTab;
	/** "游戏帮助"UI控件 */
	private TextView mGameTab;
	/** 列表显示"疑问帮助"的UI控件 */
	private ListView mDoubtListView;
	/** 列表显示"游戏帮助"的UI控件 */
	private ListView mGameListView;
	/** 装载”疑问帮助“和”游戏帮助"的数据集合 */
	private List<View> viewList;
	/***/
	private OnPreDrawListener onPreDrawListener;
	/** 帮助信息数据集合 */
	private List<HelpListBean> mHelpList;
	/** 帮助信息实体bean */
	private HelpListBean mHelpListBean;
	/** 数据适配器 */
	private HelpAdapter mDoubtAdatper, mGameAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_helpcenter);
		// 取得UI控件的引用
		initUIControl();
		//
		fillingListView();
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
		NetStat.onPausePage("HelpCenterActivity");
	}

	/**
	 * 填充列表
	 */
	private void fillingListView() {
		View headerView = getLayoutInflater().inflate(R.layout.egame_helpcenter_head, null);
		mDoubtAdatper = new HelpAdapter(HelpCenterActivity.this, getHelpInfomation(0));
		mGameAdapter = new HelpAdapter(HelpCenterActivity.this, getHelpInfomation(1));
		// 疑问帮助
		mDoubtListView.addHeaderView(headerView);
		mDoubtListView.setAdapter(mDoubtAdatper);
		// 游戏帮助
		mGameListView.addHeaderView(headerView);
		mGameListView.setAdapter(mGameAdapter);
	}

	/**
	 * 功能：返回帮助信息列表
	 */
	private List<HelpListBean> getHelpInfomation(int type) {
		mHelpList = new ArrayList<HelpListBean>();
		if (1 == type) {
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("4627");
			mHelpListBean.setHelpItemName("客户端上的游戏有免费的吗？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("2345");
			mHelpListBean.setHelpItemName("客户端使用收费吗？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("1344");
			mHelpListBean.setHelpItemName("我如何下载游戏？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("6785");
			mHelpListBean.setHelpItemName("我下载的游戏都....");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("4627");
			mHelpListBean.setHelpItemName("客户端上的游戏有免费的吗？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("2345");
			mHelpListBean.setHelpItemName("客户端使用收费吗？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("1344");
			mHelpListBean.setHelpItemName("我如何下载游戏？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("6785");
			mHelpListBean.setHelpItemName("我下载的游戏都....");
			mHelpList.add(mHelpListBean);

		} else {
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("2345");
			mHelpListBean.setHelpItemName("客户端使用收费吗？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("1344");
			mHelpListBean.setHelpItemName("我如何下载游戏？");
			mHelpList.add(mHelpListBean);
			mHelpListBean = new HelpListBean();
			mHelpListBean.setAccessNumber("6785");
			mHelpListBean.setHelpItemName("我下载的游戏都....");
			mHelpList.add(mHelpListBean);

		}

		return mHelpList;
	}

	/**
	 * 功能：取得UI控件的引用
	 */
	private void initUIControl() {
		// 数据集合
		mBack.setOnClickListener(this);
		// 编辑框
//		mInputEdit = (EditText) this.findViewById(R.id.m_input_edit);
		// "搜索"
		mSearchHelp = (TextView) this.findViewById(R.id.m_search_help);
		mSearchHelp.setOnClickListener(this);
		// "疑问帮助"
		mDoubtTab = (TextView) this.findViewById(R.id.m_doubt_tab);
		mDoubtTab.setOnClickListener(this);
		// "游戏帮助"
		mGameTab = (TextView) this.findViewById(R.id.m_game_tab);
		mGameTab.setOnClickListener(this);
		// “疑问帮助”ListView
		mDoubtListView = (ListView) this.findViewById(R.id.m_doubt_listview);
		// “游戏帮助"ListView
		mGameListView = (ListView) this.findViewById(R.id.m_game_listview);

		// 装载数据UI
		viewList.add(mDoubtTab);
		viewList.add(mGameTab);
		onPreDrawListener = UIUtils.initButtonState(mDoubtTab);
	}

	/**
	 * 功能：UI控件的单击事件的处理函数
	 */
	@Override
	public void onClick(View v) {
		if (v == mDoubtTab) {
			mDoubtListView.setVisibility(View.VISIBLE);
			// “游戏帮助"ListView
			mGameListView.setVisibility(View.GONE);
			UIUtils.buttonStateChange(viewList, v, onPreDrawListener);
		} else if (v == mGameTab) {
			mDoubtListView.setVisibility(View.GONE);
			// “游戏帮助"ListView
			mGameListView.setVisibility(View.VISIBLE);
			UIUtils.buttonStateChange(viewList, v, onPreDrawListener);
		} else if (v == mBack) {
			HelpCenterActivity.this.finish();
		}
	}

}
