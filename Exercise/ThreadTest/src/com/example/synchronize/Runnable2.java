package com.example.synchronize;

public class Runnable2 implements Runnable {

	private SimpleBlockingQueue mq;

	public Runnable2(SimpleBlockingQueue mq) {
		this.mq = mq;
	}

	@Override
	public void run() {
		while (true) {
			if (null != mq) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					System.out.println(mq.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
