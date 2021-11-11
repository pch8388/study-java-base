package com.example.studywebflux.monostudy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MonoController {

	@GetMapping("/")
	Mono<String> hello() {
		log.info("pos1");
		Mono<String> m = Mono.fromSupplier(this::generateHello).doOnNext(log::info).log();
		log.info("pos2");
		return m;
	}

	private String generateHello() {
		log.info("method generateHello()");
		return "Hello Mono";
	}

}
