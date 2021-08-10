package me.reactive.study.base;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

public class ErrorHandleTest {

	@DisplayName("subscribe onError 콜백")
	@Test
	void subscribe_exception_handle() {
		Flux<String> s = Flux.range(1, 10)
			.map(this::doSomethingDangerous)
			.map(this::doSecondTransform);

		s.subscribe(value -> System.out.println("RECEIVED " + value),
			error -> System.err.println("CAUGHT " + error) // error handle
		);
	}

	@DisplayName("static fallback")
	@Test
	void static_fallback() {
		final Flux<String> flux = Flux.range(1, 10)
			.map(this::doSomethingDangerous)
			.onErrorReturn("RECOVERED");

		flux.subscribe(value -> System.out.println("RECEIVED " + value));
	}

	@DisplayName("static fallback predicate")
	@Test
	void static_fallback_predicate() {
		final Flux<String> flux = Flux.range(1, 10)
			.map(this::doSomethingDangerous)
			.onErrorReturn(e -> e.getMessage().equals("doSomethingException"), "RECOVERED");

		flux.subscribe(value -> System.out.println("RECEIVED " + value));
	}

	@Test
	void using() {
		AtomicBoolean isDisposed = new AtomicBoolean();
		Disposable disposableInstance = new Disposable() {
			@Override
			public void dispose() {
				isDisposed.set(true);
			}

			@Override
			public String toString() {
				return "DISPOSABLE";
			}
		};

		Flux<String> flux =
			Flux.using(
				() -> disposableInstance,
				disposable -> Flux.just(disposable.toString()),
				Disposable::dispose
			);

		flux.subscribe(System.out::println);
	}

	@Test
	void interval() throws InterruptedException {
		final Flux<String> flux = Flux.interval(Duration.ofMillis(250))
			.map(input -> {
				if (input < 3)
					return "tick " + input;
				throw new RuntimeException("boom");
			})
			.onErrorReturn("Uh oh");

		flux.subscribe(System.out::println);
		Thread.sleep(2100);
	}

	@Test
	void retry() throws InterruptedException {
		Flux.interval(Duration.ofMillis(250))
			.map(input -> {
				if (input < 3)
					return "tick " + input;
				throw new RuntimeException("boom");
			})
			.retry(1)
			.elapsed() // 이전 데이터 방출 후 소요시간 + 현 데이터 방출 소요시간을 묶어서 보여주기 위함
			.subscribe(System.out::println, System.err::println);

		Thread.sleep(2100);
	}

	@Test
	void unchecked_exception_onError() {
		Flux.just("foo")
			.map(s -> { throw new IllegalArgumentException(s); })
			.subscribe(v -> System.out.println("got value"),
				e -> System.err.println("ERROR : " + e));
	}

	@Test
	void checked_exception() {
		final Flux<String> flux = Flux.range(1, 10)
			.map(i -> {
				try {
					return convert(i);
				} catch (IOException e) {
					throw Exceptions.propagate(e);
				}
			});

		flux.subscribe(
			v -> System.out.println("RECEIVED : " + v),
			e -> {
				if (Exceptions.unwrap(e) instanceof IOException) {
					System.out.println("Something bad happened with I/O");
				} else {
					System.out.println("Something bad happened");
				}
			}
		);
	}

	private String convert(int i) throws IOException {
		if (i > 3) {
			throw new IOException("boom " + i);
		}
		return "OK " + i;
	}

	private String doSecondTransform(String s) {
		return s;
	}

	private String doSomethingDangerous(int v) {
		if (v == 5) {
			throw new RuntimeException("doSomethingException");
		}
		return String.valueOf(v);
	}
}
