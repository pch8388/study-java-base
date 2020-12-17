package me.study.base.compare;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CompareTest {

	@Test
	void sort_primitive() {
		int[] arr = new int[] {9, 3, 1, 4, 5, 8};
		int[] expected = new int[] {1, 3, 4, 5, 8, 9};

		Arrays.sort(arr);
		assertArrayEquals(expected, arr);
	}

	@Test
	void sort_string() {
		String[] arr = new String[] {"a", "c", "e", "k", "d"};
		String[] expected = new String[] {"a", "c", "d", "e", "k"};

		Arrays.sort(arr);
		assertArrayEquals(expected, arr);
	}

	@Test
	void sort_object() {
		assertThrows(ClassCastException.class,
			() -> Arrays.sort(IntStream.range(0, 10)
				.mapToObj(NotCompare::new).toArray()));
	}

	@Test
	void sort_comparator_implements() {
		List<Integer> numbers = Arrays.asList(3, 9, -1, 7, 6);
		List<Integer> expected = Arrays.asList(9, 7, 6, 3, -1);

		numbers.sort(Comparator.comparing(Integer::intValue).reversed());
		assertEquals(expected, numbers);
	}

	@Test
	void sort_thenComparing() {
		List<Person> people = Arrays.asList(
			new Person(10, "bcd"),
			new Person(12, "csb"),
			new Person(10, "abc")
		);
		List<Person> expected = Arrays.asList(
			new Person(10, "abc"),
			new Person(10, "bcd"),
			new Person(12, "csb")
		);

		people.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));
		assertEquals(expected, people);
	}
}
