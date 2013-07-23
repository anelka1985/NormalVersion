package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.egame.R;

/**
 * @desc 游戏详情中展示图片的适配器
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
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<BitmapDrawable> list;
	private float density;

	public ImageAdapter(Context context, List<BitmapDrawable> list,
			float density) {
		this.mContext = context;
		this.list = list;
		this.density = density;
	}

	@Override
	public int getCount() {
		// return Integer.MAX_VALUE;
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
		ImageView imageView = new ImageView(mContext);
		if(list.get(position % list.size())==null){
			imageView.setBackgroundResource(R.drawable.egame_screenshot_default);
		}else{
		imageView.setBackgroundDrawable(list.get(position % list.size()));}
		imageView.setLayoutParams(new Gallery.LayoutParams(
				(int) (320 * density / 240), (int) (480 * density / 240)));
		return imageView;
	}

}