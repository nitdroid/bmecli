package com.nitdroid.clientbme;

import java.lang.String;

public class BMEInfo {

	int chargerType;
	int chargingTime;
	int batteryMaxLevel;
	int batteryCurLevel;
	int batteryMaxCapacity;
	int batteryCurCapacity;
	int batteryMaxVoltage;
	int batteryCurVoltage;
	int batteryPctLevel;
	int batteryTemperature;
	int batteryCurrent;
	int batteryLastFullCapacity;
	
	@Override
	public String toString() {
		String res = "charger type: " + chargerType + "\n"
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
