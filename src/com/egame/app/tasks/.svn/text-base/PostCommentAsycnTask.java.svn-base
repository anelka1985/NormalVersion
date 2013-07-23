/**
 * 
 */
package com.egame.app.tasks;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.egame.app.uis.GameCommentActivity;
import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;

/**
 * 描述:发表游戏的语言和星级评论
 * 
 * @author LiuHan
 * @version 1.0 create on:2011/1/14
 * 
 */
public class PostCommentAsycnTask extends AsyncTask<String, Integer, String> {
	private GameCommentActivity context;
	private ProgressDialog progressDialog;
	private String gameId = "";
	private String userId = "";
	private String score;
	private String content = "";
	private String resultMsg = "";
	private int resultCode = 1;
	private String urlType = "";
	private String nickName;
	private String gameName;
	private int type;

	/**
	 * 构造函数 游戏的评论调用
	 * 
	 * @param context
	 *            相关上下文
	 * @param type
	 *            评论的类型
	 * @param gameId
	 *            游 戏的Id
	 * @param userId
	 *            用户的Id
	 * @param score
	 *            星星的参数
	 * @param content
	 *            文字评论的内容
	 */
	public PostCommentAsycnTask(GameCommentActivity context, String gameId, String userId, String score,
			String content, String nickName, String gameName, int type) {
		this.gameId = gameId;
		this.userId = userId;
		this.score = score;
		this.content = content;
		this.context = context;
		this.nickName = nickName;
		this.gameName = gameName;
		this.type = type;
		progressDialog = new ProgressDialog(this.context);
		progressDialog.setMessage("请稍候....");
		progressDialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params) {
		urlType = params[0];
		if ("comment".equals(params[0])) {
			try {
				String s = HttpConnect.getHttpString(Urls.publishGameComment(context, gameId, score, userId, content,
						nickName, gameName, type));
				JSONObject obj = new JSONObject(s);
				JSONObject json = obj.getJSONObject("result");
				resultCode = json.getInt("resultcode");
				resultMsg = json.getString("resultmsg");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 获取用户上次评论的结果：是否发表过星级评论
		if ("getLastComment".equals(params[0])) {
			try {
				String s = HttpConnect.getHttpString(Urls.publishGameComment(context, gameId, score, userId, content,
						nickName, gameName, type));
				JSONObject obj = new JSONObject(s);
				score = obj.optInt("score") + "";
				JSONObject json = obj.getJSONObject("result");
				resultCode = json.getInt("resultcode");
				resultMsg = json.getString("resultmsg");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return resultMsg;

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		if ("comment".equals(urlType)) {
			context.netWorkPrompt(context, result, resultCode, null);
		} else if ("getLastComment".equals(urlType)) {
			context.netWorkPrompt(context, resultMsg, resultCode, score);
		}
	}
}
