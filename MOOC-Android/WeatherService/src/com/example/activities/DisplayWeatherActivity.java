package com.example.activities;

import java.util.List;

import com.example.R;
import com.example.aidl.WeatherData;
import com.example.operations.WeatherOps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayWeatherActivity extends LifecycleLoggingActivity {

	private TextView mTextCityName;
	private TextView mTextDate;
	private TextView mTextTemp;
	private TextView mTextHuashi;
	private TextView mTextHumandity;
	private TextView mTextPressure;
	private TextView mTextWind;
	private ImageView mWeatherPic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_weather_activity);
		initView();
		
		Intent intent = getIntent();
		List<WeatherData> weatherlists = intent.getParcelableArrayListExtra(WeatherOps.WEATHRE_DATA);
		WeatherData weather = weatherlists.get(0);
		Log.d(TAG, "weather name " + weather.getmName());
		DisplayWeatherActivity(weather);
	}
	
	private void DisplayWeatherActivity(WeatherData weather) {
		mTextCityName.setText(weather.getmName());
		mTextTemp.setText("" + weather.getmTemp());
		mTextHuashi.setText("" + weather.getmTemp());
		mTextHumandity.setText("" + weather.getmHumidity());
		mTextWind.setText("wind degree:" + weather.getmDeg() + "\nWind speed:" + weather.getmSpeed());
	}

	private void initView() {
		mTextCityName = (TextView) findViewById(R.id.cityName);
		mTextDate = (TextView) findViewById(R.id.date);
		mTextTemp = (TextView) findViewById(R.id.temp);
		mTextHuashi =(TextView) findViewById(R.id.huashi);
		mTextHumandity = (TextView) findViewById(R.id.hunmandity);
		mTextPressure = (TextView) findViewById(R.id.pressure);
		mTextWind =(TextView) findViewById(R.id.Wind);
		mWeatherPic =(ImageView) findViewById(R.id.image);
	}
	
}
