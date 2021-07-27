package me.reactive.study.base;

import org.junit.jupiter.api.Test;

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

}