package me.reactive.study.testing;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class VirtualTimeTest {

	@Test
	void virtualTime() {
		StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
			.expectSubscription()
			.expectNoEvent(Duration.ofDays(1))
			.expectNext(0L)
			.verifyComplete();
	}

	@Test
	void thenAwait() {
		StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
			.thenAwait(Duration.ofDays(1))
			.expectNext(0L)
			.verifyComplete();
	}
}
