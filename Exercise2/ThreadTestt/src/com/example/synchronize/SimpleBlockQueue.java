package com.example.synchronize;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockQueue {

	private static int MAX_BUF_SIZE = 10;
	private Object lock = new Object();
	
	private List<String> queueList = new ArrayList<String>();
	
	private int bufferSize = 0;
	
	public void put(String msg) {
		synchronized(this) {
			if(bufferSize < MAX_BUF_SIZE) {
				queueList.add(msg);
				bufferSize++;
				notifyAll();
			}
		}
	}
	
	public String take() throws InterruptedException {
		
		synchronized (this) {
			while (queueList.isEmpty()) {
				wait();
			}
			bufferSize--;
			return queueList.remove(0);
		}
	}
	
}
