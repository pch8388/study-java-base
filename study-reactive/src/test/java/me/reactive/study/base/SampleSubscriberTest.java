package me.reactive.study.base;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

class SampleSubscriberTest {

	@Test
	void base_subscriber() {
		SampleSubscriber<Integer> subscriber = new SampleSubscriber<>();
		Flux<Integer> ints = Flux.range(1, 4);
		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error),
			() -> System.out.println("Done"),
			s -> s.request(10));

		ints.subscribe(subscriber);
	}

	@Test
	void base_subscriber_anonymous() {
		Flux.range(1, 10)
			.doOnRequest(r -> System.out.println("request of " + r))
			.subscribe(new BaseSubscriber<>() {
				@Override
				protected void hookOnSubscribe(Subscription subscription) {
					request(1);
				}

				@Override
				protected void hookOnNext(Integer value) {
					System.out.println("Cancelling after having received " + value);
					cancel();
				}
			});
	}

}