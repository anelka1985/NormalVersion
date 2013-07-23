package com.egame.app.uis;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.MyReplyDetailAdapter;
import com.egame.app.adapters.ReplyListAdapter;
import com.egame.app.tasks.GetReplyListTask;
import com.egame.app.tasks.ReplyDetailTask;
import com.egame.app.tasks.UserReplyPostTask;
import com.egame.beans.ReplyMessageByContentID;
import com.egame.beans.ReplyMessageByPhone;
import com.egame.config.Const;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.MD5;
import com.egame.utils.sys.DialogStyle;
import com.egame.utils.ui.ProgressDialogInterface;
import com.egame.utils.ui.StringUtil;
import com.eshore.network.stat.NetStat;

/**
 * 
 * 类说明：我的反馈
 * 
 * @创建时间 2011-12-27
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class MyReplyActivity extends Activity implements OnScrollListener,
		ProgressDialogInterface {
	/** 返回按钮 */
	private ImageView ivBack;

	/** 意见反馈选项卡 */
	private TextView tvYjfk;

	/** 我的反馈选项卡 */
	private TextView tvWdfk;

	/** 意见反馈布局 */
	private ScrollView svYjfktab;

	/** 反馈问题输入框 */
	private EditText etNote;

	/** 联系方式输入框 */
	private EditText etContact;

	/** 提交反馈 */
	private LinearLayout llSubmission;

	/** 我的反馈布局 */
	private RelativeLayout rlWdfktab;

	/** 我的反馈问题列表 */
	private ListView lvMessageList;

	/** 我要反馈问题布局 */
	private LinearLayout llReplyQuestion;

	/** 反馈详情布局 */
	private RelativeLayout rlReplyDetail;

	/** 单个主题反馈详情 */
	private ListView lvMyReplyDetail;

	/** 继续反馈按钮 */
	private Button btnContinueReply;

	/** 重新提问按钮 */
	private Button btnNewReply;

	/** 用户反馈的问题信息 */
	private String user_reply_message;

	/** 终端型号+操作系统版本信息+UA（用“|”分隔） */
	private String client_info;

	/** 版本号 */
	private String app_version;

	/** IMEI编码 */
	private String client_imei;

	/** 用户id */
	private String client_uid;

	/** 主题ID */
	private String contentID;

	private ProgressDialog mProgressDialog;

	/** 加载布局 */
	private View loadMoreView;

	/** 加载按钮 */
	private Button loadMoreButton;

	private Handler handler = new Handler();

	/** 反馈信息集合 */
	private List<ReplyMessageByPhone> replyList = new ArrayList<ReplyMessageByPhone>();

	/** 自定义Adapter */
	private ReplyListAdapter adapter;

	/** 最后的可视项索引 */
	@SuppressWarnings("unused")
	private int visibleLastIndex = 0;

	/** 当前窗口可见项总数 */
	@SuppressWarnings("unused")
	private int visibleItemCount;

	private MyReplyDetailAdapter myReplyDetailAdapter;

	/** 我的反馈详情集合 */
	private List<ReplyMessageByContentID> myReplyDetailList = new ArrayList<ReplyMessageByContentID>();

	private int flag;

	private boolean isAdd = false;
	/** InputMethodManager变量的声明 */
	public InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_myreply);
		initDate();
		initView();
		initEvent();
		EgameApplication.Instance().addActivity(this);
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
		NetStat.onPausePage("MyReplyActivity");
	}

	/** 初始化数据 */
	void initDate() {
		String clientInfo = android.os.Build.MODEL + "|"
				+ android.os.Build.VERSION.RELEASE + "|"
				+ android.os.Build.PRODUCT;
		try {
			client_info = URLEncoder.encode(clientInfo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		client_uid = LoginDataStoreUtil.fetchUser(MyReplyActivity.this,
				LoginDataStoreUtil.LOG_FILE_NAME).getUserId();
		app_version = getAppVersionName(MyReplyActivity.this);

		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		client_imei = mTelephonyMgr.getDeviceId();

		// 设置软盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	/**
	 * 初始化ui，主要是实现findViewById的操作
	 */
	void initView() {
		ivBack = (ImageView) findViewById(R.id.back);
		tvYjfk = (TextView) findViewById(R.id.yjfk);
		tvWdfk = (TextView) findViewById(R.id.wdfk);
		svYjfktab = (ScrollView) findViewById(R.id.yjfktab);
		etNote = (EditText) findViewById(R.id.note);
		etContact = (EditText) findViewById(R.id.contact);
		llSubmission = (LinearLayout) findViewById(R.id.submission);
		rlWdfktab = (RelativeLayout) findViewById(R.id.wdfktab);
		lvMessageList = (ListView) findViewById(R.id.lv_messageList);
		llReplyQuestion = (LinearLayout) findViewById(R.id.replyQuestion);
		rlReplyDetail = (RelativeLayout) findViewById(R.id.replyDetail);
		lvMyReplyDetail = (ListView) findViewById(R.id.myReplyDetail);
		btnContinueReply = (Button) findViewById(R.id.continueReply);
		btnNewReply = (Button) findViewById(R.id.newReply);

		// 动态加载
		loadMoreView = getLayoutInflater().inflate(R.layout.egame_loadmore,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
	}

	/**
	 * 初始化事件,主要是实现按钮点击等事件的处理
	 */
	void initEvent() {
		ivBack.setOnClickListener(ivBackOnClickListener);
		tvYjfk.setOnClickListener(tvYjfkOnClickListner);
		tvWdfk.setOnClickListener(tvWdfkOnClickListner);
		llSubmission.setOnClickListener(llSubmissionOnClickListener);
		loadMoreButton.setOnClickListener(loadMoreButtonOnClickListener);
		llReplyQuestion.setOnClickListener(llReplyQuestionOnClickListener);
		lvMessageList.setOnScrollListener(this);
		lvMessageList.setOnItemClickListener(messageListOnClickListener);
		btnNewReply.setOnClickListener(llReplyQuestionOnClickListener);
		btnContinueReply.setOnClickListener(btnContinueReplyOnClickListener);
		etNote.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (etNote.getText().length() == 180) {
					Toast.makeText(MyReplyActivity.this, "您输入的内容超过180个字",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		etContact.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (etContact.getText().length() == 40) {
					Toast.makeText(MyReplyActivity.this, "您输入的内容超过40个字",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	/**
	 * 加载更多数据
	 */
	private void loadMoreData() {
		int count = adapter.getCount();

		if (count + 7 <= replyList.size()) {
			for (int i = count; i < count + 7; i++) {
				ReplyMessageByPhone replyMessage = new ReplyMessageByPhone();
				replyMessage.setUser_reply_message(replyList.get(i)
						.getUser_reply_message());
				replyMessage.setReply_content(replyList.get(i)
						.getReply_content());
				adapter.addReplyMessage(replyMessage);
			}
		} else {
			for (int i = count; i <= replyList.size() - 1; i++) {
				ReplyMessageByPhone replyMessage = new ReplyMessageByPhone();
				replyMessage.setUser_reply_message(replyList.get(i)
						.getUser_reply_message());
				replyMessage.setReply_content(replyList.get(i)
						.getReply_content());
				adapter.addReplyMessage(replyMessage);
			}
		}
		adapter.notifyDataSetChanged();
		// lvMessageList.setAdapter(adapter);
	}

	// 我的反馈界面设置
	private void getMyReply() {
		rlWdfktab.setVisibility(View.VISIBLE);
		svYjfktab.setVisibility(View.GONE);
		rlReplyDetail.setVisibility(View.GONE);
		tvYjfk.setBackgroundResource(R.drawable.egame_menuoff);
		tvWdfk.setBackgroundResource(R.drawable.egame_menuon);

		if (replyList.size() >= 7) {
			if (isAdd == false) {
				lvMessageList.addFooterView(loadMoreView);
				isAdd = true;
			}
		}
		// else
		// {
		// lvMessageList.removeFooterView(loadMoreView);
		//
		// }
		initializeAdapter();
		lvMessageList.setAdapter(adapter);

	}

	/**
	 * 初始化ListView的适配器
	 */
	private void initializeAdapter() {
		List<ReplyMessageByPhone> replyMessageLists = new ArrayList<ReplyMessageByPhone>();

		if (replyList.size() < 8) {
			for (int i = 0; i < replyList.size(); i++) {
				ReplyMessageByPhone items = new ReplyMessageByPhone();
				items.setUser_reply_message(replyList.get(i)
						.getUser_reply_message());
				items.setReply_content(replyList.get(i).getReply_content());
				replyMessageLists.add(items);
			}
		} else {

			for (int i = 0; i < 7; i++) {
				ReplyMessageByPhone items = new ReplyMessageByPhone();
				items.setUser_reply_message(replyList.get(i)
						.getUser_reply_message());
				items.setReply_content(replyList.get(i).getReply_content());
				replyMessageLists.add(items);
			}
		}

		if (null != replyMessageLists) {
			adapter = new ReplyListAdapter(replyMessageLists,
					MyReplyActivity.this);
		}
	}

	// 返回按钮事件
	private ImageView.OnClickListener ivBackOnClickListener = new ImageView.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (svYjfktab.isShown()) {
				MyReplyActivity.this.finish();

			} else if (rlReplyDetail.isShown()) {
				svYjfktab.setVisibility(View.GONE);
				rlWdfktab.setVisibility(View.VISIBLE);
				myReplyDetailList = new ArrayList<ReplyMessageByContentID>();
				myReplyDetailAdapter = new MyReplyDetailAdapter(
						myReplyDetailList, MyReplyActivity.this);
				lvMyReplyDetail.setAdapter(myReplyDetailAdapter);
				rlReplyDetail.setVisibility(View.GONE);

			} else {
				MyReplyActivity.this.finish();
			}

		}
	};

	// 显示更多
	private View.OnClickListener loadMoreButtonOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			loadMoreButton.setText("正在加载中"); // 设置按钮文字
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					loadMoreData();
					adapter.notifyDataSetChanged();
					loadMoreButton.setText("显示更多"); // 恢复按钮文字
				}
			}, 1000);

		}
	};

	// 意见反馈选项卡
	private TextView.OnClickListener tvYjfkOnClickListner = new TextView.OnClickListener() {

		@Override
		public void onClick(View v) {
			svYjfktab.setVisibility(View.VISIBLE);
			rlWdfktab.setVisibility(View.GONE);
			rlReplyDetail.setVisibility(View.GONE);
			tvYjfk.setBackgroundResource(R.drawable.egame_menuon);
			tvWdfk.setBackgroundResource(R.drawable.egame_menuoff);
			etNote.setText("");
		}
	};

	// 我的反馈选项卡
	private TextView.OnClickListener tvWdfkOnClickListner = new TextView.OnClickListener() {

		@Override
		public void onClick(View v) {
			// 关闭已开启的虚拟键盘
			imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(tvWdfk.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			getReplyListTask();

		}
	};

	// 我要反馈问题按钮事件
	private LinearLayout.OnClickListener llReplyQuestionOnClickListener = new LinearLayout.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			rlWdfktab.setVisibility(View.GONE);
			svYjfktab.setVisibility(View.VISIBLE);
			rlReplyDetail.setVisibility(View.GONE);
			tvYjfk.setBackgroundResource(R.drawable.egame_menuon);
			tvWdfk.setBackgroundResource(R.drawable.egame_menuoff);
			etNote.setText("");
		}
	};

	// 提交反馈
	private LinearLayout.OnClickListener llSubmissionOnClickListener = new LinearLayout.OnClickListener() {

		@Override
		public void onClick(View v) {
			String replyMessage = etNote.getText().toString();
			String contact = etContact.getText().toString();
			if (StringUtil.isEmpty(replyMessage)) {
				Toast.makeText(MyReplyActivity.this, "反馈内容不能为空！",
						Toast.LENGTH_SHORT).show();
				return;
			}

			try {
				String message = null;
				if ("".equals(contact) || null == contact) {
					message = replyMessage;
				} else {

					message = replyMessage + "[联系方式]:" + contact;
				}

				user_reply_message = URLEncoder.encode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String replyDate = String.valueOf(System.currentTimeMillis())
					.substring(0, 10);

			String time = replyDate;

			String sig = MD5.getMD5Str(time + Const.KEY_FOR_GAME).toLowerCase();

			String url = "http://10000club.189.cn:80/service/userReq.php?"
					+ "app_version=" + app_version + "&client_imei="
					+ client_imei + "&client_imsi=" + "&client_mdn="
					+ "&client_uid=" + client_uid + "&user_reply_message="
					+ user_reply_message + "&reply_date=" + replyDate
					+ "&remark=0" + "&sig=" + sig + "&time=" + time
					+ "&client_info=" + client_info
					+ "&application_id=5&content_id=0";

			// 反馈问题请求
			flag = 0;
			UserReplyPostTask userReplyPostTask = new UserReplyPostTask(
					MyReplyActivity.this);
			userReplyPostTask.execute(url);

		}

	};

	/**
	 * 根据提交反馈问题结果继续流程
	 * 
	 * @param result
	 */
	public void postReply(String result) {
		if ("OK".equals(result)) {
			Toast.makeText(MyReplyActivity.this, "您的反馈提交成功！",
					Toast.LENGTH_SHORT).show();

			if (flag == 0) {
				getReplyListTask();
			} else {
				replyDetailTask();
			}
		} else {
			Toast.makeText(MyReplyActivity.this, "网络繁忙，请重新提交！",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 获取反馈问题请求
	 */
	private void getReplyListTask() {
		GetReplyListTask getReplyListTask = new GetReplyListTask(
				MyReplyActivity.this);

		String queryByPhoneDate = String.valueOf(System.currentTimeMillis())
				.substring(0, 10);

		String queryByPhoneTime = queryByPhoneDate;

		String queryByPhoneSig = MD5.getMD5Str(
				queryByPhoneTime + Const.KEY_FOR_GAME).toLowerCase();
		String queryByPhoneUrl = "http://10000club.189.cn:80/service/queryByPhone.php?"
				+ "application_id=5"
				+ "&app_version="
				+ app_version
				+ "&client_imei="
				+ client_imei
				+ "&client_imsi=&client_mdn=&client_uid="
				+ client_uid
				+ "&sig="
				+ queryByPhoneSig
				+ "&time="
				+ queryByPhoneTime
				+ "&page_count=100&page=1";
		getReplyListTask.execute(queryByPhoneUrl, client_imei, client_uid);

	}

	/**
	 * 获取反馈问题列表
	 * 
	 * @param result
	 */
	public void getReplyList(List<ReplyMessageByPhone> result) {
		if (null != result) {
			if (result.size() > 0) {
				replyList = result;
				getMyReply();
			} else {
				Toast.makeText(MyReplyActivity.this, "你尚未提交反馈问题！",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(MyReplyActivity.this, "由于网络原因，获取反馈问题列表失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

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

	// ListView 点击事件
	private ListView.OnItemClickListener messageListOnClickListener = new ListView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int p, long arg3) {
			rlWdfktab.setVisibility(View.GONE);
			svYjfktab.setVisibility(View.GONE);
			rlReplyDetail.setVisibility(View.VISIBLE);
			myReplyDetailAdapter = new MyReplyDetailAdapter(
					new ArrayList<ReplyMessageByContentID>(),
					MyReplyActivity.this);
			lvMyReplyDetail.setAdapter(myReplyDetailAdapter);
			// 根据单个主题ID去服务器端取得反馈详情
			contentID = replyList.get(p).getContent_id();
			replyDetailTask();

		}
	};

	/**
	 * 调用ReplyDetailTask
	 */
	private void replyDetailTask() {
		String time = String.valueOf(System.currentTimeMillis()).substring(0,
				10);
		String sig = MD5.getMD5Str(time + Const.KEY_FOR_GAME).toLowerCase();
		String queryByContentIdUrl = "http://10000club.189.cn:80/service/queryByContentId.php?"
				+ "content_id="
				+ contentID
				+ "&time="
				+ time
				+ "&sig="
				+ sig
				+ "&page_count=100&page=1";

		ReplyDetailTask replyDetailTask = new ReplyDetailTask(
				MyReplyActivity.this);
		replyDetailTask.execute(queryByContentIdUrl);
	}

	/**
	 * 加载反馈详情
	 * 
	 * @param result
	 */
	public void getReplyDetail(List<ReplyMessageByContentID> result) {
		if (null != result) {
			myReplyDetailList = result;
			myReplyDetailAdapter = new MyReplyDetailAdapter(myReplyDetailList,
					MyReplyActivity.this);
			lvMyReplyDetail.setAdapter(myReplyDetailAdapter);
		} else {
			Toast.makeText(MyReplyActivity.this, "获取数据失败！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	// 继续反馈按钮事件
	private Button.OnClickListener btnContinueReplyOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {

			final EditText editText = new EditText(MyReplyActivity.this);
			editText.setHeight(200);
			editText.setTextSize(16);
			editText.setHint("请输入您要继续反馈的问题(180字以内)");
			editText.setGravity(Gravity.TOP);
			editText.setMovementMethod(ScrollingMovementMethod.getInstance());
			// 设置字数限制
			editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					180) });
			editText.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					if (editText.getText().length() == 180) {
						Toast.makeText(MyReplyActivity.this, "您输入的内容超过180个字",
								Toast.LENGTH_SHORT).show();
					}

				}
			});

			DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					// 关闭已开启的虚拟键盘
					imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							btnContinueReply.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					String replyContent = editText.getText().toString();

					if (null == replyContent || "".equals(replyContent)) {
						Toast.makeText(MyReplyActivity.this, "请输入您要继续反馈的问题！",
								Toast.LENGTH_SHORT).show();
						return;
					}
					String time = String.valueOf(System.currentTimeMillis())
							.substring(0, 10);

									String sig = MD5.getMD5Str(
											time + Const.KEY_FOR_GAME)
											.toLowerCase();

									try {
										replyContent = URLEncoder.encode(
												replyContent, "UTF-8");
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
									String replyUrl = "http://10000club.189.cn:80/service/userReq.php?"
											+ "app_version="
											+ app_version
											+ "&client_imei="
											+ client_imei
											+ "&client_imsi="
											+ "&client_mdn="
											+ "&client_uid="
											+ client_uid
											+ "&user_reply_message="
											+ replyContent
											+ "&reply_date="
											+ time
											+ "&remark=0"
											+ "&sig="
											+ sig
											+ "&time="
											+ time
											+ "&client_info="
											+ client_info
											+ "&application_id=5&content_id="
											+ contentID;

					Log.d("test", "replyUrl;-->" + replyUrl);
					flag = 1;
					UserReplyPostTask userReplyPostTask = new UserReplyPostTask(
							MyReplyActivity.this);
					userReplyPostTask.execute(replyUrl);

				}
			};

			DialogStyle ds = new DialogStyle();
			AlertDialog.Builder builder = ds.getBuilder(MyReplyActivity.this,
					"提交", "取消", comfirmL, null);

			builder.setTitle("问题反馈").setIcon(android.R.drawable.ic_dialog_map)
					.setView(editText).create().show();

		}
	};

	/**
	 * 获取用户评价结果
	 * 
	 * @param result
	 */
	public void getSatisfaction(String result) {
		if ("OK".equals(result)) {
			replyDetailTask();
		} else {
			Toast.makeText(MyReplyActivity.this, "由于网络原因，提交评价失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

		// 如果所有的记录选项等于数据集的条数，则移除列表底部视图
		if (totalItemCount == replyList.size() + 1) {
			// loadMoreView.setVisibility(View.GONE);
			lvMessageList.removeFooterView(loadMoreView);
			isAdd = false;
			adapter.notifyDataSetChanged();
			lvMessageList.requestFocusFromTouch();
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 数据集最后一项的索引
		int itemsLastIndex = adapter.getCount() - 1;
		int lastIndex = itemsLastIndex + 1;

		if (lastIndex == replyList.size() + 1) {
			// loadMoreView.setVisibility(View.GONE);
			lvMessageList.removeFooterView(loadMoreView);
			isAdd = false;
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (svYjfktab.isShown()) {
			MyReplyActivity.this.finish();

		} else if (rlReplyDetail.isShown()) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				svYjfktab.setVisibility(View.GONE);
				rlWdfktab.setVisibility(View.VISIBLE);
				myReplyDetailList = new ArrayList<ReplyMessageByContentID>();
				myReplyDetailAdapter = new MyReplyDetailAdapter(
						myReplyDetailList, MyReplyActivity.this);
				lvMyReplyDetail.setAdapter(myReplyDetailAdapter);
				rlReplyDetail.setVisibility(View.GONE);

			}
		} else {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				MyReplyActivity.this.finish();
			}
		}
		return true;
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
		}
		return versionName;
	}

}
