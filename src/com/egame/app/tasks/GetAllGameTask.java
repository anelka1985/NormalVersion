package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.egame.app.EgameApplication;
import com.egame.app.uis.SearchActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

// 异步任务(模糊查询联想关键词)
public class GetAllGameTask extends AsyncTask<String, Integer, List<String>> {

	Activity context;

	EgameApplication application;

	public GetAllGameTask(Activity context) {
		this.context = context;
		this.application = (EgameApplication) context.getApplication();
	}

	@Override
	protected List<String> doInBackground(String... params) {
		try {
			String s = HttpConnect.getHttpString(Urls.getQueyrListByGameName(context, application.getUA()));
			JSONObject obj = new JSONObject(s);
			List<String> autoText = new ArrayList<String>();
			if ("0".equals(obj.getJSONObject("result").getString("resultcode"))) {
				JSONArray array = obj.getJSONArray("values");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObj = array.getJSONObject(i);
//					Log.i("info", jsonObj.getString("wordValue"));
					autoText.add(jsonObj.getString("wordValue"));
				}
			}
			if(application.getAutoText().size() == 0){
				application.getAutoText().addAll(autoText);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}