package com.weather.jsonlocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.JsonReader;
import android.util.Log;

public class BaiduLocationJsonParser implements LocationJsonParser {

	public final String TAG = getClass().getSimpleName();
	
	@SuppressWarnings("resource")
	@Override
	public String parseJsonStream(InputStream in) {
		JsonReader reader;
		try {
			 reader = new JsonReader(new InputStreamReader(in,"UTF-8"));
			reader.beginObject();
			while(reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case "result" :
					return ParseResult(reader);

				default:
					Log.d(TAG, "reader name is " + name);
					reader.skipValue();
					break;
				}
			}
			reader.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.d(TAG, "json parser error");
		
		return null;
	}
	private String ParseResult(JsonReader reader) {
		try {
			reader.beginObject();
			while(reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case "addressComponent":
					return parseAddr(reader);
				default:
					reader.skipValue();
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, "json parser result  error");
		
		return null;
	}
	
	private String parseAddr(JsonReader reader) {
		String cityName = null;
		String distName = null;
		try {
			reader.beginObject();
			while(reader.hasNext()) {
				String name = reader.nextName();
				switch (name) {
				case "city":
					cityName =  reader.nextString();
					break;
				case "district":
					distName = reader.nextString();
					if(distName.contains("ÊÐ"))
						return cityName + distName + ",";
					else {
						return cityName + distName;
					}
				default:
					reader.skipValue();
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, "json parser addr  error");
		return null;
	}

}
