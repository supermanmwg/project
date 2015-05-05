package example.person.walkbehavior;

import example.person.WalkBehavior;

public class Fast implements WalkBehavior {

	@Override
	public void walk() {
		System.out.println("Walk Fast!");
	}

}
