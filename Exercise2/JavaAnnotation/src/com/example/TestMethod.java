package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TestMethod {
	
	@Secured(role="user")
	public void store() {
		ProcessAnnotation.processAnnotation(this);
	}

	@Secured(role="")
	public void hasRole(){
		System.out.println("HaHa, I have a 'store' role");
	}
	
	@Secured(role="")
	public void hasNotRole() {
		System.out.println("HaHa, I haven't a 'store' role");
	}

}
