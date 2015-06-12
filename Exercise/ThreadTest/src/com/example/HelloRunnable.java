package com.example;

public class HelloRunnable implements Runnable {
	
	private volatile boolean stopSign = true;
	
	@Override
	public void run() {
		System.out.println("I am hello runnable");

		while(true == stopSign){
			if(Thread.interrupted()) {
				stopSign = false;
				System.out.println("hello runnable is terminated");
			}
			}
		}
	}


