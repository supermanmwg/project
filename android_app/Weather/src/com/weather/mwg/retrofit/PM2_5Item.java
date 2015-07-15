package com.weather.mwg.retrofit;

public class PM2_5Item {

	/**
	 * Weather air condition (AQI value)
	 */
	private long aqi;
	
	public PM2_5Item(long aqi) {
		this.aqi = aqi;
	}

	public long getaqi() {
		return aqi;
	}
}
