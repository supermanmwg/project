package com.weather.mwg.retrofit;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Interface defining methods used by RetroFit to access current
 * weather data from the pm2.5 Service web service.
 * example:  http://www.pm25.in/api/querys/pm2_5.json?city=Öéº£&token=5j1znBVAsnSf5xQyNQyq
 */
public interface PM2_5ServiceProxy {
	/**
     * URL to the Web Search web service to use with the Retrofit
     * service.
     */
	public final String sPM2_5_Service_URL_Retro = "http://www.pm25.in/api/querys";
	
	@GET("/pm2_5.json")
	List<PM2_5Item> getPM2_5(@Query("city") String location ,@Query("token") String key);

}
