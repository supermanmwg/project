package com.weather.mwg.operation;

import java.lang.ref.WeakReference;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UniqueOps {

	/**
	 * String for display name
	 */
	public static final String DISPLAY_NAME = "Display_Name";
	
	/**
	 * String for provider name
	 */
	public static final String SQL_NAME = "Sql_Name";

	/**
	 * String for AQI value
	 */
	public static final String AQI = "Aqi_Value";
	
	/**
	 * String for PM25 city name;
	 */
	public static final String PM25_CITY= "pm2_5";
	
	/**
	 * Used to enable garbage collection.
	 */
	private WeakReference<Activity> mActivity;
	
	public UniqueOps(Activity activity) {
		mActivity = new WeakReference<Activity>(activity);
	}
	
	/**
	 * Get the name of type(String)
	 * @param type
	 * @return
	 */
	public String getName(String type) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		String name = pref.getString(type, null);

		return name;
	}

	/**
	 * Set the name of type(String)
	 * @param type
	 * @param name
	 */
	public void SetName(String type, String name) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		SharedPreferences.Editor mEditor;
		mEditor = pref.edit();
		mEditor.putString(type, name);
		mEditor.commit();
	}

	/**
	 * Get the name of type(int)
	 * @param type
	 * @return
	 */
	public int getValue(String type) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		int value = pref.getInt(type, 0);

		return value;
	}
	
	/**
	 * Set the name of type(int)
	 * @param type
	 * @param value
	 */
	public void SetValue(String type, int value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		SharedPreferences.Editor mEditor;
		mEditor = pref.edit();
		mEditor.putInt(type, value);
		mEditor.commit();
	}
}
