package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.TopicBean;
import com.egame.beans.TopicPageBean;

/**
 * @desc 专题列表适配器
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-29
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameTopicListAdapter extends BaseExpandableListAdapter {

	List<TopicPageBean> topicPageList;

	Context context;

	public GameTopicListAdapter(List<TopicPageBean> topicPageList, Context context) {
		this.topicPageList = topicPageList;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return topicPageList.get(groupPosition).getTopicList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return topicPageList.get(groupPosition).getTopicList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return topicPageList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return topicPageList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		TopicPageBean topicPage = topicPageList.get(groupPosition);
		LinearLayout item = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.egame_game_topic_list_head, null);
		TextView tvGroupName = (TextView) item.findViewById(R.id.topicHead);
		tvGroupName.setText(topicPage.getTopicName());
		return item;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		TopicBean topic = topicPageList.get(groupPosition).getTopicList().get(childPosition);
		if (topic.isBigPic()) {
			LinearLayout item = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.egame_game_topic_list_item_big, null);
			ImageView icon = (ImageView) item.findViewById(R.id.icon);
			if (topic.getIcon() != null) {
				icon.setBackgroundDrawable(topic.getIcon());
			}else{
				icon.setBackgroundResource(R.drawable.egame_topic);
			}
			return item;
		} else {
			LinearLayout item = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.egame_game_topic_list_item_small, null);
			TextView name = (TextView) item.findViewById(R.id.name);
			TextView content = (TextView) item.findViewById(R.id.content);
			ImageView icon = (ImageView) item.findViewById(R.id.icon);
			if (topic.getIcon() != null) {
				icon.setBackgroundDrawable(topic.getIcon());
			}else{
				icon.setBackgroundResource(R.drawable.egame_defaultgamepic);
			}
			name.setText(topic.getTitle());
			content.setText(topic.getIntroduce());
			return item;
		}
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
