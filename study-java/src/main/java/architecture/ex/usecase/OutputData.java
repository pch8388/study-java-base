package architecture.ex.usecase;

public class OutputData implements OutputBoundary {
	private final String result;

	public OutputData(String result) {
		this.result = result;
	}

	@Override
	public String output() {
		return result;
	}
}
