package com.egame.app.tasks;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import android.os.AsyncTask;

import com.egame.beans.GameInfo;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
import com.egame.utils.ui.DialogAbleActivity;
import com.egame.utils.xml.XmlUtil;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-3
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public abstract class UrlInfoTask2 extends AsyncTask<String, Integer, Integer> {
	GameInfo gameInfo;
	DialogAbleActivity context;
	String msisdn;

	public UrlInfoTask2(GameInfo gameInfo, DialogAbleActivity context, String msisdn) {
		this.gameInfo = gameInfo;
		this.context = context;
		this.msisdn = msisdn;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... params) {
		try {
			boolean status = false;
			String result = httpOrder(gameInfo.getOrderURL());
			L.d("DOM", "" + gameInfo.getOrderURL());
			status = XmlUtil.get(result, "code").equals("0");
			L.d("dd", "Order result:" + result);
			if (status) {// 如果订购成功,开始点播,点播扣费
				httpOrder(gameInfo.getDianboURL());
				new Thread(new Runnable() {
					@Override
					public void run() {
						// 记录订购状态.
						String url = Urls.getSaveOrderStatues(msisdn, gameInfo.getGameId());
						try {
							String save = HttpConnect.getHttpString(url, null, 30000);
							L.d("dd", "saveOrder:" + save);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
				return 0;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		L.d("dd", "dianbo result:" + result);
		if (result != null) {
			onSuccess();
		} else {
			// ToastUtil.show(context, "订购游戏失败");
		}
	}

	/**
	 * 获取订购或者点播的http请求
	 * 
	 * @param order
	 * @return
	 * @throws IOException
	 */
	public static String httpOrder(String order) throws IOException {
		StringBuffer buffer = new StringBuffer();
		URL url = new URL(order);
		InputStream is = null;
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		Properties prop = System.getProperties();
		prop.put("http.proxyHost", "10.0.0.200");
		prop.put("http.proxyPort", 80);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(20000);
		conn.setReadTimeout(20000);
		conn.setRequestProperty("X-ClientType", "1");
		conn.setRequestProperty("Content-Type", "application/xml");
		conn.setRequestMethod("GET");
		conn.connect();
		is = conn.getInputStream();
		L.d(conn.getResponseCode() + "");
		bis = new BufferedInputStream(is, 1024);
		int i = -1;
		byte data[] = new byte[1024];
		while ((i = bis.read(data)) != -1) {
			out.write(data, 0, i);
		}
		L.d("RT", buffer.toString());
		buffer.append(new String(out.toByteArray(), "utf-8"));
		prop.remove("http.proxyHost");
		prop.remove("http.proxyPort");
		return buffer.toString();
	}
	
	abstract public void onSuccess();
	abstract public void onFail();
}
