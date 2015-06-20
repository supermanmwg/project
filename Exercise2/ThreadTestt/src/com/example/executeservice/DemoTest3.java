package com.example.executeservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DemoTest3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		// executor runnable
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("execute runnable");
			}
		});
		
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("submit runnable");
			}
		});
		
		String result = executorService.submit(new MyCallable("submit callable")).get();
		
		System.out.println(result);
		
		List<Callable<String>> callables = new ArrayList<Callable<String>>();
		callables.add(new MyCallable("task 1"));
		callables.add(new MyCallable("task 2"));
		callables.add(new MyCallable("task 3"));
		
		result = executorService.invokeAny(callables);
		System.out.println("invokeany invoke " + result);
		
		List<Future<String>> futures = executorService.invokeAll(callables);
		
		for (Future<String> f : futures) {
			System.out.println(f.get());
		}
		
	}
}
