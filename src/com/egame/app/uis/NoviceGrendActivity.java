package com.egame.app.uis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.tasks.NoviceCommitModifyTask;
import com.egame.beans.HobbyBean;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.ToastUtil;
import com.eshore.network.stat.NetStat;

/**
 * 描述：选择性别和游戏类型界面类
 * 
 * @author LiuHan
 * @version 1.0
 */
public class NoviceGrendActivity extends Activity implements OnClickListener {
	public static NoviceGrendActivity instance;
	/** 返回按钮 */
	private TextView mGrendBack;
	/** 游戏类型显示列表 */
	private GridView mGameGridview;
	/** 用户头像UI */
	private ImageView mIconMan, mIconMade, mIconAll;
//	private TextView tvMan, tvMade, tvAll;
	private LinearLayout llMan,llMade,llAll;
	private ImageView pointMan,pointMade,pointAll;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private List<HobbyBean> listHobby = new ArrayList<HobbyBean>();
	/** 提交UI布局 */
	private RelativeLayout mCommit;
	private int mGrend = -1;
	private String gametype = "";
	private int position = -1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_novice_grend);
		instance = this;
		// 取得UI控件的引用
		initControlUI();
		// 读取游戏的类型
		new GetHobbyTask(NoviceGrendActivity.this).execute("");
	}

	/**
	 * 取得UI控件的引用
	 */
	private void initControlUI() {
		mCommit = (RelativeLayout) this.findViewById(R.id.m_novice_commit);
		mCommit.setOnClickListener(this);
		mGrendBack = (TextView) this.findViewById(R.id.m_grend_back);
		mGrendBack.setOnClickListener(this);
		mGameGridview = (GridView) this.findViewById(R.id.m_game_gridview);
		mIconMan = (ImageView) this.findViewById(R.id.m_icon_man);
//		mIconMan.setOnClickListener(this);
		mIconMade = (ImageView) this.findViewById(R.id.m_icon_made);
//		mIconMade.setOnClickListener(this);
		mIconAll = (ImageView) this.findViewById(R.id.m_icon_all);
//		mIconAll.setOnClickListener(this);
		llMan = (LinearLayout) findViewById(R.id.llMan);
		llMade = (LinearLayout) findViewById(R.id.llMade);
		llAll = (LinearLayout) findViewById(R.id.llAll);
		pointMade = (ImageView) findViewById(R.id.pointMade);
		pointMan = (ImageView) findViewById(R.id.pointMan);
		pointAll = (ImageView) findViewById(R.id.pointAll);
//		tvMan = (TextView) findViewById(R.id.m_btn_man);
//		tvMan.setOnClickListener(this);
//		tvMade = (TextView) findViewById(R.id.m_btn_made);
//		tvMade.setOnClickListener(this);
//		tvAll = (TextView) this.findViewById(R.id.m_btn_all);
//		tvAll.setOnClickListener(this);
		llMade.setOnClickListener(this);
		llMan.setOnClickListener(this);
		llAll.setOnClickListener(this);
		mGameGridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (position == arg2) {
					// 单击同一个 取消选择
					if ("true".equals(list.get(position).get("isselect").toString())) {
						list.get(position).put("img", R.drawable.egame_select_contactsoff);
						list.get(position).put("isselect", "false");
					} else {
						list.get(position).put("img", R.drawable.egame_lselect_contactson);
						list.get(position).put("isselect", "true");
					}
					adapter.notifyDataSetChanged();
				} else {
					position = arg2;
					for (int i = 0; i < list.size(); i++) {
						if (i == arg2) {
							list.get(i).put("isselect", "true");
							list.get(i).put("img", R.drawable.egame_lselect_contactson);
							gametype = list.get(i).get("info").toString();
						} else {
							list.get(i).put("img", R.drawable.egame_select_contactsoff);
							list.get(i).put("isselect", "false");
						}
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
		TextView mNoviceName = (TextView) this.findViewById(R.id.m_novice_name);
		LoginDataStoreUtil.User mBean = LoginDataStoreUtil.fetchUser(NoviceGrendActivity.this, LoginDataStoreUtil.LOG_FILE_NAME);
		mNoviceName.setText(mBean.getNickName() + "，欢迎来到爱游戏社区");
	}

	/**
	 * 初始化UI的状态
	 */
	private void initUIStatus() {
		// 性别
		if ("男生".equals(this.getIntent().getBundleExtra("bundle").get("grend"))) {
			// 当前选中的是男生
			changeSelect("man");
		} else if ("女生".equals(this.getIntent().getBundleExtra("bundle").get("grend"))) {
			changeSelect("made");
		} else if ("所有人".equals(this.getIntent().getBundleExtra("bundle").get("grend"))) {
			changeSelect("all");
		}
		// 爱好
		for (int i = 0; i < list.size(); i++) {
			if ((list.get(i).get("info").equals(this.getIntent().getBundleExtra("bundle").getString("type")))) {
				list.get(i).put("img", R.drawable.egame_lselect_contactson);
				//
				position = i;
				this.gametype = this.getIntent().getBundleExtra("bundle").getString("type");
				list.get(position).put("isselect", "true");
			}
		}

	}

	/**
	 * 相关按钮的单击事件处理函数
	 */

	public void onClick(View v) {
		if (v == mGrendBack) {
			NoviceGrendActivity.this.finish();
		} else if (v == llMan) {
			changeSelect("man");
		} else if (v == llMade) {
			changeSelect("made");
		} else if (v == mCommit) {
			commitEvent();
		} else if (v == llAll) {
			changeSelect("all");
		}
	}

	private void commitEvent() {
		int positions = position;
		for (int i = 0; i < list.size(); i++) {
			if ("true".equals(list.get(i).get("isselect"))) {
				Log.i("list.get(isselect", list.get(i).get("isselect") + "i" + i);
				positions = i;
			}
		}
		if (mGrend == -1) {
			Toast.makeText(NoviceGrendActivity.this, "请选择一个性别哦", Toast.LENGTH_SHORT).show();
		} else {
			if (-1 != positions) {
				// 提交爱好的修改
				new NoviceCommitModifyTask(NoviceGrendActivity.this, LoginDataStoreUtil.fetchUser(NoviceGrendActivity.this, LoginDataStoreUtil.LOG_FILE_NAME)
						.getUserId(), 0, 0, "", "", "", "", listHobby.get(positions).getInterestId(), mGrend, "hobby").execute("");
			} else {
				Toast.makeText(NoviceGrendActivity.this, "请选择一下喜欢的游戏类型", Toast.LENGTH_SHORT).show();
			}

		}
	}

	public void backActivity() {
		Intent intent = new Intent();
		String str = "";
		if (mGrend == 1) {
			str = "男生";
		} else if (mGrend == 2) {
			str = "女生";
		} else if (mGrend == 3) {
			str = "所有人";
		}
		Bundle bundle = NovicePrimaryActivity.createBundle(str, gametype, position);
		intent.putExtra("bundle", bundle);
		setResult(RESULT_OK, intent);
		NoviceGrendActivity.this.finish();
	}

	/**
	 * 取得游戏类型名称的数据
	 */
	private List<Map<String, Object>> getData(List<HobbyBean> lists) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < lists.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("info", lists.get(i).getInterest());
			map.put("img", R.drawable.egame_select_contactsoff);
			map.put("isselect", "false");
			list.add(map);
		}
		return list;
	}

	/**
	 * 更改图片的背景
	 * 
	 * @param grend
	 */
	public void changeSelect(String grend) {
		if ("man".equals(grend)) {
			mGrend = 1;
			mIconMan.setBackgroundResource(R.drawable.egame_people_man_on);
			pointMan.setBackgroundResource(R.drawable.egame_radiobutton_select);
			mIconMade.setBackgroundResource(R.drawable.egame_people_woman_off);
			pointMade.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
			mIconAll.setBackgroundResource(R.drawable.egame_people_all_off);
			pointAll.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
		} else if ("made".equals(grend)) {
			mGrend = 2;
			mIconMade.setBackgroundResource(R.drawable.egame_people_woman_on);
			pointMade.setBackgroundResource(R.drawable.egame_radiobutton_select);
			mIconMan.setBackgroundResource(R.drawable.egame_people_man_off);
			pointMan.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
			mIconAll.setBackgroundResource(R.drawable.egame_people_all_off);
			pointAll.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
		} else {
			mGrend = 3;
			mIconAll.setBackgroundResource(R.drawable.egame_people_all_on);
			pointAll.setBackgroundResource(R.drawable.egame_radiobutton_select);
			mIconMan.setBackgroundResource(R.drawable.egame_people_man_off);
			pointMan.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
			mIconMade.setBackgroundResource(R.drawable.egame_people_woman_off);
			pointMade.setBackgroundResource(R.drawable.egame_radiobutton_unselect);
		}
	}

	/**
	 * @param grend
	 *            性别
	 * @param type
	 *            类型
	 * @param userid
	 *            用户id
	 * @return
	 */
	public static Bundle getBundle(String grend, String type, String userid) {
		Bundle bundle = new Bundle();
		bundle.putString("grend", grend);
		bundle.putString("type", type);
		bundle.putString("userid", userid);
		return bundle;
	}

	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("NoviceGrendActivity");
	}

	/**
	 * @author LiuHan
	 * 
	 */
	public class GetHobbyTask extends AsyncTask<String, Integer, String> {
		/** 上下文 */
		private NoviceGrendActivity context;
		private ProgressDialog pDialog;

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if ("exception".equals(result)) {
				ToastUtil.show(context, context.getResources().getString(R.string.egame_net_error));
				NoviceGrendActivity.this.finish();
			} else {

				try {
					List<HobbyBean> list1 = new ArrayList<HobbyBean>();
					JSONObject json = new JSONObject(result);
					JSONArray hobbyArray = json.getJSONArray("hobbyList");
					for (int i = 0; i < hobbyArray.length(); i++) {
						JSONObject obj = hobbyArray.getJSONObject(i);
						HobbyBean hBean = new HobbyBean(obj);
						list1.add(hBean);
					}
					listHobby = list1;
					// 填充游戏类型列表
					list = getData(list1);
					// 更改UI显示状态
					adapter = new SimpleAdapter(NoviceGrendActivity.this, list, R.layout.egame_game_type_select_item, new String[] { "img", "info" },
							new int[] { R.id.m_type_icon, R.id.m_type_name });
					mGameGridview.setAdapter(adapter);
					initUIStatus();

				} catch (Exception e) {
					e.printStackTrace();
					// ToastUtil.show(
					// context,
					// context.getResources().getString(
					// R.string.egame_json_error));
				}
			}
		}

		public GetHobbyTask(NoviceGrendActivity context) {
			this.context = context;
			pDialog = new ProgressDialog(context);
			pDialog.setMessage(this.context.getResources().getString(R.string.egame_progress_dialog));
			pDialog.show();
		}

		protected String doInBackground(String... arg0) {
			try {
				return HttpConnect.getHttpString(Urls.getHobbyUrl(NoviceGrendActivity.this));
			} catch (Exception e) {
				e.printStackTrace();
				return "exception";
			}
		}
	}
}
