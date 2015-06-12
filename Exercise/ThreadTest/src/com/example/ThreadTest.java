package com.example;

public class ThreadTest {


	public static void main(String[] args) {
		
		long id;
		HelloRunnable r = new HelloRunnable();
		Thread hello = new Thread(r);
		hello.start();
		id = hello.getId();
		
		TerminateRunnable t = new TerminateRunnable(hello);
		Thread temThread = new Thread(t); //其实Java传的参数都是引用
		temThread.start();
		try {
			temThread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			hello.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Main Thread exit");
	}
}
