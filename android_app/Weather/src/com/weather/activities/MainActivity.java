package com.weather.activities;

import com.weather.R;
import com.weather.operation.ImageOps;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity  extends FragmentActivity implements OnClickListener,
OnPageChangeListener{
	
	private ImageOps mImageOps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);
		
		mImageOps = new ImageOps(this);
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		if (positionOffset >= 0.9 || positionOffset < 0.3)
		{
			 mImageOps.changeTab(position);
		}

	}
	@Override
	public void onPageSelected(int arg0) {
		
	}

	@Override
	public void onClick(View v) {
		mImageOps.clickTab(v);
		
	}

}
