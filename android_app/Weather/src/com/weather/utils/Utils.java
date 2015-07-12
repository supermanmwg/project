package com.weather.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.util.Log;
import com.weather.aidl.WeatherData;
import com.weather.jsonlocation.LocationJSONParser;
import com.weather.retrofit.WeatherDataCurrent;
import com.weather.retrofit.WeatherDataForeCast;

public class Utils {

	/**
	 * @param mCurrent
	 *            :current weather data
	 * @param mForeCast
	 *            :forecast weather data
	 * @param cnt
	 *            : forecast day's count
	 * @return
	 * @Description get a Weather data list from today and forecast
	 */

	public final static String TAG = "Utils";
	public final static String sLocation_Service_URL = "http://api.map.baidu.com/geocoder?output=json&location=";

	public static List<WeatherData> genList(WeatherDataCurrent mCurrent,
			WeatherDataForeCast mForeCast, long cnt) {
		List<WeatherData> list = new ArrayList<WeatherData>();
		/*
		WeatherData mData = getWeatherFromCurrent(mCurrent);
		if (null != mData)
			list.add(mData);
			*/
		String name = mForeCast.getCity().getName();
		String country = mForeCast.getCity().getCountry();
		for (int i = 0; i < cnt; i++) {
			WeatherData mForCast = genWeatherDataFromForeCast(name, country, i,
					mForeCast);
			if (null != mForCast)
				list.add(mForCast);
		}
		return list;
	}

	/**
	 * 
	 * @param mCurrent
	 *            :current weather data
	 * @return
	 * @description: get WeatherData from current weather data
	 * 
	 */

	private static WeatherData getWeatherFromCurrent(WeatherDataCurrent mCurrent) {
		WeatherData mData = new WeatherData();
		mData.setmCountry(mCurrent.getSys().getCountry());
		mData.setmName(mCurrent.getName());
		mData.setmDate(mCurrent.getDate());
		mData.setmDeg(mCurrent.getWind().getDeg());
		mData.setmHumidity(mCurrent.getMain().getHumidity());
		mData.setmIconID(mCurrent.getWeathers().get(0).getIcon());
		mData.setmPressure(mCurrent.getMain().getPressure());
		mData.setmSpeed(mCurrent.getWind().getSpeed());
		mData.setmTemp(mCurrent.getMain().getTemp());
		mData.setmTempMax(mCurrent.getMain().getmTempMax());
		mData.setmTempMin(mCurrent.getMain().getmTempMin());
		mData.setmDescription(mCurrent.getWeathers().get(0).getDescription());

		return mData;
	}

	/**
	 * @param name
	 *            :city name
	 * @param country
	 * @param cnt
	 *            :forecast day's count
	 * @param mForCast
	 *            :forecast weather data
	 * @return
	 * @description : get WeatherData from forecast weather data
	 */
	private static WeatherData genWeatherDataFromForeCast(String name,
			String country, int cnt, WeatherDataForeCast mForCast) {
		WeatherData mData = new WeatherData();

		mData.setmCountry(country);
		mData.setmName(name);
		mData.setmDate(mForCast.getList().get(cnt).getDt());
		mData.setmDeg(mForCast.getList().get(cnt).getDeg());
		mData.setmHumidity(mForCast.getList().get(cnt).getHumidity());
		mData.setmIconID(mForCast.getList().get(cnt).getWeather().get(0)
				.getIcon());
		mData.setmPressure(mForCast.getList().get(cnt).getPressure());
		mData.setmSpeed(mForCast.getList().get(cnt).getSpeed());
		mData.setmTemp(mForCast.getList().get(cnt).getTemp().getDay());
		mData.setmTempMax(mForCast.getList().get(cnt).getTemp().getMax());
		mData.setmTempMin(mForCast.getList().get(cnt).getTemp().getMin());
		mData.setmDescription(mForCast.getList().get(cnt).getWeather().get(0)
				.getDescription());

		return mData;

	}

