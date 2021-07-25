package base.effective.item1;

public interface Item {
	static Item of(String name, String auth) {
		if (name.startsWith("book")) {
			return new Book(name, auth);
		}
		return new Bag(name, auth);
	}
	static Item from(String name) {
		if (name.startsWith("book")) {
			return new Book(name, "anonymous");
		}
		return new Bag(name, "star");
	}

	String info();
}
