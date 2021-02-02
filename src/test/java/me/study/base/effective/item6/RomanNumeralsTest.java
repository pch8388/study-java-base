package me.study.base.effective.item6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RomanNumeralsTest {

	@Test
	@DisplayName("String matches 사용")
	void slowMethod() {
		for (int i = 0; i < 100_000; i++) {
			RomanNumerals.isRomanNumeralWithStringMatchers("MCMLXXVI");
		}
	}

	@Test
	@DisplayName("Pattern matches 사용")
	void fastMethod() {
		for (int i = 0; i < 100_000; i++) {
			RomanNumerals.isRomanNumeralWithPattern("MCMLXXVI");
		}
	}

	@Test
	@DisplayName("primitive type sum")
	void primitive_sum() {
		long sum = 0L;
		for (int i = 0; i < 1_000_000_000; i++) {
			sum += i;
		}
	}

	@Test
	@DisplayName("wrapper type sum")
	void wrapper_sum() {
		Long sum = 0L;
		for (int i = 0; i < 1_000_000_000; i++) {
			sum += i;
		}
	}
}