package com.factory.example;

import com.factory.example.pizza.Pizza;
import com.factory.example.store.NYStylePizzaStore;
import com.factory.example.store.PizzaStore;

public class PizzaTestDrive {
	
	public static  void main(String[] args) {
		PizzaStore nyStore = new NYStylePizzaStore();
	
		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println("MWG ordered a " + pizza.getName() + "\n");
	}
}
