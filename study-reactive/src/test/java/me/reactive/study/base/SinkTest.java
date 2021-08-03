package me.reactive.study.base;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class SinkTest {

	@Test
	void generate() {
		Flux<String> generateFlux = Flux.generate(
			() -> 1,
			(state, sink) -> {
				sink.next("3 x " + state + " = " + 3 * state);
				if (state == 9)
					sink.complete();
				return state + 1;
			});

		generateFlux.subscribe(System.out::println);
	}
}
