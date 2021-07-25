package base.effective.item1;

public class Bag implements Item {
	private String bagName;
	private String author;

	Bag(String bagName, String author) {
		this.bagName = bagName;
		this.author = author;
	}

	@Override
	public String info() {
		return "bag name is " + bagName + ", author is " + author;
	}
}
