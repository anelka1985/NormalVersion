package com.egame.beans;

import org.json.JSONObject;

/**
 * @desc 游戏评论列表的实体类
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-5
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class CommentBean {
	private String comment;
	private String userName;
	private int score;
	private int userId;
	private int commentId;
	private String submitTime;
	private int gameId;
	private String gameName;

	public CommentBean(JSONObject obj) {
		this.comment=obj.optString("comment");
		this.userName = obj.optString("userName");
		this.score = obj.optInt("score");
		this.userId=obj.optInt("userId");
		this.commentId = obj.optInt("commentId");
		this.submitTime = obj.optString("submitTime");
		this.gameId = obj.optInt("gameId");
		this.gameName = obj.optString("gameName");
	}

	/**
	 * @return 返回 comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param 对comment进行赋值
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return 返回 userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param 对userName进行赋值
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return 返回 score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param 对score进行赋值
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return 返回 userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param 对userId进行赋值
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return 返回 commentId
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * @param 对commentId进行赋值
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return 返回 submitTime
	 */
	public String getSubmitTime() {
		return submitTime;
	}

	/**
	 * @param 对submitTime进行赋值
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * @return 返回 gameId
	 */
	public int getGameId() {
		return gameId;
	}

	/**
	 * @param 对gameId进行赋值
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	/**
	 * @return 返回 gameName
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param 对gameName进行赋值
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

}
