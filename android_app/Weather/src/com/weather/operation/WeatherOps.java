package com.weather.operation;

import java.util.Set;

import com.weather.activities.MainActivity;

import android.R.string;
import android.view.View;


public interface WeatherOps {
	/**
	 * String for intent extra info.
	 */
	public static final String WEATHRE_DATA = "Weather Data";
	
	public static final int LOCATE = 1;
	public static final int UPDATE = 2;
	public static final int  ADD = 3;
	

	
	/**
	 * Initiate the service binding protocol.
	 */
	public void bindService();

	/**
	 * Initiate the service unbinding protocol.
	 */
	public void unbindService();
	

	/**
	 * Called after a runtime configuration change occurs to finish the
	 * initialization steps
	 */
	public void onConfigurationChange(MainActivity activity);

	public void changeTab(int position);

	public void clickTab(View v);

	public void onLocation();
	
	public void onUpdate(String string,int tag);
	
	public String getName(String type);

	public void SetName(String type, String name);

	public Set<String> getList(String type);
	
	public void setListName(String type, Set<String> nameSet);
	
}
