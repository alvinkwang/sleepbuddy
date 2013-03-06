package com.example.sleepbuddy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class AlarmActivity extends Activity {

	private MediaPlayer mediaPlayer;

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
				// User clicked OK button
				Toast.makeText(getApplicationContext(), "DISMISS", Toast.LENGTH_SHORT).show();
				mediaPlayer.stop();
			}
		});
		builder.setNegativeButton(R.string.dialog_button_snooze, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
				mediaPlayer.stop();
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
