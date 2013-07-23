package com.egame.app.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.GameListBean;
import com.egame.utils.ui.BaseAdapterEx;

/**
 * @desc 游戏列表适配器
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-27
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class SearchGameListAdapter extends BaseAdapterEx<GameListBean> {

	public SearchGameListAdapter(List<GameListBean> list, Activity context) {
		super(list, context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.egame_search_game_list_item, null);
			holder.flIconFrame = convertView.findViewById(R.id.iconFrame);
			holder.ivTip = (View) convertView.findViewById(R.id.tip);
			holder.ivIcon = (View) convertView.findViewById(R.id.icon);
			holder.tvName = (TextView) convertView.findViewById(R.id.name);
			holder.tvInfo = (TextView) convertView.findViewById(R.id.info);
			holder.ivStar = (View) convertView.findViewById(R.id.star);
//			holder.tvDownloadCount = (TextView) convertView
//					.findViewById(R.id.downloadCount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GameListBean bean = list.get(position);
		if (bean.getIcon() != null) {
			holder.ivIcon.setBackgroundDrawable(bean.getIcon());
		}
		if (bean.isHot()) {
			holder.ivTip.setVisibility(View.VISIBLE);
		} else {
			holder.ivTip.setVisibility(View.GONE);
		}
		if (bean.getSpanName() != null) {
			holder.tvName.setText(bean.getSpanName());
		} else {
			holder.tvName.setText(bean.getGameName());
		}
		if (bean.getSpanInfo() != null) {
			holder.tvInfo.setText(bean.getSpanInfo());
		} else {
			holder.tvInfo.setText(bean.getProvider());
		}
//		holder.tvDownloadCount.setText(String.valueOf(bean.getDownloadTimes()));
		switch (bean.getScore()) {
		case 0:
			holder.ivStar.setBackgroundResource(R.drawable.egame_star0);
			break;
		case 1:
			holder.ivStar.setBackgroundResource(R.drawable.egame_star1);
			break;
		case 2:
			holder.ivStar.setBackgroundResource(R.drawable.egame_star2);
			break;
		case 3:
			holder.ivStar.setBackgroundResource(R.drawable.egame_star3);
			break;
		case 4:
			holder.ivStar.setBackgroundResource(R.drawable.egame_star4);
			break;
		default:
			holder.ivStar.setBackgroundResource(R.drawable.egame_star5);
		}
		return convertView;
	}

	public final class ViewHolder {
		public View flIconFrame;
		public View ivTip;
		public View ivIcon;
		public TextView tvName;
		public TextView tvInfo;
		public View ivStar;
//		public TextView tvDownloadCount;
	}
}
