package com.egame.app.widgets;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 类说明：
 * 
 * @创建时间 2012-1-9 上午11:15:47
 * @创建人： 陆林
 * @邮箱：15366189868@189.cn
 */
public class SensorGet implements SensorEventListener {
	public static final int TYPE_HUANDONG = 1;
	// 这个控制精度，越小表示反应越灵敏
	public int linmin = 500;

	private Context mContext;

	public SensorGet(Context c, OnSensor onSensor) {
		mContext = c;
		this.onSensor = onSensor;
		sm = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
	}

	private SensorManager sm;

	public void register() {
		initShake();
		sm.registerListener(this,
				sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
	}

	public void unRegister() {
		sm.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) {
			// 获取加速度传感器的三个参数
			float x = event.values[SensorManager.DATA_X];
			float y = event.values[SensorManager.DATA_Y];
			float z = event.values[SensorManager.DATA_Z];
			// 获取当前时刻的毫秒数
			curTime = System.currentTimeMillis();
			// 100毫秒检测一次
			if ((curTime - lastTime) > 100) {
				if (last_x == 0.0f && last_y == 0.0f && last_z == 0.0f) {
				} else {
					// 单次晃动幅度
					shake = (Math.abs(x - last_x) + Math.abs(y - last_y) + Math
							.abs(z - last_z));
				}

				// 把每次的晃动幅度相加，得到总体晃动幅度
				totalShake += shake;
				// 判断是否为摇动

				if (shake > 20) {
					if (onSensor != null) {
						onSensor.onSensor();
					}
					initShake();
				}
				last_x = x;
				last_y = y;
				last_z = z;
				lastTime = curTime;

			}
		}
	}

	private OnSensor onSensor;

	public interface OnSensor {
		public void onSensor();
	}

	private float last_x, last_y, last_z, shake, totalShake;
	private long lastTime, curTime;

	private void initShake() {
		lastTime = 0;
		curTime = 0;
		last_x = 0.0f;
		last_y = 0.0f;
		last_z = 0.0f;
		shake = 0.0f;
		totalShake = 0.0f;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
