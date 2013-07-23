package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.GameSortBean;

/**
 * @desc 游戏分类列表中的adapter
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenvov-cw.com
 * 
 * @timer 2011-12-26
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameSortAdapter extends BaseAdapter {
	Context ctx;
	List<GameSortBean> list;

	public GameSortAdapter(Context context, List<GameSortBean> list) {
		this.ctx = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout.egame_gamesort_item, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GameSortBean bean = list.get(position);
//		L.d("dd", bean.getGameClassName() + "||" + bean.getTypeName());
		if ("".equals(bean.getGameClassName()) || null == bean.getGameClassName()
				|| "null".equals(bean.getGameClassName())) {
			holder.name.setText(bean.getTypeName());
		} else {
			holder.name.setText(bean.getGameClassName());
		}

		holder.count.setText(ctx.getString(R.string.egame_game_count, "" + bean.getTotalNumber()));
		if (bean.getIcon() != null) {
			holder.icon.setBackgroundDrawable(bean.getIcon());
		} else {
			holder.icon.setBackgroundResource(R.drawable.egame_defaultgamepic);
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView icon;
		public TextView name;
		public TextView count;
	}
}
