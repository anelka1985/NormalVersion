/**
 * 
 */
package com.egame.app.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.ContactsBean;

/**
 * 描述：联系人列表数据适配器
 * 
 * @author LiuHan
 * @version 1.0 create date :Sat Dec 31 2011
 */
public class ContactsAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 联系人列表 */
	private List<ContactsBean> mContactsList = new ArrayList<ContactsBean>();
	/** 联系人实体类对象 */
	private ContactsBean mContactsBean;

	/**
	 * 构造函数
	 */
	public ContactsAdapter(Context mContext, List<ContactsBean> mContactsList) {
		this.mContext = mContext;
		this.mContactsList = mContactsList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return mContactsList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public ContactsBean getItem(int position) {
		return mContactsList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mContactsBean = getItem(position);
		LinearLayout itemView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.egame_contacts_list_item, null);
		TextView mContactsName = (TextView) itemView
				.findViewById(R.id.m_contacts_name);
		mContactsName.setText(mContactsBean.getmContactsName());
		TextView mContactsPhone = (TextView) itemView
				.findViewById(R.id.m_contacts_phone);
		mContactsPhone.setText(mContactsBean.getmContactsPhone());
		TextView mContactsIcon = (TextView) itemView
				.findViewById(R.id.m_contacts_icon);
		if (mContactsBean.ismIsSelect() == false) {
			mContactsIcon
					.setBackgroundResource(R.drawable.egame_select_contactsoff);
		} else {
			mContactsIcon
					.setBackgroundResource(R.drawable.egame_lselect_contactson);
		}
		return itemView;
	}
}
