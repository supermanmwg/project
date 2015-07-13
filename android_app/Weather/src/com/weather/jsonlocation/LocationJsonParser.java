package com.weather.jsonlocation;

import java.io.InputStream;

public interface LocationJsonParser{

	public String parseJsonStream(InputStream in);
}
