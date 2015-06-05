package com.example.jsonweather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import android.text.StaticLayout;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

/**
 * Parses the Json weather data returned from the Weather Services API
 * and returns a List of JsonWeather objects that contain this data.
 */
public class WeatherJSONParser {
	/**
     * Used for logging purposes.
     */
    private final String TAG =
        this.getClass().getCanonicalName();

    /**
     * Parse the @a inputStream and convert it into a List of JsonWeather
     * objects.
     */
    public List<JsonWeather> parseJsonStream(InputStream inputStream)
        throws IOException {
        // TODO -- you fill in here.
			try (JsonReader reader = 
						new JsonReader(new InputStreamReader(inputStream,"UTF-8"))){
				return parseJsonWeatherArray(reader);
			}
    }

    /**
     * Parse a Json stream and convert it into a List of JsonWeather
     * objects.
     */
    public List<JsonWeather> parseJsonWeatherArray(JsonReader reader)
        throws IOException {	
    	 List<JsonWeather> weather = new ArrayList<JsonWeather>();
    	 
    	//Create a JsonWeather object for each element in the
    	//Json array
		Log.d(TAG, "paserJson start");
    	weather.add(parseJsonWeather(reader));
    		
    	return weather;	
    }

    /**
     * Parse a Json stream and return a JsonWeather object.
     */
    public JsonWeather parseJsonWeather(JsonReader reader) 
        throws IOException {
    	int i = 0;
    	JsonWeather mWeather = new JsonWeather();
    	Log.d(TAG, "1 now is " + reader.peek() + "i :" + i++);
    	reader.beginObject();
    	Log.d(TAG, "2 now is " + reader.peek() + "i :" + i++);
    	while(reader.hasNext()){
    		Log.d(TAG, "3 now is " + reader.peek() + "i :" + i++);
    		String name = reader.nextName();
    		Log.d(TAG, "name : " + name);
    		switch (name) {
			case JsonWeather.name_JSON:
				mWeather.setName(reader.nextString());
				break;
			case JsonWeather.weather_JSON:
				if (reader.peek() == JsonToken.BEGIN_ARRAY)
					mWeather.setWeather(parseWeathers(reader));
			case JsonWeather.base_JSON:
				String hh = reader.nextName();
				mWeather.setBase(hh);
				Log.d(TAG, "after base " + reader.nextString());
				break;
			case JsonWeather.dt_JSON:
				mWeather.setDt(reader.nextLong());
				break;
			case JsonWeather.main_JSON:
				mWeather.setMain(parseMain(reader));
				break;
			case JsonWeather.sys_JSON:
				mWeather.setSys(parseSys(reader));
				break;
			case JsonWeather.wind_JSON:
				mWeather.setWind(parseWind(reader));
				break;
			default:
				Log.d(TAG, "skip name is " + name);
				 reader.skipValue();
				break;
			}
    	}
    	reader.endObject();
    	
    	return mWeather;
    }
    
    /**
     * Parse a Json stream and return a List of Weather objects.
     */
    public List<Weather> parseWeathers(JsonReader reader) throws IOException {
	
		reader.beginArray();
		try {
			List<Weather> weathers = new ArrayList<Weather>();
			
			while (reader.hasNext()) {
				weathers.add(parseWeather(reader));
			}
			
			return weathers;
			
		} finally {
			reader.endArray();
		}

    
    }

    /**
     * Parse a Json stream and return a Weather object.
     */
    public Weather parseWeather(JsonReader reader) throws IOException {

    	reader.beginObject();
    	Weather weather = new Weather();
    		while(reader.hasNext()) {
    			String name = reader.nextName();
				Log.d(TAG, "weather name : " + name);
    			switch(name) {
    			case Weather.id_JSON:
    				weather.setId(reader.nextLong());
    				break;
    			case Weather.main_JSON:
    				weather.setMain(reader.nextString());
    				break;
    			case Weather.description_JSON:
    				weather.setDescription(reader.nextString());
    				break;
    			case Weather.icon_JSON:
    				weather.setIcon(reader.nextString());
    				break;
    			default:
    				 reader.skipValue();
    				 break;
    			}
    		}
    		reader.endObject();

    	return weather;   
    }
    
    /**
     * Parse a Json stream and return a Main Object.
     */
    public Main parseMain(JsonReader reader) 
        throws IOException {
	
        // TODO -- you fill in here.    
    	reader.beginObject();
    	Main main = new Main();
    		while(reader.hasNext()) {
    			String name = reader.nextName();
				Log.d(TAG, "Main name : " + name);
    			switch (name) {
				case Main.grndLevel_JSON:
					main.setGrndLevel(reader.nextDouble());
					break;
				case Main.humidity_JSON:
					main.setHumidity(reader.nextLong());
					break;
				case Main.pressure_JSON:
					main.setPressure(reader.nextDouble());
					break;
				case Main.seaLevel_JSON:
					main.setSeaLevel(reader.nextDouble());
					break;
				case Main.temp_JSON:
					main.setTemp(reader.nextDouble());
					break;
				case Main.tempMax_JSON:
					main.setTempMax(reader.nextDouble());
					break;
				case Main.tempMin_JSON:
					main.setTempMin(reader.nextDouble());
					break;
    			default:
   				 reader.skipValue();
   				 break;
				}
    		}
    	reader.endObject();
    	
    	return main;
    }

    /**
     * Parse a Json stream and return a Wind Object.
     */
    public Wind parseWind(JsonReader reader) throws IOException {

        // TODO -- you fill in here.  
    	reader.beginObject();
    	Wind wind = new Wind();
    	try {
    		while(reader.hasNext()) {
    			String name = reader.nextName();
				Log.d(TAG, "Wind name : " + name);
    			switch (name) {
				case Wind.deg_JSON:
					wind.setDeg(reader.nextDouble());
					break;
				case Wind.speed_JSON:
					wind.setSpeed(reader.nextDouble());
					break;
    			default:
   				 reader.skipValue();
   				 break;
				}
    		}
    	}finally {
    		reader.endObject();
    	}
    	
    	return wind;
    }

    /**
     * Parse a Json stream and return a Sys Object.
     */
    public Sys parseSys(JsonReader reader) throws IOException {

        // TODO -- you fill in here.    
    	reader.beginObject();
    	Sys sys = new Sys();
    	try {
    		while(reader.hasNext()) {
    			String name = reader.nextName();
				Log.d(TAG, "Sys name : " + name);
    			switch (name) {
				case Sys.country_JSON:
					sys.setCountry(reader.nextString());
					break;
				case Sys.message_JSON:
					sys.setMessage(reader.nextDouble());
					break;
				case Sys.sunrise_JSON:
					sys.setSunrise(reader.nextLong());
					break;
				case Sys.sunset_JSON:
					sys.setSunset(reader.nextLong());
					break;
    			default:
   				 reader.skipValue();
   				 break;
				}
    		}
    	} finally {
    		reader.endObject();
    	}
    	return sys;
    }
}
