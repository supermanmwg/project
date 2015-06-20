package com.example.synchronize;

public class DemoTest2 {

	public static void main(String[] args) throws InterruptedException {
		SimpleBlockQueue mq = new SimpleBlockQueue();
		
		Consumer mConsumer = new Consumer(mq);
		Productor mProductor = new Productor(mq);
		
		Thread conThread = new Thread(mConsumer);
		Thread proThread = new Thread(mProductor);
		
		conThread.start();
		proThread.start();
		
		proThread.join();
		conThread.join();
		
	}
}
