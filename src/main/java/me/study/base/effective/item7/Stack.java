package me.study.base.effective.item7;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public E push(E item) {
		ensureCapacity();
		elements[size++] = item;
		return item;
	}

	public E pop() {
		if (size == 0)
			throw new EmptyStackException();
		return (E) elements[--size];
	}

	Object[] getElements() {
		return elements;
	}
	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}
}
