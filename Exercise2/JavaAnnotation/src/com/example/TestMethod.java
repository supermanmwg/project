package com.example;

public class TestMethod {
	
	@Secured(role="user")
	public void store() {
		ProcessAnnotation.processAnnotation(this);
	}

	public void hasRole(){
		System.out.println("HaHa, I have a 'store' role");
	}
	
	public void hasNotRole() {
		System.out.println("HaHa, I haven't a 'store' role");
	}

}
