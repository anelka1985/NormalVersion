package com.egame.weibos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout.LayoutParams;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.app.tasks.CommenttoFriendTask;
import com.egame.app.tasks.RenrenGetGameIconTask;
import com.egame.app.tasks.ShareNumberCommitTask;
import com.egame.app.uis.GamesDetailActivity;
import com.egame.app.uis.RecomPlatFormActivity;
import com.egame.app.uis.RecommContactsActivity;
import com.egame.app.uis.TencentShareActivity;
import com.egame.beans.GameInfo;
import com.egame.config.Const;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.common.PreferenceUtil;
import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.view.RenrenAuthListener;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.OAuthClient;

/**
 * 描述:分享窗口界面
 * 
 * @author LiuHan
 * @version 1.0 Create on:2012/1/6
 */
public class ShareWindows {
	/** 上下文 */
	private static Context context;
	/** 分享的内容 */
	private String content;
	/** 装载游戏信息的bean */
	private GameInfo gameInfo;
	/** 用来提示人人网的认证状态 */
	private Handler handler = new Handler();
	/** 声明Renren对象 */
	private Renren renren;
	public static OAuth oauth;
	public static OAuthClient auth;
	/** 存放腾讯token 和 secret 的 数组 */
	private static String[] oauth_token_array;
	private static String oauth_token = "";
	private static String oauth_token_secret = "";
	private Dialog dialog;
	private String shareType = "client";
	private Intent intent = new Intent(Intent.ACTION_SEND);
	private String userid;
	/**
	 * 构造函数//该函数参数不去 待确认
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            分享的文字内容 在推荐页面（非游戏的分享）时不为空
	 * @param gameInfo
	 *            存储和游戏相关的数据 在非遊戲的分享時 為null
	 */
	public ShareWindows(Activity context, String content, GameInfo gameInfo,String userid) {
		ShareWindows.context = context;
		this.content = content;
		this.gameInfo = gameInfo;
		shareType = PreferenceUtil.fetchType(context);
		this.userid = userid;
	}

	/**
	 * 打开推荐方式对话框
	 */
	public void show() {
		dialog = new Dialog(context);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutInflater factory = LayoutInflater.from(context);
		final View views = factory.inflate(R.layout.egame_recomm_dialog, null);
		ListView listView = (ListView) views.findViewById(R.id.m_recommway_list);
		SimpleAdapter adapter = new SimpleAdapter(context, getData(), R.layout.egame_recomm_dialog_item, new String[] { "share_icon", "share_name" }, new int[] {
				R.id.m_recomway_icon, R.id.m_recomway_name });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());

		dialog.setContentView(views, params);
		if(context instanceof GamesDetailActivity){
		dialog.setTitle("请选择分享方式");}
		else{
			dialog.setTitle("请选择推荐方式");
		}
		dialog.show();
	}

	/**
	 * 取得数据
	 */
	private List<Map<String, Object>> getData() {
		/** 推荐方式的名称 */
		final CharSequence[] recomWay = context.getResources().getStringArray(R.array.egame_share);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		// 通讯录
		map = new HashMap<String, Object>();
		map.put("share_icon", R.drawable.egame_contacts);
		map.put("share_name", recomWay[0]);
		list.add(map);
		// 更多
		map = new HashMap<String, Object>();
		map.put("share_icon", R.drawable.egame_more);
		map.put("share_name", recomWay[4]);
		list.add(map);
//		if ("game".equals(shareType) && !LoginDataStoreUtil.NULL_VALUE.equals(LoginDataStoreUtil.fetchUser(context, LoginDataStoreUtil.LOG_FILE_NAME).getUserId())) {
//			// 社区好友
//			map = new HashMap<String, Object>();
//			map.put("share_icon", R.drawable.egame_friend);
//			map.put("share_name", recomWay[5]);
//			list.add(map);
//		}
//		map = new HashMap<String, Object>();
//		String brand = android.os.Build.BRAND;
//		if("samsung".equals(brand))
//		{
//
//			// 腾讯微博
//			map = new HashMap<String, Object>();
//			map.put("share_icon", R.drawable.egame_microblogging);
//			map.put("share_name", recomWay[2]);
//			list.add(map);
//
//		}
//		else{
//			map.put("share_icon", R.drawable.egame_sina);
//			map.put("share_name", recomWay[3]);
//			list.add(map);
//			// 腾讯微博
//			map = new HashMap<String, Object>();
//			map.put("share_icon", R.drawable.egame_microblogging);
//			map.put("share_name", recomWay[2]);
//			list.add(map);
//			// 人人网
//			map = new HashMap<String, Object>();
//			map.put("share_icon", R.drawable.egame_renren);
//			map.put("share_name", recomWay[1]);
//			list.add(map);
//			// 更多
//			map = new HashMap<String, Object>();
//			map.put("share_icon", R.drawable.egame_more);
//			map.put("share_name", recomWay[4]);
//			list.add(map);
//		}
		// 新浪微博
		return list;
	}

	/**
	 * 列表事件的处理
	 * 
	 * @author LiuHan
	 * 
	 */
	class MyOnItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			dialog.dismiss();
			String brand = android.os.Build.BRAND;
