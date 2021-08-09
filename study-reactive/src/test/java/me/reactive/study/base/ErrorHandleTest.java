package me.reactive.study.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class ErrorHandleTest {

	@DisplayName("subscribe onError 콜백")
	@Test
	void subscribe_exception_handle() {
		Flux<String> s = Flux.range(1, 10)
			.map(this::doSomethingDangerous)
			.map(this::doSecondTransform);

		s.subscribe(value -> System.out.println("RECEIVED " + value),
			error -> System.err.println("CAUGHT " + error) // error handle
		);
	}

	private String doSecondTransform(Integer v) {
		return String.valueOf(v);
	}

	private int doSomethingDangerous(int v) {
		if (v == 5) {
			throw new RuntimeException();
		}
		return v;
	}
}
