package me.reactive.study.base;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SubscribeTests {

	@Test
	void subscribe() {
		Flux<Integer> ints = Flux.range(1, 3);
		ints.subscribe();

		ints.subscribe(System.out::println);
	}

	@Test
	void subscribe_error() {
		Flux<Integer> ints = Flux.range(1, 4)
			.map(i -> {
				if (i <= 3) return i;
				throw new RuntimeException("Got to 4");
			});

		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error));
	}

	@Test
	void subscribe_complete() {
		Flux<Integer> ints = Flux.range(1, 4);

		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error),
			() -> System.out.println("Done"));

		System.out.println("======================");

		ints.subscribe(System.out::println,
			error -> System.err.println("Error : " + error),
			() -> System.out.println("Done"),
			sub -> sub.request(1));
	}

	@Test
	void buffer() {
		Flux.range(1, 20)
			.buffer(5)
			.subscribe(i -> System.out.println("Received : " + i));

		System.out.println("=============================");

		final Flux<Integer> elements1 = Flux.range(1, 10);
		final Flux<Integer> elements2 = Flux.range(101, 10);

		Flux.merge(elements1, elements2)
			.buffer(5)
			.subscribe(i -> System.out.println("Received2 : " + i));


		System.out.println("=============================");

		Flux.range(1, 10)
			.buffer(3, 2)  // 첫번째 파라미터 개수만큼 수집하고, 두번째 파라미터 개수만큼 스킵
			.subscribe(i -> System.out.println("Received3 : " + i));
	}

	@Test
	void thread() throws InterruptedException {
		System.out.println("main thread " + Thread.currentThread().getName());
		Mono<String> mono = Mono.just("hello ");

		Thread t = new Thread(() -> mono.map(msg -> msg + "thread ")
			.subscribe(v ->
				System.out.println(v + Thread.currentThread().getName())));

		t.start();
		t.join();
	}
}
