package me.reactive.study.base;

import static me.reactive.study.base.DeferEx.Repository.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeferEx {
	public static void main(String[] args) {
		Flux.range(0, 5)
			.flatMap(x -> Mono.just(x * 2))
			.switchIfEmpty(Flux.defer(DeferEx::getJust))
			.subscribe(System.out::println);

		Flux.range(0, 5)
			.flatMap(x -> Mono.just(x * 2))
			.switchIfEmpty(getJust())
			.subscribe(x -> System.out.println("print just : " + x));
	}

	private static Mono<Integer> getJust() {
		return getInt();
	}

	static class Repository {
		static Mono<Integer> getInt() {
			try {
				System.out.println("getInt In !!");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Mono.just(100);
		}
	}
}
