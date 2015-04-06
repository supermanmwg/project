package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DynamicReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "dynamic broadcast receiver", Toast.LENGTH_SHORT).show();
		String tmp = getResultData() == null ? "":getResultData();
		setResultData(tmp + ":Dynamic Receiver:");
		
	}

}
