package com.egame.app.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.PreferenceUtil;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-28 下午02:06:20
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class SearchHotTask extends AsyncTask<String, Integer, String> {
	private Context mContext;

	public SearchHotTask(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			String s = HttpConnect.getHttpString(Urls.getSearchHotUrl(mContext));
			JSONObject obj = new JSONObject(s);
			JSONArray array = obj.getJSONArray("gameLabelList");
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				String label = json.optString("labelName");
				if (!TextUtils.isEmpty(label)) {
					if (label.length() != label.getBytes().length) {
						if (label.length() > 5)
							label.substring(0, 5);
					} else {
						if (label.length() > 8)
							label.substring(0, 8);
					}
					buf.append(label + "_");
				}
			}
			if (buf.length() > 0) {
				buf.deleteCharAt(buf.length() - 1);
				PreferenceUtil.setSearchLabel(mContext, buf.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
