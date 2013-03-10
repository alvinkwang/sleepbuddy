package com.example.sleepbuddy;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class DetectUserAwakeService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {

		Dialog dialog = AwakeConfirmationActivity.getDialog();
		if (dialog.isShowing()) {
			dialog.dismiss();
			sendSMS();
		}
	}

	private void sendSMS() {

		SmsManager sm = SmsManager.getDefault();
		String contactNum = "91110021";
		String msg = "Alvin has snoozed 3 times!";
		sm.sendTextMessage(contactNum, null, msg, null, null);
		Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();

	}
}