package com.egame.app.uis;

import weibo4android.http.BASE64Encoder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.services.UpdateService;
import com.egame.utils.common.L;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.ProgressDialogInterface;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

/**
 * 
 * 类说明：软件升级
 * 
 * @创建时间 2011-12-31
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class AboutActivity extends Activity implements
		android.view.View.OnClickListener, ProgressDialogInterface {
	/**
	 * 临时文件名称
	 */
	// private final static String tempfile = "egametmp.apk";
	//
	// private final static String tempfilepath = "/data/data/com.egame/files/";

	/** 返回按钮 */
	private ImageView ivBack;

	/** 软件更新按钮 */
	private Button btnUpdate;

	private Button btnNoUpdate;

	/** 版本号 */
	private TextView tvVersion;

	private ProgressDialog mProgressDialog;

	/** 热线电话 */
	private TextView tvHotLine;

	/** 官网 */
	private TextView tvWapgame;

	/** 邮箱 */
	private TextView tvServiceMail;

	// 网上营业厅
	// private TextView tvWsyyt;

	// 掌上营业厅
	// private TextView tvZsyyt;
	private String type;
	// private String downloadpath;
	// private String version;
	public AlertDialog mDialog;
	private TextView helpTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_update_software);
		initView();
		initDate();
		initEvent();
		EgameApplication.Instance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	/**
 * 
 */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("AboutActivity");
	}

	/**
	 * 初始化ui，主要是实现findViewById的操作
	 */
	void initView() {
		ivBack = (ImageView) findViewById(R.id.back);
		btnUpdate = (Button) findViewById(R.id.update);
		btnNoUpdate = (Button) findViewById(R.id.noUpdate);
		tvVersion = (TextView) findViewById(R.id.m_version);
		helpTitle = (TextView) findViewById(R.id.helpTitle);
		PackageInfo info = null;

		// 机型都以B64编码显示
		BASE64Encoder b64 = new BASE64Encoder();
		helpTitle.setText("关于");

		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info != null) {


			tvVersion.setText(tvVersion.getText().toString()
					+ info.versionName
					+ b64.encode(PreferenceUtil.getLastUa(AboutActivity.this)
							.getBytes()));
		}

		tvHotLine = (TextView) findViewById(R.id.hotLine);
		tvWapgame = (TextView) findViewById(R.id.wapgame);
		tvServiceMail = (TextView) findViewById(R.id.servicemail);
		// tvWsyyt = (TextView) findViewById(R.id.wsyyt);
		// tvZsyyt = (TextView) findViewById(R.id.zsyyt);
	}

	/**
	 * 初始化获得软件升级相关数据
	 */
	void initDate() {
		btnNoUpdate.setVisibility(View.VISIBLE);
		tvHotLine.setText(Html.fromHtml("<u>" + "4008-289-289" + "</u>"));
		tvWapgame.setText(Html.fromHtml("<u>" + "http://game.189.cn" + "</u>"));
		tvServiceMail.setText(Html.fromHtml("<u>" + "gameservice@189.cn"
				+ "</u>"));
		// tvWsyyt.setText(Html.fromHtml("<u>" + "http://www.189.cn" + "</u>"));
		// tvZsyyt.setText(Html.fromHtml("<u>" + "http://wapzt.189.cn" +
		// "</u>"));
		type = PreferenceUtil.getUpdateType(AboutActivity.this) + "";
		// downloadpath = PreferenceUtil.getUpdatePath(AboutActivity.this);
		// version = PreferenceUtil.getNewVersion(AboutActivity.this) + "";

		if ("1".equals(type) || "2".equals(type)) {
			btnNoUpdate.setVisibility(View.GONE);
			btnUpdate.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 初始化事件,主要是实现按钮点击等事件的处理
	 */
	void initEvent() {
		ivBack.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		tvHotLine.setOnClickListener(this);
		tvServiceMail.setOnClickListener(this);
	}

	public void showProgress(int resId) {
		mProgressDialog = ProgressDialog.show(this, null, getResources()
				.getString(resId), true);
	}

	public void hideProgress() {
		try {
			mProgressDialog.dismiss();
		} catch (Exception e) {
		}
		mProgressDialog = null;
	}

	@Override
	public void onClick(View v) {
		if (v == ivBack) {
			AboutActivity.this.finish();
		} else if (v == btnUpdate) {
			View dialog = LayoutInflater.from(AboutActivity.this).inflate(
					R.layout.egame_software_update, null);
			TextView remark = (TextView) dialog.findViewById(R.id.remark);
			remark.setText("更新内容：\n"
					+ PreferenceUtil.getUpdateRemark(AboutActivity.this));
			DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					Intent intent = new Intent(AboutActivity.this,
							UpdateService.class);
					startService(intent);
					finish();

				}
			};
			DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			};
			DialogStyle ds = new DialogStyle();
			AlertDialog.Builder builder = ds.getBuilder(AboutActivity.this,
					"升级", "取消", comfirmL, cancelL);
			builder.setView(dialog).setTitle("软件升级").create().show();

		} else if (v == tvHotLine) {
			DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						Intent dialIntent = new Intent(Intent.ACTION_CALL,
								Uri.parse("tel:" + "4008289289"));
						startActivity(dialIntent);
					} catch (Exception e) {
						L.d("AboutActivity", e.getMessage());
					}
				}
			};
			DialogStyle ds = new DialogStyle();
			AlertDialog.Builder builder = ds.getBuilder(AboutActivity.this,
					"确定", "取消", comfirmL, null);
			builder.setMessage("拨打技术支持热线？");
			builder.create().show();

		} else if (v == tvServiceMail) {
			try {
				Intent mailIntent = new Intent(Intent.ACTION_SEND);
				String[] tos = { "gameservice@189.cn" };
				mailIntent.setType("message/rfc822");
				mailIntent.putExtra(Intent.EXTRA_EMAIL, tos);
				mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"egame MailService");
				startActivity(mailIntent);
			} catch (Exception e) {
				L.d("AboutActivity", e.getMessage());
				ToastUtil.show(AboutActivity.this,
						getResources().getString(R.string.egame_mykgxzdmail));
			}
		}

	}

}
