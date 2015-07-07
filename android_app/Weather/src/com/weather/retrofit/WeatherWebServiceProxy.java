package com.weather.retrofit;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Interface defining methods used by RetroFit to access current
 * weather data from the Weather Service web service.
 * example: http://api.openweathermap.org/data/2.5/forecast/daily?q=London&units=metric&cnt=7
 */
public interface WeatherWebServiceProxy {
    /**
     * URL to the Web Search web service to use with the Retrofit
     * service.
     */
    final String sWeather_Service_URL_Retro =
        "http://api.openweathermap.org/data/2.5";
    
    /**
     *For temperature in Fahrenheit use units=imperial
     *For temperature in Celsius use units=metric
     *For temperature in Kelvin is used by default
    */
    public static final String Celsius = "metric";
    public static final String Fahrenheit = "imperial";
    
    @GET("/forecast/daily")
    WeatherDataForeCast getWeatherData(@Query("q") String location, @Query("units") String metric, @Query("cnt") long cnt);
    
    @GET("/weather")
   WeatherDataCurrent getWeatherData(@Query("q") String location ,@Query("units") String metric);
}

