package com.egame.app.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egame.R;
import com.egame.app.tasks.SatisfactionTask;
import com.egame.app.uis.MyReplyActivity;
import com.egame.beans.Content;
import com.egame.beans.ReplyMessage;
import com.egame.beans.ReplyMessageByContentID;
import com.egame.config.Const;
import com.egame.utils.common.CommonUtil;
import com.egame.utils.common.L;
import com.egame.utils.common.MD5;

/**
 * 
 * 类说明：单个主题反馈详情
 * 
 * @创建时间 2011-12-29
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class MyReplyDetailAdapter extends BaseAdapter {
	private List<ReplyMessage> listReplyMessage;

	private String reply_content_id;

	private Context ctx;

	private LayoutInflater mInflater;

	public MyReplyDetailAdapter(List<ReplyMessageByContentID> list, Context ctx) {
		super();
		this.ctx = ctx;
		listReplyMessage = new ArrayList<ReplyMessage>();
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < list.size(); i++) {
			ReplyMessage replyMessage = new ReplyMessage();
			replyMessage.setContent_id(list.get(i).getContent_id());
			replyMessage.setReply_date(list.get(i).getReply_date());
			replyMessage.setUser_reply_message(list.get(i)
					.getUser_reply_message());
			listReplyMessage.add(replyMessage);

			List<Content> reply_content = list.get(i).getReply_content();

			if (null != reply_content) {

				for (int j = 0; j < reply_content.size(); j++) {
					ReplyMessage replyMessageKeFu = new ReplyMessage();
					replyMessageKeFu.setContent(reply_content.get(j)
							.getContent());
					replyMessageKeFu.setReplyContentId(reply_content.get(j)
							.getReplyContentId());

					replyMessageKeFu.setSatisfactoryDate(reply_content.get(j)
							.getSatisfactoryDate());
					replyMessageKeFu.setTime(reply_content.get(j).getTime());
					replyMessageKeFu.setUserReplySatisfactory(reply_content
							.get(j).getUserReplySatisfactory());
					listReplyMessage.add(replyMessageKeFu);
				}
			}
		}
		L.d("sizes:" + listReplyMessage.size());

	}

	@Override
	public int getCount() {
		return listReplyMessage.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (null == view) {
			view = mInflater.inflate(R.layout.egame_myreply_detail, null);
			holder = new ViewHolder();
			holder.llMyReply = (LinearLayout) view.findViewById(R.id.myReply);
			holder.llKefuAnswer = (LinearLayout) view
					.findViewById(R.id.kefuAnswer);
			holder.tvMyReplyDate = (TextView) view
					.findViewById(R.id.myReplyDate);
			holder.tvMyReplyContent = (TextView) view
					.findViewById(R.id.myReplyContent);
			holder.tvKefuAnswerDate = (TextView) view
					.findViewById(R.id.kefuAnswerDate);
			holder.tvKefuAnswerContent = (TextView) view
					.findViewById(R.id.kefuAnswerContent);
			holder.tvAppraise = (TextView) view.findViewById(R.id.appraise);
			holder.btnAppraise = (Button) view.findViewById(R.id.btn_appraise);
			holder.llAppraise = (LinearLayout) view
					.findViewById(R.id.ll_appraise);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.btnAppraise.setOnClickListener(new Appraise(position));
		holder.tvAppraise.setVisibility(View.GONE);
		holder.llAppraise.setVisibility(View.GONE);

		L.d("position--->" + position);
		if (null != listReplyMessage.get(position).getUser_reply_message()) {

			holder.tvMyReplyDate.setText(listReplyMessage.get(position)
					.getReply_date());
			holder.tvMyReplyContent.setText(listReplyMessage.get(position)
					.getUser_reply_message());
		} else {
			holder.llMyReply.setVisibility(View.GONE);
		}

		if (null != listReplyMessage.get(position).getContent()) {

			holder.tvKefuAnswerContent.setText(listReplyMessage.get(position)
					.getContent());
			holder.tvKefuAnswerDate.setText(listReplyMessage.get(position)
					.getTime());
			String appraise = CommonUtil.getSatisfactionDetail(ctx,
					listReplyMessage.get(position).getUserReplySatisfactory());
			if (null != appraise) {
				holder.tvAppraise.setVisibility(View.VISIBLE);
				holder.llAppraise.setVisibility(View.GONE);
				holder.tvAppraise.setText("我的评价：" + appraise);
			} else {
				holder.tvAppraise.setVisibility(View.GONE);
				holder.llAppraise.setVisibility(View.VISIBLE);
			}
		} else {
			holder.llKefuAnswer.setVisibility(View.GONE);
		}
		return view;
	}

	public static class ViewHolder {
		LinearLayout llKefuAnswer;

		LinearLayout llMyReply;

		public TextView tvMyReplyDate;

		public TextView tvMyReplyContent;

		public TextView tvKefuAnswerDate;

		public TextView tvKefuAnswerContent;

		public TextView tvAppraise;

		public Button btnAppraise;

		public LinearLayout llAppraise;
	}

	class Appraise implements OnClickListener {
		private int position;

		Appraise(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(ctx)
					.setTitle(R.string.egame_kfpj)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(R.array.egame_pingjia, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 发送评价信息
									dialog.dismiss();
									String satisfactory_date = String.valueOf(
											System.currentTimeMillis())
											.substring(0, 10);
									String time = satisfactory_date;
									String sig = MD5.getMD5Str(
											time + Const.KEY_FOR_GAME)
											.toLowerCase();

									String satisfactio = CommonUtil
											.getSatisfaction(which);
									reply_content_id = listReplyMessage.get(
											position).getReplyContentId();
									String url = "http://10000club.189.cn:80/service/userSatisfactory.php?application_id=5&user_reply_satisfactory="
											+ satisfactio
											+ "&satisfactory_date="
											+ satisfactory_date
											+ "&time="
											+ time
											+ "&sig="
											+ sig
											+ "&reply_content_id="
											+ reply_content_id;
									//
									SatisfactionTask satisfactionTask = new SatisfactionTask(
											(MyReplyActivity) ctx);
									satisfactionTask.execute(url);

								}
							}).setNegativeButton("取消", null).show();
		}

	}
}
