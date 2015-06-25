package example.demo;

import example.components.Amplifier;
import example.components.DvdPlayer;
import example.components.Projector;
import example.components.Screen;
import example.components.TheaterLights;
import example.facade.HomeTheaterFacade;

public class DemoTest {

	
	public  static void main(String[] args) {
		Amplifier amp = new Amplifier();
		DvdPlayer dvd = new DvdPlayer();
		Projector projector = new Projector();
		Screen screen = new Screen();
		TheaterLights theaterLights = new TheaterLights();
		HomeTheaterFacade homeTheater =new HomeTheaterFacade(amp, dvd, projector, screen, theaterLights);
		homeTheater.watchMovie("Haha");
		homeTheater.endMovie();
	}
}
