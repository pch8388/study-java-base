package architecture.ex.adapter;

import architecture.ex.usecase.InputBoundary;
import architecture.ex.usecase.InputData;
import architecture.ex.usecase.OutputBoundary;
import architecture.ex.usecase.UseCaseInteractor;

public class Controller {

	private final UseCaseInteractor interactor;
	private final Presenter presenter;

	public Controller(UseCaseInteractor interactor, Presenter presenter) {
		this.interactor = interactor;
		this.presenter = presenter;
	}

	public ViewModel find(String id) {
		final InputBoundary inputBoundary = new InputData(id);
		final OutputBoundary outputBoundary = interactor.find(inputBoundary);
		return presenter.convert(outputBoundary);
	}
}
