package com.weather.activities;

import com.weather.R;
import com.weather.lang.Chinese;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class AlterActivity extends Activity {
	public static final String DATA = "data";
	public final String TAG = getClass().getSimpleName(); 
	private EditText mEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alter);
		
		mEditText = (EditText) findViewById(R.id.edit);
		
	}
	
	public void onOK(View v) {
		String name = mEditText.getText().toString();
		if(null != name && !name.equals("")) {
			Log.d(TAG, "edit text is " + name);
			Intent intent = new Intent();
			intent.putExtra(DATA, name);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(), Chinese.NO_INPUT, Toast.LENGTH_SHORT).show();
		}
		
	}

}
