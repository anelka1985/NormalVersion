/**\
 * 
 */
package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.NovicePrimaryActivity;
import com.egame.beans.CityBean;
import com.egame.beans.ProvinceBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.ToastUtil;

/**
 * @author LiuHan
 * 
 */
public class CityProvinceTask extends AsyncTask<String, Integer, String> {
	/** 上下文 */
	private NovicePrimaryActivity context;
	private ProgressDialog pDialog;

	public CityProvinceTask(NovicePrimaryActivity context) {
		this.context = context;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(this.context.getResources().getString(R.string.egame_progress_dialog));
		pDialog.show();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		if ("exception".equals(result)) {
			ToastUtil.show(context, context.getResources().getString(R.string.egame_net_error));
		} else {
			try {
				List<ProvinceBean> list = new ArrayList<ProvinceBean>();
				JSONObject json = new JSONObject(result);
				JSONArray provinceArray = json.getJSONArray("provinceAndCityList");
				for (int i = 0; i < provinceArray.length(); i++) {
					List<CityBean> list1 = new ArrayList<CityBean>();
					JSONObject obj = provinceArray.getJSONObject(i);
					JSONArray cityArray = obj.getJSONArray("citys");
					for (int j = 0; j < cityArray.length(); j++) {
						JSONObject objs = cityArray.getJSONObject(j);
						CityBean beans = new CityBean(objs);
						list1.add(beans);
					}
					ProvinceBean bean = new ProvinceBean(obj, list1);
					list.add(bean);
				}
				context.showProvinceCityData(list);

			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.show(context, context.getResources().getString(R.string.egame_json_error));
			}
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getProvinceCitUrl(context, "0"));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}

	}

}