//			if("samsung".equals(brand)){
//				switch (arg2) {
//				case 0:
//					// 推荐到通讯录
//						new CommenttoFriendTask(userid).execute("");
//					shareToContacts();
//					break;
//				case 1:
//					// 推荐到腾讯微博
//						new CommenttoFriendTask(userid).execute("");
//					shareToTencentMicroblog();
//					break;
////					shareToContacts();
////					break;
////				case 1:
////					// 分享到社区好友
////					shareToFriend();
////					break;
////				case 2:
////					// 推荐到新浪微博
////					if (null != gameInfo && null != gameInfo.getGameId() && !"".equals(gameInfo.getGameId())) {
////						new ShareNumberCommitTask("11",userid).execute("");
//////						Log.i("AAAA", "AAAA");
////					}
////					shareToSinaMicroblog();
////					break;
////				case 3:
////					// 推荐到腾讯微博
////					if (null != gameInfo && null != gameInfo.getGameId() && !"".equals(gameInfo.getGameId())) {
////						new ShareNumberCommitTask("22",userid).execute("");
////					}
////					shareToTencentMicroblog();
////					break;
////				case 4:
////					// 推荐到人人网
////					if (null != gameInfo && null != gameInfo.getGameId() && !"".equals(gameInfo.getGameId())) {
////						new ShareNumberCommitTask("33",userid).execute("");
////					}
////					shareToRenren();
////					break;
////				case 5:
////					// 更多分享
////					if (null != gameInfo && null != gameInfo.getGameId() && !"".equals(gameInfo.getGameId())) {
////						new ShareNumberCommitTask("55",userid).execute("");
////					}
////					shareToMore();
////					break;
//				}
//			} else {
				switch (arg2) {
				case 0:
					// 推荐到通讯录
						new CommenttoFriendTask(userid).execute("");
					shareToContacts();
					break;
//				case 1:
//					// 推荐到新浪微博
//						new CommenttoFriendTask(userid).execute("");
////						Log.i("BBBBBB", "BBBBBB="+userid);
//					shareToSinaMicroblog();
//					break;
//				case 2:
//					// 推荐到腾讯微博
//						new CommenttoFriendTask(userid).execute("");
//					shareToTencentMicroblog();
//					break;
//				case 3:
//					// 推荐到人人网
//						new CommenttoFriendTask(userid).execute("");
//					shareToRenren();
//					break;
				case 1:
					// 更多分享
						new CommenttoFriendTask(userid).execute("");
					shareToMore();
					break;
				}
