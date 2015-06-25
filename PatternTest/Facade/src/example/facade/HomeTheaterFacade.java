package example.facade;

import example.components.*;

public class HomeTheaterFacade {
	Amplifier amp;
	DvdPlayer dvd;
	Projector projector;
	Screen screen;
	TheaterLights theaterLights;

	public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd, Projector projector,
			Screen screen, TheaterLights theaterLights) {
		this.amp = amp;
		this.dvd = dvd;
		this.projector = projector;
		this.screen = screen;
		this.theaterLights = theaterLights;
	}
	
	public void watchMovie(String name) {
		System.out.println("Watch " + name + " is beginning");
		amp.on();
		dvd.on();
		projector.on();
		screen.on();
		theaterLights.on();
		System.out.println("Watch " + name + " is ready");
	}
	
	public void endMovie() {
		System.out.println("End movie is beginning");
		amp.off();
		dvd.off();
		projector.off();
		screen.off();
		theaterLights.off();
		System.out.println("End movie is ok");
	}

}
