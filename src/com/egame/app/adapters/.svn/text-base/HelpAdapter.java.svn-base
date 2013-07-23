package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.HelpListBean;

/**
 * 描述：帮助列表数据适配器
 * 
 * @author LiuHan
 * @version 1.0 create date 2011-12-29
 */
public class HelpAdapter extends BaseAdapter {
	
	/** 存放帮助信息的数据集合 */
	private List<HelpListBean> mListData;
	
	/** 上下文 */
	private Context mContext;

	/**
	 * @param list
	 * @param listView
	 */
	public HelpAdapter(Context context, List<HelpListBean> list) {
		this.mContext = context;
		this.mListData = list;
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout mHelpItem = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.egame_helpcenter_item, null);
		((TextView) mHelpItem.findViewById(R.id.m_help_name)).setText(mListData.get(position).getHelpItemName());
		((TextView) mHelpItem.findViewById(R.id.m_access_number)).setText("(" + mListData.get(position).getAccessNumber() + ")");
		return mHelpItem;
	}

}
