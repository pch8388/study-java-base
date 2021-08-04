package me.reactive.study.base;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SequenceCreatorTest {

	@Test
	void creator() throws InterruptedException {
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		List<Integer> sequence1 = sequenceGenerator.generateFibonacciWithTuples().take(3).collectList().block();
		List<Integer> sequence2 = sequenceGenerator.generateFibonacciWithTuples().take(4).collectList().block();

		SequenceCreator sequenceCreator = new SequenceCreator();
		Thread producingThread1 = new Thread(() -> sequenceCreator.consumer.accept(sequence1));
		Thread producingThread2 = new Thread(() -> sequenceCreator.consumer.accept(sequence2));

		List<Integer> consolidated = new ArrayList<>();
		sequenceCreator.createNumberSequence().subscribe(consolidated::add);

		producingThread1.start();
		producingThread2.start();
		producingThread1.join();
		producingThread2.join();

		assertThat(consolidated).containsExactlyInAnyOrder(0, 1, 1, 0, 1, 1 ,2);
	}
}