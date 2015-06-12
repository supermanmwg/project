package com.example.synchronize;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockingQueue {
	
	private List<String> mQ = new ArrayList<String>();
	private Object lock =null;
	
	public SimpleBlockingQueue() {

	}
	
	public void put(String msg) {
		
		synchronized (this) {
			mQ.add(msg);
			notifyAll();
		}
	}
	
	public String take() throws InterruptedException {
		
		 String mq = "hahaha";
		
		synchronized (this) {
			while (mQ.isEmpty()){
				wait();
			}
			return mQ.remove(0);	
		}
	}

}
