package example.person;

public abstract class Person {
	
	private String name;
	protected TalkBehavior talkBehavior;
	protected WalkBehavior walkBehavior;
	protected Color color;
	
	public void talk() {
		talkBehavior.talk();
	}
	
	public void walk() {
		walkBehavior.walk();
	}
	
	public void getColor() {
		color.getColor();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
