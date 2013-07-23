package com.egame.app.widgets;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

/**
 * 类说明：
 * 
 * @创建时间 2011-12-27 上午10:56:52
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class HotKey {

	private String text;
	private int x;
	private int y;
	private int offsetY;
	public int width;
	public int height;
	private int beginX;
	private int beginY;
	private int endX;
	private int endY;
	private Paint paint;
	private boolean draw;

	public HotKey() {
		text = "";
		paint = new Paint();
		draw = false;
	}

	public void setText(String text, int size, int color) {
		this.text = text;
		paint.setColor(color);
		paint.setTextSize(size);
		paint.setAntiAlias(true);
		width = (int) paint.measureText(text);
		FontMetrics fontMetrics = paint.getFontMetrics();
		height = (int) (fontMetrics.bottom - fontMetrics.top);
		offsetY = (int) fontMetrics.top;
	}

	public void initLocation(int beginX, int beginY, int endX, int endY) {
		this.x = beginX;
		this.y = beginY + SearchHotView.DRAW_TOP;
		this.beginX = beginX;
		this.beginY = beginY + SearchHotView.DRAW_TOP;
		this.endX = endX;
		this.endY = endY + SearchHotView.DRAW_TOP;
		draw = true;
	}

	public String getText() {
		return text;
	}

	public void move() {
		if (beginX < endX) {
			x += (endX - beginX) / SearchHotView.MOVE_COUNT;
		} else {
			x -= (beginX - endX) / SearchHotView.MOVE_COUNT;
		}
		if (beginY < endY) {
			y += (endY - beginY) / SearchHotView.MOVE_COUNT;
		} else {
			y -= (beginY - endY) / SearchHotView.MOVE_COUNT;
		}
	}

	public void end() {
		x = endX;
		y = endY;
	}

	public boolean inText(float pX, float pY) {
		if (pX >= x && pX <= x + width && pY >= y + offsetY
				&& pY <= y + offsetY + height)
			return true;
		return false;
	}

	public void drawString(Canvas canvas) {
		if (draw)
			canvas.drawText(text, x, y, paint);
	}
}