//			}

		}

		/**
		 * 分享到社区好友
		 */
		private void shareToFriend() {
			Intent intent = new Intent(context, RecomPlatFormActivity.class);
			intent.putExtra("gameId", gameInfo.getGameId());
			context.startActivity(intent);
		}

	}

	/**
	 * 分享到通讯录
	 */
	private void shareToContacts() {
		Intent intent = new Intent(context, RecommContactsActivity.class);
		if ("game".equals(this.shareType) && null != gameInfo) {
			intent.putExtra("gameId", gameInfo.getGameId());
			intent.putExtra("gameName", gameInfo.getGameName());
		}
		context.startActivity(intent);
	}

	/**
	 * 分享到人人网
	 */
	private void shareToRenren() {
		boolean isHad = false;
		isHad = startAimApplication(getAppListInfo(), "人人", TencentMicroblogUtil.RENREN_BLOG);
		if (!isHad) {
			renren = RenrenUtil.initRenren(context);
			if (renren.isSessionKeyValid() && renren.isAccessTokenValid()) {
				// 启动分享操作
				if ("game".equals(shareType)) {
					// 游戏的分享
					new RenrenGetGameIconTask(context, gameInfo, renren).execute("");
				} else {
					// 分享游戏大厅
					RenrenUtil.postFeedSoft(context, content);
				}

			} else {
				renren.authorize((Activity) context, listener);
			}
		}
	}

	/**
	 * 分享到腾讯微博
	 */
	private void shareToTencentMicroblog() {
		boolean isHad = false;
		isHad = startAimApplication(getAppListInfo(), "腾讯微博", TencentMicroblogUtil.TENCENT_BLOG);
		if (!isHad) {
			if (null == ShareWindows.auth) {
				auth = new OAuthClient();
			}
			// 取得上次存储的Access token
			oauth_token_array = PreferenceUtil.fetch(context, "tencent");
			oauth_token = oauth_token_array[0];
			oauth_token_secret = oauth_token_array[1];
			Const.type = "0";
			// 实例化oauth
			ShareWindows.oauth = TencentMicroblogUtil.initOAuth(context);
			// 客户端大厅的分享
			boolean isHadAuthorize = TencentMicroblogUtil.isHadAuthorize(context, "0", oauth_token, oauth_token_secret);
			SharedPreferences preferences = context.getSharedPreferences(PreferenceUtil.StoreDataName, 0);
			Editor edit = preferences.edit();
			if ("game".equals(shareType)) {
				// 是游戏分享
				Const.type = "0";
				// 设置分享的内容
				edit.putString("tencent", getShareContent(gameInfo));
				// 设置图片路径
				edit.putString("picture", gameInfo.getPicturePath());
			} else {
				// 是客户端的分享
				Const.type = "1";
				// 设置分享内容
				edit.putString("tencent", this.content);
			}
			edit.commit();
			if (isHadAuthorize) {
				/** 发布一条微博信息 */
				startShare();
			} else {
				// 还没有绑定微博 导航到授权认证页面
				new TencentDialog((Activity) context, TencentMicroblogUtil.CONSUMERKEY_TENCENT, TencentMicroblogUtil.CONSUMERSECRET_TENCENT).show();
			}
		}

	}

	/**
	 * 启动腾讯分享编辑界面
	 */
	public static void startShare() {
		String[] array = PreferenceUtil.fetch(context, "tencent");
		Intent intent = new Intent(context, TencentShareActivity.class);
		intent.putExtra("oauth_token", array[0]);
		intent.putExtra("oauth_token_secret", array[1]);
		context.startActivity(intent);
	}

	/**
	 * 分享到新浪微博
	 */
	private void shareToSinaMicroblog() {
		// 遍历应用程序列表
		boolean isHad = false;
		isHad = startAimApplication(getAppListInfo(), "新浪微博", TencentMicroblogUtil.SINA_BLOG);
		if (!isHad) {
			// 新浪微博
			if (!"game".equals(shareType)) {
				// 客户端大厅的推荐分享
				SinaMicroblogUtil.shareToSina(context, null, content);
			} else {
				// 游戏的分享
				SinaMicroblogUtil.shareToSina(context, gameInfo, null);
			}
		}
	}

	/**
	 * 更多分享
	 */
	private void shareToMore() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain"); // 分享的数据类型
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享爱游戏"); // 主题
		if ("client".equals(shareType)) {
			intent.putExtra(Intent.EXTRA_TEXT, ShareWindows.this.content); // 内容
		} else {
			intent.putExtra(Intent.EXTRA_TEXT, getShareContent(gameInfo)); // 内容
		}
		context.startActivity(Intent.createChooser(intent, "请选择分享方式")); // 目标应用选择对话框的标题
	}

	/**
	 * 和人人网相关的listener的创建
	 */
	final RenrenAuthListener listener = new RenrenAuthListener() {
		@Override
		public void onComplete(Bundle values) {
			// 认证返回后执行的动作
			if ("game".equals(shareType)) {// 如果是进行游戏的分享
				// 启动分享的异步任务
				new RenrenGetGameIconTask(context, gameInfo, renren).execute("");
			} else {// 是进行游戏大厅的分享
				RenrenUtil.postFeedSoft(context, content);
			}

		}

		@Override
		public void onRenrenAuthError(RenrenAuthError renrenAuthError) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, "认证失败", Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onCancelLogin() {
		}

		@Override
		public void onCancelAuth(Bundle values) {
		}
	};

	private String getShareContent(GameInfo info) {
		return "我在  @中国电信爱游戏  发现了一个非常好玩的游戏：" + info.getGameName() + "，快来试试吧！快去下载吧  http://game.189.cn/game/" + info.getGameId() + "/38.html";
	}

	/**
	 * 功能：获取手机中支持分享功能的应用程序
	 */
	private List<ResolveInfo> getAppListInfo() {
		List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		PackageManager pManager = context.getPackageManager();
		mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		for (int i = 0; i < mApps.size(); i++) {
			Log.i(ShareWindows.class.getCanonicalName(), mApps.get(i).activityInfo.name);
		}

		return mApps;
	}

	/**
	 * 查看手机是不是安装了符合条件的应用程序
	 */
	private boolean startAimApplication(List<ResolveInfo> mApps, String name, String packageName) {
		for (ResolveInfo x : mApps) {
			Pattern p = Pattern.compile(packageName);
			Matcher m = p.matcher(x.activityInfo.name);
			if (null != x.activityInfo.loadLabel(context.getPackageManager()) && m.find()) {
				if ("game".equals(shareType)) {
					new PostMyMicroblogs(x, x.activityInfo.name).execute("");

				} else {
					intent.setType("text/plain");
					// 分享的数据类型
					intent.putExtra(Intent.EXTRA_TEXT, ShareWindows.this.content);
					intent.setClassName(x.activityInfo.packageName, x.activityInfo.name);
					context.startActivity(intent);
				}
				return true;
			}
		}

		return false;
	}

	/**
	 * 发布微博的异步任务
	 * 
	 * @author LiuHan
	 * @version 1.0 create on:2012/1/18
	 */
	class PostMyMicroblogs extends AsyncTask<String, Integer, String> {
		private File file, sendFile;
		private URL url;
		private ResolveInfo resolve;
		private String type;

		public PostMyMicroblogs(ResolveInfo zz, String type) {
			this.resolve = zz;
			this.type = type;
		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
				// 发送图片和文字
				url = new URL(gameInfo.getPicturePath() + "pic1.jpg");
				file = new File(TencentMicroblogUtil.PIC_PATH);// 创建文件
				if (file.exists()) {
					file.delete();
				}
				// 创建URL
				HttpURLConnection con = (HttpURLConnection) url.openConnection();// 获得连接
				con.setRequestMethod("GET");// get方式
				con.setReadTimeout(5 * 1000);// 请求最大时间
				InputStream is = con.getInputStream();// 获得流
				byte[] data = readinputstream(is);// 读取流到二进制数组
				FileOutputStream outputstream = context.openFileOutput("pic.jpg", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);// 创建文件输出流
				outputstream.write(data);// 将二进制文件输出
				outputstream.close();// 关闭
				sendFile = new File(TencentMicroblogUtil.GET_PATH);// 创建文件
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			intent.setType("image/*");
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + sendFile.getAbsolutePath()));
			intent.putExtra(Intent.EXTRA_TEXT, getShareContent(gameInfo));
			intent.setClassName(resolve.activityInfo.packageName, type);
			context.startActivity(intent);
		}

		public byte[] readinputstream(InputStream is) throws Exception {
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];// 缓存区
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outstream.write(buffer, 0, len);// 写入内存
			}
			is.close();
			return outstream.toByteArray();
		}

	}
}
