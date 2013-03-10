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

public class MathSumActivity extends Activity {

	private String displayQn = "";
	private int solution;
	private int userAnswer;
	private EditText solutionField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_math_sum);

		generateMathSum(0);
		TextView qnField = (TextView) findViewById(R.id.mathSum);
		qnField.setText(displayQn);

		solutionField = (EditText) findViewById(R.id.mathSolution);
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

	private void generateMathSum(int difficulty) {
		int x = 1;
		int y = 1;
		switch (difficulty) {
		case 0:
			x = 10;
			y = 10;
			break;
		case 1:
			x = 10;
			y = 15;
			break;
		case 2:
			x = 15;
			y = 15;
			break;
		default:
			break;
		}

		int a = (int) ((Math.random() * x) + 1);
		int b = (int) ((Math.random() * y) + 1);
		solution = a * b;
		displayQn = a + " * " + b + " :";
	}

	private boolean isAnswerCorrect() {
		userAnswer = Integer.parseInt(solutionField.getText().toString());
		if (userAnswer == solution) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.math_sum, menu);
		return true;
	}

}
