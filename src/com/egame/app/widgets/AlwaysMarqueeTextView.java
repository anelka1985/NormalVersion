package com.egame.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 类说明：
 * 
 * @创建时间 2012-1-20 上午09:56:46
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class AlwaysMarqueeTextView extends TextView {

	public AlwaysMarqueeTextView(Context context) {
		super(context);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
