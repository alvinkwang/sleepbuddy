package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class SnoozeServiceTwo extends Service {

	private int gameType;
	private int snoozeDuration;

	@Override
	public void onStart(Intent intent, int startId) {
		Bundle b = intent.getExtras();
		if (b != null) {
			gameType = b.getInt("gameType");
			snoozeDuration = b.getInt("snooze");
			// Toast.makeText(getApplicationContext(), "OMFG: " + gameType + "|"
			// + snooze, Toast.LENGTH_SHORT).show();
		}
		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivityTwo.class);
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