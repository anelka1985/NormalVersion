package com.egame.app.adapters;

import java.util.List;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.uis.GameRecommendActivity;
import com.egame.beans.AdBean;
import com.egame.beans.AdPageBean;
import com.egame.utils.common.L;

/**
 * @desc 推荐顶部画廊适配器
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2011-12-28
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class AdGallaryAdapter extends BaseAdapter {

	List<AdPageBean> list;

	GameRecommendActivity context;

	public AdGallaryAdapter(List<AdPageBean> list, GameRecommendActivity context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup item = null;
		if (list.size() > 0) {
			AdPageBean page = list.get(position);
			List<AdBean> adList = page.getAdList();
			if (adList.size() == 1) { // 如果只有一个,进入一页的布局
				final AdBean ad = adList.get(0);
				item = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.egame_ad_single, null);
				TextView tvAdText = (TextView) item.findViewById(R.id.adText);
				ImageView ivAdPic = (ImageView) item.findViewById(R.id.adPic);

				ivAdPic.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						context.getBean().setAd(ad);
						return false;
					}
				});

				tvAdText.setText(ad.getName());
				if (ad.getIcon() != null) {
					ivAdPic.setBackgroundDrawable(ad.getIcon());
				} else {
					ivAdPic.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.egame_single));
				}
			} else if (adList.size() == 2) {// 如果有两页,进入两页的布局
				final AdBean ad1 = adList.get(0);
				final AdBean ad2 = adList.get(1);
				item = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.egame_ad_double, null);
				TextView tvAdText1 = (TextView) item.findViewById(R.id.adText1);
				ImageView ivAdPic1 = (ImageView) item.findViewById(R.id.adPic1);
				TextView tvAdText2 = (TextView) item.findViewById(R.id.adText2);
				ImageView ivAdPic2 = (ImageView) item.findViewById(R.id.adPic2);
				tvAdText1.setText(ad1.getName());
				if (ad1.getIcon() != null) {
					ivAdPic1.setBackgroundDrawable(ad1.getIcon());
				} else {
					ivAdPic1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.egame_double2));
					L.d("icon", "null");
				}
				tvAdText2.setText(ad2.getName());
				if (ad2.getIcon() != null) {
					ivAdPic2.setBackgroundDrawable(ad2.getIcon());
				} else {
					ivAdPic2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.egame_double2));
					L.d("icon", "is not null");
					L.d("icon", "null");
				}
				ivAdPic1.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						L.d("on1", event.getAction() + "|" + event.getX());
						context.getBean().setAd(ad1);
						return false;
					}
				});
				ivAdPic2.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						L.d("on1", event.getAction() + "|" + event.getX());
						context.getBean().setAd(ad2);
						return false;
					}
				});
			}
		}
		return item;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
