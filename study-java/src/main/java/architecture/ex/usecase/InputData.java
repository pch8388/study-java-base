package architecture.ex.usecase;

public class InputData implements InputBoundary {
	private final String id;

	public InputData(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
