package com.example.sleepbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

/*
 * TODO
 * (b) Implement Save and Cancel button
 * (c) Implement Dialogs for Alarm Repeat
 * (d) Implement Dialogs for SMS Buddy
 * (e) Currently hardcoded buddy list. MAX SIZE == 5!
 * (f) Cancel button of SMS Buddy not working
 */

public class CreateAlarmActivity extends ListActivity {

	static final String[] ALARM_SETTINGS = { "Alarm Repeat", "Snooze Duration", "Game Type", "SMS Buddy" };
	static final String[] ALARM_SETTINGS_DEFAULT = { "One Off", "5 minutes", "Math Sum", "-" };
	static final boolean[] ALARM_SETTINGS_ICON = { true, true, true, true };
	

	static final String[] SNOOZE_DURATION = { "3 minutes", "5 minutes", "10 minutes", "15 minutes", "30 minutes" };
	static final String[] GAME_TYPE = { "Math Sum", "Captcha", "Shaker" };
	static final String[] BUDDY_LIST = { "SPIDERMAN", "BATMAN", "SUPERMAN", "CATWOMEN", "Thor"};
	
	private int prevSelection = -1;
	private int snoozeDurationSelected = 1;
	private int gameTypeSelected = 0;
	private ArrayList<Integer> selectedBuddies = new ArrayList<Integer>();
	private boolean[] selectedBuddiesBoolean = {false, false, false, false, false};
 
	private ArrayList<Map<String, String>> list;
	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_alarm);
		
		//create timepicker
		TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
		
		list = buildData();
		String[] from = { "name", "purpose" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
		setListAdapter(adapter);

		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
				switch (position) {

				case 0: // Alarm Repeat
					Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
					buildAlarmRepeatDialog();
					break;
				case 1: // Snooze Duration
					buildSnoozeDurationDialog();
					break;
				case 2: // Game Type
					buildGameTypeDialog();
					break;
				case 3: // SMS Buddy
					buildSMSBuddyDialog();
					break;
				default:
					Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});// end setOnItemClickListener
	}
	
	private ArrayList<Map<String, String>> buildData() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < ALARM_SETTINGS.length; i++) {
			list.add(putData(ALARM_SETTINGS[i], ALARM_SETTINGS_DEFAULT[i]));
		}
		return list;
	}

	private void updateList(int index, String updateValue) {
		this.list.set(index, putData(ALARM_SETTINGS[index], updateValue));
	}

	private HashMap<String, String> putData(String name, String purpose) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("name", name);
		item.put("purpose", purpose);
		return item;
	}

	private void buildAlarmRepeatDialog() {

	}

	private void buildSnoozeDurationDialog() {
		prevSelection = snoozeDurationSelected;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title_snooze_duration);
		builder.setNegativeButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				updateList(1, SNOOZE_DURATION[snoozeDurationSelected]);
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				snoozeDurationSelected = prevSelection;
				Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setSingleChoiceItems(SNOOZE_DURATION, snoozeDurationSelected, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {
				snoozeDurationSelected = index;
				Toast.makeText(getApplicationContext(), SNOOZE_DURATION[index], Toast.LENGTH_SHORT).show();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void buildGameTypeDialog() {
		prevSelection = gameTypeSelected;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title_game_type);
		builder.setNegativeButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				updateList(2, GAME_TYPE[gameTypeSelected]);
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				gameTypeSelected = prevSelection;
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

	private void buildSMSBuddyDialog() {
		// prevSelection = 0;
		final ArrayList<Integer> tempBuddyList = selectedBuddies;
		final boolean[] tempBuddySelected = selectedBuddiesBoolean;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title_sms_buddy);
		builder.setNegativeButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				selectedBuddies = tempBuddyList;
				selectedBuddiesBoolean = tempBuddySelected;
//				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(getApplicationContext(), "Cancel" , Toast.LENGTH_SHORT).show();
			}
		});
		builder.setMultiChoiceItems(BUDDY_LIST, selectedBuddiesBoolean, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					// If the user checked the item, add it to the selected
					// items
					tempBuddyList.add(which);
					tempBuddySelected[which] = true;
				} else if (selectedBuddies.contains(which)) {
					// Else, if the item is already in the array, remove it
					tempBuddyList.remove(Integer.valueOf(which));
					tempBuddySelected[which] = false;
				}
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
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
