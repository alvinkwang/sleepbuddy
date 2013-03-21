package com.example.sleepbuddy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.widget.Toast;

public class AlarmActivityThree extends Activity implements OnPreparedListener {

	private static final int RESULT_MATH_SUM = 1;
	private static final int RESULT_STRING_MATCH = 2;
	private static final int RESULT_SHAKER = 3;
	private MediaPlayer mp;
	private int gameType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_activity_three);

		// Extract values from Bundle
		Bundle b = this.getIntent().getExtras();
		if (b != null) {
			gameType = b.getInt("gameType");
			// Toast.makeText(getApplicationContext(), "AlarmActivityThree" +
			// gameType + "|0", Toast.LENGTH_SHORT).show();
		}

		mp = AlarmActivity.getMediaPlayer();
		playAlarm();
		createAlertDialog();
	}

	private void playAlarm() {
		mp.setOnPreparedListener(this);
		mp.prepareAsync();
	}

	public void onPrepared(MediaPlayer player) {
		player.start();
	}

	private void createAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
		String currentDateandTime = sdf.format(new Date());

		builder.setTitle(R.string.dialog_title_alarm);
		builder.setMessage(currentDateandTime);
		builder.setPositiveButton(R.string.dialog_button_dismiss, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked DISMISS button
				displaySelectedGame();
			}
		});
		builder.setNegativeButton(R.string.dialog_button_snooze, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked SNOOZE button
				mp.stop();
				sendSMS();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void sendSMS() {

		SmsManager sm = SmsManager.getDefault();
		String contactNum = "91110021";
		String msg = "My dear buddy! Call me to wake me up!";
		sm.sendTextMessage(contactNum, null, msg, null, null);
		Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();

		createSMSSentDialog();
	}

	private void createSMSSentDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
		String currentDateandTime = sdf.format(new Date());

		builder.setTitle(R.string.dialog_title_sms_sent);
		// FIXME: Display user selected buddy in notification
		String msg = "A SMS has been sent to Alvin Kwang at " + currentDateandTime;
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.dialog_button_dismiss, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void displaySelectedGame() {
		Intent intent;
		switch (gameType) {
		case 0:
			intent = new Intent(this, MathSumActivity.class);
			intent.putExtra("level", 3);
			startActivityForResult(intent, RESULT_MATH_SUM);
			break;
		case 1:
			intent = new Intent(this, StringMatchActivity.class);
			intent.putExtra("level", 3);
			startActivityForResult(intent, RESULT_STRING_MATCH);
			break;
		case 2:
			intent = new Intent(this, ShakerActivity.class);
			intent.putExtra("level", 3);
			startActivityForResult(intent, RESULT_SHAKER);
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (RESULT_MATH_SUM): {
			if (resultCode == Activity.RESULT_OK) {
				finish();
			}
			break;
		}
		case (RESULT_STRING_MATCH): {
			if (resultCode == Activity.RESULT_OK) {
				finish();
			}
			break;
		}
		case (RESULT_SHAKER): {
			if (resultCode == Activity.RESULT_OK) {
			}
			break;
		}
		}

		startAwakeNotificationService();
		finish();
	}

	private void startAwakeNotificationService() {
		Intent intent = new Intent(this, AwakeNotificationService.class);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// FIXME: Set to pop up after 15mins
		// calendar.add(Calendar.SECOND, 15*60);
		calendar.add(Calendar.SECOND, MainActivity.TEST_AWAKE_NOTIFICATION_DURATION);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_activity_one, menu);
		return true;
	}

}
