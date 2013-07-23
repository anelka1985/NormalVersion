package com.egame.app.uis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.tasks.PostCommentAsycnTask;
import com.egame.utils.ui.MyTextWatcher;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

/**
 * @desc 发表评论页面
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-6
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class GameCommentActivity extends Activity implements OnClickListener {
	EgameApplication application;


//	/** 游戏评论的类型 0:星级评论 1：语言评论 2；星级和语言评论 */
//	private int postType = 0;

	/** 出发返回事件的UI控件 */
	private Button btnBack;

	/** 提示用户进行星级评论的UI控件 */
	private TextView tvIsComment;

	/** 评分条UI控件 */
	private RatingBar rbRatingBar;

	/** 显示用户评论过的星级 */
	private ImageView ivIsStar;

	/** 显示星星评论效果的UI控件：如 很给力 */
	private TextView tvStarText;

	/** 编辑评论文字的UI控件 */
	private EditText etContent;

	/** 触发发表评论网络请求的UI控件 */
	private LinearLayout llComment;

	Bundle bundle;

	/** 是否有文字评论内容 */
	private boolean isEdit;

	/** 是否改变过星级状态 */
	private boolean isRatEdit;

	/** 星星个数 */
	private int starNumber = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_gamecomment);
		application = (EgameApplication) getApplication();
		isRatEdit = false;
		isEdit = false;
		// 取得UI控件的引用
		initUIControl();
		// 取得相关初始化数据
		getIntentExtras();
		new PostCommentAsycnTask(GameCommentActivity.this, bundle.getString("gameId"), bundle.getString("userId"),
				null, null, null, null, 0).execute("getLastComment");
		// 为评分条添加事件
		addRatingBarEvent();
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
		NetStat.onPausePage("GameCommentActivity");
	}

	private void getIntentExtras() {
		bundle = this.getIntent().getExtras();
		if (null == bundle) {
			// 数据获取失败 返回
			ToastUtil.show(this, this.getResources().getString(R.string.egame_data_wrong));
			GameCommentActivity.this.finish();
		}
	}

	/**
	 * 取得UI控件的引用
	 */
	public void initUIControl() {
		btnBack = (Button) findViewById(R.id.back);
		btnBack.setOnClickListener(this);
		tvIsComment = (TextView) findViewById(R.id.isComment);
		rbRatingBar = (RatingBar) findViewById(R.id.ratingbar);
		ivIsStar = (ImageView) findViewById(R.id.isStar);
		tvStarText = (TextView) findViewById(R.id.starText);
		etContent = (EditText) findViewById(R.id.content);
		etContent.addTextChangedListener(new MyTextWatcher(GameCommentActivity.this, 120, etContent));
		llComment = (LinearLayout) findViewById(R.id.comment);
		llComment.setOnClickListener(this);
	}

	/**
	 * 功能：为评分条添加事件监听
	 */
	public void addRatingBarEvent() {
		rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				if (rating <= 1) {
					ratingBar.setRating(1.0f);
					starNumber = 1;
					tvStarText.setText(R.string.egame_star1);
				} else if (rating <= 2 && rating > 1) {
					starNumber = 2;
					tvStarText.setText(R.string.egame_star2);
				} else if (rating <= 3 && rating > 2) {
					starNumber = 3;
					tvStarText.setText(R.string.egame_star3);
				} else if (rating <= 4 && rating > 3) {
					starNumber = 4;
					tvStarText.setText(R.string.egame_star4);
				} else if (rating <= 5 && rating > 4) {
					starNumber = 5;
					tvStarText.setText(R.string.egame_star5);
				}
			}
		});
	}

	/**
	 * 功能：相关UI控件的单击事件处理函数
	 */
	@Override
	public void onClick(View v) {
		if (v == btnBack) {
			this.finish();

		} else if (v == llComment) {
			// 发表评论
			try {
				enterPostComemnt();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 功能：显示网络请求获取数据的结果
	 */
	public void netWorkPrompt(Context context, String resultMsg, int resultCode, String score) {
		if (score != null) {
			if (Integer.parseInt(score) == 0) {
				isRatEdit = true;
			} else if (Integer.parseInt(score) > 0) {
//				L.d("dd", "score=" + score);
				tvIsComment.setText("您已发表过星级评论：");
				ivIsStar.setVisibility(View.VISIBLE);
				rbRatingBar.setVisibility(View.INVISIBLE);
				if ("5".equals(score)) {
					ivIsStar.setBackgroundResource(R.drawable.egame_bigstar5);
					tvStarText.setText(R.string.egame_star5);
				} else if ("4".equals(score)) {
					ivIsStar.setBackgroundResource(R.drawable.egame_bigstar4);
					tvStarText.setText(R.string.egame_star4);
				} else if ("3".equals(score)) {
					ivIsStar.setBackgroundResource(R.drawable.egame_bigstar3);
					tvStarText.setText(R.string.egame_star3);
				} else if ("2".equals(score)) {
					ivIsStar.setBackgroundResource(R.drawable.egame_bigstar2);
					tvStarText.setText(R.string.egame_star2);
				} else if ("1".equals(score)) {
					ivIsStar.setBackgroundResource(R.drawable.egame_bigstar1);
					tvStarText.setText(R.string.egame_star1);
				}
			}
		} else if (resultCode == 0) {
			ToastUtil.show(context, R.string.egame_comment_success);
			setResult(1);
			this.finish();
		} else if (resultCode == 1) {
			if ("".equals(resultMsg)) {
				ToastUtil.show(context, R.string.egame_request_wrong);
			} else {
				ToastUtil.show(context, resultMsg);
			}
		}
	}

	/**
	 * 进入评论的流程检测，检测用户的输入，给予相应的提示:不允许只发表星级评论
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void enterPostComemnt() throws UnsupportedEncodingException {
		if (!"".equals(etContent.getText().toString())) {
			isEdit = true;
		}
//		L.d("dd", isEdit + "文字|||星级" + isRatEdit);
		if (!isEdit) {
			// 如果用户没有编辑过文字评论
			ToastUtil.show(this, this.getResources().getString(R.string.egame_input_prompt));
		} else {
			if (isRatEdit == false) {
				new PostCommentAsycnTask(this, bundle.getString("gameId"), bundle.getString("userId"), null,
						URLEncoder.encode(etContent.getText().toString(), "UTF-8"), bundle.getString("nickName"),
						bundle.getString("gameName"), -1).execute("comment");
			} else {
				// 启动发表评论的异步任务
				new PostCommentAsycnTask(this, bundle.getString("gameId"), bundle.getString("userId"),
						String.valueOf(starNumber), URLEncoder.encode(etContent.getText().toString(), "UTF-8"),
						bundle.getString("nickName"), bundle.getString("gameName"), -1).execute("comment");
			}
		}
	}
}
