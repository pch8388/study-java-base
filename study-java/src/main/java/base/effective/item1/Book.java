package base.effective.item1;

public class Book implements Item {
	private String name;
	private String author;

	Book(String name, String author) {
		this.name = name;
		this.author = author;
	}

	@Override
	public String info() {
		return "book name is " + name + ", author is " + author;
	}
}
