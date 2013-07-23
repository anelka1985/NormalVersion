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
import com.egame.beans.NoviceBean;

/**
 * @author LiuHan
 * 
 */
public class NoviceAdapters extends BaseAdapter {
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 装载用户信息的数据集合
	 */
	private List<NoviceBean> list;

	public NoviceAdapters(Context context, List<NoviceBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater factory = LayoutInflater.from(context);
		RelativeLayout view = (RelativeLayout) factory.inflate(R.layout.egame_novice_items, null);
		// 用户头像
		ImageView userIcon = (ImageView) view.findViewById(R.id.m_friend_icon);
		if ((list.get(position).getmUserGexOld()).contains("男")) {
			userIcon.setBackgroundResource(R.drawable.egame_icon_men);
		} else {
			userIcon.setBackgroundResource(R.drawable.egame_icon_made);
		}
		if (null != list.get(position).getIcon()) {
			userIcon.setBackgroundDrawable(list.get(position).getIcon());
		}

		// 用户的姓名
		TextView userName = (TextView) view.findViewById(R.id.m_friend_name);
		userName.setText(list.get(position).getmUserName());
		// 用户的性别和年龄
		TextView userGexOld = (TextView) view.findViewById(R.id.m_friend_gex);
		if (list.get(position).getmUserGexOld().length() < 2) {
			userGexOld.setText("");
		} else {
			userGexOld.setText(list.get(position).getmUserGexOld());
		}

		// 用户的住址和爱玩游戏的类型
		TextView addressType = (TextView) view.findViewById(R.id.m_friend_address);
		addressType.setText(list.get(position).getmAddressType());
		// 添加好友按钮
		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.m_addfriend);

		RelativeLayout rl2 = (RelativeLayout) view.findViewById(R.id.m_addfriend1);

		if (list.get(position).isAddSuccess()) {
			rl.setVisibility(View.GONE);
			rl2.setVisibility(View.VISIBLE);
		} else {
			rl2.setVisibility(View.GONE);
			rl.setVisibility(View.VISIBLE);
		}
		return view;
	}
}
