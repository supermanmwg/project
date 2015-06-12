package com.example.parceltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		Person haha = intent.getParcelableExtra(PERSON);
		Log.i(PERSON, "haha name is " + haha.getName() + ". haha age is "
				+ haha.getAge() + ".haha sex is " + haha.getSex());

	}

}
