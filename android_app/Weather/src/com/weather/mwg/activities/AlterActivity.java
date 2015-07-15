package com.weather.mwg.activities;

import com.weather.mwg.R;
import com.weather.mwg.operation.LangOps;
import com.weather.mwg.operation.langOpsImpl.CnLangOpsImpl;
import com.weather.mwg.operation.langOpsImpl.EnLangOpsImpl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class AlterActivity extends BaseActivity {

	/**
	 * City name edit text
	 */
	private EditText mEditText;

	/**
	 * Language operation(English or Chinese)
	 */
	private LangOps mLangOps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alter);
		if (CN == getLanguage()) {
			mLangOps = new CnLangOpsImpl();
		} else {
			mLangOps = new EnLangOpsImpl();
		}

		mEditText = (EditText) findViewById(R.id.edit);
	}

	/**
	 * onClick event of OK button
	 */
	public void onOK(View v) {
		String name = mEditText.getText().toString();
		if (null != name && !name.equals("")) {
			Log.d(TAG, "edit text is " + name);
			Intent intent = new Intent();
			intent.putExtra(DATA, name);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(), mLangOps.getNo_Input(),
					Toast.LENGTH_SHORT).show();
		}
	}
}
