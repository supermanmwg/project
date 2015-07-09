package com.weather.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.weather.aidl.WeatherData;
import com.weather.retrofit.WeatherDataCurrent;
import com.weather.retrofit.WeatherDataForeCast;

public class Utils {

	/**
	 * @param mCurrent:current weather data
	 * @param mForeCast:forecast weather data
	 * @param cnt: forecast day's count
	 * @return
	 *  @Description 
	 * 	 	get a Weather data list from today and forecast
	 */
	
	public final static String TAG = "Utils";
	
	public static List<WeatherData> genList(WeatherDataCurrent mCurrent,
			WeatherDataForeCast mForeCast,long cnt) {
		List<WeatherData> list = new ArrayList<WeatherData>();
		WeatherData mData = getWeatherFromCurrent( mCurrent);
		if(null != mData)
			list.add(mData);
		String name = mForeCast.getCity().getName();
		String country = mForeCast.getCity().getCountry();
		for(int i =0; i < cnt; i++) {
			WeatherData mForCast = genWeatherDataFromForeCast(name, country, i, mForeCast);
			if(null != mForCast)
				list.add(mForCast);
		}
		return list;
	}
	
	/**
	 * 
	 * @param mCurrent:current weather data
	 * @return
	 * @description:
	 * 	get WeatherData from current weather data
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
	 * @param name:city name
	 * @param country
	 * @param cnt:forecast day's count
	 * @param mForCast:forecast weather data
	 * @return
	 * @description : 
	 * 		get WeatherData from forecast weather data
	 */
	private static WeatherData genWeatherDataFromForeCast(String name, String country, int  cnt, WeatherDataForeCast mForCast) {
		WeatherData mData = new WeatherData();
		
		mData.setmCountry(country);
		mData.setmName(name);
		mData.setmDate(mForCast.getList().get(cnt).getDt());
		mData.setmDeg(mForCast.getList().get(cnt).getDeg());
		mData.setmHumidity(mForCast.getList().get(cnt).getHumidity());
		mData.setmIconID(mForCast.getList().get(cnt).getWeather().get(0).getIcon());
		mData.setmPressure(mForCast.getList().get(cnt).getPressure());
		mData.setmSpeed(mForCast.getList().get(cnt).getSpeed());
		mData.setmTemp(mForCast.getList().get(cnt).getTemp().getDay());
		mData.setmTempMax(mForCast.getList().get(cnt).getTemp().getMax());
		mData.setmTempMin(mForCast.getList().get(cnt).getTemp().getMin());
		mData.setmDescription(mForCast.getList().get(cnt).getWeather().get(0).getDescription());
		Log.d(TAG, "121desc" + mData.getmDescription());
		
		return mData;
		
	}
}
