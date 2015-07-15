package com.weather.mwg.activities;

import com.weather.mwg.R;
import com.weather.mwg.operation.UniqueOps;
import com.weather.mwg.operation.WeatherOps;
import com.weather.mwg.operation.weatherOpsImpl.WeatherOpsImpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {

	private final int REQUEST_CODE = 1;
	/**
	 * Used for weather operation
	 */
	private WeatherOps mWeatherOps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		// Create the concreted weather ops
		mWeatherOps = new WeatherOpsImpl(this);

		// Bind weather service
		mWeatherOps.bindService();
	}

	/**
	 * Called after the service was connected by MainActiviy
	 */
	public void initData() {
		String displayName = mWeatherOps.getName(UniqueOps.DISPLAY_NAME);

		// If the display city is null£¬ relocate the position and get the
		// weather data
		// else update the display city weather info.
		if (null == displayName) {
			mWeatherOps.onLocation();
		} else {
			mWeatherOps.onUpdate(mWeatherOps.getName(UniqueOps.PM25_CITY),
					WeatherOps.UPDATE);
		}
	}

	/**
	 * Refresh button Event Refresh the city weather info
	 */
	public void onUpdate(View v) {

		// If the display city is null£¬ relocate the position and get the
		// weather data,
		// else update the display city weather info.
		if (null == mWeatherOps.getName(UniqueOps.DISPLAY_NAME)) {
			mWeatherOps.onLocation();
		} else {
			mWeatherOps.onUpdate(mWeatherOps.getName(UniqueOps.PM25_CITY),
					WeatherOps.UPDATE);
		}
	}

	/**
	 * Location button Event Locate your city and refresh the weather info of
	 * your city
	 */
	public void onLocation(View v) {

		mWeatherOps.onLocation();
	}

	/**
	 * Alter City button Event Alter the City for user wanted
	 */
	public void onAlter(View v) {
		Intent intent = new Intent(this, AlterActivity.class);
		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * Used to get the City from AlterActivity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE:
			if (RESULT_OK == resultCode) {
				String cityName = data.getStringExtra(AlterActivity.DATA);
				mWeatherOps.onUpdate(cityName, WeatherOps.ADD);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * For tab clicking
	 */
	@Override
	public void onClick(View v) {
		mWeatherOps.clickTab(v);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset >= 0.9 || positionOffset < 0.3) {
			mWeatherOps.changeTab(position);
		}
	}

	@Override
	public void onPageSelected(int arg0) {

	}

	@Override
	protected void onDestroy() {
		mWeatherOps.unbindService();
		super.onDestroy();
	}
}
