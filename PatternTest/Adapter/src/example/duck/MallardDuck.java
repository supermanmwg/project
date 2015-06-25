package example.duck;

public class MallardDuck implements Duck {

	@Override
	public void quack() {
		System.out.println("mallark quacks");
	}

	@Override
	public void fly() {
		System.out.println("mallard is flying...");
	}

}
