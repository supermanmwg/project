package com.example.parceltest;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Person mwg = new Person();
		mwg.setAge(18);
		mwg.setSex("man");
		mwg.setName("MengWeiguang");
		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra(PERSON, mwg);
		startActivity(intent);
	}

}
