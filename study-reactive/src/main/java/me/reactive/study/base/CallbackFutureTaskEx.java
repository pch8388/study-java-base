package me.reactive.study.base;

import static me.reactive.study.utils.Logger.*;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CallbackFutureTaskEx {
	interface SuccessCallback {
		void onSuccess(String result);
	}

	interface ExceptionCallback {
		void onError(Throwable t);
	}

	static class CallbackFutureTask extends FutureTask<String> {
		SuccessCallback sc;
		ExceptionCallback ec;
		public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
			super(callable);
			this.sc = Objects.requireNonNull(sc);
			this.ec = Objects.requireNonNull(ec);
		}

		@Override
		protected void done() {
			try {
				sc.onSuccess(get());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				ec.onError(e.getCause());
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();

		CallbackFutureTask f = new CallbackFutureTask(() -> {
			Thread.sleep(2000);
			log("Async");
			return "Hello";
		},
			s -> log("Result : " + s),
			e -> log("Error : " + e.getMessage()));

		es.execute(f);
		es.shutdown();
	}
}
