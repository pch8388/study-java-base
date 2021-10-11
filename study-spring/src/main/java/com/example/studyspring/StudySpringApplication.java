package com.example.studyspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
public class StudySpringApplication {

	@Component
	static class MyService {
		@Async
		public ListenableFuture<String> hello()  {
			log.info("hello()");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				log.info(e.getMessage());
			}
			return new AsyncResult<>( "Hello");
		}
	}

	public static void main(String[] args) {
		try (ConfigurableApplicationContext c = SpringApplication.run(StudySpringApplication.class, args)) {
		}
	}

	@Autowired MyService myService;

	@Bean
	ApplicationRunner run() {
		return args -> {
			log.info("run()");
			ListenableFuture<String> res = myService.hello();
			res.addCallback(
				s -> System.out.println("result : " + s),
				e -> System.out.println("error : " + e.getMessage()));
			log.info("exit");
		};
	}
}
