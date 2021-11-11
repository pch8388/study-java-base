package com.example.studywebflux.monostudy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class MonoController {

	@GetMapping("/")
	Mono<String> hello() {
		return Mono.just("Hello WebFlux").log();
	}

}
