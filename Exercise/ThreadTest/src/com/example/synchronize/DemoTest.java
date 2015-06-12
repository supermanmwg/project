package com.example.synchronize;

public class DemoTest {
	
	public static void main(String[] args ){

		SimpleBlockingQueue mq = new SimpleBlockingQueue();
		Thread t1 = new Thread(new Runnable1(mq));
		Thread t2 = new Thread(new Runnable2(mq));
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
