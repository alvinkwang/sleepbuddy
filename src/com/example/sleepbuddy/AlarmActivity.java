package com.example.sleepbuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;

public class AlarmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		playAlarm();
		createAlertDialog();

	}

	private void playAlarm() {
		MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oldschool_alarm);
		mediaPlayer.start();
	}

	private void createAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setPositiveButton(R.string.dialog_button_dismiss, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });
		builder.setNegativeButton(R.string.dialog_button_snooze, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

}
