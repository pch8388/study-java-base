package com.example.studyspring.future.completion;

import java.util.function.Consumer;

public class ErrorCompletion<T> extends Completion<T, T> {
	Consumer<Throwable> econ;
	public ErrorCompletion(Consumer<Throwable> econ) {
		this.econ = econ;
	}

	@Override
	void error(Throwable e) {
		econ.accept(e);
	}

	@Override
	void run(T value) {
		if (next != null) next.run(value);
	}
}
