package com.weather.mwg.jsonlocation;

import java.io.InputStream;

public interface LocationJsonParser{

	public String parseJsonStream(InputStream in);
	
}
