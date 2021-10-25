package com.example.studyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
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

		@GetMapping("/rest")
		public DeferredResult<String> rest(int idx) {
			// 언젠가 값을 반환해줌
			DeferredResult<String> dr = new DeferredResult<>();

			ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(
				"http://localhost:8083/service?req={req}", String.class,
				"hello" + idx);

			// 예외를 전파해봤자 정확하게 스프링 핸들러에서 받을지 모름 => 어떤 thread 에서 실행중인지 알 수 없기 때문
			f1.addCallback(s -> dr.setResult(s.getBody() + "/work"),
				e -> dr.setErrorResult(e.getMessage()));

			return dr;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(StudySpringApplication.class, args);
	}
}
