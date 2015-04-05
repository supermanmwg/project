package com.example.networktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONResponseHandler implements ResponseHandler<List<String>> {


	private static final String LONGITUDE_TAG = "lng";
	private static final String LATITUDE_TAG = "lat";
	private static final String MAGNITUDE_TAG = "magnitude";
	private static final String EARTHQUAKE_TAG = "earthquakes";

	@Override
	public List<String> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		List<String> result = new ArrayList<String>();
		String JSONResponse = new BasicResponseHandler()
				.handleResponse(response);
		try {

			// Get top-level JSON Object - a Map
			JSONObject responseObject = (JSONObject) new JSONTokener(
					JSONResponse).nextValue();

			// Extract value of "earthquakes" key -- a List
			JSONArray earthquakes = responseObject
					.getJSONArray(EARTHQUAKE_TAG);

			// Iterate over earthquakes list
			for (int idx = 0; idx < earthquakes.length(); idx++) {

				// Get single earthquake data - a Map
				JSONObject earthquake = (JSONObject) earthquakes.get(idx);

				// Summarize earthquake data as a string and add it to
				// result
				result.add(MAGNITUDE_TAG + ":"
						+ earthquake.get(MAGNITUDE_TAG) + ","
						+ LATITUDE_TAG + ":"
						+ earthquake.getString(LATITUDE_TAG) + ","
						+ LONGITUDE_TAG + ":"
						+ earthquake.get(LONGITUDE_TAG));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
