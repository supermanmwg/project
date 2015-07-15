package com.weather.mwg.operation;

import android.view.View;

public interface WeatherOps {
	/**
	 * String for intent extra info.
	 */
	public static final String WEATHRE_DATA = "Weather Data";

	public static final int LOCATE = 1;
	public static final int UPDATE = 2;
	public static final int ADD = 3;

	/**
	 * Initiate the service binding protocol.
	 */
	public void bindService();

	/**
	 * Initiate the service unbinding protocol.
	 */
	public void unbindService();

	/**
	 * Set the Tab indicator's alpha to 1 (opacity)
	 */
	public void changeTab(int position);
	
	/**
	 * Tab click event
	 */
	public void clickTab(View v);

	/**
	 * Locate the city .
	 */
	public void onLocation();

	/**
	 * Update the weather info
	 * @param string
	 * @param tag :
	 * 				(1) ADD : for altering city (need to modify the display city);  
	 * 				(2) LOCATE : for locating city (need to refresh the display city);  
	 * 				(3) UPDATE : for updating the present display city weather info. 
	 */
	public void onUpdate(String string, int tag);

	/**
	 * Get simple share data 
	 * @param type
	 * @return
	 */
	public String getName(String type);

	/**
	 * Set simple share data of the type
	 * @param type
	 * @param name
	 */
	public void SetName(String type, String name);
}
