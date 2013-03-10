package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SnoozeServiceTwo extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivityTwo.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dialogIntent.putExtras(intent.getExtras());
		getApplication().startActivity(dialogIntent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}