package com.example.networktest;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.R.integer;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AndroidHttpClientTask extends AsyncTask<Void, Void, List<String>> {

	private static final String TAG = "AndroidHttpClientTask";
	private static final String USER_NAME = "aporter";
	private static final String URL = "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
			+ USER_NAME;
	private static TextView mTextView;
	AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
	private HttpInterface mHttpInterface = null;
	
	@Override
	protected void onPreExecute() {
		
		super.onPreExecute();
		mHttpInterface = (HttpInterface)(BaseActivity.getmActivity());
	}
	
	@Override
	protected List<String> doInBackground(Void... params) {
		
		HttpGet request = new HttpGet(URL);
		ResponseHandler<List<String>> responseHandler = new JSONResponseHandler();
		
		try {
			return mClient.execute(request,responseHandler);
		} catch (ClientProtocolException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<String> result) {
		// TODO Auto-generated method stub
		if(null != mClient) mClient.close();
		
		for(int i = 0; i < result.size(); i++) {
			Log.i(TAG,result.get(i));
		}
		
		mHttpInterface.setAdapter(result);
	}

}
