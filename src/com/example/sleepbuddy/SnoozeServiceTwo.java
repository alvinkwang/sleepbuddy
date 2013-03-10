package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SnoozeServiceTwo extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(getApplicationContext(), "SnoozeServiceTwo", Toast.LENGTH_SHORT).show();
		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivityTwo.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplication().startActivity(dialogIntent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}