package com.egame.app.tasks;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.RecommContactsActivity;
import com.egame.beans.ContactsBean;
import com.egame.utils.ui.ContactsUtils;
import com.egame.utils.ui.ToastUtil;

/**
 * 描述:取得联系人数据的异步任务
 * 
 * @author LiuHan
 * @version 1.0 create date :Sat Dec 31 2011
 * 
 */
public class ContactsAsyncTask extends
		AsyncTask<Integer, String, List<ContactsBean>> {
	/** 获取对话框 */
	private ProgressDialog mPdialog;
	/** 联系人数据列表 */
	List<ContactsBean> list;
	/** 上下文 */
	private Context context;

	public ContactsAsyncTask(Context context, List<ContactsBean> list) {
		this.list = list;
		this.context = context;
		mPdialog = new ProgressDialog(RecommContactsActivity.instance);
		mPdialog.setMessage(this.context.getResources().getString(R.string.egame_progress_dialog));
		mPdialog.show();
	}

	@Override
	protected List<ContactsBean> doInBackground(Integer... params) {
		// 取得联系人列表 s数据
		return ContactsUtils
				.getContactsInfoMation(RecommContactsActivity.instance);

	}

	@Override
	public void onPostExecute(List<ContactsBean> mResult) {
		super.onPostExecute(mResult);
		mPdialog.dismiss();
		if (mResult.size() == 0) {
			ToastUtil.show(this.context, this.context.getResources().getString(R.string.egame_contacts_prompt));
			((Activity) this.context).finish();
		} else {
			this.list.addAll(mResult);
			((RecommContactsActivity) this.context).initUIShow();
		}
	}
}
