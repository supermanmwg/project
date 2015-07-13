package com.weather.operation;

import android.annotation.SuppressLint;


public class CnLangOpsImpl implements LangOps{

	public static final String REFRESH_SUCCESS = "ˢ�����";
	public static final String LOCATE_SUCCESS = "��λ���";
	public static final String NO_INPUT = "�������������";
	public static String NET_ERROR = "�����������";
	public static String CITY_NOT_FOUND = "����û���ҵ�";
	public static String PM2_5_ERROR = "PM2_5 �������";
	public static String OR = "��";
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
    		return "�޷�";
    	}  else if (0.3 <= speed && speed < 1.5) {
    		return "���";
    	} else if (1.5 <= speed && speed < 3.3) {
    		return "���";
    	} else if (3.3 <= speed && speed < 5.4) {
    		return "΢��";
    	} else if (5.4 <= speed && speed < 7.9) {
    		return "�ͷ�";
    	} else if (7.9 <= speed && speed < 10.7) {
    		return "�ᾢ��";
    	} else if (10.7 <= speed && speed < 13.8) {
    		return "ǿ��";
    	} else if (13.8 <= speed && speed < 17.1) {
    		return "����";
    	} else if (17.1 <= speed && speed < 20.7) {
    		return "���";
    	} else if (20.7 <= speed && speed < 24.4) {
    		return "�ҷ�";
    	} else if (24.4 <= speed && speed < 28.4) {
    		return "���";
    	} else if (28.4 <= speed && speed < 32.6) {
    		return "����";
    	} else if (32.6 <= speed) {
    		return "̨��";
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
			return "��";
		} else if(50 < aqi && aqi <= 100) {
			return "��";
		} else if (100 < aqi && aqi <= 150) {
			return "�����Ⱦ";
		} else if (150 < aqi && aqi <= 200) {
			return "�ж���Ⱦ";
		} else if(200 < aqi && aqi <= 300){
			return "�ض���Ⱦ";
		} else {
			return "������Ⱦ";
		}
	}

	@Override
	public String getWeatherLang() {
		
		return LANG;
	}

	@Override
	public String getWindLevelString() {
		// TODO Auto-generated method stub
		return "��";
	}

	@Override
	public String getHumidString() {
		
		return "ʪ�ȣ�";
	}

	@Override
	public String getDateFormat() {
		
		return "M��d��";
	}

	@Override
	public String getCityName(String name) {
		
		return name;
	}

}
