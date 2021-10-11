package com.example.studyspring.callable;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CallableController {
	@GetMapping("/callable")
	public Callable<String> callable() {
		log.info("callable");
		return () -> {
			log.info("async");
			Thread.sleep(2000);
			return "hello";
		};
	}

	@GetMapping("/block")
	public String block() throws InterruptedException {
		log.info("block");
		Thread.sleep(2000);
		return "hello";
	}
}
