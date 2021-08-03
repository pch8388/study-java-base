package me.reactive.study.base;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class SinkTest {

	@Test
	void generate() {
		Flux<String> generateFlux = Flux.generate(
			() -> 1,
			(state, sink) -> {
				sink.next("3 x " + state + " = " + 3 * state);
				if (state == 9) sink.complete();
				return state + 1;
			});

		generateFlux.subscribe(System.out::println);

		Flux<String> longFlux = Flux.generate(
			AtomicLong::new,
			(state, sink) -> {
				long i = state.getAndIncrement();
				sink.next("5 x " + i + " = " + 5 * i);
				if (i == 10) sink.complete();
				return state;
			});

		longFlux.subscribe(System.out::println);
	}
}
