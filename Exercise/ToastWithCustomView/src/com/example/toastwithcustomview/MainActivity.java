package com.example.toastwithcustomview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button toastButton;
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.main);
		initView();
	}
	
	private void initView() {
		toastButton = (Button)findViewById(R.id.toast_button);
		toastButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.toast_button:
				toastCustom();
				break;
			default:
				break;
		}
	}
	
	private void toastCustom() {
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(getLayoutInflater().inflate(R.layout.main, null));
		toast.show();
	}
		
}
