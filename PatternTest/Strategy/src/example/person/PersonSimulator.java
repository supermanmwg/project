package example.person;

import example.person.kinds.OldBlackPerson;
import example.person.kinds.YoungBlackPerson;

public class PersonSimulator {

	public static void main(String[] args) {
		Person youngBlack = new YoungBlackPerson();
		Person oldBlack = new OldBlackPerson();
		
		youngBlack.getColor();
		youngBlack.talk();
		youngBlack.walk();
		oldBlack.getColor();
		oldBlack.talk();
		oldBlack.walk();
	}
}
