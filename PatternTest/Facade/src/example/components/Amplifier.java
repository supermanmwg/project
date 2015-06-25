package example.components;

public class Amplifier implements Toggle {

	@Override
	public void on() {
		System.out.println("Amplifier is on");
	}

	@Override
	public void off() {
		System.out.println("Amplifier is off");
	}
	
	public void setDvd(){
		System.out.println("Amplifier  set DVD");
	}
	
	public void setSurroundSound() {
		System.out.println("Amplifier set surrounding sound");
	}
	
	public void setVolume(int volume){
		System.out.println("Amplifier set volume is " + volume);
	}

}
