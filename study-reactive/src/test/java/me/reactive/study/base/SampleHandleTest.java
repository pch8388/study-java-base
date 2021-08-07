package me.reactive.study.base;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class SampleHandleTest {

	@Test
	void handle_map_null() {
		Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
			.handle((i, sink) -> {
				String letter = alphabet(i);
				if (letter != null) {
					sink.next(letter);
				}
		});

		StepVerifier.create(alphabet)
			.expectNext("M", "I", "T")
			.expectComplete()
			.verify();
	}

	private String alphabet(int letterNumber) {
		if (letterNumber < 1 || letterNumber > 26) {
			return null;
		}

		int letterIndexAscii = 'A' + letterNumber - 1;
		return "" + (char) letterIndexAscii;
	}
}
