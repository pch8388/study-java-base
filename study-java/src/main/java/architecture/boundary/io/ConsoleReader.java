package architecture.boundary.io;

import architecture.boundary.encrypt.CharReader;

public class ConsoleReader implements CharReader {
	@Override
	public String read() {
		return "source";
	}
}
