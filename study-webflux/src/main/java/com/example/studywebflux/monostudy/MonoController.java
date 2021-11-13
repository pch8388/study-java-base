package com.example.studywebflux.monostudy;

import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

	@GetMapping("/event/{id}")
	Mono<Event> event(@PathVariable long id) {
		return Mono.just(new Event(id, "event"+id));
	}

	// text/event-stream 을 이용하려면 Flux 로 return 해야 함
	// text/event-stream : 데이터를 stream 형태로 나눠서 보냄
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Event> events() {
		return Flux
			.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), "value")))
			.take(10);
	}

	@Data @AllArgsConstructor
	public static class Event {
		long id;
		String value;
	}

}
