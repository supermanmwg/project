package com.example.synchronize;

public class Productor implements Runnable{

	private SimpleBlockQueue mq;
	private static Integer i = 0;
	public Productor(SimpleBlockQueue mq) {
		this.mq = mq;
	}
	
	@Override
	public void run() {
		System.out.print("---Productor start");
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mq.put((++i).toString());
		}
	}

}
