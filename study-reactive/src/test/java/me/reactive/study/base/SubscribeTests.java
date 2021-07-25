package me.reactive.study.base;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class SubscribeTests {

	@Test
	void subscribe() {
		Flux<Integer> ints = Flux.range(1, 3);
		ints.subscribe();

		ints.subscribe(System.out::println);
	}

	@Test
	void subscribe_error() {
		Flux<Integer> ints = Flux.range(1, 4)
			.map(i -> {
				if (i <= 3) return i;
				throw new RuntimeException("Got to 4");
			});

		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error));
	}

	@Test
	void subscribe_complete() {
		Flux<Integer> ints = Flux.range(1, 4);

		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error),
			() -> System.out.println("Done"));

		System.out.println("======================");

		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error),
			() -> System.out.println("Done"),
			sub -> sub.request(1));
	}
}
