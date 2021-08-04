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

	@Test
	void generator_with_custom_class() {
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		Flux<Integer> fibonacciFlux = sequenceGenerator.generateFibonacciWithCustomClass(10);

		StepVerifier.create(fibonacciFlux)
			.expectNext(0, 1, 1, 2, 3, 5, 8)
			.expectComplete()
			.verify();
	}
}