/**
 * 
 */
package com.egame.app.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.egame.beans.GameInfo;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.ui.IconBean;
import com.egame.utils.ui.ToastUtil;
import com.egame.weibos.RenrenUtil;
import com.renren.api.connect.android.Renren;

/**
 * 描述：加载要分享的游戏的图片 进入分享编辑界面
 * 
 * @author LiuHan
 * @version 1.0 create on:2012/1/16
 * 
 */
public class RenrenGetGameIconTask extends
		AsyncTask<String, Integer, BitmapDrawable> {
	private GameInfo gameInfo;
	private Context context;
	private Renren renren;
	private ProgressDialog progressDialog;

	/**
	 * 构造函数
	 * @param context 上下文
	 * @param gameInfo 装载游戏信息的游戏实体类
	 * @param renren 人人对象的引用
	 */
	public RenrenGetGameIconTask(Context context, GameInfo gameInfo,
			Renren renren) {
		this.gameInfo = gameInfo;
		this.context = context;
		this.renren = renren;
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("请稍候. . . . . . ");
		//progressDialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected BitmapDrawable doInBackground(String... params) {
		try {
			IconBean bean = (IconBean) gameInfo;
			if (null == bean.getIcon()) {
				BitmapDrawable drawable;
				drawable = HttpConnect.getHttpDrawable(bean.getIconurl());
				bean.setIcon(drawable);
				return drawable;
			} else {
				return bean.getIcon();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(BitmapDrawable result) {
		//progressDialog.dismiss();
		if (null != result) {
			// 启动人人网分享对话框
			RenrenUtil.postGameShare(context, renren, "", gameInfo, result);
		} else {
			ToastUtil.show(context, "分享数据加载失败，请重新尝试!");
		}
	
	}

}
