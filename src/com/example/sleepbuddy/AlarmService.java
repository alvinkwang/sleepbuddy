package com.example.sleepbuddy;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends Service {

	@Override
	public void onCreate() {
//		Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
	}

	@Override
	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
		return null;

	}

	@Override
	public void onDestroy() {

		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {
//		int snoozeCnt = 0;
//		
//		Bundle bundle = intent.getExtras();
//		if (bundle != null) {
//			snoozeCnt = bundle.getInt("snooze");
//		}

		Intent dialogIntent = new Intent(getBaseContext(), AlarmActivity.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		dialogIntent.putExtra("snooze", 2);
		getApplication().startActivity(dialogIntent);

	}

	@Override
	public boolean onUnbind(Intent intent) {

		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
		return super.onUnbind(intent);

	}
}
