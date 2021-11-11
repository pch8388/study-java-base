package com.example.studywebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAsync
public class StudyWebfluxApplication {

	public static void main(String[] args) {
		System.setProperty("reactor.ipc.netty.workerCount", "1");
		System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
		SpringApplication.run(StudyWebfluxApplication.class, args);
	}

}
