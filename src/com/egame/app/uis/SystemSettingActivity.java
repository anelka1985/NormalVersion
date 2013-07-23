package com.egame.app.uis;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.utils.common.PreferenceUtil;
import com.eshore.network.stat.NetStat;

/**
 * 
 * 类说明：系统设置
 * 
 * @创建时间 2011-12-26
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class SystemSettingActivity extends Activity implements OnClickListener {
	/** 返回按钮 */
	private ImageView ivBack;

	/** 新游戏上线允许模块 */
	private LinearLayout llNewGameRhr;

	/** 新游戏上线不允许模块 */
	private LinearLayout llNewGameJhy;

	/** 社区消息允许模块 */
	private LinearLayout llCommunityRhr;

	/** 社区消息不允许模块 */
	private LinearLayout llCommunityJhy;

	/** 新游戏上线允许ImageView */
	private TextView ivNewGameRhr;

	/** 新游戏上线不允许ImageView */
	private TextView ivNewGameJhy;

	/** 社区消息允许ImageView */
	private TextView ivCommunityRhr;

	/** 社区消息不允许ImageView */
	private TextView ivCommunityJhy;

	/** 社区消息不允许TextView */

	/** 新游戏上线 */
	private boolean pushNewGame;

	/** 社区消息 */
	private boolean pushMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_system);
		initView();
		initDate();
		initEvent();

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
		NetStat.onPausePage("SystemSettingActivity");
	}

	/**
	 * 初始化ui，主要是实现findViewById的操作
	 */
	void initView() {
		ivBack = (ImageView) findViewById(R.id.back);

		llNewGameRhr = (LinearLayout) findViewById(R.id.llNewGameRhr);
		llNewGameJhy = (LinearLayout) findViewById(R.id.llNewGameJhy);
		llCommunityRhr = (LinearLayout) findViewById(R.id.llCommunityRhr);
		llCommunityJhy = (LinearLayout) findViewById(R.id.llCommunityJhy);

		ivNewGameRhr = (TextView) findViewById(R.id.newGameRhr);
		ivNewGameJhy = (TextView) findViewById(R.id.newGameJhy);

		ivCommunityRhr = (TextView) findViewById(R.id.communityRhr);
		ivCommunityJhy = (TextView) findViewById(R.id.communityJhy);

	}

	/**
	 * 初始化系统设置
	 */
	void initDate() {
		boolean[] settings = PreferenceUtil.getBackRunSetting(SystemSettingActivity.this);

		pushNewGame = settings[0];
		pushMessage = settings[1];
		initSystemSetting(pushNewGame, "pushNewGame");
		initSystemSetting(pushMessage, "pushMessage");

	}

	/**
	 * 初始化事件,主要是实现按钮点击等事件的处理
	 */
	void initEvent() {
		// 返回按钮
		ivBack.setOnClickListener(this);

		// 新游戏上线
		llNewGameRhr.setOnClickListener(this);
		llNewGameJhy.setOnClickListener(this);
		// 社区消息
		llCommunityRhr.setOnClickListener(this);
		llCommunityJhy.setOnClickListener(this);
	}

	public void onClick(View v) {
		// 新游戏上线
		if (v == llNewGameRhr) {
			pushNewGame = true;
			ivNewGameRhr.setBackgroundResource(R.drawable.egame_lvse);
			ivNewGameRhr.setTextColor(Color.WHITE);
			ivNewGameJhy.setBackgroundResource(R.drawable.egame_huiseright);
			ivNewGameJhy.setTextColor(Color.GRAY);
		}

		if (v == llNewGameJhy) {
			pushNewGame = false;
			ivNewGameJhy.setBackgroundResource(R.drawable.egame_lvseright);
			ivNewGameJhy.setTextColor(Color.WHITE);
			ivNewGameRhr.setBackgroundResource(R.drawable.egame_huiseleft);
			ivNewGameRhr.setTextColor(Color.GRAY);
		}

		// 社区消息

		if (v == llCommunityRhr) {
			pushMessage = true;
			ivCommunityRhr.setBackgroundResource(R.drawable.egame_lvse);
			ivCommunityRhr.setTextColor(Color.WHITE);
			ivCommunityJhy.setBackgroundResource(R.drawable.egame_huiseright);
			ivCommunityJhy.setTextColor(Color.GRAY);
		}

		if (v == llCommunityJhy) {
			pushMessage = false;
			ivCommunityJhy.setBackgroundResource(R.drawable.egame_lvseright);
			ivCommunityJhy.setTextColor(Color.WHITE);
			ivCommunityRhr.setBackgroundResource(R.drawable.egame_huiseleft);
			ivCommunityRhr.setTextColor(Color.GRAY);
		}

		// 返回按钮提交修改
		if (v == ivBack) {
			saveSetting();
			SystemSettingActivity.this.finish();
		}

	}

	/**
	 * 初始化显示单选按钮
	 * 
	 * @param flag
	 * @param name
	 */
	void initSystemSetting(boolean flag, String name) {
		// 新游戏上线
		if ("pushNewGame".equals(name)) {
			if (flag == true) {
				ivNewGameRhr.setBackgroundResource(R.drawable.egame_lvse);
				ivNewGameRhr.setTextColor(Color.WHITE);
				ivNewGameJhy.setBackgroundResource(R.drawable.egame_huiseright);
				ivNewGameJhy.setTextColor(Color.GRAY);
			} else {
				ivNewGameJhy.setBackgroundResource(R.drawable.egame_lvseright);
				ivNewGameJhy.setTextColor(Color.WHITE);
				ivNewGameRhr.setBackgroundResource(R.drawable.egame_huiseleft);
				ivNewGameRhr.setTextColor(Color.GRAY);
			}
		}

		// 社区消息
		else if ("pushMessage".equals(name)) {
			if (flag == true) {
				ivCommunityRhr.setBackgroundResource(R.drawable.egame_lvse);
				ivCommunityRhr.setTextColor(Color.WHITE);
				ivCommunityJhy.setBackgroundResource(R.drawable.egame_huiseright);
				ivCommunityJhy.setTextColor(Color.GRAY);
			} else {
				ivCommunityJhy.setBackgroundResource(R.drawable.egame_lvseright);
				ivCommunityJhy.setTextColor(Color.WHITE);
				ivCommunityRhr.setBackgroundResource(R.drawable.egame_huiseleft);
				ivCommunityRhr.setTextColor(Color.GRAY);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			saveSetting();
			SystemSettingActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void saveSetting() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor edit = prefs.edit();
		edit.putBoolean("push_game", pushNewGame);
		edit.putBoolean("push_message", pushMessage);
		edit.commit();
	}
}
