package com.egame.weibos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.egame.utils.common.PreferenceUtil;
import com.tencent.weibo.utils.MySSLSocketFactory;
import commonshttp.CommonsHttpOAuthConsumer;
import commonshttp.CommonsHttpOAuthProvider;

/**
 * @author LiuHan
 * @version 1.0 create 0n:2012/1/30
 */
public class MyOAuth {
	public static final String SIGNATURE_METHOD = "HMAC-SHA1";

	public static MyOAuth instance;
	/** 声明OAuthConsumer对象 */
	public static OAuthConsumer consumer;
	/** 声明OAuthProvider对象 */
	public static OAuthProvider provider;
	/** 未授权的token */
	public String consumerKey;
	/** 未授权的 token secret */
	public String consumerSecret;
	/** 签名字段 */
	public String verifier;
	private VerfierRecivier reciver;
	private Activity activity;

	private boolean isRegister = false;

	/**
	 * 构造函数
	 * 
	 * @param consumerKey
	 *            未授权的token
	 * @param consumerSecret
	 *            未授权的 token secret
	 */
	public MyOAuth(Activity activity, String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.activity = activity;
		instance = this;
	}

	/**
	 * 
	 * @param activity
	 *            上下文
	 * @param callbackurl
	 *            囘調地址
	 * @param requestToken
	 * @param accessToken
	 * @param authorization
	 * @return
	 */
	public String requestAccessToken(Context activity, String callbackurl,
			String requestToken, String accessToken, String authorization) {
		consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		provider = new CommonsHttpOAuthProvider(requestToken, accessToken,
				authorization);
		String authUrl = "";
		try {
			authUrl = provider.retrieveRequestTokenForTencent(consumer,
					callbackurl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == consumer.getToken() || null == consumer.getTokenSecret()) {
			return "exception";
		} else {
			IntentFilter filter = new IntentFilter();
			filter.addAction(TencentMicroblogUtil.VERIFIER);
			reciver = new VerfierRecivier();
			isRegister = true;
			Log.i(MyOAuth.class.getCanonicalName(),"注册了广播");
			this.activity.registerReceiver(reciver, filter);
		}
		return authUrl;
	}

	public void unRegister() {
		if (null != activity && this.isRegister) {
			this.isRegister = false;
			Log.i(MyOAuth.class.getCanonicalName(),"已经注销了广播");
			activity.unregisterReceiver(reciver);
		}

	}

	/**
	 * 功能：取得签名
	 * 
	 * @param token
	 * @param tokenSecret
	 * @param conn
	 * @return
	 */
	public HttpURLConnection signRequest(String token, String tokenSecret,
			HttpURLConnection conn) {
		consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		consumer.setTokenWithSecret(token, tokenSecret);
		try {
			consumer.sign(conn);
		} catch (OAuthMessageSignerException e1) {
			e1.printStackTrace();
		} catch (OAuthExpectationFailedException e1) {
			e1.printStackTrace();
		} catch (OAuthCommunicationException e1) {
			e1.printStackTrace();
		}
		try {
			conn.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 功能：取得签名
	 * 
	 * @param token
	 * @param tokenSecret
	 * @param url
	 * @param params
	 * @return
	 */
	public HttpResponse signRequest(String token, String tokenSecret,
			String url, List<BasicNameValuePair> params) {
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 关闭Expect:100-Continue握手
		// 100-Continue握手需谨慎使用，因为遇到不支持HTTP/1.1协议的服务器或者代理时会引起问题
		post.getParams().setBooleanParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		return signRequest(token, tokenSecret, post);
	}

	public HttpResponse signRequest(String token, String tokenSecret,
			HttpPost post) {
		consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		consumer.setTokenWithSecret(token, tokenSecret);
		HttpResponse response = null;
		try {
			consumer.sign(post);
		} catch (OAuthMessageSignerException e1) {
			e1.printStackTrace();
		} catch (OAuthExpectationFailedException e1) {
			e1.printStackTrace();
		} catch (OAuthCommunicationException e1) {
			e1.printStackTrace();
		}
		// 取得HTTP response
		try {
			response = (HttpResponse) getNewHttpClient().execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	class VerfierRecivier extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			boolean exception = false;
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				verifier = bundle.getString("verifier");
				try {
					provider.setOAuth10a(true);
					provider.retrieveAccessTokenForTencent(consumer, verifier);
				} catch (Exception e) {
					exception = true;
					//ToastUtil.show(activity, "取得授权的token异常");
					e.printStackTrace();
				}
				if (!exception) {
					// 保存授权后的Token 和 TokenSercret
					PreferenceUtil.store(context, consumer.getToken(),
							consumer.getTokenSecret(), "tencent");
					Log.i(MyOAuth.class.getCanonicalName(),
							"consumer.getToken()//getTokenSecret():"
									+ consumer.getToken() + "//"
									+ consumer.getTokenSecret());
					// 启动分享对话框
					ShareWindows.startShare();
				}
			}
		}
	}
}
