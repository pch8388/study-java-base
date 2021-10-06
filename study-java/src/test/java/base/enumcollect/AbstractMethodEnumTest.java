package base.enumcollect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AbstractMethodEnumTest {

	@DisplayName("enum 에 abstract method 구현")
	@Test
	void apply() {
		assertEquals("abstract method enum test",
			AbstractMethodEnum.PRINT.apply("abstract method"));
	}
}