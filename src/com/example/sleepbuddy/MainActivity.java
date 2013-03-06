package com.example.sleepbuddy;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	public static ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
	
	public final static String EXTRA_MESSAGE = "MESSAGE";
	ArrayAdapter<String> adapter;
	ArrayList<String> listItems = new ArrayList<String>();
//	int clickCounter=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, listItems);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void createAlarm(View view) {
		Intent intent = new Intent(this, CreateAlarmActivity.class);
		startActivity(intent);
//		listItems.add("Clicked : "+clickCounter++);
//		adapter.notifyDataSetChanged();
	}

	public static ArrayList<Alarm> getAlarmList() {
		return alarmList;
	}
}
