package me.study.base.compare;

import java.util.Objects;

public class Compare implements Comparable<Compare> {
	private int number;

	public Compare(int number) {
		this.number = number;
	}

	@Override
	public int compareTo(Compare o) {
		return o.number - number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Compare compare = (Compare)o;
		return number == compare.number;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
}
