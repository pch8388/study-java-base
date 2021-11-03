package com.example.studyspring;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StudySpringApplication {

	@RestController
	public static class MyController {
		public static final String URL1 = "http://localhost:8083/service?req={req}";
		public static final String URL2 = "http://localhost:8083/service2?req={req}";
		AsyncRestTemplate rt = new AsyncRestTemplate();

		@Autowired MyService myService;

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
	}

	public static class ErrorCompletion<T> extends Completion<T, T> {
		Consumer<Throwable> econ;
		public ErrorCompletion(Consumer<Throwable> econ) {
			this.econ = econ;
		}

		@Override
		void error(Throwable e) {
			econ.accept(e);
		}

		@Override
		void run(T value) {
			if (next != null) next.run(value);
		}
	}

	public static class AcceptCompletion<S> extends Completion<S, Void> {
		Consumer<S> con;
		public AcceptCompletion(Consumer<S> con) {
			this.con = con;
		}

		@Override
		void run(S value) {
			con.accept(value);
		}
	}

	public static class ApplyCompletion<S, T> extends Completion<S, T> {
		Function<S, ListenableFuture<T>> fn;
		public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
			this.fn = fn;
		}

		@Override
		void run(S value) {
			fn.apply(value)
				.addCallback(this::complete, this::error);
		}
	}

	public static class Completion<S, T> {
		Completion next;

		public Completion() {}

		public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
			Completion<T, V> c = new ApplyCompletion<>(fn);
			this.next = c;
			return c;
		}

		public Completion<T, T> andError(Consumer<Throwable> econ) {
			Completion<T, T> c = new ErrorCompletion<>(econ);
			this.next = c;
			return c;
		}

		public void andAccept(Consumer<T> con) {
			Completion<T, Void> c = new AcceptCompletion<>(con);
			this.next = c;
		}
		public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
			Completion<S, T> c = new Completion<>();
			lf.addCallback(c::complete, c::error);
			return c;
		}

		void error(Throwable e) {
			if (next != null) next.error(e);
		}

		void complete(T s) {
			if (next != null) next.run(s);
		}

		void run(S value) {}
	}

	@Service
	public static class MyService {
		@Async
		public ListenableFuture<String> work(String req) {
			return new AsyncResult<>(req + "/asyncwork");
		}
	}

	// 하나의 thread 만 사용하도록 하기 위해 하나짜리 pool 을 만듬
	@Bean
	public ThreadPoolTaskExecutor myThreadPool() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(1);
		te.setMaxPoolSize(1);
		te.initialize();
		return te;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudySpringApplication.class, args);
	}
}
