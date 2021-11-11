package com.example.studywebflux.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MyController {


	public static final String URL1 = "http://localhost:8083/service?req={req}";
	public static final String URL2 = "http://localhost:8083/service2?req={req}";
	final MyService myService;
	WebClient client = WebClient.create();

	// spring 에서 Mono/Flux 타입에 대한 subscribe 를 처리해준다
	@GetMapping("/rest")
	public Mono<String> rest(int idx) {
		return client.get().uri(URL1, idx)
			.exchangeToMono(r -> r.bodyToMono(String.class))
			.doOnNext(log::info)
			.flatMap(res -> client.get().uri(URL2, res)
				.exchangeToMono(r -> r.bodyToMono(String.class)))
			.doOnNext(log::info)
			.flatMap(r -> Mono.fromCompletionStage(myService.work(r)))
			.doOnNext(log::info);
	}
}
