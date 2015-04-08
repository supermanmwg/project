package com.example.healthalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends Activity{
	
	private SensorManager mSensorManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onDestroy() {

		super.onDestroy();
		if(mSensorManager != null) {
			mSensorManager.unregisterListener(listener);
		}
	}
	
	private SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			
			if(xValue > 15 || yValue > 15 || zValue > 15) {
				Toast.makeText(getApplicationContext(), "ƒ÷÷”Õ£÷π", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};

}
