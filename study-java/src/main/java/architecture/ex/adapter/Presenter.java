package architecture.ex.adapter;

import architecture.ex.usecase.OutputBoundary;

public class Presenter {
	public ViewModel convert(OutputBoundary outputBoundary) {
		return new ViewModel(outputBoundary);
	}
}
