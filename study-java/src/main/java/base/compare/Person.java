package base.compare;

import java.util.Objects;

public class Person implements Comparable<Person> {
	private int age;
	private String name;

	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Person person = (Person)o;
		return age == person.age && Objects.equals(name, person.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, name);
	}

	@Override
	public String toString() {
		return "Person{" +
			"age=" + age +
			", name='" + name + '\'' +
			'}';
	}

	@Override
	public int compareTo(Person o) {
		return name.compareTo(o.name);
	}
}
