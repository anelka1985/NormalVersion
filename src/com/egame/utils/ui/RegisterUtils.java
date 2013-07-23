package com.egame.utils.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.BaseInfoRegisterActivity;
import com.egame.app.uis.UserLoginActivity;
import com.egame.config.Const;
import com.egame.config.Urls;

/**
 * 描述：用于注册的工具类
 * 
 * @author LiuHan
 * @version 1.0 create on:Tue 28 Feb,2012
 */
public class RegisterUtils {
	private Activity ehActivity;
	private Dialog registerDialog;
	private int isAgreement = 0;

	public RegisterUtils(Activity ehActivity) {
		this.ehActivity = ehActivity;
	}

	/**
	 * 游客客的注册提示对话框
	 */
	public void showPromptRegister() {
		RelativeLayout rl = (RelativeLayout) LayoutInflater.from(ehActivity).inflate(R.layout.egame_register_prompt, null);
		registerDialog = new Dialog(ehActivity, R.style.egame_dialog);
		registerDialog.setContentView(rl);
		registerDialog.show();
		// 快速注册按钮
		final Button fastRegister = (Button) rl.findViewById(R.id.m_accept);
		// 直接登录按钮
		Button loginNow = (Button) rl.findViewById(R.id.m_login_now);
		// 同意注册协议
		final TextView text = (TextView) rl.findViewById(R.id.m_agreement);
		text.setTextColor(Color.BLUE);
		text.setText(Html.fromHtml("<u>" + ehActivity.getResources().getString(R.string.egame_accept_agreement) + "</u>"));
		// 是否同意註冊協議圖標按鈕
		final ImageView mIcon = (ImageView) rl.findViewById(R.id.m_icon);
		// 同意注册协议事件处理
		text.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Uri uri = Uri.parse(Urls.REGISTER_AGREEMENT_URL);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				ehActivity.startActivity(intent);
			}

		});
		// 同意注册协议图片事件处理
		mIcon.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isAgreement % 2 == 0) {
					mIcon.setBackgroundResource(R.drawable.egame_select_contactsoff);
					fastRegister.setEnabled(true);
					text.setTextColor(Color.BLUE);
				} else {
					mIcon.setBackgroundResource(R.drawable.egame_lselect_contactson);
					fastRegister.setEnabled(true);
					text.setTextColor(Color.BLUE);
				}
				isAgreement++;

			}

		});
		// 直接登录
		loginNow.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				registerDialog.dismiss();
				isAgreement = 0;
				Intent communityIntent = new Intent();
				communityIntent.setClass(ehActivity, UserLoginActivity.class);
				ehActivity.startActivityForResult(communityIntent,EnterCommunity.RCODE_ENTERCOMUNITY_LOGIN);
			}

		});
		// 快速注册
		fastRegister.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				registerDialog.dismiss();
				if (isAgreement % 2 != 0) {
					Toast.makeText(ehActivity, "请先阅读并同意注册协议", Toast.LENGTH_SHORT).show();
				} else {
					isAgreement = 0;
					Intent intentRegister = new Intent();
					intentRegister.setClass(ehActivity, BaseInfoRegisterActivity.class);
					ehActivity.startActivity(intentRegister);
				}
			}
		});
	}
}
