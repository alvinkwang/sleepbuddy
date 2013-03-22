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
	
//	private void sendSMS() {
//		String msg = "My dear buddy! Call me to wake me up!";
//		
//		SmsManager sm = SmsManager.getDefault();
//		processSMSCheat();
//
//		int idx = -1;
//		for (int i = 0; i < count; i++) {
//			if (values.length() > 1) {
//				idx = Integer.parseInt(values.substring(0,1));
//				values = values.substring(1, values.length());
//			} else {
//				idx = Integer.parseInt(values);
//			}
//			
//			sm.sendTextMessage(CONTACT_LIST[idx], null, msg, null, null);
//		}
//
//		Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();
//
//		createSMSSentDialog();
//	}
//
//	private void processSMSCheat() {
//		if (smsCheat.equals("0")) {
//			// do nth
//		} else if (smsCheat.length() >= 2) {
//			String s = smsCheat.substring(0,1);
//			String t = smsCheat.substring(1, smsCheat.length());
//			count = Integer.parseInt(s);
//			values = t;
////			Toast.makeText(getApplicationContext(), s+ " | " + t, Toast.LENGTH_SHORT).show();
//		}
//	}
}