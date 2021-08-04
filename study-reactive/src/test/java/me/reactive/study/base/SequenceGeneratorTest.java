package me.reactive.study.base;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SequenceGeneratorTest {

	@Test
	void generator() {
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		Flux<Integer> fibonacciFlux = sequenceGenerator.generateFibonacciWithTuples().take(5);

		StepVerifier.create(fibonacciFlux)
			.expectNext(0, 1, 1, 2, 3)
			.expectComplete()
			.verify();
	}
}