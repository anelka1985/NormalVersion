package com.egame.weibos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.StatusSetListener;
import com.egame.beans.GameInfo;
import com.egame.utils.ui.MyTextWatcher;
import com.renren.api.connect.android.AsyncRenren;
import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.common.AbstractRequestListener;
import com.renren.api.connect.android.exception.RenrenError;
import com.renren.api.connect.android.feed.FeedPublishRequestParam;
import com.renren.api.connect.android.feed.FeedPublishResponseBean;
import com.renren.api.connect.android.status.StatusSetRequestParam;

/**
 * 描述：人人网分享的工具类
 * 
 * @author LiuHan
 * @version 1.0 create on：2012/1/16
 * 
 */
public class RenrenUtil {
	/** 应用相关的常量 */
	private static final String API_KEY = "d17ba085e0914fe1a7115743218ed10a";
	private static final String SECRET_KEY = "62dad6ec33e540238656c048ef2ba6e7";
	private static final String APP_ID = "164798";
	/** Renren对象的声明 */
	private static Renren renren;
	/** 请求网络的提示UI */
	private static ProgressDialog mProgressDialog;

	/**
	 * 实例化Renren对象
	 * 
	 * @param context
	 * @return
	 */
	public static Renren initRenren(final Context context) {
		renren = new Renren(API_KEY, SECRET_KEY, APP_ID, context);
		return renren;
	}

