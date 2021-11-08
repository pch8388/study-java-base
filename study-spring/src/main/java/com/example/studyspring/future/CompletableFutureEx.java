package com.example.studyspring.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureEx {
	public static void main(String[] args) throws InterruptedException {
		CompletableFuture
				.runAsync(() -> log.info("runAsync"))
				.thenRun(() -> log.info("thenRun"))
				.thenRun(() -> log.info("thenRun"));

		ExecutorService es = Executors.newFixedThreadPool(10);

		CompletableFuture
			.supplyAsync(() -> {
				log.info("supplyAsync");
				// if (1==1) throw new RuntimeException();
				return 1;
			})
			.thenCompose(s -> {
				log.info("thenCompose {}", s);
				return CompletableFuture.completedFuture(s + 1);
			})
			.thenApplyAsync(s2 -> {
				log.info("thenApplyAsync {}", s2);
				return s2 * 3;
			}, es)
			.exceptionally(e -> -10)
			.thenAcceptAsync(s3 -> log.info("thenAccept {}", s3), es);
		log.info("exit");

		ForkJoinPool.commonPool().shutdown();
		ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
	}
}
