package com.weather.activities;

import java.util.ArrayList;
import java.util.List;

import com.weather.R;
import com.weather.aidl.WeatherData;
import com.weather.customview.AddCityAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class AddCityActivity extends Activity{
	
	private List<WeatherData> cityList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addcity);
		
		initCity();
		final AddCityAdapter mAdapter = new AddCityAdapter(AddCityActivity.this, R.layout.city_item, cityList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(mAdapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.remove(mAdapter.getItem(position));
				return true;
			}
		});
	}
	
	public void initCity() {
		WeatherData zoucheng = new WeatherData();
		zoucheng.setmName("zoucheng");
		cityList.add(zoucheng);
		WeatherData jining = new WeatherData();
		jining.setmName("jining");
		cityList.add(jining);
	}

}
