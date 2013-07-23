package com.egame.app.adapters;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.services.DBService;
import com.egame.app.services.DownloadService;
import com.egame.app.uis.GamesDetailActivity;
import com.egame.beans.DownloadListBean;
import com.egame.config.Const;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.SourceUtils;
import com.egame.utils.sys.DialogStyle;

/**
 * @desc 下载任务适配器
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4th
 * 
 * @Author yaopp@gzylxx.com
 * 
 * @timer 2011-01-05
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameDownloadMissionListAdapter extends BaseAdapter {

	int expendIndex = -999;

	protected List<DownloadListBean> list;

	protected Map<String, Bitmap> iconMap;

	protected Activity context;

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

	public int getExpendIndex() {
		return expendIndex;
	}

	public void setExpendIndex(int expendIndex) {
		this.expendIndex = expendIndex;
	}

	EgameApplication application;

	public GameDownloadMissionListAdapter(List<DownloadListBean> list, Map<String, Bitmap> iconMap, Activity context) {
		this.list = list;
		this.context = context;
		this.application = (EgameApplication) context.getApplication();
		steup = context.getResources().getString(R.string.egame_manager_setup);
		continueStr = context.getResources().getString(R.string.egame_manager_continue);
		downloadError = context.getResources().getString(R.string.egame_manager_download_error);
		installed = context.getResources().getString(R.string.egame_manager_installed);
		pause = context.getResources().getString(R.string.egame_manager_pause);
		tautology = context.getResources().getString(R.string.egame_manager_tautology);
		this.iconMap = iconMap;
	}

	private String steup;

	private String continueStr;

	private String tautology;

	private String downloadError;

	private String installed;

	private String pause;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final DownloadListBean bean = list.get(position);
		final LinearLayout item = (LinearLayout) View.inflate(context, R.layout.egame_game_downloadmission_item, null);
		if (position == expendIndex) {
			item.findViewById(R.id.rlGameManagerAction).setVisibility(View.VISIBLE);
		}
		TextView name = (TextView) item.findViewById(R.id.name);
		TextView size = (TextView) item.findViewById(R.id.size);
		ImageView icon = (ImageView) item.findViewById(R.id.icon);
		// 按钮
		final ImageButton ibDownload = (ImageButton) item.findViewById(R.id.downloadBtn);
		// 标示的图片暂停、重新下载。。
		final ImageView ivMarkpic = (ImageView) item.findViewById(R.id.markpic);
		// 下载按钮文字
		final TextView tvdownText = (TextView) item.findViewById(R.id.downText);
		// 进度条
		ProgressBar progressBar = (ProgressBar) item.findViewById(R.id.progressBar);
		// 下载按钮下方提示
		final TextView tvdownload = (TextView) item.findViewById(R.id.downloading);
		BitmapDrawable bd = new BitmapDrawable(iconMap.get(bean.getServiceid()));
		if (null != bd) {
			icon.setBackgroundDrawable(bd);
		}
		name.setText(bean.getName());
		if(TextUtils.isEmpty(bean.getSortName())){
			size.setText(bean.getGameSizeM());
		}else{
			size.setText(bean.getSortName()+"类  " + bean.getGameSizeM());
		}

		if (bean.isDownFinishAndNotInstall()) {// 已完成
			progressBar.setVisibility(View.GONE);
			tvdownload.setVisibility(View.GONE);
			tvdownText.setText(steup);
			tvdownText.setTextColor(context.getResources().getColor(R.color.egame_text_green));
			ivMarkpic.setBackgroundResource(R.drawable.egame_anzhuang);
			ibDownload.setBackgroundResource(R.drawable.egame_btn_green_selector);
		} else if (bean.isDownloading()) {// 下载中
			tvdownload.setText(context.getString(R.string.egame_manager_downloading, "" + bean.getPercentage() + "%..."));
		} else if (bean.isDownError()) {// 中断
			tvdownText.setText(tautology);
			tvdownText.setTextColor(context.getResources().getColor(R.color.egame_text_green));
			ivMarkpic.setBackgroundResource(R.drawable.egame_chongshiicon);
			tvdownload.setText(downloadError);
			ibDownload.setBackgroundResource(R.drawable.egame_btn_green_selector);
		} else if (bean.isInstalled()) {// 已安装
			tvdownText.setText(installed);
			tvdownText.setTextColor(Color.WHITE);
			ibDownload.setBackgroundResource(R.drawable.egame_huisebutton);
			ibDownload.setEnabled(false);
		} else if (bean.isPause()) { // 已暂停
			tvdownText.setText(continueStr);
			tvdownText.setTextColor(context.getResources().getColor(R.color.egame_text_green));
			ivMarkpic.setBackgroundResource(R.drawable.egame_jixu);
			tvdownload.setText(pause);
			ibDownload.setBackgroundResource(R.drawable.egame_btn_green_selector);
		}

		// 设置进度条
		progressBar.setProgress(bean.getPercentage());
		ibDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bean.isDownFinishAndNotInstall()) { // 点击安装
					CommonUtil.installGames(bean.getServiceid(), context);
				} else if (bean.isDownError() || bean.isPause()) { // 点击继续
//					L.d("down2", "totalsize:" + bean.getTotalsize());
					Bundle bundle = DownloadService.getBundle(context, Integer.parseInt(bean.getServiceid()), (int)(bean.getTotalsize() / 1024),
							application.getPhoneNum(), bean.getCpid(), bean.getCpcode(), bean.getServiceCode(), bean.getName(), bean.getChannelid(),
							bean.getIconurl(), application.getUA(), CommonUtil.getUserId(context), SourceUtils.DEFAULT,bean.getSortName());
					Intent intent = new Intent(context, DownloadService.class);
					intent.putExtras(bundle);
					context.startService(intent);
				} else if (bean.isDownloading()) {
					Intent intent = new Intent("com.egame.app.services.DownloadService");
					intent.putExtra("msg", "pause");
					intent.putExtra("gameid", bean.getServiceid());
					context.sendBroadcast(intent);
				}
			}
		});
		View ib_ycgl = item.findViewById(R.id.ib_ycgl);
		// 隐藏管理
		ib_ycgl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				expendIndex = -999;
				item.findViewById(R.id.rlGameManagerAction).setVisibility(View.GONE);
			}
		});

		View ib_scxz = item.findViewById(R.id.ib_scxz);
		// 删除下载
		ib_scxz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							if (bean.isDownloading()) {
								Intent intent = new Intent(
										"com.egame.app.services.DownloadService");
								intent.putExtra("msg", "cancel");
								intent.putExtra("gameid", bean.getServiceid());
								context.sendBroadcast(intent);
							} else if (bean.isDownError()
									|| bean.isDownFinishAndNotInstall()
									|| bean.isPause()) {
								new File(Const.DIRECTORY + "/"
										+ bean.getServiceid() + ".apk")
										.delete();
								new File(Const.DIRECTORY + "/"
										+ bean.getServiceid() + ".apk"
										+ DownloadService.TEMP_FILE_FIX)
										.delete();
								DBService dbservice = new DBService(context);
								dbservice.open();
								dbservice.delGameByServiceId(Integer
										.parseInt(bean.getServiceid()));
								dbservice.close();
								CommonUtil.sendChangeBroadCast(context);
							}
							expendIndex = -999;
							notifyDataSetChanged();

						}
					};
					DialogStyle ds = new DialogStyle();
					AlertDialog.Builder builder = ds.getBuilder(
							context.getParent(), "确定", "取消", comfirmL, null);
					builder.setIcon(android.R.drawable.ic_dialog_map)
							.setTitle("确认？").setMessage("是否确认删除下载？").create()
							.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// item.findViewById(R.id.rlGameManagerAction).setVisibility(View.GONE);
			}
		});

		View ib_ckxq = item.findViewById(R.id.ib_ckxq);
		// 查看详情
		ib_ckxq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Bundle bundle = GamesDetailActivity.makeIntentData(Integer.parseInt(bean.getServiceid()), SourceUtils.DEFAULT);
					Intent intent = new Intent(context, GamesDetailActivity.class);
					intent.putExtras(bundle);
					context.startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return item;
	}

}
