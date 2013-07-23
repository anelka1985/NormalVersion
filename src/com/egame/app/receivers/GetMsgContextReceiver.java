package com.egame.app.receivers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.egame.app.uis.BaseInfoRegisterActivity;
import com.egame.app.uis.GamePackageDetailActivity;
import com.egame.app.uis.UserLoginActivity;

public class GetMsgContextReceiver extends BroadcastReceiver {
	public void onReceive(Context context, Intent intent) {
		try {
			SmsMessage[] msg = null;
			
			Class paramTypes[] = new Class[2];

			paramTypes[0] = byte[].class;

			paramTypes[1] = String.class;

			Method createFromPdu = SmsMessage.class.getDeclaredMethod("createFromPdu", paramTypes);



			
			
			if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					Object[] pdusObj = (Object[]) bundle.get("pdus");
					msg = new SmsMessage[pdusObj.length];
					for (int i = 0; i < pdusObj.length; i++) {
//						msg[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
						msg[i] = (SmsMessage) createFromPdu.invoke(null, (byte[]) pdusObj[i], "3gpp");
					}
				}
				if (null != msg) {
					for (int i = 0; i < msg.length; i++) {
						Pattern phone = Pattern.compile("\\[(\\d+)\\]");
						if (null != msg[i]) {
							String mMsg = msg[i].getMessageBody();
							if (null != mMsg && mMsg.length() > 0) {
								if (mMsg.contains("爱游戏") && mMsg.contains("注册验证码")) {
									List<String> list = new ArrayList<String>();
									Intent mIntent = new Intent(BaseInfoRegisterActivity.MSG_CONTENT_STR);
									Matcher m = phone.matcher(mMsg);
									while (m.find()) {
										list.add(m.group(1));
									}
									if (null != list && list.size() >= 1) {
										mIntent.putExtra("verificater", list.get(0));
										BaseInfoRegisterActivity.instance.sendBroadcast(mIntent);
									}
								}

								if (mMsg.contains("爱游戏") && mMsg.contains("密码")) {
									List<String> list = new ArrayList<String>();
									Intent mIntent = new Intent(UserLoginActivity.BROADCAST_STR);
									Matcher m = phone.matcher(mMsg);
									while (m.find()) {
										list.add(m.group(1));
									}
									if (null != list && list.size() >= 2) {
										mIntent.putExtra("account", list.get(0));
										mIntent.putExtra("password", list.get(1));
										UserLoginActivity.instance.sendBroadcast(mIntent);
									}
								}
								if (mMsg.contains("爱游戏") && mMsg.contains("游戏包") && mMsg.contains("验证码")) {
									List<String> list = new ArrayList<String>();
									Intent mIntent = new Intent(GamePackageDetailActivity.MSG_CONTENT_STR);
									Matcher m = phone.matcher(mMsg);
									while (m.find()) {
										list.add(m.group(1));
									}
									if (null != list && list.size() >= 1) {
										mIntent.putExtra("validateCode", list.get(0));
										GamePackageDetailActivity.INSTANCE.sendBroadcast(mIntent);
									}
								}
							}
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}