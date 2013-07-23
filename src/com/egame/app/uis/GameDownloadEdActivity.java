package com.egame.app.uis;

import java.util.ArrayList;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.adapters.GameDownLoadEdAdapter;
import com.egame.app.services.DBService;
import com.egame.beans.GameListBean;
import com.egame.utils.ui.GameSortByChar;
import com.egame.utils.ui.ImageUtils;
import com.eshore.network.stat.NetStat;

public class GameDownloadEdActivity extends ExpandableListActivity {
	List<String> group = new ArrayList<String>(); // 组列表

	/**
	 * 游戏列表实体列表
	 */

	List<List<GameListBean>> child = new ArrayList<List<GameListBean>>(); // 子列表

	GameDownLoadEdAdapter adapter; // 数据适配器

	DBService dbService;

	PackageManager pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_gamedownloaded_sort_expand);
		dbService = new DBService(this);
		dbService.open();
		pm = getPackageManager();
		EgameApplication.Instance().addActivity(this);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		String isNull = child.get(groupPosition).get(childPosition).getActionFlag();
		for (int i = 0; i < child.size(); i++) {
			for (int j = 0; j < child.get(i).size(); j++) {
				child.get(i).get(j).setActionFlag(null);
			}

		}
		if (null == isNull) {
			child.get(groupPosition).get(childPosition).setActionFlag("true");
		}
		adapter.notifyDataSetChanged();
		if (groupPosition == group.size() - 1 && childPosition == child.get(groupPosition).size() - 1) {
			parent.setSelection(100);
		}
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}

	protected void getInstalledGame() {
		// SD卡读取已安装文件
		new InstalledGameTask().execute();
	}

	@Override
	protected void onDestroy() {
		dbService.close();
		super.onDestroy();
	}

	private int sumGameCount(List<List<GameListBean>> child) {
		int count = 0;
		for (int i = 0; i < child.size(); i++) {
			count += child.get(i).size();
		}
		return count;
	}

	/**
	 * 
	 * 类说明：获取已安装游戏
	 * 
	 * @创建时间 2012-1-31
	 * @创建人： 王先云
	 * @邮箱：wangxy@gzylxx.com
	 */
	class InstalledGameTask extends AsyncTask<String, Integer, List<GameListBean>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<GameListBean> doInBackground(String... params) {
			List<GameListBean> list = new ArrayList<GameListBean>();
			List<String[]> installedPackageList = getInstalledList();
			if (installedPackageList.size() == 0)
				return list;
			List<PackageInfo> packages = pm.getInstalledPackages(0);
			GameListBean tempGameListBean = null;
			for (int i = installedPackageList.size() - 1; i >= 0; i--) {
				PackageInfo packageInfo = getPackageInfoByName(packages, installedPackageList.get(i)[1]);
				if (packageInfo != null) {
					Drawable appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());

					tempGameListBean = new GameListBean();
					tempGameListBean.setGameName(installedPackageList.get(i)[0]);
					tempGameListBean.setPackageName(installedPackageList.get(i)[1]);
					tempGameListBean.setFileSize(Integer.parseInt(installedPackageList.get(i)[2]));
					tempGameListBean.setGameId(Integer.parseInt(installedPackageList.get(i)[3]));
					tempGameListBean.setClassName(installedPackageList.get(i)[4]);
					tempGameListBean.setIcon(new BitmapDrawable(ImageUtils.getRoundedCornerBitmap(
							ImageUtils.resizeImage(ImageUtils.drawableToBitmap(appIcon), 90, 90), 10)));
					list.add(tempGameListBean);
				}
			}
			return list;
		}

		private PackageInfo getPackageInfoByName(List<PackageInfo> packages, String name) {
			for (int i = 0; i < packages.size(); i++) {
				PackageInfo packageInfo = packages.get(i);
				if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && packageInfo.packageName.equals(name)) {
					return packageInfo;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<GameListBean> result) {
			super.onPostExecute(result);
			group.clear();
			child.clear();
			GameSortByChar(result);
			// 设置安装游戏的统计
			TextView gameDownloadedHint = (TextView) findViewById(R.id.gameDownloadedHint);
			int count = sumGameCount(child);
			if (count == 0) {
				gameDownloadedHint.setPadding(0, 74, 0, 0);
				gameDownloadedHint.setText(R.string.egame_nmyyazdyx);
				getExpandableListView().setVisibility(View.GONE);
			} else {
				gameDownloadedHint.setPadding(0, 7, 0, 0);
				gameDownloadedHint.setText("已安装游戏：" + count + "个");
				getExpandableListView().setVisibility(View.VISIBLE);
			}
			adapter = new GameDownLoadEdAdapter(GameDownloadEdActivity.this, null, group, child);
			ExpandableListView expandableListView = getExpandableListView();
			expandableListView.setAdapter(adapter);
			expandableListView.setGroupIndicator(null);
			expandableListView.setCacheColorHint(0); // 设置拖动列表的时候防止出现黑色背景

			int groupCount = child.size();
			for (int i = 0; i < groupCount; i++) {
				getExpandableListView().expandGroup(i);
			}
			// 设置不能点击父节点
			expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

				@Override
				public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
					return true;
				}
			});
			expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
				
				@Override
				public void onGroupCollapse(int groupPosition) {
					for(int i=0;i<child.size();i++){
						getExpandableListView().expandGroup(i);
					}
				}
			});
		}

		private void GameSortByChar(List<GameListBean> result) {
			if (result.size() == 0) {
				return;
			}
			String name = null;
			child.add(0, new ArrayList<GameListBean>());
			group.add("A");
			child.add(1, new ArrayList<GameListBean>());
			group.add("B");
			child.add(2, new ArrayList<GameListBean>());
			group.add("C");
			child.add(3, new ArrayList<GameListBean>());
			group.add("D");
			child.add(4, new ArrayList<GameListBean>());
			group.add("E");
			child.add(5, new ArrayList<GameListBean>());
			group.add("F");
			child.add(6, new ArrayList<GameListBean>());
			group.add("G");
			child.add(7, new ArrayList<GameListBean>());
			group.add("H");
			child.add(8, new ArrayList<GameListBean>());
			group.add("I");
			child.add(9, new ArrayList<GameListBean>());
			group.add("J");
			child.add(10, new ArrayList<GameListBean>());
			group.add("K");
			child.add(11, new ArrayList<GameListBean>());
			group.add("L");
			child.add(12, new ArrayList<GameListBean>());
			group.add("M");
			child.add(13, new ArrayList<GameListBean>());
			group.add("N");
			child.add(14, new ArrayList<GameListBean>());
			group.add("O");
			child.add(15, new ArrayList<GameListBean>());
			group.add("P");
			child.add(16, new ArrayList<GameListBean>());
			group.add("Q");
			child.add(17, new ArrayList<GameListBean>());
			group.add("R");
			child.add(18, new ArrayList<GameListBean>());
			group.add("S");
			child.add(19, new ArrayList<GameListBean>());
			group.add("T");
			child.add(20, new ArrayList<GameListBean>());
			group.add("U");
			child.add(21, new ArrayList<GameListBean>());
			group.add("V");
			child.add(22, new ArrayList<GameListBean>());
			group.add("W");
			child.add(23, new ArrayList<GameListBean>());
			group.add("X");
			child.add(24, new ArrayList<GameListBean>());
			group.add("Y");
			child.add(25, new ArrayList<GameListBean>());
			group.add("Z");
			for (int i = 0; i < result.size(); i++) {
				name = result.get(i).getGameName();
				char tempChar = GameSortByChar.getInstance().String2Alpha(name.substring(0, 1));
				switch (tempChar) {
				case 'A':
					child.get(0).add(result.get(i));
					break;
				case 'B':
					child.get(1).add(result.get(i));
					break;
				case 'C':
					child.get(2).add(result.get(i));
					break;
				case 'D':
					child.get(3).add(result.get(i));
					break;
				case 'E':
					child.get(4).add(result.get(i));
					break;
				case 'F':
					child.get(5).add(result.get(i));
					break;
				case 'G':
					child.get(6).add(result.get(i));
					break;
				case 'H':
					child.get(7).add(result.get(i));
					break;
				case 'I':
					child.get(8).add(result.get(i));
					break;
				case 'J':
					child.get(9).add(result.get(i));
					break;
				case 'K':
					child.get(10).add(result.get(i));
					break;
				case 'L':
					child.get(11).add(result.get(i));
					break;
				case 'M':
					child.get(12).add(result.get(i));
					break;
				case 'N':
					child.get(13).add(result.get(i));
					break;
				case 'O':
					child.get(14).add(result.get(i));
					break;
				case 'P':
					child.get(15).add(result.get(i));
					break;
				case 'Q':
					child.get(16).add(result.get(i));
					break;
				case 'R':
					child.get(17).add(result.get(i));
					break;
				case 'S':
					child.get(18).add(result.get(i));
					break;
				case 'T':
					child.get(19).add(result.get(i));
					break;
				case 'U':
					child.get(20).add(result.get(i));
					break;
				case 'V':
					child.get(21).add(result.get(i));
					break;
				case 'W':
					child.get(22).add(result.get(i));
					break;
				case 'X':
					child.get(23).add(result.get(i));
					break;
				case 'Y':
					child.get(24).add(result.get(i));
					break;
				case 'Z':
					child.get(25).add(result.get(i));
					break;
				default:
					child.get(0).add(result.get(i));
					break;
				}
			}

			for (int i = 0; i < child.size(); i++) {
				if (child.get(i).size() == 0) {
					child.remove(i);
					group.remove(i);
					i--;
				}
			}
		}
	}

	/**
	 * 获取已安装游戏列表
	 * 
	 * @return
	 */
	public List<String[]> getInstalledList() {
		List<String[]> list = new ArrayList<String[]>();
		Cursor cursor = null;
		try {
			cursor = dbService.getGameInstalledTest();
			if (cursor.moveToFirst()) {
				do {
					String name = cursor.getString(cursor
							.getColumnIndex(DBService.KEY_GAMENAME));
					String packagename = cursor.getString(cursor
							.getColumnIndex(DBService.KEY_PACKAGENAME));
					String totalsize = cursor.getString(cursor
							.getColumnIndex(DBService.KEY_TOTALSIZE));
					String serviceid = cursor.getString(cursor
							.getColumnIndex(DBService.KEY_SERVICEID));
					String sortName = cursor.getString(cursor
							.getColumnIndex(DBService.KEY_SORT_NAME));
					list.add(new String[] { name, packagename, totalsize,
							serviceid, sortName });
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}

		}
		return list;
	}

	@Override
	protected void onResume() {
		getInstalledGame();
		super.onResume();
		NetStat.onResumePage();
	}

	/**
     * 
     */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("GameDownloadEdActivity");
	}

}
