package com.egame.app.widgets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

/**
 * 类说明：
 * 
 * @创建时间 2012-3-24 上午11:55:27
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class CustomerDatePickerDialog extends DatePickerDialog {

	public CustomerDatePickerDialog(Context context,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		setTitle(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		setTitle(year + "-" + (month + 1) + "-" + day);
	}
}
