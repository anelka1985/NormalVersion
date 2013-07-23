package com.egame.app.tasks;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.beans.GameInfo;
import com.egame.beans.interfaces.DownGameAble;
import com.egame.config.Urls;
import com.egame.utils.common.L;
import com.egame.utils.common.Md5PwdEncoder;
import com.egame.utils.ui.ToastUtil;
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
public class UrlInfoTask extends AsyncTask<String, Integer, Integer> {
	GameInfo gameInfo;
	String type;
	String msisdn;
	DownGameAble downGameAble;

	public UrlInfoTask(GameInfo gameInfo, DownGameAble downGameAble, String msisdn) {
		this.gameInfo = gameInfo;
		this.downGameAble = downGameAble;
		this.msisdn = msisdn;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... params) {
		type = params[0];
		try {
			boolean status = false;
			String result = httpOrder(gameInfo.getOrderURL());
			L.d("UrlInfoTask", "开始订购的链接是:" + gameInfo.getOrderURL());
			status = XmlUtil.get(result, "code").equals("0");
			if (status) {// 如果订购成功,开始点播,点播扣费
				int resultCode = 1;
				String opTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				Md5PwdEncoder m = new Md5PwdEncoder();
				String key = m.encodeLoginPassword(gameInfo.getGameId() + "CTGAME" + opTime + msisdn);
				try {
					/** 用代理访问 */
					String mResult = httpOrder(Urls.getDianboResult(gameInfo.getDianboURL(), gameInfo.getGameId(), opTime, key));
					L.d("UrlInfoTask", "点播结果:" + mResult);
					resultCode = new JSONObject(mResult).getJSONObject("result").optInt("resultcode");
				} catch (Exception e1) {
					e1.printStackTrace();
					return 1;
				}
				return resultCode;
			} else {
				return 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (result == 0) {
			downGameAble.downloadGame(1);
		} else {
			ToastUtil.show(downGameAble.getDialogActivity(), "订购游戏失败");
		}
	}

	/**
	 * 获取订购或者点播的http请求（代理）
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
}
