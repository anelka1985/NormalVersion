/**
 * 
 */
package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.NoviceFindFriendActivity;
import com.egame.app.uis.NoviceUpPhotoActivity;
import com.egame.beans.NoviceBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @author LiuHan
 * 
 */
public class GetFindFriendAsyncTask extends AsyncTask<String, Integer, String> {
	/** 上下文 */
	private NoviceFindFriendActivity context;
	/** 用户ID */
	private String userid;
	/** 用户年龄 */
	private int age;
	/** 用户所在的省 */
	private String province;
	private String city;
	private int index = 0;
	private int grender = 1;

	/**
	 * @param context
	 *            上下文
	 * @param age
	 *            用户年龄
	 * @param province
	 *            用户所在的省F
	 * @param userid
	 *            用户ID
	 */
	public GetFindFriendAsyncTask(NoviceFindFriendActivity context, int age, String province, String city,String userid, int index, int grend) {
		this.context = context;
		this.userid = userid;
		this.age = age;
		this.province = province;
		this.city = city;
		this.index = index;
		this.grender = grend;
	}

	/**
	 * 异步任务完成后的流程控制
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if ("exception".equals(result)) {
			Toast.makeText(context, this.context.getResources().getString(R.string.egame_net_error), Toast.LENGTH_SHORT).show();
			context.updateLoading(1, index);
		} else {
			try {
				context.updateLoading(0, index);
				List<NoviceBean> list = new ArrayList<NoviceBean>();
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if (0 == jobj.optInt("resultcode", 1)) {
					if (0 == json.optInt("totalRecored", -1)) {
						// 没有找到好友 进入传头像界面
						Intent intent = new Intent(this.context, NoviceUpPhotoActivity.class);
						this.context.startActivity(intent);
						this.context.finish();
					} else {
						// 列表获取成功
						JSONArray array = json.getJSONArray("searchFriendList");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsons = array.getJSONObject(i);
							NoviceBean fb = new NoviceBean(jsons, index);
							list.add(fb);
						}
						if (list.size() > 0) {
							this.context.loadFriendList(list, index);
						} else {
							Intent intent = new Intent(this.context, NoviceUpPhotoActivity.class);
							this.context.startActivity(intent);
							this.context.finish();
						}
					}

				} else {
					context.updateLoading(1, index);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(context, context.getResources().getString(R.string.egame_json_error), Toast.LENGTH_SHORT);
				this.context.finish();
			}
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			return HttpConnect.getHttpString(Urls.getFindFriendUrl(context, age, province, city,userid, index, grender), 30000);
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

}
