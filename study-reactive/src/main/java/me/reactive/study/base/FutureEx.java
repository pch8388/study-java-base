package me.reactive.study.base;

import static me.reactive.study.utils.Logger.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureEx {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();

		FutureTask<String> async = new FutureTask<>(() -> {
			Thread.sleep(2000);
			log("Async");
			return "Hello";
		}) {
			@Override
			protected void done() {
				try {
					log(get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};

		es.execute(async);
		log("exit");

		es.shutdown();
	}
}
