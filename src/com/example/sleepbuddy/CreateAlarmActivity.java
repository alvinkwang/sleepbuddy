package com.example.sleepbuddy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateAlarmActivity extends ListActivity {

	static final String[] ALARM_SETTINGS = { "Alarm Repeat", "Snooze Duration", "Game Type", "SMS Buddy" };
	static final String[] ALARM_SETTINGS_DEFAULT = { "One Off", "5 minutes", "Math Sum", "-" };
	static final boolean[] ALARM_SETTINGS_ICON = { true, true, true, true };

	static final String[] REPEAT = { "One Off", "Repeat" };
	static final String[] SNOOZE_DURATION = { "3 minutes", "5 minutes", "10 minutes", "15 minutes", "30 minutes" };
	static final String[] GAME_TYPE = { "Math Sum", "String Match", "Shaker" };
	static final String[] BUDDY_LIST = { "Alvin Kwang", "Yiang Meng", "Tony Tran", "Haja Shaik", "Chen Xiao Xi" };

	private int prevSelection = -1;
	private int repeatSelected = 0;
	private int snoozeDurationSelected = 1;
	private int gameTypeSelected = 0;
	private ArrayList<Integer> selectedBuddies = new ArrayList<Integer>();
	private boolean[] selectedBuddiesBoolean = { false, false, false, false, false };
	private String smsBuddyCheat = "0";

	private ArrayList<Map<String, String>> list;
	private SimpleAdapter adapter;
	private TimePicker timePicker;
	private int hour;
	private int min;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_alarm);

		addTimePicker();
		addSaveButton();
		addCancelButton();

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

	private void addTimePicker() {
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				hour = hourOfDay;
				min = minute;
			}
		});
	}

	private void addSaveButton() {
		Button saveButton = (Button) findViewById(R.id.saveAlarmBtn);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(CreateAlarmActivity.this, AlarmService.class);
				intent.putExtra("gameType", gameTypeSelected);
				intent.putExtra("snooze", getSnoozeDurationInSeconds());
				intent.putExtra("smsCheat", smsBuddyCheat);
				PendingIntent pendingIntent = PendingIntent.getService(CreateAlarmActivity.this, 0, intent, 0);

				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				// FIXME: To set alarm to trigger at time defined by user
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.add(Calendar.SECOND, getSnoozeDurationInSeconds());
				alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

				Alarm alarm = new Alarm(hour, min, REPEAT[repeatSelected], SNOOZE_DURATION[snoozeDurationSelected],
						GAME_TYPE[gameTypeSelected], BUDDY_LIST);
				MainActivity.getAlarmList().add(alarm);

				finish();
			}
		});
	}

	private int getSnoozeDurationInSeconds() {
		String s = SNOOZE_DURATION[snoozeDurationSelected];
		String value = s.substring(0, s.indexOf(' '));
		int mins = Integer.parseInt(value);
		int seconds = mins * 60;
		// return seconds;
		// FIXME: hardcoded in mainActivity
		return MainActivity.TEST_SNOOZE_DURATION;
	}

	private void addCancelButton() {
		Button cancelButton = (Button) findViewById(R.id.cancelAlarmBtn);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
		prevSelection = repeatSelected;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title_alarm_repeat);
		builder.setNegativeButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				updateList(0, REPEAT[repeatSelected]);
				adapter.notifyDataSetChanged();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				repeatSelected = prevSelection;
			}
		});
		builder.setSingleChoiceItems(REPEAT, repeatSelected, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {
				repeatSelected = index;
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void buildSnoozeDurationDialog() {
		prevSelection = snoozeDurationSelected;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title_snooze_duration);
		builder.setNegativeButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				updateList(1, SNOOZE_DURATION[snoozeDurationSelected]);
				adapter.notifyDataSetChanged();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				snoozeDurationSelected = prevSelection;
			}
		});
		builder.setSingleChoiceItems(SNOOZE_DURATION, snoozeDurationSelected, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {
				snoozeDurationSelected = index;
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
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				gameTypeSelected = prevSelection;
			}
		});
		builder.setSingleChoiceItems(GAME_TYPE, gameTypeSelected, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {
				gameTypeSelected = index;
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private String getBuddiesNames(ArrayList<Integer> buddyList) {
		String names = "";
		for (int i=0; i<BUDDY_LIST.length; i++) {
			if (buddyList.contains(i)) {
				names = names + " " + BUDDY_LIST[i] + ";";
			}
		}
		return names;
	}
	
	private void generateBuddyIntentString(ArrayList<Integer> buddyList) {
		String s = "" + buddyList.size();
		for (int i=0; i<BUDDY_LIST.length; i++) {
			if (buddyList.contains(i)) {
				s = s + i;
			}
		}
		smsBuddyCheat = s;
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
				// adapter.notifyDataSetChanged();
				updateList(3, getBuddiesNames(selectedBuddies));
				generateBuddyIntentString(selectedBuddies);
				adapter.notifyDataSetChanged();
			}
		});
		builder.setPositiveButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		builder.setMultiChoiceItems(BUDDY_LIST, selectedBuddiesBoolean,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) {
							// If the user checked the item, add it to the
							// selected
							// items
							tempBuddyList.add(which);
							tempBuddySelected[which] = true;
						} else if (selectedBuddies.contains(which)) {
							// Else, if the item is already in the array, remove
							// it
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
