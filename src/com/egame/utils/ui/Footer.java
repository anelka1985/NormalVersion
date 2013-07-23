package com.egame.utils.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egame.R;

/**
 * @author zhouzhe@lenovo-cw.com
 * 
 *
 */
public class Footer {
	public RelativeLayout footerLayout;
	LinearLayout loadinglayout;
	LinearLayout reloadlayout;
	LinearLayout nodatalayout;
	TextView loadingtext;
	TextView reloadtext;
	ProgressBar progressBar;
    Button reloadButton;

	public Footer(Context ctx) {
		this.footerLayout = (RelativeLayout) LayoutInflater.from(ctx).inflate(R.layout.egame_progress_small, null);
		this.loadingtext = (TextView) footerLayout.findViewById(R.id.loadingtext);
		this.reloadtext = (TextView) footerLayout.findViewById(R.id.reloadtext);
		this.progressBar = (ProgressBar) footerLayout.findViewById(R.id.progress_small);
		this.reloadButton = (Button) footerLayout.findViewById(R.id.reload);
		this.loadinglayout = (LinearLayout) footerLayout.findViewById(R.id.loadinglayout);
		this.reloadlayout = (LinearLayout) footerLayout.findViewById(R.id.reloadlayout);
		this.nodatalayout = (LinearLayout) footerLayout.findViewById(R.id.nodatalayout);
	}

	/**
	 * 设置可见属性
	 * 
	 * @param visibility
	 */
	public void setVisibility(int visibility) {
		this.footerLayout.setVisibility(visibility);
	}

	public void showReload() {  //重新加载
		this.footerLayout.setVisibility(View.VISIBLE);
		loadinglayout.setVisibility(View.GONE);
		reloadlayout.setVisibility(View.VISIBLE);
		nodatalayout.setVisibility(View.GONE);
	}

	public void showLoading() {
		this.footerLayout.setVisibility(View.VISIBLE);
		loadinglayout.setVisibility(View.VISIBLE);
		reloadlayout.setVisibility(View.GONE);
		nodatalayout.setVisibility(View.GONE);
	}
	
	public void showNoData(){
		this.footerLayout.setVisibility(View.VISIBLE);
		loadinglayout.setVisibility(View.GONE);
		reloadlayout.setVisibility(View.GONE);
		nodatalayout.setVisibility(View.VISIBLE);
	}
	
	public RelativeLayout getFooter(){
		return this.footerLayout;
	}
	
	public void setReloadButtonListener(View.OnClickListener listener){
		this.reloadButton.setOnClickListener(listener);
	}
	
	
	
}
