/**
 * 
 */
package com.egame.utils.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.uis.BaseInfoRegisterActivity;

/**
 * 描述：限制EditText的输入长度，并给予提示
 * 
 * @author LiuHan
 * @version 1.0 日期：2011-11-22
 */
public class MyTextWatcher implements TextWatcher {
	/**
	 * 相关上下文
	 */
	private Context ctx;
	/**
	 * 最大长度
	 */
	private int length;

	/**
	 * 监听改变的文本框
	 */
	private EditText editText, editText2, editText1;

	private Button textView;

	private String flag = "default";
	private Button button1;
	private TextView textPrompt;

	/**
	 * 功能：带参数构造器
	 * 
	 * @param ctx
	 * @param length
	 * @param editText
	 */
	public MyTextWatcher(Context ctx, int length, EditText editText) {
		this.ctx = ctx;
		this.length = length;
		this.editText = editText;
	}

	/**
	 * 
	 * @param ctx
	 * @param length
	 * @param editText
	 * @param view
	 * @param flag
	 */
	public MyTextWatcher(BaseInfoRegisterActivity ctx, int length,
			EditText editText, Button view, TextView vView, String flag) {
		this.ctx = ctx;
		this.length = length;
		this.editText = editText;
		this.flag = flag;
		this.textView = view;
		if ("register".equals(flag)) {
			this.textPrompt = vView;
		}

	}

	/**
	 * 
	 * @param ctx
	 * @param edit1
	 * @param edit2
	 * @param button1
	 * @param button2
	 * @param flag
	 */
	public MyTextWatcher(Context ctx, EditText edit1, EditText edit2,
			Button button1, String flag) {
		this.ctx = ctx;
		this.editText1 = edit1;
		this.editText2 = edit2;
		this.button1 = button1;
		// this.button2 = button2;
		this.flag = flag;

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if ("login".equals(flag)) {
			if (this.editText1.getText().length() > 0
					&& this.editText2.length() > 0) {
				this.button1.setEnabled(true);
				this.button1.setTextColor(this.ctx.getResources().getColor(
						R.color.egame_text_orange));
				this.button1
						.setBackgroundResource(R.drawable.egame_user_login_selector);
			} else if (this.editText1.getText().length() == 0
					&& this.editText2.length() == 0) {
				this.button1.setEnabled(false);
				this.button1.setTextColor(Color.WHITE);
				this.button1
						.setBackgroundResource(R.drawable.egame_login_forbid_icon);
			} else {
				this.button1.setEnabled(false);
				this.button1.setTextColor(Color.WHITE);
				this.button1
						.setBackgroundResource(R.drawable.egame_login_forbid_icon);
			}

		} else {
			Editable editable = editText.getText();

			int len = editable.length();
			// 大于最大长度
			if (len > length) {
				Toast.makeText(this.ctx, "字数多了，不能多于" + length + "个字噢！",
						Toast.LENGTH_SHORT).show();
				int selEndIndex = Selection.getSelectionEnd(editable);

				String str = editable.toString();
				/**
 * 
 */
				String newStr = "";
				if (selEndIndex - 1 > length) {
					newStr = str.substring(0, length);
					selEndIndex = length + 1;
				}

				else {
					int newinputsize = str.length()-length;
					if (selEndIndex - 1 != length) {
						newStr = str.substring(0, selEndIndex - newinputsize)
								+ str.substring(selEndIndex, length + newinputsize);
						selEndIndex -= newinputsize;
					} else {
						newStr = str.substring(0, selEndIndex - 1)
								+ str.substring(selEndIndex - 1, length);
					}
				}
				/**
				 * 
				 */
				// 截取新字符串
				// String newStr = str.substring(0, length);
				editText.setText(newStr);
				editable = editText.getText();
				// 新字符串长度
				int newLen = editable.length();
				// 旧光标位置超过字符串长度
				if (selEndIndex > newLen) {
					selEndIndex = editable.length();
				}
				// 设置新的光标所在位置
				Selection.setSelection(editable, selEndIndex);
			}

			if ("register".equals(flag)) {

				if (len <= length) {
					if (len == length) {
						textView.setEnabled(true);
						textView.setTextColor(this.ctx.getResources().getColor(
								R.color.egame_register_color));
						textView.setBackgroundResource(R.drawable.egame_btn_green_circularity_selector);

						if (!((BaseInfoRegisterActivity) ctx).isVerificater()
								&& null != textPrompt) {
							if (textPrompt.getVisibility() == View.GONE) {
								textPrompt.setVisibility(View.VISIBLE);
							}
						}
					} else {
						textView.setEnabled(false);
						textView.setTextColor(Color.WHITE);
						textView.setBackgroundResource(R.drawable.egame_update_forbid_bg);
						if (null != textPrompt
								&& textPrompt.getVisibility() == View.VISIBLE) {
							textPrompt.setVisibility(View.GONE);
						}

					}
				}
			}
		}
	}
}
