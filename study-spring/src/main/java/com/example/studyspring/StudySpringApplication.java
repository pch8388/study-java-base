package com.example.studyspring;

import java.util.concurrent.Callable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
public class StudySpringApplication {

	@RestController
	static class MyController {
		@GetMapping("/callable")
		public Callable<String> callable() {
			log.info("callable");
			return () -> {
				log.info("async");
				Thread.sleep(2000);
				return "hello";
			};
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(StudySpringApplication.class, args);
	}
}
