package com.example.sleepbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShakerActivity extends Activity implements SensorEventListener {
	private static final int[] difficulty = { 30, 40, 50 };
	private int targetShakes = 0;
	private int shakeCounter = 0;
	private TextView shakeCount;
	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;

	private SensorManager mSensorManager;

	private Sensor mAccelerometer;

	private final float NOISE = (float) 2.0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shaker);
		mInitialized = false;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

		TextView targetShakesText = (TextView) findViewById(R.id.targetShakes);
		targetShakesText.setText(difficulty[0] + " shakes");
		targetShakes = difficulty[0];
		shakeCount = (TextView) findViewById(R.id.shakeCounter);
		shakeCount.setText(Integer.toString(shakeCounter) + " shakes");
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	private void updateShakeCounter() {
		shakeCount.setText(Integer.toString(shakeCounter) + " shakes");
		if (checkShakeReached()) {
			stopAlarm();
		}
	}

	private boolean checkShakeReached() {
		if (shakeCounter >= targetShakes) {
			return true;
		}
		return false;
	}

	public void stopAlarm() {
		MediaPlayer mp = AlarmActivity.getMediaPlayer();
		if (mp.isPlaying()) {
			AlarmActivity.getMediaPlayer().stop();
		}
		Intent resultIntent = new Intent();
		setResult(Activity.RESULT_OK, resultIntent);
		finish();

		Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			mInitialized = true;
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
			if (deltaX < NOISE)
				deltaX = (float) 0.0;
			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;
			mLastX = x;
			mLastY = y;
			mLastZ = z;

			if (deltaX > 3.5 || deltaY > 3.5 || deltaZ > 3.5) {
				shakeCounter = shakeCounter + 1;
				updateShakeCounter();
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}
}