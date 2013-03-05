package com.example.sleepbuddy;

public class Alarm {

	private int hour;
	private int min;
	private String repeat;
	private int snoozeDuration;
	private String gameType;
	private String[] smsBuddy;
	
	public Alarm(int hour, int min, String repeat, String snoozeDuration, String gameType, String[] smsList) {
		this.hour = hour;
		this.min = min;
		this.repeat = repeat;
		this.snoozeDuration = extractSnoozeDuration(snoozeDuration);
		this.gameType = gameType;
		this.smsBuddy = smsList;
		createAlarm();
	}
	
	private void createAlarm() {
		//stub
		//call alarm manager to trigger alarm
	}
	
	private int extractSnoozeDuration(String snoozeAmt) {
		String value = snoozeAmt.substring(0, snoozeAmt.indexOf(' '));
		return Integer.parseInt(value);
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public int getSnoozeDuration() {
		return snoozeDuration;
	}

	public void setSnoozeDuration(int snoozeDuration) {
		this.snoozeDuration = snoozeDuration;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String[] getSmsBuddy() {
		return smsBuddy;
	}

	public void setSmsBuddy(String[] smsBuddy) {
		this.smsBuddy = smsBuddy;
	}
	
}
