package com.example.synchronize;

public class DemoTest {
	
	public static void main(String[] args ){

		SimpleBlockingQueue mq = new SimpleBlockingQueue();
		Thread t1 = new Thread(new Runnable1(mq));
		Thread t2 = new Thread(new Runnable2(mq));
		Thread t3 = new Thread(new Runnable2(mq));
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			//Because t1 is a while loop, so the main thread will wait the t1 loop, so
			// t2 & t3 would not use the join() method.
		//	t2.join();
		//	t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
