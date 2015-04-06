package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Toast.makeText(context, "haha", Toast.LENGTH_SHORT).show();
		
		String tmp = getResultData() == null?"":getResultData();
		setResultData(tmp + ":Receiver:");
		
	}

}
