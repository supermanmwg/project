package com.weather.mwg.aidl;

import com.weather.mwg.aidl.WeatherData;
import java.util.List;

/**
 * Interface defining the method that receives callbacks from the
 * WeatherServiceAsync.  
 */
interface WeatherResults {

     // For weather info
    oneway void sendResult(in List<WeatherData> results);
    
   	// For location
   	oneway void sendLocationName(in String name);
   	
   	// For PM2.5
   	oneway void sendPM2_5(in int value);
   	
   	// For error info
   	oneway void sendErrors(int error);
}
