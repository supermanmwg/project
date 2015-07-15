package com.weather.mwg.operation.langOpsImpl;

import com.weather.mwg.operation.LangOps;
import com.weather.mwg.utils.Utils;

import android.annotation.SuppressLint;

public class EnLangOpsImpl implements LangOps{
	
	public static final String REFRESH_SUCCESS = "Refresh success";
	public static final String LOCATE_SUCCESS = "Locate Successfully";
	public static final String NO_INPUT = "Please enter the city's name";
	public static final String GPS_NOT_OPEN = "GPS was not opended";
	public static String NET_ERROR = "Network Error";
	public static String CITY_NOT_FOUND = "The city was not found";
	public static String PM2_5_ERROR = "PM2_5 Error";
	public static String OR = "or";
	public static String LANG = "";
	
	
	@Override
	public String getRefreshSuccess() {
		
		return REFRESH_SUCCESS;
	}

	@Override
	public String getLocateSuccess() {
		
		return LOCATE_SUCCESS;
	}

	@Override
	public String getNo_Input() {
		
		return NO_INPUT;
	}

	@Override
	public String getNetError() {
		
		return NET_ERROR;
	}

	@Override
	public String getCityNotFound() {
		
		return CITY_NOT_FOUND;
	}

	@Override
	public String getPM25Error() {
		
		return PM2_5_ERROR;
	}

	@Override
	public String getOr() {
		
		return OR;
	}
	
	/**
	 * Convert wind speed to details info
	 */
	@Override
    public String convertWindDetails(double speed) {
    	if(0 <= speed && speed < 0.3) {
    		return "Calm";
    	}  else if (0.3 <= speed && speed < 1.5) {
    		return "Light air";
    	} else if (1.5 <= speed && speed < 3.3) {
    		return "Light breeze";
    	} else if (3.3 <= speed && speed < 5.4) {
    		return "Gentle breeze";
    	} else if (5.4 <= speed && speed < 7.9) {
    		return "Moderate breeze";
    	} else if (7.9 <= speed && speed < 10.7) {
    		return "Fresh breeze";
    	} else if (10.7 <= speed && speed < 13.8) {
    		return "Strong breeze";
    	} else if (13.8 <= speed && speed < 17.1) {
    		return "Moderate gale";
    	} else if (17.1 <= speed && speed < 20.7) {
    		return "Fresh gale";
    	} else if (20.7 <= speed && speed < 24.4) {
    		return "Strong gale";
    	} else if (24.4 <= speed && speed < 28.4) {
    		return "Whole gale";
    	} else if (28.4 <= speed && speed < 32.6) {
    		return "Storm";
    	} else if (32.6 <= speed) {
    		return "Hurricane";
    	} else {
    		return null;
    	}
    }

    /**
     * Convert wind speed format from m/s to level
     * @param speed
     * 				: wind speed (m/s)
     * @return level 
     * 				: wind level (int)
     */
    @SuppressLint("SimpleDateFormat")
    @Override
	public int convertSpeed(double speed) {
    	if(0 <= speed && speed < 0.3) {
    		return 0;
    	}  else if (0.3 <= speed && speed < 1.5) {
    		return 1;
    	} else if (1.5 <= speed && speed < 3.3) {
    		return 2;
    	} else if (3.3 <= speed && speed < 5.4) {
    		return 3;
    	} else if (5.4 <= speed && speed < 7.9) {
    		return 4;
    	} else if (7.9 <= speed && speed < 10.7) {
    		return 5;
    	} else if (10.7 <= speed && speed < 13.8) {
    		return 6;
    	} else if (13.8 <= speed && speed < 17.1) {
    		return 7;
    	} else if (17.1 <= speed && speed < 20.7) {
    		return 8;
    	} else if (20.7 <= speed && speed < 24.4) {
    		return 9;
    	} else if (24.4 <= speed && speed < 28.4) {
    		return 10;
    	} else if (28.4 <= speed && speed < 32.6) {
    		return 11;
    	} else if (32.6 <= speed) {
    		return 12;
    	} else {
    		return -1;
    	}
    }
    
	/**
	 * Generate the weather air condition from aqi value
	 */
    @Override
	public String genWeatherConditon(int aqi) {
		if(0 <= aqi && aqi <= 50) {
			return "Good";
		} else if(50 < aqi && aqi <= 100) {
			return "Moderate";
		} else if (100 < aqi && aqi <= 150) {
			return "Unhealthy for Sensitive Groups";
		} else if (150 < aqi && aqi <= 200) {
			return "Unhealthy";
		} else if(200 < aqi && aqi <= 300){
			return "Very Unhealthy";
		} else {
			return "Hazardous";
		}
	}

	@Override
	public String getWeatherLang() {
		
		return LANG;
	}

	@Override
	public String getWindLevelString() {
		// TODO Auto-generated method stub
		return "level";
	}

	@Override
	public String getHumidString() {
		return "Humidity:";
	}

	@Override
	public String getDateFormat() {
		
		return "dd-MM-yyyy";
	}

	@Override
	public String getCityName(String name) {
		
		
		return captureName(Utils.HanyuToPinyin(name));
	}

    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
        
    }

	@Override
	public String getGPSNotFound() {
		
		return GPS_NOT_OPEN;
	}

}
