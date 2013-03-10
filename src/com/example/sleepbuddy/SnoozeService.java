package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class SnoozeService extends Service {

	private int gameType;
	private int snoozeDuration;

	@Override
	public void onStart(Intent intent, int startId) {
		Bundle b = intent.getExtras();
		if (b != null) {
			gameType = b.getInt("gameType");
			snoozeDuration = b.getInt("snooze");
//			Toast.makeText(getApplicationContext(), "Service: " + gameType + "|" + snoozeDuration, Toast.LENGTH_SHORT)
//					.show();
		}
		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivityOne.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dialogIntent.putExtra("gameType", gameType);
		dialogIntent.putExtra("snooze", snoozeDuration);
		getApplication().startActivity(dialogIntent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
