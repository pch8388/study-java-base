package com.example.studyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StudySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudySpringApplication.class, args);
	}
}
