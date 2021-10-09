package me.reactive.study.base;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxScEx {
	public static void main(String[] args) {
		Flux.range(1, 10)
			.log()
			// 새로운 thread 로 실행
			.subscribeOn(Schedulers.newSingle("test"))
			.subscribe(System.out::println);

		// main thread 에서 실행되기 때문에 가장 먼저 종료된다
		System.out.println("exit");

		// 새로운 thread 를 만들어 사용하기 때문에 (thread pool 을 만듬)
		// 모든 동작이 종료되어도 thread pool 이 남아있기 때문에 바로 종료되지 않음
	}
}
