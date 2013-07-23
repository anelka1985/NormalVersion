/**
 * 
 */
package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.ShareFriendBean;

/**
 * @author LiuHan
 * 
 */
public class ShareFriendAdapter extends BaseAdapter {

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater factory = LayoutInflater.from(context);
		RelativeLayout view = (RelativeLayout) factory.inflate(
				R.layout.egame_share_friend_item, null);
		ImageView icon = (ImageView)view.findViewById(R.id.m_icon);
		if((1==list.get(position).getGrender())){
			icon.setBackgroundResource(R.drawable.egame_icon_men);
		}else{
			icon.setBackgroundResource(R.drawable.egame_icon_made);
		}
		if (null != list.get(position).getIcon()) {
			icon.setBackgroundDrawable(list.get(position).getIcon());
		}
		TextView name = (TextView)view.findViewById(R.id.m_friend_name);
		name.setText(list.get(position).getUserName());
		ImageView onlineIcon = (ImageView)view.findViewById(R.id.m_online);
		if(1==list.get(position).getOnlineStatus()){
			onlineIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.egame_icon_online));
		}else{
			onlineIcon.setVisibility(View.GONE);
		}
		ImageView selectIcon = (ImageView)view.findViewById(R.id.m_select_icon);
		if(list.get(position).isSelect()){
			selectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.egame_lselect_contactson));
		}else{
			selectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.egame_select_contactsoff));
		}
		return view;
	}
	
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 装载用户信息的数据集合
	 */
	private List<ShareFriendBean> list;
    
	/**
	 * 
	 */
	public ShareFriendAdapter(Context context, List<ShareFriendBean> list) {
		this.context = context;
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return list.get(position);
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
	

}

