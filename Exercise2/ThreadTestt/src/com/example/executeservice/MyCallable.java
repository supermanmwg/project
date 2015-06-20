package com.example.executeservice;

import java.util.concurrent.Callable;

public class MyCallable implements  Callable<String> {

	private String mString;
	
	public MyCallable() {
	}
	
	public MyCallable(String m) {
		this.mString = m;
	}
	
	@Override
	public String call() throws Exception {
		return mString;
	}

}
