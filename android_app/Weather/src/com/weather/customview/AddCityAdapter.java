package com.weather.customview;

import java.util.List;

import com.weather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddCityAdapter extends ArrayAdapter<City>{

	private int mResourceId;
	
	public AddCityAdapter(Context context,
			int textViewResourceId, List<City> objects) {
		super(context, textViewResourceId, objects);
		mResourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		City mCity = getItem(position);
		View mView = LayoutInflater.from(getContext()).inflate(mResourceId, null);
		ImageView mIcon = (ImageView) mView.findViewById(R.id.weather_icon);
		TextView mCityName = (TextView) mView.findViewById(R.id.city_name);
		TextView mTemp = (TextView)mView.findViewById(R.id.temperature);

		mCityName.setText(mCity.getName());
		mTemp.setText(mCity.getTemperature());
		return mView;
	}

}
