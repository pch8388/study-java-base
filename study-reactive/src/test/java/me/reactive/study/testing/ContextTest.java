package me.reactive.study.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class ContextTest {

	@Test
	void context() {
		StepVerifier.create(Mono.just(1).map(i -> i + 10),
				StepVerifierOptions.create().withInitialContext(Context.of("thing1", "thing2")))
			.expectAccessibleContext()
			.contains("thing1", "thing2")
			.assertThat(context -> assertEquals(context.get("thing1"), "thing2"))
			.then()
			.expectNext(11)
			.verifyComplete();
	}
}
