package com.egame.app.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egame.R;
import com.egame.app.EgameApplication;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.Utils;
import com.eshore.network.stat.NetStat;

/**
 * 类说明：
 * 
 * @创建时间 2012-2-10 上午09:39:31
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class InputPopupActivity extends Activity implements OnClickListener {
	private View mFace, mFaceArea;
	private Button mSend;
	private EditText mInput;
	private String mCallback;
	private int[] mFaceViewIds = { R.id.face0, R.id.face1, R.id.face2,
			R.id.face3, R.id.face4, R.id.face5, R.id.face6, R.id.face7,
			R.id.face8, R.id.face9, R.id.face10, R.id.face11, R.id.face12,
			R.id.face13, R.id.face14, R.id.face15, R.id.face16, R.id.face17,
			R.id.face18, R.id.face19, R.id.face20 };
	private View[] mFaceViews = new View[mFaceViewIds.length];
	private String[] mFaceStrs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.egame_input_popup);
		mFace = findViewById(R.id.face);
		mFaceArea = findViewById(R.id.face_area);
		mSend = (Button) findViewById(R.id.send);
		mInput = (EditText) findViewById(R.id.input);
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			Toast.makeText(getApplicationContext(), "参数不全", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {
			boolean showFace = getIntent().getBooleanExtra("face", false);
			String text = getIntent().getStringExtra("text");
			String hint = getIntent().getStringExtra("hint");
			String btnText = getIntent().getStringExtra("btn");
			mCallback = getIntent().getStringExtra("callback");
			if (showFace) {
				mFace.setVisibility(View.VISIBLE);
				mFace.setOnClickListener(this);
			} else {
				mFace.setVisibility(View.GONE);
			}
			mInput.setText(text);
			mInput.setHint(hint);
			mSend.setText(btnText);
			mSend.setOnClickListener(this);
			mFaceStrs = getResources().getStringArray(R.array.egame_face);
			for (int i = 0; i < mFaceViewIds.length; i++) {
				mFaceViews[i] = findViewById(mFaceViewIds[i]);
				mFaceViews[i].setOnClickListener(this);
			}
		}
		EgameApplication.Instance().addActivity(this);
	}

	/**
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	/**
	 * 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("InputPopupActivity");
	}

	/**
	 * 设置传递参数
	 * 
	 * @param params
	 *            param[0]- 是否显示表情开关, param[1]- 显示内容, param[2]- 输入框提示, param[3]-
	 *            按钮文字, param[4]- 接口地址，必须有一个字符串替换符, param[5]- 回调值
	 * @return
	 */
	public static Bundle getBundle(String[] params) {
		Bundle bundle = new Bundle();
		bundle.putBoolean("face", params[0].equals("true"));
		bundle.putString("text", params[1]);
		bundle.putString("hint", params[2]);
		bundle.putString("btn", params[3]);
		bundle.putString("callback", params[4]);
		return bundle;
	}

	@Override
	public void onClick(View v) {
		if (mFace == v) {
			DialogUtil.closeSoft(this);
			if (mFaceArea.getVisibility() == View.VISIBLE) {
				mFaceArea.setVisibility(View.GONE);
			} else {
				mFaceArea.setVisibility(View.VISIBLE);
			}
		} else if (mSend == v) {
			String content = mInput.getText().toString();
			if (TextUtils.isEmpty(content)) {
				Toast.makeText(getApplicationContext(),
						mInput.getHint().toString(), Toast.LENGTH_SHORT).show();
				return;
			}
			if (Utils.strlength(content) > 280) {
				Toast.makeText(getApplicationContext(),
						"长度超出限制,请输入140个中文汉字或者280个英文字符", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			Intent i = new Intent();
			i.putExtra("callback", mCallback);
			i.putExtra("content", mInput.getText().toString());
			setResult(RESULT_OK, i);
			finish();
		} else {
			int selIndex = -1;
			for (int i = 0; i < mFaceViews.length; i++) {
				if (mFaceViews[i] == v) {
					selIndex = i;
					break;
				}
			}
			if (selIndex >= 0 && selIndex < mFaceStrs.length) {
				int index = mInput.getSelectionStart();// 获取光标所在位置
				Editable edit = mInput.getEditableText();// 获取EditText的文字
				if (index < 0 || index >= edit.length()) {
					edit.append(mFaceStrs[selIndex]);
				} else {
					edit.insert(index, mFaceStrs[selIndex]);// 光标所在位置插入文字
				}
			}
		}
	}
}
