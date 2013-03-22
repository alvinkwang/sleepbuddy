package com.example.sleepbuddy;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class DetectUserAwakeService extends Service {

	private String smsCheat = "0";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {

		Dialog dialog = AwakeConfirmationActivity.getDialog();
		if (dialog.isShowing()) {
			dialog.dismiss();

			// Extract values from Bundle
			Bundle b = intent.getExtras();
			if (b != null) {
				smsCheat = b.getString("smsCheat");
			}
			
			Intent dialogIntent = new Intent(getBaseContext(), SendNotificationSMS.class);
			dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			dialogIntent.putExtra("sms", smsCheat);
			getApplication().startActivity(dialogIntent);
		}
	}

}