package me.reactive.study.base;

import java.time.Duration;

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

	@Test
	void buffer() {
		Flux.range(1, 20)
			.buffer(5)
			.subscribe(i -> System.out.println("Received : " + i));

		System.out.println("=============================");

		final Flux<Integer> elements1 = Flux.range(1, 10);
		final Flux<Integer> elements2 = Flux.range(101, 10);

		Flux.merge(elements1, elements2)
			.buffer(5)
			.subscribe(i -> System.out.println("Received2 : " + i));


		System.out.println("=============================");

		Flux.range(1, 10)
			.buffer(3, 2)
			.subscribe(i -> System.out.println("Received3 : " + i));
	}
}
