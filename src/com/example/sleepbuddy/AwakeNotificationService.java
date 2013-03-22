package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class AwakeNotificationService extends Service {

	private String s;
	
	@Override
	public void onStart(Intent intent, int startId) {
		
		Bundle b = intent.getExtras();
		if (b != null) {
			s = b.getString("smsCheat");
		}
		
		Intent dialogIntent = new Intent(getBaseContext(), AwakeConfirmationActivity.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dialogIntent.putExtra("sms", s);
		getApplication().startActivity(dialogIntent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
