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
import android.os.Bundle;
import android.view.Menu;

public class AwakeConfirmationActivity extends Activity {

	private static AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_awake_confirmation);

		displayAwakeConfirmationDialog();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.awake_confirmation, menu);
		return true;
	}

	private void displayAwakeConfirmationDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
		String currentDateandTime = sdf.format(new Date());

		builder.setTitle(R.string.dialog_title_awake_confirmation);
		// FIXME: Display user selected buddy in notification
		String msg = "Are you awake? Time: " + currentDateandTime;
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.dialog_button_dismiss, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
			}
		});

		dialog = builder.create();
		dialog.show();

		checkIfUserAwake();
	}

	public static AlertDialog getDialog() {
		return dialog;
	}

	private void checkIfUserAwake() {
		Intent intent = new Intent(this, DetectUserAwakeService.class);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// FIXME: Set to pop up after 15mins
		// calendar.add(Calendar.SECOND, 15*60);
		calendar.add(Calendar.SECOND, MainActivity.TEST_IS_USER_AWAKE_DURATION);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

	}
}
