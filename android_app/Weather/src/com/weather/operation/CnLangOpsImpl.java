package com.weather.operation;

import android.annotation.SuppressLint;


public class CnLangOpsImpl implements LangOps{

	public static final String REFRESH_SUCCESS = "刷新完成";
	public static final String LOCATE_SUCCESS = "定位完成";
	public static final String NO_INPUT = "请输入城市名称";
	public static String NET_ERROR = "网络出现问题";
	public static String CITY_NOT_FOUND = "城市没有找到";
	public static String PM2_5_ERROR = "PM2_5 请求错误";
	public static String OR = "或";
	public static String LANG = "zh_cn";
	
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
	
	@Override
    public String convertWindDetails(double speed) {
    	if(0 <= speed && speed < 0.3) {
    		return "无风";
    	}  else if (0.3 <= speed && speed < 1.5) {
    		return "软风";
    	} else if (1.5 <= speed && speed < 3.3) {
    		return "轻风";
    	} else if (3.3 <= speed && speed < 5.4) {
    		return "微风";
    	} else if (5.4 <= speed && speed < 7.9) {
    		return "和风";
    	} else if (7.9 <= speed && speed < 10.7) {
    		return "轻劲风";
    	} else if (10.7 <= speed && speed < 13.8) {
    		return "强风";
    	} else if (13.8 <= speed && speed < 17.1) {
    		return "疾风";
    	} else if (17.1 <= speed && speed < 20.7) {
    		return "大风";
    	} else if (20.7 <= speed && speed < 24.4) {
    		return "烈风";
    	} else if (24.4 <= speed && speed < 28.4) {
    		return "狂风";
    	} else if (28.4 <= speed && speed < 32.6) {
    		return "暴风";
    	} else if (32.6 <= speed) {
    		return "台风";
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
    
    @Override
	public String genWeatherConditon(int aqi) {
		if(0 <= aqi && aqi <= 50) {
			return "优";
		} else if(50 < aqi && aqi <= 100) {
			return "良";
		} else if (100 < aqi && aqi <= 150) {
			return "轻度污染";
		} else if (150 < aqi && aqi <= 200) {
			return "中度污染";
		} else if(200 < aqi && aqi <= 300){
			return "重度污染";
		} else {
			return "严重污染";
		}
	}

	@Override
	public String getWeatherLang() {
		
		return LANG;
	}

	@Override
	public String getWindLevelString() {
		// TODO Auto-generated method stub
		return "级";
	}

	@Override
	public String getHumidString() {
		
		return "湿度：";
	}

	@Override
	public String getDateFormat() {
		
		return "M月d日";
	}

	@Override
	public String getCityName(String name) {
		
		return name;
	}

}