	/**
	 * Unix timestamp be converted to to date
	 * @param timestamp 
	 * 					:Unix timestamp
	 * @param format 
	 * 					:convert format
	 * @return date
	 */
	public static String TimeStampToDate(long timestamp ,String format) {
		
		String mFormat = "M月dd日";
		String date = new java.text.SimpleDateFormat(mFormat)
				.format(new java.util.Date(timestamp * 1000));
		return date;
	}
	

    /**
     * Convert wind speed format from m/s to level
     * @param speed
     * 				: wind speed (m/s)
     * @return level 
     * 				: wind level (int)
     */
    @SuppressLint("SimpleDateFormat")
	public static int convertSpeed(double speed) {
    	if(0 <= speed && speed < 0.3) {
    		return 0;
    	}  else if (0.3 <= speed && speed < 1.5) {
    		return 1;
    	} else if (1.5 <= speed && speed < 3.3) {
    		return 2;
    	} else if (3.3 <= speed && speed < 5.4) {
    		return 3;
    	} else if (5.4 <= speed && speed < 7.9) {
    		return 4;
    	} else if (7.9 <= speed && speed < 10.7) {
    		return 5;
    	} else if (10.7 <= speed && speed < 13.8) {
    		return 6;
    	} else if (13.8 <= speed && speed < 17.1) {
    		return 7;
    	} else if (17.1 <= speed && speed < 20.7) {
    		return 8;
    	} else if (20.7 <= speed && speed < 24.4) {
    		return 9;
    	} else if (24.4 <= speed && speed < 28.4) {
    		return 10;
    	} else if (28.4 <= speed && speed < 32.6) {
    		return 11;
    	} else if (32.6 <= speed) {
    		return 12;
    	} else {
    		return -1;
    	}
    }
    
    public static String convertWindDetails(double speed) {
    	if(0 <= speed && speed < 0.3) {
    		return "无风";
    	}  else if (0.3 <= speed && speed < 1.5) {
    		return "软风";
    	} else if (1.5 <= speed && speed < 3.3) {
    		return "轻风";
    	} else if (3.3 <= speed && speed < 5.4) {
    		return "微风";
    	} else if (5.4 <= speed && speed < 7.9) {
    		return "和风";
    	} else if (7.9 <= speed && speed < 10.7) {
    		return "轻劲风";
    	} else if (10.7 <= speed && speed < 13.8) {
    		return "强风";
    	} else if (13.8 <= speed && speed < 17.1) {
    		return "疾风";
    	} else if (17.1 <= speed && speed < 20.7) {
    		return "大风";
    	} else if (20.7 <= speed && speed < 24.4) {
    		return "烈风";
    	} else if (24.4 <= speed && speed < 28.4) {
    		return "狂风";
    	} else if (28.4 <= speed && speed < 32.6) {
    		return "暴风";
    	} else if (32.6 <= speed) {
    		return "台风";
    	} else {
    		return null;
    	}
    }
    
	/**
	 * Obtain the location city name.
	 * 
	 * @return The information that responds to your current location search.
	 */
    
	public static String getLocationName(String location) {
		String cityName;
		
		try {
			// Append the location to create the full URL.
			final URL url = 
					new URL(sLocation_Service_URL 
							+ location);
			Log.d(TAG, "location URL is " + url.toString());
			//Open a connection to the Weather Service.
			HttpURLConnection urlConnection = 
					(HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			
			try (InputStream in = 
				new BufferedInputStream(urlConnection.getInputStream())) {
				//Create the parse.
				Log.d(TAG, "Utils reader input stream");
				final LocationJSONParser parser = 
						new LocationJSONParser();
				
				cityName = parser.parseJsonStream(in);
				Log.d(TAG, "Utils return city name is " + cityName);
			} finally {
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			Log.d(TAG,e.getMessage());
			return null;
		}
		
		return cityName;
	}
}
