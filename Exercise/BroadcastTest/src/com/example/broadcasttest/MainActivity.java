package com.example.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String CUSTOM_INTENT = "com.example.broadcasttest.broadreceiver.haha";

	private Button broadcastButton;
	private Button dynamicBroadcastButton;

	private final IntentFilter intentFilter = new IntentFilter(CUSTOM_INTENT);
	private final DynamicReceiver dynamicReceiver = new DynamicReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// dynamically broadcast receiver
		registerReceiver(dynamicReceiver, intentFilter);

		broadcastButton = (Button) findViewById(R.id.broadcast_button);
		broadcastButton.setOnClickListener(this);

		dynamicBroadcastButton = (Button) findViewById(R.id.dynamic_broadcast_button);
		dynamicBroadcastButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.broadcast_button:
			sendOrderedBroadcast(new Intent(CUSTOM_INTENT), null);
			break;
		case R.id.dynamic_broadcast_button:
			sendOrderedBroadcast(new Intent(CUSTOM_INTENT), null,
					new BroadcastReceiver() {

						@Override
						public void onReceive(Context context, Intent intent) {
							Toast.makeText(context,
									"Final result is" + getResultData(),
									Toast.LENGTH_SHORT).show();
						}
					}, null, 0, null, null);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(dynamicReceiver);
		super.onDestroy();
	}

}
