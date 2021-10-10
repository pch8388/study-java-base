package me.reactive.study.base;

import static me.reactive.study.utils.Logger.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class PubSubEx {
	public static void main(String[] args) {
		Publisher<Integer> pub = sub -> sub.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {
				log("request : " + n);
				sub.onNext(1);
				sub.onNext(2);
				sub.onNext(3);
				sub.onNext(4);
				sub.onNext(5);
				sub.onComplete();
			}

			@Override
			public void cancel() {

			}
		});

		Publisher<Integer> subOnPub = sub -> {
			ExecutorService es = Executors.newSingleThreadExecutor(r -> new Thread(r, "subOn"));
			es.execute(() -> pub.subscribe(sub));
			es.shutdown();
		};

		Publisher<Integer> pubOnPub = sub -> subOnPub.subscribe(new Subscriber<>() {
			final ExecutorService es = Executors.newSingleThreadExecutor(r -> new Thread(r, "pubOn"));
			@Override
			public void onSubscribe(Subscription s) {
				log("onSubscribe");
				sub.onSubscribe(s);
			}

			@Override
			public void onNext(Integer integer) {
				es.execute(() -> sub.onNext(integer));
			}

			@Override
			public void onError(Throwable t) {
				es.execute(() -> sub.onError(t));
				es.shutdown();
			}

			@Override
			public void onComplete() {
				es.execute(sub::onComplete);
				es.shutdown();
			}
		});

		pubOnPub.subscribe(new Subscriber<>() {
			final ExecutorService es = Executors.newSingleThreadExecutor(r -> new Thread(r, "final"));
			@Override
			public void onSubscribe(Subscription s) {
				log("onSubscribe");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer integer) {
				es.execute(() -> log("onNext : " + integer));
			}

			@Override
			public void onError(Throwable t) {
				es.execute(() -> log("onError : " + t.getMessage()));
				es.shutdown();
			}

			@Override
			public void onComplete() {
				es.execute(() -> log("onComplete"));
				es.shutdown();
			}
		});

		System.out.println("exit");
	}
}
