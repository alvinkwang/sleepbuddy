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
import android.view.Menu;

public class AlarmActivityTwo extends Activity implements OnPreparedListener {

	private static final int RESULT_MATH_SUM = 1;
	private static final int RESULT_STRING_MATCH = 2;
	private static final int RESULT_SHAKER = 3;
	private MediaPlayer mp;
	private Bundle b;
	private int gameType;
	private int snoozeDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_activity_two);

		// Extract values from Bundle
		b = this.getIntent().getExtras();
		if (b != null) {
			gameType = b.getInt("gameType");
			snoozeDuration = b.getInt("snooze");
			// Toast.makeText(getApplicationContext(), "AlarmActivityTwo" +
			// gameType + "|" + snoozeDuration, Toast.LENGTH_SHORT).show();
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

				// FIXME: Retrieve snoozeDuration based on alarm created
				snooze(snoozeDuration);
				mp.stop();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void displaySelectedGame() {
		Intent intent;
		if (gameType == 0) {
			gameType = (int) (Math.floor(Math.random() * 3) + 1);
		}
		switch (gameType) {
		case 1:
			intent = new Intent(this, MathSumActivity.class);
			intent.putExtra("level", 2);
			startActivityForResult(intent, RESULT_MATH_SUM);
			break;
		case 2:
			intent = new Intent(this, StringMatchActivity.class);
			intent.putExtra("level", 2);
			startActivityForResult(intent, RESULT_STRING_MATCH);
			break;
		case 3:
			intent = new Intent(this, ShakerActivity.class);
			intent.putExtra("level", 2);
			startActivityForResult(intent, RESULT_SHAKER);
			break;
		}

	}

	private void snooze(int snoozeDurationSeconds) {
		Intent intent = new Intent(this, SnoozeServiceThree.class);
		intent.putExtra("gameType", gameType);
		intent.putExtra("snooze", snoozeDuration);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, snoozeDurationSeconds);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

		finish();
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
