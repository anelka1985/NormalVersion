/**
 * 
 */
package com.egame.weibos;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.ImageItem;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.BindSinaAsyncTask;
import com.egame.app.tasks.SinaShareAsyncTask;
import com.egame.beans.GameInfo;
import com.egame.utils.common.PreferenceUtil;
import com.egame.utils.ui.MyTextWatcher;

/**
 * 描述:新浪微博的工具类
 * 
 * @author LiuHan
 * @version 1.0 create on :2012/1/9
 */
public class SinaMicroblogUtil {
	/** 新浪微博日志输出字段 */
	public static final String SINALOG_TAG = "MicroblogUtils-Sina";
	public static final String SINA_TOKEN = "sina_token";
	public static final String SINA_TOKEN_SECRET = "sina_token_secret";
	public static final String SINA = "weibo_sina";// 新浪微博名称
	/** 新浪微博KEY */
	public static final String SINA_KEY = "888807693";
	/** 新浪微博SECRET */
	public static final String SINA_SECRET = "0a8bda9fd4cbb5a2da063b84981281c1";

	public static Dialog inputDialog = null;

	public static String content = "";

	/**
	 * 分享到新浪微博的入口函数
	 * 
	 * @param ctx
	 *            上下文
	 * @param gameInfo
	 *            遊戲實體類 非遊戲的分享時 該職位 null
	 * @param content
	 *            分享的文字內容 遊戲分享時 該職位""
	 */
	public static void shareToSina(Context ctx, GameInfo gameInfo,
			String content) {
		SinaMicroblogUtil.content = content;
		if ("client".equals(PreferenceUtil.fetchType(ctx))) {
			// 判断是不是已经绑定了新浪微博
			if (isBindSinaWeibo(ctx)) {
				// 已绑定，弹出发送分享对话框
				sendShareWeiboDialog(ctx, gameInfo, "client");
			} else {
				// 为绑定，则弹出绑定对话框
				XAuthBindWeiboDialog(ctx, null);
			}
		} else {
			// 判断是不是已经绑定了新浪微博
			if (isBindSinaWeibo(ctx)) {
				// 已绑定，弹出发送分享对话框
				sendShareWeiboDialog(ctx, gameInfo, "game");
			} else {
				// 为绑定，则弹出绑定对话框
				XAuthBindWeiboDialog(ctx, gameInfo);
			}
		}
	}

