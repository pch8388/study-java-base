package architecture.boundary.io;

import architecture.boundary.encrypt.CharWriter;

public class ConsoleWriter implements CharWriter {
	@Override
	public String write(String source) {
		return source;
	}
}
