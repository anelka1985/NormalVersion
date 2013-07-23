package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.UserLoginAsyncTask;
import com.egame.beans.LoginUserBean;
import com.egame.config.RegexChk;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.Utils;
import com.eshore.network.stat.NetStat;

/**
 * 描述：用户登录界面
 * 
 * @author LiuHan
 * @version 1.0 create on:Web 29 Feb,2012
 */
public class UserLoginActivity extends Activity implements OnClickListener {
	public static UserLoginActivity instance;
	private EditText mAccount;
	private EditText mPassword;
	private Animation shake;
	private ImageView mShowPass;
	private LinearLayout mLayout;
	private Button mLogin, mRegister;
	private RelativeLayout mFindPass;
	private boolean isSelect = false;
	/** 登录的初始化变量 0:进入我的反馈 1：进入社区2:进入隐私设置3:进入游戏详情 。。。。 */
	// public static String mInitData = "0";
	/** 显示已经成功登录的用户 */
	Dialog userDialog;
	List<LoginUserBean> list = new ArrayList<LoginUserBean>();
	public final static String BROADCAST_STR = "com.egame.app.uis.UserLoginActivity";
	/** 热线电话 */
	private TextView tvHotLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_login_layout);
		instance = this;
		initControlUI();
		registerBoradcastReceiver();
	}

	/**
	 * 取得UI控件的引用
	 */
	private void initControlUI() {
		mLogin = (Button) this.findViewById(R.id.m_login_the);
		mLogin.setOnClickListener(this);
		shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		mRegister = (Button) this.findViewById(R.id.m_greenhand);
		mRegister.setOnClickListener(this);
		mFindPass = (RelativeLayout) this.findViewById(R.id.m_findpass);
		mFindPass.setOnClickListener(this);
		mAccount = (EditText) this.findViewById(R.id.m_username);
		mAccount.setText(this.getIntent().getStringExtra("account"));
		mPassword = (EditText) this.findViewById(R.id.m_password);
		mShowPass = (ImageView) this.findViewById(R.id.m_show_pass);
		mLayout = (LinearLayout) this.findViewById(R.id.layout13);
		mLayout.setOnClickListener(this);
		tvHotLine = (TextView) findViewById(R.id.hotLine);
		tvHotLine.setOnClickListener(this);
		tvHotLine.setTextColor(this.getResources().getColor(R.color.egame_rexian));
		tvHotLine.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	/**
	 * as 单击事件的处理函数
	 */
	@Override
	public void onClick(View v) {
		if (v == mLayout) {
			if (isSelect) {
				isSelect = false;
				mShowPass.setBackgroundResource(R.drawable.egame_lselect_contactson);
				mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				Editable etable = mPassword.getText();
				Selection.setSelection(etable, etable.length());
			} else {
				isSelect = true;
				mShowPass.setBackgroundResource(R.drawable.egame_select_contactsoff);
				mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				Editable etable = mPassword.getText();
				Selection.setSelection(etable, etable.length());
			}
		} else if (v == mLogin) {
			// 非空检查
			if ("".equals(mAccount.getText().toString()) || "".equals(mPassword.getText().toString())) {
				if ("".equals(mAccount.getText().toString())) {
					Toast.makeText(UserLoginActivity.this, "请输入您的账号", Toast.LENGTH_SHORT).show();
					return;
				}

				if ("".equals(mPassword.getText().toString())) {
					Toast.makeText(UserLoginActivity.this, "请输入您的密码", Toast.LENGTH_SHORT).show();
					return;
				}
			} else {

				if (!Utils.checkUsername(mAccount.getText().toString())) {
					Toast.makeText(UserLoginActivity.this, "帐号输入不合法请重新输入", Toast.LENGTH_SHORT).show();
					mAccount.setText("");
				} else {
					if ("".equals(mPassword.getText().toString())) {
						mPassword.startAnimation(shake);
						Toast.makeText(UserLoginActivity.this, "请输入您的密码", Toast.LENGTH_SHORT).show();
					} else {
						if (RegexChk.checkPw(mPassword.getText().toString())) {
							// Log.i(UserLoginActivity.class.getCanonicalName(),
							// mInitData);
							new UserLoginAsyncTask(UserLoginActivity.this, mAccount.getText().toString(), mPassword
									.getText().toString()).execute("");
						} else {
							Toast.makeText(UserLoginActivity.this, "账号或密码错误，请核实后重新输入", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
			DialogUtil.closeSoft(UserLoginActivity.this);
		} else if (v == mRegister) {
			enterNewUserRegister();
		} else if (v == mFindPass) {
			Intent intent = new Intent(UserLoginActivity.this, FindPassWordActivity.class);
			startActivity(intent);
		} else if (v == tvHotLine) {
			DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent dialIntent = new Intent(Intent.ACTION_CALL,
							Uri.parse("tel:" + "4008289289"));
					startActivity(dialIntent);
				}
			};
			DialogStyle ds = new DialogStyle();
			AlertDialog.Builder builder = ds.getBuilder(UserLoginActivity.this,
					"确定", "取消", comfirmL, null);
			builder.setMessage("拨打技术支持热线？");
			builder.create().show();
		}
	}

	/**
	 * 用户登录界面中的新用户注册流程 简单处理 不再作手机号的又一次获取
	 */
	private void enterNewUserRegister() {
		LoginDataStoreUtil.User user = LoginDataStoreUtil.fetchUser(UserLoginActivity.this,
				LoginDataStoreUtil.PAG_FILE_NAME);
		if (!LoginDataStoreUtil.NULL_VALUE.equals(user.getAccountName())
				&& LoginDataStoreUtil.NULL_VALUE.equals(user.getUserId())) {
			// 本机号没有注册
			registerPrompt(user.getAccountName());
		} else {
			// 本机号码已经注册 或者没有反查到手机号 跳到填写手机号注册界面
			// 注册流程2,验证码注册
			Intent intent = new Intent(UserLoginActivity.this, BaseInfoRegisterActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(BROADCAST_STR)) {
				if (null != intent.getStringExtra("account") && null != intent.getStringExtra("password")) {
					mAccount.setText(intent.getStringExtra("account"));
					mPassword.setText(intent.getStringExtra("password"));
				}
			}
		}

	};

	/**
	 * 注册广播s
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(BROADCAST_STR);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("UserLoginActivity");
	}

	/**
	 * 本机号没有注册 提示注册对话框
	 */
	public void registerPrompt(final String phone) {
		// 本机号码
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				Intent intent = new Intent(UserLoginActivity.this,
						FinishRegisterActivity.class);
				intent.putExtra("bundle",
						FinishRegisterActivity.getFinishRegisterBunder(phone));
				UserLoginActivity.this.startActivity(intent);

			}
		};
		DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				Intent intent = new Intent(UserLoginActivity.this,
						BaseInfoRegisterActivity.class);
				UserLoginActivity.this.startActivity(intent);

			}
		};
		DialogStyle ds = new DialogStyle();
		AlertDialog.Builder builder = ds.getBuilder(
				UserLoginActivity.this,
				UserLoginActivity.this.getResources().getString(
						R.string.egame_menu_yes), UserLoginActivity.this
						.getResources().getString(R.string.egame_menu_no),
				comfirmL, cancelL);
		builder.setTitle("系统提示").setMessage("系统检测到你的手机号，是否用本机号进行注册?").create()
				.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 注册成功
			setResult(RESULT_OK);
			finish();
		}
	}

}
