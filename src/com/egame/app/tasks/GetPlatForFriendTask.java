/**
 * 
 */
package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.RecomPlatFormActivity;
import com.egame.beans.ShareFriendBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.ToastUtil;

/**
 * @author LiuHan
 * 
 */
public class GetPlatForFriendTask extends AsyncTask<String, Integer, String> {
	private RecomPlatFormActivity context;
	/** 上下文 */
	private int index = 0;

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if ("exception".equals(result) || "".equals(result)) {
			ToastUtil.show(context,
					context.getResources().getString(R.string.egame_net_error));
			context.updateLoading(1);
		} else {
			try {
				context.updateLoading(0);
				List<ShareFriendBean> list1 = new ArrayList<ShareFriendBean>();
				JSONObject json = new JSONObject(result);
				JSONArray hobbyArray = json.getJSONArray("friendList");
				for (int i = 0; i < hobbyArray.length(); i++) {
					JSONObject obj = hobbyArray.getJSONObject(i);
					ShareFriendBean hBean = new ShareFriendBean(json,obj, index);
					list1.add(hBean);
				}

				if (list1.size() == 0) {
					// 没有找到好友 进入传头像界面
					Toast.makeText(context, "还没有社区好友哦!", Toast.LENGTH_SHORT)
							.show();
					context.finish();
				} else {
					this.context.loadFriendData(list1, index);
				}

			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.show(
						context,
						context.getResources().getString(
								R.string.egame_json_error));
			}
		}
	}

	public GetPlatForFriendTask(RecomPlatFormActivity context, int index) {
		this.context = context;
		this.index = index;
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getFriendListUrl(arg0[0],
					index, this.context));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

}
