package com.example.sleepbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.widget.Toast;

public class SendNotificationSMS extends Activity {

	static final String[] CONTACT_LIST = { "91110021", "96797873", "86131486", "92383266", "91110021" };
	private int count;
	private String values;
	private Bundle b;
	private String smsCheat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_notification_sms);

		// Extract values from Bundle
		b = this.getIntent().getExtras();
		if (b != null) {
			smsCheat = b.getString("sms");
			// Toast.makeText(getApplicationContext(), "AlarmActivityTwo" +
			// gameType + "|" + snoozeDuration, Toast.LENGTH_SHORT).show();
		}
		
		sendSMS();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_notification_sm, menu);
		return true;
	}

	private void sendSMS() {
		String msg = "My dear buddy, I might have fallen asleep again! Call me to wake me up!";

		SmsManager sm = SmsManager.getDefault();
		processSMSCheat();

		int idx = -1;
		for (int i = 0; i < count; i++) {
			if (values.length() > 1) {
				idx = Integer.parseInt(values.substring(0, 1));
				values = values.substring(1, values.length());
			} else {
				idx = Integer.parseInt(values);
			}

			sm.sendTextMessage(CONTACT_LIST[idx], null, msg, null, null);
		}

		Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();
		
	}

	private void processSMSCheat() {
		if (smsCheat.equals("0")) {
			// do nth
		} else if (smsCheat.length() >= 2) {
			String s = smsCheat.substring(0, 1);
			String t = smsCheat.substring(1, smsCheat.length());
			count = Integer.parseInt(s);
			values = t;
			// Toast.makeText(getApplicationContext(), s+ " | " + t,
			// Toast.LENGTH_SHORT).show();
		}
	}

}
