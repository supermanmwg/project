package com.example.operations;

import com.example.activities.MainActivity;

import android.app.Activity;
import android.view.View;

public interface WeatherOps {
	/**
	 * String for intent extra info.
	 */
	public static final String WEATHRE_DATA = "Weather Data";
	
	/**
	 * Initiate the service binding protocol.
	 */
	public void bindService();

	/**
	 * Initiate the service unbinding protocol.
	 */
	public void unbindService();
	
	public void expandWeahter(String cityName, View v);

	/**
	 * Called after a runtime configuration change occurs to finish the
	 * initialization steps
	 */
	public void onConfigurationChange(MainActivity activity);
}
