package com.weather.operation;

import java.lang.ref.WeakReference;
import java.util.Set;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UniqueOps {

	/**
	 * String for display name
	 */
	public static final String DISPLAY_NAME = "Display_Name";
	
	/**
	 * String for list name
	 */
	public static final String SET_NAME = "Set_Name";
	
	private WeakReference<Activity> mActivity;
	
	public UniqueOps(Activity activity) {
		mActivity = new WeakReference<Activity>(activity);
	}
	
	public String getDisplayName() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		String name = pref.getString(DISPLAY_NAME, null);

		return name;
	}


	public void SetDisplayName(String name) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		SharedPreferences.Editor mEditor;
		mEditor = pref.edit();
		mEditor.putString(DISPLAY_NAME, name);
		mEditor.commit();
	}


	public Set<String> getListName() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		Set<String> mSet = pref.getStringSet(SET_NAME, null);

		return mSet;
	}
	
	public void setListName(Set<String> nameSet) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(mActivity.get());
		SharedPreferences.Editor mEditor;
		mEditor = pref.edit();
		mEditor.putStringSet(SET_NAME, nameSet);
		mEditor.commit();

	}


}
