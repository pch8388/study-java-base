package me.reactive.study.testing;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class AppendTest {

	@Test
	public void testAppendBoomError() {
		Flux<String> source = Flux.just("thing1", "thing2");

		StepVerifier.create(
				Append.appendBoomError(source))
			.expectNext("thing1")
			.expectNext("thing2")
			.expectErrorMessage("boom")
			.verify();
	}
}