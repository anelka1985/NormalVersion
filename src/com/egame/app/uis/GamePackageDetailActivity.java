package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.TestGameListAdapter;
import com.egame.app.receivers.GetMsgContextReceiver;
import com.egame.app.receivers.ListNotifyReceiver;
import com.egame.app.tasks.GamePackageDetailTask;
import com.egame.app.tasks.GetListIconAsyncTask;
import com.egame.app.tasks.OrderGamePackageAsyncTask;
import com.egame.beans.GameListBean;
import com.egame.beans.PackageDetailBean;
import com.egame.config.Const;
import com.egame.config.Urls;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.SourceUtils;
import com.egame.utils.sys.ApnUtils;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.BaseActivity;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.Loading;
import com.egame.utils.ui.StringUtil;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

/**
 * @desc 游戏包详情页面
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
public class GamePackageDetailActivity extends Activity implements
		BaseActivity, OnItemClickListener, android.view.View.OnClickListener {
	public static String TAG = "游戏包详情页面";

	public static GamePackageDetailActivity INSTANCE;
	public final static String MSG_CONTENT_STR = "com.egame.app.receivers.GetMsgContextReceiver";
	EgameApplication application;
	/** 游戏包内的游戏列表 */
	ListView lvGamePackageDetail;

	ListNotifyReceiver br;

	/** 顶部标题 */
	TextView tvTitle;

	List<GameListBean> list;

	/** 游戏包列表头部 */
	LinearLayout llHead;

	/** 游戏包名 */
	private TextView tvPackagename;

	/** 游戏包内游戏数量 */
	private TextView tvCount;

	/** 订购按钮 */
	private LinearLayout llDinggou;

	/** 游戏包详情描述 */
	private TextView tvPackage_detail_desc;

	/** 游戏包价格 */
	private TextView tvPrice;

	/** 顶部返回按钮 */
	private Button btnBack;

	private TestGameListAdapter adapter;

	private Bundle bundle;

	// private Footer footer;

	private Loading loading;

	private String packageId;

	private PackageDetailBean packageDetailBean;

	private ProgressDialog progress;

	private LinearLayout dialogLayout;

	private EditText etValidateCode;
	/** 当前时间 */
	private long currentTime = 0;
	/** 最后一次启动获取验证码的时间 */
	private long lastTime = 0;
	/** 订购或退订游戏包的链接 */
	private String packageUrl;
	/** 是否为第一次请求验证码 */
	private boolean isFirst = true;

	public GetMsgContextReceiver re;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			progress.dismiss();

			if (msg.what == Const.MSG_GET_SUCCESS) {
				try {
					JSONObject jObj = new JSONObject(msg.obj.toString());
					JSONObject resultObj = jObj.getJSONObject("result");
					int resultCode = resultObj.optInt("resultcode");
					if (resultCode == 0) {
						isFirst = false;
						lastTime = System.currentTimeMillis();
						ToastUtil.show(GamePackageDetailActivity.this,
								"验证码发送成功");
						showValidateCodeDialog();
						L.d(TAG, "验证码对话框显示");
					} else {
						ToastUtil.show(GamePackageDetailActivity.this,
								"验证码发送失败，请稍后重试");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (msg.what == Const.MSG_GET_FAIL) {
				if (!isFirst) {
					showValidateCodeDialog();
				} else {
					lastTime = 0;
				}
				ToastUtil.show(GamePackageDetailActivity.this, "验证码发送失败，请稍后重试");
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EgameApplication) getApplication();
		setContentView(R.layout.egame_gamepackagedetail);
		INSTANCE = this;
		initView();
		bundle = this.getIntent().getExtras();
		packageId = bundle.getString("packageId");
		list = new ArrayList<GameListBean>();
		adapter = new TestGameListAdapter(list, GamePackageDetailActivity.this,
				SourceUtils.PACKAGE, false);
		lvGamePackageDetail.setAdapter(adapter);
		initData();
		initEvent();
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(MSG_CONTENT_STR);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
		br = new ListNotifyReceiver(adapter);
		IntentFilter intentFilter = new IntentFilter(
				"com.egame.app.uis.GameDownloadMissionActivity");
		this.registerReceiver(br, intentFilter);
		EgameApplication.Instance().addActivity(this);
	}

	/**
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
		re = new GetMsgContextReceiver();
		IntentFilter itf = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		this.registerReceiver(re, itf);
	}

	/**
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("GamePackageDetailActivity");
		this.unregisterReceiver(re);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.getDbService().close();
		unregisterReceiver(br);
		unregisterReceiver(mBroadcastReceiver);
	}

	public void initData() {
		lvGamePackageDetail.setVisibility(View.GONE);
		loading.showLoading();
		if (packageDetailBean != null) {
			packageDetailBean.getList().clear();
			list.clear();
			adapter.notifyDataSetChanged();
		}
		new GamePackageDetailTask(GamePackageDetailActivity.this,
				packageDetailBean, adapter, application.getPhoneNum(),
				packageId, LoginDataStoreUtil.fetchUser(
						GamePackageDetailActivity.this,
						LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
				application.getUA()).execute();
	}

	public void initView() {
		progress = DialogUtil.getProgressDialog(GamePackageDetailActivity.this);
		progress.setMessage("请稍后...");
		loading = new Loading(GamePackageDetailActivity.this);
		btnBack = (Button) findViewById(R.id.back);
		lvGamePackageDetail = (ListView) findViewById(R.id.gamepackagedetail);
		tvTitle = (TextView) findViewById(R.id.title);
		llHead = (LinearLayout) LayoutInflater.from(
				GamePackageDetailActivity.this).inflate(
				R.layout.egame_gamepackagedetail_head, null);
		tvPackagename = (TextView) llHead.findViewById(R.id.packagename);
		tvCount = (TextView) llHead.findViewById(R.id.count);
		llDinggou = (LinearLayout) llHead.findViewById(R.id.dinggou);
		tvPrice = (TextView) llHead.findViewById(R.id.price);
		tvPackage_detail_desc = (TextView) llHead
				.findViewById(R.id.package_detail_desc);
		lvGamePackageDetail.addHeaderView(llHead);
		dialogLayout = (LinearLayout) LayoutInflater.from(
				GamePackageDetailActivity.this).inflate(
				R.layout.egame_package_order, null);
		etValidateCode = (EditText) dialogLayout
				.findViewById(R.id.validateCode);
	}

	public void initViewData() {
		tvTitle.setText("游戏优惠频道-" + packageDetailBean.getPackageName());
		tvPackagename.setText(packageDetailBean.getPackageName());
		tvCount.setText("每月推出精品游戏" + packageDetailBean.getList().size() + "款");
		tvPackage_detail_desc.setText(packageDetailBean.getReCommend());
		if (packageDetailBean.isOrder()) {
			if ("322".equals(packageId)) {
				tvPrice.setText("如何退订");
			} else {
				tvPrice.setText("您已订购（点击可退订）");
			}
		} else {
			if ("322".equals(packageId)) {
				tvPrice.setText("如何订购");
			} else {
				tvPrice.setText("立即订购（" + packageDetailBean.getPrice() + "）");
			}
		}
		if (progress.isShowing() == true) {
			progress.dismiss();
		}
		loading.setVisibility(View.GONE);
		lvGamePackageDetail.setVisibility(View.VISIBLE);
	}

	/**
	 * 自定义广播处理类
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			L.d(TAG, "广播接收器接收到广播");
			String action = intent.getAction();
			if (action.equals(MSG_CONTENT_STR)) {
				// 填充验证码
				etValidateCode.setText(intent.getStringExtra("validateCode"));
			}
		}

	};

	public void showOrderState(int orderState, String s) {
		// 无论订购（退订）成功或者失败，都允许重新获取验证码
		lastTime = 0;
		isFirst = true;
		if (progress.isShowing() == true) {
			progress.dismiss();
		}
		if (orderState == 1) {
			// tvPrice.setText("您已订购（点击可退订）");
			ToastUtil.show(GamePackageDetailActivity.this, s);
			// 提示游戏包列表页面刷新数据
			setResult(Const.MSG_GET_SUCCESS);
		} else if (orderState == 2) {
			// tvPrice.setText("立即订购（" + packageDetailBean.getPrice()
			// + "）");
			ToastUtil.show(GamePackageDetailActivity.this, s);
			setResult(Const.MSG_GET_SUCCESS);
		} else {
			ToastUtil.show(GamePackageDetailActivity.this, s);
		}
	}

	public void initEvent() {
		lvGamePackageDetail.setOnItemClickListener(this);
		llDinggou.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		loading.setOnReloadClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loading.showLoading();
				new GamePackageDetailTask(GamePackageDetailActivity.this,
						packageDetailBean, adapter, application.getPhoneNum(),
						packageId, LoginDataStoreUtil.fetchUser(
								GamePackageDetailActivity.this,
								LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
						application.getUA()).execute();
			}
		});
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == 0) {
			return;
		}
		Intent intent = new Intent(GamePackageDetailActivity.this,
				GamesDetailActivity.class);
		intent.putExtras((GamesDetailActivity.makeIntentData(list.get(arg2 - 1)
				.getGameId(), SourceUtils.PACKAGE)));
		startActivity(intent);
	}

	public void onClick(View v) {
		if (v == btnBack) {
			finish();
		} else if (v == llDinggou) {

			if ("322".equals(packageId)) {
				// 如果属于畅游包的,提示用户去营业厅订购
				new AlertDialog.Builder(this).setTitle("提示")
						.setMessage("目前游戏包业务仅支持天翼用户使用。天翼用户请拨打10000号订购畅游包")
						.setPositiveButton("确定", null).show();
			} else {
				// if (StringUtil.isNullOrEmpty(application.getIMSI())) {
				// new
				// AlertDialog.Builder(this).setTitle("提示").setMessage("仅供电信用户使用").setPositiveButton("确定",
				// null)
				// .show();
				// } else {
				// 属于电信用户

				if (isCtUser()) {
					if (TextUtils.isEmpty(application.getPhoneNum())
							|| application.getPhoneNum().equals("00000000000")) {
						DialogUtil.showDialog(GamePackageDetailActivity.this,
								"订购游戏包失败，请稍后重试。");
					} else {
						if (CommonUtil.canOrderPackage(packageDetailBean
								.getIsOrder())) {
							showOrderDialog();
						} else {
							showCancelDialog();
						}
					}
				} else { // 不属于电信用户
					DialogUtil.showDialog(GamePackageDetailActivity.this,
							"目前游戏包业务仅支持天翼用户使用");
				}
				// 不属于畅游包,提示用户是否订购/退订
				// if (ApnUtils.isCtwap(GamePackageDetailActivity.this)) {
				// if (TextUtils.isEmpty(application.getPhoneNum()) ||
				// application.getPhoneNum().equals("00000000000")) {
				// DialogUtil.showDialog(GamePackageDetailActivity.this,
				// "系统检测到异常，请重启软件后再订购。");
				// } else {
				// if
				// (CommonUtil.canOrderPackage(packageDetailBean.getIsOrder()))
				// {
				// showOrderDialog();
				// } else {
				// showCancelDialog();
				// }
				// }
				// } else {
				// DialogUtil.showDialog(GamePackageDetailActivity.this,
				// "请使用中国电信CTWAP方式连接。");
				// }
			}
		}
		// }
	}

	/**
	 * 是否属于电信用户,根据imsi判断
	 * 
	 * @return
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2012-6-20
	 */
	private boolean isCtUser() {
		TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telManager.getSubscriberId() + "";
		if (imsi.startsWith("46003")) {
			return true;
		} else {
			return false;
		}
	}

	public static Bundle makeIntentData(String packageId) {
		Bundle bd = new Bundle();
		bd.putString("packageId", packageId);
		return bd;
	}

	public void dataCodeProcess(PackageDetailBean mPackageDetailBean) {
		if (mPackageDetailBean == null) {
			loading.showReload();
		} else {
			packageDetailBean = mPackageDetailBean;
			list.addAll(packageDetailBean.getList());
			initViewData();
			new GetListIconAsyncTask<GameListBean>(
					mPackageDetailBean.getList(), adapter).execute("");
		}
	}

	void showOrderDialog() {

		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (ApnUtils.isCtwap(GamePackageDetailActivity.this)) { // ctwap订购,保持以前的流程不变
					packageOrder(packageDetailBean.getDinggou(), null);
					// progress.show();
					// new
					// OrderGamePackageAsyncTask(GamePackageDetailActivity.this,
					// packageDetailBean, packageId,
					// application.getPhoneNum(), application.getUA(),
					// LoginDataStoreUtil.fetchUser(GamePackageDetailActivity.this,
					// LoginDataStoreUtil.LOG_FILE_NAME).getUserId()).execute(packageDetailBean.getDinggou());
				} else { // 非ctwap订购
					packageUrl = packageDetailBean.getDinggou();
					showSendDialog();
					// sendValidateCode();
					// showValidateCodeDialog();
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
		AlertDialog.Builder builder = ds.getBuilder(
				GamePackageDetailActivity.this, "确定", "取消", comfirmL, cancelL);

		builder.setTitle("爱游戏")
				.setMessage(
						"你确认订购中国电信游戏运营中心提供的"
								+ packageDetailBean.getPackageName() + "("
								+ packageDetailBean.getPrice() + ")" + "吗？")
				.create().show();
	}

	void showCancelDialog() {
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				L.d("游戏包",
						"" + ApnUtils.isCtwap(GamePackageDetailActivity.this));
				if (ApnUtils.isCtwap(GamePackageDetailActivity.this)) {
					packageOrder(packageDetailBean.getTuiding(), null);
				} else {
					packageUrl = packageDetailBean.getTuiding();
					showSendDialog();
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
		AlertDialog.Builder builder = ds.getBuilder(
				GamePackageDetailActivity.this, "确定", "取消", comfirmL, cancelL);

		builder.setTitle("你确定退订该游戏包吗？").create().show();
	}

	void showValidateCodeDialog() {
		dialogLayout = (LinearLayout) LayoutInflater.from(
				GamePackageDetailActivity.this).inflate(
				R.layout.egame_package_order, null);
		etValidateCode = (EditText) dialogLayout
				.findViewById(R.id.validateCode);

		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				packageOrder(packageUrl, etValidateCode.getText().toString());

			}
		};
		DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendValidateCode();
			}
		};
		DialogStyle ds = new DialogStyle();
		AlertDialog.Builder builder = ds
				.getBuilder(GamePackageDetailActivity.this, "确定", "重新发送",
						comfirmL, cancelL);
		builder.setView(dialogLayout).setTitle(R.string.egame_dialog_title)
				.create().show();

	}

	/**
	 * 显示“点击发送验证码”的对话框
	 * */
	void showSendDialog() {
		new AlertDialog.Builder(GamePackageDetailActivity.this)
				.setTitle(R.string.egame_dialog_title)
				.setMessage("为了您的账号安全，请先验证手机号")
				.setPositiveButton(R.string.egame_getValidateCode,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								sendValidateCode();
							}
						}).create().show();
	}

	/**
	 * 本网非CTWAP用户获取验证码
	 **/
	void sendValidateCode() {
		L.d("游戏包验证码", "上次请求验证码时间" + lastTime + "||是否是第一次请求:" + isFirst);
		currentTime = System.currentTimeMillis();
		// 首次请求验证码或验证码请求间隔时间超过1分钟
		if (lastTime == 0 || currentTime - lastTime > 60000) {
			progress.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = new Message();
					try {
						String s = HttpConnect.getHttpString(Urls
								.getPackageValidateCodeUrl(application
										.getPhoneNum()));
						msg.what = Const.MSG_GET_SUCCESS;
						msg.obj = s;
						handler.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
						msg.what = Const.MSG_GET_FAIL;
						handler.sendMessage(msg);
					}
				}
			}).start();
		} else {
			showValidateCodeDialog();
			ToastUtil.show(GamePackageDetailActivity.this, "再过"
					+ (60000 - (currentTime - lastTime)) / 1000 + "秒才可再次获取验证码");
		}

	}

	/**
	 * 订购或退订游戏包
	 * 
	 * @param orderOrNotUrl
	 *            :订购退订链接
	 * @param validateCode
	 *            :验证码（CTWAP用户为空值）
	 * */
	void packageOrder(String orderOrNotUrl, String validateCode) {
		progress.show();
		// L.d(TAG,
		// "http://202.102.39.18:9084/sns-clientV4/sso/orderPackage?PACKAGEID="
		// + packageId
		// + "&PRODUCTID=135000000000000020456&USERID=81895398" + "&MSISDN=" +
		// application.getPhoneNum()
		// + "&validatecode=" + validateCode);
		// orderOrNotUrl = orderOrNotUrl.replace("http://202.102.39.13:8084",
		// "http://202.102.39.18:9084");
		L.d(TAG, orderOrNotUrl);
		new OrderGamePackageAsyncTask(GamePackageDetailActivity.this,
				packageDetailBean, packageId, application.getPhoneNum(),
				application.getUA(), LoginDataStoreUtil.fetchUser(
						GamePackageDetailActivity.this,
						LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
				validateCode).execute(orderOrNotUrl);
	}
}
