package com.weather.activities;

import com.weather.R;

import android.support.v4.app.FragmentActivity;

public class BaseActivity  extends FragmentActivity{
	public static final int EN = 0;
	public static final int CN = 1;

	/**
	 * Get the System language
	 * 
	 */
	public int getLanguage() {
		if(getResources().getString(R.string.app_name).equals("Éµ¹ÏÌìÆø"))
			return CN;
		else 
		return EN;
	}

}
