package com.example.studyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
public class StudySpringApplication {

	@RestController
	public static class MyController {
		RestTemplate rt = new RestTemplate();

		@GetMapping("/rest")
		public String rest(int idx) {
			final String res = rt.getForObject("http://localhost:8083/service?req={req}", String.class,
				"hello" + idx);

			return "rest " + idx;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(StudySpringApplication.class, args);
	}
}
