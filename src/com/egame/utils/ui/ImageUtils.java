package com.egame.utils.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.egame.config.Const;
import com.egame.utils.common.L;

public class ImageUtils {

	/**
	 * 图片转byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/***
	 * 压缩放大图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		L.d("width = " + width);
		L.d("height = " + height);
		L.d("w = " + w);
		L.d("h = " + h);

		float scaleWidth = ((float) w) / width;
		float scaleHeight = ((float) h) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	/**
	 * 图片圆角
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null) {
			return null;
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		if (roundPx <= 0)
			roundPx = 12;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		paint = null;
		rect = null;
		rectF = null;
		canvas = null;
		return output;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return (Drawable) bd;

	}

	/**
	 * 保存bitmap到sd卡
	 * 
	 * @param url
	 * @param bitmap
	 * @throws IOException
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2011-12-6
	 */
	public static void saveBitmap(String url, Bitmap bitmap) throws IOException {
		File f = new File(url);
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 
	 * @param url
	 * @author Administrator
	 * @throws Exception
	 * @time 2011-12-6
	 */
	public static void compressedBitmap(String url, int sampleSize)
			throws Exception {
		FileInputStream fis = null;
		ByteArrayOutputStream out = null;
		Bitmap bitmap = null;
		try {
			fis = new FileInputStream(url);
			out = new ByteArrayOutputStream();
			int i = -1;
			L.d("MEM", "0:" + Debug.getNativeHeapAllocatedSize());
			byte buf[] = new byte[Const.ARRAY_SIZE];
			while ((i = fis.read(buf)) != -1) {
				out.write(buf, 0, i);
			}
			L.d("MEM", "1:" + Debug.getNativeHeapAllocatedSize());
			byte imgData[] = out.toByteArray();
			L.d("MEM", "2:" + Debug.getNativeHeapAllocatedSize());
			// 进行抽样缩放
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 1;
			opts.inJustDecodeBounds = true;
			L.d("MEM", "3:" + Debug.getNativeHeapAllocatedSize());
			bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length,
					opts);
			// 计算抽样比率
			opts.inSampleSize = computeSampleSize(opts, sampleSize);
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length,
					opts);
			L.d("MEM", "4:" + Debug.getNativeHeapAllocatedSize());
			saveBitmap(url, bitmap);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				bitmap.recycle();
				fis.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Bitmap getCompressedBitmap(String url, int sampleSize)
			throws Exception {
		Bitmap bitmap = null;
		try {
			System.out.println("getCompressedBitmap url=" + url);
			// 进行抽样缩放
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 1;
			opts.inJustDecodeBounds = true;
			L.d("MEM", "3:" + Debug.getNativeHeapAllocatedSize());
			bitmap = BitmapFactory.decodeFile(url, opts);
			System.out.println("bitmap width=" + bitmap.getWidth());
			System.out.println("bitmap height=" + bitmap.getHeight());
			// 计算抽样比率
			opts.inSampleSize = computeSampleSize(opts, sampleSize);
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(url, opts);
			System.out.println("bitmap1 width=" + bitmap.getWidth());
			System.out.println("bitmap1 height=" + bitmap.getHeight());
			L.d("MEM", "4:" + Debug.getNativeHeapAllocatedSize());
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return bitmap;
	}

	static int computeSampleSize(BitmapFactory.Options options, int target) {
		int w = options.outWidth;
		int h = options.outHeight;
		int candidateW = w / target;
		int candidateH = h / target;
		int candidate = Math.max(candidateW, candidateH);
		L.d("down", w + "|" + h + "|" + candidate);
		if (candidate == 0)
			return 1;
		if (candidate > 1) {
			if ((w > target) && (w / candidate) < target)
				candidate -= 1;
		}
		if (candidate > 1) {
			if ((h > target) && (h / candidate) < target)
				candidate -= 1;
		}
		return candidate;
	}

	public static byte[] convertBitmapToBinaryArray(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, bos);
		byte[] bitmapData = bos.toByteArray();
		return bitmapData;
	}

	private static char PAD = '=';
	private static final char[] ENCODE64 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '+', '/' };

	public static String encodeBase64(byte[] data) {
		if (data == null) {
			return "";
		}

		char[] charBuffer = new char[(data.length + 2) / 3 * 4];
		int position = 0;
		int _3byte = 0;
		for (int i = 0; i < data.length - 2; i += 3) {
			_3byte = ((data[i] & 0xFF) << 16) + ((data[i + 1] & 0xFF) << 8)
					+ (data[i + 2] & 0xFF);
			charBuffer[position++] = ENCODE64[_3byte >> 18];
			charBuffer[position++] = ENCODE64[(_3byte >> 12) & 0x3F];
			charBuffer[position++] = ENCODE64[(_3byte >> 6) & 0x3F];
			charBuffer[position++] = ENCODE64[_3byte & 0x3F];
		}
		switch (data.length % 3) {
		case 1: // [111111][11 0000][0000 00][000000]
			_3byte = ((data[data.length - 1] & 0xFF) << 16);
			charBuffer[position++] = ENCODE64[_3byte >> 18];
			charBuffer[position++] = ENCODE64[(_3byte >> 12) & 0x3F];
			charBuffer[position++] = PAD;
			charBuffer[position++] = PAD;
			break;
		case 2: // [111111][11 1111][1111 00][000000]
			_3byte = ((data[data.length - 2] & 0xFF) << 16)
					+ ((data[data.length - 1] & 0xFF) << 8);
			charBuffer[position++] = ENCODE64[_3byte >> 18];
			charBuffer[position++] = ENCODE64[(_3byte >> 12) & 0x3F];
			charBuffer[position++] = ENCODE64[(_3byte >> 6) & 0x3F];
			charBuffer[position++] = PAD;
			break;
		}

		return new String(charBuffer);
	}

	public static Bitmap decodeByteArray(byte[] bytes) throws Exception {
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = 1;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, o2);
	}

	private final static int IMAGE_MAX_SIZE = 800;

	public static Bitmap decodeFile(String path) throws Exception {
		Bitmap b = null;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, o);
		int scale = 1;
		if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
			// scale = (int) Math.pow(
			// 2,
			// (int) Math.round(Math.log(IMAGE_MAX_SIZE
			// / (double) Math.max(o.outHeight, o.outWidth))
			// / Math.log(0.5)));
			scale = getScaleSize(o);
		}
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		o2.inJustDecodeBounds = false;
		b = BitmapFactory.decodeFile(path, o2);
		return b;
	}

	private static int getScaleSize(BitmapFactory.Options options) {
		int scale = 1;
		if (options.outWidth > 0 && options.outHeight > 0) {
			// Now see how much we need to scale it down.
			int widthFactor = (options.outWidth + IMAGE_MAX_SIZE - 1)
					/ IMAGE_MAX_SIZE;
			int heightFactor = (options.outHeight + IMAGE_MAX_SIZE - 1)
					/ IMAGE_MAX_SIZE;

			widthFactor = Math.max(widthFactor, heightFactor);
			widthFactor = Math.max(widthFactor, 1);

			// Now turn it into a power of two.
			if (widthFactor > 1) {
				if ((widthFactor & (widthFactor - 1)) != 0) {
					while ((widthFactor & (widthFactor - 1)) != 0) {
						widthFactor &= widthFactor - 1;
					}

					widthFactor <<= 1;
				}
			}
			scale = widthFactor;
		}
		return scale;
	}
}
