package com.egame.app.uis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.sys.DialogStyle;
import com.eshore.network.stat.NetStat;

/**
 * 
 * service中弹出对话框的activity
 * 
 * @author yaopp@gzylxx.com
 */
public class AlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		this.setContentView(R.layout.egame_alert_layout);
		DialogInterface.OnClickListener comfirmL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				CommonUtil.installGames(AlertActivity.this.getIntent()
						.getStringExtra("gameid"), AlertActivity.this);
				AlertActivity.this.finish();

			}
		};
		DialogInterface.OnClickListener cancelL = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlertActivity.this.finish();
			}
		};
		DialogStyle ds = new DialogStyle();
		AlertDialog.Builder builder = ds.getBuilder(AlertActivity.this, "确定",
				"取消", comfirmL, cancelL);
		builder.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP
						&& keyCode == KeyEvent.KEYCODE_BACK) {
					AlertActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		
		AlertDialog d = builder.setIcon(android.R.drawable.ic_dialog_map).setTitle("下载完成!")
		.setMessage("是否立即安装已下载的游戏?").create();
		d.setCanceledOnTouchOutside(false);
		d.show();
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
		NetStat.onPausePage("AlertActivity");
	}
}
