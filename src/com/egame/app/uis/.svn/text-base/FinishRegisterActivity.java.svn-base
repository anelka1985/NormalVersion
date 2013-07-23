package com.egame.app.uis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.UserRegisterAsyncTask;
import com.egame.config.Urls;
import com.egame.utils.sys.DialogStyle;
import com.eshore.network.stat.NetStat;

/**
 * 描述：能反查到手机号 却还没有注册的手机号 注册页面 也就是一般用户注册页面
 * 
 * @author LiuHan
 * @version 1.0 Sat 3 Mar,2012
 */
public class FinishRegisterActivity extends Activity implements OnClickListener {
	/** 返回按钮 */
	private TextView mFinishBack, mTextMade, mTextMen;
	/** 女性頭像显示UI控件 */
	private ImageView mViewMade, mPointMade;
	/** 男性头像显示UI 控件 */
	private ImageView mViewMen, mPointMen;
	/** 性别选择计数器 */
	private int mGrendCount = -1;

	private TextView mWelcomeHere;
	private Button mFinishRegister;

	private TextView text;
	private ImageView icon;
	private int isAgreement = 0;

	/** 热线电话 */
	private TextView tvHotLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_finish_register);
		if (null != this.getIntent().getBundleExtra("bundle").getString("phone") && !"".equals(this.getIntent().getBundleExtra("bundle").getString("phone"))) {
			initControlUI();
		} else {
			finish();
			Toast.makeText(this, "数据丢失,请重试", Toast.LENGTH_SHORT).show();
		}
	}

	private void initControlUI() {
		mFinishBack = (TextView) this.findViewById(R.id.m_finish_registback);
		mFinishBack.setOnClickListener(this);
		mViewMade = (ImageView) this.findViewById(R.id.m_icon_made);
		mViewMade.setOnClickListener(this);
		mPointMade = (ImageView) this.findViewById(R.id.m_point_made);
		mPointMade.setOnClickListener(this);
		mTextMade = (TextView) this.findViewById(R.id.m_text_made);
		mTextMade.setOnClickListener(this);
		tvHotLine = (TextView) findViewById(R.id.hotLine);
		tvHotLine.setOnClickListener(this);
		tvHotLine.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mViewMen = (ImageView) this.findViewById(R.id.m_icon_man);
		mViewMen.setOnClickListener(this);
		mPointMen = (ImageView) this.findViewById(R.id.m_point_men);
		mPointMen.setOnClickListener(this);
		mTextMen = (TextView) this.findViewById(R.id.m_text_men);
		mTextMen.setOnClickListener(this);
		mWelcomeHere = (TextView) this.findViewById(R.id.m_layoutz);
		mWelcomeHere = (TextView) this.findViewById(R.id.m_layoutz);
		mWelcomeHere.setText("欢迎您（" + this.getIntent().getBundleExtra("bundle").getString("phone") + "）来到社区");
		mFinishRegister = (Button) this.findViewById(R.id.m_finish_register);
		mFinishRegister.setOnClickListener(this);
		text = (TextView) findViewById(R.id.m_agreement);
		text.setTextColor(Color.BLUE);
		text.setText(Html.fromHtml("<u>" + getResources().getString(R.string.egame_accept_agreement) + "</u>"));
		// 是否同意註冊協議圖標按鈕
		icon = (ImageView) findViewById(R.id.m_icon);
		// 同意注册协议事件处理
		text.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Uri uri = Uri.parse(Urls.REGISTER_AGREEMENT_URL);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}

		});
		icon.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isAgreement % 2 == 0) {
					icon.setBackgroundResource(R.drawable.egame_select_contactsoff);
					text.setTextColor(Color.BLUE);
				} else {
					icon.setBackgroundResource(R.drawable.egame_lselect_contactson);
					text.setTextColor(Color.BLUE);
				}
				isAgreement++;
			}

		});
	}

	/**
	 * 相关UI控件的单击事件处理函数
	 */
	@Override
	public void onClick(View v) {
		if (v == mFinishBack) {
			FinishRegisterActivity.this.finish();
		} else if (v == mViewMade || v == mPointMade || v == mTextMade) {
			mGrendCount = 2;
			// 改变用户性别的选择
			mViewMade.setBackgroundResource(R.drawable.egame_headicon_select);
			mPointMade.setBackgroundResource(R.drawable.egame_radiobutton_select);
			mViewMen.setBackgroundResource(R.drawable.egame_headicon_unselect);
			mPointMen.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
		} else if (v == mViewMen || v == mPointMen || v == mTextMen) {
			mGrendCount = 1;
			mViewMade.setBackgroundResource(R.drawable.egame_headicon_unselect);
			mPointMade.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
			mViewMen.setBackgroundResource(R.drawable.egame_headicon_select);
			mPointMen.setBackgroundResource(R.drawable.egame_radiobutton_select);
		} else if (v == mFinishRegister) {
			if (isAgreement % 2 == 0) {
				// 同意协议
				if (-1 == mGrendCount) {
					Toast.makeText(FinishRegisterActivity.this, "请选择您的性别哦", Toast.LENGTH_SHORT).show();
				} else {
					// 提交注册数据
					new UserRegisterAsyncTask(FinishRegisterActivity.this, this.getIntent().getBundleExtra("bundle").getString("phone"), mGrendCount).execute("");
				}
			} else {
				Toast.makeText(FinishRegisterActivity.this, "请先阅读并同意注册协议哦", Toast.LENGTH_SHORT).show();
			}

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
			AlertDialog.Builder builder = ds.getBuilder(FinishRegisterActivity.this,
					"确定", "取消", comfirmL, null);
			builder.setMessage("拨打技术支持热线？");
			builder.create().show();
		}
	}

	public static Bundle getFinishRegisterBunder(String phone) {
		Bundle bundle = new Bundle();
		bundle.putString("phone", phone);
		return bundle;
	}

	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("FinishRegisterActivity");
	}

}
