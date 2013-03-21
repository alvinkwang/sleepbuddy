package com.example.sleepbuddy;

import java.util.Random;

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
	private int level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_math_sum);

		// Extract game level
		Bundle b = this.getIntent().getExtras();
		if (b != null) {
			level = b.getInt("level");
		}

		generateMathSum(level);
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
		Random r = new Random();
		int x = 1;
		int y = 1;
		int a = 1;
		int b = 1;
		int low = 1;
		int high = 1;
		switch (difficulty) {
		case 0:
			x = 10;
			y = 10;
			a = (int) ((Math.random() * x) + 1);
			b = (int) ((Math.random() * y) + 1);
			break;
		case 1:
			low = 5;
			high = 12;
			a = r.nextInt(high - low) + low;
			b = r.nextInt(high - low) + low;
			break;
		case 2:
			low = 10;
			high = 15;
			a = r.nextInt(high - low) + low;
			b = r.nextInt(high - low) + low;
			break;
		case 3:
			low = 12;
			high = 15;
			a = r.nextInt(high - low) + low;
			b = r.nextInt(high - low) + low;
			break;
		default:
			break;
		}

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
