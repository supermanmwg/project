package com.weather.aidl;

import com.weather.aidl.WeatherData;
import java.util.List;

/**
 * Interface defining the method that receives callbacks from the
 * WeatherServiceAsync.  This method should be implemented by the
 * WeatherActivity.
 */
interface WeatherResults {
    /**
     * This one-way (non-blocking) method allows WeatherServiceAsync
     * to return the List of WeatherData results associated with a
     * one-way WeatherRequest.getCurrentWeather() call.
     */
     //for weather info
    oneway void sendResult(in List<WeatherData> results);
    oneway void sendErrors(String error);
    
   	//for location
   	oneway void sendLocationName(in String name);
   	
   	//for PM2.5
   	oneway void sendPM2_5(in int value);
}
