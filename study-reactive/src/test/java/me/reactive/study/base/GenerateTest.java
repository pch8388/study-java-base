package me.reactive.study.base;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class GenerateTest {

	@DisplayName("평범한 사용")
	@Test
	void generate() {
		Flux<String> generateFlux = Flux.generate(
			() -> 1,
			(state, sink) -> {
				sink.next("3 x " + state + " = " + 3 * state);
				if (state == 9) sink.complete();
				return state + 1;
			});

		generateFlux.subscribe(System.out::println);
	}

	@DisplayName("state 가 변할 경우")
	@Test
	void generate_mutable_state() {
		Flux<String> mutableStateFlux = Flux.generate(
			AtomicLong::new,
			(state, sink) -> {
				long i = state.getAndIncrement();
				sink.next("5 x " + i + " = " + 5 * i);
				if (i == 10) sink.complete();
				return state;
			});

		mutableStateFlux.subscribe(System.out::println);
	}

	@DisplayName("Consumer 사용")
	@Test
	void generate_with_consumer() {
		Flux<String> generateConsumer = Flux.generate(
			AtomicLong::new,
			(state, sink) -> {
				long i = state.getAndIncrement();
				sink.next("6 x " + i + " = " + 6 * i);
				if (i == 10)
					sink.complete();
				return state;
			}, (state) -> System.out.println("state : " + state));

		// Consumer 가 상태 값의 처리가 끝난 후의 처리를 담당한다
		generateConsumer.subscribe(System.out::println);
	}
}
