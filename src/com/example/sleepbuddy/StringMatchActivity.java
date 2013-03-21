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

	private String[] qnText = { "So call me maybe", "Shine bright like a diamond", "Sorry for party rocking",
			"Oppa gangnam style", "Sexy and I know it" };
	private String[] qnText1 = { "call me 90122923", "diamond sparkles 029324", "Sorry for 21323123",
			"My 00000000 style", "118899931 I know it" };
	private String[] qnText3 = { "S0 c@ll me mAyb3", "Sh!n3 brlghT lik3 @ d1am0nD", "S0rrY fOr p@rty_r0ck!nG~",
			"!OpB@ (gAnGnaM StyL3) ^-^", "[^_^] SeXy @nD 1 kn0w lt" };
	private String[] qnText2 = { "Lorem ipsum dolor sit amet", "consectetur adipisicing elit", "sed do eiusmod tempor incididunt",
			"ut labore magna aliqua", "cillum dolore eu fugiat" };
	private EditText answerField;
	private String qn;
	private int level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_string_match);

		// Extract game level
		Bundle b = this.getIntent().getExtras();
		if (b != null) {
			level = b.getInt("level");
		}
 
		TextView qnField = (TextView) findViewById(R.id.stringMatchQn);
		setQn();
		qnField.setText(qn);
		answerField = (EditText) findViewById(R.id.stringMatchInput);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.string_match, menu);
		return true;
	}

	private void setQn() {
		int index = (int) (Math.floor(Math.random() * 4)) + 1;
		String questionText = "";
		switch (level) {
		case 0:
			questionText = qnText[index];
			break;
		case 1:
			questionText = qnText1[index];
			break;
		case 2:
			questionText = qnText2[index];
			break;
		case 3:
			questionText = qnText3[index];
			break;
		default:
			questionText = "ERROR ENCOUNTERED! :D";
			break;
		}
		qn = questionText;
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
