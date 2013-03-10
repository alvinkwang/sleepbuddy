package com.example.sleepbuddy;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StringMatchActivity extends Activity {

	private String[] qnText = {"So call me maybe", "Shine bright like a diamond", "Sorry for party rocking", "Oppa gangnam style", "Sexy and I know it"};
	private EditText answerField; 
	private String qn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_string_match);
		
		TextView qnField = (TextView) findViewById(R.id.stringMatchQn);
		int qnIndex = (int) (Math.floor(Math.random()*4))+1;
		qn = qnText[qnIndex];
		qnField.setText(qn);
		answerField = (EditText) findViewById(R.id.stringMatchInput);
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

			Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Incorrect Answer.", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isAnswerCorrect() {
		String userAns = answerField.getText().toString();
		if (qn.equals(userAns.trim())) {
			return true;
		}
		return false;
	}

}
