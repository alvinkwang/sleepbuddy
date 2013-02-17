package com.example.sleepbuddy;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class CreateAlarmActivity extends ListActivity {

	static final String[] ALARM_SETTINGS = { "Alarm Repeat", "Snooze Duration", "Game Type", "SMS Buddy" };
	static final boolean[] ALARM_SETTINGS_ICON = { true, true, true, true };

	static final String[] GAME_TYPE = { "Math Sum", "Captcha", "Shaker" };

	int gameTypeSelected = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_create_alarm);
		// Show the Up button in the action bar.
		setupActionBar();

		setListAdapter(new ImageAdapter(this, R.layout.activity_create_alarm, R.id.text1, R.id.image1, ALARM_SETTINGS,
				ALARM_SETTINGS_ICON));

		// setListAdapter(new ArrayAdapter<String>(this,
		// R.layout.activity_create_alarm, ALARM_SETTINGS));
		//
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
				switch (position) {

				case 0: // Alarm Repeat
					Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
					break;
				case 1: // Snooze Duration
					Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
					break;
				case 2: // Game Type
					buildGameTypeDialog();
					break;
				case 3: // SMS Buddy
					// stub
					break;
				default:
					Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
	}

	private void buildGameTypeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title_game_type);
		builder.setNegativeButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setSingleChoiceItems(GAME_TYPE, gameTypeSelected, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {
				gameTypeSelected = index;
				Toast.makeText(getApplicationContext(), GAME_TYPE[index], Toast.LENGTH_SHORT).show();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
