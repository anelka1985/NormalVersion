package com.egame.app.uis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.CityProvinceTask;
import com.egame.app.tasks.GetUserInfoTask;
import com.egame.app.tasks.NoviceCommitModifyTask;
import com.egame.app.tasks.NoviceHideSetTask;
import com.egame.beans.ProvinceBean;
import com.egame.beans.UserBean;
import com.egame.config.Const;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.LoginDataStoreUtil.User;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.ToastUtil;
import com.egame.utils.ui.UIUtils;
import com.egame.utils.ui.Utils;
import com.eshore.network.stat.NetStat;

/**
 * 描述：填写个人基本信息界面类
 * 
 * @author LiuHan
 * @version 1.0 create on:Mon 6 February,2012
 * 
 */
public class NovicePrimaryActivity extends Activity implements View.OnClickListener {
	private TextView mNoviceName;
	public static NovicePrimaryActivity instance;
	/** 触发返回事件的UI控件 */
	private TextView mBack;
	/** 触发找朋友操作的UI控件 */
	private RelativeLayout mFindFriend;
	/** 用户昵称 */
	private TextView mNickName;
	/** 生日 */
	private TextView mBirthday;
	/** 爱好 游戏类型 */
	private TextView mTasteGender;
	/** 城市 */
	private TextView mProvince;
	ListView listViews, listView;
	/** 省份 */
	private List<ProvinceBean> mProvinceList;
	/** 省份对应的省会 */
	private static Map<String, String> mCapital = Utils.addCapital();
	private String userBirthday = "1980-01-01";
	private ProvinceBean selectedBean;
	private String myCity = "", myProvince = "", grend = "", gameTypes = "";
	public int position;
	public String userId = "";
	int showBirthday = 91, showAddress = 101;
	// 昵称匹配2-8个汉字
	Pattern nickName = Pattern.compile("^[\u4e00-\u9fa5]{2,8}");
	/** 星座 */
	private String mConstellation = "";

