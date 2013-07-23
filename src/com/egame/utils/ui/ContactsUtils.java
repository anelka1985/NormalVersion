/**
 * 
 */
package com.egame.utils.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.egame.beans.ContactsBean;

/**
 * 描述:取得联系人信息
 * 
 * @author LiuHan
 * @version 1.0 create on: 2012-1-4
 */
public class ContactsUtils {
	/** 手机号码合法性的匹配规则 */
	// public static Pattern phoneVa = Pattern
	// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	public static boolean checkPhoneNum(String phoneNum) {
		if (phoneNum != null) {
			if (phoneNum.length() == 11 && phoneNum.startsWith("1")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 功能:取得本地的联系人信息
	 * 
	 * @return 联系人信息列表
	 */
	public static List<ContactsBean> getContactsInfoMation(Context mContext) {

		List<ContactsBean> mContactsList = new ArrayList<ContactsBean>();
		// 取得Cursor对象的引用
		Cursor mCursor = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		try {
			if (mCursor.getCount() > 0) {
				// 联系人不为空
				while (mCursor.moveToNext()) {
					// 取得联系人实体类
					ContactsBean mContactsBean = new ContactsBean();
					String mContactsId = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
					String mContactsName = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					int hasPhone = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					String number = "";
					if (hasPhone == 1) {
						Cursor phones = null;
						try {
							phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + mContactsId, null, null);
							phones.moveToFirst();
							number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (phones != null) {
								phones.close();
							}
						}
					}
					// 过滤掉“-”分隔符
					if (number.contains("-")) {
						number = number.replaceAll("-", "");
					}
					if (number.contains(" ")) {
						number = number.replaceAll(" ", "");
					}
					if (checkPhoneNum(number)) {
						// 对手机号码合法性的检查
						mContactsBean.setmContactsName(mContactsName);
						mContactsBean.setmContactsPhone(number);
						mContactsBean.setmIsSelect(false);
						mContactsList.add(mContactsBean);
					}

				}
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} finally {
			mCursor.close();
		}
		return mContactsList;
	}
}
