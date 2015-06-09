package com.example.activities;

import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.transform.Templates;

import com.example.R;
import com.example.aidl.WeatherData;
import com.example.operations.WeatherOps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
		WeatherData weather = intent.getParcelableExtra(WeatherOps.WEATHRE_DATA);
		Log.d(TAG, "weather name " + weather.getmCountry());
		DisplayWeatherActivity(weather) ;
		

	}
	
	private void DisplayWeatherActivity(WeatherData weather) {
		mTextCityName.setText("" + weather.getmName()+"," + weather.getmCountry());
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("CCT"));
		c.setTimeInMillis(weather.getmDate() * 1000);
		mTextDate.setText("   " +c.get(Calendar.DAY_OF_MONTH) + "," + (c.get(Calendar.MONTH)+1) + "," + c.get(Calendar.YEAR));
		int cTemp = (int)(weather.getmTemp() - 273.15);
		int fTemp = cTemp * 9 /5 +32;
		mTextTemp.setText("" + cTemp + " ¡æ ");
		mTextHuashi.setText("" + fTemp + " ¨H");
		mTextHumandity.setText("Humandity : " + weather.getmHumidity() + "%");
		mTextPressure.setText("Pressure :"+weather.getmPressure() + "hPa");
		mTextWind.setText("wind degree : " + weather.getmDeg() + "¡ã\nWind speed  :" + weather.getmSpeed()+ "m/s");
		Log.d(TAG,"icon path :" + weather.getmIconID());
		Bitmap mBitmap = BitmapFactory.decodeFile(weather.getmIconID());
		if(mBitmap != null)
			mWeatherPic.setImageBitmap(mBitmap);
	}

	private void initView() {
		mTextCityName = (TextView) findViewById(R.id.city_name);
		mTextDate = (TextView) findViewById(R.id.date);
		mTextTemp = (TextView) findViewById(R.id.temp);
		mTextHuashi =(TextView) findViewById(R.id.huashi);
		mTextHumandity = (TextView) findViewById(R.id.hunmandity);
		mTextPressure = (TextView) findViewById(R.id.pressure);
		mTextWind =(TextView) findViewById(R.id.Wind);
		mWeatherPic =(ImageView) findViewById(R.id.weather_pic);
	}
	
}
