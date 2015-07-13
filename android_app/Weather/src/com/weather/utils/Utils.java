package com.weather.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.util.Log;

import com.weather.aidl.WeatherData;
import com.weather.jsonlocation.BaiduLocationJsonParser;
import com.weather.jsonlocation.LocationJsonParser;
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
		

		String date = new java.text.SimpleDateFormat(format)
				.format(new java.util.Date(timestamp * 1000));
		return date;
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
				final LocationJsonParser parser = 
						new BaiduLocationJsonParser();
				
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
	
	 public static String HanyuToPinyin(String name){
	    	String pinyinName = "";
	        char[] nameChar = name.toCharArray();
	        HanyuPinyinOutputFormat defaultFormat = 
	                                           new HanyuPinyinOutputFormat();
	        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        for (int i = 0; i < nameChar.length; i++) {
	            if (nameChar[i] > 128) {
	                try {
	                    pinyinName += PinyinHelper.toHanyuPinyinStringArray
	                                           (nameChar[i], defaultFormat)[0];
	                } catch (BadHanyuPinyinOutputFormatCombination e) {
	                    e.printStackTrace();
	                }
	            } 
	        }
	        return pinyinName;
	    }
	 
	
}
