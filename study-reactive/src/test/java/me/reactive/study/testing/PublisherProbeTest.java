package me.reactive.study.testing;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

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

	@Test
	void testCommandEmptyPathIsUsed() {
		PublisherProbe<Void> probe = PublisherProbe.empty();

		StepVerifier.create(processOrFallback(Mono.empty(), probe.mono()))
			.verifyComplete();

		probe.assertWasSubscribed();
		probe.assertWasRequested();
		probe.assertWasNotCancelled();
	}

	static Mono<Void> processOrFallback(Mono<String> commandSource, Mono<Void> doWhenEmpty) {
		return commandSource.flatMap(command -> executeCommand(command).then())
			.switchIfEmpty(doWhenEmpty);
	}

	private static Mono<String> executeCommand(String command) {
		return Mono.just(command + " DONE");
	}
}
