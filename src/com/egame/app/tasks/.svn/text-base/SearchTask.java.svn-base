package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.uis.SearchActivity;
import com.egame.beans.GameListBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.InterceptString;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-28 下午02:06:20
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class SearchTask extends AsyncTask<String, Integer, List<GameListBean>> {
	private SearchActivity mActivity;
	// private int mSearchType;
	private String mSearchKey;
	private EgameApplication application;
	public static final String KEY_GAME_LIST = "gameListBySearch";

	public SearchTask(SearchActivity activity, String searchKey) {
		application = (EgameApplication) activity.getApplication();
		mActivity = activity;
		// mSearchType = searchType;
		// // 界面中的定义和接口定义有冲突，这里强制转换一下
		// if (searchType == 2) {
		// mSearchType = 3;
		// } else if (searchType == 3) {
		// mSearchType = 2;
		// }
		mSearchKey = searchKey;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.showProgress(R.string.egame_searchWait);
	}

	@Override
	protected List<GameListBean> doInBackground(String... params) {
		try {
			List<GameListBean> result = new ArrayList<GameListBean>();
			String s = HttpConnect.getHttpString(Urls.getSearchAllUrl(mActivity, mSearchKey, application.getUA()));
			JSONObject obj = new JSONObject(s);
			if (obj.has(KEY_GAME_LIST)) {
				JSONArray array = obj.getJSONArray(KEY_GAME_LIST);
				for (int i = 0; i < array.length(); i++) {
					JSONObject json = array.getJSONObject(i);
					GameListBean bean = new GameListBean(json);
					// switch (mSearchType) {
					// case 1:
					bean.setSpanName(InterceptString.getSpannableString(bean.getGameName(), mSearchKey));
					bean.setSpanInfo(InterceptString.getSpannableString(bean.getProvider(), mSearchKey));
					// break;
					// case 2:
					// bean.setSpanName(InterceptString.getSpannableString(bean.getGameName(),
					// mSearchKey));
					// bean.setSpanInfo(InterceptString.getSpannableString(bean.getProvider(),
					// mSearchKey));
					// break;
					// case 3:
					// bean.setSpanName(InterceptString.getSpannableString(bean.getGameName(),
					// mSearchKey));
					// bean.setSpanInfo(InterceptString.getSpannableString(bean.getProvider(),
					// mSearchKey));
					// break;
					// }
					result.add(bean);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<GameListBean> result) {
		super.onPostExecute(result);
		mActivity.hideProgress();
		if (mActivity.isFinishing())
			return;
		if (result == null) {
			Toast.makeText(mActivity, R.string.egame_net_error, Toast.LENGTH_SHORT).show();
		} else {
			mActivity.searchCompleted(result);
		}
	}

}
