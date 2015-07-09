package com.weather.customview;

import java.util.List;

import com.weather.R;
import com.weather.aidl.WeatherData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddCityAdapter extends ArrayAdapter<WeatherData>{

	private int mResourceId;
	
	public AddCityAdapter(Context context,
			int textViewResourceId, List<WeatherData> objects) {
		super(context, textViewResourceId, objects);
		mResourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WeatherData mWeatherData = getItem(position);
		View mView = LayoutInflater.from(getContext()).inflate(mResourceId, null);
		ImageView mIcon = (ImageView) mView.findViewById(R.id.weather_icon);
		TextView mCityName = (TextView) mView.findViewById(R.id.city_name);
		TextView mTemp = (TextView)mView.findViewById(R.id.temperature);

		mCityName.setText(mWeatherData.getmName());
	//	mTemp.setText(""+ mWeatherData.getmTempMin() +"бу/"+ mWeatherData + "бу");
		return mView;
	}

}
