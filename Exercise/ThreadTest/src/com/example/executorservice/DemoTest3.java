package com.example.executorservice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.example.synchronize.DemoTest;

public class DemoTest3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		//execute method
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("execute() runnable run");
			}
		});
		
		//submit runnable
		Future future = executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("submit() runnable run");
			}
		});
		
		if(null != future.get()) {
			System.out.println("the task hasn't finished");
		} else {
			System.out.println("the task has finished");
		}
		
		//submit callable
		future = executorService.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println("submit() callable run"); 
				return " submit callable result return";
			}
		});
		System.out.println("future get :" + future.get());
		
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		DemoTest3 demo = new DemoTest3();
		MyCallable task1 = demo.new MyCallable("task 1");
		MyCallable task2 = demo.new MyCallable("task 5");
		MyCallable task3 = demo.new MyCallable("task 3");
		MyCallable task4 = demo.new MyCallable("task 4");
		callables.add(task1);
		callables.add(task2);
		callables.add(task3);
		callables.add(task4);
		String result = executorService.invokeAny(callables);
		System.out.println("invoke any return is " + result);
		
		List<Future<String>> futures = executorService.invokeAll(callables);
		
		for (Future<String> f : futures ) {
			System.out.println(f.get());
		}
		//submit shutdown
		executorService.shutdown();
	}


	class MyCallable implements Callable<String>{

		private String rString;
		
		public MyCallable() {
		}
		
		public MyCallable(String name){
			
			this.rString = name;
			
		}
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			return rString;
		}
		
	}
}
