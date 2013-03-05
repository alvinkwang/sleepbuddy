package com.example.sleepbuddy;

public class Alarm {

	private int hour;
	private int min;
	private boolean repeat;
	private int snoozeDuration;
	private String gameType;
	private String[] smsBuddy;
	
	public Alarm(boolean repeat, String snoozeDuration, String gameType, String[] smsList) {
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

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
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
