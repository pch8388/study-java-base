package architecture.boundary.encrypt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import architecture.boundary.io.ConsoleReader;
import architecture.boundary.io.ConsoleWriter;

class EncryptTest {

	@Test
	void encrypt() {
		Encrypt encrypt = new Encrypt(new ConsoleReader(), new ConsoleWriter());
		assertEquals("source", encrypt.encrypt());
	}
}