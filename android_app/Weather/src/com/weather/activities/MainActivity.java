package com.weather.activities;

import com.weather.R;
import com.weather.operation.ImageOps;
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
	
	public static int i = 0;
	public void onLocation(View v){
		Log.d(TAG, "onLoaction is beginning...");
		String name;
		if(i%2 == 0) {
			 name = "±±";
			 i++;
		} else {
			 name = "ÉÏ";
			 i++;
		}
		
		mWeatherOps.onLocation(name);
	}

}
