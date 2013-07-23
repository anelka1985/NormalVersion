package com.egame.app.adapters;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.GameListBean;

/**
 * 
 * 已安装游戏adapter
 * 
 * @author YaoPengpeng 姚朋朋
 * @version
 */
public class GameDownLoadEdAdapter extends BaseExpandableListAdapter {
	Activity c;

	List<Integer> groupIcon;

	List<String> group; // 组列表

	List<List<GameListBean>> child; // 子列表

	public GameDownLoadEdAdapter(Activity c, List<Integer> groupIcon, List<String> group, List<List<GameListBean>> child) {
		super();
		this.c = c;
		this.groupIcon = groupIcon;
		this.group = group;
		this.child = child;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// 当分类排序中只有一个条目，且不是最后一个分组，使用此布局
		if (child.get(groupPosition).size() == 1 && !(groupPosition == group.size() - 1) && null == child.get(groupPosition).get(childPosition).getActionFlag()) {
			convertView = LayoutInflater.from(c).inflate(R.layout.egame_game_manage_single_item, null);
			initView(convertView, childPosition, groupPosition);
		} else if (isLastChild && !(groupPosition == group.size() - 1) && null == child.get(groupPosition).get(childPosition).getActionFlag()) {
			convertView = LayoutInflater.from(c).inflate(R.layout.egame_game_manage_bottom_item, null);
			initView(convertView, childPosition, groupPosition);
		} else if (0 == childPosition) {
			convertView = LayoutInflater.from(c).inflate(R.layout.egame_game_manage_top_item, null);
			initView(convertView, childPosition, groupPosition);
		} else {
			convertView = LayoutInflater.from(c).inflate(R.layout.egame_game_manage_item, null);
			initView(convertView, childPosition, groupPosition);
		}
		if (null != child.get(groupPosition).get(childPosition).getActionFlag()) {
			convertView.findViewById(R.id.rlGameManagerAction).setVisibility(View.VISIBLE);
		}
		// 各类操作
		anyAction(groupPosition, childPosition, convertView);

		return convertView;
	}

	private void anyAction(final int groupPosition, final int childPosition, final View convertView) {
		convertView.findViewById(R.id.ib_ycgl).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				convertView.findViewById(R.id.rlGameManagerAction).setVisibility(View.GONE);
				child.get(groupPosition).get(childPosition).setActionFlag(null);
				notifyDataSetChanged();
			}
		});
		convertView.findViewById(R.id.startgame).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				c.startActivity(c.getPackageManager().getLaunchIntentForPackage("" + child.get(groupPosition).get(childPosition).getPackageName()));
			}
		});

	}

	/** 初始化数据 */
	void initView(View convertView, int childPosition, int groupPosition) {

		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView size = (TextView) convertView.findViewById(R.id.size);
		Button btnckxq = (Button) convertView.findViewById(R.id.ib_ckxq);
		Button btnxzyx = (Button) convertView.findViewById(R.id.ib_xzyx);
		name.setText(child.get(groupPosition).get(childPosition).getGameName());
		if(TextUtils.isEmpty(child.get(groupPosition).get(childPosition).getClassName())){
			size.setText(getGameSizeM(child.get(groupPosition).get(childPosition).getFileSize()));
		}else{
			size.setText(child.get(groupPosition).get(childPosition).getClassName() + "类  "
					+ getGameSizeM(child.get(groupPosition).get(childPosition).getFileSize()));
		}
		
		ivIcon.setBackgroundDrawable(child.get(groupPosition).get(childPosition).getIcon());
		final int serviceId = child.get(groupPosition).get(childPosition).getGameId();
		final String packageName = child.get(groupPosition).get(childPosition).getPackageName();
		// 查看游戏详情
		btnckxq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Bundle bundle = GamesDetailActivity.makeIntentData(serviceId, "");
					Intent intent = new Intent(c, GamesDetailActivity.class);
					intent.putExtras(bundle);
					c.startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnxzyx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null));
				c.startActivityForResult(it, 0);
			}
		});
	}

	public String getGameSizeM(int size) {
		float gameSize = size * 1.0f / (1024 * 1024);
		return new DecimalFormat("#0.00").format(gameSize) + "M";
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		LayoutInflater inflate = LayoutInflater.from(c);
		View view = inflate.inflate(R.layout.egame_game_sort_group, null); // 用childlayout这个layout作为条目的视图
		TextView sortText = (TextView) view.findViewById(R.id.groupListTitle);
		sortText.setText(group.get(groupPosition));
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
