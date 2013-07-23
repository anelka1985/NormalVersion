package com.egame.utils.sys;




import com.egame.config.Const;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogStyle {

	public  AlertDialog.Builder getBuilder(Context ctx, String confirmtxt, String canceltxt,
			DialogInterface.OnClickListener comfirmL,
			DialogInterface.OnClickListener cancelL) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		if (Const.ISICS) {
			builder.setNegativeButton(confirmtxt, comfirmL).setPositiveButton(
					canceltxt, cancelL);
			return builder;
		} else {
			builder.setPositiveButton(confirmtxt, comfirmL).setNegativeButton(
					canceltxt, cancelL);
			return builder;
		}
	}

}
