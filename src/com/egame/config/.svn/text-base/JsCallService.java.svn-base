package com.egame.config;

import java.io.File;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.tasks.GetRunnable;
import com.egame.app.uis.EgameWebActivity;
import com.egame.app.uis.ImageViewActivity;
import com.egame.app.uis.InputPopupActivity;
import com.egame.app.uis.NovicePrimaryActivity;
import com.egame.app.widgets.CustomerDatePickerDialog;
import com.egame.utils.common.L;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.Utils;

/**
 * 类说明：
 * 
 * @创建时间 2012-2-11 下午12:25:49
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class JsCallService {
	private EgameWebActivity mEgameWebActivity;

	private final static String mErrorPrompt = "您的输入不合法，请重新输入！";

	public JsCallService(EgameWebActivity context) {
		mEgameWebActivity = context;
	}

	/**
	 * 打印到控制台
	 * 
	 * @param s
	 */
	public void println(String s) {
		// System.out.println("JsCallService s=" + s);

		L.d("JsCallService s=" + s);
	}

	/**
	 * 页面开始加载
	 */
	public void pageLoadStart() {
		mEgameWebActivity.handler.sendEmptyMessage(2);
	}

	/**
	 * 页面加载完成
	 */
	public void pageLoadFinish() {
		mEgameWebActivity.handler.sendEmptyMessage(1);
	}

	/**
	 * 页面后退
	 */
	public void goBack() {
		mEgameWebActivity.back();
	}

	/**
	 * Toast提示
	 * 
	 * @param msg
	 */
	public void toast(String msg) {
		mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, msg));
	}

	/**
	 * 弹出选择对话框
	 * 
	 * @param msg
	 * @param callBack
	 */
	public void showChoiceDialog(String msg, final String callBack) {
		DialogStyle ds = new DialogStyle();
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				mEgameWebActivity.mWebView.loadUrl("javascript:" + callBack);
			
			}
		};

		AlertDialog.Builder builder = ds.getBuilder(mEgameWebActivity, "确定", "取消", comfirmL, null);
		builder.setTitle("请选择");
		builder.create().show();
	}

	/**
	 * 打开日期选择对话框
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param callBack
	 */
	public void openDateDialog(String year, String month, String day, final String callBack) {
		int yearInt = 1980;
		int monthInt = 1;
		int dayInt = 1;
		try {
			yearInt = Integer.parseInt(year);
			monthInt = Integer.parseInt(month) - 1;
			dayInt = Integer.parseInt(day);
		} catch (Exception e) {
		}
		new CustomerDatePickerDialog(mEgameWebActivity, new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar c = Calendar.getInstance();
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, monthOfYear);
				c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				if (System.currentTimeMillis() < c.getTimeInMillis()) {
					mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, "请填写您的真实出生日期哦～"));
				} else {
					mEgameWebActivity.mWebView.loadUrl("javascript:" + String.format(callBack, "" + year, "" + (monthOfYear + 1), "" + dayOfMonth));
				}
			}
		}, yearInt, monthInt, dayInt).show();
	}

	/**
	 * 打开性别选择提示框
	 * 
	 * @param gender
	 * @param callBack
	 */
	public void openGenderDialog(final String callBack) {
		new AlertDialog.Builder(mEgameWebActivity).setTitle(R.string.egame_sel_gender)
				.setItems(R.array.egame_sex, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						setGender(item + 1);
						mEgameWebActivity.mWebView.loadUrl("javascript:" + String.format(callBack, "" + (item + 1)));
					}
				}).create().show();
	}

	/**
	 * 打开文本输入框
	 * 
	 * @param text
	 * @param hint
	 * @param patternType
	 *            1-昵称，2-8个汉字或者4-16个字符 2-手机号码 3-邮箱 4-公司名称，20个汉字或者40个字符
	 *            5-签名，60个汉字或者120个字符
	 * @param formatErrorTip
	 * @param callBack
	 */
	public void openTextDialog(final String title, final String text, final String hint, final String patternType, final String formatErrorTip,
			final String callBack) {
		final EditText editText = new EditText(mEgameWebActivity);
		editText.setText(text);
		editText.setHint(hint);
		DialogStyle ds = new DialogStyle();
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String value = editText.getText().toString();
				if (TextUtils.isEmpty(value)) {
					mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, hint));
				} else {
					if (hint.indexOf("昵称") > 0) {
						showResult(Utils.isNickname(value), callBack, value, dialog);
					} else if (hint.indexOf("个性签名") > 0) {
						showResult(RegexChk.checkPlatitude(value), callBack, value, dialog);
					} else if (hint.indexOf("公司") > 0) {
						showResult(RegexChk.checkCompany(value), callBack, value, dialog);
					} else {
						showResult(RegexChk.checkInput(patternType, value), callBack, value, dialog);
					}
				}
							
			}
		};

		AlertDialog.Builder builder = ds.getBuilder(mEgameWebActivity, "确定", "取消", comfirmL, null);
		builder.setTitle(title).setIcon(android.R.drawable.ic_dialog_info).setView(editText);
		builder.create().show();
	}

	/**
	 * @param isTrue
	 * @param callBack
	 * @param value
	 * @param dialog
	 */
	private void showResult(boolean isTrue, final String callBack, final String value, DialogInterface dialog) {
		if (isTrue) {
			mEgameWebActivity.mWebView.loadUrl("javascript:" + String.format(callBack, value));
			dialog.dismiss();
		} else {
			mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, mErrorPrompt));
		}
	}

	/**
	 * http请求header信息
	 * 
	 * @return header信息
	 */
	public String getHeader() {
		// L.d(Urls.getLogParams(mEgameWebActivity));
		return Urls.getLogParams(mEgameWebActivity);
	}

	/**
	 * 打开详情页
	 */
	public void showDetail(String type, String id) {
		System.out.println("type=" + type + ",id=" + id);
		if (type.equals("gameId")) {
			int gameId = 0;
			try {
				gameId = Integer.parseInt(id);
			} catch (Exception e) {
				mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, "打开游戏详情页失败, gameId错误:" + id));
			}
			try {
				Bundle bundle = new Bundle();
				bundle.putInt("gameId", gameId);
				Intent intent = new Intent(mEgameWebActivity, Class.forName("com.egame.app.uis.GamesDetailActivity"));
				intent.putExtras(bundle);
				mEgameWebActivity.startActivity(intent);
			} catch (ClassNotFoundException e) {
				mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(10, "gamedetails.html?gameid=" + gameId + "&USERID="
						+ mEgameWebActivity.getUserId()));
			}
		} else if (type.equals("activeId")) {
			showSysBrowse("wapgame.189.cn/activitys/" + id);
		} else if (type.equals("newsId")) {
			showSysBrowse("wapgame.189.cn/news/" + id);
		} else if (type.equals("friendId")) {
			mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(10, "goodfriend-deta.html?friendId=" + id + "&USERID="
					+ mEgameWebActivity.getUserId()));
		} else if (type.equals("topicId")) {
			int topicId = 0;
			try {
				topicId = Integer.parseInt(id);
			} catch (Exception e) {
				mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, "打开专题详情页失败, topicId错误:" + id));
			}
			try {
				Class.forName("com.egame.app.uis.GamesTopicDetailActivity");
				Bundle bundle = new Bundle();
				bundle.putInt("id", topicId);
				Intent intent = new Intent(mEgameWebActivity, Class.forName("com.egame.app.uis.GamesTopicDetailActivity"));
				intent.putExtras(bundle);
				mEgameWebActivity.startActivity(intent);
			} catch (Exception e) {
				showSysBrowse("wapgame.189.cn/topic/" + id);
			}
		} else if (type.equals("wap")) {
			showSysBrowse(id);
		} else {
			mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(14, "打开详情页失败, 未定义格式:" + type));
		}
	}

	/**
	 * 得到电话簿联系人
	 * 
	 * @return
	 */
	public void getContacts() {
		ContentResolver cr = mEgameWebActivity.getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		Cursor cursor = cr.query(uri, null, null, null, null);
		List<PersonMsg> list = new ArrayList<PersonMsg>();
		while (cursor.moveToNext()) {

			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

			Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
					+ "=" + id, null, null);
			while (phoneCursor.moveToNext()) {
				PersonMsg pm = new PersonMsg();
				pm.name = name;
				String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				pm.phone = phone;
				if (phone.length() == 11) {
					list.add(pm);
				}
			}

			phoneCursor.close();
		}
		cursor.close();
		final RuleBasedCollator collator = (RuleBasedCollator) Collator.getInstance(Locale.CHINA);
		Collections.sort(list, new Comparator<PersonMsg>() {
			public int compare(PersonMsg e1, PersonMsg e2) {
				return collator.compare(e1.name, e2.name);
			}
		});
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			PersonMsg p = list.get(i);
			result.append(p.name);
			result.append(",");
			result.append(p.phone);
			result.append(",");
		}
		if (result.length() > 0)
			result.deleteCharAt(result.length() - 1);
		mEgameWebActivity.mWebView.loadUrl("javascript:setContacts('" + result.toString() + "')");
	}

	class PersonMsg {
		String name = "";
		String phone = "";
		boolean select = false;
	}

	/**
	 * 给指定用户发送短信
	 * 
	 * @param phoneNumbers
	 *            用户号码
	 * @param content
	 *            短信内容
	 */
	public void sendSms(String phoneNumbers, String content) {
		System.out.println("phoneNumbers = " + phoneNumbers);
		if (phoneNumbers != null && phoneNumbers.length() > 0) {
			StringBuffer buf = new StringBuffer();
			String[] numbers = phoneNumbers.split(",");
			for (String number : numbers) {
				if (!TextUtils.isEmpty(number)) {
					String model = android.os.Build.BRAND;
					// if (TextUtils.isEmpty(model)) {
					// buf.append(number + ",");
					// } else if (model.indexOf("HTC") >= 0
					// || model.indexOf("HUAWEI") >= 0
					// || model.indexOf("C8600") >= 0
					// || model.indexOf("C8650") >= 0
					// || model.indexOf("E239") >= 0) {
					// buf.append(number + ";");
					// } else {
					// buf.append(number + ",");
					// }
					if (TextUtils.isEmpty(model)) {
						buf.append(number + ";");
					} else if (model.toUpperCase().indexOf("MOT") >= 0 || model.toUpperCase().indexOf("SAMSUNG") >= 0) {
						buf.append(number + ",");
					} else {
						buf.append(number + ";");
					}
				}
			}
			if (content != null && content.length() > 0) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("smsto:" + buf.toString()));
				intent.putExtra("sms_body", content);
				mEgameWebActivity.startActivity(intent);
			} else {
			}
			// 增加推荐经验值
			String userid = mEgameWebActivity.getUserId();
			new Thread(new GetRunnable(Urls.getInvite(userid, mEgameWebActivity))).start();
		} else {
		}
	}

	/**
	 * 打开系统浏览器
	 * 
	 * @param url
	 */
	public void showSysBrowse(String url) {
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		if (url.indexOf("?") > 0) {
			url += "&" + getHeader();
		} else {
			url += "?" + getHeader();
		}
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW);
		it.setData(uri);
		mEgameWebActivity.startActivity(it);
	}

	/**
	 * 显示图片
	 * 
	 * @param imageUrl
	 */
	public void showImage(String imageUrl) {
		if (TextUtils.isEmpty(imageUrl))
			return;
		Bundle bundle = ImageViewActivity.getBundle(imageUrl);
		Intent intent = new Intent(mEgameWebActivity, ImageViewActivity.class);
		intent.putExtras(bundle);
		mEgameWebActivity.startActivity(intent);
	}

	/**
	 * 弹出输入对话框,js中调用
	 * 
	 * @param param
	 *            true|false,text,textHint,buttonText,callService,callback
	 */
	public void showPopupInput(String param) {
		if (TextUtils.isEmpty(param))
			return;
		String[] params = param.split(",", 5);
		if (params.length == 5) {
			Bundle bundle = InputPopupActivity.getBundle(params);
			Intent intent = new Intent(mEgameWebActivity, InputPopupActivity.class);
			intent.putExtras(bundle);
			mEgameWebActivity.startActivityForResult(intent, EgameWebActivity.INPUT_POPUP_REQUEST_CODE);
		}
	}

	/**
	 * 打开新手教学
	 */
	public void openNewTask() {
		Intent intent = new Intent(mEgameWebActivity, NovicePrimaryActivity.class);
		intent.putExtra("flag", "web");
		mEgameWebActivity.startActivity(intent);
	}

	/**
	 * 弹出图片来源选择对话框，js中调用
	 */
	public void showPicDialog() {
		new AlertDialog.Builder(mEgameWebActivity).setItems(R.array.egame_get_pic, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					// 照相机
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(Environment.getExternalStorageDirectory(), "camera.png");
					Uri uri = Uri.fromFile(out);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					mEgameWebActivity.startActivityForResult(intent, EgameWebActivity.IMAGE_CAPTURE_REQUEST_CODE);
				} else {
					// 图片库
					// Intent intent = new Intent(
					// Intent.ACTION_GET_CONTENT);
					// intent.addCategory(Intent.CATEGORY_OPENABLE);
					// intent.setType("image/*");
					// intent.putExtra("crop", "true");
					// intent.putExtra("aspectX", 1);
					// intent.putExtra("aspectY", 1);
					// intent.putExtra("outputX", 250);
					// intent.putExtra("outputY", 250);
					// intent.putExtra("return-data", true);
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/*");
					mEgameWebActivity.startActivityForResult(intent, EgameWebActivity.CATEGORY_OPENABLE_REQUEST_CODE);
				}
			}
		}).create().show();
	}

	/**
	 * 显示new消息标签
	 */
	public void showNewMessage() {
		mEgameWebActivity.handler.sendEmptyMessage(11);
	}

	/**
	 * 得到推荐游戏
	 * 
	 * @param page
	 */
	public void getHotGames() {
		String game = getRecoListJson();
		System.out.println("getHotGames game=" + game);
		mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(12, "setHotGames('" + getRecoListJson() + "')"));
	}

	/**
	 * 弹出对话框
	 * 
	 * @param content
	 */
	public void showDialog(String content) {
		new AlertDialog.Builder(mEgameWebActivity).setMessage(content).create().show();
	}

	public void setNickname(String nickname) {
		mEgameWebActivity.user.setNickName(nickname);
		LoginDataStoreUtil.storeUser(mEgameWebActivity, mEgameWebActivity.user, LoginDataStoreUtil.LOG_FILE_NAME);
		mEgameWebActivity.handler.sendMessage(mEgameWebActivity.handler.obtainMessage(15));
	}

	private void setGender(int gender) {
		mEgameWebActivity.user.setGender(gender);
		LoginDataStoreUtil.storeUser(mEgameWebActivity, mEgameWebActivity.user, LoginDataStoreUtil.LOG_FILE_NAME);
	}

	private String getRecoListJson() {
		EgameApplication application = (EgameApplication) mEgameWebActivity.getApplication();
		return application.getRecoListJson();
	}
}
