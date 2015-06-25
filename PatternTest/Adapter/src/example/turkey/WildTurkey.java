package example.turkey;

public class WildTurkey implements Turkey {

	@Override
	public void gobble() {
		System.out.println("wild turkey is gobbling");
	}

	@Override
	public void fly() {
		System.out.println("wild turkey is flying...");
	}

}
