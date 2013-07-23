package com.egame.utils.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.egame.utils.ui.ImageCacheUtils;

/**
 * http访问类
 * 
 * @author zhouzhe@lenovo-cw.com
 */
public class HttpConnect {

	private final static String TAG = "HttpConnect";

	/**
	 * 默认图片读取超时
	 */
	private final static int DEFAULT_BITMAP_TIMEOUT = 10 * 1000;

	/**
	 * 默认图片读取超时
	 */
	private final static int DEFAULT_TIMEOUT = 15 * 1000;

	/**
	 * http状态正常值
	 */
	private final static int HTTP_STATE_OK = 200;

	/**
	 * 默认编码
	 */
	public final static String ENCODING = "UTF-8";

	/**
	 * 缓冲大小
	 */
	private final static int BUFFER_SIZE = 1024 * 4;

	/**
	 * 从网络获取图片
	 * 
	 * @param 图片路径bitmapPath
	 * @param 超时时间timeout
	 * @return 返回bitmap
	 * @throws 网络错误或者解析图片为null的时候抛出Exception
	 */
	public static Bitmap getHttpBitmap(String bitmapPath, int timeout)
			throws Exception {
		long time = System.currentTimeMillis();
		Bitmap bitmap = ImageCacheUtils.getBitmapFormCache(bitmapPath);
		if (bitmap != null)
			return bitmap;
		InputStream is = null;
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		L.i(TAG, "Bitmap url=" + bitmapPath);
		URL url = new URL(bitmapPath);
		conn = (HttpURLConnection) url.openConnection();
		if (timeout > 0) {
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
		}
		conn.setRequestProperty("Connection", "close");
		conn.connect();
		is = conn.getInputStream();
		url = null;
		if (conn.getResponseCode() == HTTP_STATE_OK) {
			bis = new BufferedInputStream(is, BUFFER_SIZE);
			int i = -1;
			byte buf[] = new byte[4 * 1024];
			while ((i = bis.read(buf)) != -1) {
				out.write(buf, 0, i);
			}
			byte imgData[] = out.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
			// BitmapFactory.Options opts = new BitmapFactory.Options();
			// opts.inTempStorage = new byte[12 * 1024];
			// bitmap = BitmapFactory.decodeStream(bis, null, opts);
		}
		try {
			if (is != null)
				is.close();
			if (bis != null)
				bis.close();
			if (out != null)
				out.close();
			if (conn != null)
				conn.disconnect();
			is = null;
			bis = null;
			out = null;
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		L.i(TAG, "Bitmap return in " + (System.currentTimeMillis() - time));
		if (bitmap == null) {
			throw new Exception();
		} else {
			ImageCacheUtils.saveBitmapToCache(bitmapPath, bitmap);
			return bitmap;
		}
	}

	/**
	 * 默认超时时间读取图片的方法
	 * 
	 * @param bitmapPath
	 * @return
	 * @throws Exception
	 */
	public static Bitmap getHttpBitmap(String bitmapPath) throws Exception {
		return getHttpBitmap(bitmapPath, DEFAULT_BITMAP_TIMEOUT);
	}

	public static BitmapDrawable getHttpDrawable(String bitmapPath)
			throws Exception {
		return new BitmapDrawable(getHttpBitmap(bitmapPath));
	}

	/**
	 * http post方法基于httpclient
	 * 
	 * @param url
	 *            请求的url
	 * @param list
	 *            参数列表
	 * @param headers
	 *            http头列表
	 * @param timeout
	 *            超时时间
	 * @return 返回字符串
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postHttpString(String url, List<NameValuePair> list,
			List<Header> headers, int timeout) throws Exception {

		// 打印访问的url
		L.d("post", "url:" + url);

		// 打印参数键值对
		if (list != null) {
			for (NameValuePair pair : list) {
				L.d("post", pair.getName() + ":" + pair.getValue());
			}
		}

		// 打印头参数
		if (headers != null) {
			for (Header head : headers) {
				L.d("head", head.getName() + ":" + head.getValue());
			}
		}

		HttpClient httpclient = new DefaultHttpClient();

		// 设置超时时间,连接超时和读取超时
		if (timeout > 0) {
			httpclient.getParams().setIntParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
			httpclient.getParams().setIntParameter(
					CoreConnectionPNames.SO_TIMEOUT, timeout);
		}

		// 设置请求头参数,为null的时候不设置
		if (headers != null) {
			httpclient.getParams()
					.setParameter("http.default-headers", headers);
		}
		HttpPost httppost = new HttpPost(url);

		// 设置编码格式
		if (list != null) {
			httppost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
		}

		// 请求服务器
		HttpResponse response = httpclient.execute(httppost);

		// 获取http状态码
		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode == HttpStatus.SC_OK) {
			return EntityUtils.toString(response.getEntity());
		} else {
			// http状态不正确,主动抛出异常
			throw new Exception(statuscode + "");
		}
	}

	/**
	 * http post方法基于httpclient,使用默认的http请求头
	 * 
	 * @param url
	 *            请求的url
	 * @param list
	 *            参数列表
	 * @param timeout
	 *            超时事件
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @time 2011-12-26
	 */
	public static String postHttpString(String url, List<NameValuePair> list,
			int timeout) throws Exception {
		return postHttpString(url, list, null, timeout);
	}

	/**
	 * http post方法基于httpclient,使用默认的http请求头,默认的超时事件
	 * 
	 * @param url
	 *            请求的url
	 * @param list
	 *            参数列表
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @time 2011-12-26
	 */
	public static String postHttpString(String url, List<NameValuePair> list)
			throws Exception {
		return postHttpString(url, list, null, DEFAULT_TIMEOUT);
	}

	/**
	 * http get方法基于httpclient返回String
	 * 
	 * @param url
	 *            请求url
	 * @param http头列表
	 * @param timeout
	 *            超时时间
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String getHttpString(String url, List<Header> headers,
			int timeout) throws Exception {
		// 打印访问的url
		L.d("get", "url:" + url);
		// 打印参数键值对
		HttpClient httpclient = new DefaultHttpClient();
		// 设置超时时间,连接超时和读取超时
		if (timeout > 0) {
			httpclient.getParams().setIntParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
			httpclient.getParams().setIntParameter(
					CoreConnectionPNames.SO_TIMEOUT, timeout);
		}
		// 设置请求头参数,为null的时候不设置
		if (headers != null) {
			httpclient.getParams()
					.setParameter("http.default-headers", headers);
		}
		HttpGet httpget = new HttpGet(url);
		// 请求服务器
		HttpResponse response = httpclient.execute(httpget);
		// 获取http状态码
		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode == HttpStatus.SC_OK) {
			String s = EntityUtils.toString(response.getEntity());
			L.d("getHttpString", "" + s);
			return s;
		} else {
			// http状态不正确,主动抛出异常
			throw new Exception(statuscode + "");
		}
	}

	/**
	 * http get方法基于httpclient返回String，使用默认的请求头
	 * 
	 * @param url
	 *            请求url
	 * @param timeout
	 *            超时时间
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String getHttpString(String url, int timeout)
			throws Exception {
		return getHttpString(url, null, timeout);
	}

	/**
	 * http get方法基于httpclient返回String，使用默认的请求头，
	 * 
	 * @param url
	 *            请求url
	 * @param timeout
	 *            超时时间
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String getHttpString(String url) throws Exception {
		return getHttpString(url, null, DEFAULT_TIMEOUT);
	}

	// /**
	// * http get方法基于httpclient返回JSONObject
	// *
	// * @param url
	// * @return
	// * @throws Exception
	// * @author Administrator
	// * @time 2012-1-10
	// */
	// public static JSONObject getHttpJsonObject(String url) throws Exception{
	// String s = getHttpString(url);
	// L.d("getJsonString","" + s);
	// return new JSONObject(s);
	// }

}
