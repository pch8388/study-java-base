package me.reactive.study.base;

import static me.reactive.study.utils.Logger.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureEx {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();

		FutureTask<String> async = new FutureTask<>(() -> {
			Thread.sleep(2000);
			log("Async");
			return "Hello";
		});

		es.execute(async);

		log(async.isDone());
		log("before get");
		// 결과를 받기 위해 blocking 된다
		log(async.get());
		log(async.isDone());
		log("exit");

		es.shutdown();
	}
}
