package com.example.test;

public class StartRunnale implements Runnable {

	private boolean stopSign = false;
	@Override
	public void run() {
		System.out.println("----Start runnable start");
		while (true != stopSign) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("\nsleep------Start runnable receive interrupt and exit!");
				stopSign = true;
			}
			
			if(Thread.interrupted()) {
				System.out.println("\ninterrupt------Start runnable receive interrupt and exit!");
				stopSign = true;
			}
		}
	}

}

