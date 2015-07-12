package com.weather.retrofit;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

public interface PM2_5ServiceProxy {
	/*
	 * http://www.pm25.in/api/querys/pm2_5.json?city=Öéº£&token=5j1znBVAsnSf5xQyNQyq
	 */
	
	public final String sPM2_5_Service_URL_Retro = "http://www.pm25.in/api/querys";
	
	@GET("//pm2_5.json")
	List<PM2_5Item> getPM2_5(@Query("city") String location ,@Query("token") String key);

}
