/**
 * 
 */
package com.egame.app.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.egame.R;
import com.egame.utils.ui.ToastUtil;
import com.renren.api.connect.android.common.AbstractRequestListener;
import com.renren.api.connect.android.exception.RenrenError;
import com.renren.api.connect.android.status.StatusSetResponseBean;

/**
 * 描述：监听异步调用发送状态接口的响应
 * 
 * @author LiuHan
 * @version 1.0 create on：2012/1/16
 */
public class StatusSetListener extends
		AbstractRequestListener<StatusSetResponseBean> {
	private Context context;
	private Handler handler;
	private ProgressDialog progressDialog;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param progressDialog
	 *            显示请求网络的UI控件
	 */
	public StatusSetListener(Context context, ProgressDialog progressDialog) {
		this.context = context;
		this.handler = new Handler(context.getMainLooper());
		this.progressDialog = progressDialog;
	}

	@Override
	public void onFault(Throwable fault) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
				Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onComplete(StatusSetResponseBean bean) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
				ToastUtil.show(
						context,
						context.getResources().getString(
								R.string.egame_post_status_success));
			}
		});
	}

	@Override
	public void onRenrenError(RenrenError renrenError) {
		final int errorCode = renrenError.getErrorCode();
		handler.post(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
				if (errorCode == RenrenError.ERROR_CODE_OPERATION_CANCELLED) {
					ToastUtil.show(context, context.getResources()
							.getString(R.string.egame_post_status_cancel));
				} else {
					ToastUtil.show(context, context.getResources()
							.getString(R.string.egame_post_status_failure));
				}
			}
		});
	}
}
