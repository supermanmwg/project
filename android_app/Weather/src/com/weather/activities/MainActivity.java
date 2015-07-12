package com.weather.activities;

import com.weather.R;
import com.weather.operation.ImageOps;
import com.weather.operation.UniqueOps;
import com.weather.operation.WeatherOps;
import com.weather.operation.WeatherOpsImpl;
import com.weather.utils.Utils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class MainActivity  extends FragmentActivity implements OnClickListener,
OnPageChangeListener{
	
	private final String TAG = getClass().getSimpleName();
	private WeatherOps mWeatherOps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);
		
		mWeatherOps =  new WeatherOpsImpl(this);
		mWeatherOps.bindService();
		
	}
	
	@Override
	protected void onDestroy() {
		mWeatherOps.unbindService();
		super.onDestroy();
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset >= 0.9 || positionOffset < 0.3){
			mWeatherOps.changeTab(position);
		}
	}
	
	@Override
	public void onPageSelected(int arg0) {
		
	}

	@Override
	public void onClick(View v) {
		mWeatherOps.clickTab(v);
	}
	
	
	//just for test
	public static int i = 0;
	/**
	 * Refresh the city weather info 
	 */
	public void onUpdate(View v){
		Log.d(TAG, "onUpdate is beginning...");
		String name;
		if(i%2 == 0) {
			 name = "北京";
			 i++;
		} else {
			 name = "上海";
			 i++;
		}
		/*
		if(null == mWeatherOps.getName(UniqueOps.SQL_NAME)) {
			mWeatherOps.onLocation();
		} else {
			mWeatherOps.onUpdate(mWeatherOps.getName(UniqueOps.SQL_NAME));
		}
		*/
		mWeatherOps.onUpdate(name);
	}

	/**
	 * Locate your city and refresh the weather info of your city
	 */
	public void onLocation(View v) {
		Log.d(TAG, "onLocations is  start...");
		mWeatherOps.onLocation();
	}
}
