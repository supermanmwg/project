package com.example.threadtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class HandlerIconTask implements Runnable {

	private static final long mDelay = 500;
	private final int resId;
	private final Handler handler;

	HandlerIconTask(int resId, Handler handler) {
		this.resId = resId;
		this.handler = handler;
	}

	@Override
	public void run() {

		final Bitmap tmp = BitmapFactory.decodeResource(BaseActivity
				.getmContext().getResources(), resId);
		
		for (int i = 1; i < 11; i++) {
			sleep();
		}
		
		Message msg = handler.obtainMessage(UIHandler.SET_BITMAP,tmp);
		handler.sendMessage(msg);
		
	}
	
	private void sleep() {
		try {
			Thread.sleep(mDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
