package com.example.test;

public class DemoTest1 {

	public static void main(String[] args) throws InterruptedException {
		
		Thread tStart = new Thread(new StartRunnale());
		Thread tTerm = new Thread(new TerminateRunnable(tStart));
		tStart.start();
		tTerm.start();
		
		tTerm.join();
		tStart.join();
	}
}
