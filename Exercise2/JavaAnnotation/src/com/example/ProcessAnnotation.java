package com.example;

import java.lang.reflect.Method;

public class ProcessAnnotation {
	
	public static void  processAnnotation(Object o) {
		try {
			Class c = o.getClass();
			System.out.println("Class name is " + c.getSimpleName());
			System.out.println("process annotation start ...");
			for(Method field : c.getDeclaredMethods()){

				field.setAccessible(true);
				Secured role = field.getAnnotation(Secured.class);

				if(null != role) {
					System.out.println("field name is " + field.getName());
					TestMethod t = (TestMethod) o;
					if("user".equals(role.role())) {
						t.hasRole();
					} else {
						t.hasNotRole();
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
