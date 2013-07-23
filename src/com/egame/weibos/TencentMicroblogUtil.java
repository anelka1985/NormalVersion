/**
 * 
 */
package com.egame.weibos;

import android.content.Context;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.Configuration;
import com.tencent.weibo.utils.OAuthClient;
import com.tencent.weibo.utils.TencentDialog;

/**
 * 描述：分享懂啊腾讯微博的相关工具类
 * 
 * @author LiuHan
 * @version 1.0 create on:2012/1/17
 * 
 */
public class TencentMicroblogUtil {
	/**标志微博的字符串*/
	public static final String SINA_BLOG="sina";
	public static final String TENCENT_BLOG="tencent";
	public static final String RENREN_BLOG="renren";
	/** ip setting */
	public static final String LOCAL_IP = "127.0.0.1";
	/** 腾讯分配的App_Key */
	public static String CONSUMERKEY_TENCENT = "801003022";
	/** 腾讯分配的App_Secret */
	public static String CONSUMERSECRET_TENCENT = "90b87b5ba3ba2ad1b010e420ad7a6413";
	
	
	private static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow";
	private static final String OAUTH_CALLBACK_HOST = "callback";
	/** 回调地址 */
	private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://" + OAUTH_CALLBACK_HOST;
	/** 显示认证页面的UI */
	public static TencentDialog tencentDialog = null;
	/** 进行游戏分享时图片存放的本地路径 */
	public static final String PIC_PATH = "/data/data/com.egame/pic.jpg";
	public static final String GET_PATH="/data/data/com.egame/files/pic.jpg";
	public static boolean ISACCESS;
	/** 关闭WebView浏览器，将verifier跳转到授权页面 */
	public static final String VERIFIER = "ACTION_VERFIER";
	public static String verifiers = "";

	/** 获取未授权的Request Token */
	public static final String requestTokenEndpointUrl_tencent = "https://open.t.qq.com/cgi-bin/request_token";

	/** 使用授权后的Request Token换取Access Token */
	public static final String accessTokenEndpointUrl_tencent = "https://open.t.qq.com/cgi-bin/access_token";

	/** 请求用户授权Request Token */
	public static final String authorizationWebsiteUrl_tencent = "https://open.t.qq.com/cgi-bin/authorize";

	public static void setToken(String str1, String str2) {
		CONSUMERKEY_TENCENT = str1;
		CONSUMERSECRET_TENCENT = str2;
	}

	/**
	 * 初始化TencentMicroblogUtil工具类
	 */
	public static OAuth initOAuth(Context context) {
		// 初始化OAuth请求令牌
		OAuth oauth = new OAuth(OAUTH_CALLBACK_URL);
		// 设置App_Key
		oauth.setOauth_consumer_key(CONSUMERKEY_TENCENT);
		// 设置App_Secret
		oauth.setOauth_consumer_secret(CONSUMERSECRET_TENCENT);
		Configuration.wifiIp = LOCAL_IP;
		return oauth;
	}

	public static void setOAuthToken(OAuth oauth, String oauth_token,
			String oauth_token_secret) {
		oauth.setOauth_token(oauth_token);
		oauth.setOauth_token_secret(oauth_token_secret);
	}

	/**
	 * @param oauth_verifier
	 * @param oauth_token
	 */
	public static OAuth getOAuthToken(OAuth oauth, OAuthClient auth,
			Context context, String clientIp, String oauth_verifier,
			String oauth_token) {
		oauth.setOauth_verifier(oauth_verifier);
		oauth.setOauth_token(oauth_token);
		clientIp = LOCAL_IP;
		try {
			oauth = auth.accessToken(oauth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oauth;
	}

	/**
	 * 功能：判断是否已经被授权
	 * 
	 * @param context
	 *            上下文
	 * @param type
	 *            0:软件分享 1：游戏分享
	 */
	public static boolean isHadAuthorize(Context context, String type,
			String oauth_token, String oauth_token_secret) {
		// 判断是不是已经有了Access Token
		if (null != oauth_token && !"".equals(oauth_token)
				&& null != oauth_token_secret && !"".equals(oauth_token_secret)) {
			return true;
		} else {
			return false;
		}
	}

}
