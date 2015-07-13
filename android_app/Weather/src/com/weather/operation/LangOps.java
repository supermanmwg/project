package com.weather.operation;

public interface LangOps {
	public String getRefreshSuccess();
	public String getLocateSuccess();
	public String getNo_Input();
	public String getNetError();
	public String getCityNotFound();
	public String getPM25Error();
	public String getOr();
	public String convertWindDetails(double speed);
	public int convertSpeed(double speed);
	public String genWeatherConditon(int aqi);
	public String getWeatherLang();
	public String getWindLevelString();
	public String getHumidString();
	public String getDateFormat();
	public String getCityName(String name);
}
