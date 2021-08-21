package me.reactive.study.testing;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Append {

	public static <T> Flux<T> appendBoomError(Flux<T> source) {
		return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
	}
}
