package architecture.ex.entity;

public class Entities {
	private final String id;

	public Entities(String id) {
		this.id = id;
	}

	public String run() {
		return "run logic " + this.id;
	}
}
