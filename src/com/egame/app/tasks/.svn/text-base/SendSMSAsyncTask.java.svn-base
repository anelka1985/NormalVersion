package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.egame.R;
import com.egame.beans.ContactsBean;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.ToastUtil;

/**
 * 描述:发送短消息的异步任务
 * 
 * @author LiuHan
 * @version 1.0 create on:2012/1/5
 */
public class SendSMSAsyncTask extends AsyncTask<String, Integer, String> {
	private static final String LOG_TAG = "SenSMSAsyncTask";

	private boolean mException = false;

	/** 要发送短信的联系人数据集合 */
	private List<ContactsBean> mCBean;

	/** 发送状态提示UI */
	private ProgressDialog mPDialog;

	/** 上下文 */
	private Context context;

	private TelephonyManager mTelephonyManager;
	private String  gameId,gameName;

	public SendSMSAsyncTask(Context context, List<ContactsBean> mContactsBean,
			String gameId,String gameName) {
		this.mCBean = mContactsBean;
		this.context = context;
		this.mCBean = mContactsBean;
		this.gameId = gameId;
		this.gameName = gameName;
		mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		mPDialog = new ProgressDialog(context);
		mPDialog.setMessage("分享发送中，请稍后. . . . . ");
		mPDialog.show();
	}

	public SendSMSAsyncTask(Context context, List<ContactsBean> mContactsBean) {
		this.mCBean = mContactsBean;
		this.context = context;
		this.mCBean = mContactsBean;
		mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		mPDialog = new ProgressDialog(context);
		mPDialog.setMessage("分享发送中，请稍后. . . . . ");
		mPDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		// 读取运营商的名称
		Log.i(LOG_TAG, getOperatorName());
		if ("unknown".equals(getOperatorName())) {
			return "无法读取运营商的信息，请确保手机已正常入网，然后重试。";
		}
		String mStr = readSimState();
		Log.i(LOG_TAG, mStr);
		// 判断SIM卡的状态
		if ("Fine".equals(mStr)) {
			// 如歌手机不欠费 可以正常发送短信
			SmsManager sms = SmsManager.getDefault();
			for (int i = 0; i < this.mCBean.size(); i++) {
				try {
					ContactsBean mBean = this.mCBean.get(i);
					String num = mBean.getmContactsPhone();
					String content;
					if ("game".equals(PreferenceUtil.fetchType(context))) {
						content = getPhoneShareWord(this.gameId,this.gameName);
					} else {
						content = getClientShareWord();
					}

					if (content.length() > 70) {
						// 使用短信管理器进行短信内容的分段，返回分成的段
						ArrayList<String> contents = sms.divideMessage(content);
						for (String msg : contents) {
							// 使用短信管理器发送短信内容
							// 参数一为短信接收者
							// 参数三为短信内容
							// 其他可以设为null
							sms.sendTextMessage(num, null, msg, null, null);
						}
					} else {
						// 否则一次过发送
						sms.sendTextMessage(num, null, content, null, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					mException = true;
				}
			}
			return "分享消息发送成功!";
		} else {
			return mStr;
		}
	}

	private String getClientShareWord() {
		String imsi = null;
		try {
			TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = telManager.getSubscriberId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.context.getResources().getString(
				R.string.egame_share_content)+imsi;
	}

	private String getPhoneShareWord(String gameId,String gameName) {
		return "我正在玩《" + gameName
				+ "》，很给力啊！http://wapgame.189.cn/c/game/details/"
				+ gameId ;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mPDialog.dismiss();
		if (mException) {
			ToastUtil.show(this.context, "分享消息发送失败！");
		} else {
			ToastUtil.show(this.context, result);
		}
	}

	/**
	 * 功能：取得当前手机运营商的名称
	 * 
	 * @return string 运营商的名称
	 */
	public String getOperatorName() {
		// 取得运营商的信息
		String operatorInfo = mTelephonyManager.getSimOperator();
		String operatorName = "";
		Log.i(LOG_TAG, operatorInfo);
		if ("46000".equals(operatorInfo) || "46002".equals(operatorInfo)
				|| "46007".equals(operatorInfo)) {
			// 中国移动
			operatorName = "中国移动";
		} else if ("46001".equals(operatorInfo)) {
			// 中国联通
			operatorName = "中国联通";
		} else if ("46003".equals(operatorInfo)) {
			// 中国电信
			operatorName = "中国电信";
		} else {
			operatorName = "unknown";
		}
		return operatorName;
	}

	/**
	 * 功能:读取Sim卡的状态
	 * 
	 * @return Sim卡的状态
	 */
	public String readSimState() {
		String mString = "";
		int simState = mTelephonyManager.getSimState();
		switch (simState) {
		case TelephonyManager.SIM_STATE_ABSENT:
			mString = "未找到SIM卡，请确认插入了SIM卡，然后重试。";
			break;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			mString = "需要NetworkPIN解锁";
			break;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			mString = "需要PIN解锁";
			break;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			mString = "需要PUN解锁";
			break;
		case TelephonyManager.SIM_STATE_READY:
			mString = "Fine";
			break;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			mString = "未能正确读取SIM卡的信息，请确认SIM卡是否正确放入卡槽内。";
			break;
		}
		return mString;
	}

}
