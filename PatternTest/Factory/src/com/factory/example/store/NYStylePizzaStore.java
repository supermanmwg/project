package com.factory.example.store;

import com.factory.example.pizza.NYStyleCheesePizza;
import com.factory.example.pizza.NyStyleVeggiePizza;
import com.factory.example.pizza.NytyleClamPizza;
import com.factory.example.pizza.Pizza;

public class NYStylePizzaStore extends PizzaStore {

	@Override
	Pizza createPizza(String type) {
		
		if(type.equals("cheese")) {
			return new NYStyleCheesePizza();
		} else if(type.equals("veggie")) {
			return new NyStyleVeggiePizza();
		} else if(type.equals("clam")) {
			return new NytyleClamPizza();
		}
		else {
			return null;
		}
	}
}
