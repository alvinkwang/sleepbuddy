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
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class AlarmActivity extends Activity {

	private static final int RESULT_MATH_SUM = 1;
	private static MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		playAlarm();
		createAlertDialog();

	}

	private void playAlarm() {
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_siren);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
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
				snooze(3);
				mediaPlayer.stop();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void snooze(int snoozeDurationSeconds) {
		int snoozeCnt = 0;
		// Extract information from bundle
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			snoozeCnt = extras.getInt("snooze");
		}

		if (snoozeCnt < 3) {
			Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
			PendingIntent pendingIntent = PendingIntent.getService(AlarmActivity.this, 0, intent, 0);
			
//			Toast.makeText(getApplicationContext(), snoozeCnt, Toast.LENGTH_SHORT).show();

			int newSnoozeCnt = snoozeCnt++;
			// Pass SnoozeCount to next Intent
			intent.putExtra("snooze", newSnoozeCnt);

//			Toast.makeText(getApplicationContext(), newSnoozeCnt, Toast.LENGTH_SHORT).show();
			
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, snoozeDurationSeconds);
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

		} else { // sends SMS message
			Toast.makeText(getApplicationContext(), "STOP SNOOZING!", Toast.LENGTH_SHORT).show();
		}
		finish();
	}

	private void displaySelectedGame() {
		// FIXME: Retrieve game type from alarm class
		int gameType = 0;
		Intent intent;
		switch (gameType) {
		case 0:
			intent = new Intent(this, MathSumActivity.class);
			startActivityForResult(intent, RESULT_MATH_SUM);
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
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	}

	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

}
