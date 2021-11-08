package com.example.studyspring.future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class MyService {
	@Async
	public ListenableFuture<String> work(String req) {
		return new AsyncResult<>(req + "/asyncwork");
	}

	public String workCF(String req) {
		return req + "/asyncwork";
	}

}
