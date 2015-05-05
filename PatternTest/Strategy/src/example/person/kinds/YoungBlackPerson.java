package example.person.kinds;

import example.person.Person;
import example.person.color.BlackMan;
import example.person.talkbehavior.Louder;
import example.person.walkbehavior.Fast;

public class YoungBlackPerson  extends Person{
	
	public YoungBlackPerson() {
		talkBehavior = new Louder();
		walkBehavior = new Fast();
		color = new BlackMan();
	}

}
