package com.egame.weibos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.utils.ui.ToastUtil;

/**
 * 描述：一对话框的形式加载授权页面
 * 
 * @author LiuHan
 * @version 1.0 create on：2012/1/18
 * 
 */
public class TencentDialog extends Dialog {
	/** 声明WebView的引用 */
	protected WebView webView;
	/** 布局 */
	private FrameLayout mContent;
	private RelativeLayout mShowLoad;
	private TextView textView;
	/** 要加载的Url */
	private String mUrl = "";
	/** 横屏时的大小 */
	private static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
	/** 竖屏时的大小 */
	private static final float[] DIMENSIONS_PORTRAIT = { 300, 370 };
	/** 上下文 */
	public Activity mContext;
	public static final String CALLBACKURL = "app:AuthorizeActivity";
	private MyOAuth oauth;
	private String verifier = "";
	private int index = 0;
	public final static int RESULT_CODE = 1;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param url
	 *            请求授权的Url
	 */
	public TencentDialog(Activity context, String token, String tokenSecret) {
		super(context);
		this.mContext = context;
		TencentMicroblogUtil.setToken(token, tokenSecret);
		// 初始化相关数据
		oauth = new MyOAuth(this.mContext,
				TencentMicroblogUtil.CONSUMERKEY_TENCENT,
				TencentMicroblogUtil.CONSUMERSECRET_TENCENT);
		new GetAccessTokenTask().execute("");
		if ("exception".equals(this.mUrl)) {
			Toast.makeText(mContext, "请求失败稍候重试", Toast.LENGTH_SHORT);
			this.dismiss();
		}
	}

	/**
	 * @param context
	 * @param theme
	 */
	public TencentDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public TencentDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取得线性布局的引用
		mContent = new FrameLayout(getContext());
		mContent.setBackgroundColor(Color.WHITE);
		// 构建加载提示布局
		setLoadLayout();
		mContent.addView(mShowLoad);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全局布局
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		float scale = getContext().getResources().getDisplayMetrics().density;
		float[] dimensions = display.getWidth() < display.getHeight() ? DIMENSIONS_PORTRAIT
				: DIMENSIONS_LANDSCAPE;
		addContentView(mContent, new FrameLayout.LayoutParams(
				(int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
						* scale + 0.5f)));
	}

	/**
	 * 功能构建加载提示布局
	 */
	private void setLoadLayout() {
		// 构建加载提示布局
		mShowLoad = new RelativeLayout(getContext());
		RelativeLayout.LayoutParams rLayoutFill = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rLayoutFill.addRule(RelativeLayout.CENTER_IN_PARENT,
				RelativeLayout.TRUE);
		textView = new TextView(getContext());
		textView.setText("请稍候  . . .");
		textView.setTextColor(Color.GRAY);
		textView.setTextSize(16);
		mShowLoad.addView(textView, rLayoutFill);
	}

	/**
	 * 功能：启动WebView
	 */
	private void setUpWebView(String urls) {
		webView = new WebView(getContext());
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.requestFocusFromTouch();
		webView.loadUrl(urls);
		FrameLayout.LayoutParams fill = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		webView.setLayoutParams(fill);

		webView.addJavascriptInterface(new JavaScriptInterface(), "Methods");
		webView.setWebViewClient(new MyWebViewClient());
		mContent.removeView(mShowLoad);
		mContent.addView(webView);
		// mContent.addView(mShowLoad);
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.i("TencentDialog", "loading end. . . ");
			super.onPageFinished(view, url);
			if (mShowLoad.getVisibility() == View.VISIBLE) {
				mShowLoad.setVisibility(View.GONE);
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.i("errorCode", "errorCode=" + errorCode);
			TencentDialog.this.dismiss();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("TencentDialog", "loading in. . . " + url);
			return false;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.i("TencentDialog", "loading begin....Url:" + url);
			// if (mShowLoad.getVisibility() == View.GONE) {
			// mShowLoad.setVisibility(View.VISIBLE);
			// textView.setText("授权认证中. . . ");
			// webView.setVisibility(View.GONE);
			// }
			Pattern p = Pattern.compile("^" + CALLBACKURL
					+ ".*oauth_verifier=(\\d+)");
			Matcher m = p.matcher(url);
			if (m.find() && index == 0) {
				index++;
				verifier = m.group(1);
				Log.i(TencentDialog.class.getCanonicalName(), verifier);
				Intent intent = new Intent();
				intent.setAction(TencentMicroblogUtil.VERIFIER);
				Bundle extras = new Bundle();
				extras.putString("verifier", verifier);
				intent.putExtras(extras);
				getContext().sendBroadcast(intent);
			}

			if (url.indexOf("http://api.t.163.com/oauth/authorize") != -1
					&& url.indexOf("authorize=1") != -1) {
				TencentMicroblogUtil.ISACCESS = true;
				Toast.makeText(getContext(), "请重新返回“绑定界面”进行最后授权！",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	class JavaScriptInterface {
		public void getHTML(String html) {
			Log.i(TencentDialog.class.getCanonicalName(), html);
			String verifiers = getVerifier(html);
			if (!"".equals(verifiers)) {
				Log.i(TencentMicroblogUtil.class.getCanonicalName(), verifiers);
				TencentMicroblogUtil.verifiers = verifiers;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != webView) {
				if (webView.canGoBack()) {
					webView.goBack();
					return true;
				}
			}
			TencentDialog.this.dismiss();
			this.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 根据正则表达式获取验证码
	 * 
	 * @param html
	 * @return
	 */
	public String getVerifier(String html) {
		String ret = "";
		String regEx = "授权码：[0-9]{6}"; // 匹配模式
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		boolean result = m.find();
		if (result) {
			// 获取验证码
			ret = m.group(0).substring(4);
		}
		return ret;
	}

	private class GetAccessTokenTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return oauth.requestAccessToken(getContext(), CALLBACKURL,
					TencentMicroblogUtil.requestTokenEndpointUrl_tencent,
					TencentMicroblogUtil.accessTokenEndpointUrl_tencent,
					TencentMicroblogUtil.authorizationWebsiteUrl_tencent);
		}

		/**
		 * @param result
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// MyOAuth.unRegister(getContext());
			if ("exception".equals(result)) {
				exitTencentDialog();
			} else {
				setUpWebView(result);
			}
		}
	}

	public void exitTencentDialog() {
		ToastUtil.show(getContext(), "网络请求异常.... ");
		TencentDialog.this.dismiss();
	}

}
