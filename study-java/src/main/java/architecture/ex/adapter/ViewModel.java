package architecture.ex.adapter;

import architecture.ex.usecase.OutputBoundary;

public class ViewModel {
	private final OutputBoundary outputBoundary;

	public ViewModel(OutputBoundary outputBoundary) {
		this.outputBoundary = outputBoundary;
	}

	public String getResult() {
		return outputBoundary.output();
	}
}
