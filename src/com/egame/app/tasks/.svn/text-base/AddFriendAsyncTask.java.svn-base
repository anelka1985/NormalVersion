/**
 * 
 */
package com.egame.app.tasks;

import java.util.List;

import weibo4android.org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.adapters.NoviceAdapters;
import com.egame.app.uis.NoviceFindFriendActivity;
import com.egame.beans.NoviceBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * @author LiuHan
 * 
 */
public class AddFriendAsyncTask extends AsyncTask<String, Integer, String> {

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		this.pDialog.dismiss();
		if ("exception".equals(result)) {
			Toast.makeText(
					context,
					this.context.getResources().getString(
							R.string.egame_net_error), Toast.LENGTH_SHORT)
					.show();
		} else {
			try {
				JSONObject json = new JSONObject(result);
				JSONObject jobj = json.getJSONObject("result");
				if ("0".equals(jobj.getString("resultcode"))) {
					// 加好友成功 改变 按钮的状态
					list.get(position).setAddSuccess(true);
					nAdapter.notifyDataSetChanged();
					NoviceFindFriendActivity.instance.updateNextButton();
				}
				Toast.makeText(context, jobj.getString("resultmsg"),
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(
						context,
						this.context.getResources().getString(
								R.string.egame_json_error), Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			return HttpConnect.getHttpString(Urls.getAddFriendUrl(
					this.friendId, this.userId, this.context));
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
	}

	public AddFriendAsyncTask(Context context, NoviceAdapters nAdapter,
			long friendId, String userId, int position, List<NoviceBean> list) {
		this.context = context;
		this.friendId = friendId;
		this.userId = userId;
		this.position = position;
		this.list = list;
		this.nAdapter = nAdapter;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(context.getResources().getString(
				R.string.egame_progress_dialog));
		pDialog.show();
	}

	private Context context;
	private long friendId;
	private String userId;
	private ProgressDialog pDialog;
	private int position = 0;
	List<NoviceBean> list;
	NoviceAdapters nAdapter;

}
