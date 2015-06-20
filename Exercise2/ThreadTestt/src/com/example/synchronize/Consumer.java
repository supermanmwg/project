package com.example.synchronize;

public class Consumer implements Runnable {

	private SimpleBlockQueue mq;
	
	public Consumer(SimpleBlockQueue mqL) {
		this.mq = mqL;
	}
	
	@Override
	public void run() {
		System.out.println("\n----consumer start");
		try {
			while(true) {
				Thread.sleep(2000);
				System.out.println(mq.take());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
