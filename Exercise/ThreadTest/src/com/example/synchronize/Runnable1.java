package com.example.synchronize;

public class Runnable1 implements Runnable {

	private SimpleBlockingQueue mq = null;

	public Runnable1(SimpleBlockingQueue mq) {
		this.mq = mq;
	}

	@Override
	public void run() {
		Integer i = 0;
		while (true) {
			if (null != mq) {
				try {
					Thread.sleep(100);
					mq.put((++i).toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