	/**
	 * 判断是否绑定了新浪微博
	 * 
	 * @return
	 */
	public static boolean isBindSinaWeibo(Context ctx) {
		String[] array = PreferenceUtil.fetch(ctx, "sina");
		if (null == array[0] || null == array[1]) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 绑定新浪微博的用户输入窗口
	 * 
	 * @param context
	 */
	public static void XAuthBindWeiboDialog(final Context context,
			final GameInfo gameInfo) {
		// 创建用户输入对话框
		LayoutInflater factory = LayoutInflater.from(context);
		final View DialogView = factory.inflate(R.layout.egame_sina_dialog,
				null);
		inputDialog = new Dialog(context, R.style.egame_sinaDialog);
		inputDialog.setContentView(DialogView);
		inputDialog.show();
		// 新浪微博的账号
		final EditText sinaAccount = (EditText) DialogView
				.findViewById(R.id.sina_account);
		// 新浪微博的账号密码
		final EditText sinaPass = (EditText) DialogView
				.findViewById(R.id.sina_password);
		Button sendRequest = (Button) DialogView
				.findViewById(R.id.request_bind);
		sendRequest.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 请求绑定操作 取出用户输入的用户名和密码
				if ("".equals(sinaAccount.getText().toString())
						|| "".equals(sinaPass.getText().toString())) {
					Toast.makeText(context, R.string.egame_sina_bind_prompt,
							Toast.LENGTH_SHORT).show();
				} else {
					inputDialog.dismiss();
					// 启动绑定异步任务
					new BindSinaAsyncTask(context, gameInfo).execute(
							sinaAccount.getText().toString(), sinaPass
									.getText().toString());
				}

			}

		});
		Button cancelRequest = (Button) DialogView
				.findViewById(R.id.cancel_request);
		cancelRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputDialog.dismiss();
			}

		});
	}

	/**
	 * 发送分享的用户输入窗口
	 * 
	 * @param context
	 */
	public static void sendShareWeiboDialog(final Context context,
			final GameInfo gameInfo, final String type) {
		// 启动用户输入对话框
		LayoutInflater factory = LayoutInflater.from(context);
		final View DialogView = factory.inflate(
				R.layout.egame_sina_share_dialog, null);
		final Dialog inputDialog = new Dialog(context, R.style.egame_sinaDialog);
		inputDialog.setContentView(DialogView);
		inputDialog.show();
		final EditText yourWord = (EditText) DialogView
				.findViewById(R.id.share_my_say);
		if ("client".equals(type)) {
			yourWord.setText(getShareWeiboWord());
		} else {
			yourWord.setText("我在@中国电信爱游戏 发现了一个非常好玩的游戏："
					+ gameInfo.getGameName() + "，快来试试吧！");
		}
		yourWord.addTextChangedListener(new MyTextWatcher(context, 140,
				yourWord));
		yourWord.requestFocus();
		Button sendButton = (Button) DialogView.findViewById(R.id.send_button);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if ("".equals(yourWord.getText().toString())) {
					if ("client".equals(type)) {
						new SinaShareAsyncTask(context, gameInfo)
								.execute(getShareWeiboWord());
					} else {
						new SinaShareAsyncTask(context, gameInfo)
								.execute(getShareWeiboWord(
										gameInfo.getGameName(),
										String.valueOf(gameInfo.getEntityId())));
					}
					inputDialog.dismiss();
				} else {
					new SinaShareAsyncTask(context, gameInfo).execute(yourWord
							.getText().toString());
					inputDialog.dismiss();
				}
			}
		});
		Button cancelButton = (Button) DialogView
				.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputDialog.dismiss();
			}

		});
	}

	/**
	 * 分享默认的语言内容
	 * 
	 * @param gamename
	 *            游戏名称
	 * @param gameid
	 *            游戏ID
	 * @return
	 */
	public static String getShareWeiboWord(String gamename, String gameid) {
		String s = "我在@中国电信爱游戏 发现了一个非常好玩的游戏：" + gamename
				+ "，免费下载，快来试试吧！http://game.189.cn/game/" + gameid + "/38.html";
		return s;
	}

	/**
	 * 分享的内容
	 */
	public static String getShareWeiboWord() {
		return SinaMicroblogUtil.content;
	}

	/**
	 * 功能:将网络上的图片以流的方式储存在byte数组中
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] getNetfile(String filePath) throws IOException {
		URL url = new URL(filePath);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				request.getInputStream());
		int length = bufferedInputStream.available();
		byte[] temp = new byte[length];
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		int size = 0;
		while ((size = bufferedInputStream.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		bufferedInputStream.close();
		byte[] content = out.toByteArray();
		return content;
	}

	/**
	 * 向新浪微博发布一条消息并上传一张图片
	 * 
	 * @param ctx
	 * @param content
	 * @param type
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String updateState(Context ctx, String content,
			String filePath) {
		// 取得Weibo对象的一个引用
		Weibo weibo = new Weibo();
		// 设置微博的Key和Secret
		addKeyAndSecret();
		// 设置微博的token和tokenSecret
		getTokenAndTokenSecret(ctx, weibo);
		try {
			try {
				byte[] contents = getNetfile(filePath);
				ImageItem image = new ImageItem("pic", contents);
				String str = URLEncoder.encode(content, "utf-8");
				// 上传图片和文本内容
				weibo4android.Status status = weibo.uploadStatus(str, image);
				if (status != null) {
					return "true";
				} else {
					return "false";
				}
			} catch (WeiboException e1) {
				e1.printStackTrace();
				return String.valueOf(e1.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "isException";
		}

	}

	/**
	 * 向新浪微博发布一条消息
	 * 
	 * @param ctx
	 * @param content
	 * @throws UnsupportedEncodingException
	 */
	public static String updateState(Context ctx, String content) {
		// 取得Weibo的对象的引用
		Weibo weibo = new Weibo();
		// 设置微博的Key和Secret
		addKeyAndSecret();
		// 设置微博的token和tokenSecret
		getTokenAndTokenSecret(ctx, weibo);
		try {
			try {

				Status status = weibo.updateStatus(content);
				if (status != null) {
					return "true";
				} else {
					return "false";
				}
			} catch (WeiboException e1) {
				e1.printStackTrace();
				// 取得 用户一次登录 发布相同信心的异常代码
				return String.valueOf(e1.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 别的异常不做特殊处理
			return "isException";
		}

	}

	/**
	 * 设置 一些相关的信息 ：Weibo.CONSUMER_KEY 和 Weibo.CONSUMER_SECRET
	 */
	public static void addKeyAndSecret() {
		Weibo.CONSUMER_KEY = SinaMicroblogUtil.SINA_KEY;
		Weibo.CONSUMER_SECRET = SinaMicroblogUtil.SINA_SECRET;
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);
	}

	/**
	 * 取得上次返回的tokenSecret和token
	 * 
	 * @param weibo
	 *            要设置tokenSecret和token的Weibo对象
	 */
	public static void getTokenAndTokenSecret(Context context, Weibo weibo) {
		SharedPreferences share = context.getSharedPreferences(
				PreferenceUtil.StoreDataName, Context.MODE_PRIVATE);
		String token = share.getString(SinaMicroblogUtil.SINA_TOKEN, "");
		String tokenSecret = share.getString(
				SinaMicroblogUtil.SINA_TOKEN_SECRET, "");
		weibo.setToken(token, tokenSecret);
	}

}
