/**
 * 
 */
package com.egame.app.uis;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.FindPasswordTask;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.ContactsUtils;
import com.egame.utils.ui.ToastUtil;
import com.egame.utils.ui.Utils;
import com.eshore.network.stat.NetStat;

/**
 * 描述：找回密码主界面
 * 
 * @author LiuHan
 * @version 1.0 create on:Fri 2 Mar,2012
 */
public class FindPassWordActivity extends Activity implements OnClickListener {

	private TextView mFindWay;
	private String[] mKeyArray;
	private int mKey = 0;
	private EditText mInput;
	private Button mFindButton;
	private TextView mBack;
	public static Pattern emailPattern = Pattern.compile("(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}");
	private UpdateHandler myHandler;
	private Thread threads;
	private int timer = 60000;
	private int mPhoneCount = 0, mEmailCount = 0;
	private TextView mPrompt;
	// 两个小时
	public final static int mConstTime = 7200000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_find_pass_layout);
		initControlUI();
	}

	/**
	 * 取得UI控件的引用
	 */
	private void initControlUI() {
		mFindWay = (TextView) this.findViewById(R.id.m_find_way);
		mFindWay.setOnClickListener(this);
		mKeyArray = getResources().getStringArray(R.array.egame_findpass_way);
		mFindWay.setText(mKeyArray[mKey]);
		mInput = (EditText) this.findViewById(R.id.m_input);
		mFindButton = (Button) this.findViewById(R.id.m_find_password);
		mFindButton.setOnClickListener(this);
		mBack = (TextView) this.findViewById(R.id.m_find_back);
		mBack.setOnClickListener(this);
		mPrompt = (TextView) this.findViewById(R.id.m_prompt);
		mPrompt.setVisibility(View.GONE);
		// 取得当前时间
	}

	/**
	 * 单击事件处理函数
	 */
	@Override
	public void onClick(View arg0) {
		if (arg0 == mFindWay) {
			openFindWay();
		} else if (arg0 == mFindButton) {
			// 输入框内容非空判断
			if ("".equals(mInput.getText().toString())) {
				ToastUtil.show(FindPassWordActivity.this, getResources().getString(R.string.egame_find_pass_prompt));
			} else {
				if (mKey == 0 && mPhoneCount <= 5) {
					// 输入的是手机号 手机号和邮箱的有效性验证
					String str = mInput.getText().toString();
					if (ContactsUtils.checkPhoneNum(str)) {
						if (isCanFind(str)) {
							int curTime = (int) System.currentTimeMillis();
							int storeTime = PreferenceUtil.fetchFindPasswordTime(FindPassWordActivity.this, mInput.getText().toString());
							Log.i("间隔时间", (curTime - storeTime) + "");
							if (-1 == storeTime || (60000 - (curTime - storeTime)) <= 1000) {
								PreferenceUtil.storeFindPasswordTime(FindPassWordActivity.this, mInput.getText().toString(),
										(int) System.currentTimeMillis());
								Log.i("storeTime", PreferenceUtil.fetchFindPasswordTime(FindPassWordActivity.this, mInput.getText().toString()) + "");
								new FindPasswordTask(FindPassWordActivity.this, str).execute("phone");
								timer = 60000;
								mPrompt.setVisibility(View.VISIBLE);
								myHandler = new UpdateHandler();
								UpdateThread m = new UpdateThread();
								threads = new Thread(m);
								threads.start();
								mPhoneCount++;
							} else {
								Toast.makeText(FindPassWordActivity.this, "再过" + (60000 - (curTime - storeTime)) / 1000 + "秒才可再次找回",
										Toast.LENGTH_SHORT).show();
							}
						}

					} else {
						ToastUtil.show(FindPassWordActivity.this, getResources().getString(R.string.egame_find_pass_phone_prompt));
					}
				} else if (mKey == 1 && mEmailCount <= 5) {
					// 输入的是邮箱 检查邮箱的有效性
					String spr = mInput.getText().toString();
					if (Utils.checkEmailWithSuffix(mInput.getText().toString())) {
						if (isCanFind(spr)) {
							int curTime = (int) System.currentTimeMillis();
							int storeTime = PreferenceUtil.fetchFindPasswordTime(FindPassWordActivity.this, mInput.getText().toString());
							if (-1 == storeTime || 60000 - (curTime - storeTime) <= 1000) {
								PreferenceUtil.storeFindPasswordTime(FindPassWordActivity.this, mInput.getText().toString(),
										(int) System.currentTimeMillis());
								new FindPasswordTask(FindPassWordActivity.this, mInput.getText().toString()).execute("email");
								timer = 60000;
								mPrompt.setVisibility(View.VISIBLE);
								myHandler = new UpdateHandler();
								UpdateThread m = new UpdateThread();
								threads = new Thread(m);
								threads.start();
								mEmailCount++;

							} else {
								Toast.makeText(FindPassWordActivity.this, "再过" + (60000 - (curTime - storeTime)) / 1000 + "秒才可再次找回",
										Toast.LENGTH_SHORT).show();
							}

						}
					} else {
						ToastUtil.show(FindPassWordActivity.this, getResources().getString(R.string.egame_find_pass_email_prompt));
					}
				} else if (mKey == 0 && mPhoneCount > 5) {
					// 手机找回密码次数过多
					Toast.makeText(FindPassWordActivity.this, "对不起，您的找回次数过多，请稍后重试", Toast.LENGTH_SHORT).show();
					PreferenceUtil.storeFindFailKey(FindPassWordActivity.this, mInput.getText().toString(), (int) System.currentTimeMillis());
					PreferenceUtil.storeFindFailKstr(FindPassWordActivity.this, mInput.getText().toString());
				} else if (mKey == 1 && mEmailCount > 5) {
					// 邮箱找回密码次数过多
					Toast.makeText(FindPassWordActivity.this, "对不起，您的找回次数过多，请稍后重试", Toast.LENGTH_SHORT).show();
					PreferenceUtil.storeFindFailKey(FindPassWordActivity.this, mInput.getText().toString(), (int) System.currentTimeMillis());
					PreferenceUtil.storeFindFailKstr(FindPassWordActivity.this, mInput.getText().toString());
				}

			}

		} else if (arg0 == mBack) {
			FindPassWordActivity.this.finish();
		}
	}

	public boolean isCanFind(String key) {
		int leftTime = ((int) System.currentTimeMillis() - PreferenceUtil.fetchFindTime(FindPassWordActivity.this, mInput.getText().toString()));
		if (leftTime <= mConstTime && -1 != PreferenceUtil.fetchFindTime(FindPassWordActivity.this, mInput.getText().toString())) {
			// 暂时还不可以找
			Toast.makeText(FindPassWordActivity.this, (mConstTime - leftTime) / 1000 + "秒后可再找回", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	private void openFindWay() {
		new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.egame_AlertDialogType))
				.setSingleChoiceItems(mKeyArray, mKey, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						mKey = item;
						mFindWay.setText(mKeyArray[item]);
						dialog.dismiss();
					}
				}).create().show();
	}

	class UpdateThread implements Runnable {
		public void run() {
			while (timer > 0) {
				timer = timer - 1000;
				Message msg = new Message();
				msg.what = timer;
				myHandler.sendMessage(msg);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	class UpdateHandler extends Handler {
		public UpdateHandler() {

		}

		public UpdateHandler(Looper L) {
			super(L);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what >= 1000) {
				mFindButton.setEnabled(false);
				mFindButton.setTextColor(Color.WHITE);
				mFindButton.setBackgroundResource(R.drawable.egame_login_forbid_icon);
				mPrompt.setText(msg.what / 1000 + "秒后可再次发送");
			} else {
				mFindButton.setEnabled(true);
				mFindButton.setTextColor(getResources().getColor(R.color.egame_register_color));
				mFindButton.setBackgroundResource(R.drawable.egame_user_other_login_selector);
				mPrompt.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("FindPassWordActivity");
	}
}
