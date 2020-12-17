package me.study.base.compare;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompareTest {

	@Test
	@DisplayName("기본형 정렬")
	void sort_primitive() {
		int[] arr = new int[] {9, 3, 1, 4, 5, 8};
		int[] expected = new int[] {1, 3, 4, 5, 8, 9};

		Arrays.sort(arr);
		assertArrayEquals(expected, arr);
	}

	@Test
	@DisplayName("문자열 정렬")
	void sort_string() {
		String[] arr = new String[] {"a", "c", "e", "k", "d"};
		String[] expected = new String[] {"a", "c", "d", "e", "k"};

		Arrays.sort(arr);
		assertArrayEquals(expected, arr);
	}

	@Test
	@DisplayName("Comparable 을 구현하지 않은 객체는 예외 발생")
	void sort_object_exception() {
		assertThrows(ClassCastException.class,
			() -> Arrays.sort(IntStream.range(0, 10)
				.mapToObj(NotCompare::new).toArray()));
	}

	@Test
	@DisplayName("Comparable 을 구현한 객체의 정렬")
	void sort_object() {
		List<Compare> compares = IntStream.range(0, 10)
			.mapToObj(Compare::new).collect(toList());

		List<Compare> expected = new ArrayList<>();
		for (int i = 9; i >= 0; i--) {
			expected.add(new Compare(i));
		}

		Collections.sort(compares);

		assertEquals(expected, compares);
	}

	@Test
	@DisplayName("comparator 통해 정렬")
	void sort_comparator_implements() {
		List<Integer> numbers = Arrays.asList(3, 9, -1, 7, 6);
		List<Integer> expected = Arrays.asList(9, 7, 6, 3, -1);

		numbers.sort((n1, n2) -> n2 - n1);
		assertEquals(expected, numbers);
	}

	@Test
	@DisplayName("함수를 통해 정렬조건을 여러개 넣기")
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

	@Test
	@DisplayName("PriorityQueue 순서 확인")
	void priority_queue() {
		PriorityQueue<Person> people = new PriorityQueue<>(Comparator.comparing(Person::getAge));
		people.offer(new Person(15, "aaa"));
		people.offer(new Person(20, "aaa"));
		people.offer(new Person(11, "aaa"));
		people.offer(new Person(30, "aaa"));
		people.offer(new Person(33, "aaa"));
		people.offer(new Person(3, "aaa"));
		people.offer(new Person(13, "aaa"));
		people.offer(new Person(14, "aaa"));
		people.offer(new Person(15, "aaa"));

		while (!people.isEmpty()) {
			System.out.println(people);
			System.out.println(people.poll());
		}
	}


	@Test
	void shift_operation() {
		IntStream.range(0, 11).map(i -> i >>> 1).forEach(System.out::println);
	}
}
