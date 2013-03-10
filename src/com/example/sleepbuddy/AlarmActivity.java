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
	private static final int RESULT_STRING_MATCH = 2;
	private static MediaPlayer mediaPlayer;
	private Bundle b;
	private int gameType;
	private int snoozeDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		// Extract values from Bundle
		b = this.getIntent().getExtras();
		if (b != null) {
			gameType = b.getInt("game");
			snoozeDuration = b.getInt("snooze");
			Toast.makeText(getApplicationContext(), "AlarmActivity: " + gameType + "|" + snoozeDuration, Toast.LENGTH_SHORT)
					.show();
		}

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
				displaySelectedGame(gameType);
			}
		});
		builder.setNegativeButton(R.string.dialog_button_snooze, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked SNOOZE button

				// FIXME: Retrieve snoozeDuration based on alarm created
				snooze(snoozeDuration);
				mediaPlayer.stop();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void snooze(int snoozeDurationSeconds) {
		Intent intent = new Intent(AlarmActivity.this, SnoozeService.class);
		intent.putExtra("gameType", gameType);
		intent.putExtra("snooze", snoozeDuration);
//		Toast.makeText(getApplicationContext(), "Send: " + gameType + "|" + snoozeDuration, Toast.LENGTH_SHORT).show();
		PendingIntent pendingIntent = PendingIntent.getService(AlarmActivity.this, 0, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, snoozeDurationSeconds);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

		finish();
	}

	private void displaySelectedGame(int gameType) {
		Intent intent;
		switch (gameType) {
		case 0:
			intent = new Intent(this, MathSumActivity.class);
			startActivityForResult(intent, RESULT_MATH_SUM);
			break;
		case 1:
			intent = new Intent(this, StringMatchActivity.class);
			startActivityForResult(intent, RESULT_STRING_MATCH);
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
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

}
