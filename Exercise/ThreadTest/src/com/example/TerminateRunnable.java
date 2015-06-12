package com.example;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public class TerminateRunnable implements Runnable{

	private Thread temThread =null;
	
	public TerminateRunnable(Thread thread) {
		temThread = thread;
	}
	@Override
	public void run() {
		System.out.println("I am terminated Runnable");
		while (null == temThread) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Terminate the Hello Runnable");
		temThread.interrupt();

	}

}
