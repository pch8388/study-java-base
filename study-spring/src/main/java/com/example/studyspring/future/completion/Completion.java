package com.example.studyspring.future.completion;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.util.concurrent.ListenableFuture;

public class Completion<S, T> {
	Completion next;

	public Completion() {}

	public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
		Completion<T, V> c = new ApplyCompletion<>(fn);
		this.next = c;
		return c;
	}

	public Completion<T, T> andError(Consumer<Throwable> econ) {
		Completion<T, T> c = new ErrorCompletion<>(econ);
		this.next = c;
		return c;
	}

	public void andAccept(Consumer<T> con) {
		Completion<T, Void> c = new AcceptCompletion<>(con);
		this.next = c;
	}
	public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
		Completion<S, T> c = new Completion<>();
		lf.addCallback(c::complete, c::error);
		return c;
	}

	void error(Throwable e) {
		if (next != null) next.error(e);
	}

	void complete(T s) {
		if (next != null) next.run(s);
	}

	void run(S value) {}
}
