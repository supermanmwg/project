package example.person.talkbehavior;

import example.person.TalkBehavior;

public class Lower implements TalkBehavior {

	@Override
	public void talk() {
		System.out.println("Talk Low!");
	}

}
