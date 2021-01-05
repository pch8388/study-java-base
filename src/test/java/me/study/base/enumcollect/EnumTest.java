package me.study.base.enumcollect;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnumTest {

	@Test
	@DisplayName("HashMap 과 EnumMap 비교")
	void map() {
		Map<String, String> map = new HashMap<>();
		map.put(null, "test");
		assertEquals("test", map.get(null));

		Map<NumberEnum, String> enumMap = new EnumMap<>(NumberEnum.class);
		assertThrows(NullPointerException.class, () -> enumMap.put(null, "enum-test"));

		enumMap.put(NumberEnum.ONE, "one");
		assertEquals("one", enumMap.get(NumberEnum.ONE));

		assertDoesNotThrow(() -> enumMap.get(null));
		assertDoesNotThrow(() -> enumMap.remove(null));

		Map<NumberEnum, String> syncEnumMap = Collections.synchronizedMap(new EnumMap<>(NumberEnum.class));
		syncEnumMap.put(NumberEnum.ONE, "one");
		assertEquals("one", syncEnumMap.get(NumberEnum.ONE));
	}

	@Test
	@DisplayName("")
	void enum_function() {
		Map<NumberEnum, Function<String, Integer>> enumMap = new EnumMap<>(NumberEnum.class);
		enumMap.put(NumberEnum.ONE, s -> Integer.parseInt(s) + 1);
		enumMap.put(NumberEnum.TWO, s -> Integer.parseInt(s) + 2);
		enumMap.put(NumberEnum.THREE, s -> Integer.parseInt(s) + 3);

		assertEquals(2, enumMap.get(NumberEnum.ONE).apply("1"));
		assertEquals(3, enumMap.get(NumberEnum.TWO).apply("1"));
		assertEquals(4, enumMap.get(NumberEnum.THREE).apply("1"));
	}
}