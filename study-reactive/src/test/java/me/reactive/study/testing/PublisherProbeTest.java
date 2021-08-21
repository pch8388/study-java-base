package me.reactive.study.testing;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PublisherProbeTest {

	@Test
	void notEmpty() {
		StepVerifier.create(processOrFallback(Mono.just("just a phrase with		tabs!"), Mono.just("EMPTY_PHRASE")))
			.expectNext("just", "a", "phrase", "with", "tabs!")
			.verifyComplete();
	}

	@Test
	void switchIfEmpty() {
		StepVerifier.create(processOrFallback(Mono.empty(), Mono.just("EMPTY_PHRASE")))
			.expectNext("EMPTY_PHRASE")
			.verifyComplete();
	}

	static Flux<String> processOrFallback(Mono<String> source, Publisher<String> fallback) {
		return source.flatMapMany(phrase -> Flux.fromArray(phrase.split("\\s+")))
			.switchIfEmpty(fallback);
	}
}
