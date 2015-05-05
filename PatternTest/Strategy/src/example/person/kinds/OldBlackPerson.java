package example.person.kinds;

import example.person.Person;
import example.person.color.BlackMan;
import example.person.talkbehavior.Lower;
import example.person.walkbehavior.Slow;

public class OldBlackPerson extends Person{

	public OldBlackPerson() {
		talkBehavior = new Lower();
		walkBehavior = new Slow();
		color = new BlackMan();
	}
}
