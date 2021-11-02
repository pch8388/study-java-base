package architecture.ex.usecase;

import architecture.ex.entity.Entities;

public interface DataAccessInterface {
	Entities find(String id);
}
