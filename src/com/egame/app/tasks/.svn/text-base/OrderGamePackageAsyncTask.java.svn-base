package com.egame.app.tasks;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

import com.egame.app.EgameApplication;
import com.egame.app.uis.GamePackageDetailActivity;
import com.egame.beans.PackageDetailBean;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;
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
 * @timer 2012-1-30
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class OrderGamePackageAsyncTask extends AsyncTask<String, Integer, String> {
	private PackageDetailBean packageDetailBean;
	private String packageid;
	private int orderState;
	GamePackageDetailActivity context;
	String MSISDN;
	String UA;
	String userId;
	String validateCode;

	public OrderGamePackageAsyncTask(GamePackageDetailActivity ctx, PackageDetailBean packageDetailBean,
			String packageid, String MSISDN, String UA, String userId, String validateCode) {
		this.context = ctx;
		this.packageDetailBean = packageDetailBean;
		this.packageid = packageid;
		this.orderState = packageDetailBean.getIsOrder();
		this.MSISDN = MSISDN;
		this.UA = UA;
		this.userId = userId;
		this.validateCode = validateCode;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		try {
			String s;
			if (validateCode == null) {
				L.d("RT", "代理订购流程");
				s = orderGamePackage(url);
			} else {
				L.d("RT", "一般请求订购流程");
				s = HttpConnect.getHttpString(url + "&MSISDN=" + MSISDN + "&validatecode=" + validateCode);
			}
			L.d("RT", "order s:" + s);
			String result = "";
			result = XmlUtil.get(s, "code");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		L.d("RT", "order result:" + result);
		String s = "";
		if ("322".equals(packageid)) {
			// 如果属于畅游包,则进入畅游包的流程
			if (CommonUtil.canOrderPackage(packageDetailBean.getIsOrder())) {
				s = "如何订购";
			} else {
				s = "如何退订";
			}
		} else {
			// 如果不属于畅游包,进入普通流程
			if (CommonUtil.canOrderPackage(packageDetailBean.getIsOrder())) {
				s = "订购";
			} else {
				s = "退订";
			}
		}

		if (result.equals("0")) {
			if (!CommonUtil.canOrderPackage(packageDetailBean.getIsOrder())) {
				orderState = 2;
				context.showOrderState(orderState, s + "成功");
			} else {
				orderState = 1;
				context.showOrderState(orderState, s + "成功");
			}
			context.initData();
		} else {
			// context.initButton("", s);
			// L.d("dd", orderState + "");
			new checkOrderTask(context, s + "成功!", packageid, orderState, MSISDN, UA, userId).execute("");
		}

	}

	/**
	 * 请求订购/退订游戏包
	 * 
	 * @param str
	 *            请求串
	 * @return
	 * @throws Exception
	 */
	public String orderGamePackage(String str) throws Exception {
		StringBuffer buffer = new StringBuffer();
		InputStream is = null;
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		// Properties prop = System.getProperties();
		// prop.put("http.proxyHost", "10.0.0.200");
		// prop.put("http.proxyPort", 80);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		URL url = new URL(str);
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		EgameApplication application = (EgameApplication) context.getApplication();
		conn.setRequestProperty("x-up-calling-line-id", application.getPhoneNum() + "");
		conn.setRequestProperty("X-ClientType", "1");
		conn.connect();
		is = conn.getInputStream();
		bis = new BufferedInputStream(is, 1024);
		int i = -1;
		byte data[] = new byte[1024];
		while ((i = bis.read(data)) != -1) {
			out.write(data, 0, i);
		}
		buffer.append(new String(out.toByteArray(), "utf-8"));
		if (conn != null) {
			conn.disconnect();
		}
		// prop.remove("http.proxyHost");
		// prop.remove("http.proxyPort");
		return buffer.toString();
	}

}
