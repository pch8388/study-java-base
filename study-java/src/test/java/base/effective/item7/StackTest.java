package base.effective.item7;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StackTest {

	@Test
	@DisplayName("메모리 누수 확인")
	void memory_leak() {
		Stack<String> stack = new Stack<>();
		stack.push("test1");
		stack.push("test2");
		stack.push("test3");

		assertEquals("test3", stack.pop());
		assertEquals("test2", stack.pop());

		// pop 을 하여 스택에서 꺼냈지만 메모리에 남아있음
		assertEquals("test3", stack.getElements()[2]);
		assertEquals("test2", stack.getElements()[1]);
	}
}