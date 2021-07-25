package base.effective.item1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {

	@Test
	@DisplayName("정적 팩토리 메서드 사용")
	void factory_method() {
		final Item book = Item.of("book-Effective Java", "Joshua");
		final Item bag = Item.of("bag-backpack", "me");

		assertEquals("book name is book-Effective Java, author is Joshua", book.info());
		assertEquals("bag name is bag-backpack, author is me", bag.info());
	}

	@Test
	@DisplayName("값을 미리 저장")
	void cache_value() {
		assertEquals(IntegerFactory.valueOf(0), IntegerFactory.valueOf(0));
		assertEquals(IntegerFactory.valueOf(10), IntegerFactory.valueOf(10));
		assertNotEquals(IntegerFactory.valueOf(111), IntegerFactory.valueOf(111));
	}

}