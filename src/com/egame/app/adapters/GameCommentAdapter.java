package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.CommentBean;

/**
 * @desc 游戏评论列表的适配器
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-5
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameCommentAdapter extends BaseAdapter {
	List<CommentBean> list;
	Context context;

	public GameCommentAdapter(List<CommentBean> list, Context ctx) {
		this.list = list;
		this.context = ctx;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.egame_comment_list_item, null);
			holder.commenter = (TextView) convertView.findViewById(R.id.commenter);
			holder.star = (ImageView) convertView.findViewById(R.id.star);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CommentBean bean = list.get(position);
		holder.commenter.setText("" + bean.getUserName());
		holder.time.setText("( " + bean.getSubmitTime() + " ) ");
		holder.content.setText("" + bean.getComment());
		if (bean.getScore() == 0) {
			holder.star.setBackgroundResource(R.drawable.egame_star0);
		} else if (bean.getScore() == 1) {
			holder.star.setBackgroundResource(R.drawable.egame_star1);
		} else if (bean.getScore() == 2) {
			holder.star.setBackgroundResource(R.drawable.egame_star2);
		} else if (bean.getScore() == 3) {
			holder.star.setBackgroundResource(R.drawable.egame_star3);
		} else if (bean.getScore() == 4) {
			holder.star.setBackgroundResource(R.drawable.egame_star4);
		} else {
			holder.star.setBackgroundResource(R.drawable.egame_star5);
		}

		return convertView;
	}

	public class ViewHolder {
		TextView commenter;
		ImageView star;
		TextView time;
		TextView content;
	}
}