	/**
	 * 功能：分享游戏大厅到人人网
	 */
	public static void postFeedSoft(final Context context, String shareWord) {
		// 启动用户输入对话框
		LayoutInflater factory = LayoutInflater.from(context);
		final View dialogview = factory.inflate(
				R.layout.egame_renren_client_dialog, null);
		final Dialog inputDialog = new Dialog(context, R.style.egame_sinaDialog);
		inputDialog.setContentView(dialogview);
		inputDialog.show();
		// 分享的内容
		final EditText shareWords = (EditText) dialogview
				.findViewById(R.id.myword);
		shareWords.setText(shareWord);
		shareWords.addTextChangedListener(new MyTextWatcher(context, 140,
				shareWords));
		shareWords.requestFocus();// 设置EditText的光标的位置
		
		//按钮事件处理
		final Button publishButton = (Button) dialogview
				.findViewById(R.id.publish_button);
		publishButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				mProgressDialog = new ProgressDialog(context);
				mProgressDialog.setMessage(context.getResources().getString(
						R.string.egame_post_status_sending));
				mProgressDialog.show();
				// 进行分享操作
				StatusSetRequestParam param = new StatusSetRequestParam(shareWords.getText().toString());
				StatusSetListener listener = new StatusSetListener(context,
						mProgressDialog);
				try {
					AsyncRenren aRenren = new AsyncRenren(renren);
					aRenren.publishStatus(param, listener, // 对结果进行监听
							true); // 若超过140字符，则自动截短
				} catch (Throwable e) {
					e.getMessage();
				}
				inputDialog.dismiss();
			}
		});

		final Button cancelButton = (Button) dialogview
				.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				mProgressDialog.dismiss();
				Toast.makeText(context, "您取消了分享操作", Toast.LENGTH_SHORT).show();
				inputDialog.dismiss();
			}

		});

		
	}

	/**
	 * 功能：分享游戏到人人网
	 * 
	 * @param context
	 *            上下文
	 * @param renren2
	 *            人人对象
	 * @param string
	 *            文字内容 游戏分享时该值为null
	 * @param gameInfo
	 *            装载游戏信息的java bean 在进行大厅分享时该值为null
	 */
	public static void postGameShare(final Context context, Renren renren2,
			String string, final GameInfo gameInfo, BitmapDrawable gameIcon) {
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(context.getResources().getString(
				R.string.egame_post_status_sending));

		// 启动用户输入对话框
		LayoutInflater factory = LayoutInflater.from(context);
		final View dialogview = factory.inflate(R.layout.egame_renren_dialog,
				null);
		final Dialog inputDialog = new Dialog(context, R.style.egame_sinaDialog);
		inputDialog.setContentView(dialogview);
		inputDialog.show();
		// 游戏图片
		ImageView gamePic = (ImageView) dialogview.findViewById(R.id.game_pics);
		// gamePic.setImageBitmap(gameIcon);
		gamePic.setBackgroundDrawable(gameIcon);
		// 游戏名称
		TextView gameName = (TextView) dialogview.findViewById(R.id.game_name);
		gameName.setText(gameInfo.getGameName());
		// 游戏类型
		TextView gameType = (TextView) dialogview.findViewById(R.id.game_type);
		gameType.setText("游戏类型：" + gameInfo.getTypeName());
		// 游戏介绍
		TextView gameIntroduction = (TextView) dialogview
				.findViewById(R.id.game_introduction);
		String introStr = gameInfo.getIntroduction().length() < 33 ? gameInfo
				.getIntroduction() : (gameInfo.getIntroduction().substring(0,
				33) + "......");
		gameIntroduction.setText("游戏介绍：" + introStr);

		// 分享的内容
		final EditText shareWord = (EditText) dialogview
				.findViewById(R.id.myword);
		shareWord.setText("我在中国电信爱游戏 发现了一个非常好玩的游戏：" + gameInfo.getGameName()
				+ "，快来试试吧！");
		shareWord.addTextChangedListener(new MyTextWatcher(context, 140,
				shareWord));
		shareWord.requestFocus();// 设置EditText的光标的位置
		final Button publishButton = (Button) dialogview
				.findViewById(R.id.publish_button);
		publishButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog.show();
				AsyncRenren asyncRenren = new AsyncRenren(renren);
				FeedPublishRequestParam feed = new FeedPublishRequestParam(
						gameInfo.getGameName(), // 游戏的名称

						"游戏介绍："
								+ (gameInfo.getIntroduction().length() >= 190 ? (gameInfo
										.getIntroduction().substring(0, 190) + "...")
										: gameInfo.getIntroduction()),// 游戏介绍

						"http://game.189.cn/game/" + gameInfo.getGameId()
								+ "/38.html", // A
						gameInfo.getPicturePath() + "pic1.jpg", // A

						"游戏类型:" + gameInfo.getTypeName(),// A

						"中国电信爱游戏", "http://game.189.cn/", // 动作链接,
						shareWord.getText().toString());// A

				final Handler handler = new Handler(context.getMainLooper());
				AbstractRequestListener<FeedPublishResponseBean> listener = new AbstractRequestListener<FeedPublishResponseBean>() {
					@Override
					public void onRenrenError(RenrenError renrenError) {
						final int errorCode = renrenError.getErrorCode();
						handler.post(new Runnable() {
							@Override
							public void run() {
								if (errorCode == RenrenError.ERROR_CODE_OPERATION_CANCELLED) {
									progressDialog.dismiss();
									inputDialog.dismiss();
									Toast.makeText(context, "发布分享取消",
											Toast.LENGTH_SHORT).show();
								} else {
									progressDialog.dismiss();
									inputDialog.dismiss();
									Toast.makeText(context, "发布分享失败",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
					}

					@Override
					public void onFault(Throwable fault) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								progressDialog.dismiss();
								inputDialog.dismiss();
								Toast.makeText(context, "发布分享失败",
										Toast.LENGTH_SHORT).show();
							}
						});
					}

					@Override
					public void onComplete(FeedPublishResponseBean bean) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								progressDialog.dismiss();
								Toast.makeText(context, "发布分享成功",
										Toast.LENGTH_SHORT).show();
								inputDialog.dismiss();
							}
						});
					}
				};
				asyncRenren.publishFeed(feed, listener, true);
			}

		});

		final Button cancelButton = (Button) dialogview
				.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog.dismiss();
				Toast.makeText(context, "您取消了分享操作", Toast.LENGTH_SHORT).show();
				inputDialog.dismiss();
			}

		});

	}
}
