package com.example.studyspring.future.completion;

import java.util.function.Consumer;

public class AcceptCompletion<S> extends Completion<S, Void> {
	Consumer<S> con;
	public AcceptCompletion(Consumer<S> con) {
		this.con = con;
	}

	@Override
	void run(S value) {
		con.accept(value);
	}
}
