package me.reactive.study.debug;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

public class DebugTest {

	@DisplayName("Hooks.onOperatorDebug")
	@Test
	void debug_error() {
		Hooks.onOperatorDebug();
		FakeRepository.findAllUserByName(Flux.just("pedro", "simon", "stephane"))
			.transform(FakeUtil::applyFilters)
			.transform(FakeUtilSecond::fakeFilters)
			.blockLast();
	}

	static class FakeRepository {
		public static Flux<String> findAllUserByName(Flux<String> just) {
			return just.map(j -> {
				if ("simon".equals(j)) {
					throw new RuntimeException();
				}
				return j;
			});
		}
	}

	static class FakeUtil {
		public static Flux<String> applyFilters(Flux<String> flux) {
			return flux.map(f -> f);
		}
	}

	static class FakeUtilSecond {
		public static Flux<String> fakeFilters(Flux<String> flux) {
			return flux.map(f -> f);
		}
	}
}
