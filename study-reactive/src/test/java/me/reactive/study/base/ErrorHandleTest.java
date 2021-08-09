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

	@DisplayName("static fallback")
	@Test
	void static_fallback() {
		final Flux<String> flux = Flux.range(1, 10)
			.map(this::doSomethingDangerous)
			.onErrorReturn("RECOVERED");

		flux.subscribe(value -> System.out.println("RECEIVED " + value));
	}

	@DisplayName("static fallback predicate")
	@Test
	void static_fallback_predicate() {
		final Flux<String> flux = Flux.range(1, 10)
			.map(this::doSomethingDangerous)
			.onErrorReturn(e -> e.getMessage().equals("doSomethingException"), "RECOVERED");

		flux.subscribe(value -> System.out.println("RECEIVED " + value));
	}

	private String doSecondTransform(String s) {
		return s;
	}

	private String doSomethingDangerous(int v) {
		if (v == 5) {
			throw new RuntimeException("doSomethingException");
		}
		return String.valueOf(v);
	}
}
