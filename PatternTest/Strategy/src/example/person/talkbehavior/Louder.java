package example.person.talkbehavior;

import example.person.TalkBehavior;

public class Louder implements TalkBehavior{

	@Override
	public void talk() {
		System.out.println("Talk louder!");
	}

}
