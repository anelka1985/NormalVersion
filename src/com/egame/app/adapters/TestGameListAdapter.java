package com.egame.app.adapters;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.services.DBService;
import com.egame.app.services.DownloadService;
import com.egame.app.tasks.CheckUaTask;
import com.egame.app.tasks.CheckUaTask.CheckUaResultListener;
import com.egame.app.tasks.GameDetailTask;
import com.egame.app.tasks.GameDetailTask.GameResultListener;
import com.egame.app.tasks.PopUpTask;
import com.egame.app.tasks.UrlInfoTask;
import com.egame.app.uis.EgameHomeActivity;
import com.egame.app.uis.GameHomeActivityGroup;
import com.egame.app.widgets.ExpDialog;
import com.egame.beans.DownloadListBean;
import com.egame.beans.GameInfo;
import com.egame.beans.GameListBean;
import com.egame.beans.biz.DownloadToRomBizBean;
import com.egame.beans.interfaces.DownGameAble;
import com.egame.config.Urls;
import com.egame.utils.common.BroswerUtil;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.sys.ApnUtils;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.BaseAdapterEx;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.ToastUtil;
import com.egame.utils.ui.UIUtils;
import com.egame.utils.ui.Utils;

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
public class TestGameListAdapter extends BaseAdapterEx<GameListBean> implements
		GameResultListener, CheckUaResultListener, DownGameAble {

	DBService dbService;
	EgameApplication application;
	/** 列表类型 -1=普通列表 0=推荐 1=新品 2=排行 */
	int listType = -1;
	GameInfo gameInfo;
	ProgressDialog mPDialog;
	long contentLength = 100;
	String downloadFromer;
	boolean isGameHome;
	List<Integer> updateVersionList = new LinkedList<Integer>();
	PopupWindow pw;
	ExpDialog expDialog;
	// 是否展示过下载经验
	boolean isShowDownExpPop = false;
	// 是否展示过下载经验提示对话框
	boolean isShowDownExpDialog = false;
	// 是否有登录态
	boolean isLogin = false;
	float density = 0;
	DownloadToRomBizBean downToRomBizBean;

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	public TestGameListAdapter(List<GameListBean> list, Activity context,
			String downloadFromer, boolean isGameHome) {
		super(list, context);
		this.downloadFromer = downloadFromer;
		this.isGameHome = isGameHome;
		application = (EgameApplication) context.getApplication();
		if (isGameHome) {
			mPDialog = DialogUtil.getProgressDialog(EgameHomeActivity.INSTANCE);
		} else {
			mPDialog = DialogUtil.getProgressDialog(context);
		}

		dbService = new DBService(context);
		dbService.open();

		isLogin = !LoginDataStoreUtil
				.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME)
				.getUserId().equals(LoginDataStoreUtil.NULL_VALUE);
		// isLogin = false;
		isShowDownExpPop = PreferenceUtil.isShowDownExpPop(context);
		// isShowDownExpPop = true;
		isShowDownExpDialog = PreferenceUtil.isShowDownExpDialog(context);

		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density;

	}

	public TestGameListAdapter(List<GameListBean> list, Activity context,
			int listType, String downloadFromer, boolean isGameHome) {
		this(list, context, downloadFromer, isGameHome);
		this.listType = listType;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.egame_game_list_item, null);
			holder.flIconFrame = convertView.findViewById(R.id.iconFrame);
			holder.ivTip = (View) convertView.findViewById(R.id.tip);
			holder.ivIcon = (View) convertView.findViewById(R.id.icon);
			holder.tvName = (TextView) convertView.findViewById(R.id.name);
			holder.tvInfo = (TextView) convertView.findViewById(R.id.info);
			holder.ivStar = (View) convertView.findViewById(R.id.star);
			// holder.tvDownloadCount = (TextView)
			// convertView.findViewById(R.id.downloadCount);
			holder.btnDownload = convertView.findViewById(R.id.download);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
			holder.tvDownloadText = (TextView) convertView
					.findViewById(R.id.download_text);
			holder.ivDownloadIcon = (ImageView) convertView
					.findViewById(R.id.download_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GameListBean bean = list.get(position);
		// 显示游戏图标
		if (bean.getIcon() != null) {
			holder.ivIcon.setBackgroundDrawable(bean.getIcon());
		} else {
			holder.ivIcon
					.setBackgroundResource(R.drawable.egame_defaultgamepic);
		}
		// 是否是热门推荐
		if (bean.isHot() && isRecoList()) {
			holder.ivTip.setVisibility(View.VISIBLE);
			holder.ivTip.setBackgroundResource(R.drawable.egame_hot);
		} else if (bean.isHot() && isNewList()) {
			holder.ivTip.setVisibility(View.VISIBLE);
			holder.ivTip.setBackgroundResource(R.drawable.egame_new);
		} else if (isRankList()) {
			if (position == 0) {
				holder.ivTip.setVisibility(View.VISIBLE);
				holder.ivTip.setBackgroundResource(R.drawable.egame_rank_no1);
			} else if (position == 1) {
				holder.ivTip.setVisibility(View.VISIBLE);
				holder.ivTip.setBackgroundResource(R.drawable.egame_rank_no2);
			} else if (position == 2) {
				holder.ivTip.setVisibility(View.VISIBLE);
				holder.ivTip.setBackgroundResource(R.drawable.egame_rank_no3);
			} else if (position == 3) {
				holder.ivTip.setVisibility(View.VISIBLE);
				holder.ivTip.setBackgroundResource(R.drawable.egame_rank_no4);
			} else if (position == 4) {
				holder.ivTip.setVisibility(View.VISIBLE);
				holder.ivTip.setBackgroundResource(R.drawable.egame_rank_no5);
			} else {
				holder.ivTip.setVisibility(View.GONE);
			}
		} else {
			holder.ivTip.setVisibility(View.GONE);
		}
		holder.tvName.setText(bean.getGameName());
		holder.tvInfo.setText(bean.getGameInfo());
		holder.tvPrice.setText(bean.getPayName());
		// holder.tvDownloadCount.setText(String.valueOf(bean.getDownloadTimes()));
		// 显示星级
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
		// 查找本地状态
		Cursor cursor = null;
		if (dbService.getDb() != null && dbService.getDb().isOpen()) {
			cursor = dbService.getGameByServiceId(bean.getGameId() + "");
		}
		if (cursor != null && cursor.getCount() > 0) { // 存在本地状态记录
			final DownloadListBean downloadBean = new DownloadListBean(cursor);
			// 更新本地版本
			downloadBean.setVersionCode(bean.getPackageVersionCode());
			if (!updateVersionList.contains(bean.getGameId())) {
				dbService.updateData(bean.getGameId() + "",
						DBService.KEY_VERSION, bean.getPackageVersionCode()
								+ "");
				updateVersionList.add(bean.getGameId());
			}
			// L.d("listR", "" + downloadBean.getState());
			// 暂停或者下载中断
			// if (downloadBean.isDownError() || downloadBean.isPause()) {
			// holder.tvDownloadText.setTextColor(context.getResources().getColor(R.color.egame_text_green));
			// holder.btnDownload.setBackgroundResource(R.drawable.egame_btn_green_selector);
			// holder.ivDownloadIcon.setVisibility(View.VISIBLE);
			// holder.ivDownloadIcon.setImageResource(R.drawable.egame_jixu);
			// holder.tvDownloadText.setText("继续");
			// holder.btnDownload.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// ToastUtil.show(context, R.string.egame_addDownload_hint);
			// Bundle bundle = DownloadService.getBundle(context,
			// bean.getGameId(), bean.getFileSize() + "",
			// application.getPhoneNum(), bean.getCpid(), bean.getCpCode(),
			// bean.getServiceCode() + "", bean.getGameName(),
			// bean.getChannelCode(),
			// bean.getIconurl(), application.getUA(), application.getUserId(),
			// downloadFromer);
			// Intent intent = new Intent(context, DownloadService.class);
			// intent.putExtras(bundle);
			// context.startService(intent);
			// context.sendBroadcast(new Intent(Utils.RECEIVER_DOWNLOAD));
			// }
			// });
			// } else
			if (downloadBean.isDownFinishAndNotInstall()) { // 已完成未安装
				holder.tvDownloadText.setTextColor(context.getResources()
						.getColor(R.color.egame_text_green));
				holder.btnDownload
						.setBackgroundResource(R.drawable.egame_btn_green_selector);
				holder.ivDownloadIcon.setVisibility(View.VISIBLE);
				holder.ivDownloadIcon
						.setImageResource(R.drawable.egame_anzhuang);
				holder.tvDownloadText.setText("安装");
				holder.btnDownload.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						CommonUtil.installGames(bean.getGameId() + "", context);
					}
				});

			} else if (downloadBean.isInstalled()) { // 已安装
				if (downloadBean.isUpdate()) { // 需要升级
					holder.tvDownloadText.setTextColor(context.getResources()
							.getColor(R.color.egame_text_dark_yellow));
					holder.btnDownload
							.setBackgroundResource(R.drawable.egame_btn_yellow_selector);
					holder.ivDownloadIcon.setVisibility(View.VISIBLE);
					holder.ivDownloadIcon
							.setImageResource(R.drawable.egame_shengjiicon);
					holder.tvDownloadText.setText("升级");
					holder.btnDownload
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									if (ApnUtils.checkNetWorkStatus(context)) {
										if (Environment
												.getExternalStorageState()
												.equals(android.os.Environment.MEDIA_MOUNTED)) {
											if (getAvailableStore(android.os.Environment
													.getExternalStorageDirectory()
													.getAbsolutePath()) < contentLength) {
												ToastUtil.show(context,
														"SD卡空间不足");
											} else {
												ToastUtil
														.show(context,
																R.string.egame_addUpdate_hint);
												Bundle bundle = DownloadService
														.getBundle(
																context,
																bean.getGameId(),
																bean.getFileSize(),
																application
																		.getPhoneNum(),
																bean.getCpid(),
																bean.getCpCode(),
																bean.getServiceCode()
																		+ "",
																bean.getGameName(),
																bean.getChannelCode(),
																bean.getIconurl(),
																application
																		.getUA(),
																CommonUtil
																		.getUserId(context),
																downloadFromer,
																bean.getClassName());
												Intent intent = new Intent(
														context,
														DownloadService.class);
												intent.putExtras(bundle);
												context.startService(intent);
											}
										} else {
											ToastUtil.show(context,
													"未找到SD卡，请插入");
										}
									} else {
										ToastUtil.show(context,
												"无法连接到网络，请检查网络配置");
									}

									// context.sendBroadcast(new
									// Intent(Utils.RECEIVER_DOWNLOAD));
								}
							});
				} else { // 不需要升级
					holder.tvDownloadText.setTextColor(context.getResources()
							.getColor(R.color.egame_text_green));
					holder.btnDownload
							.setBackgroundResource(R.drawable.egame_btn_green_selector);
					holder.ivDownloadIcon.setVisibility(View.VISIBLE);
					holder.ivDownloadIcon
							.setImageResource(R.drawable.egame_kaishiicon);
					holder.tvDownloadText.setText("开始");
					holder.btnDownload
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = context.getPackageManager()
											.getLaunchIntentForPackage(
													downloadBean
															.getPackageName());
									context.startActivity(intent);
								}
							});
				}
			} else if (downloadBean.isDownloading()
					|| downloadBean.isDownError() || downloadBean.isPause()) { // 下载中
				holder.tvDownloadText.setTextColor(context.getResources()
						.getColor(R.color.egame_white));
				holder.btnDownload
						.setBackgroundResource(R.drawable.egame_huisebutton);
				holder.ivDownloadIcon.setVisibility(View.GONE);
				// holder.ivDownloadIcon.setImageResource(R.drawable.egame_zantingicon);
				holder.tvDownloadText.setText("下载");
				holder.btnDownload.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// Intent intent = new
						// Intent("com.egame.app.services.DownloadService");
						// intent.putExtra("msg", "pause");
						// intent.putExtra("gameid",
						// downloadBean.getServiceid());
						// context.sendBroadcast(intent);
					}
				});
			} else {
				// 属于单机
				holder.tvDownloadText.setTextColor(context.getResources()
						.getColor(R.color.egame_text_green));
				holder.btnDownload
						.setBackgroundResource(R.drawable.egame_btn_green_selector);
				holder.ivDownloadIcon.setVisibility(View.VISIBLE);
				holder.ivDownloadIcon
						.setImageResource(R.drawable.egame_xiazaiicon);
				holder.tvDownloadText.setText("下载");
				holder.btnDownload.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mPDialog.setMessage(context.getResources().getString(R.string.egame_menu_qsh));
						mPDialog.show();
						if (ApnUtils.checkNetWorkStatus(context)) {
							if (Environment.getExternalStorageState().equals(
									android.os.Environment.MEDIA_MOUNTED)) {
								if (getAvailableStore(android.os.Environment
										.getExternalStorageDirectory()
										.getAbsolutePath()) < contentLength) {
									mPDialog.dismiss();
									ToastUtil.show(context, "SD卡空间不足");
								} else {
									new GameDetailTask(
											TestGameListAdapter.this,
											context,
											bean.getGameId() + "",
											application.getPhoneNum(),
											LoginDataStoreUtil
													.fetchUser(
															context,
															LoginDataStoreUtil.LOG_FILE_NAME)
													.getUserId(), application
													.getUA(), false)
											.execute("");
								}
							} else {
								mPDialog.dismiss();
								ToastUtil.show(context, "未找到SD卡，请插入");
							}
						} else {
							mPDialog.dismiss();
							ToastUtil.show(context, "无法连接到网络，请检查网络配置");
						}
					}
				});

			}
		} else {
			if (bean.isWap()) { // 属于wap游戏,直接运行
				holder.tvDownloadText.setTextColor(context.getResources()
						.getColor(R.color.egame_text_green));
				holder.btnDownload
						.setBackgroundResource(R.drawable.egame_btn_green_selector);
				holder.ivDownloadIcon.setVisibility(View.VISIBLE);
				holder.ivDownloadIcon
						.setImageResource(R.drawable.egame_kaishiicon);
				holder.tvDownloadText.setText("开始");
				holder.btnDownload.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (ApnUtils.checkNetWorkStatus(context)) {
							if (bean.isPackaged()) {
								new GameDetailTask(
										TestGameListAdapter.this,
										context,
										bean.getGameId() + "",
										application.getPhoneNum(),
										LoginDataStoreUtil
												.fetchUser(
														context,
														LoginDataStoreUtil.LOG_FILE_NAME)
												.getUserId(), application
												.getUA(), false).execute("");
							} else {
								// BroswerUtil.startIeDefault(bean.getWapURL(),
								// context);
								Uri uri = Uri.parse(bean.getWapURL());
								Intent it = new Intent(Intent.ACTION_VIEW);
								it.setData(uri);
								context.startActivity(it);
							}
						} else {
							ToastUtil.show(context, "无法连接到网络，请检查网络配置");
						}

					}
				});
			} else {// 属于单机
				holder.tvDownloadText.setTextColor(context.getResources()
						.getColor(R.color.egame_text_green));
				holder.btnDownload
						.setBackgroundResource(R.drawable.egame_btn_green_selector);
				holder.ivDownloadIcon.setVisibility(View.VISIBLE);
				holder.ivDownloadIcon
						.setImageResource(R.drawable.egame_xiazaiicon);
				holder.tvDownloadText.setText("下载");
				holder.btnDownload.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mPDialog.setMessage(context.getResources().getString(R.string.egame_menu_qsh));
						mPDialog.show();
						if (ApnUtils.checkNetWorkStatus(context)) {
							// if
							// (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
							// {
							// if
							// (getAvailableStore(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())
							// < contentLength) {
							// mPDialog.dismiss();
							// ToastUtil.show(context, "SD卡空间不足");
							// } else {
							new GameDetailTask(TestGameListAdapter.this,
									context, bean.getGameId() + "", application
											.getPhoneNum(),
									LoginDataStoreUtil.fetchUser(context,
											LoginDataStoreUtil.LOG_FILE_NAME)
											.getUserId(), application.getUA(),
									false).execute("");
							// }
							// } else {
							// mPDialog.dismiss();
							// ToastUtil.show(context, "未找到SD卡，请插入");
							// }
						} else {
							mPDialog.dismiss();
							ToastUtil.show(context, "无法连接到网络，请检查网络配置");
						}
					}
				});
			}
		}
		// 注释掉后预制版本不显示经验值popwindows
		// if (isRecoList() && isShowDownExpPop && pw == null && isLogin) {
		// View popView =
		// LayoutInflater.from(context).inflate(R.layout.egame_exppopup, null);
		// popView.setBackgroundResource(R.drawable.egame_exppopup_bg_right);
		// TextView tv = (TextView) popView.findViewById(R.id.pop_text);
		// tv.setText("“下载游戏”可得经验升等级啦！多下多得哦~");
		// pw = new PopupWindow(popView);
		// pw.setAnimationStyle(R.style.egame_Popup_fade);
		// pw.setOutsideTouchable(true);
		// pw.setBackgroundDrawable(new BitmapDrawable());
		// pw.showAtLocation(holder.btnDownload, Gravity.RIGHT | Gravity.TOP, 0,
		// 0); // 在父视图的左上方显示
		// L.d("d:" + density);
		// int pwH = Math.round(28 * density);
		// int pwW = Math.round(250 * density);
		// pw.update(Math.round(37 * density), Math.round(220 * density), pwW,
		// pwH); // 更新位置。
		// popView.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		// pw.dismiss();
		// }
		// });
		// new PopUpTask(pw,8000).execute("");
		// PreferenceUtil.setShowDownExpPop(context);
		// } else if (isRecoList() && isShowDownExpDialog && expDialog == null
		// && !isLogin) {
		// 注释掉后预制版本不显示经验值dialog框
		// if (isGameHome) {
		// expDialog = new ExpDialog(EgameHomeActivity.INSTANCE, 1);
		// } else {
		// expDialog = new ExpDialog(context, 1);
		// }
		//
		// expDialog.show();
		// PreferenceUtil.setShowDownExpDialog(context);
		// }

		if (cursor != null) {
			cursor.close();
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
		// public TextView tvDownloadCount;
		public View btnDownload;
		public TextView tvPrice;
		// 下载按钮上得文字
		public TextView tvDownloadText;
		// 下载按钮前边的图标
		public ImageView ivDownloadIcon;
	}

	boolean isRankList() {
		if (listType == 2) {
			return true;
		} else {
			return false;
		}
	}

	boolean isRecoList() {
		if (listType == 0) {
			return true;
		} else {
			return false;
		}
	}

	boolean isNewList() {
		if (listType == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void getGameResult(String result, GameInfo gameInfo) {
		if ("1".equals(result)) {
			this.gameInfo = gameInfo;
			if (gameInfo.isWap()) {
				if (CommonUtil.canPlayPackage(gameInfo.getDownOrderStatus())) {
					// BroswerUtil.startIeDefault(gameInfo.getWapURL(),
					// context);
					Uri uri = Uri.parse(gameInfo.getWapURL());
					Intent it = new Intent(Intent.ACTION_VIEW);
					it.setData(uri);
					context.startActivity(it);

				} else {
					new AlertDialog.Builder(context)
							.setTitle(R.string.egame_menu_tip)
							.setMessage(R.string.egame_package_not_order)
							.setNegativeButton("确定", null).show();
				}
			} else {
				new CheckUaTask(TestGameListAdapter.this, context, gameInfo,
						application.getUA()).execute("GameList");
			}
		} else if ("exception".equals(result)) {
			mPDialog.dismiss();
			ToastUtil.show(context, "网络异常，请重试");
		}

	}

	/**
	 * 检查适配的结果
	 * */
	public void checkResult(String result) {
		mPDialog.dismiss();
		if ("false".equals(result)) {
			if (isGameHome) {
				UIUtils.showMessage(EgameHomeActivity.INSTANCE,
						R.string.egame_menu_tip, R.string.egame_not_match);
			} else {
				UIUtils.showMessage(context, R.string.egame_menu_tip,
						R.string.egame_not_match);
			}
		} else {
			if (isGameHome) {
				UIUtils.showMessage(EgameHomeActivity.INSTANCE,
						R.string.egame_menu_tip, R.string.egame_match_exception);
			} else {
				UIUtils.showMessage(context, R.string.egame_menu_tip,
						R.string.egame_match_exception);
			}

		}
	}

	public void startDownloadStep1() {
		L.d("down", "ischeck: true");
		mPDialog.dismiss();
		if (gameInfo.isPackageGame()) {// 包内游戏
			startPackageGame();
		} else if (gameInfo.isPay()) {
			L.d("down", "needpay game");
			startDownloadPayGame();
		} else {
			downloadGame(1);
		}
	}

	/**
	 * 启动包内游戏
	 */
	private void startPackageGame() {
		if (CommonUtil.canPlayPackage(gameInfo.getDownOrderStatus())) {
			downloadGame(1);
		} else {
			if (isGameHome) {
				new AlertDialog.Builder(EgameHomeActivity.INSTANCE)
						.setTitle(R.string.egame_menu_tip)
						.setMessage(R.string.egame_package_not_order)
						.setNegativeButton("确定", null).show();
			} else {
				new AlertDialog.Builder(context)
						.setTitle(R.string.egame_menu_tip)
						.setMessage(R.string.egame_package_not_order)
						.setNegativeButton("确定", null).show();
			}
		}
	}

	/**
	 * 启动按次下载计费的
	 */
	private void startDownloadPayGame() {
		int money;
		Context ctx;
		if (isGameHome) {
			ctx = EgameHomeActivity.INSTANCE;
		} else {
			ctx = context;
		}
		try {
			money = Integer.parseInt(gameInfo.getMoney());
		} catch (Exception e) {
			money = 0;
		}
		if (gameInfo.getDownOrderStatus().equals("0")) {// 未订购,开始订购流程
			L.d("down", "not order");
			if (ApnUtils.isCtwap(ctx)) {
				showDownloadOrderDialog(money);
			} else {
				DialogUtil.showDialog(ctx, R.string.str_ctwapalertdialog_body);
			}
		} else {// 已订购,开始下载
			L.d("down", "isorder----start down");
			downloadGame(1);
		}

	}

	/**
	 * 显示下载按次计费的对话框
	 */
	private void showDownloadOrderDialog(int money) {
		final Context ctx;
		if (isGameHome) {
			ctx = EgameHomeActivity.INSTANCE;
		} else {
			ctx = context;
		}
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (gameInfo.getOrderURL().equals("")) {
					DialogUtil.showDialog(ctx, "下载计费游戏只供电信用户下载");
				} else {
					// 订购点播异步任务 需要订购 点播的url
					new UrlInfoTask(gameInfo, TestGameListAdapter.this,
							application.getPhoneNum()).execute("adapter");
				}

			}
		};
		DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		};
		DialogStyle ds = new DialogStyle();
		AlertDialog.Builder builder = ds.getBuilder(ctx, "确定", "取消", comfirmL,
				cancelL);
		builder.setTitle(R.string.egame_app_name)
				.setMessage(
						"本次下载将一次性收取您"
								+ money
								+ "元信息费。如下载失败，您可以在当前自然月内免费重复下载。下载产生的流量费用按当地运营商资费标准收取。")
				.create().show();
	}

	/**
	 * 开始下载游戏
	 */
	@Override
	public void downloadGame(int type) {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			if (getAvailableStore(android.os.Environment
					.getExternalStorageDirectory().getAbsolutePath()) < contentLength) {
				ToastUtil.show(context, "SD卡空间不足");
				return;
			} else { // 有sd卡且空间足够
				if (!LoginDataStoreUtil
						.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME)
						.getUserId().equals(LoginDataStoreUtil.NULL_VALUE)) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							Urls.gameDownloadLog(
									context,
									LoginDataStoreUtil.fetchUser(context,
											LoginDataStoreUtil.LOG_FILE_NAME)
											.getUserId(), gameInfo.getGameId());
						}
					}).start();
				}
				ToastUtil.show(context, R.string.egame_addDownload_hint);
				Bundle bundle = DownloadService.getBundle(context,
						Integer.parseInt(gameInfo.getGameId()),
						gameInfo.getFileSize(), application.getPhoneNum(),
						gameInfo.getCpId(), gameInfo.getCpCode(),
						gameInfo.getServiceCode(), gameInfo.getGameName(),
						gameInfo.getChannelCode(), gameInfo.getIconurl(),
						application.getUA(), CommonUtil.getUserId(context),
						downloadFromer, gameInfo.getClassName());
				Intent intent = new Intent(context, DownloadService.class);
				intent.putExtras(bundle);
				context.startService(intent);
				context.sendBroadcast(new Intent(Utils.RECEIVER_DOWNLOAD));
			}
		} else { // 无sd卡下载流程
			if (!LoginDataStoreUtil
					.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME)
					.getUserId().equals(LoginDataStoreUtil.NULL_VALUE)) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						Urls.gameDownloadLog(
								context,
								LoginDataStoreUtil.fetchUser(context,
										LoginDataStoreUtil.LOG_FILE_NAME)
										.getUserId(), gameInfo.getGameId());
					}
				}).start();
			}
			if (downToRomBizBean != null) {
				downToRomBizBean.init(gameInfo);
				downToRomBizBean.startDownloadToRom();
			} else {
				downToRomBizBean = new DownloadToRomBizBean(this, gameInfo,
						downloadFromer);
				downToRomBizBean.startDownloadToRom();
			}
		}
	}

	/**
	 * 
	 * 获取存储卡的剩余容量，单位为字节
	 * 
	 * @param filePath
	 * 
	 * @return availableSpare
	 */
	public long getAvailableStore(String filePath) {
		StatFs statFs = new StatFs(filePath);// 取得sdcard文件路径
		long blocSize = statFs.getBlockSize();// 获取block的SIZE
		long availaBlock = statFs.getAvailableBlocks();// 可使用的Block的数量
		long availableSpare = availaBlock * blocSize;
		return availableSpare;
	}

	@Override
	public Activity getDialogActivity() {
		if (isGameHome) {
			return GameHomeActivityGroup.INSTANCE;
		} else {
			return context;
		}
	}

}
