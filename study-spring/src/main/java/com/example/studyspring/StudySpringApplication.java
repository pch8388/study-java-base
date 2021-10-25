package com.example.studyspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StudySpringApplication {

	@RestController
	public static class MyController {
		AsyncRestTemplate rt = new AsyncRestTemplate();

		@Autowired MyService myService;

		@GetMapping("/rest")
		public DeferredResult<String> rest(int idx) {
			// 언젠가 값을 반환해줌
			DeferredResult<String> dr = new DeferredResult<>();

			ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(
				"http://localhost:8083/service?req={req}", String.class,
				"hello" + idx);


			// 예외를 전파해봤자 정확하게 스프링 핸들러에서 받을지 모름 => 어떤 thread 에서 실행중인지 알 수 없기 때문
			f1.addCallback(s -> {
					ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(
						"http://localhost:8083/service2?req={req}", String.class, s.getBody());

					f2.addCallback(s2 -> {
							ListenableFuture<String> f3 = myService.work(s2.getBody());
							f3.addCallback(dr::setResult,
								ex -> dr.setErrorResult(ex.getMessage()));
						},
						e -> dr.setErrorResult(e.getMessage()));
				},
				e -> dr.setErrorResult(e.getMessage()));

			return dr;
		}
	}

	@Service
	public static class MyService {
		@Async
		public ListenableFuture<String> work(String req) {
			return new AsyncResult<>(req + "/asyncwork");
		}
	}

	// 하나의 thread 만 사용하도록 하기 위해 하나짜리 pool 을 만듬
	@Bean
	public ThreadPoolTaskExecutor myThreadPool() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(1);
		te.setMaxPoolSize(1);
		te.initialize();
		return te;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudySpringApplication.class, args);
	}
}