	private TextView mBirthdayShow, mBirthdayHide, mAddressShow, mAddressHide;
	TextView text;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_novice_primary);
		instance = this;
		initControlUI();
		// 取得用户的信息
		new GetUserInfoTask(this, LoginDataStoreUtil.fetchUser(NovicePrimaryActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId()).execute("");
	}

	/**
	 * 填充用户信息
	 */
	public void addDataControlUI(UserBean userBean) {
		mNoviceName.setText(userBean.getUserName() + "，欢迎来到爱游戏社区");
		myProvince = userBean.getProvince();
		myCity = userBean.getCity();
		this.mNickName.setText(userBean.getUserName().length() > 1 ? userBean.getUserName() : mNickName.getText());
		String[] str = userBean.getBirthday().split("-");
		userBirthday = userBean.getBirthday();
		if (null != str && str.length >= 3) {
			this.mBirthday.setText(userBean.getBirthday() + "  " + Utils.getConstellation(Integer.parseInt(str[1]), Integer.parseInt(str[2])));
		}
		String addstr = userBean.getProvince() == null ? "" : userBean.getProvince() + " " + userBean.getCity() == null ? "" : userBean.getCity();
		mProvince.setText(addstr.length() > 1 ? addstr : mProvince.getText());
		String[] str1 = userBean.getHobby().split(",");
		if (str1.length >= 4) {
			grend = str1[1];
			gameTypes = str1[3];
			this.mTasteGender.setText("想和 " + str1[1] + " 一起玩 " + str1[3]);
		}
	}

	/**
	 * 修改成功后刷新界面显示
	 */
	public void refreshUI(String type, String name) {
		if ("birthday".equals(type)) {
			mBirthday.setText(userBirthday + "" + "    " + this.mConstellation);
		} else if ("name".equals(type)) {
			mNickName.setText(name);
			mNoviceName.setText(name + "，欢迎来到爱游戏社区");
			User user = LoginDataStoreUtil.fetchUser(this, LoginDataStoreUtil.LOG_FILE_NAME);
			user.setNickName(name);
			LoginDataStoreUtil.storeUser(this, user, LoginDataStoreUtil.LOG_FILE_NAME);

		} else if ("address".equals(type)) {
			mProvince.setText(myProvince + " " + myCity);
		}
	}

	/**
	 * 相关UI控件的引用
	 */
	public void initControlUI() {
		userId = LoginDataStoreUtil.fetchUser(NovicePrimaryActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId();
		mFindFriend = (RelativeLayout) this.findViewById(R.id.m_novice_findfriend);
		mFindFriend.setOnClickListener(this);
		// 返回按钮
		mBack = (TextView) this.findViewById(R.id.m_back);
		mBack.setOnClickListener(this);
		// 昵称
		mNickName = (TextView) this.findViewById(R.id.m_nick_name);
		mNickName.setOnClickListener(this);
		// 生日
		mBirthday = (TextView) this.findViewById(R.id.m_novice_birthday);
		mBirthday.setOnClickListener(this);
		// 爱好，游戏类型
		mTasteGender = (TextView) this.findViewById(R.id.m_taste_gender);
		mTasteGender.setOnClickListener(this);
		mProvince = (TextView) this.findViewById(R.id.m_novice_province);
		mProvince.setOnClickListener(this);
		mNoviceName = (TextView) this.findViewById(R.id.m_novice_name);
		mBirthdayShow = (TextView) this.findViewById(R.id.m_birthday_show);
		mBirthdayShow.setOnClickListener(this);
		mBirthdayHide = (TextView) this.findViewById(R.id.m_birthday_hide);
		mBirthdayHide.setOnClickListener(this);
		mBirthdayHide.setTextColor(Color.GRAY);
		mAddressShow = (TextView) this.findViewById(R.id.m_address_show);
		mAddressShow.setOnClickListener(this);
		mAddressHide = (TextView) this.findViewById(R.id.m_address_hide);
		mAddressHide.setOnClickListener(this);
		mAddressHide.setTextColor(Color.GRAY);
		String str = this.getIntent().getStringExtra("flag");
		if (null != str) {
			Const.isWebStart = str;
		}
	}

	/**
	 * 相关按钮的单击事件处理函数
	 */

	public void onClick(View v) {
		if (v == mBack) {
			DialogUtil.showNoviceCancelDialog(this);
		} else if (v == mFindFriend) {
			String str = checkPromptResult();
			// 提交修改
			if ("finish".equals(str)) {
				new NoviceHideSetTask(NovicePrimaryActivity.this, LoginDataStoreUtil.fetchUser(NovicePrimaryActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
						showBirthday, showAddress).execute("");
			} else {
				ToastUtil.show(NovicePrimaryActivity.this, str);
			}

		} else if (v == this.mNickName) { // 修改昵称
			DialogUtil.showModifyNameDialog(NovicePrimaryActivity.this, mNickName.getText().toString());

		} else if (v == mBirthday) {
			// 修改生日
			modifyBirthday();
		} else if (v == mTasteGender) {
			// 爱好 游戏类型 启动性别选择界面
			Intent intent = new Intent(NovicePrimaryActivity.this, NoviceGrendActivity.class);
			String[] str = this.mTasteGender.getText().toString().trim().split(" ");
			Bundle bundle = NoviceGrendActivity.getBundle(grend, gameTypes, userId);
			if (str.length >= 4) {
				bundle = NoviceGrendActivity.getBundle(str[1], str[3], userId);
			}
			intent.putExtra("bundle", bundle);
			startActivityForResult(intent, 0);
		} else if (v == mProvince) {
			// 修改省份
			new CityProvinceTask(NovicePrimaryActivity.this).execute("");
		} else if (v == mBirthdayShow) {
			changeViewShow(mBirthdayShow, mBirthdayHide, "left");
			showBirthday = 91;
		} else if (v == mBirthdayHide) {
			changeViewShow(mBirthdayShow, mBirthdayHide, "right");
			showBirthday = 92;
		} else if (v == mAddressShow) {
			changeViewShow(mAddressShow, mAddressHide, "left");
			showAddress = 101;
		} else if (v == mAddressHide) {
			changeViewShow(mAddressShow, mAddressHide, "right");
			showAddress = 102;
		}

	}

	public String checkPromptResult() {
		if ("".equals(mNickName.getText().toString())) {
			return "请输入昵称";
		} else if ("".equals(mBirthday.getText().toString())) {
			return "请选择您的生日";
		} else if ("点击选择城市".equals(mProvince.getText().toString())) {
			return "请选择您的所在省、市";
		} else if ("点击选择您喜欢的游戏类型".equals(this.mTasteGender.getText().toString())) {
			return "请选择您喜欢的游戏类型";
		} else {
			return "finish";
		}
	}

	public void changeViewShow(TextView view1, TextView view2, String type) {
		if ("left".equals(type)) {
			view1.setTextColor(Color.WHITE);
			view1.setBackgroundResource(R.drawable.egame_lvse);
			view2.setTextColor(Color.GRAY);
			view2.setBackgroundResource(R.drawable.egame_huiseright);
		} else {
			view2.setTextColor(Color.WHITE);
			view2.setBackgroundResource(R.drawable.egame_lvseright);
			view1.setTextColor(Color.GRAY);
			view1.setBackgroundResource(R.drawable.egame_huiseleft);
		}
	}

	/**
	 * 跳到找朋友界面
	 */
	public void enterFindFriend() {
		// 修改成功 跳到找朋友界面
		Intent intent = new Intent(this, NoviceFindFriendActivity.class);
		Bundle bundle = NoviceFindFriendActivity.createFindFriendBundle("男生".equals(mTasteGender.getText().toString()) ? 1 : 2, Utils.getAge(userBirthday), myCity, myProvince,
				userId);
		intent.putExtra("bundle", bundle);
		startActivity(intent);
		this.finish();
	}

	public static Bundle createBundle(String grend, String type, int interestId) {
		Bundle bundle = new Bundle();
		bundle.putString("grend", grend);
		bundle.putString("type", type);
		bundle.putInt("position", interestId);
		return bundle;
	}

	/**
	 * 取得身份数据列表
	 * 
	 * @param list
	 */
	public void showProvinceCityData(List<ProvinceBean> list) {
		this.mProvinceList = list;
		LayoutInflater factory = LayoutInflater.from(NovicePrimaryActivity.this);
		final RelativeLayout views = (RelativeLayout) factory.inflate(R.layout.egame_novice_city_dialog, null);
		listView = (ListView) views.findViewById(R.id.m_province_listview);
		listViews = (ListView) views.findViewById(R.id.m_city_listview);
		text = (TextView) views.findViewById(R.id.m_show);
		String[] address = mProvince.getText().toString().split(" ");
		passValue(address);
		if (!"".equals(myCity) || !"".equals(myProvince)) {
			text.setVisibility(View.VISIBLE);
		}
		SimpleAdapter adapter = new SimpleAdapter(NovicePrimaryActivity.this, getProvince(), R.layout.egame_novice_province_item, new String[] { "province" },
				new int[] { R.id.m_province });
		listView.setAdapter(adapter);
		Button mSure = (Button) views.findViewById(R.id.m_sure);

		final Button mCancel = (Button) views.findViewById(R.id.m_cancel);

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				mCancel.setText("返回");
				text.setVisibility(View.VISIBLE);
				selectedBean = mProvinceList.get(position);
				final String str = getProvince().get(position).get("province").toString();
				listView.setVisibility(View.GONE);
				SimpleAdapter adapters = new SimpleAdapter(NovicePrimaryActivity.this, getCity(selectedBean), R.layout.egame_novice_province_item, new String[] { "province" },
						new int[] { R.id.m_province });
				listViews.setAdapter(adapters);
				myProvince = getProvince().get(position).get("province").toString();
				myCity = mCapital.get(getProvince().get(position).get("province").toString());
				UIUtils.changeTextShow(text, myProvince, myCity);
				text.setGravity(Gravity.LEFT);
				listViews.setVisibility(View.VISIBLE);
				listViews.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						myCity = getCity(selectedBean).get(arg2).get("province");
						myProvince = str;
						UIUtils.changeTextShow(text, myProvince, myCity);
					}

				});

			}

		});

		final Dialog dialog = new Dialog(NovicePrimaryActivity.this);
		dialog.setContentView(views);
		dialog.setTitle("选择我的住址");
		dialog.show();
		mSure.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				dialog.dismiss();
				if (!"".equals(myProvince) && !"".equals(myCity)) {
					// 修改城市
					new NoviceCommitModifyTask(NovicePrimaryActivity.this, LoginDataStoreUtil.fetchUser(NovicePrimaryActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId(),
							0, 0, "", "", myProvince, myCity, "", 9, "address").execute("");
				} else {
					Toast.makeText(NovicePrimaryActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
				}
			}

		});
		mCancel.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				if ("返回".equals(mCancel.getText().toString())) {
					listView.setVisibility(View.VISIBLE);
					listViews.setVisibility(View.GONE);
					mCancel.setText("取消");
				} else {
					dialog.dismiss();
				}
			}

		});
	}

	void passValue(String[] address) {
		switch (address.length) {
		case 1:
			myProvince = address[0];
			break;
		case 2:
			myCity = address[1];
			myProvince = address[0];
			break;
		default:
			myCity = "";
			myProvince = "";

		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			mTasteGender.setText("想和 " + data.getBundleExtra("bundle").getString("grend") + " 一起玩 " + data.getBundleExtra("bundle").getString("type"));

			position = data.getBundleExtra("bundle").getInt("positon");
		}

	}

	/**
	 * 取得用户所在省份的相关信，用于启动地址选择对话框直接定位到城市的选择
	 * 
	 * @param provinceName
	 * @return
	 */
	public ProvinceBean getUserProvince(String provinceName) {
		ProvinceBean pBean = null;
		for (int i = 0; i < mProvinceList.size(); i++) {
			if (mProvinceList.get(i).getProvinceName().contains("provinceName")) {
				pBean = mProvinceList.get(i);
			}
		}
		return pBean;
	}

	private List<HashMap<String, String>> getProvince() {
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> title = null;
		for (int k = 0; k < this.mProvinceList.size(); k++) {
			title = new HashMap<String, String>();
			title.put("province", this.mProvinceList.get(k).getProvinceName());
			data.add(title);
		}
		return data;
	}

	private List<HashMap<String, String>> getCity(ProvinceBean pBeans) {
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> title = null;
		for (int k = 0; k < pBeans.getList().size(); k++) {
			title = new HashMap<String, String>();
			title.put("province", pBeans.getList().get(k).getCityName());
			Log.i(pBeans.getProvinceName() + "//" + pBeans.getProvinceId(), pBeans.getList().get(k).getCityId());
			data.add(title);
		}
		return data;
	}

	/**
	 * 修改生日
	 */
	public void modifyBirthday() {
		// 用来获取日期和时间的
		Dialog dialog = null;
		DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
				if (!"".equals(year)) {
					if (1950 <= year && 2006 >= year) {
						userBirthday = year + "-" + (month + 1) + "-" + dayOfMonth;
						mConstellation = Utils.getConstellation(month + 1, dayOfMonth);
						new NoviceCommitModifyTask(NovicePrimaryActivity.this, userId, 1, 2, userBirthday, "4", "5", "6", "7", 8, "birthday").execute("");
					} else {
						Toast.makeText(NovicePrimaryActivity.this, NovicePrimaryActivity.this.getResources().getString(R.string.egame_birthday_error1), Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(NovicePrimaryActivity.this, NovicePrimaryActivity.this.getResources().getString(R.string.egame_birthday_error), Toast.LENGTH_SHORT).show();
				}

			}
		};

		String[] dates = userBirthday.split("-");
		if (null != dates && dates.length >= 3) {
			dialog = new DatePickerDialog(this, dateListener, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
		} else {
			dialog = new DatePickerDialog(this, dateListener, 1980, 00, 01);
		}
		dialog.show();
	}

	public void updateNickName(String str) {
		mNoviceName.setText(str + "，欢迎来到爱游戏社区");
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_MENU)) {
			DialogUtil.showNoviceCancelDialog(this);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("NovicePrimaryActivity");
	}

}
