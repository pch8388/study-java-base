package architecture.ex.usecase;

import architecture.ex.entity.Entities;

public class UseCaseInteractor {
	private final DataAccessInterface dataAccessInterface;

	public UseCaseInteractor(DataAccessInterface dataAccessInterface) {
		this.dataAccessInterface = dataAccessInterface;
	}

	public OutputBoundary find(InputBoundary inputBoundary) {
		final Entities entities = dataAccessInterface.find(inputBoundary.getId());
		return new OutputData(entities.run());
	}
}
