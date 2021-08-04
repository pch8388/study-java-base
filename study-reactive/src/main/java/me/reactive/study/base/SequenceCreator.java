package me.reactive.study.base;

import java.util.List;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;

public class SequenceCreator {
	public Consumer<List<Integer>> consumer;

	public Flux<Integer> createNumberSequence() {
		return Flux.create(sink ->
			SequenceCreator.this.consumer = items -> items.forEach(sink::next));
	}
}
