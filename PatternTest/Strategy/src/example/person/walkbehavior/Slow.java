package example.person.walkbehavior;

import example.person.WalkBehavior;

public class Slow implements WalkBehavior{

	@Override
	public void walk() {
		System.out.println("Walk Slow!");		
	}

}
