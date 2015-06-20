package com.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProcessAnnotation {
	
	public static void  processAnnotation(Object o) {
		try {
			Class c = o.getClass();
			System.out.println("Class name is " + c.getSimpleName());
			System.out.println("process annotation start ...");
			for(Method field : c.getDeclaredMethods()){
				System.out.println("field name is " + field.getName());
				field.setAccessible(true);
				Secured role = field.getAnnotation(Secured.class);
			//	System.out.println("role is " + role);
				if(null != role) {
				//	System.out.println("what !!");
					TestMethod t = (TestMethod) o;
					if("user" == role.role()) {
						t.hasRole();
					} else {
						t.hasNotRole();
					}
				} else {
				//	System.out.println("what a fuck !!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
