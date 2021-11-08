package com.example.studyspring.future;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteService {
	@RestController
	public static class MyController {
		@GetMapping("/service")
		public String rest(String req) throws InterruptedException {
			Thread.sleep(1000);
			// throw new RuntimeException();
			return req + "/service";
		}

		@GetMapping("/service2")
		public String service2(String req) throws InterruptedException {
			Thread.sleep(1000);
			return req + "/service2";
		}
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RemoteService.class);
		app.setDefaultProperties(Map.of("server.tomcat.threads.max", "1000", "server.port", "8083"));
		app.run(args);
	}
}
