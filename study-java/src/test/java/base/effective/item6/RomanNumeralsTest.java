package base.effective.item6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

	@Test
	@DisplayName("key set 데이터 공유 확인")
	void map_keySet() {
		Map<String, String> map = new HashMap<>();
		map.put("test1", "test1");
		map.put("test2", "test2");

		Set<String> preKeySet = map.keySet();
		assertThat(preKeySet.size()).isEqualTo(2);

		Set<String> keys = map.keySet();
		keys.remove("test1");

		assertThat(preKeySet == keys).isTrue();
		assertThat(preKeySet.size()).isEqualTo(keys.size());
	}
}