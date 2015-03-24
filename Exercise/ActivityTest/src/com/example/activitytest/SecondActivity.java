package com.example.activitytest;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity {

	private static final String TAG = "SecondActivity";

	@Override
	protected void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.second);
	}

	// when press back key ,the onBackPressed() method is called.
	// Be carefully, don't add "super.onBackPressed();" in first line of the
	// method,If not, it will back to the original activity immediately
	@Override
	public void onBackPressed() {
		Random r = new Random();
		int i = r.nextInt(10);
		Intent intent = new Intent();
		intent.putExtra("haha", i);
		Log.i(TAG, "I am num" + i);
		setResult(RESULT_OK, intent);
		finish();

	}

}
