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
		
		Bundle b = intent.getExtras();
		if (b != null) {
			gameType = b.getInt("gameType");
		}

		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivity.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dialogIntent.putExtra("game", gameType);
		getApplication().startActivity(dialogIntent);
		
	}
}
