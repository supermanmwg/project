package com.weather.mwg.aidl;

import com.weather.mwg.aidl.WeatherResults;

/**
 * Interface defining the method implemented within
 * WeatherServiceAsync that provides asynchronous access to the
 * Weather Service web service.
 */
interface WeatherRequest {

	// for weather info
    oneway void getCurrentWeather(in String Weather, in String metric, in long cnt,
    				String lang, in WeatherResults results); 
	
	// for location                                  
    oneway void getLocation( in String location, in WeatherResults results);
    
    //for pm2.5 info 
    oneway void getPM2_5(in String cityName, in WeatherResults results);
}
