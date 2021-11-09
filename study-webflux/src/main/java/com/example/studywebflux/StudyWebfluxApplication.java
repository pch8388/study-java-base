package com.example.studywebflux;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
@EnableAsync
public class StudyWebfluxApplication {

	public static final String URL1 = "http://localhost:8083/service?req={req}";
	public static final String URL2 = "http://localhost:8083/service2?req={req}";

	@RestController
	public static class MyController {

		@Autowired MyService myService;
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

	@Service
	public static class MyService {
		@Async
		public CompletableFuture<String> work(String req) {
			return CompletableFuture.completedFuture(req + "/asyncwork");
		}
	}

	public static void main(String[] args) {
		System.setProperty("reactor.ipc.netty.workerCount", "1");
		System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
		SpringApplication.run(StudyWebfluxApplication.class, args);
	}

}
