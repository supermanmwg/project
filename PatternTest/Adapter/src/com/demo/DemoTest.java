package com.demo;

import example.adapter.TurkeyAdapter;
import example.duck.Duck;
import example.turkey.Turkey;
import example.turkey.WildTurkey;

public class DemoTest {

	public static void main(String[] args) {
		
		Turkey wildTurkey = new WildTurkey();
		Duck mTurkeyAdapter = new TurkeyAdapter(wildTurkey);
		testDuck(mTurkeyAdapter);
	}

	private static void testDuck(Duck duck) {
		duck.fly();
		duck.quack();
	}

}
