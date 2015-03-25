package com.example.intenttest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {
	
	private Button press;
	private final static String TAG="haha";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		float x = getResources().getDisplayMetrics().widthPixels/2;
		float y = getResources().getDisplayMetrics().heightPixels/2;
		Log.i(TAG,"x is " + x + ". y is " + y);
		
		press = (Button) findViewById(R.id.press);
		float ax = getResources().getDimension(R.dimen.button_width);
		float ay = getResources().getDimension(R.dimen.button_height);
		press.setWidth((int) ax);
		press.setHeight((int) ay);
		Log.i(TAG,"ax is " + ax + ". ay is " + ay);
		press.setX(x-ax/2);
		press.setY(y-ay/2);
		press.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.press:
			//Intent newInt = new Intent();
			//newInt.setAction(Intent.ACTION_DIAL);
			//newInt.setData(Uri.parse(getResources().getString(R.string.telephone_num)));
			Intent newInt = new Intent("com.example.activitytest.SecondActivity2");
			startActivity(newInt);
			break;
		default:
			break;
		}
	}

}
