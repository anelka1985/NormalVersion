package com.egame.app.uis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.tasks.GetHideSettingTask;
import com.egame.app.tasks.HideSettingTask;
import com.egame.app.tasks.ModifyPasswdTask;
import com.egame.beans.HideBean;
import com.egame.beans.ResultBean;
import com.egame.config.RegexChk;
import com.egame.config.Urls;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.ProgressDialogInterface;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

/**
 * 
 * 类说明：隐私设置
 * 
 * @创建时间 2012-1-4
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class HideSettingActivity extends Activity implements OnClickListener,
		ProgressDialogInterface {
	/** 返回按钮 */
	private ImageView ivBack;

	/** 谁能看我的主页布局--任何人 */
	private LinearLayout llHomepageRhr;

	/** 谁能看我的主页布局--仅好友 */
	private LinearLayout llHomepageJhy;

	/** 谁能给我发消息布局--任何人 */
	private LinearLayout llSendMesageRhr;

	/** 谁能给我发消息布局--仅好友 */
	private LinearLayout llSendMesageJhy;

	/** 是否显示我的年龄布局--允许 */
	private LinearLayout llMyAgeRhr;

	/** 是否显示我的年龄布局--不允许 */
	private LinearLayout llMyAgeJhy;

	/** 是否显示我的所在地布局--允许 */
	private LinearLayout llMySiteRhr;

	/** 是否显示我的所在地布局--不允许 */
	private LinearLayout llMySiteJhy;

	/** 谁能看我的主页--任何人 */
	private TextView ivMyHomepageRhr;

	/** 谁能看我的主页--仅好友 */
	private TextView ivMyHomepageJhy;

	/** 谁能给我发消息--任何人 */
	private TextView ivSendMesageRhr;

	/** 谁能给我发消息--仅好友 */
	private TextView ivSendMesageJhy;

	/** 是否显示我的年龄--允许 */
	private TextView ivMyAgeRhr;

	/** 是否显示我的年龄--不允许 */
	private TextView ivMyAgeJhy;

	/** 是否显示我的所在地--允许 */
	private TextView ivMySiteRhr;

	/** 是否显示我的所在地--不允许 */
	private TextView ivMySiteJhy;

	/** 保存设置 */
	private Button btnSave;

	/** 原密码 */
	private EditText etOriginalPassword;

	/** 新密码 */
	private EditText etNewPassword;

	/** 确认密码 */
	private EditText etConfirmPassword;

	/** 确定修改密码按钮 */
	private Button btnModify;

	/** 新密码 */
	private boolean newPasswordFlag;

	private ProgressDialog mProgressDialog;

	private int homePage = 11;

	private int sendMessage = 61;

	private int myAge = 91;

	private int mySite = 101;

	EgameApplication application;

	private boolean modify = true;

	private boolean hideModify = false;

	String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_hide);
		application = (EgameApplication) getApplication();
		initView();
		initEvent();
		// 取得用户的隐私设置的初始值
		new GetHideSettingTask(this).execute("");
	}

	/**
	 * 初始化ui，主要是实现findViewById的操作
	 */
	void initView() {
		userId = LoginDataStoreUtil.fetchUser(HideSettingActivity.this,
				LoginDataStoreUtil.LOG_FILE_NAME).getUserId();
		ivBack = (ImageView) findViewById(R.id.back);

		llHomepageRhr = (LinearLayout) findViewById(R.id.llHomepageRhr);
		llHomepageJhy = (LinearLayout) findViewById(R.id.llHomepageJhy);
		llSendMesageRhr = (LinearLayout) findViewById(R.id.llSendMesageRhr);
		llSendMesageJhy = (LinearLayout) findViewById(R.id.llSendMesageJhy);
		llMyAgeRhr = (LinearLayout) findViewById(R.id.llMyAgeRhr);
		llMyAgeJhy = (LinearLayout) findViewById(R.id.llMyAgeJhy);
		llMySiteRhr = (LinearLayout) findViewById(R.id.llMySiteRhr);
		llMySiteJhy = (LinearLayout) findViewById(R.id.llMySiteJhy);

		ivMyHomepageRhr = (TextView) findViewById(R.id.myHomepageRhr);
		ivMyHomepageJhy = (TextView) findViewById(R.id.myHomepageJhy);

		ivSendMesageRhr = (TextView) findViewById(R.id.sendMesageRhr);
		ivSendMesageJhy = (TextView) findViewById(R.id.sendMesageJhy);

		ivMyAgeRhr = (TextView) findViewById(R.id.myAgeRhr);
		ivMyAgeJhy = (TextView) findViewById(R.id.myAgeJhy);

		ivMySiteRhr = (TextView) findViewById(R.id.mySiteRhr);
		ivMySiteJhy = (TextView) findViewById(R.id.mySiteJhy);

		etOriginalPassword = (EditText) findViewById(R.id.originalPassword);
		etNewPassword = (EditText) findViewById(R.id.newPassword);
		etConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
		btnModify = (Button) findViewById(R.id.modify);
		btnSave = (Button) findViewById(R.id.save);

	}

	public void initData(HideBean hBean) {
		// SharedPreferences sharedPreferences = getSharedPreferences("hide",
		// Context.MODE_WORLD_READABLE);
		// homePage = sharedPreferences.getInt("homePage", 11);
		// sendMessage = sharedPreferences.getInt("sendMessage", 61);
		// myAge = sharedPreferences.getInt("myAge", 91);
		// mySite = sharedPreferences.getInt("mySite", 101);
	    homePage = hBean.getHomePage();
	    sendMessage = hBean.getSendMessage();
	    myAge = hBean.getMyAge();
	    mySite =  hBean.getMySite();
		initHideSetting(homePage, "homePage");
		initHideSetting(sendMessage, "sendMessage");
		initHideSetting(myAge, "myAge");
		initHideSetting(mySite, "mySite");

	}

	/**
	 * 初始化隐私设置
	 * 
	 * @param flag
	 * @param name
	 */
	void initHideSetting(int flag, String name) {
		if ("homePage".equals(name)) {
			if (flag == 11) {
				ivMyHomepageRhr.setBackgroundResource(R.drawable.egame_lvse);
				ivMyHomepageRhr.setTextColor(Color.WHITE);
				ivMyHomepageJhy
						.setBackgroundResource(R.drawable.egame_huiseright);
				ivMyHomepageJhy.setTextColor(Color.GRAY);
			} else {
				ivMyHomepageJhy
						.setBackgroundResource(R.drawable.egame_lvseright);
				ivMyHomepageJhy.setTextColor(Color.WHITE);
				ivMyHomepageRhr
						.setBackgroundResource(R.drawable.egame_huiseleft);
				ivMyHomepageRhr.setTextColor(Color.GRAY);
			}

		} else if ("sendMessage".equals(name)) {
			if (flag == 61) {
				ivSendMesageRhr.setBackgroundResource(R.drawable.egame_lvse);
				ivSendMesageRhr.setTextColor(Color.WHITE);
				ivSendMesageJhy
						.setBackgroundResource(R.drawable.egame_huiseright);
				ivSendMesageJhy.setTextColor(Color.GRAY);
			} else {
				ivSendMesageJhy
						.setBackgroundResource(R.drawable.egame_lvseright);
				ivSendMesageJhy.setTextColor(Color.WHITE);
				ivSendMesageRhr
						.setBackgroundResource(R.drawable.egame_huiseleft);
				ivSendMesageRhr.setTextColor(Color.GRAY);
			}
		} else if ("myAge".equals(name)) {
			if (flag == 91) {
				ivMyAgeRhr.setBackgroundResource(R.drawable.egame_lvse);
				ivMyAgeRhr.setTextColor(Color.WHITE);
				ivMyAgeJhy.setBackgroundResource(R.drawable.egame_huiseright);
				ivMyAgeJhy.setTextColor(Color.GRAY);
			} else {

				ivMyAgeJhy.setBackgroundResource(R.drawable.egame_lvseright);
				ivMyAgeJhy.setTextColor(Color.WHITE);
				ivMyAgeRhr.setBackgroundResource(R.drawable.egame_huiseleft);
				ivMyAgeRhr.setTextColor(Color.GRAY);
			}
		} else if ("mySite".equals(name)) {
			if (flag == 101) {
				ivMySiteRhr.setBackgroundResource(R.drawable.egame_lvse);
				ivMySiteRhr.setTextColor(Color.WHITE);
				ivMySiteJhy.setBackgroundResource(R.drawable.egame_huiseright);
				ivMySiteJhy.setTextColor(Color.GRAY);
			} else {
				ivMySiteJhy.setBackgroundResource(R.drawable.egame_lvseright);
				ivMySiteJhy.setTextColor(Color.WHITE);
				ivMySiteRhr.setBackgroundResource(R.drawable.egame_huiseleft);
				ivMySiteRhr.setTextColor(Color.GRAY);
			}
		}

	}

	/**
	 * 初始化事件,主要是实现按钮点击等事件的处理
	 */
	void initEvent() {
		ivBack.setOnClickListener(this);

		// 谁能看我的主页

		llHomepageRhr.setOnClickListener(this);
		llHomepageJhy.setOnClickListener(this);

		// 谁能给我发消息
		llSendMesageRhr.setOnClickListener(this);
		llSendMesageJhy.setOnClickListener(this);
		// 是否显示我的年龄
		llMyAgeRhr.setOnClickListener(this);
		llMyAgeJhy.setOnClickListener(this);
		// 是否显示我的所在地

		llMySiteRhr.setOnClickListener(this);
		llMySiteJhy.setOnClickListener(this);

		etNewPassword.setOnFocusChangeListener(etNewPasswordListener);
		// 修改密码
		btnModify.setOnClickListener(this);

		// 保存设置
		btnSave.setOnClickListener(this);
	}

	public void onClick(View v) {
		// 谁能看我的主页

		if (v == llHomepageRhr) {
			ivMyHomepageRhr.setBackgroundResource(R.drawable.egame_lvse);
			ivMyHomepageRhr.setTextColor(Color.WHITE);
			ivMyHomepageJhy.setBackgroundResource(R.drawable.egame_huiseright);
			ivMyHomepageJhy.setTextColor(Color.GRAY);
			homePage = 11;
			hideModify = true;
		}

		if (v == llHomepageJhy) {
			ivMyHomepageJhy.setBackgroundResource(R.drawable.egame_lvseright);
			ivMyHomepageJhy.setTextColor(Color.WHITE);
			ivMyHomepageRhr.setBackgroundResource(R.drawable.egame_huiseleft);
			ivMyHomepageRhr.setTextColor(Color.GRAY);
			homePage = 12;
			hideModify = true;
		}
		// 谁能给我发消息

		if (v == llSendMesageRhr) {
			ivSendMesageRhr.setBackgroundResource(R.drawable.egame_lvse);
			ivSendMesageRhr.setTextColor(Color.WHITE);
			ivSendMesageJhy.setBackgroundResource(R.drawable.egame_huiseright);
			ivSendMesageJhy.setTextColor(Color.GRAY);
			sendMessage = 61;
			hideModify = true;
		}

		if (v == llSendMesageJhy) {
			ivSendMesageJhy.setBackgroundResource(R.drawable.egame_lvseright);
			ivSendMesageJhy.setTextColor(Color.WHITE);
			ivSendMesageRhr.setBackgroundResource(R.drawable.egame_huiseleft);
			ivSendMesageRhr.setTextColor(Color.GRAY);
			sendMessage = 62;
			hideModify = true;
		}

		// 是否显示我的年龄

		if (v == llMyAgeRhr) {
			ivMyAgeRhr.setBackgroundResource(R.drawable.egame_lvse);
			ivMyAgeRhr.setTextColor(Color.WHITE);
			ivMyAgeJhy.setBackgroundResource(R.drawable.egame_huiseright);
			ivMyAgeJhy.setTextColor(Color.GRAY);
			myAge = 91;
			hideModify = true;
		}

		if (v == llMyAgeJhy) {

			ivMyAgeJhy.setBackgroundResource(R.drawable.egame_lvseright);
			ivMyAgeJhy.setTextColor(Color.WHITE);
			ivMyAgeRhr.setBackgroundResource(R.drawable.egame_huiseleft);
			ivMyAgeRhr.setTextColor(Color.GRAY);
			myAge = 92;
			hideModify = true;
		}

		// 是否显示我的所在地
		if (v == llMySiteRhr) {
			ivMySiteRhr.setBackgroundResource(R.drawable.egame_lvse);
			ivMySiteRhr.setTextColor(Color.WHITE);
			ivMySiteJhy.setBackgroundResource(R.drawable.egame_huiseright);
			ivMySiteJhy.setTextColor(Color.GRAY);
			mySite = 101;
			hideModify = true;
		}

		if (v == llMySiteJhy) {
			ivMySiteJhy.setBackgroundResource(R.drawable.egame_lvseright);
			ivMySiteJhy.setTextColor(Color.WHITE);
			ivMySiteRhr.setBackgroundResource(R.drawable.egame_huiseleft);
			ivMySiteRhr.setTextColor(Color.GRAY);
			mySite = 102;
			hideModify = true;
		}

		// 返回按钮
		if (v == ivBack) {
			if (modify && hideModify) {
				dialog();
			} else {
				HideSettingActivity.this.finish();
			}
		}

		// 修改密码
		if (v == btnModify) {
			if ("".equals(etOriginalPassword.getText().toString())
					|| null == etOriginalPassword.getText().toString()) {
				ToastUtil.show(HideSettingActivity.this,
						R.string.egame_originalPasswordFlag);
				return;
			}

			if (!newPasswordFlag) {
				ToastUtil.show(HideSettingActivity.this,
						R.string.egame_passwordRule);
				return;
			}

			if (!etNewPassword.getText().toString()
					.equals(etConfirmPassword.getText().toString())) {
				L.d("dd", "new:" + etNewPassword.getText().toString()
						+ "||confirm:" + etConfirmPassword.getText().toString());
				ToastUtil.show(HideSettingActivity.this,
						R.string.egame_passwowdDisaccord);
				return;
			}

			// 修改密码操作传输到服务器
			String url = Urls.modifyPassword(HideSettingActivity.this, userId,
					etOriginalPassword.getText().toString(), etNewPassword
							.getText().toString());
			ModifyPasswdTask modifyPasswdTask = new ModifyPasswdTask(
					HideSettingActivity.this);
			modifyPasswdTask.execute(url);
		}

		// 保存设置
		if (v == btnSave) {
			modify = false;
			saveHideSetting();
		}

	}

	/**
	 * 确认隐私设置
	 */
	private void saveHideSetting() {
		String hideUrl = Urls.saveHide(HideSettingActivity.this, homePage,
				sendMessage, myAge, mySite, userId);
		HideSettingTask hideSettingTask = new HideSettingTask(
				HideSettingActivity.this);
		hideSettingTask.execute(hideUrl);
	}

	/**
	 * 修改密码的结果
	 * 
	 * @param result
	 */
	public void modifyPasswdResult(ResultBean resultBean) {
		if ("0".equals(resultBean.getResultCode())) {
			// 根据result判断
			ToastUtil.show(HideSettingActivity.this,
					R.string.egame_modifyPasswordSuccess);
			cleanPasswd();
		} else {
			ToastUtil.show(HideSettingActivity.this,
			    resultBean.getResultMessage());
		}

	}

	/**
	 * 密码输入框制空
	 */
	private void cleanPasswd() {
		etOriginalPassword.setText("");
		etNewPassword.setText("");
		etConfirmPassword.setText("");
	}

	/**
	 * 修改隐私结果
	 * 
	 * @param result
	 */
	public void saveHideResult(String result) {
		if ("0".equals(result)) {
			// SharedPreferences sharedPreferences =
			// getSharedPreferences("hide", Context.MODE_WORLD_READABLE);
			// Editor edit = sharedPreferences.edit();
			// edit.putInt("homePage", homePage);
			// edit.putInt("sendMessage", sendMessage);
			// edit.putInt("myAge", myAge);
			// edit.putInt("mySite", mySite);
			// edit.commit();
			ToastUtil.show(HideSettingActivity.this,
					R.string.egame_modifySuccess);
		} else {
			ToastUtil.show(HideSettingActivity.this, R.string.egame_saveFaild);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			HideSettingActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// 新密码输入框规定
	private EditText.OnFocusChangeListener etNewPasswordListener = new EditText.OnFocusChangeListener() {

		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			if (etNewPassword.hasFocus() == false) {
				if (!RegexChk.checkPw(etNewPassword.getText().toString())) {
					newPasswordFlag = false;
					ToastUtil.show(HideSettingActivity.this,
							R.string.egame_passwordRule);
				} else {
					newPasswordFlag = true;
				}

			}
		}

	};

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

	protected void dialog() {
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				hideModify = false;
				saveHideSetting();

			}
		};
		DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				HideSettingActivity.this.finish();
			}
		};
		DialogStyle ds = new DialogStyle();
		AlertDialog.Builder builder = ds.getBuilder(HideSettingActivity.this,
				"确定", "取消", comfirmL, cancelL);
		builder.setTitle("您修改了隐私设置，请确认修改").create().show();
	}

	/**
	 * 
	 */
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
		NetStat.onPausePage("HideSettingActivity");
	}

}
