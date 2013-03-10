package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class AlarmService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		int gameType = 0;
		int snoozeDuration = 0;
		Bundle b = intent.getExtras();
		if (b != null) {
			gameType = b.getInt("gameType");
			snoozeDuration = b.getInt("snooze");
		}

		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivity.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dialogIntent.putExtra("game", gameType);
		dialogIntent.putExtra("snooze", snoozeDuration);
		getApplication().startActivity(dialogIntent);
		
	}
}
