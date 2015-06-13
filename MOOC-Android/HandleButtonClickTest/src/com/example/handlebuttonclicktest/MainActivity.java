package com.example.handlebuttonclicktest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	/**
	 * handle button click
	 * 
	 * @param v
	 * 		The button view
	 * 
	 *  @return void
	 */
	
	public void handleButtonClick(View v) {
		
		Toast.makeText(this, "button 1", Toast.LENGTH_SHORT).show();
		switch (v.getId()) {
		case R.id.button1:
			Toast.makeText(this, "button 1", Toast.LENGTH_SHORT).show();
			break;
		
		case R.id.button2:
			Toast.makeText(this, "button 2", Toast.LENGTH_SHORT).show();
			break;
		
		case R.id.button3:
			Toast.makeText(this, "button 3", Toast.LENGTH_SHORT).show();
			break;

		default:
			Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}