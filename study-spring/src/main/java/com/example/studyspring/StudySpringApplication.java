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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

	@Bean
	ThreadPoolTaskExecutor tp() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		// 초기 풀 사이즈
		te.setCorePoolSize(10);
		// 쓰레드마다 할당되는 큐에 수용할 수 있는 최대 요청
		te.setQueueCapacity(200);
		// 초기 풀사이즈에 있는 큐가 모두 가득 차면 쓰레드수를 최대 수만큼 늘림
		te.setMaxPoolSize(100);
		te.setThreadNamePrefix("my-thread");
		te.initialize();

		return te;
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
				s -> log.info("result : " + s),
				e -> log.info("error : " + e.getMessage()));
			log.info("exit");
		};
	}
}
