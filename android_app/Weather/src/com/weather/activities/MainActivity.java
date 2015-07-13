package com.weather.activities;

import com.weather.R;
import com.weather.operation.ImageOps;
import com.weather.operation.UniqueOps;
import com.weather.operation.WeatherOps;
import com.weather.operation.WeatherOpsImpl;
import com.weather.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class MainActivity  extends BaseActivity implements OnClickListener,
OnPageChangeListener{
	
	private final String TAG = getClass().getSimpleName();
	private final int REQUEST_CODE = 1;
	private WeatherOps mWeatherOps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate Start");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);
		
		mWeatherOps =  new WeatherOpsImpl(this);
		mWeatherOps.bindService();
		
		Log.d(TAG, "language is " + getLanguage());
	}

	
	public void initData(){
		String displayName = mWeatherOps.getName(UniqueOps.DISPLAY_NAME);
		if(null == displayName) {
			Log.d(TAG, "display name is null");
			mWeatherOps.onLocation();
		} else {
			Log.d(TAG, "display name is not null");
			mWeatherOps.onUpdate(mWeatherOps.getName(UniqueOps.PM25),WeatherOps.UPDATE);
		}
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
		if(null == mWeatherOps.getName(UniqueOps.DISPLAY_NAME)) {
			mWeatherOps.onLocation();
		} else {
			mWeatherOps.onUpdate(mWeatherOps.getName(UniqueOps.PM25),WeatherOps.UPDATE);
		}
	}

	/**
	 * Locate your city and refresh the weather info of your city
	 */
	public void onLocation(View v) {
		Log.d(TAG, "onLocations is  start...");
		mWeatherOps.onLocation();
	}
	
	public void onAlter(View v) {
		Intent intent = new Intent(this, AlterActivity.class);
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE:
			if(RESULT_OK == resultCode) {
				String cityName = data.getStringExtra(AlterActivity.DATA);
				Log.d(TAG, "onActivity return city name is " + cityName);
				mWeatherOps.onUpdate(cityName,WeatherOps.ADD);
			}
			break;
		default:
			break;
		}
	}
}
