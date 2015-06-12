package com.example.synchronize;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockingQueue {
	
	private static int MAX_BUFFER_SIZE = 10;
	private List<String> mQ = new ArrayList<String>();
	private Object lock =null;
	private int buffersize = 0;
	
	public SimpleBlockingQueue() {

	}
	
	public void put(String msg) {
		
		synchronized (this) {
			if(buffersize < MAX_BUFFER_SIZE) {
				mQ.add(msg);
				buffersize++;
				System.out.println("+++++put");
				System.out.println("+++++buffer size is " + buffersize);
				notifyAll();
			}
		}
	}
	
	public String take() throws InterruptedException {
		
		 String mq = "hahaha";
		
		synchronized (this) {
			while (mQ.isEmpty()){
				wait();
			}
			buffersize--;
			System.out.println("-----take");
			return mQ.remove(0);	
		}
	}

}
