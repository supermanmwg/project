package com.example.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.aidl.WeatherData;
import com.example.jsonweather.JsonWeather;
import com.example.jsonweather.WeatherJSONParser;

public class UtilsNet {
	/**
	 * Logging tag used by the debugger
	 */
	private final static String TAG = UtilsNet.class.getSimpleName();

	/**
	 * URL to the Weather web service.
	 */
	private final static String sWeather_Web_Service_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

	/**
	 * Obtain the weather information.
	 * 
	 * @return The information that responds to your current weather search.
	 */
	public static WeatherData getResults(String cityName) {
		// Create a List that will return the WeatherData obtaioned
		// from the Weather Service web service.
		WeatherData returnWeather = null;
		
		// A List of JsonWeather objects.
		List<JsonWeather> jsonWeathers = null;
		try {
			// Append the location to create the full URL.
			final URL url = 
					new URL(sWeather_Web_Service_URL 
							+ cityName);
			
			//Open a connection to the Weather Service.
			HttpURLConnection urlConnection = 
					(HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			
			//Sends the Get request and reads the Json results.
			try (InputStream in = 
				new BufferedInputStream(urlConnection.getInputStream())) {
				//Create the parse.
				final WeatherJSONParser parser = 
						new WeatherJSONParser();
				
				//Parse the Json results and create JsonWeather data 
				//objects.
				jsonWeathers = parser.parseJsonStream(in);
			} finally {
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//see if we parse any valid data.
		if(null !=jsonWeathers && jsonWeathers.size() > 0) {
			//Convert the JsonWeather data objects to our weatherData ojbect,
			//which can be passed between processes.
			Log.d(TAG, "json weather is not null");
			for(JsonWeather jsonWeather: jsonWeathers) {

				Log.d(TAG, "**Country name :" + jsonWeather.getSys().getCountry() + "Pressure :" + jsonWeather.getMain().getPressure());
				return new WeatherData(jsonWeather.getName(),jsonWeather.getWind().getSpeed(),
						jsonWeather.getWind().getDeg(), jsonWeather.getMain().getTemp(), jsonWeather.getMain().getHumidity(),
						jsonWeather.getSys().getSunrise(), jsonWeather.getSys().getSunset(), 
						jsonWeather.getMain().getPressure(), jsonWeather.getDt(), jsonWeather.getSys().getCountry(), 
						jsonWeather.getWeather().get(0).getIcon());
			}

		} else{
			Log.d(TAG, "json weather is null");
			return null;
		}
		return null;
	}
}
