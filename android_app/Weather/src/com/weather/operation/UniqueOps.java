package com.weather.operation;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UniqueOps {

	/**
	 * String for display name
	 */
	public static final String DISPLAY_NAME = "Display_Name";
	
	/**
	 * String sql name
	 */
	public static final String SQL_NAME = "Sql_Name";
	
	/**
	 * String for list name
	 */
	public static final String SQL_SET_NAME = "Sql_Set_Name";
	
	public static final String DISPLAY_SET_NAME = "Display_Set_NAME";
	
	private WeakReference<Activity> mActivity;
	
	public UniqueOps(Activity activity) {
		mActivity = new WeakReference<Activity>(activity);
	}
	
	public String getName(String type) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		String name = pref.getString(type, null);

		return name;
	}


	public void SetName(String type, String name) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		SharedPreferences.Editor mEditor;
		mEditor = pref.edit();
		mEditor.putString(type, name);
		mEditor.commit();
	}


	public Set<String> getList(String type) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		Set<String> mSet = pref.getStringSet(type, null);

		return mSet;
	}
	
	public void setListName(String type, Set<String> nameSet) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		SharedPreferences.Editor mEditor;
		mEditor = pref.edit();
		mEditor.putStringSet(type, nameSet);
		mEditor.commit();

	}


}
