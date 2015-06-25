package example.adapter;

import example.duck.Duck;
import example.turkey.Turkey;

public class TurkeyAdapter implements Duck{

	private Turkey turkey;
	
	public TurkeyAdapter(){
		
	}
	
	public TurkeyAdapter(Turkey t) {
		this.turkey = t;
	}
	
	@Override
	public void quack() {
		turkey.gobble();
	}

	@Override
	public void fly() {
		turkey.fly();
	}

}
