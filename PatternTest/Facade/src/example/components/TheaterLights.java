package example.components;

public class TheaterLights implements Toggle{

	@Override
	public void on() {
		System.out.println(getClass().getSimpleName() + "is on");
	}

	@Override
	public void off() {
		System.out.println(getClass().getSimpleName() + "is off");
		
	}

}