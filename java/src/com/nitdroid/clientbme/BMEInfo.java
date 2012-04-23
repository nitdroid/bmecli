package com.nitdroid.clientbme;

import java.lang.String;

public class BMEInfo {

	public int chargerType;
	public int chargingTime;
	public int batteryMaxLevel;
	public int batteryCurLevel;
	public int batteryMaxCapacity;
	public int batteryCurCapacity;
	public int batteryMaxVoltage;
	public int batteryCurVoltage;
	public int batteryPctLevel;
	public int batteryTemperature;
	public int batteryCurrent;
	public int batteryLastFullCapacity;

	private final static String chargerTypeToString(int type) {
		switch (type) {
		case 0: return "None";
		case 1: return "USB (no charging)";
		case 2: return "USB";
		case 3: return "Wall";
		}
		return "Unknown: " + new Integer(type).toString();
	}

	@Override
	public final String toString() {
		String res = "charger type: " + chargerTypeToString(chargerType) + "\n"
				+ "charging time: " + chargingTime + "\n"
				+ "battery Max. Level: " + batteryMaxLevel + "\n"
				+ "battery Cur. Level: " + batteryCurLevel + "\n"
				+ "battery Max. Capacity: " + batteryMaxCapacity + "\n"
				+ "battery Cur. Capacity: " + batteryCurCapacity + "\n"
				+ "battery Max. Voltage: " + batteryMaxVoltage + "\n"
				+ "battery Cur. Voltage: " + batteryCurVoltage + "\n"
				+ "battery Pct. Level: " + batteryPctLevel + "\n"
				+ "battery Temperature: " + ((float)batteryTemperature - 273.15f) + "\n"
				+ "battery Current: " + batteryCurrent + "\n"
				+ "battery Last Full Capacity: " + batteryLastFullCapacity + "\n";
		return res;
	}
}
