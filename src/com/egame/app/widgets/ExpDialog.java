package com.egame.app.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.uis.UserLoginActivity;

/**
 * @desc 经验值 运营中使用的提示登录对话框
 * 
 * @Copyright lenovo
 * 
 * @Project Egame4Web
 * 
 * @Author zhouzhe@lenovo-cw.com
 * 
 * @timer 2012-6-19
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ExpDialog extends Dialog {

	Button btnLeft;
	Button btnRight;
	TextView tvMsg;
	// 1=下载得经验登陆框 2=分享得经验登录框 3=更多,推荐给朋友
	int type = 1;

	public ExpDialog(Context context, int type) {
		super(context, R.style.egame_expDialog);
		this.type = type;
	}

	// public ExpDialog(Context context, int theme) {
	// super(context, theme);
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_expdialog);
		btnLeft = (Button) findViewById(R.id.left);
		btnRight = (Button) findViewById(R.id.right);
		tvMsg = (TextView) findViewById(R.id.msg);

		if (type == 1) {
			btnLeft.setText("立即登录");
			btnRight.setText("取消");
			tvMsg.setText("登陆账号,下载游戏可得经验值啦！");

			btnRight.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});

			btnLeft.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getContext(), UserLoginActivity.class);
					getContext().startActivity(intent);
					dismiss();
				}
			});
		} else if (type == 2) {
			btnLeft.setText("立即登录");
			btnRight.setText("取消");
			tvMsg.setText("登陆账号,分享游戏可得经验值啦！");

			btnRight.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});

			btnLeft.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getContext(), UserLoginActivity.class);
					getContext().startActivity(intent);
					dismiss();
				}
			});
		} else if (type == 3) {
			btnLeft.setText("立即登录");
			btnRight.setText("取消");
			tvMsg.setText("登陆账号,推荐游戏可得经验值啦！");

			btnRight.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});

			btnLeft.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getContext(), UserLoginActivity.class);
					getContext().startActivity(intent);
					dismiss();
				}
			});
		}

	}

}
