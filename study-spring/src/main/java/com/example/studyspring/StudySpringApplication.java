package com.example.studyspring;

import java.util.function.Consumer;

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
		public static final String URL1 = "http://localhost:8083/service?req={req}";
		public static final String URL2 = "http://localhost:8083/service2?req={req}";
		AsyncRestTemplate rt = new AsyncRestTemplate();

		@Autowired MyService myService;

		@GetMapping("/rest")
		public DeferredResult<String> rest(int idx) {
			// 언젠가 값을 반환해줌
			DeferredResult<String> dr = new DeferredResult<>();

			Completion
				.from(rt.getForEntity(URL1, String.class, "hello" + idx))
				.andAccept(s -> dr.setResult(s.getBody()));

			return dr;
		}
	}

	public static class Completion {
		Completion next;
		Consumer<ResponseEntity<String>> con;

		public Completion() {}

		public Completion(Consumer<ResponseEntity<String>> con) {
			this.con = con;
		}

		public void andAccept(Consumer<ResponseEntity<String>> con) {
			Completion c = new Completion(con);
			this.next = c;
		}
		public static Completion from(ListenableFuture<ResponseEntity<String>> lf) {
			Completion c = new Completion();
			lf.addCallback(s -> {
				c.complete(s);
			}, e -> {
				c.error(e);
			});
			return c;
		}

		private void error(Throwable e) {

		}

		private void complete(ResponseEntity<String> s) {
			if (next != null) next.run(s);
		}

		private void run(ResponseEntity<String> value) {
			if (con != null) con.accept(value);
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
