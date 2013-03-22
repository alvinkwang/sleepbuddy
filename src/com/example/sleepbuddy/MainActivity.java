package com.example.sleepbuddy;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

	public static ArrayList<Alarm> alarmList = new ArrayList<Alarm>();

	public final static int TEST_SNOOZE_DURATION = 5;
	public final static int TEST_AWAKE_NOTIFICATION_DURATION = 5;
	public final static int TEST_IS_USER_AWAKE_DURATION = 5;
	ArrayAdapter<String> adapter;
	ArrayList<String> listItems = new ArrayList<String>();

	// int clickCounter=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, listItems);
                  
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                data.add(addListAlarm("06:30 AM", "Shaker"));
                data.add(addListAlarm("09:30 AM", "Maths Sum"));

                SimpleAdapter listAlarmAdapter = new SimpleAdapter(this, data,
                    android.R.layout.simple_list_item_2, 
                    new String[] {"Title", "Game Type" }, 
                    new int[] {android.R.id.text1, android.R.id.text2 });
		setListAdapter(listAlarmAdapter);
	}
        
        public Map addListAlarm(String title, String game){
            Map<String, String> listAlarm = new HashMap<String, String>(2);
                listAlarm.put("Title", title);
                listAlarm.put("Game Type", game);            
                return listAlarm;
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
		// listItems.add("Clicked : "+clickCounter++);
		// adapter.notifyDataSetChanged();
	}

	public static ArrayList<Alarm> getAlarmList() {
		return alarmList;
	}

}
