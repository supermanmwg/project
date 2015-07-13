package com.weather.activities;

import com.weather.R;
import com.weather.operation.CnLangOpsImpl;
import com.weather.operation.EnLangOpsImpl;
import com.weather.operation.LangOps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class AlterActivity extends BaseActivity {
	public static final String DATA = "data";
	public final String TAG = getClass().getSimpleName(); 
	private EditText mEditText;
	private LangOps mLangOps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alter);
		if(CN == getLanguage()) {
			mLangOps = new CnLangOpsImpl();
		} else {
			mLangOps = new EnLangOpsImpl();
		}
		
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
			Toast.makeText(getApplicationContext(), mLangOps.getNo_Input(), Toast.LENGTH_SHORT).show();
		}
		
	}
	

}
