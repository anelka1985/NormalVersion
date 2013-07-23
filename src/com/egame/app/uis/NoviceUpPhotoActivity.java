package com.egame.app.uis;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egame.R;
import com.egame.config.Urls;
import com.egame.utils.common.LoginDataStoreUtil;
import com.egame.utils.ui.DialogUtil;
import com.egame.utils.ui.ImageUtils;
import com.eshore.network.stat.NetStat;

/**
 * @author LiuHan
 * 
 */
public class NoviceUpPhotoActivity extends Activity implements OnClickListener {
	public static NoviceUpPhotoActivity instance;
	private TextView mNoviceName;
	private TextView mPhotoBack;
	/** 完成按钮UI */
	private RelativeLayout mFinish;
	/** 从相册上传布局UI */
	private RelativeLayout mPhotos;
	/** 拍照布局UI */
	private RelativeLayout mPictures;
	/** 用户头像 */
	private ImageView mUserIcon;

	private Bitmap cameraBitmap;
	/** 存储头像图片的路径 */
	String[] str = null;

	public ProgressDialog pDialog;

	Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mUserIcon.setBackgroundDrawable((Drawable) msg.obj);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.egame_novice_photo);
		instance = this;
		initControlUI();
	}

	/**
	 * 
	 */

	protected void onResume() {
		super.onResume();
		NetStat.onResumePage();
	}

	/**
	 * 
	 */

	protected void onPause() {
		super.onPause();
		NetStat.onPausePage("NoviceUpPhotoActivity");
	}

	private void initControlUI() {
		mNoviceName = (TextView) this.findViewById(R.id.m_novice_name);
		mPhotoBack = (TextView) this.findViewById(R.id.m_photo_back);
		mPhotoBack.setOnClickListener(this);
		mFinish = (RelativeLayout) this.findViewById(R.id.m_finish);
		mFinish.setOnClickListener(this);
		mPhotos = (RelativeLayout) this.findViewById(R.id.m_photos);
		mPhotos.setOnClickListener(this);
		mPictures = (RelativeLayout) this.findViewById(R.id.m_pictures);
		mPictures.setOnClickListener(this);
		mUserIcon = (ImageView) this.findViewById(R.id.m_user_icon);
		if (1 == LoginDataStoreUtil.fetchUser(NoviceUpPhotoActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getGender()) {
			mUserIcon.setBackgroundResource(R.drawable.egame_icon_men);
		} else {
			mUserIcon.setBackgroundResource(R.drawable.egame_icon_made);
		}
		mNoviceName.setText(LoginDataStoreUtil.fetchUser(NoviceUpPhotoActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getNickName() + "，欢迎来到爱游戏社区");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */

	public void onClick(View v) {
		if (v == mPhotoBack) {
			DialogUtil.showNoviceCancelDialog(this);
		} else if (v == mFinish) {
			// 完成
			new UpdateIconTask().execute(new Bitmap[] { cameraBitmap });
		} else if (v == mPhotos) {
			// 从相册上传
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			// intent.putExtra("crop", "true");
			// intent.putExtra("aspectX", 1);
			// intent.putExtra("aspectY", 1);
			// intent.putExtra("outputX", 80);
			// intent.putExtra("outputY", 80);
			// intent.putExtra("return-data", true);
			startActivityForResult(intent, 2);
		} else if (v == mPictures) {
			// 拍照
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File out = new File(Environment.getExternalStorageDirectory(), "camera.png");
			Uri uri = Uri.fromFile(out);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(intent, 3);

		}
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_MENU)) {
			DialogUtil.showNoviceCancelDialog(this);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 2: {
				/** 保存并跳转 */
				// try {
				// cameraBitmap = (Bitmap) data.getExtras().get("data");
				// mHandler.sendMessage(mHandler.obtainMessage(1,
				// ImageUtils.bitmap2Drawable(cameraBitmap)));
				// } catch (Exception e) {
				// e.printStackTrace();
				// Toast.makeText(NoviceUpPhotoActivity.this,
				// "获取图片数据失败，请检查SD卡", Toast.LENGTH_SHORT).show();
				// }
				try {
					Uri uri = data.getData();
					Cursor cursor = getContentResolver().query(uri, null, null, null, null);
					cursor.moveToFirst();
					String srcFile = cursor.getString(1);
					cursor.close();

					cameraBitmap = ImageUtils.decodeFile(srcFile);
					mHandler.sendMessage(mHandler.obtainMessage(1, ImageUtils.bitmap2Drawable(cameraBitmap)));
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, R.string.egame_get_pic_error, Toast.LENGTH_SHORT).show();
				}
			}
				break;
			case 3: {
				try {
					// 压缩图片
					cameraBitmap = ImageUtils.decodeFile(Environment.getExternalStorageDirectory() + "/camera.png");
					mHandler.sendMessage(mHandler.obtainMessage(1, ImageUtils.bitmap2Drawable(cameraBitmap)));
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, R.string.egame_camera_pic_error, Toast.LENGTH_SHORT).show();
				}
				// try {
				// // 压缩图片
				// DisplayMetrics dm = new DisplayMetrics();
				// getWindowManager().getDefaultDisplay().getMetrics(dm);
				// float density = dm.density;
				// int target_size = Math.round((200 * density));
				// ImageUtils.compressedBitmap(PIC_PATH, target_size);
				// /** 拍照后保存图片，并跳到裁剪功能 */
				// Intent intent = new Intent("com.android.camera.action.CROP");
				// intent.setData(Uri.parse(android.provider.MediaStore.Images.Media
				// .insertImage(getContentResolver(),
				// Environment.getExternalStorageDirectory()
				// + "/camera.png", null, null)));
				// intent.putExtra("crop", "true");
				// intent.putExtra("aspectX", 1);
				// intent.putExtra("aspectY", 1);
				// intent.putExtra("outputX", 80);
				// intent.putExtra("outputY", 80);
				// intent.putExtra("return-data", true);
				// startActivityForResult(intent, 2);
				// } catch (Exception e) {
				// e.printStackTrace();
				// Toast.makeText(NoviceUpPhotoActivity.this,
				// "获取图片数据失败，请检查SD卡", Toast.LENGTH_SHORT).show();
				// }
			}
				break;
			}
		}
	}

	class UpdateIconTask extends AsyncTask<Bitmap, Integer, String> {
		DataOutputStream dos = null;

		public UpdateIconTask() {
			pDialog = new ProgressDialog(NoviceUpPhotoActivity.this);
			pDialog.setMessage("请稍候...");
			pDialog.show();
		}

		protected String doInBackground(Bitmap... arg0) {
			String end = "\r\n";
			String twoHyphens = "--";
			String boundary = "******";
			HttpURLConnection httpURLConnection = null;
			try {
				URL url = new URL(Urls.getUploadIconUrl(LoginDataStoreUtil.fetchUser(NoviceUpPhotoActivity.this, LoginDataStoreUtil.LOG_FILE_NAME)
						.getUserId(), NoviceUpPhotoActivity.this));
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setConnectTimeout(10 * 1000);
				httpURLConnection.setReadTimeout(120 * 1000);
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
				httpURLConnection.setRequestProperty("USERID",
						LoginDataStoreUtil.fetchUser(NoviceUpPhotoActivity.this, LoginDataStoreUtil.LOG_FILE_NAME).getUserId());
				httpURLConnection.setRequestProperty("Charset", "UTF-8");
				httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				httpURLConnection.connect();
				dos = new DataOutputStream(httpURLConnection.getOutputStream());
				if (dos != null) {
					dos.writeBytes(twoHyphens + boundary + end);

					dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"headimg\"" + end);
					dos.writeBytes(end);
					dos.write(ImageUtils.Bitmap2Bytes(arg0[0]));
					dos.writeBytes(end);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
					dos.flush();
				}

				if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					return "ok";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (httpURLConnection != null)
					httpURLConnection.disconnect();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				// 如果来自社区
				// if ("web".equals(Const.isWebStart)) {
				// new FinishNoviceTask(NoviceUpPhotoActivity.this).execute("");
				// } else {
				pDialog.dismiss();
				Intent intent = new Intent(NoviceUpPhotoActivity.this, NoviceFinishActivity.class);
				startActivity(intent);
				finish();
				// }
			} else {
				pDialog.dismiss();
				if (null == cameraBitmap) {
					Toast.makeText(NoviceUpPhotoActivity.this, NoviceUpPhotoActivity.this.getResources().getString(R.string.egame_upload_icon_fail),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(NoviceUpPhotoActivity.this, "上传头像失败", Toast.LENGTH_SHORT).show();
				}

			}

		}
	}

}
