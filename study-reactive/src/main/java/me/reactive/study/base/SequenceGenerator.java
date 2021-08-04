package me.reactive.study.base;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class SequenceGenerator {
	public Flux<Integer> generateFibonacciWithTuples() {
		return Flux.generate(
			() -> Tuples.of(0, 1),
			(state, sink) -> {
				sink.next(state.getT1());
				return Tuples.of(state.getT2(), state.getT1() + state.getT2());
			});
	}
}
