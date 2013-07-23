package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.ProductListBean;
import com.egame.utils.common.SourceUtils;

public class ProductListAdapter extends BaseAdapter {
	private List<ProductListBean> list;

	private Context ctx;

	private LayoutInflater mInflater;

	public ProductListAdapter(List<ProductListBean> list, Context ctx) {
		super();
		this.list = list;
		this.ctx = ctx;
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		final int p = position;
		ViewHolder holder = null;
		if (null == view) {
			view = mInflater.inflate(R.layout.egame_productlist_item, null);
			holder = new ViewHolder();
			holder.ivProductImage = (ImageView) view.findViewById(R.id.productImage);
			holder.tvProductName = (TextView) view.findViewById(R.id.productName);
			holder.btnSee = (Button) view.findViewById(R.id.see);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvProductName.setText(list.get(position).getProductName());

		if (null != list.get(position).getIcon()) {
			holder.ivProductImage.setBackgroundDrawable(list.get(position).getIcon());
		} else {
			holder.ivProductImage.setBackgroundResource(R.drawable.egame_defaultgamepic);
		}

		holder.btnSee.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int productType = list.get(p).getProductType();

				if (productType == 1) {
					// 游戏详情
					Bundle bundle = GamesDetailActivity.makeIntentData(Integer.parseInt(list.get(p).getProductId()),
							SourceUtils.MORE_LIST);
					Intent intent = new Intent(ctx, GamesDetailActivity.class);
					intent.putExtras(bundle);
					ctx.startActivity(intent);

				} else if (productType == 2) {
					// 打开wap
					openBrowser(list.get(p).getLinkUrl());
				}
			}
		});
		return view;
	}

	public static class ViewHolder {
		public ImageView ivProductImage;
		public TextView tvProductName;
		public Button btnSee;
	}

	/***
	 * 打开网页
	 * 
	 * @param url
	 */
	private void openBrowser(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		ctx.startActivity(intent);
	}

}
