package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SnoozeService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(getApplicationContext(), "SnoozeService", Toast.LENGTH_SHORT).show();
		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivityOne.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplication().startActivity(dialogIntent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
