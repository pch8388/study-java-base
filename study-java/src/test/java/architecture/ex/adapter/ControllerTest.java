package architecture.ex.adapter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import architecture.ex.entity.Entities;
import architecture.ex.usecase.DataAccessInterface;
import architecture.ex.usecase.UseCaseInteractor;

class ControllerTest {

	@Test
	void 클린_아키텍처_예제_실습() {
		final String id = "test";

		Controller controller = new Controller(new UseCaseInteractor(new MockDataAccess()), new Presenter());
		final ViewModel viewModel = controller.find(id);
		assertEquals("run logic " + id, viewModel.getResult());
	}

	static class MockDataAccess implements DataAccessInterface {
		@Override
		public Entities find(String id) {
			return new Entities(id);
		}
	}

}