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
import com.egame.beans.GameInPackageBean;
import com.egame.beans.GamePackageBean;

/**
 * @desc 游戏包页面的adapter
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
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
public class GamePackageAdapter extends BaseAdapter {
	List<GamePackageBean> list;
	Context ctx;

	public GamePackageAdapter(Context context, List<GamePackageBean> list) {
		this.ctx = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		GamePackageBean bean = list.get(position);
		List<GameInPackageBean> gameList = list.get(position).getList();
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout.egame_gamepackage_item, null);
			holder.packageName = (TextView) convertView.findViewById(R.id.packagename);
			holder.cost = (TextView) convertView.findViewById(R.id.cost);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.gameIcon1 = (ImageView) convertView.findViewById(R.id.gameIcon1);
			holder.gameName1 = (TextView) convertView.findViewById(R.id.gameName1);
			holder.gameIcon2 = (ImageView) convertView.findViewById(R.id.gameIcon2);
			holder.gameName2 = (TextView) convertView.findViewById(R.id.gameName2);
			holder.gameIcon3 = (ImageView) convertView.findViewById(R.id.gameIcon3);
			holder.gameName3 = (TextView) convertView.findViewById(R.id.gameName3);
			holder.gameIcon4 = (ImageView) convertView.findViewById(R.id.gameIcon4);
			holder.gameName4 = (TextView) convertView.findViewById(R.id.gameName4);
			holder.gameIcon5 = (ImageView) convertView.findViewById(R.id.gameIcon5);
			holder.gameName5 = (TextView) convertView.findViewById(R.id.gameName5);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (null == list.get(position)) {
//			L.d("dd", "null");
		}
		holder.gameIcon1.setVisibility(View.GONE);
		holder.gameName1.setVisibility(View.GONE);
		holder.gameIcon2.setVisibility(View.GONE);
		holder.gameName2.setVisibility(View.GONE);
		holder.gameIcon3.setVisibility(View.GONE);
		holder.gameName3.setVisibility(View.GONE);
		holder.gameIcon4.setVisibility(View.GONE);
		holder.gameName4.setVisibility(View.GONE);
		holder.gameIcon5.setVisibility(View.GONE);
		holder.gameName5.setVisibility(View.GONE);
		holder.packageName.setText("" + bean.getPackageName());
		if(bean.getIsorder().equals("0")){
			holder.cost.setText(ctx.getResources().getString(R.string.egame_zifei) + bean.getPrice());	
		}
		else if(bean.getIsorder().equals("1")){
			holder.cost.setText("已订购");
		}
//		holder.cost.setText(ctx.getResources().getString(R.string.egame_zifei) + bean.getPrice());
		holder.count.setText(bean.getDesc() + "");
//		L.d("dd", position + " size  is  " + bean.getList().size());
		if (bean.getTotal() >= 5) {
			holder.gameIcon1.setVisibility(View.VISIBLE);
			holder.gameName1.setVisibility(View.VISIBLE);
			if (null == gameList.get(0).getIcon()) {
				holder.gameIcon1.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon1.setBackgroundDrawable(gameList.get(0).getIcon());
			}
			holder.gameName1.setText(gameList.get(0).getGameName());
			holder.gameIcon2.setVisibility(View.VISIBLE);
			holder.gameName2.setVisibility(View.VISIBLE);
			if (null == gameList.get(1).getIcon()) {
				holder.gameIcon2.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon2.setBackgroundDrawable(gameList.get(1).getIcon());
			}
			holder.gameName2.setText(gameList.get(1).getGameName());
			holder.gameIcon3.setVisibility(View.VISIBLE);
			holder.gameName3.setVisibility(View.VISIBLE);
			if (null == gameList.get(2).getIcon()) {
				holder.gameIcon3.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon3.setBackgroundDrawable(gameList.get(2).getIcon());
			}
			holder.gameName3.setText(gameList.get(2).getGameName());
			holder.gameIcon4.setVisibility(View.VISIBLE);
			holder.gameName4.setVisibility(View.VISIBLE);
			if (null == gameList.get(3).getIcon()) {
				holder.gameIcon4.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon4.setBackgroundDrawable(gameList.get(3).getIcon());
			}
			holder.gameName4.setText(gameList.get(3).getGameName());
			holder.gameIcon5.setVisibility(View.VISIBLE);
			holder.gameName5.setVisibility(View.VISIBLE);
			if (null == gameList.get(4).getIcon()) {
				holder.gameIcon5.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon5.setBackgroundDrawable(gameList.get(4).getIcon());
			}
			holder.gameName5.setText(gameList.get(4).getGameName());
		} else if (bean.getTotal() == 4) {
			holder.gameIcon1.setVisibility(View.VISIBLE);
			holder.gameName1.setVisibility(View.VISIBLE);
			if (null == gameList.get(0).getIcon()) {
				holder.gameIcon1.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon1.setBackgroundDrawable(gameList.get(0).getIcon());
			}
			holder.gameName1.setText(gameList.get(0).getGameName());
			holder.gameIcon2.setVisibility(View.VISIBLE);
			holder.gameName2.setVisibility(View.VISIBLE);
			if (null == gameList.get(1).getIcon()) {
				holder.gameIcon2.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon2.setBackgroundDrawable(gameList.get(1).getIcon());
			}
			holder.gameName2.setText(gameList.get(1).getGameName());
			holder.gameIcon3.setVisibility(View.VISIBLE);
			holder.gameName3.setVisibility(View.VISIBLE);
			if (null == gameList.get(2).getIcon()) {
				holder.gameIcon3.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon3.setBackgroundDrawable(gameList.get(2).getIcon());
			}
			holder.gameName3.setText(gameList.get(2).getGameName());
			holder.gameIcon4.setVisibility(View.VISIBLE);
			holder.gameName4.setVisibility(View.VISIBLE);
			if (null == gameList.get(3).getIcon()) {
				holder.gameIcon4.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon4.setBackgroundDrawable(gameList.get(3).getIcon());
			}
			holder.gameName4.setText(gameList.get(3).getGameName());
		} else if (bean.getTotal() == 3) {
			holder.gameIcon2.setVisibility(View.VISIBLE);
			holder.gameName2.setVisibility(View.VISIBLE);
			if (null == gameList.get(0).getIcon()) {
				holder.gameIcon2.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon2.setBackgroundDrawable(gameList.get(0).getIcon());
			}
			holder.gameName2.setText(gameList.get(0).getGameName());
			holder.gameIcon3.setVisibility(View.VISIBLE);
			holder.gameName3.setVisibility(View.VISIBLE);
			if (null == gameList.get(1).getIcon()) {
				holder.gameIcon3.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon3.setBackgroundDrawable(gameList.get(1).getIcon());
			}
			holder.gameName3.setText(gameList.get(1).getGameName());
			holder.gameIcon4.setVisibility(View.VISIBLE);
			holder.gameName4.setVisibility(View.VISIBLE);
			if (null == gameList.get(2).getIcon()) {
				holder.gameIcon4.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon4.setBackgroundDrawable(gameList.get(2).getIcon());
			}
			holder.gameName4.setText(gameList.get(2).getGameName());
		} else if (bean.getTotal() == 2) {
			holder.gameIcon1.setVisibility(View.VISIBLE);
			holder.gameName1.setVisibility(View.VISIBLE);
			if (null == gameList.get(0).getIcon()) {
				holder.gameIcon1.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon1.setBackgroundDrawable(gameList.get(0).getIcon());
			}
			holder.gameName1.setText(gameList.get(0).getGameName());
			holder.gameIcon2.setVisibility(View.VISIBLE);
			holder.gameName2.setVisibility(View.VISIBLE);
			if (null == gameList.get(1).getIcon()) {
				holder.gameIcon2.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon2.setBackgroundDrawable(gameList.get(1).getIcon());
			}
			holder.gameName2.setText(gameList.get(1).getGameName());
		} else if (bean.getTotal() == 1) {
			holder.gameIcon3.setVisibility(View.VISIBLE);
			holder.gameName3.setVisibility(View.VISIBLE);
			if (null == gameList.get(0).getIcon()) {
				holder.gameIcon3.setBackgroundResource(R.drawable.egame_defaultgamepic);
			} else {
				holder.gameIcon3.setBackgroundDrawable(gameList.get(0).getIcon());
			}
			holder.gameName3.setText(gameList.get(0).getGameName());
		}
		return convertView;
	}

	public class ViewHolder {
		public TextView packageName;
		public TextView cost;
		public TextView count;
		public ImageView gameIcon1;
		public TextView gameName1;
		public ImageView gameIcon2;
		public TextView gameName2;
		public ImageView gameIcon3;
		public TextView gameName3;
		public ImageView gameIcon4;
		public TextView gameName4;
		public ImageView gameIcon5;
		public TextView gameName5;
	}
}
