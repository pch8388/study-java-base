package com.example.studyspring.future.completion;

import java.util.function.Function;

import org.springframework.util.concurrent.ListenableFuture;

public class ApplyCompletion<S, T> extends Completion<S, T> {
	Function<S, ListenableFuture<T>> fn;
	public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
		this.fn = fn;
	}

	@Override
	void run(S value) {
		fn.apply(value)
			.addCallback(this::complete, this::error);
	}
}
