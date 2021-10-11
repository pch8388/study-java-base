package com.example.studyspring.deffered;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DeferredResultController {
	Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

	@GetMapping("/dr")
	public DeferredResult<String> deferredResult() {
		log.info("dr");
		DeferredResult<String> dr = new DeferredResult<>(600000L);
		results.add(dr);
		return dr;
	}

	@GetMapping("/dr/count")
	public String drCount() {
		return String.valueOf(results.size());
	}

	@GetMapping("/dr/event")
	public String drEvent(String msg) {
		for (DeferredResult<String> dr : results) {
			dr.setResult("Hello " + msg);
			results.remove(dr);
		}

		return "OK";
	}
}
