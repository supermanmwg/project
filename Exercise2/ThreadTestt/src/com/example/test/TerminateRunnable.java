package com.example.test;

public class TerminateRunnable implements Runnable{

	private Thread temThread;
	
	public TerminateRunnable() {
	}
	
	public TerminateRunnable(Thread t){
		this.temThread = t;
	}
	@Override
	public void run() {
		System.out.println("\n----Terminate Runnable start!");
		temThread.interrupt();
	}
	

}
