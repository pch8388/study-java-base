package me.reactive.study.utils;

public class Logger {
	public static void log(String message) {
		final String threadName = Thread.currentThread().getName();
		final String printMessage = String.format("[ %s ] : %s", threadName, message);
		System.out.println(printMessage);
	}

	public static void log(boolean message) {
		log(String.valueOf(message));
	}
}
