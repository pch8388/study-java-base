package com.example.studyspring.future;

import java.util.concurrent.CompletableFuture;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.example.studyspring.future.completion.Completion;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MyController {
	public static final String URL1 = "http://localhost:8083/service?req={req}";
	public static final String URL2 = "http://localhost:8083/service2?req={req}";
	AsyncRestTemplate rt = new AsyncRestTemplate();

	private final MyService myService;

	@GetMapping("/rest")
	public DeferredResult<String> rest(int idx) {
		// 언젠가 값을 반환해줌
		DeferredResult<String> dr = new DeferredResult<>();

		Completion
			.from(rt.getForEntity(URL1, String.class, "hello" + idx))
			.andApply(s -> rt.getForEntity(URL2, String.class, s.getBody()))
			.andApply(s -> myService.work(s.getBody()))
			.andError(e -> dr.setErrorResult(e.toString()))
			.andAccept(dr::setResult);

		return dr;
	}

	@GetMapping("/restCF")
	public DeferredResult<String> restCF(int idx) {
		DeferredResult<String> dr = new DeferredResult<>();

		toCF(rt.getForEntity(URL1, String.class, "h" + idx))
			.thenCompose(s -> toCF(rt.getForEntity(URL2, String.class, s.getBody())))
			.thenApplyAsync(s -> myService.workCF(s.getBody()))
			.thenAccept(dr::setResult)
			.exceptionally(e -> {dr.setErrorResult(e.getMessage()); return (Void) null;});

		return dr;
	}


	<T> CompletableFuture<T> toCF(ListenableFuture<T> lf) {
		CompletableFuture<T> cf = new CompletableFuture<>();
		lf.addCallback(cf::complete, cf::completeExceptionally);
		return cf;
	}
}
