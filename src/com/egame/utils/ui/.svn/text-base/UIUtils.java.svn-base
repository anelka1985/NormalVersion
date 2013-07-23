package com.egame.utils.ui;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;

public class UIUtils {
	public static OnPreDrawListener initButtonState(final View v) {
		OnPreDrawListener op = new OnPreDrawListener() {

			public boolean onPreDraw() {
				v.setSelected(true);
				return true;
			}
		};
		v.getViewTreeObserver().addOnPreDrawListener(op);
		return op;
	}

	public static void buttonStateChange(List<View> vs, View v,
			OnPreDrawListener op) {
		for (View view : vs) {
			view.getViewTreeObserver().removeOnPreDrawListener(op);
			view.setSelected(false);
		}
		v.setSelected(true);
	}

	public static int getDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static void showMessage(Context ctx, int title, String msg) {
		new AlertDialog.Builder(ctx).setTitle(title).setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
	}

	public static void showMessage(Context ctx, int title, int msg) {
		new AlertDialog.Builder(ctx).setTitle(title).setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
	}

	public static void changeTextShow(TextView textview, String str, String str1) {
		Log.i("str,str1",str+"//"+str1);
		if (textview.getVisibility() == View.GONE) {
			textview.setVisibility(View.VISIBLE);
		}
		if (null == str || null == str1 || "".equals(str1) || "".equals(str)) {
			textview.setVisibility(View.GONE);
			
		} else {
			textview.setText(str + "  " + str1);
		}
	}
}
