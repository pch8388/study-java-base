package com.example.studywebflux.base;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	@Async
	public CompletableFuture<String> work(String req) {
		return CompletableFuture.completedFuture(req + "/asyncwork");
	}
}
