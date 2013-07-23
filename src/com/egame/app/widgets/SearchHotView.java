package com.egame.app.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.egame.utils.common.PreferenceUtil;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-26 下午05:16:15
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class SearchHotView extends View implements Runnable {
	// 当前状态 - 普通状态
	public final static byte STATUS_NONE = 0;
	// 当前状态 - 准备状态，初始化位置
	public final static byte STATUS_READY = 1;
	// 当前状态 - 移动状态，位置变化
	public final static byte STATUS_MOVE = 2;

	// 四周进入动画类型
	public final static byte ANIM_AROUND = 0;
	// 中间扩散动画类型
	public final static byte ANIM_CENTER = 1;

	// 顶部空出像数,防止小屏顶部溢出
	public final static byte DRAW_TOP = 20;

	// 关键字移动次数
	public final static int MOVE_COUNT = 10;

	// 显示关键字数
	public final static int HOT_COUNT = 12;

	// 基础文字大小,小
	public final static int BASE_SIZE_L = 12;
//	 基础文字大小,中
	public final static int BASE_SIZE_M = 16;
	// 基础文字大小,大
	public final static int BASE_SIZE_H = 20;
	
//	// 基础文字大小,小
//	public final static int BASE_SIZE_L = 24;
//	// 基础文字大小,中
//	public final static int BASE_SIZE_M = 32;
//	// 基础文字大小,大
//	public final static int BASE_SIZE_H = 40;

	private int mBaseSize = BASE_SIZE_H;

	// 浮动文字大小
	public final static int UNSTEADY_SIZE = 3;

	// 行数
	public final static int ROW = 5;

	private byte mAnimType = ANIM_AROUND;

	private List<String> mKeys = new ArrayList<String>();

	private HotKey[] mHotKeys = new HotKey[HOT_COUNT];

	private byte mStatus = STATUS_NONE;
	private int mCount;
	private Random mRandom;

	private boolean mRun;

	private Paint mBgPaint;

	public SearchHotView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mRandom = new Random();

		String labels = PreferenceUtil.getSearchLabel(context);
		if (!TextUtils.isEmpty(labels)) {
			String[] strings = labels.split("_");
			if (strings != null) {
				for (String s : strings) {
					mKeys.add(s);
				}
			}
		}

		for (int i = 0; i < mHotKeys.length; i++) {
			mHotKeys[i] = new HotKey();
		}

		DisplayMetrics dm2 = getResources().getDisplayMetrics();
		if (dm2.widthPixels <= 240) {
			mBaseSize = (int) ((dm2.density)*BASE_SIZE_L);
		} else if (dm2.widthPixels <= 320) {
			mBaseSize = (int) ((dm2.density)*BASE_SIZE_M);
		} else {
			mBaseSize = (int) ((dm2.density)*BASE_SIZE_H);
		}

		mBgPaint = new Paint();
		mBgPaint.setColor(0xfff1f1f1);
	}

	public HotKey[] getHotKeys() {
		return mHotKeys;
	}

	private int getKeySize() {
		return mBaseSize + mRandom.nextInt(UNSTEADY_SIZE);
	}

	private int[] mTextColors = { 0xff388216, 0xff0c00ff, 0xffe59f07,
			0xffb90808, 0xff0084ff, 0xffff0000, 0xff1e1e1e, 0xff079691 };

	private int getKeyColor() {
		return mTextColors[mRandom.nextInt(mTextColors.length)];
	}

	public void setHotKey() {
		if (mStatus == STATUS_NONE) {
			if (mKeys.size() >= mHotKeys.length) {
				List<String> hotKeys = new ArrayList<String>();
				hotKeys.addAll(mKeys);
				for (int i = 0; i < mHotKeys.length; i++) {
					String text = hotKeys
							.remove(mRandom.nextInt(hotKeys.size()));
					int size = getKeySize();
					int color = getKeyColor();
					mHotKeys[i].setText(text, size, color);
				}
				mStatus = STATUS_READY;
				mCount = 0;
			}
		}
	}

	public void begin() {
		mRun = true;
		new Thread(this).start();
		setHotKey();
	}

	public void end() {
		mRun = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		switch (mStatus) {
		case STATUS_NONE: {
			for (int i = 0; i < mHotKeys.length; i++) {
				mHotKeys[i].drawString(canvas);
			}
		}
			break;
		case STATUS_READY: {
			// 0 1 2
			// - - -
			// 3 4
			// - -
			// 5 6 7
			// - - -
			// 8
			// -
			// 9 10 11
			// - - -
			mAnimType = (byte) mRandom.nextInt(2);
			// 每行的高度
			int row = height / ROW;
			for (int i = 0; i < mHotKeys.length; i++) {
				HotKey hotKey = mHotKeys[i];
				// 左上
				if (i == 0) {
					// 第1行列的宽度
					int column = width / 3;
					int beginX = 0;
					int beginY = 0;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 0在第1行，第1列
					hotKey.initLocation(beginX, beginY,
							(column - hotKey.width) / 2,
							(row - hotKey.height) / 2);
				} else if (i == 1) {
					// 第1行列的宽度
					int column = width / 3;
					int beginX = 0;
					int beginY = 0;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 1在第1行，第2列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column, (row - hotKey.height) / 2);
				} else if (i == 3) {
					// 第1行列的宽度
					int column = width / 2;
					int beginX = 0;
					int beginY = 0;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 3在第2行，第1列
					hotKey.initLocation(beginX, beginY,
							(column - hotKey.width) / 2, (row - hotKey.height)
									/ 2 + row);
				}
				// 右上
				else if (i == 2) {
					// 第1行列的宽度
					int column = width / 3;
					int beginX = width - hotKey.width;
					int beginY = 0;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 2在第1行，第3列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column * 2, (row - hotKey.height) / 2);
				} else if (i == 4) {
					// 第2行列的宽度
					int column = width / 2;
					int beginX = width - hotKey.width;
					int beginY = 0;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 4在第2行，第2列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column, (row - hotKey.height) / 2 + row);
				} else if (i == 7) {
					// 第3行列的宽度
					int column = width / 3;
					int beginX = width - hotKey.width;
					int beginY = 0;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 7在第3行，第3列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column * 2, (row - hotKey.height) / 2 + row
							* 2);
				}
				// 左下
				else if (i == 5) {
					// 第3行列的宽度
					int column = width / 3;
					int beginX = 0;
					int beginY = height - hotKey.height;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 5在第3行，第1列
					hotKey.initLocation(beginX, beginY,
							(column - hotKey.width) / 2, (row - hotKey.height)
									/ 2 + row * 2);
				} else if (i == 8) {
					// 第4行列的宽度
					int column = width;
					int beginX = 0;
					int beginY = height - hotKey.height;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 8在第4行，第1列
					hotKey.initLocation(beginX, beginY,
							(column - hotKey.width) / 2, (row - hotKey.height)
									/ 2 + row * 3);
				} else if (i == 9) {
					// 第5行列的宽度
					int column = width / 3;
					int beginX = 0;
					int beginY = height - hotKey.height;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 9在第5行，第1列
					hotKey.initLocation(beginX, beginY,
							(column - hotKey.width) / 2, (row - hotKey.height)
									/ 2 + row * 4);
				}
				// 右下
				else if (i == 6) {
					// 第3行列的宽度
					int column = width / 3;
					int beginX = width - hotKey.width;
					int beginY = height - hotKey.height;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 6在第3行，第2列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column, (row - hotKey.height) / 2 + row * 2);
				} else if (i == 10) {
					// 第5行列的宽度
					int column = width / 3;
					int beginX = width - hotKey.width;
					int beginY = height - hotKey.height;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 10在第5行，第2列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column, (row - hotKey.height) / 2 + row * 4);
				} else if (i == 11) {
					// 第5行列的宽度
					int column = width / 3;
					int beginX = width - hotKey.width;
					int beginY = height - hotKey.height;
					if (mAnimType == ANIM_CENTER) {
						beginX = (width - hotKey.width) / 2;
						beginY = (height - hotKey.height) / 2;
					}
					// 11在第5行，第3列
					hotKey.initLocation(beginX, beginY, (column - hotKey.width)
							/ 2 + column * 2, (row - hotKey.height) / 2 + row
							* 4);
				}
			}
			mStatus = STATUS_MOVE;
			mCount = 0;
		}
			break;
		case STATUS_MOVE: {
			for (int i = 0; i < mHotKeys.length; i++) {
				mHotKeys[i].move();
				mHotKeys[i].drawString(canvas);
			}
			mCount++;
			if (mCount >= MOVE_COUNT) {
				for (int i = 0; i < mHotKeys.length; i++) {
					mHotKeys[i].end();
				}
				mStatus = STATUS_NONE;
				mCount = 0;
			}
		}
			break;
		}
	}

	public void run() {
		while (mRun) {
			try {
				Thread.sleep(100);
				postInvalidate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
