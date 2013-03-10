package com.example.sleepbuddy;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class StringMatchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_string_match);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.string_match, menu);
		return true;
	}
	
	public void stopAlarm(View view) {
		MediaPlayer mp = AlarmActivity.getMediaPlayer();
		if (isAnswerCorrect()) {
			if (mp.isPlaying()) {
				AlarmActivity.getMediaPlayer().stop();
			}
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_OK, resultIntent);
			finish();

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			
			Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Incorrect Answer.", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isAnswerCorrect() {
		//FIXME
		return true;
	}

}
